public class JsonVisitor implements Visitor {
    @Override
    public void drawTask(CompositeTask task, int depth) {
        String padding = getPadding(depth);
        System.out.println(padding + "{");
        System.out.println(padding + "  title: \"" + task.getTitle() + "\",");
        System.out.println(padding + "  date: \"" + task.getDate() + "\",");
        System.out.println(padding + "  isComplete: \"" + task.isComplete() + ",");
        System.out.println(padding + "  sub: [ ");
    }

    @Override
    public void end(int depth) {
        String padding = getPadding(depth);
        System.out.println(padding+ "  ]");
        System.out.println(padding+ "};");
    }

    private String getPadding(int depth) {
        String padding = "";
        for (int i = 0; i < depth; i++) {
            padding += "  ";
        }
        return padding;
    }
}
