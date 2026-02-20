
public class Nock {

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;

    public Nock(String filePath) {
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

            default:
                throw new NockException("Unknown command.");
        }
    }

    public static void main(String[] args) {
        new Nock("./data/nock.txt").run();
    }
}
