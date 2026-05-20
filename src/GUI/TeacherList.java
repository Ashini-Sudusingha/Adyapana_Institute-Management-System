package GUI;

import MYSQL.mysql;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;



public class TeacherList extends javax.swing.JPanel {

    private HashMap<String, Integer> Class = new HashMap<>();
    
    public TeacherList() {
        initComponents();
        loadClass();
        setupAutoComplete();
        loadEmployees();
        CenterAlignJTable();
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
    private void loadClass() {

        try {

            ResultSet resultSet = mysql.executeSearch("SELECT * FROM `class`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("Class name"));
                Class.put(resultSet.getString("Class name"), resultSet.getInt("Classno"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            roundedCombobox1.setModel(model);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }
    private void setupAutoComplete() {
        JPopupMenu suggestionMenu = new JPopupMenu();

        // Set fixed width and height for the JPopupMenu
        suggestionMenu.setPreferredSize(new Dimension(430, 200)); // Adjust width and height as needed

        roundedTextFeild2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    String input = roundedTextFeild2.getText();
                    if (input.isEmpty()) {
                        suggestionMenu.setVisible(false);
                        return;
                    }

                    ArrayList<String> suggestions = getSuggestionsFromDB(input);
                    if (suggestions.isEmpty()) {
                        suggestionMenu.setVisible(false);
                        return;
                    }

                    suggestionMenu.removeAll();
                    for (String suggestion : suggestions) {
                        JMenuItem item = new JMenuItem(suggestion);

                        // Customize font size and color
                        item.setFont(new Font("Arial", Font.BOLD, 14)); // Set font to bold and size 14

                        //item.setForeground(Color.BLACK); // Set text color
                        item.setForeground(new Color(0, 102, 51)); // Semi-transparent background

                        item.addActionListener(event -> {
                            roundedTextFeild2.setText(suggestion);
                            suggestionMenu.setVisible(false);
                        });
                        suggestionMenu.add(item);
                    }

                    // Display the JPopupMenu
                    suggestionMenu.show(roundedTextFeild2, 0, roundedTextFeild2.getHeight());
                } catch (Exception ex) {
                    Logger.getLogger(classRForStudent.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    private ArrayList<String> getSuggestionsFromDB(String input) throws Exception {
        ArrayList<String> suggestions = new ArrayList<>();

        try {
            // Execute query using the custom `mysql.executeSearch` method
            ResultSet resultSet = mysql.executeSearch("SELECT `Name` FROM `teacher` WHERE `Name` LIKE '" + input + "%' LIMIT 10");

            // Iterate through the result set and add product names to suggestions list
            while (resultSet.next()) {
                suggestions.add(resultSet.getString("Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return suggestions;
    }
    public void CenterAlignJTable() {

        // Center-align the data in all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

    }
    private void loadEmployees() {

        try {

             ResultSet resultSet = mysql.executeSearch("SELECT * \n"
                    + "FROM `class`\n"
                    + "INNER JOIN `subject` ON `class`.`Subject_Subno` = `subject`.`Subno`\n"
                    + "INNER JOIN `teacher` ON `class`.`Teacher_Tno` = `teacher`.`Tno`\n"
                    + "INNER JOIN `stream` ON `class`.`Stream_id` = `stream`.`id`\n"
                    + "INNER JOIN `al year` ON `class`.`AL year_id` = `al year`.`id`\n"
                    + "INNER JOIN `timeslot1` ON `class`.`timeslot1_id` = `timeslot1`.`id`\n"
                    + "INNER JOIN `timeslot2` ON `class`.`timeslot2_id` = `timeslot2`.`id`\n"
                    + "INNER JOIN `ampm` ON `class`.`ampm_id` = `ampm`.`id`\n"
                    + "INNER JOIN `weekdays` ON `class`.`weekdays_id` = `weekdays`.`id`;");
            
                   

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {

                Vector<String> vector = new Vector<>();

                vector.add(resultSet.getString("teacher.nic"));
                vector.add(resultSet.getString("teacher.name"));
                vector.add(resultSet.getString("stream.name"));
                vector.add(resultSet.getString("class.Class name"));
                vector.add(resultSet.getString("weekdays.name"));
                 vector.add(resultSet.getString("ampm.name"));
                vector.add(resultSet.getString("timeslot1.name"));
                vector.add(resultSet.getString("timeslot2.name"));
                vector.add(resultSet.getString("al year.name"));
                
                

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
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        roundedCombobox1 = new Controls.roundedCombobox();
        roundedBotton3 = new Controls.RoundedBotton();
        roundedTextFeild2 = new Controls.RoundedTextFeild();
        jLabel3 = new javax.swing.JLabel();
        roundedCombobox2 = new Controls.roundedCombobox();
        jLabel4 = new javax.swing.JLabel();
        roundedCombobox3 = new Controls.roundedCombobox();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        card1.setOpaque(false);
        card1.setPreferredSize(new java.awt.Dimension(973, 40));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("TEACHER LIST");

        javax.swing.GroupLayout card1Layout = new javax.swing.GroupLayout(card1);
        card1.setLayout(card1Layout);
        card1Layout.setHorizontalGroup(
            card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(991, Short.MAX_VALUE))
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
        jPanel2.setPreferredSize(new java.awt.Dimension(973, 200));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Select Year");

        roundedCombobox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "Science Stream" }));
        roundedCombobox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedCombobox1ActionPerformed(evt);
            }
        });

        roundedBotton3.setBackground(new java.awt.Color(204, 255, 255));
        roundedBotton3.setText("Filter");
        roundedBotton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedBotton3ActionPerformed(evt);
            }
        });

        roundedTextFeild2.setText("Search Teacher by Name , NIC");
        roundedTextFeild2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        roundedTextFeild2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedTextFeild2ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Select Subject");

        roundedCombobox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "Science Stream" }));
        roundedCombobox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedCombobox2ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Select Stream");

        roundedCombobox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "Science Stream" }));
        roundedCombobox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedCombobox3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(roundedTextFeild2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(roundedCombobox1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(154, 154, 154)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(roundedCombobox3, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 13, Short.MAX_VALUE)))
                        .addGap(79, 79, 79))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(roundedCombobox2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(roundedBotton3, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(92, 92, 92))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roundedCombobox1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roundedCombobox3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roundedCombobox2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roundedBotton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(roundedTextFeild2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jTable1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Teacher NIC", "Teacher Name", "Subject Stream", "Class Name", "Weekday", "Hours", "Hours", "AM/PM", "AL Year"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        jPanel3.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void roundedCombobox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedCombobox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundedCombobox1ActionPerformed

    private void roundedBotton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedBotton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundedBotton3ActionPerformed

    private void roundedTextFeild2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedTextFeild2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundedTextFeild2ActionPerformed

    private void roundedCombobox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedCombobox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundedCombobox2ActionPerformed

    private void roundedCombobox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedCombobox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundedCombobox3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Controls.card card1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private Controls.RoundedBotton roundedBotton3;
    private Controls.roundedCombobox roundedCombobox1;
    private Controls.roundedCombobox roundedCombobox2;
    private Controls.roundedCombobox roundedCombobox3;
    private Controls.RoundedTextFeild roundedTextFeild2;
    // End of variables declaration//GEN-END:variables
}
