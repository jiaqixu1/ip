package nock;

import java.util.List;

/**
 * The main application class for Nock.
 * <p>
 * This GUI-oriented version generates reply strings for user inputs.
 * It loads tasks from storage on startup and saves after state-changing commands.
 */
public class Nock {

    private static final String DEFAULT_FILE_PATH = "data/nock.txt";

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    /**
     * Constructs a {@code Nock} instance using the given file path for storage.
     *
     * @param filePath Path to the data file used for loading/saving tasks.
     */
    public Nock(String filePath) {
        assert filePath != null && !filePath.isBlank();

        this.ui = new Ui();
        this.storage = new Storage(filePath);

        TaskList loaded;
        try {
            loaded = new TaskList(storage.load());
        } catch (NockException e) {
            loaded = new TaskList();
        }
        this.tasks = loaded;
    }

    /**
     * Constructs a {@code Nock} instance using the default storage file path.
     */
    public Nock() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Generates a response for a user's input.
     * <p>
     * This method parses the input into a command, executes it, saves changes
     * when necessary, and returns a formatted reply string for the GUI to display.
     *
     * @param input The raw user input string.
     * @return A response string to be displayed to the user.
     */
    public String getResponse(String input) {
        try {
            ParsedCommand command = Parser.parse(input);
            return executeAndGetReply(command);
        } catch (NockException e) {
            return e.getMessage();
        }
    }

    /**
     * Executes a parsed command and returns a formatted reply string.
     *
     * @param command The parsed command to execute.
     * @return A formatted reply string describing the result of the command.
     * @throws NockException If execution fails due to invalid command data.
     */
    private String executeAndGetReply(ParsedCommand command) throws NockException {
        switch (command.type) {
            case EXIT:
                return ui.formatGoodbye();

            case LIST:
                return ui.formatTasks(tasks.getTasks());

            case MARK: {
                Task markTask = tasks.get(command.index);
                markTask.markDone();
                storage.save(tasks.getTasks());
                return ui.formatMarked(markTask);
            }

            case UNMARK: {
                Task unmarkTask = tasks.get(command.index);
                unmarkTask.markUndone();
                storage.save(tasks.getTasks());
                return ui.formatUnmarked(unmarkTask);
            }

            case DELETE: {
                Task removed = tasks.remove(command.index);
                storage.save(tasks.getTasks());
                return ui.formatDeleted(removed, tasks.size());
            }

            case TODO: {
                Task todo = new Todo(command.desc);
                tasks.add(todo);
                storage.save(tasks.getTasks());
                return ui.formatAdded(todo, tasks.size());
            }

            case DEADLINE: {
                Task deadline = new Deadline(command.desc, command.by);
                tasks.add(deadline);
                storage.save(tasks.getTasks());
                return ui.formatAdded(deadline, tasks.size());
            }

            case EVENT: {
                Task event = new Event(command.desc, command.from, command.to);
                tasks.add(event);
                storage.save(tasks.getTasks());
                return ui.formatAdded(event, tasks.size());
            }

            case FIND: {
                List<Task> matches = tasks.findTasks(command.desc);
                return ui.formatFindResults(matches);
            }

            case HELP:
                return ui.formatHelp();

            default:
                throw new NockException("Unknown command."); //exception
        }
    }
}