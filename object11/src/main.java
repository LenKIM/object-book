import java.time.LocalDateTime;
import java.util.List;

public class main {

    public static void main(String[] args) {

        CommandTask root = new CommandTask("Root", LocalDateTime.now());
        root.addTask("sub1", LocalDateTime.now());
        root.addTask("sub2", LocalDateTime.now());

        Renderer renderer1 = new Renderer(() -> new JsonVisitor());
        renderer1.render(root.getReport(CompositeSortType.TITLE_ASC));

        root.save("key01");
        renderer1.render(root.getReport(CompositeSortType.TITLE_ASC));

        root.load("key01");
        renderer1.render(root.getReport(CompositeSortType.TITLE_ASC));

        root.redo();
        renderer1.render(root.getReport(CompositeSortType.TITLE_ASC));

//        TaskReport report = root.getReport(CompositeSortType.TITLE_ASC);
//        List<TaskReport> list = report.getList();
//        CompositeTask sub1 = list.get(0).getTask();
//        CompositeTask sub2 = list.get(1).getTask();
//
//        sub1.addTask("sub1_1", LocalDateTime.now());
//        sub1.addTask("sub1_2", LocalDateTime.now());
//
//        sub2.addTask("sub2_1", LocalDateTime.now());
//        sub2.addTask("sub2_2", LocalDateTime.now());

//
//        Renderer renderer2 = new Renderer(() -> new JsonVisitor());
//        renderer2.render(root.getReport(CompositeSortType.TITLE_ASC));
    }
}
