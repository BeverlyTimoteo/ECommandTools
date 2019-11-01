package ecommandtools.exception;

import javax.swing.JOptionPane;

public class ExceptionCustom extends Exception {

    private final int infoFlag;

    public ExceptionCustom(String message) {
        super(message);
        infoFlag = JOptionPane.WARNING_MESSAGE;
    }

    public ExceptionCustom(String message, int infoFlag) {
        super(message);
        this.infoFlag = infoFlag;
    }

    public int getIdType() {
        return infoFlag;
    }

}
