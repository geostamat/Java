import javax.swing.JOptionPane;

/**
 *
 * @author Parasite
 */
public class SingleBedPN extends javax.swing.JPanel {
    
    private Clinic clinic;
    private Bed bed;
    private BedScreen bedscreen;

    /** Creates new form SingleBedPN */
    public SingleBedPN(BedScreen bedscreen, Clinic clinic, Bed bed) {
        this.clinic = clinic;
        this.bed = bed;
        this.bedscreen = bedscreen;
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    private void initComponents() {//GEN-BEGIN:initComponents

        infoPN = new javax.swing.JPanel();
        infL1 = new javax.swing.JLabel();
        roomNumL = new javax.swing.JLabel();
        infoL2 = new javax.swing.JLabel();
        bedNumL = new javax.swing.JLabel();
        buttonPN = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        statusL = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        setLayout(new java.awt.GridLayout(1, 2, 5, 5));

        infoPN.setLayout(new java.awt.GridLayout(2, 2));

        infL1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        infL1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infL1.setText("Room");
        infoPN.add(infL1);

        roomNumL.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        roomNumL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        roomNumL.setText(bed.getRoom());
        infoPN.add(roomNumL);

        infoL2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        infoL2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        infoL2.setText("Bed Number");
        infoPN.add(infoL2);

        bedNumL.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        bedNumL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bedNumL.setText(bed.getBedNumber());
        bedNumL.setToolTipText(bed.getBedNumber());
        infoPN.add(bedNumL);

        add(infoPN);

        buttonPN.setLayout(new java.awt.GridLayout(2, 1, 5, 5));

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jButton1.setText("Remove");
        jButton1.setToolTipText("Removes this bed from the clinic");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        buttonPN.add(jButton1);

        statusL.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        statusL.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusL.setText(bed.getBStatus().toString());
        buttonPN.add(statusL);

        add(buttonPN);
    }//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(bed.getBStatus().equals(BedStatus.OCCUPIED)){
            JOptionPane.showMessageDialog(null, "Cannot remove an occupied bed!", "Error", JOptionPane.ERROR_MESSAGE);
        }else{
            this.clinic.removeBed(bed);
            this.bedscreen.dispose();
            new BedScreen(this.clinic);
        }
            
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bedNumL;
    private javax.swing.JPanel buttonPN;
    private javax.swing.JLabel infL1;
    private javax.swing.JLabel infoL2;
    private javax.swing.JPanel infoPN;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel roomNumL;
    private javax.swing.JLabel statusL;
    // End of variables declaration//GEN-END:variables

}