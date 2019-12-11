public class Title implements Command {

    private final String title;
    private String oldTitle;

    public Title(String title) {
        this.title = title;
    }

    @Override
    public void execute(CompositeTask task) {
        oldTitle = task.getTitle();
        task.setTitle(title);
    }

    @Override
    public void undo(CompositeTask task) {
        task.setTitle(oldTitle);
    }
}
