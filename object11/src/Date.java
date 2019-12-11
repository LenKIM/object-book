import java.time.LocalDateTime;

public class Date implements Command {

    private final LocalDateTime localDateTime;
    private LocalDateTime oldLocalDateTime;

    public Date(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    @Override
    public void execute(CompositeTask task) {
        oldLocalDateTime = task.getDate();
        task.setDate(localDateTime);
    }

    @Override
    public void undo(CompositeTask task) {
        task.setDate(oldLocalDateTime);
    }
}
