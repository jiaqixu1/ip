package nock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {

    public static ParsedCommand parse(String input) throws NockException {
        String trimmed = input.trim();

        if (trimmed.equals("bye")) return ParsedCommand.exit();
        if (trimmed.equals("list")) return ParsedCommand.list();

        if (trimmed.startsWith("mark")) return ParsedCommand.mark(parseIndex(trimmed, "mark"));
        if (trimmed.startsWith("unmark")) return ParsedCommand.unmark(parseIndex(trimmed, "unmark"));
        if (trimmed.startsWith("delete")) return ParsedCommand.delete(parseIndex(trimmed, "delete"));

        if (trimmed.startsWith("todo")) {
            String desc = trimmed.substring(4).trim();
            if (desc.isEmpty()) throw new NockException("nock.Todo description cannot be empty.");
            return ParsedCommand.addTodo(desc);
        }

        if (trimmed.startsWith("deadline")) {
            String rest = trimmed.substring(8).trim();
            String[] parts = rest.split(" /by ", 2);
            if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                throw new NockException("Use: deadline <description> /by <yyyy-MM-dd>");
            }
            LocalDate by = parseDate(parts[1].trim());
            return ParsedCommand.addDeadline(parts[0].trim(), by);
        }

        if (trimmed.startsWith("event")) {
            String rest = trimmed.substring(5).trim();
            String[] p1 = rest.split(" /from ", 2);
            if (p1.length < 2 || p1[0].trim().isEmpty()) {
                throw new NockException("Use: event <description> /from <start> /to <end>");
            }
            String[] p2 = p1[1].split(" /to ", 2);
            if (p2.length < 2 || p2[0].trim().isEmpty() || p2[1].trim().isEmpty()) {
                throw new NockException("Use: event <description> /from <start> /to <end>");
            }
            return ParsedCommand.addEvent(p1[0].trim(), p2[0].trim(), p2[1].trim());
        }

        throw new NockException("I don't know what that means.");
    }

    private static int parseIndex(String input, String command) throws NockException {
        String numberPart = input.substring(command.length()).trim();
        if (numberPart.isEmpty()) throw new NockException("Please provide a task number.");
        try {
            return Integer.parseInt(numberPart) - 1; // store as 0-based
        } catch (NumberFormatException e) {
            throw new NockException("nock.Task number must be an integer.");
        }
    }

    private static LocalDate parseDate(String s) throws NockException {
        try {
            return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new NockException("Date must be yyyy-MM-dd. Example: 2019-12-02");
        }
    }
}