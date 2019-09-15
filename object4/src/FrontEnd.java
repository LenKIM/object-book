public class FrontEnd implements Programmer {
    private Language language;
    private Library library;

    @Override
    public Program makeProgram(Paper paper) {
        if (paper instanceof Client) {
            Client pb = (Client) paper;
            language = pb.language;
            library = pb.library;
        }
        return makeFrontEndProgram();
    }

    private Program makeFrontEndProgram() {
        return new Program();
    }
}
