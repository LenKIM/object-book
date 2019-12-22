import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CompositeTask {

    private String title;
    private LocalDateTime date;
    private Boolean isComplete = false;
    private final Set<CompositeTask> list = new HashSet<>();

    public CompositeTask(String title, LocalDateTime date) {
        setTitle(title);
        setDate(date);
    }

    public void toggle() {
        isComplete = !isComplete;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void addTask(String title, LocalDateTime date) {
        list.add(new CompositeTask(title, date));
    }

    public void addRemove(CompositeTask task) {
        //메모리 주소로 식별된당.
        list.remove(task);
    }

    public TaskReport getReport(CompositeSortType type) {
//        List<CompositeTask> tasks = new ArrayList<>(list);
        //얖은 복사를 수행하게 됨.
        // 문제 발생!!!!!
//        tasks.sort((a, b) -> type.compare(a, b));

        TaskReport report = new TaskReport(this);
        for (CompositeTask t : list) {
            report.add(t.getReport(type));
        }
        return report;
    }

    public boolean isComplete(){
        return isComplete;
    }
}
