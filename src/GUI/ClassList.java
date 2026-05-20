/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI;

import MYSQL.mysql;
import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;

/**
 *
 * @author Hash_Boy
 */
public class ClassList extends javax.swing.JPanel {

    private static HashMap<String, Integer> weekdaysMap = new HashMap();
    private static HashMap<String, Integer> yearMap = new HashMap();
    private static HashMap<String, Integer> subjectMap = new HashMap();

    public ClassList() {
        initComponents();
        loadYear();
        loadsubject();
        loadweekday();
        theader1();
    }   private void theader1() {

        JTableHeader thead = jTable1.getTableHeader();

        thead.setForeground((new Color(255, 255, 255)));

        thead.setBackground(new Color(0, 0, 0));

        thead.setFont(new Font("Arial", Font.BOLD, 12));

        TableColumn coll = jTable1.getColumnModel().getColumn(0);

        coll.setPreferredWidth(100);
    }
    private void loadYear() {
        try {

            ResultSet resultSet = mysql.executeSearch("SELECT * FROM `al year`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                yearMap.put(resultSet.getString("name"), resultSet.getInt("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            roundedCombobox5.setModel(model);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadweekday() {
        try {

            ResultSet resultSet = mysql.executeSearch("SELECT * FROM `weekdays`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                weekdaysMap.put(resultSet.getString("name"), resultSet.getInt("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            roundedCombobox7.setModel(model);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadsubject() {
        try {

            ResultSet resultSet = mysql.executeSearch("SELECT * FROM `subject`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("Description"));
                subjectMap.put(resultSet.getString("Description"), resultSet.getInt("Subno"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            roundedCombobox6.setModel(model);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void searchClass(String searchText, DefaultTableModel model) {
        try {
           // System.out.println("enava");
            ResultSet resultSet = mysql.executeSearch("SELECT * FROM `class` "
                    + "INNER JOIN `teacher` ON `class`.`Teacher_Tno`= `teacher`.`Tno`"
                    + "INNER JOIN `al year` ON `class`.`AL year_id`= `al year`.`id`"
                    + "INNER JOIN `timeslot1` ON `class`.`timeslot1_id`=`timeslot1`.`id`"
                    + "INNER JOIN `timeslot2` ON `class`.`timeslot2_id`=`timeslot2`.`id`"
                    + "INNER JOIN `subject` ON `class`.`Subject_Subno`=`subject`.`Subno`"
                    + "INNER JOIN `ampm` ON `class`.`ampm_id`=`ampm`.`id`"
                    + "INNER JOIN `stream` ON `class`.`stream_id`=`stream`.`id`"
                    + "INNER JOIN `weekdays` ON `class`.`weekdays_id`=`weekdays`.`id`"
                    + "WHERE `class`.`Class name` LIKE '%" + searchText + "%' ");

            model.setRowCount(0);

            while (resultSet.next()) {
                //System.out.println("re");
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("class.Classno"));
                vector.add(resultSet.getString("class.Class name"));
                vector.add(resultSet.getString("class.Class Fee"));
                vector.add(resultSet.getString("subject.Description"));
                vector.add(resultSet.getString("teacher.Name"));
                vector.add(resultSet.getString("stream.name"));
                vector.add(resultSet.getString("al year.name"));
                vector.add(resultSet.getString("timeslot1.name"));
                vector.add(resultSet.getString("timeslot2.name"));
                vector.add(resultSet.getString("ampm.name"));
                vector.add(resultSet.getString("weekdays.name"));
                
                
                model.addRow(vector);
                System.out.println("Search venava");
                
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
        jPanel2 = new javax.swing.JPanel();
        roundedCombobox5 = new Controls.roundedCombobox();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        roundedCombobox6 = new Controls.roundedCombobox();
        jLabel4 = new javax.swing.JLabel();
        roundedCombobox7 = new Controls.roundedCombobox();
        roundedBotton1 = new Controls.RoundedBotton();
        roundedTextFeild2 = new Controls.RoundedTextFeild();
        jLabel5 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        card1.setOpaque(false);
        card1.setPreferredSize(new java.awt.Dimension(929, 40));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("CLASS LIST");

        javax.swing.GroupLayout card1Layout = new javax.swing.GroupLayout(card1);
        card1.setLayout(card1Layout);
        card1Layout.setHorizontalGroup(
            card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(818, Short.MAX_VALUE))
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

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setPreferredSize(new java.awt.Dimension(929, 200));

        roundedCombobox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedCombobox5ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel1.setText("AL Year");

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel3.setText("Subject Name");

        roundedCombobox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedCombobox6ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jLabel4.setText("Week Day");

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
                .addGap(52, 52, 52)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(roundedCombobox6, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                                .addComponent(roundedBotton1, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(roundedCombobox5, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(roundedCombobox7, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(80, 80, 80))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(roundedTextFeild2, javax.swing.GroupLayout.PREFERRED_SIZE, 698, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roundedCombobox5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roundedCombobox7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(roundedBotton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(roundedCombobox6, javax.swing.GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roundedTextFeild2, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setLayout(new java.awt.BorderLayout());

        jTable1.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Class ID", "Class Name", "Class Fee", "Subject", "Teacher", "Stream", "A/L Year", "Time Slot 1", "Time Slot 2", "AM/PM", "WeekDay"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void roundedCombobox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedCombobox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundedCombobox5ActionPerformed

    private void roundedCombobox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedCombobox6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundedCombobox6ActionPerformed

    private void roundedCombobox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedCombobox7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundedCombobox7ActionPerformed

    private void roundedBotton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedBotton1ActionPerformed
        try {
            String alYear = String.valueOf(roundedCombobox5.getSelectedItem());
            String weekday = String.valueOf(roundedCombobox7.getSelectedItem());
            String subject = String.valueOf(roundedCombobox6.getSelectedItem());

            ResultSet resultSet = null;

            if (!alYear.equals("Select") && weekday.equals("Select") && subject.equals("Select")) {

                resultSet = mysql.executeSearch("SELECT * FROM `class` "
                        + "INNER JOIN `teacher` ON `class`.`Teacher_Tno`= `teacher`.`Tno`"
                        + "INNER JOIN `al year` ON `class`.`AL year_id`= `al year`.`id`"
                        + "INNER JOIN `timeslot1` ON `class`.`timeslot1_id`=`timeslot1`.`id`"
                        + "INNER JOIN `timeslot2` ON `class`.`timeslot2_id`=`timeslot2`.`id`"
                        + "INNER JOIN `subject` ON `class`.`Subject_Subno`=`subject`.`Subno`"
                        + "INNER JOIN `ampm` ON `class`.`ampm_id`=`ampm`.`id`"
                        + "INNER JOIN `stream` ON `class`.`stream_id`=`stream`.`id`"
                        + "INNER JOIN `weekdays` ON `class`.`weekdays_id`=`weekdays`.`id`"
                        + "WHERE `class`.`AL year_id`= '" + yearMap.get(alYear) + "'");
            } else if (alYear.equals("Select") && !weekday.equals("Select") && subject.equals("Select")) {
                resultSet = mysql.executeSearch("SELECT * FROM `class` "
                        + "INNER JOIN `teacher` ON `class`.`Teacher_Tno`= `teacher`.`Tno`"
                        + "INNER JOIN `al year` ON `class`.`AL year_id`= `al year`.`id`"
                        + "INNER JOIN `timeslot1` ON `class`.`timeslot1_id`=`timeslot1`.`id`"
                        + "INNER JOIN `timeslot2` ON `class`.`timeslot2_id`=`timeslot2`.`id`"
                        + "INNER JOIN `subject` ON `class`.`Subject_Subno`=`subject`.`Subno`"
                        + "INNER JOIN `ampm` ON `class`.`ampm_id`=`ampm`.`id`"
                        + "INNER JOIN `stream` ON `class`.`stream_id`=`stream`.`id`"
                        + "INNER JOIN `weekdays` ON `class`.`weekdays_id`=`weekdays`.`id`"
                        + "WHERE `class`.`weekdays_id`= '" + weekdaysMap.get(weekday) + "'");
            } else if (alYear.equals("Select") && weekday.equals("Select") && !subject.equals("Select")) {
                resultSet = mysql.executeSearch("SELECT * FROM `class` "
                        + "INNER JOIN `teacher` ON `class`.`Teacher_Tno`= `teacher`.`Tno`"
                        + "INNER JOIN `al year` ON `class`.`AL year_id`= `al year`.`id`"
                        + "INNER JOIN `timeslot1` ON `class`.`timeslot1_id`=`timeslot1`.`id`"
                        + "INNER JOIN `timeslot2` ON `class`.`timeslot2_id`=`timeslot2`.`id`"
                        + "INNER JOIN `subject` ON `class`.`Subject_Subno`=`subject`.`Subno`"
                        + "INNER JOIN `ampm` ON `class`.`ampm_id`=`ampm`.`id`"
                        + "INNER JOIN `stream` ON `class`.`stream_id`=`stream`.`id`"
                        + "INNER JOIN `weekdays` ON `class`.`weekdays_id`=`weekdays`.`id`"
                        + "WHERE `class`.`Subject_Subno`= '" + subjectMap.get(subject) + "'");

            } else if (!alYear.equals("Select") && !weekday.equals("Select") && subject.equals("Select")) {
                resultSet = mysql.executeSearch("SELECT * FROM `class` "
                        + "INNER JOIN `teacher` ON `class`.`Teacher_Tno`= `teacher`.`Tno`"
                        + "INNER JOIN `al year` ON `class`.`AL year_id`= `al year`.`id`"
                        + "INNER JOIN `timeslot1` ON `class`.`timeslot1_id`=`timeslot1`.`id`"
                        + "INNER JOIN `timeslot2` ON `class`.`timeslot2_id`=`timeslot2`.`id`"
                        + "INNER JOIN `subject` ON `class`.`Subject_Subno`=`subject`.`Subno`"
                        + "INNER JOIN `ampm` ON `class`.`ampm_id`=`ampm`.`id`"
                        + "INNER JOIN `stream` ON `class`.`stream_id`=`stream`.`id`"
                        + "INNER JOIN `weekdays` ON `class`.`weekdays_id`=`weekdays`.`id`"
                        + "WHERE `class`.`AL year_id`= '" + yearMap.get(alYear) + "'"
                        + "AND `class`.`weekdays_id`= '" + weekdaysMap.get(weekday) + "'");
            } else if (alYear.equals("Select") && !weekday.equals("Select") && !subject.equals("Select")) {
                resultSet = mysql.executeSearch("SELECT * FROM `class` "
                        + "INNER JOIN `teacher` ON `class`.`Teacher_Tno`= `teacher`.`Tno`"
                        + "INNER JOIN `al year` ON `class`.`AL year_id`= `al year`.`id`"
                        + "INNER JOIN `timeslot1` ON `class`.`timeslot1_id`=`timeslot1`.`id`"
                        + "INNER JOIN `timeslot2` ON `class`.`timeslot2_id`=`timeslot2`.`id`"
                        + "INNER JOIN `subject` ON `class`.`Subject_Subno`=`subject`.`Subno`"
                        + "INNER JOIN `ampm` ON `class`.`ampm_id`=`ampm`.`id`"
                        + "INNER JOIN `stream` ON `class`.`stream_id`=`stream`.`id`"
                        + "INNER JOIN `weekdays` ON `class`.`weekdays_id`=`weekdays`.`id`"
                        + "WHERE AND `class`.`weekdays_id`= '" + weekdaysMap.get(weekday) + "'"
                        + "AND `class`.`Subject_Subno`= '" + subjectMap.get(subject) + "' ");
            } else if (!alYear.equals("Select") && weekday.equals("Select") && !subject.equals("Select")) {
                resultSet = mysql.executeSearch("SELECT * FROM `class` "
                        + "INNER JOIN `teacher` ON `class`.`Teacher_Tno`= `teacher`.`Tno`"
                        + "INNER JOIN `al year` ON `class`.`AL year_id`= `al year`.`id`"
                        + "INNER JOIN `timeslot1` ON `class`.`timeslot1_id`=`timeslot1`.`id`"
                        + "INNER JOIN `timeslot2` ON `class`.`timeslot2_id`=`timeslot2`.`id`"
                        + "INNER JOIN `subject` ON `class`.`Subject_Subno`=`subject`.`Subno`"
                        + "INNER JOIN `ampm` ON `class`.`ampm_id`=`ampm`.`id`"
                        + "INNER JOIN `stream` ON `class`.`stream_id`=`stream`.`id`"
                        + "INNER JOIN `weekdays` ON `class`.`weekdays_id`=`weekdays`.`id`"
                        + "WHERE `class`.`AL year_id`= '" + yearMap.get(alYear) + "'"
                        + "AND `class`.`Subject_Subno`= '" + subjectMap.get(subject) + "' ");
            } else if (!alYear.equals("Select") && !weekday.equals("Select") && !subject.equals("Select")) {

                resultSet = mysql.executeSearch("SELECT * FROM `class` "
                        + "INNER JOIN `teacher` ON `class`.`Teacher_Tno`= `teacher`.`Tno`"
                        + "INNER JOIN `al year` ON `class`.`AL year_id`= `al year`.`id`"
                        + "INNER JOIN `timeslot1` ON `class`.`timeslot1_id`=`timeslot1`.`id`"
                        + "INNER JOIN `timeslot2` ON `class`.`timeslot2_id`=`timeslot2`.`id`"
                        + "INNER JOIN `subject` ON `class`.`Subject_Subno`=`subject`.`Subno`"
                        + "INNER JOIN `ampm` ON `class`.`ampm_id`=`ampm`.`id`"
                        + "INNER JOIN `stream` ON `class`.`stream_id`=`stream`.`id`"
                        + "INNER JOIN `weekdays` ON `class`.`weekdays_id`=`weekdays`.`id`"
                        + "WHERE `class`.`AL year_id`= '" + yearMap.get(alYear) + "'"
                        + "AND `class`.`weekdays_id`= '" + weekdaysMap.get(weekday) + "'"
                        + "AND `class`.`Subject_Subno`= '" + subjectMap.get(subject) + "' ");
            }
            System.out.println("table eka thenata enakan enava");
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("class.Classno"));
                vector.add(resultSet.getString("class.Class name"));
                vector.add(resultSet.getString("class.Class Fee"));
                vector.add(resultSet.getString("subject.Description"));
                vector.add(resultSet.getString("teacher.Name"));
                vector.add(resultSet.getString("stream.name"));
                vector.add(resultSet.getString("al year.name"));
                vector.add(resultSet.getString("timeslot1.name"));
                vector.add(resultSet.getString("timeslot2.name"));
                vector.add(resultSet.getString("ampm.name"));
                vector.add(resultSet.getString("weekdays.name"));

                model.addRow(vector);
            }

            resultSet = null;

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_roundedBotton1ActionPerformed

    private void roundedTextFeild2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_roundedTextFeild2KeyReleased
        searchClass(roundedTextFeild2.getText().trim(),  (DefaultTableModel) jTable1.getModel());
    }//GEN-LAST:event_roundedTextFeild2KeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Controls.card card1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private Controls.RoundedBotton roundedBotton1;
    private Controls.roundedCombobox roundedCombobox5;
    private Controls.roundedCombobox roundedCombobox6;
    private Controls.roundedCombobox roundedCombobox7;
    private Controls.RoundedTextFeild roundedTextFeild2;
    // End of variables declaration//GEN-END:variables
}
