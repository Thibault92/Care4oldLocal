package isep.project.care4old.model;

import java.util.Date;

public class Mna {

    private int id ;
    private Date date ;
    private int appetite ;
    private int loose_weight ;
    private int motricity ;
    private int acute ;
    private int neuro ;
    private int bmi ;
    private int total ;

    public Mna(int id){
        this.id = id;
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

    public int getAppetite() {
        return appetite;
    }

    public void setAppetite(int appetite) {
        this.appetite = appetite;
    }

    public int getLoose_weight() {
        return loose_weight;
    }

    public void setLoose_weight(int loose_weight) {
        this.loose_weight = loose_weight;
    }

    public int getMotricity() {
        return motricity;
    }

    public void setMotricity(int motricity) {
        this.motricity = motricity;
    }

    public int getAcute() {
        return acute;
    }

    public void setAcute(int acute) {
        this.acute = acute;
    }

    public int getNeuro() {
        return neuro;
    }

    public void setNeuro(int neuro) {
        this.neuro = neuro;
    }

    public int getBmi() {
        return bmi;
    }

    public void setBmi(int bmi) {
        this.bmi = bmi;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
