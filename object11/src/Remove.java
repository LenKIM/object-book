import java.time.LocalDateTime;

public class Remove implements Command {

    private final CompositeTask baseTask;
    private String oldTitle;
    private LocalDateTime oldDate;

    public Remove(CompositeTask task) {
        this.baseTask = task;
    }

    @Override
    public void execute(CompositeTask task) {
        oldTitle = task.getTitle();
        oldDate = task.getDate();
        task.removeTask(baseTask);
    }

    @Override
    public void undo(CompositeTask task) {
        task.addTask(oldTitle, oldDate);
    }
}
