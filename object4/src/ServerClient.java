public class ServerClient implements Paper {
    Server server = new Server("test");
    Language backEndLanguage = new Language("java");
    Language frontEndLanguage = new Language("kotlinJS");
    private Programmer backEndProgrammer;
    private Programmer frontEndProgrammer;

    public void setBackEndProgrammer(Programmer programmer) {
        backEndProgrammer = programmer;
    }

    public void setFrontEndProgrammer(Programmer programmer) {
        frontEndProgrammer = programmer;
    }
}
