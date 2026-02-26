package nock;

import java.time.LocalDate;

public class ParsedCommand {
    public enum Type {
        EXIT, LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT, FIND, HELP
    }

    public final Type type;
    public final int index;
    public final String desc;
    public final LocalDate by;
    public final String from;
    public final String to;

    private ParsedCommand(Type type, int index, String desc, LocalDate by, String from, String to) {
        this.type = type;
        this.index = index;
        this.desc = desc;
        this.by = by;
        this.from = from;
        this.to = to;
    }
   /**
    * Exit the program
    */
    public static ParsedCommand exit() {
        return new ParsedCommand(Type.EXIT, -1, null, null, null, null);
    }

    /**
     *
     * @return List
     */
    public static ParsedCommand list() {
        return new ParsedCommand(Type.LIST, -1, null, null, null, null);
    }

    /**
     * Mark items
     * @param index task index
     * @return Mark
     */
    public static ParsedCommand mark(int index) {
        return new ParsedCommand(Type.MARK, index, null, null, null, null);
    }

    /**
     * Umark items
     * @param index task index
     * @return Unmark
     */
    public static ParsedCommand unmark(int index) {
        return new ParsedCommand(Type.UNMARK, index, null, null, null, null);
    }

    /**
     * Remove items
     * @param index task index
     * @return Delete
     */
    public static ParsedCommand delete(int index) {
        return new ParsedCommand(Type.DELETE, index, null, null, null, null);
    }

    /**
     * Add items
     * @param desc todo
     * @return Todo
     */
    public static ParsedCommand addTodo(String desc) {
        return new ParsedCommand(Type.TODO, -1, desc, null, null, null);
    }

    /**
     * Add Deadline
     * @param desc todo
     * @param by ending time
     * @return Deadline
     */
    public static ParsedCommand addDeadline(String desc, LocalDate by) {
        return new ParsedCommand(Type.DEADLINE, -1, desc, by, null, null);
    }

    /**
     * Add a event
     * @param desc todo
     * @param from starting time
     * @param to ending time
     * @return Event
     */
    public static ParsedCommand addEvent(String desc, String from, String to) {
        return new ParsedCommand(Type.EVENT, -1, desc, null, from, to);
    }

    /**
     * Find an item
     * @param keyword keyword searched
     * @return Find
     */
    public static ParsedCommand find(String keyword) {
        return new ParsedCommand(Type.FIND, -1, keyword, null, null, null);
    }

    public static ParsedCommand help() {
        return new ParsedCommand(Type.HELP, -1, null, null, null, null);
    }
}