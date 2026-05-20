package Controls;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.ui.FlatComboBoxUI;

import javax.swing.*;
import java.awt.*;

public class roundedCombobox extends JComboBox<String> {

    public roundedCombobox() {
        init();
    }

    private void init() {
        // Set the properties for rounded corners
        this.putClientProperty(FlatClientProperties.STYLE, "arc:999");
        setFont(new Font("Arial", Font.BOLD, 14));

        // Set custom UI for the combo box
        setUI(new CustomFlatComboBoxUI());

        // Set the renderer for the dropdown items
        setRenderer(new RoundedComboBoxRenderer());

        // Make the combo box non-opaque to paint custom background
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Enable anti-aliasing for smooth rendering
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Create a gradient from top to bottom (you can customize colors)
        GradientPaint gradient = new GradientPaint(0, 0, Color.decode("#454596"), getWidth(), getHeight(), Color.decode("#00E3E4"));

        // Set the gradient as the paint for the graphics object
        g2.setPaint(gradient);

        // Draw a rounded rectangle as the background
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

        // Dispose of the graphics object to clean up resources
        g2.dispose();

        // Continue with default component painting (i.e., the text, arrow, etc.)
        super.paintComponent(g);
    }

    // Custom ComboBox UI class to customize the dropdown button
    private static class CustomFlatComboBoxUI extends FlatComboBoxUI {

        @Override
        protected JButton createArrowButton() {
            // Create a custom button for the dropdown arrow
            JButton button = new JButton();
            button.setBorder(BorderFactory.createEmptyBorder()); // Remove default button borders
            button.setOpaque(false); // Make the button background transparent

            // Customize the arrow icon (down arrow)
            button.setIcon(new ArrowIcon());

            return button;
        }

        // Custom arrow icon for the dropdown button
        private static class ArrowIcon implements Icon {

            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Set arrow color
                g2.setColor(Color.WHITE);

                // Draw a simple downward-facing triangle (arrow)
                int[] xPoints = {x, x + getIconWidth() / 2, x + getIconWidth()};
                int[] yPoints = {y, y + getIconHeight(), y};
                g2.fillPolygon(xPoints, yPoints, 3);

                g2.dispose();
            }

            @Override
            public int getIconWidth() {
                return 10; // Customize the width of the arrow
            }

            @Override
            public int getIconHeight() {
                return 10; // Customize the height of the arrow
            }
        }
    }

    // Custom renderer for the dropdown items
    private static class RoundedComboBoxRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            label.setOpaque(true);
            label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Add padding
            label.setBackground(isSelected ? new Color(100, 100, 255) : Color.WHITE); // Change background color when selected
            label.setForeground(isSelected ? Color.WHITE : Color.BLACK); // Change text color when selected
            return label;
        }
    }
}
