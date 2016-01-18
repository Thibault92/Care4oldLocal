package isep.project.care4old.model;

import java.util.Date;

public class Hospitalization {

    private int idHosp;
    private int idPatient;
    private Date hospStart;
    private Date hospEnd;
    private String motif;

    public int getIdHosp() {
        return idHosp;
    }

    public void setIdHosp(int idHosp) {
        this.idHosp = idHosp;
    }

    public int getIdPatient() {
        return idPatient;
    }

    public void setIdPatient(int idUser) {
        this.idPatient = idUser;
    }

    public Date getHospStart() {
        return hospStart;
    }

    public void setHospStart(Date hospStart) {
        this.hospStart = hospStart;
    }

    public Date getHospEnd() {
        return hospEnd;
    }

    public void setHospEnd(Date hospEnd) {
        this.hospEnd = hospEnd;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

}
