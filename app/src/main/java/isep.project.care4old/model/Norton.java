package isep.project.care4old.model;

import java.util.Date;

public class Norton {

    private int id ;
    private Date date ;
    private int global ;
    private int agility ;
    private int psychic ;
    private int incontinence ;
    private int mobility ;
    private int total ;

    public Norton(int id){
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

    public int getGlobal() {
        return global;
    }

    public void setGlobal(int global) {
        this.global = global;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getPsychic() {
        return psychic;
    }

    public void setPsychic(int psychic) {
        this.psychic = psychic;
    }

    public int getIncontinence() {
        return incontinence;
    }

    public void setIncontinence(int incontinence) {
        this.incontinence = incontinence;
    }

    public int getMobility() {
        return mobility;
    }

    public void setMobility(int mobility) {
        this.mobility = mobility;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }



}