package isep.project.care4old.model;

import java.util.Date;

public class Lawton {

    private int id ;
    private Date date ;
    private int phone ;
    private int growshop ;
    private int cook ;
    private int clean ;
    private int laundry ;
    private int transport ;
    private int drugs ;
    private int money ;
    private int total ;

    public Lawton(int id){
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

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getGrowshop() {
        return growshop;
    }

    public void setGrowshop(int growshop) {
        this.growshop = growshop;
    }

    public int getCook() {
        return cook;
    }

    public void setCook(int cook) {
        this.cook = cook;
    }

    public int getClean() {
        return clean;
    }

    public void setClean(int clean) {
        this.clean = clean;
    }

    public int getLaundry() {
        return laundry;
    }

    public void setLaundry(int laundry) {
        this.laundry = laundry;
    }

    public int getTransport() {
        return transport;
    }

    public void setTransport(int transport) {
        this.transport = transport;
    }

    public int getDrugs() {
        return drugs;
    }

    public void setDrugs(int drugs) {
        this.drugs = drugs;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
