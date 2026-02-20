import java.time.LocalDate;

public class ParsedCommand {
    public enum Type {
        EXIT, LIST, MARK, UNMARK, DELETE, TODO, DEADLINE, EVENT
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

    public static ParsedCommand exit() { return new ParsedCommand(Type.EXIT, -1, null, null, null, null); }
    public static ParsedCommand list() { return new ParsedCommand(Type.LIST, -1, null, null, null, null); }

    public static ParsedCommand mark(int index) { return new ParsedCommand(Type.MARK, index, null, null, null, null); }
    public static ParsedCommand unmark(int index) { return new ParsedCommand(Type.UNMARK, index, null, null, null, null); }
    public static ParsedCommand delete(int index) { return new ParsedCommand(Type.DELETE, index, null, null, null, null); }

    public static ParsedCommand addTodo(String desc) { return new ParsedCommand(Type.TODO, -1, desc, null, null, null); }
    public static ParsedCommand addDeadline(String desc, LocalDate by) { return new ParsedCommand(Type.DEADLINE, -1, desc, by, null, null); }
    public static ParsedCommand addEvent(String desc, String from, String to) { return new ParsedCommand(Type.EVENT, -1, desc, null, from, to); }
}