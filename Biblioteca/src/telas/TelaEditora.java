/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import classes.Editora;
import controle.ControleEditora;
import enumeradores.EnumAcao;
import interfaces.ICRUDEditora;
import interfaces.ITelaCadastro;
import utilidades.Mensagens;
import static utilidades.StringUtil.nomeEditoraValido;

/**
 *
 * @author Vinicius
 */
public class TelaEditora extends javax.swing.JDialog implements ITelaCadastro {

    //--- ATRIBUTOS ----------------------------------------------------------->
    //
    private int id;
    private EnumAcao acao = null;
    private ICRUDEditora controleEditora = null;
    private Mensagens mensagem = null;
    private Editora editora = null;
    private boolean visible = false;

    //
    //--- FIM ATRIBUTOS -------------------------------------------------------|
    //
    //--- OVERRIDE ------------------------------------------------------------>
    //
    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setAcao(EnumAcao acao) {
        this.acao = acao;
        this.setTitle(acao.toString() + " cadastro de editora");
    }

    @Override
    public void setVisible(boolean b) {
        try {
            controleEditora = new ControleEditora();
            mensagem = new Mensagens();
            if (acao.equals(EnumAcao.Incluir)) {
                limparCampos();
            } else if (acao.equals(EnumAcao.Editar)) {
                editora = controleEditora.buscarPeloId(id);
                preencherCampos();
            }
        } catch (Exception e) {
            mensagem.erro(e);
        }
        visible = true;
        super.setVisible(b);
    }

    //--- FIM OVERRIDE --------------------------------------------------------|
    //
    //--- MÉTODOS ------------------------------------------------------------->
    //
    public void validarPreenchimento() throws Exception {
        //validarPreenchimento dos campos
        String campo = new String(jTextFieldNomeEditora.getText().trim());
        if (campo.length() == 0) {
            jTextFieldNomeEditora.requestFocus();
            throw new Exception("Informe o nome do editora!");
        }

        if (campo.length() < 2) {
            jTextFieldNomeEditora.requestFocus();
            jTextFieldNomeEditora.selectAll();
            throw new Exception("O nome da editora precisa ter pelo menos duas letras!");
        }

        if (!nomeEditoraValido(campo)) {
            jTextFieldNomeEditora.requestFocus();
            jTextFieldNomeEditora.selectAll();
            throw new Exception("O nome da editora deve ter apenas letras e espaços!");
        }
    }

    private void limparCampos() {
        jTextFieldIdEditora.setText("");
        jTextFieldNomeEditora.setText("");
    }

    private void preencherCampos() {

        jTextFieldIdEditora.setText(editora.getIdEditora() + "");
        jTextFieldNomeEditora.setText(editora.getNomeEditora());
    }

    private void salvar() throws Exception {
        try {
            validarPreenchimento();

            editora = new Editora();
            editora.setNomeEditora(jTextFieldNomeEditora.getText().trim());

            if (acao.equals(EnumAcao.Incluir)) {
                controleEditora.incluir(editora);
                mensagem.sucesso("Editora incluída com sucesso!");
            } else if (acao.equals(EnumAcao.Editar)) {
                editora.setIdEditora(Integer.parseInt(jTextFieldIdEditora.getText()));
                controleEditora.alterar(editora);
                mensagem.sucesso("Editora alterada com sucesso!");
            }

            visible = false;
            this.dispose();

        } catch (Exception e) {
            mensagem.erro(new Exception("Erro ao salvar: \n" + e.getMessage()));
        }
    }

//    private void validarPreenchimento() throws Exception {
//
//        String texto = jTextFieldNomeEditora.getText().trim();
//
//        if (texto.length() == 0) {
//            throw new Exception("Informe o nome da editora!");
//        }
//
//        if (!StringUtil.nomeEditoraValido(texto)) {
//            throw new Exception("Nome da editora possui caracteres inválidos!");
//        }
//    }
    //--- FIM MÉTODOS ---------------------------------------------------------|
    //
    //-- CONSTRUTOR ----------------------------------------------------------->
    //
    /**
     * Creates new form TelaEditora
     */
    public TelaEditora(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(rootPane);
    }
    //-- CONSTRUTOR ------------------------------------------------------------|

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSeparator2 = new javax.swing.JSeparator();
        jLabelIdEditora = new javax.swing.JLabel();
        jTextFieldIdEditora = new javax.swing.JTextField();
        jButtonSalvar = new javax.swing.JButton();
        jPanelDadosEdiitora = new javax.swing.JPanel();
        jTextFieldNomeEditora = new javax.swing.JTextField();
        jLabelNomeEditora = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabelIdEditora.setText("ID: ");

        jTextFieldIdEditora.setFont(new java.awt.Font("Dialog", 3, 12)); // NOI18N
        jTextFieldIdEditora.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldIdEditora.setText("  ");
        jTextFieldIdEditora.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTextFieldIdEditora.setEnabled(false);

        jButtonSalvar.setText("Salvar");
        jButtonSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarActionPerformed(evt);
            }
        });

        jPanelDadosEdiitora.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados da editora"));

        jTextFieldNomeEditora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldNomeEditoraActionPerformed(evt);
            }
        });

        jLabelNomeEditora.setText("Nome  ");

        javax.swing.GroupLayout jPanelDadosEdiitoraLayout = new javax.swing.GroupLayout(jPanelDadosEdiitora);
        jPanelDadosEdiitora.setLayout(jPanelDadosEdiitoraLayout);
        jPanelDadosEdiitoraLayout.setHorizontalGroup(
            jPanelDadosEdiitoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDadosEdiitoraLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanelDadosEdiitoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldNomeEditora)
                    .addGroup(jPanelDadosEdiitoraLayout.createSequentialGroup()
                        .addComponent(jLabelNomeEditora)
                        .addGap(0, 264, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        jPanelDadosEdiitoraLayout.setVerticalGroup(
            jPanelDadosEdiitoraLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDadosEdiitoraLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(jLabelNomeEditora)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNomeEditora, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(19, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonSalvar)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabelIdEditora)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldIdEditora, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addComponent(jPanelDadosEdiitora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldIdEditora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelIdEditora))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanelDadosEdiitora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonSalvar)
                        .addGap(20, 20, 20))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarActionPerformed
        try {
            salvar();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jButtonSalvarActionPerformed

    private void jTextFieldNomeEditoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNomeEditoraActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNomeEditoraActionPerformed

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
            java.util.logging.Logger.getLogger(TelaEditora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaEditora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaEditora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaEditora.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaEditora dialog = new TelaEditora(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabelIdEditora;
    private javax.swing.JLabel jLabelNomeEditora;
    private javax.swing.JPanel jPanelDadosEdiitora;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField jTextFieldIdEditora;
    private javax.swing.JTextField jTextFieldNomeEditora;
    // End of variables declaration//GEN-END:variables
}
