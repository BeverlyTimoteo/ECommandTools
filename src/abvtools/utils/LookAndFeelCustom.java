package abvtools.utils;

import abvtools.enumeration.SistemasOperacionais;
import java.awt.Font;
import javax.swing.UIManager;

public class LookAndFeelCustom {

    public static void set() throws Exception {
        if (SistemasOperacionais.get() == SistemasOperacionais.LINUX.getId()) {
            Fonte.setFont(new Font("Tahoma", 0, 10));
        }

        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }

}
