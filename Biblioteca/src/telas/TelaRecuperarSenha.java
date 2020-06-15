/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import classes.Colaborador;
import controle.ControleColaborador;
import controle.ControleLogin;
import enumeradores.EnumPerfil;
import interfaces.ICRUDColaborador;
import java.util.ArrayList;
import utilidades.Hash;
import utilidades.Mensagens;

/**
 *
 * @author vovostudio
 */
public class TelaRecuperarSenha extends javax.swing.JDialog {

    //--- ATRIBUTOS ----------------------------------------------------------->
    private ICRUDColaborador controleColaborador = null;
    private ArrayList<Colaborador> colecaoColaborador = null;
    private Colaborador usuario = null;
    private ControleLogin controleLogin = null;
    private Mensagens mensagem = null;
    private String senhaAdministrador = "";
    private String matriculaAdministrador = "";
    private String senhaUsuario = "";

    //--- FIM ATRIBUTOS -------------------------------------------------------|
    //
    //--- MÉTODOS ------------------------------------------------------------->
    //
    private void popularControles() throws Exception {

        jComboBoxAdministradores.removeAllItems();
        for (Colaborador colaborador : colecaoColaborador) {
            if (colaborador.getPerfil().equals(EnumPerfil.ADMINISTRADOR)) {
                jComboBoxAdministradores.addItem(colaborador.getNomeColaborador());
            }
        }
        jComboBoxAdministradores.setSelectedIndex(-1);
    }

    private void validarPreenchimento() throws Exception {
        if (jComboBoxAdministradores.getSelectedIndex() == -1) {
            throw new Exception("Selecione um administrador!");
        }

        senhaAdministrador = new String(jPasswordFieldAdministrador.getPassword());
        senhaUsuario = new String(jPasswordFieldNovaSenha.getPassword());

        try {
            controleLogin.autenticar(matriculaAdministrador, senhaAdministrador);
        } catch (Exception e) {
            throw new Exception("Senha do administrador inválida!\n" + e.getMessage());
        }

        usuario = controleLogin.validarColaborador(jTextFieldLogin.getText());
        controleLogin.validarSenha(senhaUsuario);
    }

    private void confirmar() throws Exception {
        validarPreenchimento();
        usuario.setSenha(Hash.criptografar(senhaUsuario, "SHA-256"));
        controleColaborador.alterar(usuario);
        mensagem.sucesso("Senha atualizada com sucesso!");
        this.dispose();
    }

    private void setMatriculaAdministrador() throws Exception {
        if (jComboBoxAdministradores.getSelectedIndex() > -1) {
            for (Colaborador administrador : colecaoColaborador) {
                if (administrador.getNomeColaborador().equalsIgnoreCase(jComboBoxAdministradores.getSelectedItem().toString())) {
                    matriculaAdministrador = administrador.getMatricula() + "";
                    break;
                }
            }
        }
    }

    //
    //--- FIM MÉTODOS ---------------------------------------------------------|
    //
    //--- CONSTRUTOR ---------------------------------------------------------->
    //
    /**
     * Creates new form TelaRecuperarSenha
     */
    public TelaRecuperarSenha(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(rootPane);
        mensagem = new Mensagens();
        try {
            controleColaborador = new ControleColaborador();
            colecaoColaborador = controleColaborador.listar();
            controleLogin = new ControleLogin();
            popularControles();
            jComboBoxAdministradores.requestFocus();

        } catch (Exception e) {
            mensagem.erro(new Exception(e.getMessage()));
        }
    }
    //
    //--- FIM CONSTRUTOR ------------------------------------------------------|

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBoxAdministradores = new javax.swing.JComboBox<>();
        jPasswordFieldAdministrador = new javax.swing.JPasswordField();
        jLabel1 = new javax.swing.JLabel();
        jLabelSenhaAdministrador = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPasswordFieldNovaSenha = new javax.swing.JPasswordField();
        jButtonConfirmar = new javax.swing.JButton();
        jTextFieldLogin = new javax.swing.JTextField();
        jLabelLogin = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Recuperar senha");

        jComboBoxAdministradores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAdministradoresActionPerformed(evt);
            }
        });

        jPasswordFieldAdministrador.setVerifyInputWhenFocusTarget(false);
        jPasswordFieldAdministrador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPasswordFieldAdministradorKeyReleased(evt);
            }
        });

        jLabel1.setText("Administrador");

        jLabelSenhaAdministrador.setText("Senha do Administrador");

        jLabel2.setText("Sua nova senha");

        jPasswordFieldNovaSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jPasswordFieldNovaSenhaKeyReleased(evt);
            }
        });

        jButtonConfirmar.setText("Confirmar");
        jButtonConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfirmarActionPerformed(evt);
            }
        });

        jLabelLogin.setText("Login");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel1)
                    .addComponent(jLabelLogin)
                    .addComponent(jTextFieldLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxAdministradores, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelSenhaAdministrador)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButtonConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2)
                            .addComponent(jPasswordFieldNovaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPasswordFieldAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelSenhaAdministrador))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxAdministradores, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordFieldAdministrador, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelLogin)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPasswordFieldNovaSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButtonConfirmar)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //--- EVENTOS ------------------------------------------------------------->
    private void jComboBoxAdministradoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxAdministradoresActionPerformed
        try {
            setMatriculaAdministrador();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jComboBoxAdministradoresActionPerformed

    private void jButtonConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfirmarActionPerformed
        try {
            confirmar();
        } catch (Exception e) {
            mensagem.erro(e);
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_jButtonConfirmarActionPerformed

    private void jPasswordFieldAdministradorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordFieldAdministradorKeyReleased
         try {
            String senha = new String(jPasswordFieldAdministrador.getPassword());
            if (senha.length() >= 6) {
                evt.consume();
                jPasswordFieldAdministrador.setText(senha.substring(0, 6));
            }
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jPasswordFieldAdministradorKeyReleased

    private void jPasswordFieldNovaSenhaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPasswordFieldNovaSenhaKeyReleased
         try {
            String senha = new String(jPasswordFieldNovaSenha.getPassword());
            if (senha.length() >= 6) {
                evt.consume();
                jPasswordFieldNovaSenha.setText(senha.substring(0, 6));
            }
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jPasswordFieldNovaSenhaKeyReleased
    //
    //--- FIM EVENTOS ---------------------------------------------------------|
    //
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
            java.util.logging.Logger.getLogger(TelaRecuperarSenha.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaRecuperarSenha.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaRecuperarSenha.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaRecuperarSenha.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaRecuperarSenha dialog = new TelaRecuperarSenha(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButtonConfirmar;
    private javax.swing.JComboBox<String> jComboBoxAdministradores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelLogin;
    private javax.swing.JLabel jLabelSenhaAdministrador;
    private javax.swing.JPasswordField jPasswordFieldAdministrador;
    private javax.swing.JPasswordField jPasswordFieldNovaSenha;
    private javax.swing.JTextField jTextFieldLogin;
    // End of variables declaration//GEN-END:variables
}
