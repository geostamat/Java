import java.io.Serializable;

public abstract class Person implements Serializable{
    private String ssNum;
    private String lastName;
    private String firstName;
    
    public Person() {

    }

    public void setSsNum(String ssNum) {
        this.ssNum = ssNum;
    }

    public String getSsNum() {
        return ssNum;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }


    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}// end of class
