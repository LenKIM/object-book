import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandTask {

    private final CompositeTask task;
    private List<Command> commands = new ArrayList<>();
    private final Map<String, String> saved = new HashMap<>();

    public void save(String key) {
        JsonVisitor visitor = new JsonVisitor();
        Renderer renderer1 = new Renderer(() -> visitor);
        renderer1.render(task.getReport(CompositeSortType.TITLE_ASC));
        saved.put(key, visitor.getJson());
    }

    public void load(String key) {
        if (!this.saved.containsKey(key)) {
            throw new RuntimeException("You have to json before load.");
        }
        String json = this.saved.get(key);
        if (json.trim().charAt(0) != '{') {
            throw new RuntimeException("The format of the JSON is not allowed for loading to CommandTask.");
        }

        int cursor = 0;
        this.task.removeAll();
        this.task.setTitle(this.getValue(json, cursor = this.next("\"title\": \"", json, cursor)));
        this.task.setDate(LocalDateTime.parse(this.getValue(json, cursor = this.next("\"date\": \"", json, cursor))));
        this.load(this.task, json, this.next("\"sub\": [", json, cursor));
    }

    private int load(CompositeTask parent, String json, int cursor) {
        CompositeTask child = null;
        int length = json.length();
        while (cursor < length) {
            char c = json.charAt(cursor);
            if (c == '{') {
                child = parent.addTask(
                        this.getValue(json, cursor = this.next("\"title\": \"", json, cursor)),
                        LocalDateTime.parse(this.getValue(json, cursor = this.next("\"date\": \"", json, cursor)))
                );
            }
            if (c == '[' && child != null) {
                cursor = this.load(child, json, cursor);
            }
            if (c == '}') {
                return cursor;
            }
            cursor++;
        }
        return cursor;
    }
    private int next(String target, String json, int cursor) {
        int startIndex = json.indexOf(target, cursor);
        if (startIndex == -1) {
            return cursor;
        }
        return startIndex + target.length();
    }

    private String getValue(String json, int cursor) {
        return json.substring(cursor, json.indexOf("\"", cursor + 1));
    }



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
