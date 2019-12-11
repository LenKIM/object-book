import java.time.LocalDateTime;

public class Add implements Command {

    private final String title;
    private final LocalDateTime date;
    private CompositeTask oldTask;

    public Add(String title, LocalDateTime date) {
        this.date = date;
        this.title = title;
    }

    @Override
    public void execute(CompositeTask task) {
        oldTask = task.addTask(title, date);
    }

    @Override
    public void undo(CompositeTask task) {
        task.removeTask(oldTask);
    }

}
