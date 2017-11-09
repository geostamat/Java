import javax.swing.*;
import java.io.IOException;

public class ClinicManagerLoadMe {
    static Clinic clinic = new Clinic();
    public ClinicManagerLoadMe() {
            
    }
    public static void main(String[] args) {
        
        
        try {
            clinic = clinic.loadClinic();
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "File not found!", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, e.toString(), "Error loading data!", JOptionPane.ERROR_MESSAGE);
        }
        new ClinicMainScreen(clinic); 
        
        // clinic.setBedMap(clinic.loadBeds());
        // clinic.setDocMap(clinic.loadDoctors());
        //clinic.setPatientMap(clinic.loadPatiens());
        // new ClinicMainScreen(clinic);
        
    }// end of main



}
