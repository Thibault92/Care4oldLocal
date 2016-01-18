package isep.project.care4old.model;

import java.util.Date;

public class Fragility {
    private int id ;
    private Date date ;
    private int home ;
    private int drugs ;
    private int mood ;
    private int perception ;
    private int fall ;
    private int responsability ;
    private int illness ;
    private int mobility ;
    private int continence ;
    private int feed ;
    private int cognitive ;
    private int total ;

    public Fragility(int id) {
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

    public int getHome() {
        return home;
    }

    public void setHome(int home) {
        this.home = home;
    }

    public int getDrugs() {
        return drugs;
    }

    public void setDrugs(int drugs) {
        this.drugs = drugs;
    }

    public int getMood() {
        return mood;
    }

    public void setMood(int mood) {
        this.mood = mood;
    }

    public int getPerception() {
        return perception;
    }

    public void setPerception(int perception) {
        this.perception = perception;
    }

    public int getFall() {
        return fall;
    }

    public void setFall(int fall) {
        this.fall = fall;
    }

    public int getResponsability() {
        return responsability;
    }

    public void setResponsability(int responsability) {
        this.responsability = responsability;
    }

    public int getIllness() {
        return illness;
    }

    public void setIllness(int illness) {
        this.illness = illness;
    }

    public int getMobility() {
        return mobility;
    }

    public void setMobility(int mobility) {
        this.mobility = mobility;
    }

    public int getContinence() {
        return continence;
    }

    public void setContinence(int continence) {
        this.continence = continence;
    }

    public int getFeed() {
        return feed;
    }

    public void setFeed(int feed) {
        this.feed = feed;
    }

    public int getCognitive() {
        return cognitive;
    }

    public void setCognitive(int cognitive) {
        this.cognitive = cognitive;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
