/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import GUI.*;
import MYSQL.mysql;
import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import raven.toast.Notifications;

/**
 *
 * @author Hash_Boy
 */
public class ClassAttendance extends javax.swing.JPanel {

    private static HashMap<String, Integer> classMap = new HashMap();

    public ClassAttendance() {
        initComponents();
        loadclass();
        theader1();
    }
   private void theader1() {

        JTableHeader thead = jTable1.getTableHeader();

        thead.setForeground((new Color(255, 255, 255)));

        thead.setBackground(new Color(0, 0, 0));

        thead.setFont(new Font("Arial", Font.BOLD, 12));

        TableColumn coll = jTable1.getColumnModel().getColumn(0);

        coll.setPreferredWidth(100);
    }
    private void loadclass() {
        try {

            ResultSet resultSet = mysql.executeSearch("SELECT * FROM `class`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("Class name"));
                classMap.put(resultSet.getString("Class name"), resultSet.getInt("Classno"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            roundedCombobox7.setModel(model);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void searchClass(String searchText, DefaultTableModel model) {
        try {
   
              ResultSet resultSet = mysql.executeSearch("Select * FROM `attendance`"
                    + "INNER JOIN `student` ON `attendance`.`class_has_student_student_Sno`=`student`.`Sno`"
                    + "INNER JOIN `class` ON `attendance`.`class_has_student_class_Classno` = `class`.`Classno`"
                     + "INNER JOIN `al year` ON `class`.`AL year_id`= `al year`.`id`"
                      + "INNER JOIN `attendance_type` ON `attendance`.`attendance_type_id` =`attendance_type`.`id`"
                      + "WHERE `student`.`name` LIKE '%" + searchText + "%'");
 
            System.out.println("table eka thenata enakan enava");
           
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("class.Classno"));
                vector.add(resultSet.getString("class.Class name"));
                vector.add(resultSet.getString("attendance.date"));
                vector.add(resultSet.getString("attendance.check_in"));
                vector.add(resultSet.getString("attendance.class_has_student_student_Sno"));
                vector.add(resultSet.getString("student.Name"));
                vector.add(resultSet.getString("al year.name"));
                vector.add(resultSet.getString("attendance_type.name"));
 

                model.addRow(vector);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        card1 = new Controls.card();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        roundedCombobox7 = new Controls.roundedCombobox();
        roundedBotton1 = new Controls.RoundedBotton();
        roundedTextFeild2 = new Controls.RoundedTextFeild();
        jDateChooser4 = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        card1.setOpaque(false);
        card1.setPreferredSize(new java.awt.Dimension(929, 40));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("CLASS Attendance");

        javax.swing.GroupLayout card1Layout = new javax.swing.GroupLayout(card1);
        card1.setLayout(card1Layout);
        card1Layout.setHorizontalGroup(
            card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(753, Short.MAX_VALUE))
        );
        card1Layout.setVerticalGroup(
            card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        add(card1, java.awt.BorderLayout.PAGE_START);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jTable1.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Class ID", "Class Name", "date", "ckeck in", "student Id ", "student name", "A/L Year", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(929, 200));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel1.setText("date");

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel4.setText("class");

        roundedCombobox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedCombobox7ActionPerformed(evt);
            }
        });

        roundedBotton1.setBackground(new java.awt.Color(204, 255, 255));
        roundedBotton1.setText("Filter");
        roundedBotton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedBotton1ActionPerformed(evt);
            }
        });

        roundedTextFeild2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                roundedTextFeild2KeyReleased(evt);
            }
        });

        jLabel5.setText("Search :");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel1))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jDateChooser4, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(roundedCombobox7, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addComponent(roundedBotton1, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(109, 109, 109)
                        .addComponent(roundedTextFeild2, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel4))
                        .addGap(1, 1, 1)
                        .addComponent(jDateChooser4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(roundedCombobox7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(roundedBotton1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(23, 23, 23)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundedTextFeild2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(55, 55, 55))
        );

        jPanel3.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void roundedCombobox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedCombobox7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundedCombobox7ActionPerformed

    private void roundedBotton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedBotton1ActionPerformed
        try {
            String classA = String.valueOf(roundedCombobox7.getSelectedItem());
            Date selectedDate = jDateChooser4.getDate();

            String formattedDate = null;
            if (selectedDate != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                formattedDate = sdf.format(selectedDate);
            

            

            
              ResultSet resultSet = mysql.executeSearch("Select * FROM `attendance`"
                    + "INNER JOIN `student` ON `attendance`.`class_has_student_student_Sno`=`student`.`Sno`"
                    + "INNER JOIN `class` ON `attendance`.`class_has_student_class_Classno` = `class`.`Classno`"
                     + "INNER JOIN `al year` ON `class`.`AL year_id`= `al year`.`id`"
                      + "INNER JOIN `attendance_type` ON `attendance`.`attendance_type_id` =`attendance_type`.`id`"
                    + "WHERE `attendance`.`date`= '" + formattedDate + "' AND "
                    + "`attendance`.`class_has_student_class_Classno`='"+classMap.get(classA)+"'");
 
            System.out.println("table eka thenata enakan enava");
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("class.Classno"));
                vector.add(resultSet.getString("class.Class name"));
                vector.add(resultSet.getString("attendance.date"));
                vector.add(resultSet.getString("attendance.check_in"));
                vector.add(resultSet.getString("attendance.class_has_student_student_Sno"));
                vector.add(resultSet.getString("student.Name"));
                vector.add(resultSet.getString("al year.name"));
                vector.add(resultSet.getString("attendance_type.name"));
 

                model.addRow(vector);
            }

            resultSet = null;
            }else{
              Notifications.getInstance().show(Notifications.Type.ERROR, Notifications.Location.TOP_RIGHT, "Select date and Class");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_roundedBotton1ActionPerformed

    private void roundedTextFeild2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_roundedTextFeild2KeyReleased
        searchClass(roundedTextFeild2.getText().trim(), (DefaultTableModel) jTable1.getModel());
    }//GEN-LAST:event_roundedTextFeild2KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Controls.card card1;
    private com.toedter.calendar.JDateChooser jDateChooser4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private Controls.RoundedBotton roundedBotton1;
    private Controls.roundedCombobox roundedCombobox7;
    private Controls.RoundedTextFeild roundedTextFeild2;
    // End of variables declaration//GEN-END:variables
}
