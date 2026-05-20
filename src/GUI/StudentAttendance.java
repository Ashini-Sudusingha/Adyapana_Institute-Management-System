package GUI;

import MYSQL.mysql;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.Timer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import raven.toast.Notifications;

public class StudentAttendance extends javax.swing.JPanel {
    
    private static HashMap<String, String> SelectClassMap = new HashMap();
    
    public StudentAttendance() {
        initComponents();
        setupAutoComplete();
        //    attendanceLoad();
        attendanceCount();
        svgload();
        theader1();
    }
    
       private void theader1() {

        JTableHeader thead = jTable2.getTableHeader();

        thead.setForeground((new Color(255, 255, 255)));

        thead.setBackground(new Color(0, 0, 0));

        thead.setFont(new Font("Arial", Font.BOLD, 12));

        TableColumn coll = jTable2.getColumnModel().getColumn(0);

        coll.setPreferredWidth(100);
    }
    private void svgload() {
        int iconWidth = 45;
        int iconHeight = 45;
        
        int iconW = 45;
        int iconH = 45;
        
        FlatSVGIcon credit = new FlatSVGIcon("Resourse/student.svg", iconWidth, iconHeight);
        jLabel20.setIcon(credit);
        
        FlatSVGIcon teacher = new FlatSVGIcon("Resourse/class.svg", iconW, iconH);
        jLabel19.setIcon(teacher);
    }
    
    private DefaultTableModel model;
    
    private void attendanceLoad(String date) {
        
        try {
            
            ResultSet resultSet = mysql.executeSearch("Select * FROM `attendance`"
                    + "INNER JOIN `student` ON `attendance`.`class_has_student_student_Sno`=`student`.`Sno`"
                    + "INNER JOIN `class` ON `attendance`.`class_has_student_class_Classno` = `class`.`Classno`"
                    + "INNER JOIN `attendance_type` ON `attendance`.`attendance_type_id` =`attendance_type`.`id`"
                    + "WHERE `attendance`.`date`= '" + date + "' AND "
                    + "`attendance`.`class_has_student_class_Classno`='" + classId + "'");
            
            model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);
            
            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("student.Sno"));
                vector.add(resultSet.getString("student.Name"));
                vector.add(resultSet.getString("class.Class name"));
                vector.add(resultSet.getString("attendance.check_in"));
                vector.add(resultSet.getString("attendance_type.name"));
                model.addRow(vector);
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    private String classId = "";
    
    private void setupAutoComplete() {
        JPopupMenu suggestionMenu = new JPopupMenu();
        suggestionMenu.setPreferredSize(new Dimension(430, 200)); // Adjust width and height as needed

        Timer timer = new Timer(1000, null); // 5000 milliseconds (5 seconds) delay
        timer.setRepeats(false); // Ensure the timer only triggers once per input

        jTextField1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                timer.restart(); // Restart the timer on each key release
                timer.setActionCommand(jTextField1.getText().trim()); // Store the current input

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
                                String[] parts = suggestion.replace("(", "").replace(")", "").replace(")", "").split("/");
                                if (parts.length >= 2) {
                                    jTextField1.setText(parts[0].trim());
                                    classId = parts[4].trim();
                                    // System.out.println(classId);// Set only the 'nic'
                                }
                                suggestionMenu.setVisible(false);
                            });
                            
                            suggestionMenu.add(item);
                        }
                        
                        suggestionMenu.show(jTextField1, 0, jTextField1.getHeight());
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
                    "SELECT * "
                    + "FROM `class` "
                    + "INNER JOIN `teacher` ON `class`.`Teacher_Tno`= `teacher`.`Tno`"
                    + "INNER JOIN `al year` ON class.`AL year_id` = `al year`.`id`"
                    + "WHERE `class`.`Class name` LIKE '%s%%' "
                    + "OR `al year`.`name` LIKE '%s%%' "
                    + "OR `teacher`.`Name` LIKE '%s%%' "
                    + "LIMIT 10",
                    input, input, input
            );
            
            ResultSet resultSet = mysql.executeSearch(query);
            
            while (resultSet.next()) {
                String name = resultSet.getString("class.Class Name");
                String nic = resultSet.getString("teacher.name");
                String stuID = resultSet.getString("al year.name");
                String classID = resultSet.getString("class.Classno");
                System.out.println("hera arrt" + classID);
                suggestions.add(String.format("(%s/%s/%s/%s)",
                        name != null ? name : "-",
                        nic != null ? nic : "-",
                        stuID != null ? stuID : "-",
                        classID != null ? classID : "-"
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return suggestions;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        card2 = new Controls.card();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        roundedBotton1 = new Controls.RoundedBotton();
        jLabel21 = new javax.swing.JLabel();
        flatroundedComboBox1 = new Controls.FlatroundedComboBox();
        jLabel22 = new javax.swing.JLabel();
        flatroundedComboBox2 = new Controls.FlatroundedComboBox();
        roundedBotton3 = new Controls.RoundedBotton();
        jPanel8 = new javax.swing.JPanel();
        roundedPanel1 = new Controls.RoundedPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jFormattedTextField1 = new javax.swing.JFormattedTextField();
        jLabel3 = new javax.swing.JLabel();
        roundedPanel3 = new Controls.RoundedPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        roundedPanel4 = new Controls.RoundedPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        roundedPanel2 = new Controls.RoundedPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        roundedBotton5 = new Controls.RoundedBotton();
        roundedBotton2 = new Controls.RoundedBotton();
        roundedBotton4 = new Controls.RoundedBotton();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setLayout(new java.awt.BorderLayout());

        card2.setMinimumSize(new java.awt.Dimension(100, 0));
        card2.setOpaque(false);
        card2.setPreferredSize(new java.awt.Dimension(913, 40));

        javax.swing.GroupLayout card2Layout = new javax.swing.GroupLayout(card2);
        card2.setLayout(card2Layout);
        card2Layout.setHorizontalGroup(
            card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1250, Short.MAX_VALUE)
        );
        card2Layout.setVerticalGroup(
            card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 40, Short.MAX_VALUE)
        );

        add(card2, java.awt.BorderLayout.PAGE_START);

        jPanel1.setLayout(new java.awt.BorderLayout());

        jPanel2.setPreferredSize(new java.awt.Dimension(913, 200));
        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel7.setPreferredSize(new java.awt.Dimension(1010, 80));

        jLabel1.setText("Search(Class Name/ Teacher Name/ Year) :");

        jTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        roundedBotton1.setBackground(new java.awt.Color(102, 0, 102));
        roundedBotton1.setForeground(new java.awt.Color(255, 255, 255));
        roundedBotton1.setText("Start Class Mark");
        roundedBotton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedBotton1ActionPerformed(evt);
            }
        });

        jLabel21.setText("Month ");

        jLabel22.setText("Year");

        roundedBotton3.setBackground(new java.awt.Color(0, 102, 102));
        roundedBotton3.setForeground(new java.awt.Color(255, 255, 255));
        roundedBotton3.setText("Start attendance Mark");
        roundedBotton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedBotton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 575, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roundedBotton1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(406, 406, 406)))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(130, 130, 130)
                        .addComponent(jLabel22))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(flatroundedComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(flatroundedComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(roundedBotton3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap(8, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel21)
                        .addComponent(jLabel22)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roundedBotton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(flatroundedComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(flatroundedComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(roundedBotton3, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13))
        );

        jPanel2.add(jPanel7, java.awt.BorderLayout.PAGE_START);

        jPanel8.setLayout(new java.awt.BorderLayout());

        roundedPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel7.setText("Start");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setText("00:00");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel9.setText("to");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel10.setText("End");

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel15.setText("00:00");

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Day");

        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(51, 51, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Name");

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel17.setText("Student Id:");

        jFormattedTextField1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jFormattedTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jFormattedTextField1KeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Yu Gothic UI", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Payment");

        jLabel4.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel4.setText("Class Name");

        jLabel6.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel6.setText("Sir Name");

        jLabel5.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Year");

        jLabel18.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel18.setText("Sbject");

        javax.swing.GroupLayout roundedPanel3Layout = new javax.swing.GroupLayout(roundedPanel3);
        roundedPanel3.setLayout(roundedPanel3Layout);
        roundedPanel3Layout.setHorizontalGroup(
            roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(roundedPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(76, Short.MAX_VALUE))
        );
        roundedPanel3Layout.setVerticalGroup(
            roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel3Layout.createSequentialGroup()
                .addGroup(roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundedPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addGap(7, 7, 7)
                        .addGroup(roundedPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel18)))
                    .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout roundedPanel1Layout = new javax.swing.GroupLayout(roundedPanel1);
        roundedPanel1.setLayout(roundedPanel1Layout);
        roundedPanel1Layout.setHorizontalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel17)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 316, Short.MAX_VALUE)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(145, 145, 145)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel15))
                    .addComponent(roundedPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        roundedPanel1Layout.setVerticalGroup(
            roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel1Layout.createSequentialGroup()
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(roundedPanel1Layout.createSequentialGroup()
                        .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3)))
                            .addGroup(roundedPanel1Layout.createSequentialGroup()
                                .addGap(42, 42, 42)
                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jFormattedTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(roundedPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(roundedPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(jLabel16))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel8.add(roundedPanel1, java.awt.BorderLayout.PAGE_END);

        jPanel2.add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel2, java.awt.BorderLayout.PAGE_START);

        jPanel3.setLayout(new java.awt.BorderLayout());

        roundedPanel4.setBackground(new java.awt.Color(255, 204, 204));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setText("Absendt");

        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setText("0");

        javax.swing.GroupLayout roundedPanel4Layout = new javax.swing.GroupLayout(roundedPanel4);
        roundedPanel4.setLayout(roundedPanel4Layout);
        roundedPanel4Layout.setHorizontalGroup(
            roundedPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, roundedPanel4Layout.createSequentialGroup()
                .addContainerGap(31, Short.MAX_VALUE)
                .addGroup(roundedPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(32, 32, 32))
        );
        roundedPanel4Layout.setVerticalGroup(
            roundedPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                .addContainerGap())
        );

        roundedPanel2.setBackground(new java.awt.Color(204, 255, 204));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel2.setText("Present");

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("0");

        javax.swing.GroupLayout roundedPanel2Layout = new javax.swing.GroupLayout(roundedPanel2);
        roundedPanel2.setLayout(roundedPanel2Layout);
        roundedPanel2Layout.setHorizontalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        roundedPanel2Layout.setVerticalGroup(
            roundedPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(roundedPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTextField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextField2KeyReleased(evt);
            }
        });

        roundedBotton5.setBackground(new java.awt.Color(204, 0, 0));
        roundedBotton5.setForeground(new java.awt.Color(255, 255, 255));
        roundedBotton5.setText("End Marked Attendance");
        roundedBotton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedBotton5ActionPerformed(evt);
            }
        });

        roundedBotton2.setText("Reset");
        roundedBotton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedBotton2ActionPerformed(evt);
            }
        });

        roundedBotton4.setBackground(new java.awt.Color(204, 204, 255));
        roundedBotton4.setText("Teacher Attend");
        roundedBotton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedBotton4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(114, 114, 114)
                .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(roundedPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 185, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(roundedBotton4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(roundedBotton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(roundedBotton5, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(roundedPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(roundedBotton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(roundedBotton4, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(roundedBotton5, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(roundedPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel5, java.awt.BorderLayout.PAGE_START);

        jPanel6.setLayout(new java.awt.BorderLayout());

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Student ID", "Name", "Class", "Check In", "Status", "Payment"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable2MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable2);

        jPanel6.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel3.add(jPanel6, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel3, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents
private String teacherId;
    private void roundedBotton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedBotton1ActionPerformed
        try {
            jTextField1.setText("");
            ResultSet resultSet = mysql.executeSearch("SELECT * FROM `class` "
                    + "INNER JOIN `teacher` ON `class`.`Teacher_Tno`= `teacher`.`Tno`"
                    + "INNER JOIN `al year` ON `class`.`AL year_id`= `al year`.`id`"
                    + "INNER JOIN `timeslot1` ON `class`.`timeslot1_id`=`timeslot1`.`id`"
                    + "INNER JOIN `timeslot2` ON `class`.`timeslot2_id`=`timeslot2`.`id`"
                    + "WHERE `class`.`Classno`= '" + classId + "'");
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (resultSet.next()) {
                jLabel4.setText(resultSet.getString("class.Class name"));
                jLabel5.setText(resultSet.getString("al year.name"));
                jLabel6.setText(resultSet.getString("teacher.Name"));
                jLabel8.setText(resultSet.getString("timeslot1.name"));
                jLabel15.setText(resultSet.getString("timeslot2.name"));
                jLabel16.setText(sdf.format(date));
                teacherId = resultSet.getString("teacher.Tno");
            }
            jFormattedTextField1.grabFocus();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_roundedBotton1ActionPerformed

    private void roundedBotton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedBotton2ActionPerformed
        roundedBotton2.setText("");
        jTextField1.grabFocus();
    }//GEN-LAST:event_roundedBotton2ActionPerformed

    private void jFormattedTextField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextField1KeyReleased
        try {
            
            if (!classId.isEmpty()) {
                
                Date date = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                
                String studentID = jFormattedTextField1.getText();
                
                ResultSet resultSet = mysql.executeSearch("SELECT * FROM `class_has_student` "
                        + "INNER JOIN `student` ON `class_has_student`.`student_Sno` = `student`.`Sno`"
                        + "INNER JOIN `class` ON `class_has_student`.`class_Classno`=`class`.`Classno`"
                        + "WHERE `class_Classno`='" + classId + "' AND `student_Sno` = '" + studentID + "'");
                
                if (resultSet.next()) {
                    
                    ResultSet resultSet1 = mysql.executeSearch("SELECT * FROM `attendance` "
                            + "WHERE `class_has_student_student_Sno`='" + studentID + "' AND"
                            + "`class_has_student_class_Classno`='" + classId + "' AND"
                            + "`date`='" + sdf.format(date) + "'");
                    if (resultSet1.next()) {
                        Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "This student already exsist!");
                        attendanceLoad(sdf.format(date));
                        attendanceCount();
                    } else {
                        // markedDate = sdf.parse(sdf.format(date));//set date
                        Date time = new Date();
                        SimpleDateFormat sdfT = new SimpleDateFormat("HH:mm:ss");
                        jLabel14.setText(resultSet.getString("student.Name"));
                        
                        mysql.executeIUD("INSERT INTO `attendance` (`date`,`check_in`,`class_has_student_class_Classno`,"
                                + "`class_has_student_student_Sno`,attendance_type_id) VALUES"
                                + "('" + sdf.format(date) + "','" + sdfT.format(time) + "','" + classId + "',"
                                + "'" + studentID + "','1')");
                        attendanceCount();
                        attendanceLoad(sdf.format(date));
                        jFormattedTextField1.setText("");
                        jFormattedTextField1.grabFocus();
                        Notifications.getInstance().show(Notifications.Type.SUCCESS, Notifications.Location.TOP_RIGHT, "Marked attendance successfully!");
                        
                    }
                } else {
                    Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "This Student to register in this class!");
                }
            } else {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "Select Class befor marked attendance!");
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_jFormattedTextField1KeyReleased

    private void jTable2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MouseClicked
        int row = jTable2.getSelectedRow();
        
        if (evt.getClickCount() == 2) {
            Payment payment = new Payment(null, true, this, String.valueOf(jTable2.getValueAt(row, 0)));
            payment.setclassID(classId);
            payment.setVisible(true);
            
        }
        

    }//GEN-LAST:event_jTable2MouseClicked
    
    String endattendance = null;

    private void roundedBotton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedBotton5ActionPerformed
        try {
            Date endtime = new Date();
            SimpleDateFormat sdfT = new SimpleDateFormat("HH:mm:ss");
            endattendance = sdfT.format(endtime);
            
            Date date = new Date();
            SimpleDateFormat datesdf = new SimpleDateFormat("yyyy-MM-dd");
            
            ResultSet resultSet = mysql.executeSearch("SELECT * FROM `class_has_student` "
                    + "LEFT JOIN `attendance` ON `class_has_student`.`student_Sno`=`attendance`.`class_has_student_student_Sno`"
                    + "AND `attendance`.`date` = '" + datesdf.format(date) + "'AND `attendance`.`class_has_student_class_Classno`='" + classId + "' "
                    + "WHERE `attendance`.`class_has_student_student_Sno` IS NULL  AND `class_has_student`.`class_Classno` = '3'");
            int count = 0;
            while (resultSet.next()) {
                mysql.executeIUD("INSERT INTO `attendance`(`date`,`check_in`,`class_has_student_class_Classno`,`class_has_student_student_Sno`,`attendance_type_id`)"
                        + "VALUES ('" + datesdf.format(date) + "','00:00:00','" + classId + "',"
                        + "'" + resultSet.getString("class_has_student.student_Sno") + "','2')");
                count++;
            }
            
            jLabel12.setText(String.valueOf(count));
            attendanceLoad(datesdf.format(date));
            
            ResultSet resultSet1 = mysql.executeSearch("SELECT * FROM `attendance` WHERE `date` = '" + datesdf.format(date) + "'"
                    + "AND `class_has_student_class_Classno` ='" + classId + "' AND `attendance_type`='1'");
            int presentcount = 0;
            while (resultSet1.next()) {
                presentcount++;
            }
            System.out.println(presentcount);
            jLabel13.setText(String.valueOf(presentcount));
            //end class
            mysql.executeIUD("UPDATE `class_record` SET `end_attendance`= '" + endattendance + "' "
                    + "WHERE `date`='" + datesdf.format(date) + "' AND `class_Classno`='" + classId + "' ");
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_roundedBotton5ActionPerformed
    
    String startAttendance = null;
    private void roundedBotton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedBotton3ActionPerformed
        try {
            Date startA = new Date();
            SimpleDateFormat astartsdf = new SimpleDateFormat("HH:mm:ss");
            startAttendance = astartsdf.format(startA);
            
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            ResultSet resultSet1 = mysql.executeSearch("SELECT * FROM `class_record` WHERE "
                    + "`class_ClassNo` = '" + classId + "' AND `date`='" + sdf.format(date) + "' ");
            
            if (!resultSet1.next()) {
                
                mysql.executeIUD("INSERT INTO `class_record` (`start_attendance`,`end_attendance`,"
                        + "`date`,`teacher_attendance`,`teacher_Tno`,`class_Classno`)"
                        + "VALUES ('" + startAttendance + "','00:00:00','" + sdf.format(date) + "',"
                        + "'00:00:00','" + teacherId + "','" + classId + "')");
                
            } else {
                Notifications.getInstance().show(Notifications.Type.WARNING, Notifications.Location.TOP_RIGHT, "This Class Already Start!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_roundedBotton3ActionPerformed
    private String teacherA = null;
    private void roundedBotton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedBotton4ActionPerformed
        try {
            Date startA = new Date();
            SimpleDateFormat astartsdf = new SimpleDateFormat("HH:mm:ss");
            teacherA = astartsdf.format(startA);
            
            Date date = new Date();
            SimpleDateFormat datesdf = new SimpleDateFormat("yyyy-MM-dd");
            
            mysql.executeIUD("UPDATE `class_record` SET `teacher_attendance`= '" + teacherA + "' "
                    + "WHERE `date`='" + datesdf.format(date) + "' AND `class_Classno`='" + classId + "' ");
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_roundedBotton4ActionPerformed

    private void jTextField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField2KeyReleased
        
        try {//WHERE 
            String searchText = jTextField2.getText();
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            ResultSet resultSet = mysql.executeSearch("Select * FROM `attendance`"
                    + "INNER JOIN `student` ON `attendance`.`class_has_student_student_Sno`=`student`.`Sno`"
                    + "INNER JOIN `class` ON `attendance`.`class_has_student_class_Classno` = `class`.`Classno`"
                    + "INNER JOIN `attendance_type` ON `attendance`.`attendance_type_id` =`attendance_type`.`id`"
                    + "WHERE `attendance`.`date`= '" + sdf.format(date) + "' AND "
                    + "`attendance`.`class_has_student_class_Classno`='" + classId + "' AND `student`.`Name` LIKE '%\" + searchText + \"%' ");
            
            model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);
            
            while (resultSet.next()) {
                Vector<String> vector = new Vector<>();
                vector.add(resultSet.getString("student.Sno"));
                vector.add(resultSet.getString("student.Name"));
                vector.add(resultSet.getString("class.Class name"));
                vector.add(resultSet.getString("attendance.check_in"));
                vector.add(resultSet.getString("attendance_type.name"));
                model.addRow(vector);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }

    }//GEN-LAST:event_jTextField2KeyReleased
    
    private void resetAll() throws ParseException {
        classId = "0";
        
        Date resetdate = new Date(0000);
        SimpleDateFormat resetsdf = new SimpleDateFormat("yyyy-MM-dd");
        // markedDate = resetsdf.parse(resetsdf.format(resetdate));

        jLabel4.setText("");
        jLabel5.setText("");
        jLabel6.setText("");
        jLabel8.setText("");
        jLabel15.setText("");
        
    }
    
    private void newClassAttendanceOpen() throws ParseException {
        classId = "0";
        
        Date resetdate = new Date(0000);
        SimpleDateFormat resetsdf = new SimpleDateFormat("yyyy-MM-dd");
        //markedDate = resetsdf.parse(resetsdf.format(resetdate));
        //   model.setRowCount(-1);

        jLabel4.setText("");
        jLabel5.setText("");
        jLabel6.setText("");
        jLabel8.setText("");
        jLabel15.setText("");
        
    }
    
    private void attendanceCount() {
        try {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            
            ResultSet resultSet = mysql.executeSearch("SELECT * FROM `attendance` WHERE `date`='" + sdf.format(date) + "'"
                    + "AND `class_has_student_class_Classno`='" + classId + "' AND `attendance_type_id`= '1' ");
            
            int present = 0;
            while (resultSet.next()) {
                present++;
            }
            jLabel13.setText(String.valueOf(present));

            //Absence
            ResultSet resultSet1 = mysql.executeSearch("SELECT * FROM `attendance` WHERE `date`='" + sdf.format(date) + "'"
                    + "AND `class_has_student_class_Classno`='" + classId + "' AND `attendance_type_id`= '2' ");
            
            int abcence = 0;
            while (resultSet1.next()) {
                abcence++;
            }
            jLabel12.setText(String.valueOf(abcence));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Controls.card card2;
    private Controls.FlatroundedComboBox flatroundedComboBox1;
    private Controls.FlatroundedComboBox flatroundedComboBox2;
    private javax.swing.JFormattedTextField jFormattedTextField1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private Controls.RoundedBotton roundedBotton1;
    private Controls.RoundedBotton roundedBotton2;
    private Controls.RoundedBotton roundedBotton3;
    private Controls.RoundedBotton roundedBotton4;
    private Controls.RoundedBotton roundedBotton5;
    private Controls.RoundedPanel roundedPanel1;
    private Controls.RoundedPanel roundedPanel2;
    private Controls.RoundedPanel roundedPanel3;
    private Controls.RoundedPanel roundedPanel4;
    // End of variables declaration//GEN-END:variables
}
