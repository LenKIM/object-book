import java.util.ArrayList;
import java.util.List;

public class TaskReport {

    private final CompositeTask task; // tree
    private final List<TaskReport> list = new ArrayList<>(); //leaf

    public TaskReport(CompositeTask task) {
        this.task = task;
    }

    public void add(TaskReport report) {
        list.add(report);
    }

    public CompositeTask getTask() {
        return task;
    }

    public List<TaskReport> getList() {
        return list;
    }
}
