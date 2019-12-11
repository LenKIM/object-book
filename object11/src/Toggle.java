public class Toggle implements Command {
    //얘는 무슨 일??
    @Override
    public void execute(CompositeTask task) {
        task.toggle(); //행위를 객체로 만들었음.
    }

    @Override
    public void undo(CompositeTask task) {
        task.toggle();
    }
}
