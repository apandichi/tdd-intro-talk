package eventrix.domain;

public class Reviewer implements User {

    private String name;

    public Reviewer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}