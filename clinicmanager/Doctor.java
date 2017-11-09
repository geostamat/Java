import java.io.Serializable;

public class Doctor extends Person implements Serializable{
    
    private String specialty;

    
    public Doctor() {
        super();
        specialty = "";
    }
    
    public Doctor(String sSNum, String lastName, String firstName, String specialty){
        super();
        this.setSsNum(sSNum);
        this.setLastName(lastName);
        this.setFirstName(firstName);
        this.specialty = specialty;
        
    }
    

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getSpecialty() {
        return specialty;
    }




    @Override
    public String toString() {
        return "Dr " + super.toString()+", " + specialty;
    }
    
    public String toStringWithoutSpecialty() {
        return "Dr " + super.toString();
    }
}// end of class
