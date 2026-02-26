package nock;
import java.util.List;

public class Nock {

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;
    private static final String DEFAULT_FILE_PATH = "data/nock.txt";

    public Nock(String filePath) {
        assert filePath != null && !filePath.isBlank();
        ui = new Ui();
        storage = new Storage(filePath);

        TaskList loaded;
        try {
            loaded = new TaskList(storage.load());
        } catch (NockException e) {
            ui.showError("Error loading data file. Starting with empty list.");
            loaded = new TaskList();
        }

        tasks = loaded;
    }

    public Nock() {
        this(DEFAULT_FILE_PATH);
    }

    /**
     * Run the program
     */
    public void run() {
        ui.showWelcome();

        boolean isExit = false;

        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ParsedCommand command = Parser.parse(fullCommand);
                isExit = execute(command);
            } catch (NockException e) {
                ui.showError(e.getMessage());
            }
        }

        ui.showGoodbye();
    }

/**
 * Execute different commands
 */
    private boolean execute(ParsedCommand command) throws NockException {

        switch (command.type) {

            case EXIT:
                return true;

            case LIST:
                ui.showTasks(tasks.getTasks());
                return false;

            case MARK:
                Task markTask = tasks.get(command.index);
                markTask.markDone();
                storage.save(tasks.getTasks());
                ui.showMarked(markTask);
                return false;

            case UNMARK:
                Task unmarkTask = tasks.get(command.index);
                unmarkTask.markUndone();
                storage.save(tasks.getTasks());
                ui.showUnmarked(unmarkTask);
                return false;

            case DELETE:
                Task removed = tasks.remove(command.index);
                storage.save(tasks.getTasks());
                ui.showDeleted(removed, tasks.size());
                return false;

            case TODO:
                Task todo = new Todo(command.desc);
                tasks.add(todo);
                storage.save(tasks.getTasks());
                ui.showAdded(todo, tasks.size());
                return false;

            case DEADLINE:
                Task deadline = new Deadline(command.desc, command.by);
                tasks.add(deadline);
                storage.save(tasks.getTasks());
                ui.showAdded(deadline, tasks.size());
                return false;

            case EVENT:
                Task event = new Event(command.desc, command.from, command.to);
                tasks.add(event);
                storage.save(tasks.getTasks());
                ui.showAdded(event, tasks.size());
                return false;

            case FIND:
                List<Task> matches = tasks.findTasks(command.desc);
                ui.showFindResults(matches);
                return false;

            case HELP:
                ui.showHelp();
                return false;

            default:
                throw new NockException("Unknown command.");
        }
    }

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
                throw new NockException("Unknown command.");
        }
    }
    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        try {
            ParsedCommand command = Parser.parse(input);
            return executeAndGetReply(command);
        } catch (NockException e) {
            return e.getMessage();
        }
    }
    public String getWelcomeMessage() {
        return "Hi, I am Nock. What can I help you?";
    }
    public static void main(String[] args) {
        new Nock("./data/nock.txt").run();
    }
}
