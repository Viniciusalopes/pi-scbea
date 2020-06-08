/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import classes.AreaConhecimento;
import classes.Autor;
import classes.Editora;
import classes.Livro;
import controle.ControleAreaConhecimento;
import controle.ControleAutor;
import controle.ControleEditora;
import controle.ControleLivro;
import enumeradores.EnumAcao;
import interfaces.ICRUDAreaConhecimento;
import interfaces.ICRUDAutor;
import interfaces.ICRUDEditora;
import interfaces.ICRUDLivro;
import interfaces.ITelaCadastro;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.SpinnerNumberModel;
import utilidades.Mensagens;
import utilidades.ValidadorISBN;

/**
 *
 * @author Vinicius
 */
public class TelaLivro extends javax.swing.JDialog implements ITelaCadastro {

    //--- ATRIBUTOS ----------------------------------------------------------->
    //
    private int id;
    private EnumAcao acao = null;
    private ICRUDLivro controleLivro = null;
    private ICRUDEditora controleEditora = null;
    private ArrayList<Editora> editoras = null;
    private ICRUDAutor controleAutor = null;
    private ArrayList<Autor> autores = null;
    private ICRUDAreaConhecimento controleAreaConhecimento = null;
    private ArrayList<AreaConhecimento> areasConhecimento = null;
    private Mensagens mensagem = null;
    private Livro livro = null;
    private boolean visible = false;
    int anoHoje = 0;

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
        this.setTitle(acao.toString() + " cadastro de livro");
    }

    @Override
    public void setVisible(boolean b) {
        try {
            controleLivro = new ControleLivro();
            controleEditora = new ControleEditora();
            editoras = controleEditora.listar();
            controleAutor = new ControleAutor();
            autores = controleAutor.listar();
            controleAreaConhecimento = new ControleAreaConhecimento();
            areasConhecimento = controleAreaConhecimento.listar();

            anoHoje = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));
            popularControles();
            mensagem = new Mensagens();

            // fonte: https://bibliotecaucs.wordpress.com/2012/10/18/o-primeiro-livro-impresso-no-mundo/
            jSpinnerAno.setModel(new SpinnerNumberModel(anoHoje, 1455, anoHoje, 1));
            if (acao.equals(EnumAcao.Incluir)) {
                limparCampos();
            } else if (acao.equals(EnumAcao.Editar)) {
                livro = controleLivro.buscarPeloId(id);
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
    private void popularControles() throws Exception {

        jComboBoxEditora.removeAllItems();
        for (Editora e : controleEditora.listar()) {
            jComboBoxEditora.addItem(e.getNomeEditora());
        }
        jComboBoxAutor.removeAllItems();
        for (Autor a : controleAutor.listar()) {
            jComboBoxAutor.addItem(a.getNomeAutor());

        }

        jComboBoxAreaConhecimento.removeAllItems();
        for (AreaConhecimento ac : controleAreaConhecimento.listar()) {
            jComboBoxAreaConhecimento.addItem(ac.getDescricaoAreaConhecimento());
        }
    }

    private void salvar() throws Exception {
        try {

            validarPreenchimento();

            livro = new Livro();
            livro.setTitulo(jTextFieldTitulo.getText().trim());

            livro.setEdicao(Integer.valueOf(jSpinnerEdicao.getValue().toString()));

            livro.setAnoPublicacao(Integer.valueOf(jSpinnerAno.getValue().toString()));

            livro.setIsbn(jTextFieldISBN.getText().trim());

            for (Autor a : autores) {
                if (a.getNomeAutor().equals(jComboBoxAutor.getSelectedItem())) {
                    livro.setAutor(a);
                    break;
                }
            }
            for (Editora e : editoras) {
                if (e.getNomeEditora().equals(jComboBoxEditora.getSelectedItem())) {
                    livro.setEditora(e);
                    break;
                }
            }

            for (AreaConhecimento ac : areasConhecimento) {
                if (ac.getDescricaoAreaConhecimento().equals(jComboBoxAreaConhecimento.getSelectedItem())) {
                    livro.setAreaConhecimento(ac);
                    break;
                }
            }

            livro.setDescricaoLivro(jTextAreaLivroDescricao.getText().trim());

            if (acao.equals(EnumAcao.Incluir)) {
                controleLivro.incluir(livro);
                mensagem.sucesso("Novo livro incluído com sucesso!");
            } else if (acao.equals(EnumAcao.Editar)) {
                livro.setIdLivro(Integer.parseInt(jTextFieldID.getText().trim()));
                controleLivro.alterar(livro);
                mensagem.sucesso("livro atualizado com sucesso!");
            }
            visible = false;
            this.dispose();
        } catch (Exception e) {
            throw new Exception("Erro ao salvar o cadastro do livro!");
        }
    }

    public void validarPreenchimento() throws Exception {
        //validarPreenchimento dos campos
        String campo = new String(jTextFieldTitulo.getText().trim());

        if (campo.length() == 0) {
            jTextFieldTitulo.requestFocus();
            throw new Exception("Insira o nome do livro");
        }

        if (campo.length() < 2) {
            jTextFieldTitulo.requestFocus();
            jTextFieldTitulo.selectAll();
            throw new Exception("O título do livro deve ter ao menos duas letras!");
        }

        if (!ValidadorISBN.validarIsbn13(jTextFieldISBN.getText())) {
            throw new Exception("Código ISBN invalido!");
        }

        if (jComboBoxAutor.getSelectedIndex() == -1) {
            throw new Exception("Selecione o autor do livro!");
        }
        if (jComboBoxEditora.getSelectedIndex() == -1) {
            throw new Exception("Selecione a editora do livro!");
        }
        if (jComboBoxAreaConhecimento.getSelectedIndex() == -1) {
            throw new Exception("Selecione a área de conhecimento do livro!");
        }

    }

    private void limparCampos() {
        jTextFieldID.setText(String.format(""));
        jTextFieldTitulo.setText("");
        jSpinnerEdicao.setValue(1);
        jSpinnerAno.setValue(anoHoje);
        jTextFieldISBN.setText("");
        jComboBoxAutor.setSelectedIndex(-1);
        jComboBoxEditora.setSelectedIndex(-1);
        jTextAreaLivroDescricao.setText("");
    }

    private void preencherCampos() {

        jTextFieldID.setText(String.format("%04d", livro.getIdLivro()));
        jTextFieldTitulo.setText(livro.getTitulo());
        jSpinnerEdicao.setValue(livro.getEdicao());
        jSpinnerAno.setValue(livro.getAnoPublicacao());
        jTextFieldISBN.setText(livro.getIsbn());
        jComboBoxAutor.setSelectedItem(livro.getAutor().getNomeAutor());
        jComboBoxEditora.setSelectedItem(livro.getEditora().getNomeEditora());
        jTextAreaLivroDescricao.setText(livro.getDescricaoLivro());

    }

    //--- FIM MÉTODOS ---------------------------------------------------------|
    //
    //-- CONSTRUTOR ----------------------------------------------------------->
    //
    /**
     * Creates new form TelaLivro
     */
    public TelaLivro(java.awt.Frame parent, boolean modal) {
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

        jProgressBar1 = new javax.swing.JProgressBar();
        jPanelLivro = new javax.swing.JPanel();
        jLabelTitulo = new javax.swing.JLabel();
        jTextFieldTitulo = new javax.swing.JTextField();
        jLabelAutor = new javax.swing.JLabel();
        jLabelEdicao = new javax.swing.JLabel();
        jLabelAno = new javax.swing.JLabel();
        jSpinnerAno = new javax.swing.JSpinner();
        jLabelEditora = new javax.swing.JLabel();
        jComboBoxEditora = new javax.swing.JComboBox<>();
        jLabelISBN = new javax.swing.JLabel();
        jTextFieldISBN = new javax.swing.JTextField();
        jLabelDescricao = new javax.swing.JLabel();
        jScrollPaneDescricao = new javax.swing.JScrollPane();
        jTextAreaLivroDescricao = new javax.swing.JTextArea();
        jSpinnerEdicao = new javax.swing.JSpinner();
        jComboBoxAutor = new javax.swing.JComboBox<>();
        jComboBoxAreaConhecimento = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabelID = new javax.swing.JLabel();
        jTextFieldID = new javax.swing.JTextField();
        jButtonSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanelLivro.setBorder(javax.swing.BorderFactory.createTitledBorder("Livro"));

        jLabelTitulo.setText("Título");

        jLabelAutor.setText("Autor");

        jLabelEdicao.setText("Edição");

        jLabelAno.setText("Ano");

        jSpinnerAno.setModel(new javax.swing.SpinnerNumberModel(1455, 1455, null, 1));

        jLabelEditora.setText("Editora");

        jComboBoxEditora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxEditoraActionPerformed(evt);
            }
        });

        jLabelISBN.setText("ISBN");

        jLabelDescricao.setText("Descrição");

        jTextAreaLivroDescricao.setColumns(20);
        jTextAreaLivroDescricao.setLineWrap(true);
        jTextAreaLivroDescricao.setRows(2);
        jScrollPaneDescricao.setViewportView(jTextAreaLivroDescricao);

        jSpinnerEdicao.setModel(new javax.swing.SpinnerNumberModel(1, 1, 999, 1));

        jComboBoxAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAutorActionPerformed(evt);
            }
        });

        jLabel1.setText("Área de Conhecimento");

        javax.swing.GroupLayout jPanelLivroLayout = new javax.swing.GroupLayout(jPanelLivro);
        jPanelLivro.setLayout(jPanelLivroLayout);
        jPanelLivroLayout.setHorizontalGroup(
            jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLivroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLivroLayout.createSequentialGroup()
                        .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelTitulo)
                            .addGroup(jPanelLivroLayout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(jTextFieldTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 271, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jSpinnerEdicao, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLivroLayout.createSequentialGroup()
                                        .addComponent(jLabelEdicao)
                                        .addGap(20, 20, 20)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSpinnerAno, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelAno))
                        .addGap(108, 108, 108))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLivroLayout.createSequentialGroup()
                        .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPaneDescricao, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelLivroLayout.createSequentialGroup()
                                .addComponent(jLabelDescricao)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelLivroLayout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelLivroLayout.createSequentialGroup()
                                        .addComponent(jLabelAutor)
                                        .addGap(247, 247, 247)
                                        .addComponent(jLabelEditora))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLivroLayout.createSequentialGroup()
                                        .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanelLivroLayout.createSequentialGroup()
                                                .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jComboBoxAreaConhecimento, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(jComboBoxAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18))
                                            .addGroup(jPanelLivroLayout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(127, 127, 127)))
                                        .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabelISBN)
                                            .addComponent(jComboBoxEditora, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jTextFieldISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                        .addGap(67, 67, 67))))
        );
        jPanelLivroLayout.setVerticalGroup(
            jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLivroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTitulo)
                    .addComponent(jLabelAno)
                    .addComponent(jLabelEdicao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinnerAno, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinnerEdicao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEditora)
                    .addComponent(jLabelAutor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxEditora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxAutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabelISBN))
                .addGap(4, 4, 4)
                .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxAreaConhecimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabelDescricao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabelID.setText("ID:");

        jTextFieldID.setEditable(false);
        jTextFieldID.setFont(new java.awt.Font("Dialog", 3, 12)); // NOI18N
        jTextFieldID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldID.setDisabledTextColor(new java.awt.Color(102, 102, 102));

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
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabelID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(514, 514, 514)
                        .addComponent(jButtonSalvar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanelLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelID))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelLivro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
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

    private void jComboBoxEditoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEditoraActionPerformed

    }//GEN-LAST:event_jComboBoxEditoraActionPerformed

    private void jComboBoxAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxAutorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxAutorActionPerformed

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
            java.util.logging.Logger.getLogger(TelaLivro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaLivro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaLivro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaLivro.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaLivro dialog = new TelaLivro(new javax.swing.JFrame(), true);
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
    private javax.swing.JComboBox<String> jComboBoxAreaConhecimento;
    private javax.swing.JComboBox<String> jComboBoxAutor;
    private javax.swing.JComboBox<String> jComboBoxEditora;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelAno;
    private javax.swing.JLabel jLabelAutor;
    private javax.swing.JLabel jLabelDescricao;
    private javax.swing.JLabel jLabelEdicao;
    private javax.swing.JLabel jLabelEditora;
    private javax.swing.JLabel jLabelID;
    private javax.swing.JLabel jLabelISBN;
    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JPanel jPanelLivro;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPaneDescricao;
    private javax.swing.JSpinner jSpinnerAno;
    private javax.swing.JSpinner jSpinnerEdicao;
    private javax.swing.JTextArea jTextAreaLivroDescricao;
    private javax.swing.JTextField jTextFieldID;
    private javax.swing.JTextField jTextFieldISBN;
    private javax.swing.JTextField jTextFieldTitulo;
    // End of variables declaration//GEN-END:variables
}
