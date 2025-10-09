
package app;

import javax.swing.UIManager;
import vista.ProveedorFrame;


public class MainProveedor {
    
    public static void main(String[] args) {
   try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) { 
                    UIManager.setLookAndFeel(info.getClassName()); break; }
            }
        } catch (Exception ignore) {}

        java.awt.EventQueue.invokeLater(() -> new ProveedorFrame().setVisible(true));
    }
}
