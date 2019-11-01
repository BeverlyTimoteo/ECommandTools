package abvtools.componentes.rotulo;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.Map;
import javax.swing.JLabel;
import abvtools.utils.Fonte;

public class Rotulo extends JLabel {

    private boolean link;

    public Rotulo() {
        adicionarEvento();

        this.link = false;
        setFont(Fonte.getFont());
    }

    public Rotulo(String texto) {
        adicionarEvento();

        this.link = false;
        setFont(Fonte.getFont());
        setText(texto);
    }

    public boolean isLink() {
        return this.link;
    }

    public void setLink(boolean isLink) {
        this.link = isLink;

        if (this.link) {
            Font font = getFont();
            Map attributes = font.getAttributes();
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_LOW_ONE_PIXEL);
            setFont(font.deriveFont(attributes));
        } else {
            setFont(Fonte.getFont());
        }
    }

    private void adicionarEvento() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent evt) {
                Rotulo.this.cursorSobreRotulo(evt);
            }

            @Override
            public void mouseExited(MouseEvent evt) {
                Rotulo.this.cursorForaRotulo(evt);
            }
        });
    }

    private void cursorSobreRotulo(MouseEvent evt) {
        if (this.link) {
            setForeground(Color.BLUE);
            setCursor(new Cursor(12));
        }
    }

    private void cursorForaRotulo(MouseEvent evt) {
        if (this.link) {
            setForeground(Color.BLACK);
            setCursor(new Cursor(0));
        }
    }
}
