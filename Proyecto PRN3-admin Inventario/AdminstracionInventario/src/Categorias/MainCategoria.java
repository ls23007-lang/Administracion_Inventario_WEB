
package Categorias;
import javax.swing.*;
import Categorias.vista.CategoriaFrame;

public class MainCategoria {
    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignore) {}

        SwingUtilities.invokeLater(() -> new CategoriaFrame().setVisible(true));
    }
}
