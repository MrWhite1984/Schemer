package classes;

import java.util.List;

public class Project {
    String projectName;
    List<String> tasks;
    String description;
    List<String> ideas;
    String script;
    public  Project(String projectName, List<String> tasks, String description, List<String> ideas, String script)
    {
        this.projectName = projectName;
        this.tasks = tasks;
        this.description = description;
        this.ideas = ideas;
        this.script = script;
    }
}
