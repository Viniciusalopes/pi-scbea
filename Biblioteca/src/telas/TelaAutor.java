/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import classes.Autor;
import controle.ControleAutor;
import enumeradores.EnumAcao;
import interfaces.ICRUDAutor;
import interfaces.ITelaCadastro;
import utilidades.Mensagens;
import static utilidades.StringUtil.soTemLetras;

/**
 *
 * @author Joao Pedro
 */
public class TelaAutor extends javax.swing.JDialog implements ITelaCadastro {

    // atributos
    private int id;
    private EnumAcao acao = null;
    private Autor autor = null;
    private Mensagens mensagem = new Mensagens();
    private ICRUDAutor controleAutor = null;
    private boolean visible = false;

    //fim atributos
    //metodos
    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setAcao(EnumAcao acao) {
        this.acao = acao;
        this.setTitle(acao.toString() + " cadastro de autor ");
    }

    @Override
    public void setVisible(boolean b) {
        try {
            controleAutor = new ControleAutor();

            //popularControles();
            if (acao.equals(EnumAcao.Incluir)) {
                limparCampos();
            } else if (acao.equals(EnumAcao.Editar)) {
                autor = controleAutor.buscarPeloId(id);
                preencherCampos();
            }
        } catch (Exception e) {
            mensagem.erro(e);
        }
        visible = true;
        super.setVisible(b);
        
    }

    public void validarPreenchimento() throws Exception {
        //validarPreenchimento dos campos
        String campo = new String(jTextFieldNomeAutor.getText().trim());
        if (campo.length() == 0) {
            jTextFieldNomeAutor.requestFocus();
            throw new Exception("Informe o nome do autor!");
        }

        if (campo.length() < 2) {
            jTextFieldNomeAutor.requestFocus();
            jTextFieldNomeAutor.selectAll();
            throw new Exception("O nome do autor precisa ter pelo menos duas letras!");
        }

        if (!soTemLetras(campo)) {
            jTextFieldNomeAutor.requestFocus();
            jTextFieldNomeAutor.selectAll();
            throw new Exception("O nome do autor deve ter apenas letras e espaços!");
        }
    }

    private void limparCampos() {
        jTextFieldNomeAutor.setText("");
    }

    private void preencherCampos() {
        jTextFieldIdAutor.setText(String.format("%04d", autor.getIdAutor()));
        jTextFieldNomeAutor.setText(autor.getNomeAutor());
    }

    private void salvar() throws Exception {

        validarPreenchimento();

        autor = new Autor();
        autor.setNomeAutor(jTextFieldNomeAutor.getText());

        if (acao.equals(EnumAcao.Incluir)) {
            controleAutor.incluir(autor);
            mensagem.sucesso("Autor incluído com sucesso!");
        } else if (acao.equals(EnumAcao.Editar)) {
            autor.setIdAutor(Integer.parseInt(jTextFieldIdAutor.getText()));
            controleAutor.alterar(autor);
            mensagem.sucesso("Autor atualizado com sucesso!");
        }
        visible = false;
        this.dispose();
    }
    //fim metodos

    //construtor
    public TelaAutor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(rootPane);
    }
    // fim construtor

    // eventos
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jLabelID = new javax.swing.JLabel();
        jTextFieldIdAutor = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabelNomeAutor = new javax.swing.JLabel();
        jTextFieldNomeAutor = new javax.swing.JTextField();
        jButtonSalvar = new javax.swing.JButton();

        jTextField1.setText("jTextField1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabelID.setText("ID:");

        jTextFieldIdAutor.setEditable(false);
        jTextFieldIdAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldIdAutorActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Autor"));

        jLabelNomeAutor.setText("Nome");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldNomeAutor)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabelNomeAutor)
                        .addGap(0, 348, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabelNomeAutor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextFieldNomeAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
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
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldIdAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonSalvar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelID)
                    .addComponent(jTextFieldIdAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSalvar)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextFieldIdAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldIdAutorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldIdAutorActionPerformed

    private void jButtonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarActionPerformed
        try {
            salvar();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jButtonSalvarActionPerformed
    // fim eventos

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
            java.util.logging.Logger.getLogger(TelaAutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaAutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaAutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaAutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaAutor dialog = new TelaAutor(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel jLabelID;
    private javax.swing.JLabel jLabelNomeAutor;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextFieldIdAutor;
    private javax.swing.JTextField jTextFieldNomeAutor;
    // End of variables declaration//GEN-END:variables

}
