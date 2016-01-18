package isep.project.care4old.model;

import java.util.Date;

public class Katz {

    private  int id ;
    private Date date ;
    private double hygiene ;
    private double dressing ;
    private double bathroom ;
    private double locomotion ;
    private double continence ;
    private double lunch ;
    private double total ;

    public Katz(int id){
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

    public double getHygiene() {
        return hygiene;
    }

    public void setHygiene(double hygiene) {
        this.hygiene = hygiene;
    }

    public double getDressing() {
        return dressing;
    }

    public void setDressing(double dressing) {
        this.dressing = dressing;
    }

    public double getBathroom() {
        return bathroom;
    }

    public void setBathroom(double bathroom) {
        this.bathroom = bathroom;
    }

    public double getLocomotion() {
        return locomotion;
    }

    public void setLocomotion(double locomotion) {
        this.locomotion = locomotion;
    }

    public double getContinence() {
        return continence;
    }

    public void setContinence(double continence) {
        this.continence = continence;
    }

    public double getLunch() {
        return lunch;
    }

    public void setLunch(double lunch) {
        this.lunch = lunch;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

}
