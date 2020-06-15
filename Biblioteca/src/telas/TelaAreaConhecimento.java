package telas;

import classes.AreaConhecimento;
import controle.ControleAreaConhecimento;
import enumeradores.EnumAcao;
import interfaces.ICRUDAreaConhecimento;
import interfaces.ITelaCadastro;
import utilidades.Mensagens;
import static utilidades.StringUtil.*;

public class TelaAreaConhecimento extends javax.swing.JDialog implements ITelaCadastro {

//------------------------------------------------------------------------------
//VARIÁVEIS DO ESCOPO TELA AREA CONHECIMENTO
//------------------------------------------------------------------------------    
    private int id;
    private Mensagens mensagem = new Mensagens();
    private EnumAcao acao = null;
    private ICRUDAreaConhecimento controleAreaConhecimento = null;
    private AreaConhecimento areaConhecimento = null;
    private boolean visible = false;

//------------------------------------------------------------------------------
//MÉTODOS HERDADOS DA TELA PRINCIPAL
//------------------------------------------------------------------------------       
    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setAcao(EnumAcao acao) {
        this.acao = acao;
        this.setTitle(acao.toString() + " cadastro de Área de conhecimento");
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

//------------------------------------------------------------------------------
//MÉTODOS
//------------------------------------------------------------------------------    
    private void validarPreenchimento() throws Exception {

        if (jTextFieldCdd.getText().trim().substring(0, 1).equals("0")) {
            throw new Exception("O código do CDD precisa iniciar com um número diferente de 0!");
        }

        if (!soTemNumeros(jTextFieldCdd.getText().trim().replace(".", ""))) {
            jTextFieldCdd.requestFocus();
            jTextFieldCdd.selectAll();
            throw new Exception("O código do CDD precisa ter apenas ponto e números!");
        }

        if (!tamanhoEntre(jTextFieldCdd.getText().trim().replace(".", ""), 3, 7)) {
            throw new Exception("O código do CDD precisa ter nó mínimo 3 e no máximo 7 números!");
        }
    }

    private void preencherCampos() throws Exception {
        areaConhecimento = controleAreaConhecimento.buscarPeloId(id);
        jTextFieldID.setText(areaConhecimento.getIdAreaConhecimento() + "");
        String cdd = String.format("%07d", areaConhecimento.getCdd());
        jTextFieldCdd.setText(cdd.substring(0, 3) + "." + cdd.replace(cdd.substring(0, 3), ""));
        jTextFieldDescricaoAreaConhecimento.setText(areaConhecimento.getDescricaoAreaConhecimento());
    }

    private void limparCampos() throws Exception {
        jTextFieldCdd.setText("");
        jTextFieldDescricaoAreaConhecimento.setText("");
    }

    private void salvar() throws Exception {
        validarPreenchimento();
        AreaConhecimento a = new AreaConhecimento();
        a.setCdd(Integer.parseInt(jTextFieldCdd.getText().trim().replace(".", "")));
        a.setDescricaoAreaConhecimento(jTextFieldDescricaoAreaConhecimento.getText().trim());

        if (acao.equals(EnumAcao.Incluir)) {
            controleAreaConhecimento.incluir(a);
            mensagem.sucesso("Área de conhecimento incluída com sucesso!");
        } else if (acao.equals(EnumAcao.Editar)) {
            a.setIdAreaConhecimento(id);
            controleAreaConhecimento.alterar(a);
            mensagem.sucesso("Área de conhecimento editada com sucesso!");
        }

        visible = false;
        this.dispose();
    }

//------------------------------------------------------------------------------
//CONSTRUTOR TELA
//------------------------------------------------------------------------------    
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

        jTextFieldID.setFont(new java.awt.Font("Dialog", 3, 12)); // NOI18N
        jTextFieldID.setForeground(new java.awt.Color(102, 102, 102));
        jTextFieldID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldID.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTextFieldID.setEnabled(false);

        jPanelDadosAreaConhecimento.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados para área de conhecimento"));
        jPanelDadosAreaConhecimento.setMinimumSize(new java.awt.Dimension(0, 0));
        jPanelDadosAreaConhecimento.setPreferredSize(new java.awt.Dimension(522, 81));
        jPanelDadosAreaConhecimento.setLayout(null);

        jLabel2.setText("Derscrição");
        jPanelDadosAreaConhecimento.add(jLabel2);
        jLabel2.setBounds(130, 30, 340, 15);
        jPanelDadosAreaConhecimento.add(jTextFieldDescricaoAreaConhecimento);
        jTextFieldDescricaoAreaConhecimento.setBounds(130, 50, 530, 23);
        jPanelDadosAreaConhecimento.add(jTextFieldCdd);
        jTextFieldCdd.setBounds(10, 50, 110, 23);

        jLabel3.setText("Código CDD");
        jPanelDadosAreaConhecimento.add(jLabel3);
        jLabel3.setBounds(10, 30, 100, 15);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabelId)
                        .addGap(6, 6, 6)
                        .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButtonSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanelDadosAreaConhecimento, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(26, 26, 26))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonSalvar)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

//------------------------------------------------------------------------------
//ELEMENTOS COM EVENTO
//------------------------------------------------------------------------------      
    private void jButtonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarActionPerformed
        try {
            salvar();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jButtonSalvarActionPerformed

//------------------------------------------------------------------------------
//CONFIGURAÇÕES TELA (GERADO NA ABA DESIGN)
//------------------------------------------------------------------------------
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

//------------------------------------------------------------------------------
//VARIÁVEIS DOS ELEMENTOS DA TELA
//------------------------------------------------------------------------------  

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
