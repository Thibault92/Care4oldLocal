package isep.project.care4old.model;

public class ResultTest {

    private int idUser ;
    private String Date ;
    private Norton norton ;
    private Katz katz ;
    private Mna mna ;
    private Lawton lawton ;
    private Fragility fragility ;


    public ResultTest(int id){
        this.idUser = id;
    }

    public Norton getNorton() {
        return norton;
    }

    public void setNorton(Norton norton) {
        this.norton = norton;
    }

    public Katz getKatz() {
        return katz;
    }

    public void setKatz(Katz katz) {
        this.katz = katz;
    }

    public Mna getMna() {
        return mna;
    }

    public void setMna(Mna mna) {
        this.mna = mna;
    }

    public Lawton getLawton() {
        return lawton;
    }

    public void setLawton(Lawton lawton) {
        this.lawton = lawton;
    }

    public Fragility getFragility() {
        return fragility;
    }

    public void setFragility(Fragility fragility) {
        this.fragility = fragility;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getIdUser() {
        return idUser;
    }

    public void processing (int[] tab){

        Katz katz = new Katz(idUser);
        Norton norton = new Norton(idUser);
        Mna mna = new Mna(idUser) ;
        Fragility fragility = new Fragility(idUser);
        Lawton lawton = new Lawton(idUser);


        katz.setHygiene((double) (tab[0] / 2)) ; // Question 1
        katz.setDressing((double) (tab[1] / 2)); // Question 2
        katz.setBathroom((double) (tab[2] / 2)); // Question 3

        // Question 4
        norton.setAgility(tab[3] + 1);

        if(tab[3]==0 || tab[3]==1){
            katz.setLocomotion(0);
            mna.setMotricity(0);
            fragility.setMobility(2);
        }
        else if(tab[3]==2){
            katz.setLocomotion(0.5);
            mna.setMotricity(1);
            fragility.setMobility(1);
        }
        else if(tab[3]==3){
            katz.setLocomotion(1);
            mna.setMotricity(2);
            fragility.setMobility(0);
        }

        // Question 5
        norton.setIncontinence(tab[4] + 1);

        if(tab[4]==0){
            katz.setContinence(1);
            fragility.setContinence(0);
        }
        else if(tab[4]==1){
            katz.setContinence(0.5);
            fragility.setContinence(1);
        }
        else{
            katz.setContinence(0);
            fragility.setContinence(2);
        }


        // Question 6
        katz.setLunch(tab[5]/2);
        fragility.setFeed(2 - tab[5]);

        // Question 7
        lawton.setPhone(tab[6]);

        // Question 8
        lawton.setGrowshop(tab[7]);

        // Question 9
        lawton.setClean(tab[8]);

        // Question 10
        lawton.setLaundry(tab[9]);

        // Question 11
        lawton.setTransport(tab[10]);

        // Question 12
        lawton.setDrugs(tab[11]);

        // Question 13
        lawton.setMoney(tab[12]);

        // Question 14
        norton.setGlobal(tab[13] + 1);

        // Question 15
        norton.setPsychic(tab[14] + 1);

        if(tab[14]==0){
            mna.setNeuro(0);
        }
        else if(tab[14]==3){
            mna.setNeuro(2);
        }
        else {
            mna.setNeuro(1);
        }

        // Question 16
        norton.setMobility(tab[15] + 1);

        // Question 17
        mna.setAppetite(tab[16]);

        // Question 18
        fragility.setMood(tab[17]);

        // Question 19
        fragility.setPerception(tab[18]);

        // Question 20
        fragility.setDrugs(tab[19]);

        // Total

        norton.setTotal(norton.getAgility()+norton.getGlobal()+norton.getIncontinence()+norton.getMobility()+norton.getPsychic());
        setNorton(norton);

        lawton.setTotal(lawton.getClean()+lawton.getCook()+lawton.getDrugs()+lawton.getGrowshop()+lawton.getLaundry()+lawton.getMoney()+lawton.getPhone()+lawton.getTransport());
        setLawton(lawton);

        fragility.setTotal(fragility.getPerception()+fragility.getDrugs()+fragility.getMobility()+fragility.getContinence()+fragility.getMood()+fragility.getFeed());
        setFragility(fragility);

        mna.setTotal(mna.getAppetite() + mna.getMotricity() + mna.getNeuro()) ;
        setMna(mna);

        katz.setTotal(katz.getLunch() + katz.getContinence() + katz.getLocomotion() + katz.getDressing() + katz.getHygiene() + katz.getBathroom());
        setKatz(katz);

    }


}
