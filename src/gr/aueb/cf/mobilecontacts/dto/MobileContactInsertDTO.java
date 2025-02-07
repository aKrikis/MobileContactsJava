package gr.aueb.cf.mobilecontacts.dto;

public class MobileContactInsertDTO {

    private String firstname;
    private String lastname;
    private String phoneNum;

    public MobileContactInsertDTO() {};

    public MobileContactInsertDTO(String firstname, String lastname, String phoneNum) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNum = phoneNum;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
