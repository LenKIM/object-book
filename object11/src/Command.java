public interface Command {
    void execute(CompositeTask task);
    void undo(CompositeTask task);
}
