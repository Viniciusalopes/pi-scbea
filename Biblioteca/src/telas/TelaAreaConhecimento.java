package telas;

import classes.AreaConhecimento;
import controle.ControleAreaConhecimento;
import enumeradores.EnumAcao;
import interfaces.ICRUDAreaConhecimento;
import interfaces.ITelaCadastro;
import utilidades.Mensagens;
import static utilidades.StringUtil.soTemNumeros;

public class TelaAreaConhecimento extends javax.swing.JDialog implements ITelaCadastro {

    private int id;
    private Mensagens mensagem = new Mensagens();
    private EnumAcao acao = null;
    private ICRUDAreaConhecimento controleAreaConhecimento = null;
    private AreaConhecimento areaConhecimento = null;
    private boolean visible = false;

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setAcao(EnumAcao acao) {
        this.acao = acao;
        this.setTitle(acao.toString() + "cadastro de Área de conhecimento");
    }

    @Override
    public void setVisible(boolean b) {
        try {
            controleAreaConhecimento = new ControleAreaConhecimento();
            if (acao.equals(EnumAcao.Incluir)) {
                limparCampos();
            } else {
                preencherCampos();
            }
        } catch (Exception e) {
            mensagem.erro(e);
        }
        visible = true;
        super.setVisible(b);
    }

    // incluir métodos aqui
    private void validarPreencimento() throws Exception {

        if (!soTemNumeros(jTextFieldCdd.getText())) {
            jTextFieldCdd.requestFocus();
            jTextFieldCdd.selectAll();
            throw new Exception("O código do CDD precisa ter apenas números!");
        }

        if (jTextFieldCdd.getText().trim().length() != 6) {
            jTextFieldCdd.requestFocus();
            jTextFieldCdd.selectAll();
            throw new Exception("O código do CDD precisa ter 6 dígitos!");

        }
    }

    private void preencherCampos() throws Exception {
        areaConhecimento = controleAreaConhecimento.buscarPeloId(id);
        jTextFieldCdd.setText(areaConhecimento.getCdd() + "");
        jTextFieldDescricao.setText(areaConhecimento.getDescricaoAreaConhecimento());
    }

    private void limparCampos() throws Exception {
        jTextFieldCdd.setText("");
        jTextFieldDescricao.setText("");
    }

    public TelaAreaConhecimento(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(rootPane);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextFieldCdd = new javax.swing.JTextField();
        jTextFieldDescricao = new javax.swing.JTextField();
        jButtonSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldCdd, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(273, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonSalvar)
                .addGap(29, 29, 29))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(jTextFieldCdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(jTextFieldDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 118, Short.MAX_VALUE)
                .addComponent(jButtonSalvar)
                .addGap(34, 34, 34))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarActionPerformed

        try {
            validarPreencimento();
            AreaConhecimento areaConhecimento = new AreaConhecimento();
            areaConhecimento.setCdd(Integer.parseInt(jTextFieldCdd.getText()));
            areaConhecimento.setDescricaoAreaConhecimento(jTextFieldDescricao.getText());
            controleAreaConhecimento.incluir(areaConhecimento);
            mensagem.sucesso("Área de conhecimento incluída com sucesso");
            visible = false;
            this.dispose();
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
            java.util.logging.Logger.getLogger(TelaAreaConhecimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaAreaConhecimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaAreaConhecimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaAreaConhecimento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaAreaConhecimento dialog = new TelaAreaConhecimento(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField jTextFieldCdd;
    private javax.swing.JTextField jTextFieldDescricao;
    // End of variables declaration//GEN-END:variables
}
