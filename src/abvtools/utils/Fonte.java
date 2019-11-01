package abvtools.utils;

import java.awt.Font;

public class Fonte {

    private static Font font = new Font("Tahoma", 0, 11);

    public static Font getFont() {
        return Fonte.font;
    }

    public static void setFont(Font font) {
        Fonte.font = font;
    }
}
