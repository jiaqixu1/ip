import java.io.*;
import java.util.ArrayList;

public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws NockException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks; // first run: nothing to load
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }
                tasks.add(parseTask(line));
            }
        } catch (IOException e) {
            throw new NockException("Error loading tasks from file.");
        }

        return tasks;
    }

    public void save(ArrayList<Task> tasks) throws NockException {
        try {
            File file = new File(filePath);
            File parent = file.getParentFile();
            if (parent != null) {
                parent.mkdirs(); // create ./data if missing
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                for (Task task : tasks) {
                    bw.write(formatTask(task));
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            throw new NockException("Error saving tasks to file.");
        }
    }

    private Task parseTask(String line) throws NockException {
        String[] parts = line.split(" \\| ");

        // Minimal corruption handling
        if (parts.length < 3) {
            throw new NockException("Corrupted data file.");
        }

        String type = parts[0];
        boolean done = parts[1].equals("1");

        switch (type) {
            case "T": {
                Todo t = new Todo(parts[2]);
                if (done) t.markDone();
                return t;
            }
            case "D": {
                if (parts.length < 4) throw new NockException("Corrupted data file.");
                Deadline d = new Deadline(parts[2], parts[3]);
                if (done) d.markDone();
                return d;
            }
            case "E": {
                if (parts.length < 5) throw new NockException("Corrupted data file.");
                Event e = new Event(parts[2], parts[3], parts[4]);
                if (done) e.markDone();
                return e;
            }
            default:
                throw new NockException("Corrupted data file.");
        }
    }

    private String formatTask(Task task) {
        String done = task.isDone ? "1" : "0";

        if (task instanceof Todo) {
            return "T | " + done + " | " + task.description;
        }
        if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            return "D | " + done + " | " + d.description + " | " + d.by;
        }
        if (task instanceof Event) {
            Event e = (Event) task;
            return "E | " + done + " | " + e.description + " | " + e.from + " | " + e.to;
        }
        return "";
    }
}

