public abstract class Client implements Paper {
    private Library library = new Library("vueJS");
    private Language language = new Language("kotlinJS");
    private Programmer programmer;

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Programmer getProgrammer() {
        return programmer;
    }
}

