package GUI;

import MYSQL.mysql;
import java.awt.Color;
import java.awt.Font;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import raven.toast.Notifications;

public class classR extends javax.swing.JPanel {

    private HashMap<String, Integer> Year = new HashMap<>();
    private HashMap<String, Integer> Stream = new HashMap<>();
    private HashMap<String, Integer> sub = new HashMap<>();
    private HashMap<String, Integer> teacherN = new HashMap<>();

    private HashMap<String, Integer> t1 = new HashMap<>();
    private HashMap<String, Integer> t2 = new HashMap<>();
    private HashMap<String, Integer> amp = new HashMap<>();
    private HashMap<String, Integer> weekd = new HashMap<>();

    public classR() {
        initComponents();
        loadStream();
        loadT();
        loadsub();
        loadyear();
        loadT1();
        loadT2();
        loadweek();
        loadap();
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
    private void loadT1() {

        try {

            ResultSet resultSet = mysql.executeSearch("SELECT * FROM `timeslot1`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                t1.put(resultSet.getString("name"), resultSet.getInt("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            roundedCombobox5.setModel(model);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void loadT2() {

        try {

            ResultSet resultSet = mysql.executeSearch("SELECT * FROM `timeslot2`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                t2.put(resultSet.getString("name"), resultSet.getInt("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            roundedCombobox8.setModel(model);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void loadweek() {

        try {

            ResultSet resultSet = mysql.executeSearch("SELECT * FROM `weekdays`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                weekd.put(resultSet.getString("name"), resultSet.getInt("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            roundedCombobox4.setModel(model);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void loadap() {

        try {

            ResultSet resultSet = mysql.executeSearch("SELECT * FROM `ampm`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                amp.put(resultSet.getString("name"), resultSet.getInt("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            roundedCombobox6.setModel(model);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void loadT() {

        try {

            ResultSet resultSet = mysql.executeSearch("SELECT * FROM `teacher`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("Name"));
                teacherN.put(resultSet.getString("Name"), resultSet.getInt("Tno"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            roundedCombobox3.setModel(model);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void loadsub() {

        try {

            ResultSet resultSet = mysql.executeSearch("SELECT * FROM `subject`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("Description"));
                sub.put(resultSet.getString("Description"), resultSet.getInt("Subno"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            roundedCombobox7.setModel(model);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void loadyear() {

        try {

            ResultSet resultSet = mysql.executeSearch("SELECT * FROM `al year`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                Year.put(resultSet.getString("name"), resultSet.getInt("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            roundedCombobox2.setModel(model);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    private void loadStream() {

        try {

            ResultSet resultSet = mysql.executeSearch("SELECT * FROM `stream`");

            Vector<String> vector = new Vector<>();
            vector.add("Select");

            while (resultSet.next()) {
                vector.add(resultSet.getString("name"));
                Stream.put(resultSet.getString("name"), resultSet.getInt("id"));
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(vector);
            roundedCombobox1.setModel(model);

        } catch (Exception e) {

            e.printStackTrace();

        }

    }

    public void insertData() {

        try {

            String Cname = roundedTextFeild1.getText();
            String Sstream = String.valueOf(roundedCombobox1.getSelectedItem());
            String alyear = String.valueOf(roundedCombobox2.getSelectedItem());
            String Tname = String.valueOf(roundedCombobox3.getSelectedItem());
            String weekday = String.valueOf(roundedCombobox4.getSelectedItem());
            String h1 = String.valueOf(roundedCombobox5.getSelectedItem());
            String h2 = String.valueOf(roundedCombobox8.getSelectedItem());
            String ampm = String.valueOf(roundedCombobox6.getSelectedItem());
            String classfee = roundedTextFeild2.getText();
            String subject = String.valueOf(roundedCombobox7.getSelectedItem());

            if (Cname.isEmpty()) {

                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Product type cannot be empty.");

            } else if (Sstream.equals("Select") || Sstream.isEmpty()) {

                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Product type cannot be empty.");

            } else if (alyear.equals("Select") || alyear.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Product type cannot be empty.");
            } else if (Tname.equals("Select") || Tname.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Product type cannot be empty.");
            } else if (weekday.equals("Select") || weekday.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Product type cannot be empty.");
            } else if (h1.equals("Select") || h1.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Product type cannot be empty.");
            } else if (h2.equals("Select") || h2.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Product type cannot be empty.");
            } else if (ampm.equals("Select") || ampm.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Product type cannot be empty.");
            } else if (classfee.isEmpty() || classfee.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Product type cannot be empty.");
            } else if (subject.isEmpty()) {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Product type cannot be empty.");
            } else {

                mysql.executeIUD("INSERT INTO `class` (`Class name`,`weekdays_id`,`timeslot1_id`,`timeslot2_id`,`ampm_id`,`Class Fee`,`Subject_Subno`,`Teacher_Tno`,`Stream_id`,`AL year_id`)"
                        + "VALUES('" + Cname + "','" + weekd.get(weekday) + "','" + t1.get(h1) + "','" + t2.get(h2) + "','" + amp.get(ampm) + "',"
                        + "'" + classfee + "','" + sub.get(subject) + "','" + teacherN.get(Tname) + "','" + Stream.get(Sstream) + "','" + Year.get(alyear) + "')");

                System.out.println("sss");
                loadEmployees();
            }
        } catch (Exception e) {

            e.printStackTrace();
        }

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

                vector.add(resultSet.getString("Class name"));
                vector.add(resultSet.getString("subject.Description"));
                vector.add(resultSet.getString("stream.name"));
                vector.add(resultSet.getString("al year.name"));
                vector.add(resultSet.getString("teacher.name"));
                vector.add(resultSet.getString("weekdays.name"));
                vector.add(resultSet.getString("timeslot1.name"));
                vector.add(resultSet.getString("timeslot2.name"));
                vector.add(resultSet.getString("ampm.name"));
                vector.add(resultSet.getString("Class Fee"));

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
        jLabel4 = new javax.swing.JLabel();
        roundedTextFeild1 = new Controls.RoundedTextFeild();
        jLabel1 = new javax.swing.JLabel();
        roundedCombobox1 = new Controls.roundedCombobox();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        roundedCombobox2 = new Controls.roundedCombobox();
        jLabel5 = new javax.swing.JLabel();
        roundedCombobox3 = new Controls.roundedCombobox();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        roundedCombobox4 = new Controls.roundedCombobox();
        jLabel7 = new javax.swing.JLabel();
        roundedCombobox5 = new Controls.roundedCombobox();
        roundedCombobox6 = new Controls.roundedCombobox();
        jLabel8 = new javax.swing.JLabel();
        roundedCombobox8 = new Controls.roundedCombobox();
        jLabel11 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        roundedTextFeild2 = new Controls.RoundedTextFeild();
        jLabel10 = new javax.swing.JLabel();
        roundedCombobox7 = new Controls.roundedCombobox();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        roundedBotton3 = new Controls.RoundedBotton();
        roundedBotton4 = new Controls.RoundedBotton();
        roundedBotton5 = new Controls.RoundedBotton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        card1.setOpaque(false);
        card1.setPreferredSize(new java.awt.Dimension(885, 40));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("ADD CLASS");

        javax.swing.GroupLayout card1Layout = new javax.swing.GroupLayout(card1);
        card1.setLayout(card1Layout);
        card1Layout.setHorizontalGroup(
            card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addContainerGap(975, Short.MAX_VALUE))
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

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setText("Class Name");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Subject Stream");

        roundedCombobox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "Science Stream" }));
        roundedCombobox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedCombobox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(41, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(roundedTextFeild1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(roundedCombobox1, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roundedCombobox1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roundedTextFeild1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setLayout(new java.awt.BorderLayout());

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("AL Year");

        roundedCombobox2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "Science Stream" }));
        roundedCombobox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedCombobox2ActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel5.setText("Teacher Name");

        roundedCombobox3.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "Science Stream" }));
        roundedCombobox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedCombobox3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(44, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(roundedCombobox2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 124, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(roundedCombobox3, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roundedCombobox3, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roundedCombobox2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel4, java.awt.BorderLayout.PAGE_START);

        jPanel5.setLayout(new java.awt.BorderLayout());

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel6.setText("Week Days");

        roundedCombobox4.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday", " " }));
        roundedCombobox4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedCombobox4ActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Hours");

        roundedCombobox5.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        roundedCombobox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedCombobox5ActionPerformed(evt);
            }
        });

        roundedCombobox6.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "am", "pm" }));
        roundedCombobox6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedCombobox6ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel8.setText("AM / PM");

        roundedCombobox8.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12" }));
        roundedCombobox8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedCombobox8ActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel11.setText("Hours");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(45, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(roundedCombobox4, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(roundedCombobox5, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addComponent(roundedCombobox8, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(roundedCombobox6, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roundedCombobox4, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(roundedCombobox5, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(roundedCombobox6, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(roundedCombobox8, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel5.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jPanel7.setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("Class fee (Rs)");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("Subject ");

        roundedCombobox7.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Select", "Science Stream" }));
        roundedCombobox7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedCombobox7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap(46, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(roundedTextFeild2, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 121, Short.MAX_VALUE)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(roundedCombobox7, javax.swing.GroupLayout.PREFERRED_SIZE, 430, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roundedTextFeild2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roundedCombobox7, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jPanel7.add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jPanel9.setLayout(new java.awt.BorderLayout());

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setPreferredSize(new java.awt.Dimension(930, 50));

        roundedBotton3.setBackground(new java.awt.Color(0, 153, 0));
        roundedBotton3.setForeground(new java.awt.Color(255, 255, 255));
        roundedBotton3.setText("Add Class");
        roundedBotton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedBotton3ActionPerformed(evt);
            }
        });

        roundedBotton4.setBackground(new java.awt.Color(255, 51, 51));
        roundedBotton4.setForeground(new java.awt.Color(255, 255, 255));
        roundedBotton4.setText("Delete Class");

        roundedBotton5.setBackground(new java.awt.Color(255, 255, 204));
        roundedBotton5.setText("Update Class");

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(roundedBotton3, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(260, 260, 260)
                .addComponent(roundedBotton5, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 261, Short.MAX_VALUE)
                .addComponent(roundedBotton4, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(roundedBotton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roundedBotton4, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roundedBotton5, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel9.add(jPanel10, java.awt.BorderLayout.PAGE_END);

        jPanel11.setLayout(new java.awt.BorderLayout());

        jTable1.setFont(new java.awt.Font("Century Gothic", 1, 12)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Class Name", "Subject Name", "Subject Stream", "AL Year", "Teacher", "Weekday", "Hours", "Hours", "Am/Pm", "Class fee"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jPanel11.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel9.add(jPanel11, java.awt.BorderLayout.CENTER);

        jPanel7.add(jPanel9, java.awt.BorderLayout.CENTER);

        jPanel5.add(jPanel7, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel5, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void roundedCombobox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedCombobox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundedCombobox1ActionPerformed

    private void roundedCombobox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedCombobox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundedCombobox2ActionPerformed

    private void roundedCombobox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedCombobox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundedCombobox3ActionPerformed

    private void roundedCombobox4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedCombobox4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundedCombobox4ActionPerformed

    private void roundedCombobox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedCombobox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundedCombobox5ActionPerformed

    private void roundedCombobox6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedCombobox6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundedCombobox6ActionPerformed

    private void roundedCombobox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedCombobox7ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundedCombobox7ActionPerformed

    private void roundedCombobox8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedCombobox8ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundedCombobox8ActionPerformed

    private void roundedBotton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedBotton3ActionPerformed

        insertData();
    }//GEN-LAST:event_roundedBotton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Controls.card card1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private Controls.RoundedBotton roundedBotton3;
    private Controls.RoundedBotton roundedBotton4;
    private Controls.RoundedBotton roundedBotton5;
    private Controls.roundedCombobox roundedCombobox1;
    private Controls.roundedCombobox roundedCombobox2;
    private Controls.roundedCombobox roundedCombobox3;
    private Controls.roundedCombobox roundedCombobox4;
    private Controls.roundedCombobox roundedCombobox5;
    private Controls.roundedCombobox roundedCombobox6;
    private Controls.roundedCombobox roundedCombobox7;
    private Controls.roundedCombobox roundedCombobox8;
    private Controls.RoundedTextFeild roundedTextFeild1;
    private Controls.RoundedTextFeild roundedTextFeild2;
    // End of variables declaration//GEN-END:variables
}
