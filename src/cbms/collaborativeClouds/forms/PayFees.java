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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author CollaborativeClouds Software Solutions
 */
public class PayFees extends javax.swing.JFrame {

    /**
     * Creates new form PayFees
     */
    static int prev_balance = 0;
    public String busTime = "";

    public PayFees() throws ClassNotFoundException, SQLException {
        initComponents();
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        ImageLocator mImage=new ImageLocator();
        JLabel background = new JLabel(new ImageIcon(mImage.payment));
        this.add(background);
        background.setLayout(new FlowLayout());
        //For FullScreen Window, Uncomment Below Code
        Toolkit tk = Toolkit.getDefaultToolkit();
        int xSize = ((int) tk.getScreenSize().getWidth());
        int ySize = ((int) tk.getScreenSize().getHeight());
        this.setSize(xSize, ySize);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        jLabel1.setLocation(xSize/8, ySize/4);
        branch.setLocation((xSize/8)+75, ySize/4);
        branch.setSize((xSize/4)-50, 30);
        
        jLabel2.setLocation(xSize/8, (ySize/4)+50);
        jLabel2.setSize(100, 25);
        semester.setLocation((xSize/8)+75, (ySize/4)+50);
        semester.setSize((xSize/4)-50, 30);
        
        jLabel3.setLocation(xSize/8, (ySize/4)+100);
        studentId.setLocation((xSize/8)+75, (ySize/4)+100);
        studentId.setSize((xSize/4)-50, 30);
        
        jLabel6.setLocation((xSize-xSize/3)-100,((ySize/4)));
        name.setLocation((xSize-xSize/3)-20,((ySize/4)));
        name.setSize((xSize/4)-50, 30);
        
        jLabel4.setLocation((xSize-xSize/3)-100,((ySize/4)+50));
        route.setLocation((xSize-xSize/3)-20,((ySize/4)+50));
        route.setSize((xSize/4)-50, 30);
        
        jLabel5.setLocation((xSize-xSize/3)-100,((ySize/4)+100));
        bus_stop.setLocation((xSize-xSize/3)-20,((ySize/4)+100));
        bus_stop.setSize((xSize/4)-50, 30);
        
        jLabel8.setLocation((xSize-xSize/3)-100,((ySize/4)+150));
        dues.setLocation((xSize-xSize/3)-20,((ySize/4)+150));
        dues.setSize((xSize/4)-50, 30);
        
        jLabel10.setLocation((xSize-xSize/3)-100,((ySize/4)+200));
        balance.setLocation((xSize-xSize/3)-20,((ySize/4)+200));
        balance.setSize((xSize/4)-50, 30);
        
         jLabel7.setLocation((xSize-xSize/3)-100,((ySize/4)+250));
        feestopay.setLocation((xSize-xSize/3)-20,((ySize/4)+250));
        feestopay.setSize((xSize/4)-50, 30);
        
        jLabel9.setLocation((xSize-xSize/3)-100,((ySize/4)+300));
        fees_paid.setLocation((xSize-xSize/3)-20,((ySize/4)+300));
        fees_paid.setSize((xSize/4)-50, 30);
        message.setLocation((xSize-xSize/3)-20,((ySize/4)+400));
        message.setSize(300,30);
        pay.setLocation((xSize-xSize/3)-20,((ySize/4)+350));
        jButton1.setLocation((xSize-xSize/5),((ySize/4)+350));
        load_student();
        
    }

    public void load_student() throws ClassNotFoundException, SQLException {
        try{
        studentId.removeAllItems();
        String mBranch = branch.getSelectedItem().toString();
        String mSem = semester.getSelectedItem().toString();
            //System.err.println(mBranch+" "+mSem);
        DatabaseConnector mDBConnect = new DatabaseConnector();
        String sql_select = "select student_id from tbl_student where department='" + mBranch + "' and semester=" + mSem;
        ResultSet mIds = mDBConnect.select_execute(sql_select);
        int status=0;
        while (mIds.next()) {
            status=1;
            //System.err.println(mIds.getString("student_id"));
            studentId.addItem(mIds.getString("student_id"));
        }
        if(status==1){
            load_Data();
        }
        }catch(Exception ex){
            Logger.getLogger(PayFees.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void load_Data() throws ClassNotFoundException, SQLException {
        DatabaseConnector mDBConnect = new DatabaseConnector();
        //System.err.println(studentId.getItemAt(0));
        String student_id = studentId.getSelectedItem().toString();
        String sql_select = "select * from tbl_student where student_id='" + student_id + "'";
        ResultSet mName = mDBConnect.select_execute(sql_select);
        if (mName.next()) {
            name.setText(mName.getString("student_name"));
            route.setText(mName.getString("route_no"));
            bus_stop.setText(mName.getString("bus_stop"));
        }
        load_Dues();
    }

    public void load_Dues() throws ClassNotFoundException, SQLException {
        DatabaseConnector mDBConnect = new DatabaseConnector();
        String student_id = studentId.getSelectedItem().toString();
        String sql_select = "select dues,balance from tbl_student_route where student_id='" + student_id + "'";
        ResultSet mDues = mDBConnect.select_execute(sql_select);
        int due = 0;
        int mBalance = 0;
        while (mDues.next()) {
            due += mDues.getInt("dues");
            mBalance += mDues.getInt("balance");
        }
        dues.setText("" + due);
        balance.setText("" + mBalance);
        prev_balance = mBalance;
        load_FeesToPay();
    }

    public void load_FeesToPay() throws ClassNotFoundException, SQLException {
        DatabaseConnector mDBConnect = new DatabaseConnector();
        String route_num = route.getText();
        String busStop = bus_stop.getText();
        String sql_select = "select rate,bus_time from tbl_bus where route_no='" + route_num + "' and bus_stop='" + busStop + "'";
        ResultSet mRate = mDBConnect.select_execute(sql_select);
        int due = Integer.parseInt(dues.getText());
        int total = 0;
        int rate_bus = 0;
        if (mRate.next()) {
            rate_bus = mRate.getInt("rate");
            total = due + mRate.getInt("rate");
            busTime = mRate.getString("bus_time");
        }
        total = total - prev_balance;

        String sem = semester.getSelectedItem().toString();
        String id = studentId.getSelectedItem().toString();
        int rcpt = checkPaid(id, sem);
        if (rcpt != -1) {
            int paid = getPaidAmount(id, sem);
            int amount = 0;
            if (paid < rate_bus) {
                amount = rate_bus - paid;
            } else {
                amount = 0;
            }

            feestopay.setText("" + paid);
            message.setText("Amount already Paid");
        } else {
            feestopay.setText("" + total);
            message.setText("");
        }
    }

    public int getPaidAmount(String id, String semester) throws ClassNotFoundException, SQLException {
        DatabaseConnector mDBConnect = new DatabaseConnector();
        String sql = "select dues from tbl_student_route where student_id='" + id + "' and semester=" + semester;
        ResultSet mFees = mDBConnect.select_execute(sql);
        int amount = 0;
        if (mFees.next()) {
            amount = mFees.getInt("dues");
        }
        return amount;
    }

    public int checkPaid(String mId, String mSem) throws SQLException, ClassNotFoundException {
        DatabaseConnector mDBConnect = new DatabaseConnector();

        String sql_select = "select reciept_no from tbl_student_route where student_id='" + mId + "' and semester=" + mSem;
        ResultSet mReciept = mDBConnect.select_execute(sql_select);
        int rcpt = -1;
        if (mReciept.next()) {
            rcpt = mReciept.getInt("reciept_no");
        }
        return rcpt;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel8 = new javax.swing.JLabel();
        message = new javax.swing.JLabel();
        studentId = new javax.swing.JComboBox();
        bus_stop = new javax.swing.JTextField();
        dues = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        route = new javax.swing.JTextField();
        fees_paid = new javax.swing.JTextField();
        feestopay = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        branch = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        balance = new javax.swing.JTextField();
        semester = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        pay = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Dues:");

        message.setForeground(new java.awt.Color(255, 255, 255));
        message.setText("Amount Not Paid");

        studentId.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                studentIdItemStateChanged(evt);
            }
        });

        bus_stop.setEditable(false);

        dues.setEditable(false);

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Fees to Pay:");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Bus Stop:");

        route.setEditable(false);

        feestopay.setEditable(false);

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Branch:");

        branch.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CSE", "ME", "CE", "ECE", "EEE", "IT" }));
        branch.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                branchItemStateChanged(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Route No:");

        balance.setEditable(false);

        semester.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8" }));
        semester.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                semesterItemStateChanged(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(0, 0, 0));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Name:");

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Semester:");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Student ID:");

        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Balance:");

        name.setEditable(false);

        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Fees Paid:");

        pay.setBackground(new java.awt.Color(0, 0, 0));
        pay.setForeground(new java.awt.Color(255, 255, 255));
        pay.setText("Pay");
        pay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                payActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 800, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel8)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(message)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(studentId, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(bus_stop)
                    .addContainerGap()))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(dues, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel7)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel5)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(route, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(fees_paid)
                    .addContainerGap()))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(feestopay)
                    .addContainerGap()))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(branch, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(balance, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(semester, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel10)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel9)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pay, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 459, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel8)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(message)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(studentId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(bus_stop, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(dues, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel7)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel5)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(route, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(fees_paid, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(feestopay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel1)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(branch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(balance, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(semester, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel6)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel2)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel3)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel10)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel9)
                    .addGap(0, 0, Short.MAX_VALUE)))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pay, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        DashBoard mDBoard = new DashBoard();
        mDBoard.show();
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void branchItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_branchItemStateChanged
        try {
            // TODO add your handling code here:

            load_student();
        } catch (Exception err) {

        }

    }//GEN-LAST:event_branchItemStateChanged

    private void semesterItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_semesterItemStateChanged
        // TODO add your handling code here:
        try {
            load_student();
        } catch (Exception err) {

        }
    }//GEN-LAST:event_semesterItemStateChanged

    private void studentIdItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_studentIdItemStateChanged
        // TODO add your handling code here:
        try {
            load_Data();
        } catch (Exception err) {

        }
    }//GEN-LAST:event_studentIdItemStateChanged

    public void setDuesAndBalanceToZero(String studentId) throws ClassNotFoundException, SQLException {
        DatabaseConnector mDBConnect = new DatabaseConnector();
        String sql_update = " select reciept_no from tbl_student_route where dues>0 and student_id='" + studentId + "'";
        ResultSet mReciepts = mDBConnect.select_execute(sql_update);
        int reciepts[] = new int[10];
        int index = 0;
        while (mReciepts.next()) {
            reciepts[index] = mReciepts.getInt("reciept_no");
            index++;
        }
        for (int i = 0; i < reciepts.length; i++) {
            String sql = "update tbl_student_route set dues=0,balance=0 where reciept_no=" + reciepts[i];
            int status = mDBConnect.insert_execute(sql);
        }
    }

    private void payActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_payActionPerformed
        try {
            // TODO add your handling code here:
            String mBranch = branch.getSelectedItem().toString();
            String mSem = semester.getSelectedItem().toString();
            String mId = studentId.getSelectedItem().toString();
            String mName = name.getText();
            String mRouteNo = route.getText();
            String mBusStop = bus_stop.getText();
            String mDues = dues.getText();
            String mFees = feestopay.getText();
            String mPaid = fees_paid.getText();
            int mBalance = 0;
            int mFeesToPay = Integer.parseInt(mFees);
            int mPaidFees = Integer.parseInt(mPaid);
            if (mPaidFees < mFeesToPay) {
                int due = mFeesToPay - mPaidFees;
                mDues = "" + due;
            } else if (mPaidFees > mFeesToPay) {
                int bal = mPaidFees - mFeesToPay;
                mBalance = bal;
                mDues = "" + 0;
            } else {
                mDues = "" + 0;
                mBalance = 0;
            }
            DatabaseConnector mDBConnect = new DatabaseConnector();

            String sql_select = "select reciept_no from tbl_student_route where student_id='" + mId + "' and semester=" + mSem;
            ResultSet mReciept = mDBConnect.select_execute(sql_select);
            int rcpt = -1;
            if (mReciept.next()) {
                rcpt = mReciept.getInt("reciept_no");
            }
            System.err.println("Reciept: " + rcpt);

            if (rcpt == -1) {
                /*String sql_update = "update tbl_student_route set dues=0 where student_id='" + mId + "'";
                 int status = mDBConnect.insert_execute(sql_update);

                 sql_update = "update tbl_student_route set balance=0 where student_id='" + mId + "'";
                 status = mDBConnect.insert_execute(sql_update);
                 */
                setDuesAndBalanceToZero(mId);
                String sql_insert = "insert into tbl_student_route(student_id,route_no,bus_stop,bus_time,semester,fees_paid,dues,balance)"
                        + "values('" + mId + "','" + mRouteNo + "','" + mBusStop + "','" + busTime + "'," + mSem + "," + mPaid + "," + mDues + "," + mBalance + ")";
                System.err.println(sql_insert);
                int status = mDBConnect.insert_execute(sql_insert);
                if (status == 1) {
                    //JOptionPane.showMessageDialog(null, "Successfully Paid");
                    int semester_ = Integer.parseInt(mSem);
                    Reciept mReciept_ = new Reciept();
                    mReciept_.reciept_form(mId, semester_, mName, mBranch);
                    mReciept_.show();
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Payment Failed");
                }
            } else {
                int checked = JOptionPane.showConfirmDialog(null, "Amount Already Paid for this semester\nDo you want to update new fees");
                System.err.println(checked);
                if (checked == 0) {
                    //TODO
                    //Get paid amount,due,balance of this student.
                    //Get the new paid amount.
                    //Calculate new due, balance & update new amount details.

                    String sql_select_amnt = "select * from tbl_student_route where reciept_no=" + rcpt;
                    ResultSet mResult = mDBConnect.select_execute(sql_select_amnt);
                    int mLastDue = 0, mLastBalance = 0, mLastFeesPaid;
                    if (mResult.next()) {

                    }

                    String sql_update = "update tbl_student_route set dues=" + mDues + ",balance=" + mBalance + ",fees_paid=" + mPaid + " where reciept_no=" + rcpt;
                    int status = mDBConnect.insert_execute(sql_update);
                    if (status == 1) {
                        JOptionPane.showMessageDialog(null, "Amount Updated");
                    } else {
                        JOptionPane.showMessageDialog(null, "Amount Updation Failed");
                    }
                } else if (checked == 1) {

                }
            }
        } catch (Exception err) {

        }
    }//GEN-LAST:event_payActionPerformed

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
            java.util.logging.Logger.getLogger(PayFees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PayFees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PayFees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PayFees.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new PayFees().setVisible(true);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(PayFees.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SQLException ex) {
                    Logger.getLogger(PayFees.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField balance;
    private javax.swing.JComboBox branch;
    private javax.swing.JTextField bus_stop;
    private javax.swing.JTextField dues;
    private javax.swing.JTextField fees_paid;
    private javax.swing.JTextField feestopay;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel message;
    private javax.swing.JTextField name;
    private javax.swing.JButton pay;
    private javax.swing.JTextField route;
    private javax.swing.JComboBox semester;
    private javax.swing.JComboBox studentId;
    // End of variables declaration//GEN-END:variables
}
