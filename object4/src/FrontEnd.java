public abstract class FrontEnd<T extends Paper> extends Programmer<T> {
    private Language language;
    private Library library;

    @Override
    protected Program makeProgram(){
        return new Program();
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }
}
