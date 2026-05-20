package Controls;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Font;
import javax.swing.JTextField;

public class RoundedTextFeild extends JTextField{
    
 public  RoundedTextFeild(){
      init();
    }
    
     private void init(){
    
         this.putClientProperty(FlatClientProperties.STYLE, "arc:700");
                setFont(new Font("Arial", Font.BOLD,14));
    }
    
}
