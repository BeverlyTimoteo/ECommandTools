package abvtools.componentes.botao;

import abvtools.utils.Fonte;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;

public class Botao extends JButton implements MouseListener {

    private boolean mouseFocus;
    private Color atualForeground;
    private Color novaForeground;

    public Botao() {
        mouseFocus = false;
        atualForeground = getForegroundCustom();
        novaForeground = getForegroundCustom();

        addMouseListener(this);
        setFont(Fonte.getFont());
    }

    private Color getForegroundCustom() {
        return this.getForeground();
    }

    public Color getAtualForegroud() {
        return atualForeground;
    }

    public void setAtualForeground(Color atualForegroud) {
        this.atualForeground = atualForegroud;
    }

    public Color getNovaForeground() {
        return novaForeground;
    }

    public void setNovaForeground(Color novaForegroud) {
        this.novaForeground = novaForegroud;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (mouseFocus) {
            this.setForeground(novaForeground);
        } else {
            this.setForeground(atualForeground);
        }
    }

    public boolean isMouseFocus() {
        return mouseFocus;
    }

    public void setMouseFocus(boolean mouseFocus) {
        this.mouseFocus = mouseFocus;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        setMouseFocus(true);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        setMouseFocus(false);
    }
}
