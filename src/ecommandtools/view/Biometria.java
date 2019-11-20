package ecommandtools.view;

import ecommandtools.utils.biometria.CapturarBiometria;
import com.digitalpersona.uareu.Engine;
import com.digitalpersona.uareu.Fid;
import com.digitalpersona.uareu.Fmd;
import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.ReaderCollection;
import com.digitalpersona.uareu.UareUGlobal;
import ecommandtools.exception.ExceptionCustom;
import ecommandtools.exception.ExceptionMessage;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import ecommandtools.utils.Mensagens;

public class Biometria extends JDialog implements ActionListener {

    private static Biometria dialogoBiometria;
    private BufferedImage imagem;
    private final TipoOperacao tipo;
    private Fid.Fiv view;
    private static CapturarBiometria capturar;
    private static Reader leitor;
    private static Fmd fmd = null;
    private static final String ACAO_SAIR = "sair";
    private boolean iniciuCaptura = true;
    private static EnrollmentThread enrollment;

    public Biometria(Frame parent, boolean modal, Reader leitor, TipoOperacao tipo, MouseListener evento) throws Exception {
        super(parent, modal);
        initComponents();

        Biometria.leitor = leitor;
        Biometria.fmd = null;

        this.tipo = tipo;

        switch (this.tipo) {
            case CADASTRO:
                lbLink.setVisible(false);
                btnSair.addActionListener(this);
                btnSair.setActionCommand(ACAO_SAIR);
                lbMensagem.setText("Insira a digital para cadastrar!");

                enrollment = new EnrollmentThread(Biometria.leitor, this);
                break;

            case LEITURA:
                lbLink.setVisible(false);
                btnSair.addActionListener(this);
                btnSair.setActionCommand(ACAO_SAIR);
                lbMensagem.setText("Insira a digital!");

                break;

            case LOGIN:
                lbLink.addMouseListener(evento);
                toolbarSair.setVisible(false);
                break;
        }

    }

    public void iniciarCaptura() throws Exception {
        capturar = new CapturarBiometria(leitor, Fid.Format.ANSI_381_2004, Reader.ImageProcessing.IMG_PROC_DEFAULT);
        capturar.iniciarCaptura(this);
    }

    public static void pararCaptura() throws Exception {
        if (capturar != null) {
            capturar.cancel();
        }

        if (enrollment != null) {
            enrollment.cancel();
        }
    }

    public static void esperarCaptura() throws Exception {
        if (capturar != null) {
            capturar.join(1000);
        }

        if (enrollment != null) {
            enrollment.join(1000);
        }
    }

    private void exibirDialogo(Biometria dlgParent) throws Exception {
        leitor.Open(Reader.Priority.COOPERATIVE);

        switch (this.tipo) {
            case CADASTRO:
                enrollment.start();
                break;

            default:
                iniciarCaptura();
                break;
        }

        dialogoBiometria = dlgParent;
        dialogoBiometria.pack();
        dialogoBiometria.setLocationRelativeTo(null);
        dialogoBiometria.toFront();
        dialogoBiometria.setVisible(true);
        dialogoBiometria.dispose();
    }

    public static void encerrarBiometria() throws Exception {
        if (Biometria.dialogoBiometria != null) {
            Biometria.dialogoBiometria.dispose();
        }

        pararCaptura();

        esperarCaptura();

        if (leitor != null) {
            leitor.Close();
        }

        leitor = null;

        capturar = null;

        enrollment = null;

        UareUGlobal.DestroyReaderCollection();
    }

    public static void criarLeituraLogin(Object parent, MouseListener evento) throws Exception {
        criarLeitura(parent, evento, TipoOperacao.LOGIN);
    }

    public static void criarLeituraCadastro(Object parent) throws Exception {
        criarLeitura(parent, null, TipoOperacao.CADASTRO);
    }

    public static void criarLeitura(Object parent) throws Exception {
        criarLeitura(parent, null, TipoOperacao.LEITURA);
    }

    public static void criarLeitura(Object parent, MouseListener evento, TipoOperacao tipo) throws Exception {
        ReaderCollection rc = UareUGlobal.GetReaderCollection();

        int tentativas = 0;

        while (rc.isEmpty() && tentativas++ < 5) {
            rc.GetReaders();
            Thread.sleep(500);
        }

        if (rc.isEmpty()) {
            throw new ExceptionCustom("Não foi possível iniciar a leitura!");
        }

        Biometria dlg = new Biometria((Frame) parent, true, rc.get(0), tipo, evento);
        dlg.exibirDialogo(dlg);
    }

    public static String getBiometriaCapturada() throws Exception {
        if (fmd != null) {
            return Base64.getEncoder().encodeToString(fmd.getData());
        }

        return "";
    }

    public static boolean compararBiometrias(String biometriaCapturada, String biometriaComparar) throws Exception {
        Fmd fmdCapturada = UareUGlobal.GetImporter().ImportFmd(Base64.getDecoder().decode(biometriaCapturada), Fmd.Format.ANSI_378_2004, Fmd.Format.ANSI_378_2004);
        Fmd fmdComparar = UareUGlobal.GetImporter().ImportFmd(Base64.getDecoder().decode(biometriaComparar), Fmd.Format.ANSI_378_2004, Fmd.Format.ANSI_378_2004);

        Engine engine = UareUGlobal.GetEngine();

        int valorMinimo = Engine.PROBABILITY_ONE / 100000;

        int valorComparado = engine.Compare(fmdCapturada, 0, fmdComparar, 0);

        return valorComparado < valorMinimo;

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            switch (tipo) {
                case CADASTRO:
                    if (e.getActionCommand().equals(ACAO_SAIR)) {
                        this.setVisible(false);

                    } else {
                        EnrollmentThread.EnrollmentEvent evt = (EnrollmentThread.EnrollmentEvent) e;

                        switch (e.getActionCommand()) {
                            case EnrollmentThread.ACT_PROMPT:
                                if (!iniciuCaptura) {
                                    lbMensagem.setText("Insira a mesma digital novamente!");
                                }
                                iniciuCaptura = false;
                                break;
                            case EnrollmentThread.ACT_CAPTURE:
                                if (evt.captureResult != null) {
                                    Mensagens.mensagemInfo("Falha ao capturar biometria!\nVerifique a qualidade da biometria!");

                                } else if (evt.exception != null) {
                                    Mensagens.mensagemErro("Falha ao capturar biometria!");
                                    Logger.getLogger(Biometria.class.getName()).log(Level.SEVERE, null, evt.exception);

                                } else if (evt.readerStatus != null) {
                                    Mensagens.mensagemInfo("Falha ao capturar biometria!\nVerifique o leitor!");
                                }
                                iniciuCaptura = false;
                                break;
                            case EnrollmentThread.ACT_REMOVERDIGITAL:
                                exibirImagemCapturada();
                                lbMensagem.setText("Remova a digital do leitor!");
                                break;
                            case EnrollmentThread.ACT_LIMPARDIGITAL:
                                view = null;
                                exibirImagemCapturada();
                                break;
                            case EnrollmentThread.ACT_FEATURES:
                                if (evt.exception == null) {
                                    lbMensagem.setText("Biometria capturada!");

                                } else {
                                    Mensagens.mensagemErro("Falha ao capturar biometria!");
                                    Logger.getLogger(Biometria.class.getName()).log(Level.SEVERE, null, evt.exception);
                                }
                                iniciuCaptura = false;
                                break;
                            case EnrollmentThread.ACT_DONE:
                                if (evt.exception == null) {
                                    this.fmd = evt.enrollmentFmd;

                                    Thread.sleep(2000);

                                    setVisible(false);

                                } else {
                                    Mensagens.mensagemErro("Falha ao capturar biometria!");
                                    Logger.getLogger(Biometria.class.getName()).log(Level.SEVERE, null, evt.exception);
                                }
                                break;
                            case EnrollmentThread.ACT_CANCELED:
                                this.setVisible(false);
                                break;
                        }

                        if (evt.exception != null) {
                            Mensagens.mensagemErro("Falha ao capturar biometria!");
                            Logger.getLogger(Biometria.class.getName()).log(Level.SEVERE, null, evt.exception);
                            this.setVisible(false);

                        } else if (evt.readerStatus != null && evt.readerStatus.status != Reader.ReaderStatus.READY && evt.readerStatus.status != Reader.ReaderStatus.NEED_CALIBRATION) {
                            Mensagens.mensagemErro("Falha ao capturar biometria!");
                            Logger.getLogger(Biometria.class.getName()).log(Level.SEVERE, null, evt.exception);
                            this.setVisible(false);

                        }
                    }

                    break;

                default:
                    if (e.getActionCommand().equals(ACAO_SAIR)) {
                        this.setVisible(false);

                    } else if (e.getActionCommand().equals(CapturarBiometria.CMD_CAPTURA)) {
                        CapturarBiometria.CapturarEvento evt = (CapturarBiometria.CapturarEvento) e;
                        boolean cancelado = false;

                        if (evt.captureResult != null) {
                            if (evt.captureResult.image != null && evt.captureResult.quality == Reader.CaptureQuality.GOOD) {
                                view = evt.captureResult.image.getViews()[0];

                                fmd = UareUGlobal.GetEngine().CreateFmd(evt.captureResult.image, Fmd.Format.ANSI_378_2004);

                                exibirImagemCapturada();

                                Thread.sleep(2000);

                                setVisible(false);

                            } else if (evt.captureResult.quality == Reader.CaptureQuality.CANCELED) {
                                cancelado = true;

                            } else {
                                Mensagens.mensagemInfo("Falha ao capturar biometria!");
                                esperarCaptura();
                                iniciarCaptura();
                            }

                        } else if (evt.ex != null) {
                            Mensagens.mensagemErro("Falha ao capturar biometria!");
                            Logger.getLogger(Biometria.class.getName()).log(Level.SEVERE, null, evt.ex);
                            cancelado = true;

                        } else if (evt.readerStatus != null) {
                            Mensagens.mensagemInfo("Falha ao capturar biometria!");
                            cancelado = true;
                        }

                        if (cancelado) {
                            fmd = null;
                            setVisible(false);
                        }
                    }
                    break;
            }

        } catch (Exception ex) {
            Mensagens.mensagemErro("Falha ao capturar biometria!");
            Logger.getLogger(Biometria.class.getName()).log(Level.SEVERE, null, ex);
            setVisible(false);
        }
    }

    private void exibirImagemCapturada() throws Exception {
        if (view != null) {
            imagem = new BufferedImage(view.getWidth(), view.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
            imagem.getRaster().setDataElements(0, 0, view.getWidth(), view.getHeight(), view.getImageData());
            pnImagem.getGraphics().drawImage(imagem, 0, 0, null);

        } else {
            pnImagem.repaint();
        }
    }

    public static void fecharDialogo() throws Exception {
        if (Biometria.dialogoBiometria.isVisible()) {
            Biometria.dialogoBiometria.setVisible(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnFundo = new javax.swing.JPanel();
        lbMensagem = new ecommandtools.componentes.rotulo.Rotulo();
        lbLink = new ecommandtools.componentes.rotulo.Rotulo();
        pnImagem = new javax.swing.JPanel();
        toolbarSair = new javax.swing.JToolBar();
        btnSair = new ecommandtools.componentes.radiobotao.RadioBotao();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Informe a biometria");
        setModal(true);
        setUndecorated(true);
        setResizable(false);
        setType(java.awt.Window.Type.UTILITY);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        pnFundo.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 0, 0)));

        lbMensagem.setText("Insira a digital para fazer login ou ");

        lbLink.setText("clique aki!");
        lbLink.setLink(true);

        javax.swing.GroupLayout pnImagemLayout = new javax.swing.GroupLayout(pnImagem);
        pnImagem.setLayout(pnImagemLayout);
        pnImagemLayout.setHorizontalGroup(
            pnImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 328, Short.MAX_VALUE)
        );
        pnImagemLayout.setVerticalGroup(
            pnImagemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 335, Short.MAX_VALUE)
        );

        toolbarSair.setFloatable(false);
        toolbarSair.setRollover(true);
        toolbarSair.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ecommandtools/image/exit.png"))); // NOI18N
        btnSair.setToolTipText("Sair");
        btnSair.setFocusable(false);
        btnSair.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnSair.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSair.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        toolbarSair.add(btnSair);

        javax.swing.GroupLayout pnFundoLayout = new javax.swing.GroupLayout(pnFundo);
        pnFundo.setLayout(pnFundoLayout);
        pnFundoLayout.setHorizontalGroup(
            pnFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnFundoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnImagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnFundoLayout.createSequentialGroup()
                        .addComponent(lbMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(lbLink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(toolbarSair, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnFundoLayout.setVerticalGroup(
            pnFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnFundoLayout.createSequentialGroup()
                .addGroup(pnFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnFundoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pnFundoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lbLink, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lbMensagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(toolbarSair, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnImagem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pnFundo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnFundo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        try {
            this.setVisible(false);

        } catch (Exception e) {
            ExceptionMessage.exibirMensagemException(e, getTitle());

        }
    }//GEN-LAST:event_formWindowClosing

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private ecommandtools.componentes.radiobotao.RadioBotao btnSair;
    private ecommandtools.componentes.rotulo.Rotulo lbLink;
    private ecommandtools.componentes.rotulo.Rotulo lbMensagem;
    private javax.swing.JPanel pnFundo;
    private javax.swing.JPanel pnImagem;
    private javax.swing.JToolBar toolbarSair;
    // End of variables declaration//GEN-END:variables

    enum TipoOperacao {
        LEITURA,
        LOGIN,
        CADASTRO;
    }

    public class EnrollmentThread extends Thread implements Engine.EnrollmentCallback {

        public static final String ACT_PROMPT = "enrollment_prompt";
        public static final String ACT_CAPTURE = "enrollment_capture";
        public static final String ACT_FEATURES = "enrollment_features";
        public static final String ACT_REMOVERDIGITAL = "enrollment_removerdigital";
        public static final String ACT_LIMPARDIGITAL = "enrollment_limpardigital";
        public static final String ACT_DONE = "enrollment_done";
        public static final String ACT_CANCELED = "enrollment_canceled";

        public class EnrollmentEvent extends ActionEvent {

            private static final long serialVersionUID = 102;

            public Reader.CaptureResult captureResult;
            public Reader.Status readerStatus;
            public Exception exception;
            public Fmd enrollmentFmd;

            public EnrollmentEvent(Object source, String action, Fmd fmd, Reader.CaptureResult cr, Reader.Status st, Exception ex) {
                super(source, ActionEvent.ACTION_PERFORMED, action);
                captureResult = cr;
                readerStatus = st;
                exception = ex;
                enrollmentFmd = fmd;
            }
        }

        private final Reader leitor;
        private CapturarBiometria capturarBiometria;
        private final ActionListener listenerOrigem;
        private boolean cancelado;

        protected EnrollmentThread(Reader reader, ActionListener listener) {
            leitor = reader;
            this.listenerOrigem = listener;
        }

        @Override
        public Engine.PreEnrollmentFmd GetFmd(Fmd.Format format) {
            Engine.PreEnrollmentFmd prefmd = null;

            while (null == prefmd && !cancelado) {
                capturarBiometria = new CapturarBiometria(leitor, Fid.Format.ANSI_381_2004, Reader.ImageProcessing.IMG_PROC_DEFAULT);
                capturarBiometria.start();

                SendToListener(ACT_PROMPT, null, null, null, null);

                capturarBiometria.join(0);

                CapturarBiometria.CapturarEvento evt = capturarBiometria.getUltimoEvento();

                if (evt.captureResult != null) {
                    if (evt.captureResult.quality == Reader.CaptureQuality.CANCELED) {
                        break;

                    } else if (evt.captureResult.image != null
                            && evt.captureResult.quality == Reader.CaptureQuality.GOOD) {
                        Engine engine = UareUGlobal.GetEngine();

                        try {
                            Fmd fmd = engine.CreateFmd(evt.captureResult.image, Fmd.Format.ANSI_378_2004);
                            view = evt.captureResult.image.getViews()[0];

                            SendToListener(ACT_REMOVERDIGITAL, null, null, null, null);

                            Thread.sleep(1000);

                            SendToListener(ACT_LIMPARDIGITAL, null, null, null, null);

                            prefmd = new Engine.PreEnrollmentFmd();
                            prefmd.fmd = fmd;
                            prefmd.view_index = 0;

                            SendToListener(ACT_FEATURES, null, null, null, null);

                        } catch (Exception e) {
                            SendToListener(ACT_FEATURES, null, null, null, e);
                        }
                    } else {
                        SendToListener(ACT_CAPTURE, null, evt.captureResult, evt.readerStatus, evt.ex);
                    }
                } else {
                    SendToListener(ACT_CAPTURE, null, evt.captureResult, evt.readerStatus, evt.ex);
                }
            }

            return prefmd;
        }

        public void cancel() {
            cancelado = true;

            if (capturarBiometria != null) {
                capturarBiometria.cancel();
            }
        }

        private void SendToListener(String action, Fmd fmd, Reader.CaptureResult cr, Reader.Status st, Exception ex) {
            if (listenerOrigem == null || action == null || action.equals("")) {
                return;
            }

            final EnrollmentEvent evt = new EnrollmentEvent(this, action, fmd, cr, st, ex);

            try {
                javax.swing.SwingUtilities.invokeAndWait(() -> {
                    listenerOrigem.actionPerformed(evt);
                });
            } catch (InvocationTargetException | InterruptedException e) {
                Logger.getLogger(EnrollmentThread.class.getName()).log(Level.SEVERE, null, e);
            }
        }

        @Override
        public void run() {
            Engine engine = UareUGlobal.GetEngine();

            try {
                cancelado = false;

                while (!cancelado) {
                    Fmd fmd = engine.CreateEnrollmentFmd(Fmd.Format.ANSI_378_2004, this);

                    if (null != fmd) {
                        SendToListener(ACT_DONE, fmd, null, null, null);

                    } else {
                        SendToListener(ACT_CANCELED, null, null, null, null);
                        break;
                    }
                }
            } catch (Exception e) {
                SendToListener(ACT_DONE, null, null, null, e);
            }
        }
    }

}
