/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import classes.Exemplar;
import enumeradores.EnumAcao;
import interfaces.ICRUDExemplar;
import controle.ControleExemplar;
import controle.ControleLivro;
import enumeradores.EnumTipoStatus;
import interfaces.ICRUDLivro;
import utilidades.Mensagens;
import interfaces.ITelaCadastro;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
    private int id;
    private int idLivro;
    private EnumAcao acao = null;
    private ICRUDExemplar controleExemplar = null;
    private Mensagens mensagens = null;
    private Exemplar exemplar = null;
    private boolean visible = false;
    private Date dataAquisicao = null;

    private ICRUDLivro controleLivro = null;

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
            controleExemplar = new ControleExemplar();
            controleLivro = new ControleLivro();

            mensagens = new Mensagens();
            if (acao.equals(EnumAcao.Incluir)) {
                popularControles();
                limparCampos();
            } else if (acao.equals(EnumAcao.Editar)) {
                exemplar = controleExemplar.buscarPeloId(id);
                preencherCampos();
            }
        } catch (Exception e) {
            mensagens.erro(e);
        }
        visible = true;
        super.setVisible(b);
    }
    //--------------------end override------------------------------
    //metodos 

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }

    private void popularControles() throws Exception {
        jTextFieldIDExemplar.setText(String.format("%04d", id));
        jComboBoxstatusExemplar.addItem(EnumTipoStatus.ATIVO.toString());
        jComboBoxstatusExemplar.addItem(EnumTipoStatus.INATIVO.toString());
    }

    private void salvar() throws Exception {
        try {
//            validardataAquisicao();
//            validarprecoCompra();

            exemplar = new Exemplar();
//            exemplar.setDataAquisicao(jTextFielddataAquisicao.getText());

            if (acao.equals(EnumAcao.Incluir)) {
                controleExemplar.incluir(exemplar);
                mensagens.sucesso("Exemplar incluído com sucesso!");
            } else if (acao.equals(EnumAcao.Editar)) {
                exemplar.setIdExemplar(Integer.parseInt(jTextFieldIDExemplar.getText()));
                controleExemplar.alterar(exemplar);
                mensagens.sucesso("Exemplar alterado com sucesso!");
            }

        } catch (Exception e) {
            mensagens.erro(new Exception("Erro ao salvar: \n" + e.getMessage()));
            visible = false;
            this.dispose();

        }
    }

    //validador de prenchimentos dos campos
    public void validarpreenchimento() throws Exception {
        Date data = new Date(jTextFielddataAquisicao.getText().trim());
        //fonte :https://www.devmedia.com.br/manipulando-datas-em-java/21765

        SimpleDateFormat dataDigitada = new SimpleDateFormat("dd/MM/yyyy");
        if (dataDigitada.DATE_FIELD == 0) {
            jTextFielddataAquisicao.requestFocus();
            throw new Exception("imforme a data de aquisão do exemplar");
            //vai pegar a data digitaca pelo usuario que vai ser convertida 
        }
    }

    public void validarprecoCompra() throws Exception {
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("R$ 0.00");
    }

    private void limparCampos() throws Exception {

        jTextFieldIDExemplar.setText("");
        jTextFielddataAquisicao.setText("");
        jTextAreamotivoDesativaçso.setText("");
        jTextFielddataAquisicao.setText("");

    }

    private void preencherCampos() {
        jTextFieldIDExemplar.setText(String.format("%04d", exemplar.getIdExemplar()));
        jTextFielddataAquisicao.setText(String.format("dd/MM/yyyy", exemplar.getDataAquisicao()));
        jTextAreamotivoDesativaçso.setText(String.format("%04d", jTextAreamotivoDesativaçso));
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

        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldIDExemplar = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jComboBoxstatusExemplar = new javax.swing.JComboBox<>();
        jTextFieldprecoCompra = new javax.swing.JTextField();
        jLabelMotivoDesativasao = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreamotivoDesativaçso = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextFielddataAquisicao = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextFieldTituloLivro = new javax.swing.JTextField();
        jButtonSalvar = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("ID");

        jTextFieldIDExemplar.setEditable(false);
        jTextFieldIDExemplar.setText("                          ");
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

        jTextFieldprecoCompra.setText("      ");

        jLabelMotivoDesativasao.setText("Motivo da  desativação ");

        jTextAreamotivoDesativaçso.setColumns(20);
        jTextAreamotivoDesativaçso.setRows(2);
        jTextAreamotivoDesativaçso.setToolTipText("");
        jScrollPane1.setViewportView(jTextAreamotivoDesativaçso);

        jLabel2.setText("Livro");

        jLabel3.setText("Status  ");

        jLabel4.setText("Data da aquisição ");

        jTextFielddataAquisicao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFielddataAquisicaoActionPerformed(evt);
            }
        });

        jLabel5.setText("Preço de compra  ");

        jTextFieldTituloLivro.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTextFieldTituloLivro.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelMotivoDesativasao)
                            .addComponent(jLabel2))
                        .addGap(347, 347, 347))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(jTextFieldTituloLivro)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBoxstatusExemplar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFielddataAquisicao, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4))
                                .addGap(43, 43, 43)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jTextFieldprecoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
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
                    .addComponent(jLabel4))
                .addGap(0, 0, 0)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldprecoCompra, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxstatusExemplar, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFielddataAquisicao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
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

    private void jTextFielddataAquisicaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFielddataAquisicaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFielddataAquisicaoActionPerformed

    private void jButtonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarActionPerformed
        try {
            salvar();
        } catch (Exception e) {
            mensagens.erro(e);
        }
    }//GEN-LAST:event_jButtonSalvarActionPerformed

    private void jComboBoxstatusExemplarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxstatusExemplarActionPerformed

        if (jComboBoxstatusExemplar.getSelectedIndex() == 0) {
            jLabelMotivoDesativasao.setEnabled(false);
            jTextAreamotivoDesativaçso.setEnabled(false);
        } else {
            jLabelMotivoDesativasao.setEnabled(true);
            jTextAreamotivoDesativaçso.setEnabled(true);
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabelMotivoDesativasao;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextAreamotivoDesativaçso;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextFieldIDExemplar;
    public javax.swing.JTextField jTextFieldTituloLivro;
    private javax.swing.JTextField jTextFielddataAquisicao;
    private javax.swing.JTextField jTextFieldprecoCompra;
    // End of variables declaration//GEN-END:variables

}
