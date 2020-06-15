/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import classes.Exemplar;
import classes.Livro;
import enumeradores.EnumAcao;
import interfaces.ICRUDExemplar;
import controle.ControleExemplar;
import controle.ControleLivro;
import enumeradores.EnumTipoStatus;
import interfaces.ICRUDLivro;
import utilidades.Mensagens;
import interfaces.ITelaCadastro;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Vinicius
 */
public class TelaExemplar extends javax.swing.JDialog implements ITelaCadastro {

//----------------ATRIBUTOS----------------------------------
//ESSES ATRIBUTOS SAO PWRCORIDOS PELO SISTEMA 
    //CLASSES--------------->
    //ENUMERADORES---------->
    //INTERFACE------------>
    //PERSISTENCIA-------->
    //CONTROLE----------->
    private ICRUDExemplar controleExemplar = null;
    private ICRUDLivro controleLivro = null;

    private Exemplar exemplar = null;

    private Mensagens mensagem = null;
    private EnumAcao acao = null;

    private boolean visible = false;
    private int id;
    private Livro livro;

    private SimpleDateFormat formatoData = null;
    private Calendar hoje = null;
    private Calendar menorData = null;
    private Calendar maiorData = null;

//---------------------end atributos-----------------------
//override
    @Override
    public void setId(int id) {
        this.id = id;
        //isto e o id que vai percorrer cada uma das telas
    }

    @Override
    public void setAcao(EnumAcao acao) {
        this.acao = acao;
        this.setTitle(acao.toString() + " cadastro de exemplar");
        // isto chama a açao para ser sequenciada dentro do cadastro exemeplar
        // poer isso o metodo IAtelaCadastro 
        // void setId(int id);
        // void setAcao(EnumAcao acao);
        // void setVisible(boolean b);

    }

    @Override
    public void setVisible(boolean b) {
        try {
            mensagem = new Mensagens();

            controleExemplar = new ControleExemplar();
            controleLivro = new ControleLivro();

            popularControles();

            if (acao.equals(EnumAcao.Incluir)) {
                limparCampos();
            } else if (acao.equals(EnumAcao.Editar)) {
                exemplar = controleExemplar.buscarPeloId(id);
                preencherCampos();
            }
            jFormattedTextFieldPrecoCompra.requestFocus();
            jFormattedTextFieldPrecoCompra.selectAll();

        } catch (Exception e) {
            mensagem.erro(e);
        }
        visible = true;
        super.setVisible(b);
    }
    //--------------------end override------------------------------
    //metodos 

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    private void popularControles() throws Exception {

        jTextFieldTituloLivro.setText(livro.getTitulo());

        jComboBoxstatusExemplar.addItem(EnumTipoStatus.DISPONIVEL.toString());
        jComboBoxstatusExemplar.addItem(EnumTipoStatus.INATIVO.toString());

        hoje = Calendar.getInstance();
        hoje.setTime(new Date());//data maior

        menorData = Calendar.getInstance();
        menorData.set(2000, 1, 1);

        maiorData = Calendar.getInstance();
        maiorData.setTime(hoje.getTime());

        formatoData = new SimpleDateFormat("dd/MM/yyyy");

        jSpinnerDataAquisicao.setModel(new javax.swing.SpinnerDateModel(
                hoje.getTime(), menorData.getTime(), maiorData.getTime(),
                java.util.Calendar.DAY_OF_MONTH)
        );
        jSpinnerDataAquisicao.setEditor(new javax.swing.JSpinner.DateEditor(jSpinnerDataAquisicao, "dd/MM/yyyy"));
    }

    private void limparCampos() throws Exception {
        jTextFieldIDExemplar.setText("");
        jComboBoxstatusExemplar.setSelectedIndex(0);
        jSpinnerDataAquisicao.setValue(hoje.getTime());
        jFormattedTextFieldPrecoCompra.setText("");
        jTextAreaMotivoDesativado.setText("");
    }

    private void preencherCampos() {
        jTextFieldIDExemplar.setText(exemplar.getIdExemplar() + "");
        jComboBoxstatusExemplar.setSelectedItem(exemplar.getStatusExemplar().toString());
        String dataSpinner = jSpinnerDataAquisicao.getValue().toString();
        jSpinnerDataAquisicao.setValue(exemplar.getDataAquisicao());
        jFormattedTextFieldPrecoCompra.setText(String.format("%.2f", exemplar.getPrecoCompra()));
        jSpinnerQuantidade.setValue(1);
        jSpinnerQuantidade.setEnabled(acao.equals(EnumAcao.Incluir));
        jTextAreaMotivoDesativado.setText(exemplar.getMotivoDesativado());
    }

    private void salvar() throws Exception {
        try {
            float precoCompra = jFormattedTextFieldPrecoCompra.getText().equals("")
                    ? 0
                    : Float.parseFloat(jFormattedTextFieldPrecoCompra.getText().replace(".", "_").replace(",", ".").replace("_", ""));

            exemplar = new Exemplar(
                    0,
                    livro,
                    EnumTipoStatus.valueOf(jComboBoxstatusExemplar.getSelectedItem().toString()),
                    (Date) jSpinnerDataAquisicao.getValue(),
                    precoCompra,
                    jTextAreaMotivoDesativado.getText()
            );

            if (acao.equals(EnumAcao.Incluir)) {
                for (int i = 0; i < (int) jSpinnerQuantidade.getValue(); i++) {
                    controleExemplar.incluir(exemplar);
                }
            } else if (acao.equals(EnumAcao.Editar)) {
                exemplar.setIdExemplar(Integer.parseInt(jTextFieldIDExemplar.getText()));
                controleExemplar.alterar(exemplar);
            }
        } catch (Exception e) {
            mensagem.erro(new Exception("Erro ao salvar o exemplar! \n" + e.getMessage()));
            visible = false;
            this.dispose();
        }
    }

    private void validarPreenchimento() throws Exception {
        if (jComboBoxstatusExemplar.getSelectedIndex() == 1 && jTextAreaMotivoDesativado.getText().trim().length() == 0) {
            throw new Exception("Informe o motivo da desativação do exemplar!");
        }
    }

    public TelaExemplar(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(rootPane);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jTextFieldIDExemplar = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jComboBoxstatusExemplar = new javax.swing.JComboBox<>();
        jLabelMotivoDesativasao = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaMotivoDesativado = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldTituloLivro = new javax.swing.JTextField();
        jSpinnerDataAquisicao = new javax.swing.JSpinner();
        jFormattedTextFieldPrecoCompra = new javax.swing.JFormattedTextField();
        jLabel6 = new javax.swing.JLabel();
        jSpinnerQuantidade = new javax.swing.JSpinner();
        jLabel7 = new javax.swing.JLabel();
        jButtonSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("ID");

        jTextFieldIDExemplar.setEditable(false);
        jTextFieldIDExemplar.setFont(new java.awt.Font("Dialog", 3, 12)); // NOI18N
        jTextFieldIDExemplar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldIDExemplar.setText("                          ");
        jTextFieldIDExemplar.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTextFieldIDExemplar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIDExemplarActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Exemplar "));

        jComboBoxstatusExemplar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxstatusExemplarActionPerformed(evt);
            }
        });

        jLabelMotivoDesativasao.setText("Motivo da  desativação ");

        jTextAreaMotivoDesativado.setColumns(20);
        jTextAreaMotivoDesativado.setRows(2);
        jTextAreaMotivoDesativado.setTabSize(4);
        jTextAreaMotivoDesativado.setToolTipText("");
        jTextAreaMotivoDesativado.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextAreaMotivoDesativado);

        jLabel2.setText("Livro");

        jLabel3.setText("Status  ");

        jLabel4.setText("Data da aquisição ");

        jLabel5.setText("Preço de compra  ");

        jTextFieldTituloLivro.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTextFieldTituloLivro.setEnabled(false);

        jSpinnerDataAquisicao.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(), new java.util.Date(-631077780000L), new java.util.Date(), java.util.Calendar.DAY_OF_MONTH));
        jSpinnerDataAquisicao.setEditor(new javax.swing.JSpinner.DateEditor(jSpinnerDataAquisicao, "dd/MM/yyyy"));
        jSpinnerDataAquisicao.setMaximumSize(new java.awt.Dimension(100, 26));
        jSpinnerDataAquisicao.setMinimumSize(new java.awt.Dimension(100, 26));
        jSpinnerDataAquisicao.setPreferredSize(new java.awt.Dimension(100, 26));

        jFormattedTextFieldPrecoCompra.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        jFormattedTextFieldPrecoCompra.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabel6.setText("R$");

        jSpinnerQuantidade.setModel(new javax.swing.SpinnerNumberModel(1, 1, 99, 1));

        jLabel7.setText("Quantidade");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTextFieldTituloLivro)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelMotivoDesativasao)
                            .addComponent(jLabel2)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jComboBoxstatusExemplar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(23, 23, 23)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jSpinnerDataAquisicao, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jFormattedTextFieldPrecoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel5))))
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSpinnerQuantidade)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(1, 1, 1)
                .addComponent(jTextFieldTituloLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel7))
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboBoxstatusExemplar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jSpinnerDataAquisicao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jFormattedTextFieldPrecoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel6)
                        .addComponent(jSpinnerQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelMotivoDesativasao)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonSalvar.setText("Salvar");
        jButtonSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButtonSalvar, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jTextFieldIDExemplar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldIDExemplar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonSalvar)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldIDExemplarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldIDExemplarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldIDExemplarActionPerformed

    private void jButtonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarActionPerformed
        try {
            validarPreenchimento();
            salvar();
            if (acao.equals(EnumAcao.Incluir)) {
                mensagem.sucesso("Exemplar incluído com sucesso!");
            } else if (acao.equals(EnumAcao.Editar)) {
                exemplar.setIdExemplar(Integer.parseInt(jTextFieldIDExemplar.getText()));
                mensagem.sucesso("Exemplar alterado com sucesso!");
            }
            visible = false;
            this.dispose();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jButtonSalvarActionPerformed

    private void jComboBoxstatusExemplarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxstatusExemplarActionPerformed

        if (jComboBoxstatusExemplar.getSelectedIndex() == 0) {
            jLabelMotivoDesativasao.setEnabled(false);
            jTextAreaMotivoDesativado.setEnabled(false);
        } else {
            jLabelMotivoDesativasao.setEnabled(true);
            jTextAreaMotivoDesativado.setEnabled(true);
        }

    }//GEN-LAST:event_jComboBoxstatusExemplarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaExemplar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaExemplar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaExemplar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaExemplar.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaExemplar dialog = new TelaExemplar(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonSalvar;
    private javax.swing.JComboBox<String> jComboBoxstatusExemplar;
    private javax.swing.JFormattedTextField jFormattedTextFieldPrecoCompra;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabelMotivoDesativasao;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSpinner jSpinnerDataAquisicao;
    private javax.swing.JSpinner jSpinnerQuantidade;
    private javax.swing.JTextArea jTextAreaMotivoDesativado;
    private javax.swing.JTextField jTextFieldIDExemplar;
    public javax.swing.JTextField jTextFieldTituloLivro;
    // End of variables declaration//GEN-END:variables

}
