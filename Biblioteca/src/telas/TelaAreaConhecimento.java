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
        jTextFieldID.setText(String.format("%04d", areaConhecimento.getIdAreaConhecimento()));
        jTextFieldCdd.setText(areaConhecimento.getCdd() + "");
        jTextFieldDescricaoAreaConhecimento.setText(areaConhecimento.getDescricaoAreaConhecimento());
    }

    private void limparCampos() throws Exception {
        jTextFieldCdd.setText("");
        jTextFieldDescricaoAreaConhecimento.setText("");
    }

    public TelaAreaConhecimento(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(rootPane);

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButtonSalvar = new javax.swing.JButton();
        jLabelId = new javax.swing.JLabel();
        jTextFieldID = new javax.swing.JTextField();
        jPanelDadosAreaConhecimento = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldDescricaoAreaConhecimento = new javax.swing.JTextField();
        jTextFieldCdd = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jButtonSalvar.setText("Salvar");
        jButtonSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSalvarActionPerformed(evt);
            }
        });

        jLabelId.setText("ID:");

        jTextFieldID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldID.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTextFieldID.setEnabled(false);

        jPanelDadosAreaConhecimento.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados para área de conhecimento"));
        jPanelDadosAreaConhecimento.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelDadosAreaConhecimento.setPreferredSize(new java.awt.Dimension(522, 81));
        jPanelDadosAreaConhecimento.setLayout(null);

        jLabel2.setText("Derscrição");
        jPanelDadosAreaConhecimento.add(jLabel2);
        jLabel2.setBounds(100, 30, 340, 16);
        jPanelDadosAreaConhecimento.add(jTextFieldDescricaoAreaConhecimento);
        jTextFieldDescricaoAreaConhecimento.setBounds(100, 50, 340, 24);
        jPanelDadosAreaConhecimento.add(jTextFieldCdd);
        jTextFieldCdd.setBounds(10, 50, 79, 24);

        jLabel3.setText("Código CDD");
        jPanelDadosAreaConhecimento.add(jLabel3);
        jLabel3.setBounds(10, 30, 80, 16);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabelId)
                            .addGap(6, 6, 6)
                            .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jPanelDadosAreaConhecimento, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelId)
                    .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelDadosAreaConhecimento, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonSalvar)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarActionPerformed
        try {
            validarPreencimento();
            AreaConhecimento ac = new AreaConhecimento();
            if (acao.equals(EnumAcao.Incluir)) {
                ac.setIdAreaConhecimento(id);
                ac.setCdd(Integer.parseInt(jTextFieldCdd.getText()));
                ac.setDescricaoAreaConhecimento(jTextFieldDescricaoAreaConhecimento.getText());
                controleAreaConhecimento.incluir(ac);
                mensagem.sucesso("Área de conhecimento incluída com sucesso");
            } else if (acao.equals(EnumAcao.Editar)) {
                ac.setIdAreaConhecimento(id);
                ac.setCdd(Integer.parseInt(jTextFieldCdd.getText()));
                ac.setDescricaoAreaConhecimento(jTextFieldDescricaoAreaConhecimento.getText());
                controleAreaConhecimento.alterar(ac);
                mensagem.sucesso("Área de conhecimento editada com sucesso");
            }
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabelId;
    private javax.swing.JPanel jPanelDadosAreaConhecimento;
    private javax.swing.JTextField jTextFieldCdd;
    private javax.swing.JTextField jTextFieldDescricaoAreaConhecimento;
    private javax.swing.JTextField jTextFieldID;
    // End of variables declaration//GEN-END:variables
}
