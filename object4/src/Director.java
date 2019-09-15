import java.util.HashMap;
import java.util.Map;

public class Director {

    private Map<String, Paper> projects = new HashMap<>();

    public void addProject(String name, Paper paper) {
        projects.put(name, paper);
    }

    public void runProject(String name) {
        if (!projects.containsKey(name)) throw new RuntimeException("no project");
        Paper paper = projects.get(name);
        if (paper instanceof ServerClient) {
            ServerClient project = (ServerClient) paper;
            Programmer frontEnd = new FrontEnd(), backEnd = new BackEnd();
            project.setFrontEndProgrammer(frontEnd);
            project.setBackEndProgrammer(backEnd);
            Program client = frontEnd.makeProgram(project);
            Program server = backEnd.makeProgram(project);
            deploy(name, client, server);
        } else if (paper instanceof Client) {
            Client project = (Client) paper;
            Programmer frontEnd = new FrontEnd();
            project.setProgrammer(frontEnd);
            deploy(name, frontEnd.makeProgram(project));
        }
    }

    private void deploy(String projectName, Program... programs) {
    }
}
