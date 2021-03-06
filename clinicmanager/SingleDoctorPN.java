import javax.swing.JOptionPane;

/**
 *
 * @author Parasite
 */
public class SingleDoctorPN extends javax.swing.JPanel {
    
    private Doctor doctor;
    private Clinic clinic;
    private DoctorScreen doctorscreen;

    /** Creates new form SingleDoctorPN */
    public SingleDoctorPN() {
        initComponents();
    }
    
    public SingleDoctorPN(DoctorScreen doctorscreen, Clinic clinic,Doctor doctor) {
        this.doctor = doctor;
        this.clinic = clinic;
        this.doctorscreen = doctorscreen;
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {//GEN-BEGIN:initComponents

        nameL = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        specialtyL = new javax.swing.JLabel();
        removeB = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setLayout(new java.awt.GridLayout(1, 2));

        nameL.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        nameL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        nameL.setText(doctor.toStringWithoutSpecialty());
        add(nameL);

        jPanel1.setLayout(new java.awt.GridLayout(1, 2));

        specialtyL.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        specialtyL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        specialtyL.setText(doctor.getSpecialty());
        jPanel1.add(specialtyL);

        removeB.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        removeB.setText("Remove");
        removeB.setToolTipText("Removes this doctor from the clinic");
        removeB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeBActionPerformed(evt);
            }
        });
        jPanel1.add(removeB);

        add(jPanel1);
    }//GEN-END:initComponents

    private void removeBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeBActionPerformed
        if(clinic.checkDoctorStatus(doctor)){
        JOptionPane.showMessageDialog(null, "Cannot remove doctor that is assigned to patients!","Error", JOptionPane.ERROR_MESSAGE);
        }else{
            clinic.removeDoctor(doctor);
            JOptionPane.showMessageDialog(null, "Doctor removed successfully!","Doctor removed", JOptionPane.INFORMATION_MESSAGE);
            new DoctorScreen(clinic);
            this.doctorscreen.dispose();
        }
    }//GEN-LAST:event_removeBActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel nameL;
    private javax.swing.JButton removeB;
    private javax.swing.JLabel specialtyL;
    // End of variables declaration//GEN-END:variables

}
