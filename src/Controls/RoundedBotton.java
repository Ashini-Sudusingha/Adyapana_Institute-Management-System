/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controls;

import com.formdev.flatlaf.FlatClientProperties;
import java.awt.Font;
import javax.swing.JButton;

/**
 *
 * @author Hash_Boy
 */
public class RoundedBotton extends JButton{
    
   public RoundedBotton(){
      init();
    }
    
     private void init(){
    
         this.putClientProperty(FlatClientProperties.STYLE, "arc:800");
                setFont(new Font("Arial", Font.BOLD,14));
    }
    
}
