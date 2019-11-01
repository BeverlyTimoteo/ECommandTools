package abvtools.utils.biometria;

import com.digitalpersona.uareu.Fid;
import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.UareUException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CapturarBiometria extends Thread {

    public static final String CMD_CAPTURA = "CAPTURADA_BIOMETRIA";

    private boolean cancelarCaptura;
    private final Reader leitor;
    private final Fid.Format formato;
    private final Reader.ImageProcessing processamentoImagem;
    private ActionListener listenerOrigem;
    private CapturarEvento ultimoEvento;

    public CapturarBiometria(Reader reader, Fid.Format img_format, Reader.ImageProcessing img_proc) {
        cancelarCaptura = false;
        leitor = reader;
        formato = img_format;
        processamentoImagem = img_proc;
    }

    public void cancel() {
        cancelarCaptura = true;

        try {
            leitor.CancelCapture();

        } catch (UareUException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        capturarBiometria();
    }

    public void iniciarCaptura(ActionListener listener) {
        listenerOrigem = listener;
        super.start();
    }

    public void join(int milliseconds) {
        try {
            super.join(milliseconds);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public CapturarEvento getUltimoEvento() {
        return ultimoEvento;
    }

    private void capturarBiometria() {
        try {
            boolean estaLendo = false;

            while (!estaLendo && !cancelarCaptura) {
                Reader.Status rs = leitor.GetStatus();

                if (Reader.ReaderStatus.BUSY == rs.status) {
                    try {
                        Thread.sleep(100);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }

                } else if (Reader.ReaderStatus.READY == rs.status
                        || Reader.ReaderStatus.NEED_CALIBRATION == rs.status) {
                    estaLendo = true;
                    break;

                } else {
                    NotificaListener(CMD_CAPTURA, null, rs, null);
                    break;
                }
            }

            if (cancelarCaptura) {
                Reader.CaptureResult cr = new Reader.CaptureResult();
                cr.quality = Reader.CaptureQuality.CANCELED;
                NotificaListener(CMD_CAPTURA, cr, null, null);
            }

            if (estaLendo) {
                Reader.CaptureResult cr = leitor.Capture(formato, processamentoImagem, 500, -1);
                NotificaListener(CMD_CAPTURA, cr, null, null);
            }

        } catch (UareUException e) {
            NotificaListener(CMD_CAPTURA, null, null, e);
        }
    }

    private void NotificaListener(String command, Reader.CaptureResult cr, Reader.Status st, UareUException ex) {
        final CapturarEvento evt = new CapturarEvento(this, command, cr, st, ex);

        ultimoEvento = evt;

        if (null == listenerOrigem || null == command || command.equals("")) {
            return;
        }

        javax.swing.SwingUtilities.invokeLater(() -> {
            listenerOrigem.actionPerformed(evt);
        });
    }

    public class CapturarEvento extends ActionEvent {

        public Reader.CaptureResult captureResult;
        public Reader.Status readerStatus;
        public UareUException ex;

        public CapturarEvento(Object source, String command, Reader.CaptureResult cr, Reader.Status st, UareUException ex) {
            super(source, ActionEvent.ACTION_PERFORMED, command);
            captureResult = cr;
            readerStatus = st;
            this.ex = ex;
        }
    }

}
