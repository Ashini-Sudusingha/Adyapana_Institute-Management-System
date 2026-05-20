
package Controls;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Font;
import javax.swing.JComboBox;



public class FlatroundedComboBox extends JComboBox<Object>{
     public  FlatroundedComboBox(){
      init();
    }
    
     private void init(){
    
         this.putClientProperty(FlatClientProperties.STYLE, "arc:999");
                setFont(new Font("Arial", Font.BOLD,14));
    }
    
}
