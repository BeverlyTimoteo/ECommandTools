package ecommandtools;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import ecommandtools.utils.LookAndFeelCustom;

public class Main {

    public static void main(String[] args) {
        try {
            LookAndFeelCustom.set();

            JOptionPane.showMessageDialog(null, "ECommandTools!");

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
