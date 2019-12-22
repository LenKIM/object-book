public class ConsoleVisitor implements Visitor {
    @Override
    public void drawTask(CompositeTask task, int depth) {
        String padding = "";
        for (int i = 0; i < depth; i++) {
            padding += "-";
        }
        System.out.println(padding + (task.getIsComplete() ? "[v] " : "[ ] "
                + task.getTitle() + "(" + task.getDate() + ")"));
    }

    @Override
    public void end(int depth, boolean isEnd) {
    }
}
