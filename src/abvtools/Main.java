package abvtools;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import abvtools.utils.LookAndFeelCustom;

public class Main {

    public static void main(String[] args) {
        try {
            LookAndFeelCustom.set();

            JOptionPane.showMessageDialog(null, "ABVTools!");

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
