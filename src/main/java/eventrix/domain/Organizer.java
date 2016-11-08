package eventrix.domain;

public class Organizer implements User {

    private String name;

    public Organizer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
