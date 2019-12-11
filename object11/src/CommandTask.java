import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CommandTask {

    private final CompositeTask task;
    private List<Command> commands = new ArrayList<>();

    private int cursor = 0;

    public void redo() {
        if (cursor == commands.size() - 1) return;
        commands.get(++cursor).execute(task);
    }

    public void undo() {
        if (cursor < 0) return;
        Command cmd = commands.get(cursor--);
        cmd.undo(task);
    }

    public CommandTask(String title, LocalDateTime date) {
        task = new CompositeTask(title, date);
    }

    private void addCommand(Command cmd) {
//        if (commands.size() > cursor + 1) {
//            commands.subList(cursor + 1, commands.size()).clear();
//        }
        for (int i = commands.size() - 1; i > cursor; i--) {
            commands.remove(i);
        }
        cmd.execute(task);
        commands.add(cmd);
        cursor = commands.size() - 1; //커서 위치 확정.
    }

    public void toggle() {
        addCommand(new Toggle());
        Command cmd = new Toggle(); //여기서 Toggle이 Command객체가 된다.
        cmd.execute(task);
        //커맨드 객체화 시켰음.
    }

    public void setTitle(String title) {
        addCommand(new Title(title)); //당시 Command 객체를 기억.
    }

    public void setDate(LocalDateTime date) {
        addCommand(new Date(date));
    }

    public String getTitle() {
        return task.getTitle();
    }

    public LocalDateTime getDate() {
        return task.getDate();
    }

    public Boolean getComplete() {
        return task.getComplete();
    }

    public void addTask(String title, LocalDateTime date) {
        addCommand(new Add(title, date));
    }

    public void removeTask(CompositeTask task) {
        addCommand(new Remove(task));

    }

    public TaskReport getReport(CompositeSortType type) {
        return task.getReport(type);
    }

}
