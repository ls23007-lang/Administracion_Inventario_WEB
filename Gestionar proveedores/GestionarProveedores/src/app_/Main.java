
package app_;


public class Main {
  public static void main(String[] args) {
    try { 
        javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName()); } catch(Exception ignored){}
    javax.swing.SwingUtilities.invokeLater(() -> new vista.ProveedorFrame().setVisible(true));
  }
}
