package hello.model;

import java.io.Serializable;

/**
 * Created by ybarvenko on 20.10.2015.
 */
public class Aktion implements Serializable {

    private String name;

    public void execute(Auftrag auftrag){

        System.out.println("Auftrag Nr.: "+auftrag.getNumber()+" Aktioname: "+name);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
