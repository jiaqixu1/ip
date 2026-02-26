package nock;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
/**
 * Parses user input strings into {@code ParsedCommand} objects.
 * <p>
 * This class is responsible for interpreting raw text entered by the user
 * and converting it into structured commands that can be executed by the system.
 */

public class Parser {
    /**
     * Parses a raw user input string and converts it into a {@code ParsedCommand}.
     *
     * <p>The method supports the following commands:
     * <ul>
     *     <li>{@code bye}</li>
     *     <li>{@code list}</li>
     *     <li>{@code mark <index>}</li>
     *     <li>{@code unmark <index>}</li>
     *     <li>{@code delete <index>}</li>
     *     <li>{@code todo <description>}</li>
     *     <li>{@code deadline <description> /by <yyyy-MM-dd>}</li>
     *     <li>{@code event <description> /from <start> /to <end>}</li>
     *     <li>{@code find <keyword>}</li>
     *     <li>{@code help}</li>
     * </ul>
     *
     * @param input The raw input string entered by the user.
     * @return A {@code ParsedCommand} representing the parsed command.
     * @throws NockException If the input is invalid, incomplete,
     *                       or does not match any known command format.
     */
    public static ParsedCommand parse(String input) throws NockException {
        String trimmed = input.trim();

        if (trimmed.equals("bye")) return ParsedCommand.exit();
        if (trimmed.equals("list")) return ParsedCommand.list();
        if (trimmed.startsWith("mark")) return ParsedCommand.mark(parseIndex(trimmed, "mark"));
        if (trimmed.startsWith("unmark")) return ParsedCommand.unmark(parseIndex(trimmed, "unmark"));
        if (trimmed.startsWith("delete")) return ParsedCommand.delete(parseIndex(trimmed, "delete"));

        if (trimmed.startsWith("todo")) {
            String desc = trimmed.substring(4).trim();
            if (desc.isEmpty()) throw new NockException("Todo description cannot be empty.");
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

        if (trimmed.startsWith("find ")) {
            String keyword = trimmed.substring(5).trim();
            if (keyword.isEmpty()) {
                throw new NockException("The keyword for find cannot be empty.");
            }
            return ParsedCommand.find(keyword);
        }

        if (trimmed.equals("find")) {
            throw new NockException("The keyword for find cannot be empty.");
        }

        if (trimmed.equals("help")) {
            return ParsedCommand.help();
        }

        throw new NockException("I don't know what that means.");
    }

    /**
     * Extracts and parses the task index from commands such as
     * {@code mark}, {@code unmark}, and {@code delete}.
     *
     * <p>The user provides a 1-based index, but this method converts it
     * into a 0-based index for internal storage.
     *
     * @param input   The full user input string.
     * @param command The command keyword (e.g., "mark", "delete").
     * @return The parsed task index (0-based).
     * @throws NockException If no index is provided or the index
     *                       is not a valid integer.
     */
    private static int parseIndex(String input, String command) throws NockException {
        String numberPart = input.substring(command.length()).trim();
        if (numberPart.isEmpty()) throw new NockException("Please provide a task number.");
        try {
            assert Integer.parseInt(numberPart) - 1 >= 0;
            return Integer.parseInt(numberPart) - 1; // store as 0-based
        } catch (NumberFormatException e) {
            throw new NockException("Task number must be an integer.");
        }
    }

    /**
     * Parses a date string in ISO format ({@code yyyy-MM-dd})
     * into a {@code LocalDate} object.
     *
     * @param s The date string to parse.
     * @return The parsed {@code LocalDate}.
     * @throws NockException If the date format is invalid.
     */
    private static LocalDate parseDate(String s) throws NockException {
        try {
            return LocalDate.parse(s, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            throw new NockException("Date must be yyyy-MM-dd. Example: 2019-12-02");
        }
    }
}