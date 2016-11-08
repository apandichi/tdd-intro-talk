package eventrix.domain;

public class Speaker implements User {

    private String name;

    public Speaker(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
