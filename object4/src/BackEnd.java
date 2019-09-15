public class BackEnd implements Programmer {
    private Server server;
    private Language language;

    @Override
    public Program makeProgram(Paper paper) {
        if (paper instanceof ServerClient) {
            ServerClient pa = (ServerClient) paper;
            this.server = pa.server;
            this.language = pa.backEndLanguage;
        }
        return makeBackEndProgram();
    }

    private Program makeBackEndProgram() {
        return new Program();
    }
}
