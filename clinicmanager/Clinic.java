import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Clinic implements Serializable{
    
    private HashMap<String, Bed> bedMap;
    private HashMap<String, Doctor> docMap; 
    private HashMap<String, Patient> patientMap; 
    
    
    public Clinic() {
        bedMap = new HashMap<String, Bed>();
        docMap = new HashMap<String, Doctor>();
        patientMap = new HashMap<String, Patient>();
    }


    public void setBedMap(HashMap<String, Bed> bedMap) {
        this.bedMap = bedMap;
    }

    public HashMap<String, Bed> getBedMap() {
        return bedMap;
    }

    public void setDocMap(HashMap<String, Doctor> docMap) {
        this.docMap = docMap;
    }

    public HashMap<String, Doctor> getDocMap() {
        return docMap;
    }

    public void setPatientMap(HashMap<String, Patient> patientMap) {
        this.patientMap = patientMap;
    }

    public HashMap<String, Patient> getPatientMap() {
        return patientMap;
    }
    
    public void addDoctor(Doctor d){
        docMap.put(d.getSsNum(),d);
        saveClinic();
    }
    
    public void removeDoctor(Doctor d){
        docMap.remove(d.getSsNum());
        saveClinic();
    }
    
    public void addPatient(Patient p){
        patientMap.put(p.getSsNum(),p);
        bedMap.get(p.getBed().toString2()).setBStatus(BedStatus.OCCUPIED);
        JOptionPane.showMessageDialog(null, "Patient added successfully!","Patient added", JOptionPane.INFORMATION_MESSAGE);
        saveClinic();
    }
    
    public void removePatient(Patient p){
        bedMap.get(p.getBed().toString2()).setBStatus(BedStatus.AVAILABLE);
        patientMap.remove(p.getSsNum());
        saveClinic();
    }
    
    public void addBed(Bed b){
        bedMap.put(b.toString2(), b);
        saveClinic();
    }
    
    public void removeBed(Bed b){
        bedMap.remove(b.toString2());
        saveClinic();
        
    } 
    
    public void saveClinic() {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("clinic.sav"));
            out.writeObject(this);
            out.close();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error has occured: " + e.toString(), "Error saving Clinic!", JOptionPane.ERROR_MESSAGE);
        }

    }
    
    public Clinic loadClinic() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("clinic.sav"));
        Clinic clinic = (Clinic) in.readObject();
        in.close();
        return clinic;
    }
    
    // checkes if a doctor is assigned to a patient or not
    public Boolean checkDoctorStatus(Doctor doctor){
        int counter = 0;
        for(String key: patientMap.keySet()){
            if(patientMap.get(key).getAssignedDoc()==doctor){
                counter++;
            }
        }
        if(counter>0){
            return true;
        }else{
            return false;
        }
    }
    
    // loads the hashmap of doctors to an ArrayList to be used to populate the dropdown menus
    public ArrayList doctorList(){
      ArrayList<Doctor> doctorList = new ArrayList<Doctor>();
      for(String key: docMap.keySet()){
          doctorList.add((Doctor)docMap.get(key));
      }
      return doctorList;
    }
    
    // loads the hashmap of available only beds to an ArrayList to be used to populate the dropdown menus
    public ArrayList<Bed> availableBedList(){
        ArrayList<Bed> availableBedList = new ArrayList<Bed>();
        for(String key: bedMap.keySet()){
            if(bedMap.get(key).getBStatus().equals(BedStatus.AVAILABLE)){
                availableBedList.add(bedMap.get(key));
            }
            
        }
        return availableBedList;
    }
    
    // checks if a bed already exists in the HashMap of beds
    public Boolean checkBed(String room, String bed){
        return bedMap.containsKey(room+"_"+bed);

    }
    
    // returns a count of the beds that are available
    public int availableBeds(){
        int counter = 0;
        for(String key : bedMap.keySet()){
            if(bedMap.get(key).getBStatus().equals(BedStatus.AVAILABLE)){
                counter++;
            }
        }
        return counter;
    }

}// end of class
