package nock;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.time.LocalDate;

/**
 * Handles loading and saving tasks to a local file.
 * <p>
 * The {@code Storage} class is responsible for reading tasks from disk
 * when the application starts and writing tasks back to disk when changes occur.
 */
public class Storage {
    private final String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the storage file.
     * <p>
     * If the file does not exist, an empty task list is returned.
     *
     * @return An {@code ArrayList} containing all tasks loaded from the file.
     * @throws NockException If an I/O error occurs or the file format is corrupted.
     */
    public ArrayList<Task> load() throws NockException {
        ArrayList<Task> tasks = new ArrayList<>();
        File file = new File(filePath);

        if (!file.exists()) {
            return tasks; 
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

    /**
     * Saves the given list of tasks to the storage file.
     * <p>
     * If the parent directory does not exist, it will be created automatically.
     *
     * @param tasks The list of tasks to be saved.
     * @throws NockException If an I/O error occurs during saving.
     */
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

    /**
     * Parses a single line from the storage file into a {@code Task}.
     * <p>
     * The expected file format is:
     * <pre>
     * T | 0 | description
     * D | 1 | description | yyyy-MM-dd
     * E | 0 | description | from | to
     * </pre>
     *
     * @param line A line from the storage file.
     * @return The corresponding {@code Task} object.
     * @throws NockException If the line format is invalid or corrupted.
     */
    private Task parseTask(String line) throws NockException {
        String[] parts = line.split(" \\| ");


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
                LocalDate byDate = LocalDate.parse(parts[3]);
                Deadline d = new Deadline(parts[2], byDate);
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


    /**
     * Converts a {@code Task} object into a string representation
     * suitable for storage in the file.
     *
     * @param task The task to format.
     * @return A formatted string representing the task.
     */
    private String formatTask(Task task) {
        String done = task.isDone ? "1" : "0";

        if (task instanceof Todo) {
            return "T | " + done + " | " + task.description;
        }
        if (task instanceof Deadline) {
            Deadline d = (Deadline) task;
            return "D | " + done + " | " + d.description + " | " + d.getByForStorage();
        }
        if (task instanceof Event) {
            Event e = (Event) task;
            return "E | " + done + " | " + e.description + " | " + e.from + " | " + e.to;
        }
        return "";
    }
}

