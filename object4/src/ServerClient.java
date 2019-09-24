public abstract class ServerClient implements Paper {
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


    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Language getBackEndLanguage() {
        return backEndLanguage;
    }

    public void setBackEndLanguage(Language backEndLanguage) {
        this.backEndLanguage = backEndLanguage;
    }

    public Language getFrontEndLanguage() {
        return frontEndLanguage;
    }

    public void setFrontEndLanguage(Language frontEndLanguage) {
        this.frontEndLanguage = frontEndLanguage;
    }

    public Programmer getBackEndProgrammer() {
        return backEndProgrammer;
    }

    public Programmer getFrontEndProgrammer() {
        return frontEndProgrammer;
    }
}
