public abstract class Programmer<T extends Paper> {
    public Program getProgram(T paper) {
        setData(paper);
        return makeProgram();
    }

    abstract void setData(T paper);
    abstract protected Program makeProgram();
}