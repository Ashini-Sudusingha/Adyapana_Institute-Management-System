
package Controls;

import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.JPanel;

    public class RoundedPanel extends JPanel {

    public RoundedPanel() {
        init();
    }

    private void init() {
        this.putClientProperty(FlatClientProperties.STYLE, "arc:25");
    }
}
