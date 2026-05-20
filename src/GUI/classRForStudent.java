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
import javax.swing.Timer;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import raven.toast.Notifications;

public class classRForStudent extends javax.swing.JPanel {

    private HashMap<String, Integer> Class = new HashMap<>();

    public classRForStudent() {
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
        suggestionMenu.setPreferredSize(new Dimension(430, 200)); // Adjust width and height as needed

        Timer timer = new Timer(1000, null); // 5000 milliseconds (5 seconds) delay
        timer.setRepeats(false); // Ensure the timer only triggers once per input

        roundedTextFeild2.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timer.restart(); // Restart the timer on each key release
                timer.setActionCommand(roundedTextFeild2.getText().trim()); // Store the current input

                timer.addActionListener(event -> {
                    String input = event.getActionCommand();
                    if (input.isEmpty()) {
                        suggestionMenu.setVisible(false);
                        return;
                    }

                    try {
                        ArrayList<String> suggestions = getSuggestionsFromDB(input);
                        if (suggestions.isEmpty()) {
                            suggestionMenu.setVisible(false);
                            return;
                        }

                        suggestionMenu.removeAll();
                        for (String suggestion : suggestions) {
                            JMenuItem item = new JMenuItem(suggestion);

                            item.setFont(new Font("Arial", Font.BOLD, 14));
                            item.setForeground(new Color(0, 102, 51)); // Green text color

                            item.addActionListener(ev -> {
                                String[] parts = suggestion.replace("(", "").replace(")", "").split("/");
                                if (parts.length >= 2) {
                                    roundedTextFeild2.setText(parts[1].trim()); // Set only the 'nic'
                                }
                                suggestionMenu.setVisible(false);
                            });

                            suggestionMenu.add(item);
                        }

                        suggestionMenu.show(roundedTextFeild2, 0, roundedTextFeild2.getHeight());
                    } catch (Exception ex) {
                        Logger.getLogger(classRForStudent.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
            }
        });
    }

    private ArrayList<String> getSuggestionsFromDB(String input) throws Exception {
        ArrayList<String> suggestions = new ArrayList<>();

        try {
            String query = String.format(
                    "SELECT `Name`, `NIC`, `Sno` "
                    + "FROM `student` "
                    + "WHERE `Name` LIKE '%s%%' "
                    + "   OR `NIC` LIKE '%s%%' "
                    + "   OR `Sno` LIKE '%s%%' "
                    + "LIMIT 10",
                    input, input, input
            );

            ResultSet resultSet = mysql.executeSearch(query);

            while (resultSet.next()) {
                String name = resultSet.getString("Name");
                String nic = resultSet.getString("NIC");
                String stuID = resultSet.getString("Sno");

                suggestions.add(String.format("(%s/%s/%s)",
                        name != null ? name : "-",
                        nic != null ? nic : "-",
                        stuID != null ? stuID : "-"
                ));
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

            ResultSet resultSet = mysql.executeSearch("SELECT * FROM `student` "
                    + "INNER JOIN `student_has_class` ON `student`.`Sno` = `student_has_class`.`Student_Sno` "
                    + "INNER JOIN `class` ON `class`.`Classno` = `student_has_class`.`Class_Classno`");

            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            while (resultSet.next()) {

                Vector<String> vector = new Vector<>();

                vector.add(resultSet.getString("NIC"));
                vector.add(resultSet.getString("Name"));
                vector.add(resultSet.getString("class.Class name"));

                model.addRow(vector);
            }

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void insertData() {
        try {
            String cl = String.valueOf(roundedCombobox1.getSelectedItem());
            String name = roundedTextFeild2.getText();

            ResultSet resultSet2 = mysql.executeSearch("SELECT `Sno` FROM `student` WHERE `NIC` = '" + name + "'");

            int idd = -1;

            if (resultSet2.next()) {

                idd = resultSet2.getInt("Sno");
            }

            ResultSet resultSet1 = mysql.executeSearch("SELECT * FROM `student_has_class` WHERE `Student_Sno` = '" + idd + "' AND `Class_Classno` = '" + Class.get(cl) + "'");

            if (resultSet1.next()) {

                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "This Student Already in this Class");

            } else {

                int id = -1; // Initialize id with a default value

                ResultSet resultSet = mysql.executeSearch("SELECT `Sno` FROM `student` WHERE `NIC` = '" + name + "'");

                if (resultSet.next()) {
                    id = resultSet.getInt("Sno");
                }

                if (id == -1) {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Student not found.");
                    return; // Exit the method if the student is not found
                }

                if (cl.isEmpty() || cl.equals("Select")) {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Product type cannot be empty.");
                } else {
                    mysql.executeIUD("INSERT INTO `student_has_class` (`Student_Sno`, `Class_Classno`) VALUES('" + id + "','" + Class.get(cl) + "')");
                    Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_RIGHT, "Successfully added Class");
                    loadEmployees();
                }

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
        jLabel9 = new javax.swing.JLabel();
        roundedTextFeild2 = new Controls.RoundedTextFeild();
        roundedBotton2 = new Controls.RoundedBotton();
        roundedBotton3 = new Controls.RoundedBotton();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        roundedTextFeild1 = new Controls.RoundedTextFeild();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        card1.setOpaque(false);
        card1.setPreferredSize(new java.awt.Dimension(969, 40));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ADD CLASS FOR STUDENTS");

        javax.swing.GroupLayout card1Layout = new javax.swing.GroupLayout(card1);
        card1.setLayout(card1Layout);
        card1Layout.setHorizontalGroup(
            card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(845, Short.MAX_VALUE))
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
        jPanel2.setPreferredSize(new java.awt.Dimension(969, 150));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Select Class");

        roundedCombobox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "Science Stream" }));
        roundedCombobox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedCombobox1ActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Select Student (Name / Student Id / NIC)");

        roundedBotton2.setForeground(new java.awt.Color(204, 0, 0));
        roundedBotton2.setText("Delete");
        roundedBotton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedBotton2ActionPerformed(evt);
            }
        });

        roundedBotton3.setBackground(new java.awt.Color(0, 153, 102));
        roundedBotton3.setForeground(new java.awt.Color(255, 255, 255));
        roundedBotton3.setText("Add Class for Student ");
        roundedBotton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedBotton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(roundedBotton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(roundedBotton2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(roundedCombobox1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 186, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addGap(202, 202, 202))
                            .addComponent(roundedTextFeild2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(33, 33, 33))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roundedTextFeild2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roundedCombobox1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roundedBotton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roundedBotton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(1101, 50));

        roundedTextFeild1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedTextFeild1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(95, Short.MAX_VALUE)
                .addComponent(roundedTextFeild1, javax.swing.GroupLayout.PREFERRED_SIZE, 898, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(108, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(roundedTextFeild1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jTable1.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Student NIC", "Student Name", "Class Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel5.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void roundedCombobox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedCombobox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundedCombobox1ActionPerformed

    private void roundedBotton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedBotton2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundedBotton2ActionPerformed

    private void roundedBotton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedBotton3ActionPerformed

        insertData();
    }//GEN-LAST:event_roundedBotton3ActionPerformed

    private void roundedTextFeild1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedTextFeild1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundedTextFeild1ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked

        // Get the selected row index
        int row = jTable1.getSelectedRow();

        // Retrieve values from the table
        String uNIC = String.valueOf(jTable1.getValueAt(row, 0));
        String uName = String.valueOf(jTable1.getValueAt(row, 1));
        String role = String.valueOf(jTable1.getValueAt(row, 2));

        // Display uNIC and uName in the text field (concatenated)
        roundedTextFeild2.setText(uNIC + " - " + uName);

        // Set the selected item for the combo box
        roundedCombobox1.setSelectedItem(role);


    }//GEN-LAST:event_jTable1MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Controls.card card1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private Controls.RoundedBotton roundedBotton2;
    private Controls.RoundedBotton roundedBotton3;
    private Controls.roundedCombobox roundedCombobox1;
    private Controls.RoundedTextFeild roundedTextFeild1;
    private Controls.RoundedTextFeild roundedTextFeild2;
    // End of variables declaration//GEN-END:variables
}
