public class Main {

    public static void main(String[] args) {
        Director director = new Director();
        director.addProject("AA", new Client() {
            @Override
            public Program[] run() {
                FrontEnd<Client> frontEnd = new FrontEnd<Client>() {
                    @Override
                    void setData(Client paper) {
                        Library library = paper.getLibrary();
                        Language language = paper.getLanguage();
                    }
                };
                Programmer programmer = getProgrammer();
                return new Program[]{frontEnd.getProgram(this)};
            }
        });
        director.runProject("AA");

        director.addProject("BB", new ServerClient() {
            @Override
            public Program[] run() {
                Programmer<Paper> frontEnd = new FrontEnd() {
                    @Override
                    void setData(Paper paper) {

                    }
                };

                Programmer<Paper> backend = new BackEnd() {
                    @Override
                    void setData(Paper paper) {

                    }
                };
                setBackEndProgrammer(frontEnd);
                setBackEndProgrammer(backend);
                return new Program[]{frontEnd.getProgram(this), backend.getProgram(this)};
            }

        });
        director.runProject("BB");
    }
}
