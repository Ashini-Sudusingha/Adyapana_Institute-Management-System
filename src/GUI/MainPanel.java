package GUI;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import raven.toast.Notifications;

public class MainPanel extends javax.swing.JFrame {

    public MainPanel() {
        initComponents();
        Notifications.getInstance().setJFrame(this);
        svgload();
    }
 private void svgload() {
        int iconWidth = 45;
        int iconHeight = 45;


        FlatSVGIcon credit = new FlatSVGIcon("GUI/main.svg", iconWidth, iconHeight);
        jLabel1.setIcon(credit);

    
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        card2 = new Controls.card();
        card21 = new Controls.card2();
        jLabel1 = new javax.swing.JLabel();
        card1 = new Controls.card();
        jLabel2 = new javax.swing.JLabel();
        roundedBotton2 = new Controls.RoundedBotton();
        roundedBotton3 = new Controls.RoundedBotton();
        roundedBotton4 = new Controls.RoundedBotton();
        roundedBotton5 = new Controls.RoundedBotton();
        roundedBotton6 = new Controls.RoundedBotton();
        roundedBotton7 = new Controls.RoundedBotton();
        roundedBotton8 = new Controls.RoundedBotton();
        jLabel3 = new javax.swing.JLabel();
        roundedBotton10 = new Controls.RoundedBotton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(275, 606));
        jPanel1.setLayout(new java.awt.BorderLayout());

        card2.setOpaque(false);
        card2.setLayout(new java.awt.BorderLayout());

        card21.setForeground(new java.awt.Color(255, 255, 255));
        card21.setOpaque(false);

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Yu Gothic UI Semibold", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Adyapana Institute");

        javax.swing.GroupLayout card21Layout = new javax.swing.GroupLayout(card21);
        card21.setLayout(card21Layout);
        card21Layout.setHorizontalGroup(
            card21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(card21Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(25, 25, 25))
        );
        card21Layout.setVerticalGroup(
            card21Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, card21Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1))
        );

        card2.add(card21, java.awt.BorderLayout.PAGE_START);

        card1.setOpaque(false);
        card1.setLayout(new java.awt.GridLayout(12, 1, 5, 5));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("MAIN");
        card1.add(jLabel2);

        roundedBotton2.setForeground(new java.awt.Color(0, 33, 161));
        roundedBotton2.setText("STUDENT REGISTRATION");
        roundedBotton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedBotton2ActionPerformed(evt);
            }
        });
        card1.add(roundedBotton2);

        roundedBotton3.setForeground(new java.awt.Color(0, 33, 161));
        roundedBotton3.setText("SUBJECT REGISTRATION");
        roundedBotton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedBotton3ActionPerformed(evt);
            }
        });
        card1.add(roundedBotton3);

        roundedBotton4.setForeground(new java.awt.Color(0, 33, 161));
        roundedBotton4.setText("CLASS REGISTRATION");
        roundedBotton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedBotton4ActionPerformed(evt);
            }
        });
        card1.add(roundedBotton4);

        roundedBotton5.setForeground(new java.awt.Color(0, 33, 161));
        roundedBotton5.setText("STUDENTS PAYMENTS");
        roundedBotton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedBotton5ActionPerformed(evt);
            }
        });
        card1.add(roundedBotton5);

        roundedBotton6.setForeground(new java.awt.Color(0, 33, 161));
        roundedBotton6.setText("STUDENT ATTENDENCE");
        roundedBotton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedBotton6ActionPerformed(evt);
            }
        });
        card1.add(roundedBotton6);

        roundedBotton7.setForeground(new java.awt.Color(0, 33, 161));
        roundedBotton7.setText("STUDENT ENROLLMENTS");
        roundedBotton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedBotton7ActionPerformed(evt);
            }
        });
        card1.add(roundedBotton7);

        roundedBotton8.setForeground(new java.awt.Color(0, 33, 161));
        roundedBotton8.setText("TEACHER ENROLMENTS");
        roundedBotton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedBotton8ActionPerformed(evt);
            }
        });
        card1.add(roundedBotton8);

        jLabel3.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("SETTING");
        card1.add(jLabel3);

        roundedBotton10.setForeground(new java.awt.Color(0, 33, 161));
        roundedBotton10.setText("LOGOUT");
        roundedBotton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedBotton10ActionPerformed(evt);
            }
        });
        card1.add(roundedBotton10);

        card2.add(card1, java.awt.BorderLayout.CENTER);

        jPanel1.add(card2, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel1, java.awt.BorderLayout.LINE_START);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 869, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 608, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel3, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel2, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void roundedBotton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedBotton10ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_roundedBotton10ActionPerformed

    private void roundedBotton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedBotton8ActionPerformed

        JPopupMenu popupMenu = new JPopupMenu() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient background
                GradientPaint gradientPaint = new GradientPaint(0, 0, Color.decode("#3E5151"), getWidth(), getHeight(), Color.decode("#000000"));
                g2.setPaint(gradientPaint);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                super.paintComponent(g);
            }
        };
        popupMenu.setOpaque(false);
        popupMenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add custom menu items
        String[] moduleNames = {"Add Teacher", "Teacher Enrollment", "Teacher List"};
        for (String moduleName : moduleNames) {
            JMenuItem menuItem = new JMenuItem(moduleName) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setOpaque(false);
                }
            };
            menuItem.setBackground(new Color(0, 0, 0)); // Transparent background
            menuItem.setForeground(Color.WHITE); // Text color
            menuItem.setFont(new Font("Arial Narrow", Font.BOLD, 14));

            // Module-specific action
            menuItem.addActionListener(e -> loadModule3(moduleName));
            popupMenu.add(menuItem);
        }

        // Set preferred size of the popup menu
        popupMenu.setPreferredSize(new Dimension(200, 150));

        // Show the popup menu
        popupMenu.show(roundedBotton8, roundedBotton8.getWidth() / 2, roundedBotton8.getHeight());

    }//GEN-LAST:event_roundedBotton8ActionPerformed

    private void loadModule3(String moduleName) {
        jPanel2.removeAll();

        // Dynamically load the correct JPanel based on the module name
        switch (moduleName) {
            case "Add Teacher":
                teacherR tm = new teacherR();
                jPanel2.add(tm, BorderLayout.CENTER);
                break;

            case "Class Record":

                ClassRecord classrecord = new ClassRecord();
                jPanel2.add(classrecord, BorderLayout.CENTER);
                break;
            case "Teacher List":

                TeacherList tm1 = new TeacherList();
                jPanel2.add(tm1, BorderLayout.CENTER);
                break;

            default:
                jPanel2.add(new JLabel("Module Not Found", SwingConstants.CENTER), BorderLayout.CENTER);
        }

        // Update UI
        SwingUtilities.updateComponentTreeUI(jPanel2);
    }
    private void roundedBotton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedBotton2ActionPerformed

        JPopupMenu popupMenu = new JPopupMenu() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient background
                GradientPaint gradientPaint = new GradientPaint(0, 0, Color.decode("#3E5151"), getWidth(), getHeight(), Color.decode("#000000"));
                g2.setPaint(gradientPaint);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                super.paintComponent(g);
            }
        };
        popupMenu.setOpaque(false);
        popupMenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add custom menu items
        String[] moduleNames = {"Add Student", "Student List"};
        for (String moduleName : moduleNames) {
            JMenuItem menuItem = new JMenuItem(moduleName) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setOpaque(false);
                }
            };
            menuItem.setBackground(new Color(0, 0, 0)); // Transparent background
            menuItem.setForeground(Color.WHITE); // Text color
            menuItem.setFont(new Font("Arial Narrow", Font.BOLD, 14));

            // Module-specific action
            menuItem.addActionListener(e -> loadModule(moduleName));
            popupMenu.add(menuItem);
        }

        // Set preferred size of the popup menu
        popupMenu.setPreferredSize(new Dimension(200, 150));

        // Show the popup menu
        popupMenu.show(roundedBotton2, roundedBotton2.getWidth() / 2, roundedBotton2.getHeight());


    }//GEN-LAST:event_roundedBotton2ActionPerformed
    private void loadModule(String moduleName) {
        jPanel2.removeAll();

        // Dynamically load the correct JPanel based on the module name
        switch (moduleName) {
            case "Add Student":
                studentR pm = new studentR();
                jPanel2.add(pm, BorderLayout.CENTER);
                break;
            case "Student List":

                StudentList sl = new StudentList();
                jPanel2.add(sl, BorderLayout.CENTER);
                break;
            default:
                jPanel2.add(new JLabel("Module Not Found", SwingConstants.CENTER), BorderLayout.CENTER);
        }

        // Update UI
        SwingUtilities.updateComponentTreeUI(jPanel2);
    }
    private void roundedBotton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedBotton3ActionPerformed

        jPanel2.removeAll();
        subjectR db = new subjectR();
        jPanel2.add(db, BorderLayout.CENTER);

        SwingUtilities.updateComponentTreeUI(jPanel2);

    }//GEN-LAST:event_roundedBotton3ActionPerformed

    private void roundedBotton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedBotton4ActionPerformed

        JPopupMenu popupMenu = new JPopupMenu() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient background
                GradientPaint gradientPaint = new GradientPaint(0, 0, Color.decode("#3E5151"), getWidth(), getHeight(), Color.decode("#000000"));
                g2.setPaint(gradientPaint);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

                super.paintComponent(g);
            }
        };
        popupMenu.setOpaque(false);
        popupMenu.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add custom menu items
        String[] moduleNames = {"Add Class", "Add Class For Student", "Class List"};
        for (String moduleName : moduleNames) {
            JMenuItem menuItem = new JMenuItem(moduleName) {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    setOpaque(false);
                }
            };
            menuItem.setBackground(new Color(0, 0, 0)); // Transparent background
            menuItem.setForeground(Color.WHITE); // Text color
            menuItem.setFont(new Font("Arial Narrow", Font.BOLD, 14));

            // Module-specific action
            menuItem.addActionListener(e -> loadModule2(moduleName));
            popupMenu.add(menuItem);
        }

        // Set preferred size of the popup menu
        popupMenu.setPreferredSize(new Dimension(200, 150));

        // Show the popup menu
        popupMenu.show(roundedBotton4, roundedBotton4.getWidth() / 2, roundedBotton4.getHeight());

    }//GEN-LAST:event_roundedBotton4ActionPerformed

    private void roundedBotton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedBotton6ActionPerformed
        // Attendance

        jPanel2.removeAll();
        StudentAttendance studentA = new StudentAttendance();
        jPanel2.add(studentA, BorderLayout.CENTER);

        SwingUtilities.updateComponentTreeUI(jPanel2);

    }//GEN-LAST:event_roundedBotton6ActionPerformed

    private void roundedBotton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedBotton7ActionPerformed
        jPanel2.removeAll();
        ClassAttendance classA = new ClassAttendance();
        jPanel2.add(classA, BorderLayout.CENTER);

        SwingUtilities.updateComponentTreeUI(jPanel2);

    }//GEN-LAST:event_roundedBotton7ActionPerformed

    private void roundedBotton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedBotton5ActionPerformed
          jPanel2.removeAll();
       PaymentRecord payment = new PaymentRecord();
        jPanel2.add(payment, BorderLayout.CENTER);

        SwingUtilities.updateComponentTreeUI(jPanel2);
    }//GEN-LAST:event_roundedBotton5ActionPerformed

    private void loadModule2(String moduleName) {
        jPanel2.removeAll();

        // Dynamically load the correct JPanel based on the module name
        switch (moduleName) {
            case "Add Class":
                classR pm = new classR();
                jPanel2.add(pm, BorderLayout.CENTER);
                break;
            case "Add Class For Student":

                classRForStudent pm1 = new classRForStudent();
                jPanel2.add(pm1, BorderLayout.CENTER);
                break;

            case "Class List":

                ClassList cl = new ClassList();
                jPanel2.add(cl, BorderLayout.CENTER);
                break;

            default:
                jPanel2.add(new JLabel("Module Not Found", SwingConstants.CENTER), BorderLayout.CENTER);
        }

        // Update UI
        SwingUtilities.updateComponentTreeUI(jPanel2);
    }

    public static void main(String args[]) {

        FlatLightLaf.setup();

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainPanel().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Controls.card card1;
    private Controls.card card2;
    private Controls.card2 card21;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private Controls.RoundedBotton roundedBotton10;
    private Controls.RoundedBotton roundedBotton2;
    private Controls.RoundedBotton roundedBotton3;
    private Controls.RoundedBotton roundedBotton4;
    private Controls.RoundedBotton roundedBotton5;
    private Controls.RoundedBotton roundedBotton6;
    private Controls.RoundedBotton roundedBotton7;
    private Controls.RoundedBotton roundedBotton8;
    // End of variables declaration//GEN-END:variables
}
