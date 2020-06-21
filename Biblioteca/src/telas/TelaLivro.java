/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import classes.AreaConhecimento;
import classes.Autor;
import classes.Editora;
import classes.Exemplar;
import classes.Livro;
import classes.Renderer;
import controle.ControleAreaConhecimento;
import controle.ControleAutor;
import controle.ControleEditora;
import controle.ControleExemplar;
import controle.ControleLivro;
import enumeradores.EnumAcao;
import enumeradores.EnumTipoStatus;
import interfaces.ICRUDAreaConhecimento;
import interfaces.ICRUDAutor;
import interfaces.ICRUDEditora;
import interfaces.ICRUDExemplar;
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
    private ICRUDLivro controleLivro = null;
    private ICRUDAutor controleAutor = null;
    private ICRUDEditora controleEditora = null;
    private ICRUDAreaConhecimento controleAreaConhecimento = null;
    private ICRUDExemplar controleExemplar = null;

    private ArrayList<Autor> autores = null;
    private ArrayList<Editora> editoras = null;
    private ArrayList<AreaConhecimento> areasConhecimento = null;
    private ArrayList<Exemplar> exemplares = null;

    private String[][] matriz = null;
    private String[] vetor = null;

    private Livro livro = null;

    private Mensagens mensagem = null;
    private EnumAcao acao = null;
    private SimpleDateFormat formatoData = null;
    private Renderer renderer = null;

    private boolean visible = false;
    private int id;
    private int cont = 0;
    private int anoHoje = 0;

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
            mensagem = new Mensagens();
            formatoData = new SimpleDateFormat("dd/MM/yyyy");
            renderer = new Renderer();

            controleLivro = new ControleLivro();
            controleEditora = new ControleEditora();
            controleAutor = new ControleAutor();
            controleAreaConhecimento = new ControleAreaConhecimento();
            controleExemplar = new ControleExemplar();

            popularControles();

            if (acao.equals(EnumAcao.Incluir)) {
                livro = new Livro();
                limparCampos();
            } else if (acao.equals(EnumAcao.Editar)) {
                livro = controleLivro.buscarPeloId(id);
                preencherCampos();
            }
            jButtonExcluirExemplar.setEnabled(false);
        } catch (Exception e) {
            mensagem.erro(new Exception(e.getMessage()));
        }
        visible = true;
        super.setVisible(b);
    }

    //--- FIM OVERRIDE --------------------------------------------------------|
    //
    //--- MÉTODOS ------------------------------------------------------------->
    //
    private void popularControles() throws Exception {

        anoHoje = Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date()));

        // fonte: https://bibliotecaucs.wordpress.com/2012/10/18/o-primeiro-livro-impresso-no-mundo/
        jSpinnerAno.setModel(new SpinnerNumberModel(anoHoje, 1455, anoHoje, 1));

        popularJComboBoxAutor();
        popularJComboBoxEditora();
        popularJComboBoxAreaConhecimento();

    }

    private void popularJComboBoxAutor() throws Exception {
        autores = controleAutor.listar();

        jComboBoxAutor.removeAllItems();
        for (Autor a : autores) {
            jComboBoxAutor.addItem(a.getNomeAutor());
        }
        jComboBoxAutor.setSelectedIndex(-1);
    }

    private void popularJComboBoxEditora() throws Exception {
        editoras = controleEditora.listar();

        jComboBoxEditora.removeAllItems();
        for (Editora e : editoras) {
            jComboBoxEditora.addItem(e.getNomeEditora());
        }
        jComboBoxEditora.setSelectedIndex(-1);
    }

    private void popularJComboBoxAreaConhecimento() throws Exception {
        areasConhecimento = controleAreaConhecimento.listar();

        jComboBoxAreaConhecimento.removeAllItems();
        for (AreaConhecimento ac : areasConhecimento) {
            jComboBoxAreaConhecimento.addItem(ac.getDescricaoAreaConhecimento().toString());
        }
        jComboBoxAreaConhecimento.setSelectedIndex(-1);
    }

    private void limparCampos() {
        jTextFieldID.setText(String.format(""));
        jTextFieldTitulo.setText("");
        jComboBoxAutor.setSelectedIndex(-1);
        jComboBoxEditora.setSelectedIndex(-1);
        jSpinnerEdicao.setValue(1);
        jSpinnerAno.setValue(anoHoje);
        jTextFieldISBN.setText("");
        jComboBoxAreaConhecimento.setSelectedIndex(-1);
        jTextAreaLivroDescricao.setText("");
    }

    private void preencherCampos() throws Exception {

        jTextFieldID.setText(livro.getIdLivro() + "");
        jTextFieldTitulo.setText(livro.getTitulo());
        jSpinnerEdicao.setValue(livro.getEdicao());
        jSpinnerAno.setValue(livro.getAnoPublicacao());
        jTextFieldISBN.setText(livro.getIsbn());
        jComboBoxAutor.setSelectedItem(livro.getAutor().getNomeAutor());
        jComboBoxEditora.setSelectedItem(livro.getEditora().getNomeEditora());
        jComboBoxAreaConhecimento.setSelectedItem(livro.getAreaConhecimento().getDescricaoAreaConhecimento());
        jTextAreaLivroDescricao.setText(livro.getDescricaoLivro());

        preencherJTableExemplares(livro.getIdLivro());

    }

    private void preencherJTableExemplares(int idLivro) throws Exception {
        exemplares = new ControleExemplar().exemplaresDoLivro(idLivro);
        matriz = new String[exemplares.size()][5];
        cont = 0;
        for (Exemplar exemplar : exemplares) {
            vetor = new String[]{
                exemplar.getIdExemplar() + "",
                exemplar.getStatusExemplar().toString(),
                formatoData.format(exemplar.getDataAquisicao()).toString(),
                String.format("%.2f", exemplar.getPrecoCompra()),
                exemplar.getMotivoDesativado().replaceAll("\n", " ")
            };
            matriz[cont] = vetor;
            cont++;
        }

        jTableExemplares.setModel(new javax.swing.table.DefaultTableModel(
                matriz,
                new String[]{
                    "ID", "Status", "Aquisição", "Preço (R$)", "Motivo da Desativação"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        jTableExemplares.getTableHeader().setReorderingAllowed(false);
        jScrollPaneExemplares.setViewportView(jTableExemplares);
        if (jTableExemplares.getColumnModel().getColumnCount() > 0) {
            jTableExemplares.getColumnModel().getColumn(0).setMinWidth(60);
            jTableExemplares.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTableExemplares.getColumnModel().getColumn(0).setMaxWidth(60);
            jTableExemplares.getColumnModel().getColumn(1).setMinWidth(110);
            jTableExemplares.getColumnModel().getColumn(1).setPreferredWidth(110);
            jTableExemplares.getColumnModel().getColumn(1).setMaxWidth(110);
            jTableExemplares.getColumnModel().getColumn(2).setMinWidth(100);
            jTableExemplares.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTableExemplares.getColumnModel().getColumn(2).setMaxWidth(100);
            jTableExemplares.getColumnModel().getColumn(3).setMinWidth(100);
            jTableExemplares.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTableExemplares.getColumnModel().getColumn(3).setMaxWidth(100);

            jTableExemplares.getColumnModel().getColumn(0).setCellRenderer(renderer.getRendererDireita());
            jTableExemplares.getColumnModel().getColumn(1).setCellRenderer(renderer.getRendererCentro());
            jTableExemplares.getColumnModel().getColumn(2).setCellRenderer(renderer.getRendererCentro());
            jTableExemplares.getColumnModel().getColumn(3).setCellRenderer(renderer.getRendererDireita());
        }
        jButtonExcluirExemplar.setEnabled(false);
    }

    private void validarPreenchimento() throws Exception {
        //validarPreenchimento dos campos
        String campo = new String(jTextFieldTitulo.getText().trim());

        if (campo.length() == 0) {
            jTextFieldTitulo.requestFocus();
            throw new Exception("Informe o título do livro!");
        }

        if (campo.length() < 2) {
            jTextFieldTitulo.requestFocus();
            jTextFieldTitulo.selectAll();
            throw new Exception("O título do livro deve ter ao menos duas letras!");
        }

        if (!ValidadorISBN.validarIsbn13(jTextFieldISBN.getText().trim())) {
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

    private void salvar() throws Exception {
        try {
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
                if (livro.getIdLivro() == 0) {
                    // Se for maior que 0, então já cadastrou o livro na inclusão do exemplar
                    controleLivro.incluir(livro);
                }
            } else if (acao.equals(EnumAcao.Editar)) {
                livro.setIdLivro(Integer.parseInt(jTextFieldID.getText().trim()));
                controleLivro.alterar(livro);
            } else { // Incluir livro para incluir exemplar
                livro.setIdLivro(controleLivro.incluir(livro));
                jTextFieldID.setText(livro.getIdLivro() + "");
            }
        } catch (Exception e) {
            throw new Exception("Erro ao salvar o cadastro do livro!\n" + e.getMessage());
        }
    }

    private void incluirExemplarDoLivro() throws Exception {
        if (livro.getIdLivro() == 0) {
            // Salva o livro antes de incluir o exemplar, para ter o idLivro
            validarPreenchimento();
            salvar();
        }

        TelaExemplar telaExemplar = new TelaExemplar(null, true);
        telaExemplar.setId((acao.equals(EnumAcao.Incluir)) ? 0 : Integer.parseInt(jTextFieldID.getText().trim()));
        telaExemplar.setLivro(livro);
        telaExemplar.setAcao(EnumAcao.Incluir);
        telaExemplar.setVisible(true);
        preencherJTableExemplares(livro.getIdLivro());

    }

    private void excluirExemplarDoLivro() throws Exception {
        int id = Integer.parseInt(jTableExemplares.getValueAt(jTableExemplares.getSelectedRow(), 0).toString());

        String textoPergunta = "Deseja realmente excluir o exemplar ID: " + id + "?";

        if (mensagem.pergunta(textoPergunta) == 0) {
            controleExemplar.excluir(id);
            mensagem.sucesso("Cadastro de exemplar excluído com sucesso!");
        }
        preencherJTableExemplares(livro.getIdLivro());
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
        jButtonNovaEditora = new javax.swing.JButton();
        jButtonNovoAutor = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jButtonIncluirExemplar = new javax.swing.JButton();
        jScrollPaneExemplares = new javax.swing.JScrollPane();
        jTableExemplares = new javax.swing.JTable();
        jButtonExcluirExemplar = new javax.swing.JButton();
        jButtonNovaAreaConhecimento = new javax.swing.JButton();
        jLabelID = new javax.swing.JLabel();
        jTextFieldID = new javax.swing.JTextField();
        jButtonSalvar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanelLivro.setBorder(javax.swing.BorderFactory.createTitledBorder("Livro"));

        jLabelTitulo.setText("Título");

        jLabelAutor.setText("Autor");

        jLabelEdicao.setText("Edição");

        jLabelAno.setText("Ano");

        jSpinnerAno.setModel(new javax.swing.SpinnerNumberModel(2020, 1455, 2020, 1));
        jSpinnerAno.setEditor(new javax.swing.JSpinner.NumberEditor(jSpinnerAno, ""));

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
        jTextAreaLivroDescricao.setRows(4);
        jScrollPaneDescricao.setViewportView(jTextAreaLivroDescricao);

        jSpinnerEdicao.setModel(new javax.swing.SpinnerNumberModel(1, 1, 999, 1));

        jComboBoxAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxAutorActionPerformed(evt);
            }
        });

        jLabel1.setText("Área de Conhecimento");

        jButtonNovaEditora.setText("Nova");
        jButtonNovaEditora.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNovaEditoraActionPerformed(evt);
            }
        });

        jButtonNovoAutor.setText("Novo");
        jButtonNovoAutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNovoAutorActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Exemplares"));

        jButtonIncluirExemplar.setText("Incluir Exemplar");
        jButtonIncluirExemplar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIncluirExemplarActionPerformed(evt);
            }
        });

        jTableExemplares.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Status", "Aquisição", "Preço (R$)", "Motivo da Desativação"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableExemplares.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableExemplares.getTableHeader().setReorderingAllowed(false);
        jTableExemplares.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableExemplaresMouseClicked(evt);
            }
        });
        jScrollPaneExemplares.setViewportView(jTableExemplares);
        if (jTableExemplares.getColumnModel().getColumnCount() > 0) {
            jTableExemplares.getColumnModel().getColumn(0).setMinWidth(60);
            jTableExemplares.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTableExemplares.getColumnModel().getColumn(0).setMaxWidth(60);
            jTableExemplares.getColumnModel().getColumn(1).setMinWidth(110);
            jTableExemplares.getColumnModel().getColumn(1).setPreferredWidth(110);
            jTableExemplares.getColumnModel().getColumn(1).setMaxWidth(110);
            jTableExemplares.getColumnModel().getColumn(2).setMinWidth(100);
            jTableExemplares.getColumnModel().getColumn(2).setPreferredWidth(100);
            jTableExemplares.getColumnModel().getColumn(2).setMaxWidth(100);
            jTableExemplares.getColumnModel().getColumn(3).setMinWidth(100);
            jTableExemplares.getColumnModel().getColumn(3).setPreferredWidth(100);
            jTableExemplares.getColumnModel().getColumn(3).setMaxWidth(100);
        }

        jButtonExcluirExemplar.setText("Excluir Exemplar");
        jButtonExcluirExemplar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExcluirExemplarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButtonIncluirExemplar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonExcluirExemplar)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPaneExemplares))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonIncluirExemplar)
                    .addComponent(jButtonExcluirExemplar))
                .addGap(12, 12, 12)
                .addComponent(jScrollPaneExemplares, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jButtonNovaAreaConhecimento.setText("Nova");
        jButtonNovaAreaConhecimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonNovaAreaConhecimentoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelLivroLayout = new javax.swing.GroupLayout(jPanelLivro);
        jPanelLivro.setLayout(jPanelLivroLayout);
        jPanelLivroLayout.setHorizontalGroup(
            jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLivroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabelTitulo)
                    .addComponent(jLabelDescricao)
                    .addGroup(jPanelLivroLayout.createSequentialGroup()
                        .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelLivroLayout.createSequentialGroup()
                                .addComponent(jComboBoxEditora, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonNovaEditora))
                            .addComponent(jLabelEditora))
                        .addGap(18, 18, 18)
                        .addComponent(jLabelAutor))
                    .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(jScrollPaneDescricao, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelLivroLayout.createSequentialGroup()
                            .addComponent(jTextFieldTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 356, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jComboBoxAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButtonNovoAutor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelLivroLayout.createSequentialGroup()
                            .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jComboBoxAreaConhecimento, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 521, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanelLivroLayout.createSequentialGroup()
                                    .addGap(368, 368, 368)
                                    .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanelLivroLayout.createSequentialGroup()
                                            .addComponent(jSpinnerEdicao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGap(18, 18, 18)
                                            .addComponent(jSpinnerAno, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanelLivroLayout.createSequentialGroup()
                                            .addComponent(jLabelEdicao)
                                            .addGap(38, 38, 38)
                                            .addComponent(jLabelAno)))))
                            .addGap(18, 18, 18)
                            .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabelISBN)
                                .addComponent(jTextFieldISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButtonNovaAreaConhecimento, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelLivroLayout.setVerticalGroup(
            jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLivroLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelTitulo)
                    .addComponent(jLabelAutor))
                .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxAutor, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNovoAutor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabelAno)
                        .addComponent(jLabelEdicao)
                        .addComponent(jLabelISBN))
                    .addComponent(jLabelEditora))
                .addGap(0, 0, 0)
                .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxEditora, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNovaEditora)
                    .addComponent(jSpinnerEdicao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jSpinnerAno, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldISBN, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addGap(0, 0, 0)
                .addGroup(jPanelLivroLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxAreaConhecimento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonNovaAreaConhecimento))
                .addGap(18, 18, 18)
                .addComponent(jLabelDescricao)
                .addGap(0, 0, 0)
                .addComponent(jScrollPaneDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabelID.setText("ID:");

        jTextFieldID.setEditable(false);
        jTextFieldID.setFont(new java.awt.Font("Dialog", 3, 12)); // NOI18N
        jTextFieldID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldID.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTextFieldID.setEnabled(false);

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
                        .addComponent(jLabelID)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButtonSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jPanelLivro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelID))
                .addGap(18, 18, 18)
                .addComponent(jPanelLivro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSalvar)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jButtonSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSalvarActionPerformed
        try {
            validarPreenchimento();
            salvar();
            if (acao.equals(EnumAcao.Incluir)) {
                mensagem.sucesso("Novo livro incluído com sucesso!");
            } else if (acao.equals(EnumAcao.Editar)) {
                mensagem.sucesso("livro atualizado com sucesso!");
            }

            visible = false;
            this.dispose();

        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jButtonSalvarActionPerformed

    private void jComboBoxEditoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxEditoraActionPerformed

    }//GEN-LAST:event_jComboBoxEditoraActionPerformed

    private void jComboBoxAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxAutorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxAutorActionPerformed

    private void jButtonNovoAutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovoAutorActionPerformed
        try {
            TelaAutor telaAutor = new TelaAutor(null, true);
            telaAutor.setAcao(EnumAcao.Incluir);
            telaAutor.setVisible(true);
            popularJComboBoxAutor();
        } catch (Exception e) {
            mensagem.erro(new Exception("Erro ao iniciar a tela de autor!\n" + e.getMessage()));
        }
    }//GEN-LAST:event_jButtonNovoAutorActionPerformed

    private void jButtonIncluirExemplarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIncluirExemplarActionPerformed
        try {
            incluirExemplarDoLivro();
        } catch (Exception e) {
            mensagem.erro(new Exception(e.getMessage()));
        }
    }//GEN-LAST:event_jButtonIncluirExemplarActionPerformed

    private void jButtonNovaEditoraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovaEditoraActionPerformed
        try {
            TelaEditora telaEditora = new TelaEditora(null, true);
            telaEditora.setAcao(EnumAcao.Incluir);
            telaEditora.setVisible(true);
            popularJComboBoxEditora();

        } catch (Exception e) {
            mensagem.erro(new Exception("Erro ao iniciar a tela de editora!\n" + e.getMessage()));
        }
    }//GEN-LAST:event_jButtonNovaEditoraActionPerformed

    private void jButtonNovaAreaConhecimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonNovaAreaConhecimentoActionPerformed

        try {
            TelaAreaConhecimento telaAreaConhecimento = new TelaAreaConhecimento(null, true);
            telaAreaConhecimento.setAcao(EnumAcao.Incluir);
            telaAreaConhecimento.setVisible(true);
            popularJComboBoxAreaConhecimento();
        } catch (Exception e) {
            mensagem.erro(new Exception("Erro ao iniciar a tela de area de conhecimento!\n" + e.getMessage()));
        }
    }//GEN-LAST:event_jButtonNovaAreaConhecimentoActionPerformed

    private void jTableExemplaresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableExemplaresMouseClicked
        try {
            if (evt != null) { // Quando acionado pelo keyReleased, evt = null
                // Clique duplo, mostra detalhes do cadastro
                if (evt.getClickCount() == 2) {
                    TelaExemplar telaExemplar = new TelaExemplar(null, true);
                    telaExemplar.setId(Integer.parseInt(jTableExemplares.getValueAt(jTableExemplares.getSelectedRow(), 0).toString()));
                    telaExemplar.setLivro(livro);
                    telaExemplar.setAcao(EnumAcao.Editar);
                    telaExemplar.setVisible(true);
                    preencherJTableExemplares(livro.getIdLivro());
                }
            }
            if (jTableExemplares.getSelectedRow() > -1) {
                EnumTipoStatus status = EnumTipoStatus.valueOf(jTableExemplares.getValueAt(jTableExemplares.getSelectedRow(), 1).toString());
                jButtonExcluirExemplar.setEnabled(status.equals(EnumTipoStatus.DISPONIVEL));
            }
        } catch (Exception e) {
            mensagem.erro(new Exception("Erro ao abrir cadastro de exemplar!\n " + e.getMessage()));
        }
    }//GEN-LAST:event_jTableExemplaresMouseClicked

    private void jButtonExcluirExemplarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirExemplarActionPerformed
        try {
            excluirExemplarDoLivro();
        } catch (Exception e) {
            mensagem.erro(new Exception(e.getMessage()));
        }
    }//GEN-LAST:event_jButtonExcluirExemplarActionPerformed

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
    private javax.swing.JButton jButtonExcluirExemplar;
    private javax.swing.JButton jButtonIncluirExemplar;
    private javax.swing.JButton jButtonNovaAreaConhecimento;
    private javax.swing.JButton jButtonNovaEditora;
    private javax.swing.JButton jButtonNovoAutor;
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
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanelLivro;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPaneDescricao;
    private javax.swing.JScrollPane jScrollPaneExemplares;
    private javax.swing.JSpinner jSpinnerAno;
    private javax.swing.JSpinner jSpinnerEdicao;
    private javax.swing.JTable jTableExemplares;
    private javax.swing.JTextArea jTextAreaLivroDescricao;
    private javax.swing.JTextField jTextFieldID;
    private javax.swing.JTextField jTextFieldISBN;
    private javax.swing.JTextField jTextFieldTitulo;
    // End of variables declaration//GEN-END:variables
}
