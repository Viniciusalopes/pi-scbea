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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldDescricaoAreaConhecimento = new javax.swing.JTextField();
        jTextFieldCdd = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(567, 427));

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

        jLabel1.setText("Código CDD");
        jPanelDadosAreaConhecimento.add(jLabel1);
        jLabel1.setBounds(10, 30, 66, 16);

        jLabel2.setText("Derscrição");
        jPanelDadosAreaConhecimento.add(jLabel2);
        jLabel2.setBounds(170, 30, 63, 16);
        jPanelDadosAreaConhecimento.add(jTextFieldDescricaoAreaConhecimento);
        jTextFieldDescricaoAreaConhecimento.setBounds(170, 50, 340, 24);
        jPanelDadosAreaConhecimento.add(jTextFieldCdd);
        jTextFieldCdd.setBounds(10, 50, 79, 24);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabelId)
                .addGap(6, 6, 6)
                .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanelDadosAreaConhecimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButtonSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
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

        jPanelDadosAreaConhecimento.getAccessibleContext().setAccessibleName("Dados para área de conhecimento");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarActionPerformed

        try {
            validarPreencimento();
            AreaConhecimento areaConhecimento = new AreaConhecimento();
            areaConhecimento.setCdd(Integer.parseInt(jTextFieldCdd.getText()));
            areaConhecimento.setDescricaoAreaConhecimento(jTextFieldDescricaoAreaConhecimento.getText());
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
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelId;
    private javax.swing.JPanel jPanelDadosAreaConhecimento;
    private javax.swing.JTextField jTextFieldCdd;
    private javax.swing.JTextField jTextFieldDescricaoAreaConhecimento;
    private javax.swing.JTextField jTextFieldID;
    // End of variables declaration//GEN-END:variables
}
