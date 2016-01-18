package isep.project.care4old.model;

import java.util.Date;

public class MedicalCheck {

    private int idMedcheck;
    private int idPatient;
    private Date dateConsultation;
    private int height;
    private int weight;
    private int mna;
    private int albuminemie;
    private int crp;
    private int vitamine;
    private String diagnostic;

    public int getIdMedcheck() {
        return idMedcheck;
    }

    public void setIdMedcheck(int idMedcheck) {
        this.idMedcheck = idMedcheck;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idPatient) {
        this.idPatient = idPatient;
    }

    public Date getDateConsultation() {
        return dateConsultation;
    }

    public void setDateConsultation(Date dateConsultation) {
        this.dateConsultation = dateConsultation;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getMna() {
        return mna;
    }

    public void setMna(int mna) {
        this.mna = mna;
    }

    public int getAlbuminemie() {
        return albuminemie;
    }

    public void setAlbuminemie(int albuminemie) {
        this.albuminemie = albuminemie;
    }

    public int getCrp() {
        return crp;
    }

    public void setCrp(int crp) {
        this.crp = crp;
    }

    public int getVitamine() {
        return vitamine;
    }

    public void setVitamine(int vitamine) {
        this.vitamine = vitamine;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }
}
