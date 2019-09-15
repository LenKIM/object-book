public class Client implements Paper {
    Library library = new Library("vueJS");
    Language language = new Language("kotlinsJS");
    Programmer programmer;

    public void setProgrammer(Programmer programmer) {
        this.programmer = programmer;
    }
}
