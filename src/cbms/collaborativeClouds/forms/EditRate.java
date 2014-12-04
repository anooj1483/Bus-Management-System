/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cbms.collaborativeClouds.forms;

import cbms.collaborativeClouds.dbworkers.DatabaseConnector;
import cbms.collaborativeClouds.workers.ImageLocator;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author CollaborativeClouds Software Solutions
 */
public class EditRate extends javax.swing.JFrame {

    /**
     * Creates new form EditRate
     */
    public EditRate() throws ClassNotFoundException, SQLException {
        initComponents();
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        ImageLocator mImage=new ImageLocator();
        JLabel background = new JLabel(new ImageIcon(mImage.bus_schedule));
        this.add(background);
        background.setLayout(new FlowLayout());

        Toolkit tk = Toolkit.getDefaultToolkit();
        int xSize = ((int) tk.getScreenSize().getWidth());
        int ySize = ((int) tk.getScreenSize().getHeight());
        this.setSize(xSize, ySize);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        label_route_no.setLocation((xSize/2)-200, 100);
        route_no.setLocation((xSize/2)-100, 100);
        route_no.setSize(300, 30);
        
        label_bus_stop.setLocation((xSize/2)-200, 150);
        bus_stop.setLocation((xSize/2)-100, 150);
        bus_stop.setSize(300, 30);
        
        label_bus_time.setLocation((xSize/2)-200, 200);
        bus_time.setLocation((xSize/2)-100, 200);
        bus_time.setSize(300, 30);
        
        label_bus_rate.setLocation((xSize/2)-200, 250);
        bus_rate.setLocation((xSize/2)-100, 250);
        bus_rate.setSize(300, 30);
        
        update.setLocation((xSize/2)-100, 300);
        update.setSize(100, 30);
        
        back.setLocation((xSize/2)+100, 300);
        back.setSize(100,30);
        
        label_update.setLocation(xSize/2, 50);
        load_route();
    }

    public void load_route() throws ClassNotFoundException, SQLException{
        DatabaseConnector mDBCOnnect = new DatabaseConnector();
        String sql_select="select distinct route_no from tbl_bus";
        ResultSet mRoutes=mDBCOnnect.select_execute(sql_select);
        int status=0;
        route_no.removeAllItems();
        while (mRoutes.next()) {
           status=1;
           route_no.addItem(mRoutes.getString("route_no"));
        }
        if(status==1){
            load_busStop();
        }
    }
    
    public void load_busStop() throws ClassNotFoundException, SQLException{
        DatabaseConnector mDBCOnnect = new DatabaseConnector();
        String route=route_no.getSelectedItem().toString();
        String sql_select="select bus_stop from tbl_bus where route_no='"+route+"'";
        ResultSet mRoutes=mDBCOnnect.select_execute(sql_select);
        int status=0;
        bus_stop.removeAllItems();
        while (mRoutes.next()) {
           status=1;
           bus_stop.addItem(mRoutes.getString("bus_stop"));
        }
        if(status==1){
            load_rate();
        }
    }
     public void load_rate() throws ClassNotFoundException, SQLException{
        DatabaseConnector mDBCOnnect = new DatabaseConnector();
        String route=route_no.getSelectedItem().toString();
        String stop=bus_stop.getSelectedItem().toString();
        String sql_select="select * from tbl_bus where route_no='"+route+"' and bus_stop='"+stop+"'";
        ResultSet mRoutes=mDBCOnnect.select_execute(sql_select);
        int status=0;
        if(mRoutes.next()){
            bus_time.setText(mRoutes.getString("bus_time"));
            bus_rate.setText(""+mRoutes.getInt("rate"));
        }
        
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        label_route_no = new javax.swing.JLabel();
        route_no = new javax.swing.JComboBox();
        label_bus_stop = new javax.swing.JLabel();
        label_bus_time = new javax.swing.JLabel();
        label_bus_rate = new javax.swing.JLabel();
        bus_stop = new javax.swing.JComboBox();
        bus_time = new javax.swing.JTextField();
        bus_rate = new javax.swing.JTextField();
        update = new javax.swing.JButton();
        back = new javax.swing.JButton();
        label_update = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        label_route_no.setForeground(new java.awt.Color(255, 255, 255));
        label_route_no.setText("Route No:");

        route_no.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                route_noItemStateChanged(evt);
            }
        });

        label_bus_stop.setForeground(new java.awt.Color(255, 255, 255));
        label_bus_stop.setText("Bus Stop:");

        label_bus_time.setForeground(new java.awt.Color(255, 255, 255));
        label_bus_time.setText("Time:");

        label_bus_rate.setForeground(new java.awt.Color(255, 255, 255));
        label_bus_rate.setText("Rate:");

        bus_stop.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                bus_stopItemStateChanged(evt);
            }
        });

        update.setBackground(new java.awt.Color(0, 0, 0));
        update.setForeground(new java.awt.Color(255, 255, 255));
        update.setText("Update");
        update.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateActionPerformed(evt);
            }
        });

        back.setBackground(new java.awt.Color(0, 0, 0));
        back.setForeground(new java.awt.Color(255, 255, 255));
        back.setText("Back");
        back.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backActionPerformed(evt);
            }
        });

        label_update.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        label_update.setForeground(new java.awt.Color(255, 255, 255));
        label_update.setText("UPDATE RATE");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(label_route_no)
                    .addComponent(label_bus_stop)
                    .addComponent(label_bus_time)
                    .addComponent(label_bus_rate))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(update, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
                        .addComponent(back, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(route_no, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bus_stop, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(bus_time)
                    .addComponent(bus_rate))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(101, Short.MAX_VALUE)
                .addComponent(label_update)
                .addGap(174, 174, 174))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(label_update, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_route_no)
                    .addComponent(route_no, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_bus_stop)
                    .addComponent(bus_stop, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_bus_time)
                    .addComponent(bus_time, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(label_bus_rate)
                    .addComponent(bus_rate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(update)
                    .addComponent(back))
                .addContainerGap(40, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void route_noItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_route_noItemStateChanged
        // TODO add your handling code here:
        try{
            load_busStop();
        }catch(Exception ex){
            
        }
    }//GEN-LAST:event_route_noItemStateChanged

    private void updateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateActionPerformed
        // TODO add your handling code here:
        try{
        String route=route_no.getSelectedItem().toString();
        String stop=bus_stop.getSelectedItem().toString();
        String time=bus_time.getText();
        String rate=bus_rate.getText();
        String sql="update tbl_bus set bus_time='"+time+"',rate="+rate+" where route_no='"+route+"' and bus_stop='"+stop+"'";
        DatabaseConnector mDBConnect=new DatabaseConnector();
        int status=mDBConnect.insert_execute(sql);
        if(status==1){
            JOptionPane.showMessageDialog(null, "Rate updated Successfully!!");
            bus_time.setText("");
            bus_rate.setText("");
        }else{
            JOptionPane.showMessageDialog(null, "Updation Failed");
        }
        }catch(Exception ex){
            //Logger.getLogger(EditRate.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Updation Failed");
        }
        
    }//GEN-LAST:event_updateActionPerformed

    private void backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backActionPerformed
        // TODO add your handling code here:
        Settings mDBoard = new Settings();
        mDBoard.show();
        this.dispose();
    }//GEN-LAST:event_backActionPerformed

    private void bus_stopItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_bus_stopItemStateChanged
        // TODO add your handling code here:
        try{
        load_rate();
        }catch(Exception ex){
            
        }
    }//GEN-LAST:event_bus_stopItemStateChanged

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditRate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditRate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditRate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditRate.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new EditRate().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(EditRate.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(EditRate.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton back;
    private javax.swing.JTextField bus_rate;
    private javax.swing.JComboBox bus_stop;
    private javax.swing.JTextField bus_time;
    private javax.swing.JLabel label_bus_rate;
    private javax.swing.JLabel label_bus_stop;
    private javax.swing.JLabel label_bus_time;
    private javax.swing.JLabel label_route_no;
    private javax.swing.JLabel label_update;
    private javax.swing.JComboBox route_no;
    private javax.swing.JButton update;
    // End of variables declaration//GEN-END:variables
}