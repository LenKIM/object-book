public class JsonVisitor implements Visitor {

    private String result = "";

    @Override
    public void drawTask(CompositeTask task, int depth) {
        result += "{";
        result += "  title: \"" + task.getTitle() + "\",";
        result += "  date: \"" + task.getDate() + "\",";
        result += "  isComplete: \"" + task.getIsComplete() + ",";
        result += "  sub: [ ";
    }

    @Override
    public void end(int depth, boolean isEnd) {
        result += "  ]";
        result += "}";

        if (!isEnd) {
            result += ",";
        }
    }

    public String getJson() {
        return result;
    }
}
