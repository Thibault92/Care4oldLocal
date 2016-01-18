package isep.project.care4old.model;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

public class User {

    private int idUser;
    private int idDoctorLinked; //17
    private String identifiant; //0
    private String password; //1
    private String familyName; //2
    private String firstName; //3
    private Date birthday; //4
    private String address; //5
    private int zip; //6
    private String city; //7
    private String homePhone; //8
    private String mobilePhone;  //9
    private String emergencyPhone; //10
    private String gender; //11
    private String status; //12
    private String residency; //13
    private String financialHelp; //14
    private String homeHelp; //15
    private String userType; //16

    public long getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdDoctorLinked() {
        return idDoctorLinked;
    }

    public void setIdDoctorLinked(int idDoctorLinked) {
        this.idDoctorLinked = idDoctorLinked;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getIdentifiant() {
        return identifiant;
    }

    public void setIdentifiant(String identifiant) {
        this.identifiant = identifiant;
    }

    public String getPassword() {
        return encryptShaPassword(encryptMd5Password(password));
    }

    public void setPassword(String password) {
        this.password = encryptShaPassword(encryptMd5Password(password));
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date date) {
        this.birthday = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmergencyPhone() {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone) {
        this.emergencyPhone = emergencyPhone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResidency() {
        return residency;
    }
    public void setResidency(String residency) {
        this.residency = residency;

    }
    public String getFinancialHelp() {
        return financialHelp;
    }

    public void setFinancialHelp(String financialHelp) {
        this.financialHelp = financialHelp;
    }

    public String getHomeHelp() {
        return homeHelp;
    }

    public void setHomeHelp(String homeHelp) {
        this.homeHelp = homeHelp;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public void setAttributes(String[] myUser) {
        setIdentifiant(myUser[0]);
        setPassword(myUser[1]);
        setFamilyName(myUser[2]);
        setFirstName(myUser[3]);
        setBirthday(convertStringToDate(myUser[4]));
        setAddress(myUser[5]);
        setZip(Integer.valueOf(myUser[6]));
        setCity(myUser[7]);
        setHomePhone(myUser[8]);
        setMobilePhone(myUser[9]);
        setEmergencyPhone(myUser[10]);
        setGender(myUser[11]);
        setStatus(myUser[12]);
        setResidency(myUser[13]);
        setFinancialHelp(myUser[14]);
        setHomeHelp(myUser[15]);
        setUserType(myUser[16]);
        if(myUser[16].equals("Un médecin"))
            setIdDoctorLinked(-1);
        else
            setIdDoctorLinked(Integer.parseInt(myUser[17]));
    }

    public Date convertStringToDate (String stringDate) {
        DateFormat formattedBirthday = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
        Date birthdayConverted = null;
        try {
            birthdayConverted = formattedBirthday.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return birthdayConverted;
    }

    private static String encryptShaPassword(String password) {
        String shaPassword = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("SHA-1");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            shaPassword = byteToHex(crypt.digest());
        }  catch(NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return shaPassword;
    }

    private static String encryptMd5Password(String password) {
        String md5Password = "";
        try {
            MessageDigest crypt = MessageDigest.getInstance("MD5");
            crypt.reset();
            crypt.update(password.getBytes("UTF-8"));
            md5Password = byteToHex(crypt.digest());
        }  catch(NoSuchAlgorithmException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return md5Password;
    }

    private static String byteToHex(final byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
