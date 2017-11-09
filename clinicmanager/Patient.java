import java.io.Serializable;

import java.util.Date;

public class Patient extends Person implements Serializable{
    
    private String diagnosis;
    private Doctor assignedDoc;
    private Bed bed;
    
    public Patient() {
        super();

    }
    
    public Patient(String ssn, String firstName, String lastName, String diagnosis, Bed bed, Doctor assignedDoc){
        this.setSsNum(ssn);
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.diagnosis = diagnosis;
        this.bed = bed;
        this.assignedDoc = assignedDoc;
    }

    public void setAssignedDoc(Doctor assignedDoc) {
        this.assignedDoc = assignedDoc;
    }

    public Doctor getAssignedDoc() {
        return assignedDoc;
    }


    public void setBed(Bed bed) {
        this.bed = bed;
    }

    public Bed getBed() {
        return bed;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getDiagnosis() {
        return diagnosis;
    }


    @Override
    public String toString() {
        return super.toString();
    }

}// end of class
