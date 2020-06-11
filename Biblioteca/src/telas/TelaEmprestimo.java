/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import controle.ControleTelaEmprestimo;
import enumeradores.EnumAcao;
import enumeradores.EnumCargo;
import enumeradores.EnumFiltrosColaborador;
import enumeradores.EnumTipoStatus;
import interfaces.ITelaCadastro;
import java.util.ArrayList;
import utilidades.Mensagens;

/**
 *
 * @author Vinicius
 */
public class TelaEmprestimo extends javax.swing.JDialog implements ITelaCadastro {

    //--- ATRIBUTOS ----------------------------------------------------------->
    private int id = 0;
    private int linha = 0;
    private int coluna = 0;
    private int valor = 0;
    private int cont = 0;
    private boolean incluirNoResultado = true;

    private Mensagens mensagem = new Mensagens();
    private EnumAcao acao = null;
    private boolean visible = false;
    private ControleTelaEmprestimo controleTelaEmprestimo = null;

    // Matrizes e vetores
    private String[][] matrizColaborador = null;
    private String[][] matrizLivro = null;
    private String[][] matrizExemplar = null;
    
    private String[][] matrizFiltroColaborador = null;
    private String[][] matrizFiltroLivro = null;
    private String[][] matrizFiltroExemplar = null;
    
    private String[][] matrizPesquisaColaborador = null;
    private String[][] matrizPesquisaLivro = null;

    private ArrayList<String[]> resultadoPesquisa = null;
    private String[] vetorDetalhesColaborador = null;

    //--- FIM ATRIBUTOS -------------------------------------------------------|
    //
    //--- MÉTODOS ------------------------------------------------------------->
    //
    //--- MÉTODOS OVERRIDE ---------------------------------------------------->
    //
    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setAcao(EnumAcao acao) {
        this.acao = acao;
        this.setTitle(acao.toString() + " cadastro de Empréstimos e Reservas");
    }

    @Override
    public void setVisible(boolean b) {
        try {
            controleTelaEmprestimo = new ControleTelaEmprestimo();

            matrizColaborador = controleTelaEmprestimo.getMatrizColaboradores();
            preencherJTableColaboradores(matrizColaborador);
            preencherJTableColaboradorDetalhe();

            matrizLivro = controleTelaEmprestimo.getMatrizLivros();
            preencherJTableLivro(matrizLivro);
            preencherJTableLivroDetalhe();

            matrizExemplar = controleTelaEmprestimo.getMatrizExemplares();

            popularControles();
            jComboBoxFiltrarColaborador.setSelectedIndex(0);

            jButtonReservar.setEnabled(false);
            jButtonCancelar.setEnabled(false);
            jButtonEmprestar.setEnabled(false);
            jButtonDevolver.setEnabled(false);

            //emprestimo = controleReserva.buscarPeloId(id);
            if (acao.equals(EnumAcao.Incluir)) {
                //limparCampos();
            } else if (acao.equals(EnumAcao.Editar)) {
                //preencherCampos();
            }
        } catch (Exception e) {
            mensagem.erro(e);
        }
        visible = true;
        super.setVisible(b);
    }

    //--- FIM MÉTODOS OVERRIDE ------------------------------------------------|
    //
    //--- MÉTODOS PARA CRUD --------------------------------------------------->
    //
    private void incluirCadastro() throws Exception {
    }

    private void editarCadastro() throws Exception {
    }

    private void detalheCadastro() throws Exception {
    }

    private void excluirCadastro() throws Exception {
    }

    //--- FIM MÉTODOS PARA CRUD -----------------------------------------------|
    //
    //--- MÉTODOS PARA GRID --------------------------------------------------->
    //
    private void filtrarColaboradores(int filtro) throws Exception {
        if (matrizColaborador != null) {
            if (matrizColaborador.length > 0) {
                if (filtro >= 0) {
                    resultadoPesquisa = new ArrayList<>();
                    coluna = 0;

                    for (int i = 0; i < matrizColaborador.length; i++) {
                        incluirNoResultado = false;
                        switch (filtro) {
                            case -1: // Não tem filtro selecionado
                                break;

                            case 0: // TODOS_OS_REGISTROS
                                incluirNoResultado = true;
                                break;

                            case 1: // ATIVOS
                                incluirNoResultado = (EnumTipoStatus.valueOf(matrizColaborador[i][8]) == EnumTipoStatus.ATIVO);
                                break;

                            case 2: // INATIVOS
                                incluirNoResultado = (EnumTipoStatus.valueOf(matrizColaborador[i][8]) == EnumTipoStatus.INATIVO);
                                break;

                            case 3: // ADIMPLENTE
                                incluirNoResultado = (Float.parseFloat(matrizColaborador[i][10].replace(",", ".")) == 0.0);
                                break;

                            case 4: // INADIMPLENTE
                                incluirNoResultado = (Float.parseFloat(matrizColaborador[i][10].replace(",", ".")) > 0.0);
                                break;

                            case 5: // COM_EMPRESTIMOS
                                coluna = 9;
                                incluirNoResultado = (Integer.parseInt(matrizColaborador[i][9]) > 0);
                                break;

                            case 6: // COM_RESERVAS
                                incluirNoResultado = (Integer.parseInt(matrizColaborador[i][11]) > 0);
                                break;

                            default:
                                incluirNoResultado = (EnumCargo.valueOf(matrizColaborador[i][3])
                                        == EnumCargo.valueOf(jComboBoxFiltrarColaborador.getSelectedItem().toString()
                                        ));
                                break;

                        }

                        if (incluirNoResultado) {
                            resultadoPesquisa.add(matrizColaborador[i]);
                        }
                    }

                    matrizFiltroColaborador = new String[resultadoPesquisa.size()][matrizColaborador[0].length];

                    for (int i = 0; i < resultadoPesquisa.size(); i++) {
                        matrizFiltroColaborador[i] = resultadoPesquisa.get(i);
                    }
                    preencherJTableColaboradores(matrizFiltroColaborador);
                    preencherJTableColaboradorDetalhe();
                }
            }
        }
    }

    private void filtrarLivros(int filtro) throws Exception {
        matrizFiltroLivro = matrizLivro;
    }

    private void filtrarExemplares(int idLivro) throws Exception {
        if (matrizExemplar != null) {
            if (matrizExemplar.length > 0) {
                if (idLivro >= 0) {
                    resultadoPesquisa = new ArrayList<>();
                    coluna = 0;

                    for (int i = 0; i < matrizExemplar.length; i++) {
                        if (Integer.parseInt(matrizExemplar[i][4]) == idLivro) {
                            resultadoPesquisa.add(matrizExemplar[i]);
                        }
                    }

                    matrizFiltroExemplar = new String[resultadoPesquisa.size()][matrizExemplar[0].length];

                    for (int i = 0; i < resultadoPesquisa.size(); i++) {
                        matrizFiltroExemplar[i] = resultadoPesquisa.get(i);
                    }
                    preencherJTableExemplares(matrizFiltroExemplar);
                }
            }
        }
    }

    private void pesquisarColaboradores(String texto) throws Exception {
        try {
            if (matrizFiltroColaborador != null) {
                if (matrizFiltroColaborador.length > 0) {
                    texto = texto.toLowerCase().trim();
                    if (texto.length() == 0) {
                        matrizPesquisaColaborador = matrizFiltroColaborador;
                    } else {
                        resultadoPesquisa = new ArrayList<>();

                        for (int i = 0; i < matrizFiltroColaborador.length; i++) {
                            if (matrizFiltroColaborador[i][0].toLowerCase().contains(texto)
                                    || matrizFiltroColaborador[i][1].toLowerCase().contains(texto)
                                    || matrizFiltroColaborador[i][2].toLowerCase().contains(texto)) {
                                resultadoPesquisa.add(matrizFiltroColaborador[i]);
                            }
                        }

                        matrizPesquisaColaborador = new String[resultadoPesquisa.size()][matrizFiltroColaborador[0].length];

                        for (int i = 0; i < resultadoPesquisa.size(); i++) {
                            matrizPesquisaColaborador[i] = resultadoPesquisa.get(i);
                        }
                    }
                }
                preencherJTableColaboradores(matrizPesquisaColaborador);
                preencherJTableColaboradorDetalhe();
            }

        } catch (Exception e) {
            mensagem.erro(new Exception("Erro ao pesquisar!" + e));
        }

    }

    private void preencherJTableColaboradores(String[][] matriz) throws Exception {

        jTableColaboradores.setModel(
                new javax.swing.table.DefaultTableModel(
                        matriz,
                        new String[]{"ID", "Matrícula", "Nome"}) {

            boolean[] canEdit = new boolean[]{false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
    }

    private void preencherJTableColaboradorDetalhe() throws Exception {
        linha = jTableColaboradores.getSelectedRow();
        if (linha >= 0) {
            id = Integer.parseInt(jTableColaboradores.getValueAt(linha, 0).toString());

            for (int i = 0; i < matrizFiltroColaborador.length; i++) {
                if (Integer.parseInt(matrizFiltroColaborador[i][0]) == id) {
                    String[] dados = matrizFiltroColaborador[i];

                    for (int j = 0; j < jTableColaboradorDetalhe.getRowCount(); j++) {
                        jTableColaboradorDetalhe.setValueAt(dados[j], j, 1);
                    }
                    break;
                }
            }
        } else {
            for (int i = 0; i < jTableColaboradorDetalhe.getRowCount(); i++) {
                jTableColaboradorDetalhe.setValueAt("", i, 1);
            }
        }
    }

    private void preencherJTableLivro(String[][] matriz) throws Exception {

        jTableLivro.setModel(new javax.swing.table.DefaultTableModel(
                matriz,
                new String[]{
                    "ID", "Título", "Autor", "Edição"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
    }

    private void preencherJTableLivroDetalhe() throws Exception {
        linha = jTableLivro.getSelectedRow();
        if (linha >= 0) {
            id = Integer.parseInt(jTableLivro.getValueAt(linha, 0).toString());

            for (int i = 0; i < matrizFiltroLivro.length; i++) {
                if (Integer.parseInt(matrizFiltroLivro[i][0]) == id) {
                    String[] dados = matrizFiltroLivro[i];

                    for (int j = 0; j < jTableLivroDetalhe.getRowCount(); j++) {
                        jTableLivroDetalhe.setValueAt(dados[j], j, 1);
                    }
                    break;
                }
            }
        } else {
            for (int i = 0; i < jTableLivroDetalhe.getRowCount(); i++) {
                jTableLivroDetalhe.setValueAt("", i, 1);
            }
        }
    }

    private void preencherJTableExemplares(String[][] matriz) throws Exception {
        jTableExemplares.setModel(new javax.swing.table.DefaultTableModel(
                matriz, new String[]{"ID", "Status", "Aquisição", "Preço (R$)"}
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
    }

    //--- FIM MÉTODOS PARA GRID -----------------------------------------------|
    //
    //--- MÉTODOS PARA AÇÕES DE BOTÕES ---------------------------------------->
    //
    //--- FIM MÉTODOS PARA AÇÕES DE BOTÕES  -----------------------------------|
    //
    //--- MÉTODOS PARA PREENCHIMENTO DA TELA ---------------------------------->
    //
    private void popularControles() {
        jComboBoxFiltrarColaborador.removeAllItems();
        jComboBoxFiltrarColaborador.addItem(EnumFiltrosColaborador.TODOS.toString());
        jComboBoxFiltrarColaborador.addItem(EnumTipoStatus.ATIVO.toString());
        jComboBoxFiltrarColaborador.addItem(EnumTipoStatus.INATIVO.toString());
        jComboBoxFiltrarColaborador.addItem(EnumTipoStatus.ADIMPLENTE.toString());
        jComboBoxFiltrarColaborador.addItem(EnumTipoStatus.INADIMPLENTE.toString());
        jComboBoxFiltrarColaborador.addItem(EnumFiltrosColaborador.COM_EMPRÉSTIMOS.toString().replace("_", " "));
        jComboBoxFiltrarColaborador.addItem(EnumFiltrosColaborador.COM_RESERVAS.toString().replace("_", " "));

        for (EnumCargo c : EnumCargo.values()) {
            jComboBoxFiltrarColaborador.addItem(c.toString());
        }
        jComboBoxFiltrarColaborador.setSelectedIndex(-1);

        jComboBoxFiltrarLivro.removeAllItems();
        jComboBoxFiltrarLivro.addItem(EnumTipoStatus.TODOS.toString());
        jComboBoxFiltrarLivro.addItem(EnumTipoStatus.ATIVO.toString());
        jComboBoxFiltrarLivro.addItem(EnumTipoStatus.INATIVO.toString());
        jComboBoxFiltrarLivro.addItem(EnumTipoStatus.DISPONIVEL.toString());
        jComboBoxFiltrarLivro.addItem(EnumTipoStatus.RESERVADO.toString());
        jComboBoxFiltrarLivro.addItem(EnumTipoStatus.EMPRESTADO.toString());
        jComboBoxFiltrarLivro.setSelectedIndex(-1);
    }

    private void limparCampos() {
//        jTextFieldID.setText("");
//        jComboBoxCargo.setSelectedIndex(-1);
//        jFormattedTextFieldMatricula.setText("");
//        jTextFieldNome.setText("");
//        jTextFieldOAB.setText("");
//        jComboBoxUF.setSelectedIndex(-1);
//        jTextFieldEmail.setText("");
//        jFormattedTextFieldTelefone.setText("");
//        jComboBoxPerfil.setSelectedIndex(-1);
//        jComboBoxStatus.setSelectedIndex(-1);
    }

    private void preencherCampos() {
//        jTextFieldID.setText(String.format("%04d", emprestimo.getIdColaborador()));
//        jComboBoxCargo.setSelectedIndex(emprestimo.getCargo().ordinal());
//        jFormattedTextFieldMatricula.setText(String.format("%d", emprestimo.getMatricula()));
//        jTextFieldNome.setText(emprestimo.getNomeColaborador());
//
//        if (emprestimo.getCargo().equals(EnumCargo.ADVOGADO)) {
//            String[] oab = emprestimo.getOab().split("-");
//            jTextFieldOAB.setText(oab[0]);
//            jComboBoxUF.setSelectedIndex(EnumUF.valueOf(oab[1]).ordinal());
//        }
//        jComboBoxUF.setSelectedIndex(-1);
//        jTextFieldEmail.setText(emprestimo.getEmail());
//        jFormattedTextFieldTelefone.setText(emprestimo.getTelefone());
//        jComboBoxPerfil.setSelectedIndex(emprestimo.getPerfil().ordinal());
//        jComboBoxStatus.setSelectedIndex(emprestimo.getStatus().ordinal());
//
//        boolean oProprio = (Vai.USUARIO.getIdColaborador() == emprestimo.getIdColaborador());
//        jLabelPerfil.setEnabled(!oProprio);
//        jComboBoxPerfil.setEnabled(!oProprio);
//        jLabelStatus.setEnabled(!oProprio);
//        jComboBoxStatus.setEnabled(!oProprio);
//
//        jButtonAlterarSenha.setEnabled(oProprio);
    }

    //--- FIM MÉTODOS PARA PREENCHIMENTO DA TELA ------------------------------|
    //
    //--- MÉTODOS PARA BOTÕES  ------------------------------------------------>
    //
    //--- FIM MÉTODOS PARA BOTÕES ---------------------------------------------|
    //
    //
    //--- CONSTRUTOR ---------------------------------------------------------->
    //
    /**
     * Creates new form TelaEmprestimo
     */
    public TelaEmprestimo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        jTableColaboradorDetalhe.setTableHeader(null);
        jTableLivroDetalhe.setTableHeader(null);
        this.setLocationRelativeTo(rootPane);
    }

    //--- FIM CONSTRUTOR ------------------------------------------------------|
    //
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanelColaboradores = new javax.swing.JPanel();
        jLabelPesquisarColaborador = new javax.swing.JLabel();
        jTextFieldPesquisarColaborador = new javax.swing.JTextField();
        jLabelFiltrarColaborador = new javax.swing.JLabel();
        jComboBoxFiltrarColaborador = new javax.swing.JComboBox<>();
        jLabelColaboradorDetalhe = new javax.swing.JLabel();
        jScrollPaneColaborador = new javax.swing.JScrollPane();
        jTableColaboradores = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableColaboradorDetalhe = new javax.swing.JTable();
        jLabelStatus = new javax.swing.JLabel();
        jPanelLivros = new javax.swing.JPanel();
        jLabelPesquisarLivro = new javax.swing.JLabel();
        jTextFieldPesquisarLivro = new javax.swing.JTextField();
        jLabelPesquisarCDD = new javax.swing.JLabel();
        jTextFieldPesquisarCDD = new javax.swing.JTextField();
        jLabelFliltrarLivro = new javax.swing.JLabel();
        jComboBoxFiltrarLivro = new javax.swing.JComboBox<>();
        jScrollPaneLivro = new javax.swing.JScrollPane();
        jTableLivro = new javax.swing.JTable();
        jLabelLivroDetalhe = new javax.swing.JLabel();
        jScrollPaneLivroDescricao = new javax.swing.JScrollPane();
        jTextAreaLivroDescricao = new javax.swing.JTextArea();
        jScrollPaneLivroDetalhe = new javax.swing.JScrollPane();
        jTableLivroDetalhe = new javax.swing.JTable();
        jLabelExemplaresLivro = new javax.swing.JLabel();
        jScrollPaneExemplares = new javax.swing.JScrollPane();
        jTableExemplares = new javax.swing.JTable();
        jLabelMotivoDesativacao = new javax.swing.JLabel();
        jTextFieldMotivoDesativacao = new javax.swing.JTextField();
        jPanelComandos = new javax.swing.JPanel();
        jButtonReservar = new javax.swing.JButton();
        jButtonEmprestar = new javax.swing.JButton();
        jButtonDevolver = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButtonCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(1366, 768));

        jPanelColaboradores.setBorder(javax.swing.BorderFactory.createTitledBorder("Colaboradores"));

        jLabelPesquisarColaborador.setText("Pesquisar");

        jTextFieldPesquisarColaborador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesquisarColaboradorKeyReleased(evt);
            }
        });

        jLabelFiltrarColaborador.setText("Filtrar por");

        jComboBoxFiltrarColaborador.setMaximumRowCount(13);
        jComboBoxFiltrarColaborador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFiltrarColaboradorActionPerformed(evt);
            }
        });

        jLabelColaboradorDetalhe.setText("Detalhes do colaborador:");

        jTableColaboradores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Matrícula", "Nome"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableColaboradores.getTableHeader().setResizingAllowed(false);
        jTableColaboradores.getTableHeader().setReorderingAllowed(false);
        jTableColaboradores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableColaboradoresMouseClicked(evt);
            }
        });
        jScrollPaneColaborador.setViewportView(jTableColaboradores);
        if (jTableColaboradores.getColumnModel().getColumnCount() > 0) {
            jTableColaboradores.getColumnModel().getColumn(0).setMinWidth(60);
            jTableColaboradores.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTableColaboradores.getColumnModel().getColumn(0).setMaxWidth(60);
            jTableColaboradores.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTableColaboradores.getColumnModel().getColumn(2).setPreferredWidth(250);
        }

        jTableColaboradorDetalhe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"ID", null},
                {"Matrícula", null},
                {"Nome", null},
                {"Cargo", null},
                {"OAB", null},
                {"Email", null},
                {"Telefone", null},
                {"Perfil", null},
                {"Status", null},
                {"Empréstimos", null},
                {"Saldo Devedor", null},
                {"Reservas", null}
            },
            new String [] {
                "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableColaboradorDetalhe.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        jTableColaboradorDetalhe.setAutoscrolls(false);
        jTableColaboradorDetalhe.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTableColaboradorDetalhe.setGridColor(new java.awt.Color(204, 204, 204));
        jTableColaboradorDetalhe.setName(""); // NOI18N
        jTableColaboradorDetalhe.setOpaque(false);
        jTableColaboradorDetalhe.getTableHeader().setResizingAllowed(false);
        jTableColaboradorDetalhe.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTableColaboradorDetalhe);
        if (jTableColaboradorDetalhe.getColumnModel().getColumnCount() > 0) {
            jTableColaboradorDetalhe.getColumnModel().getColumn(0).setMinWidth(100);
            jTableColaboradorDetalhe.getColumnModel().getColumn(0).setPreferredWidth(100);
            jTableColaboradorDetalhe.getColumnModel().getColumn(0).setMaxWidth(100);
            jTableColaboradorDetalhe.getColumnModel().getColumn(1).setResizable(false);
        }

        jLabelStatus.setText("jLabelStatus");

        javax.swing.GroupLayout jPanelColaboradoresLayout = new javax.swing.GroupLayout(jPanelColaboradores);
        jPanelColaboradores.setLayout(jPanelColaboradoresLayout);
        jPanelColaboradoresLayout.setHorizontalGroup(
            jPanelColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelColaboradoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPaneColaborador, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabelStatus)
                    .addComponent(jLabelColaboradorDetalhe)
                    .addGroup(jPanelColaboradoresLayout.createSequentialGroup()
                        .addGroup(jPanelColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelFiltrarColaborador)
                            .addComponent(jComboBoxFiltrarColaborador, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 18, Short.MAX_VALUE)
                        .addGroup(jPanelColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelPesquisarColaborador)
                            .addComponent(jTextFieldPesquisarColaborador, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelColaboradoresLayout.setVerticalGroup(
            jPanelColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelColaboradoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelFiltrarColaborador)
                    .addComponent(jLabelPesquisarColaborador, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(0, 0, 0)
                .addGroup(jPanelColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxFiltrarColaborador, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPesquisarColaborador, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneColaborador, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelColaboradorDetalhe)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelStatus)
                .addContainerGap())
        );

        jPanelLivros.setBorder(javax.swing.BorderFactory.createTitledBorder("Livros"));

        jLabelPesquisarLivro.setText("Pesquisar Livro");

        jLabelPesquisarCDD.setText("Pesquisar CDD");

        jLabelFliltrarLivro.setText("Filtrar por");

        jComboBoxFiltrarLivro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxFiltrarLivroActionPerformed(evt);
            }
        });

        jTableLivro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Título", "Autor", "Edição"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableLivro.getTableHeader().setReorderingAllowed(false);
        jTableLivro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableLivroMouseClicked(evt);
            }
        });
        jScrollPaneLivro.setViewportView(jTableLivro);
        if (jTableLivro.getColumnModel().getColumnCount() > 0) {
            jTableLivro.getColumnModel().getColumn(0).setMinWidth(60);
            jTableLivro.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTableLivro.getColumnModel().getColumn(0).setMaxWidth(60);
            jTableLivro.getColumnModel().getColumn(1).setMinWidth(250);
            jTableLivro.getColumnModel().getColumn(1).setPreferredWidth(250);
            jTableLivro.getColumnModel().getColumn(1).setMaxWidth(250);
        }

        jLabelLivroDetalhe.setText("Detalhes do livro:");

        jTextAreaLivroDescricao.setColumns(20);
        jTextAreaLivroDescricao.setRows(2);
        jScrollPaneLivroDescricao.setViewportView(jTextAreaLivroDescricao);

        jTableLivroDetalhe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"ID", null},
                {"Título", null},
                {"Autor", null},
                {"Editora", null},
                {"Edição", null},
                {"Ano", null},
                {"CDD", null},
                {"ISBN", null}
            },
            new String [] {
                "", ""
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableLivroDetalhe.setAutoscrolls(false);
        jTableLivroDetalhe.setGridColor(new java.awt.Color(204, 204, 204));
        jScrollPaneLivroDetalhe.setViewportView(jTableLivroDetalhe);
        if (jTableLivroDetalhe.getColumnModel().getColumnCount() > 0) {
            jTableLivroDetalhe.getColumnModel().getColumn(0).setMinWidth(50);
            jTableLivroDetalhe.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTableLivroDetalhe.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        jLabelExemplaresLivro.setText("Exemplares do livro:");

        jTableExemplares.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Status", "Aquisição", "Preço (R$)"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableExemplares.getTableHeader().setReorderingAllowed(false);
        jScrollPaneExemplares.setViewportView(jTableExemplares);
        if (jTableExemplares.getColumnModel().getColumnCount() > 0) {
            jTableExemplares.getColumnModel().getColumn(0).setMinWidth(60);
            jTableExemplares.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTableExemplares.getColumnModel().getColumn(0).setMaxWidth(60);
        }

        jLabelMotivoDesativacao.setText("Motivo da desativação:");

        javax.swing.GroupLayout jPanelLivrosLayout = new javax.swing.GroupLayout(jPanelLivros);
        jPanelLivros.setLayout(jPanelLivrosLayout);
        jPanelLivrosLayout.setHorizontalGroup(
            jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLivrosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneLivroDescricao)
                    .addComponent(jScrollPaneLivro)
                    .addGroup(jPanelLivrosLayout.createSequentialGroup()
                        .addComponent(jScrollPaneLivroDetalhe, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPaneExemplares, javax.swing.GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
                            .addGroup(jPanelLivrosLayout.createSequentialGroup()
                                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelExemplaresLivro)
                                    .addComponent(jLabelMotivoDesativacao))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jTextFieldMotivoDesativacao)))
                    .addGroup(jPanelLivrosLayout.createSequentialGroup()
                        .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelLivroDetalhe)
                            .addGroup(jPanelLivrosLayout.createSequentialGroup()
                                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelFliltrarLivro)
                                    .addComponent(jComboBoxFiltrarLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(87, 87, 87)
                                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabelPesquisarCDD)
                                    .addComponent(jTextFieldPesquisarCDD, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
                                .addGap(40, 40, 40)
                                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldPesquisarLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelPesquisarLivro))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelLivrosLayout.setVerticalGroup(
            jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLivrosLayout.createSequentialGroup()
                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLivrosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabelFliltrarLivro)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxFiltrarLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldPesquisarCDD, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanelLivrosLayout.createSequentialGroup()
                        .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelPesquisarCDD)
                            .addComponent(jLabelPesquisarLivro, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextFieldPesquisarLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPaneLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelLivroDetalhe)
                .addGap(0, 0, 0)
                .addComponent(jScrollPaneLivroDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelExemplaresLivro)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelLivrosLayout.createSequentialGroup()
                        .addComponent(jScrollPaneExemplares, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabelMotivoDesativacao))
                    .addComponent(jScrollPaneLivroDetalhe, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0)
                .addComponent(jTextFieldMotivoDesativacao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jButtonReservar.setText("Reservar");

        jButtonEmprestar.setText("Emprestar");

        jButtonDevolver.setText("Devolver");

        jButton1.setText("Receber");

        jButton2.setText("Comprovante");

        jButtonCancelar.setText("Cancelar");

        javax.swing.GroupLayout jPanelComandosLayout = new javax.swing.GroupLayout(jPanelComandos);
        jPanelComandos.setLayout(jPanelComandosLayout);
        jPanelComandosLayout.setHorizontalGroup(
            jPanelComandosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelComandosLayout.createSequentialGroup()
                .addGroup(jPanelComandosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelComandosLayout.createSequentialGroup()
                        .addComponent(jButtonReservar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(140, 140, 140)
                        .addComponent(jButtonEmprestar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonDevolver, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jButtonCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanelComandosLayout.setVerticalGroup(
            jPanelComandosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelComandosLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanelComandosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonReservar)
                    .addComponent(jButtonEmprestar)
                    .addComponent(jButtonDevolver)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButtonCancelar)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanelColaboradores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelComandos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelLivros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelLivros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelColaboradores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelComandos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTableColaboradoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableColaboradoresMouseClicked
        try {
            preencherJTableColaboradorDetalhe();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jTableColaboradoresMouseClicked

    private void jTextFieldPesquisarColaboradorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisarColaboradorKeyReleased
        try {
            pesquisarColaboradores(jTextFieldPesquisarColaborador.getText().toLowerCase());
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jTextFieldPesquisarColaboradorKeyReleased

    private void jComboBoxFiltrarColaboradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFiltrarColaboradorActionPerformed
        try {
            filtrarColaboradores(jComboBoxFiltrarColaborador.getSelectedIndex());
        } catch (Exception e) {
            mensagem.erro(new Exception("Erro ao filtrar colaboradores!\n" + e));
        }
    }//GEN-LAST:event_jComboBoxFiltrarColaboradorActionPerformed

    private void jTableLivroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableLivroMouseClicked
        try {
            preencherJTableLivroDetalhe();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jTableLivroMouseClicked

    private void jComboBoxFiltrarLivroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFiltrarLivroActionPerformed
        try {
            filtrarLivros(jComboBoxFiltrarLivro.getSelectedIndex());
            filtrarExemplares(Integer.parseInt(jTableLivro.getValueAt(jTableLivro.getSelectedRow(), 0).toString()));
        } catch (Exception e) {
            mensagem.erro(new Exception("Erro ao filtrar livros!\n" + e));
        }
    }//GEN-LAST:event_jComboBoxFiltrarLivroActionPerformed

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
            java.util.logging.Logger.getLogger(TelaEmprestimo.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaEmprestimo.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaEmprestimo.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaEmprestimo.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaEmprestimo dialog = new TelaEmprestimo(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonCancelar;
    private javax.swing.JButton jButtonDevolver;
    private javax.swing.JButton jButtonEmprestar;
    private javax.swing.JButton jButtonReservar;
    private javax.swing.JComboBox<String> jComboBoxFiltrarColaborador;
    private javax.swing.JComboBox<String> jComboBoxFiltrarLivro;
    private javax.swing.JLabel jLabelColaboradorDetalhe;
    private javax.swing.JLabel jLabelExemplaresLivro;
    private javax.swing.JLabel jLabelFiltrarColaborador;
    private javax.swing.JLabel jLabelFliltrarLivro;
    private javax.swing.JLabel jLabelLivroDetalhe;
    private javax.swing.JLabel jLabelMotivoDesativacao;
    private javax.swing.JLabel jLabelPesquisarCDD;
    private javax.swing.JLabel jLabelPesquisarColaborador;
    private javax.swing.JLabel jLabelPesquisarLivro;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JPanel jPanelColaboradores;
    private javax.swing.JPanel jPanelComandos;
    private javax.swing.JPanel jPanelLivros;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPaneColaborador;
    private javax.swing.JScrollPane jScrollPaneExemplares;
    private javax.swing.JScrollPane jScrollPaneLivro;
    private javax.swing.JScrollPane jScrollPaneLivroDescricao;
    private javax.swing.JScrollPane jScrollPaneLivroDetalhe;
    private javax.swing.JTable jTableColaboradorDetalhe;
    private javax.swing.JTable jTableColaboradores;
    private javax.swing.JTable jTableExemplares;
    private javax.swing.JTable jTableLivro;
    private javax.swing.JTable jTableLivroDetalhe;
    private javax.swing.JTextArea jTextAreaLivroDescricao;
    private javax.swing.JTextField jTextFieldMotivoDesativacao;
    private javax.swing.JTextField jTextFieldPesquisarCDD;
    private javax.swing.JTextField jTextFieldPesquisarColaborador;
    private javax.swing.JTextField jTextFieldPesquisarLivro;
    // End of variables declaration//GEN-END:variables
}
