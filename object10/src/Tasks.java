import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Tasks {

    private String title;
    private final Set<Task> list = new HashSet<>();

    public Tasks(String title) {
        setTitle(title);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void addTask(String title, LocalDateTime date) {
        list.add(new Task(title, date));
    }

    public void addRemove(Task task) {
        //메모리 주소로 식별된당.
        list.remove(task);
    }
    public List<Task> getList(SortType type) {
        List<Task> tasks = new ArrayList<>(list);
        //얖은 복사를 수행하게 됨.
        tasks.sort((a, b) -> type.compare(a, b));
        return tasks;
    }
}
