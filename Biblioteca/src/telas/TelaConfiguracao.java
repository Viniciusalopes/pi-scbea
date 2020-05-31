/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import classes.Configuracao;
import controle.ControleConfiguracao;
import javax.swing.JFileChooser;
import utilidades.Mensagens;

/**
 *
 * @author vovostudio
 */
public class TelaConfiguracao extends javax.swing.JDialog {

    private Configuracao configuracao = null;
    private Mensagens mensagem = new Mensagens();

    /**
     * Creates new form TelaConfiguracao
     */
    public TelaConfiguracao(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocationRelativeTo(rootPane);

        jSpinnerLimiteLivros.setValue(Integer.valueOf(Vai.CONFIGURACAO.getLimiteDeLivros()));
        jSpinnerDiasEmprestimo.setValue(Integer.valueOf(Vai.CONFIGURACAO.getDiasDeEmprestimo()));
        jFormattedTextFieldValorMultaDiaria.setValue(Float.valueOf(Vai.CONFIGURACAO.getValorMultaDiaria()));
        jTextFieldCaminhoBdCliente.setText(Vai.CONFIGURACAO.getCaminhoBdCliente());
        jTextFieldCaminhoBdServidor.setText(Vai.CONFIGURACAO.getCaminhoBdServidor());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelLimiteLivros = new javax.swing.JLabel();
        jSpinnerLimiteLivros = new javax.swing.JSpinner();
        jLabelDiasEmprestimo = new javax.swing.JLabel();
        jSpinnerDiasEmprestimo = new javax.swing.JSpinner();
        jLabelValorMultaDiaria = new javax.swing.JLabel();
        jFormattedTextFieldValorMultaDiaria = new javax.swing.JFormattedTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jLabelCaminhoBdCliente = new javax.swing.JLabel();
        jTextFieldCaminhoBdCliente = new javax.swing.JTextField();
        jButtonNavegar = new javax.swing.JButton();
        jLabelCaminhoBdServidor = new javax.swing.JLabel();
        jTextFieldCaminhoBdServidor = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jButtonSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("CONFIGURAÇÃO");
        setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        setResizable(false);

        jLabelLimiteLivros.setText("Limite de livros emprestados por colaborador:");

        jSpinnerLimiteLivros.setModel(new javax.swing.SpinnerNumberModel(5, 1, 10, 1));

        jLabelDiasEmprestimo.setText("Quantidade de dias para empréstimo ou renovação:");

        jSpinnerDiasEmprestimo.setModel(new javax.swing.SpinnerNumberModel(7, 1, 365, 1));

        jLabelValorMultaDiaria.setText("Valor da multa por dia de atraso: R$");

        jFormattedTextFieldValorMultaDiaria.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#,##0.00"))));
        jFormattedTextFieldValorMultaDiaria.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        jLabelCaminhoBdCliente.setText("Caminho da base de dados local:");

        jTextFieldCaminhoBdCliente.setEditable(false);

        jButtonNavegar.setText("[...]");
        jButtonNavegar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNavegarActionPerformed(evt);
            }
        });

        jLabelCaminhoBdServidor.setText("Caminho da base de dados remota:");

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
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelCaminhoBdCliente)
                            .addComponent(jLabelCaminhoBdServidor)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelValorMultaDiaria, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelDiasEmprestimo, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelLimiteLivros, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSpinnerLimiteLivros, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jSpinnerDiasEmprestimo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jFormattedTextFieldValorMultaDiaria, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(109, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jTextFieldCaminhoBdServidor, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldCaminhoBdCliente))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonNavegar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
            .addComponent(jSeparator2)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinnerLimiteLivros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelLimiteLivros))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelDiasEmprestimo)
                    .addComponent(jSpinnerDiasEmprestimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFormattedTextFieldValorMultaDiaria, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelValorMultaDiaria))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelCaminhoBdCliente)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCaminhoBdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNavegar))
                .addGap(22, 22, 22)
                .addComponent(jLabelCaminhoBdServidor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldCaminhoBdServidor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSalvar)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonNavegarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNavegarActionPerformed

        try {
            // FONTE: https://www.rgagnon.com/javadetails/java-0370.html
            JFileChooser chooser = new JFileChooser();
            String choosertitle = "Selecionar diretório";

            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File(Vai.CONFIGURACAO.getCaminhoBdCliente()));
            chooser.setDialogTitle(choosertitle);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            // disable the "All files" option.
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                jTextFieldCaminhoBdCliente.setText(chooser.getSelectedFile() + "/");
            } else {
                mensagem.informacao("O caminho da base de dados local não foi alterado.");
            }
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jButtonNavegarActionPerformed

    private void jButtonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarActionPerformed
        try {
            configuracao = new Configuracao(
                    Integer.valueOf(jSpinnerDiasEmprestimo.getValue().toString()),
                    Integer.valueOf(jSpinnerLimiteLivros.getValue().toString()),
                    Float.parseFloat(jFormattedTextFieldValorMultaDiaria.getValue().toString()),
                    jTextFieldCaminhoBdCliente.getText(), jTextFieldCaminhoBdServidor.getText());
            new ControleConfiguracao().atualizar(configuracao);

            mensagem.sucesso("Configuração salva com sucesso!");

        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jButtonSalvarActionPerformed

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
            java.util.logging.Logger.getLogger(TelaConfiguracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaConfiguracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaConfiguracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaConfiguracao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaConfiguracao dialog = new TelaConfiguracao(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButtonNavegar;
    private javax.swing.JButton jButtonSalvar;
    private javax.swing.JFormattedTextField jFormattedTextFieldValorMultaDiaria;
    private javax.swing.JLabel jLabelCaminhoBdCliente;
    private javax.swing.JLabel jLabelCaminhoBdServidor;
    private javax.swing.JLabel jLabelDiasEmprestimo;
    private javax.swing.JLabel jLabelLimiteLivros;
    private javax.swing.JLabel jLabelValorMultaDiaria;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSpinner jSpinnerDiasEmprestimo;
    private javax.swing.JSpinner jSpinnerLimiteLivros;
    private javax.swing.JTextField jTextFieldCaminhoBdCliente;
    private javax.swing.JTextField jTextFieldCaminhoBdServidor;
    // End of variables declaration//GEN-END:variables
}
