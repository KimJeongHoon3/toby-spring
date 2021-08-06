package external.config;

public class Hello {
    String name;

    public void setName(String name) {
        this.name = name;
    }

    public String sayHello(){
        return "Hello "+name;
    }
}
