package hello.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ybarvenko on 20.10.2015.
 */
public class Auftrag implements Serializable {

    private String name;

    private int number;

    private List<Aktion> actions = new ArrayList<>();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<Aktion> getActions() {
        return actions;
    }

    public void setActions(List<Aktion> actions) {
        this.actions = actions;
    }
}
