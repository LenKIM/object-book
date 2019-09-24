public abstract class BackEnd<T extends Paper> extends Programmer<T> {
    private Server server;
    private Language language;

    @Override
    protected Program makeProgram(){
        return new Program();
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }
}
