
package app;


public class MainProveedor {
  public static void main(String[] args) {
    try { 
        javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName()); } catch(Exception ignored){}
    javax.swing.SwingUtilities.invokeLater(() -> new vista.ProveedorFrame().setVisible(true));
  }
}
