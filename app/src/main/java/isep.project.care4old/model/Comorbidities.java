package isep.project.care4old.model;

import java.util.Date;

public class Comorbidities {

    private int id ;
    private Date date ;
    private String pathology ;

    public Comorbidities(int id) {
        this.id = id;
    }

    public Comorbidities(int id,String pathology) {
        this.id = id;
        this.pathology=pathology;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPathology() {
        return pathology;
    }

    public void setPathology(String pathology) {
        this.pathology = pathology;
    }
}
