/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import classes.Reserva;
import controle.ControleTelaEmprestimo;
import enumeradores.EnumAcao;
import enumeradores.EnumCargo;
import enumeradores.EnumFiltrosColaborador;
import enumeradores.EnumTipoStatus;
import interfaces.ITelaCadastro;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import utilidades.Mensagens;
import static utilidades.StringUtil.truncar;

/**
 *
 * @author Vinicius
 */
public class TelaEmprestimo extends javax.swing.JDialog implements ITelaCadastro {

    //--- ATRIBUTOS ----------------------------------------------------------->
    private int idColaborador = 0;
    private int idLivro = 0;
    private int idReserva = 0;
    private int idEmprestimo = 0;

    private int linha = 0;
    private int coluna = 0;
    private int valor = 0;
    private int cont = 0;

    private boolean incluirNoResultado = true;
    private boolean enable = false;
    private boolean visible = false;

    private Mensagens mensagem = new Mensagens();
    private EnumAcao acao = null;
    private ControleTelaEmprestimo controleTelaEmprestimo = null;
    private SimpleDateFormat formatoData = null;

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
        this.idEmprestimo = id;
    }

    @Override
    public void setAcao(EnumAcao acao) {
        this.acao = acao;
        this.setTitle(acao.toString() + " cadastro de Empréstimos e Reservas");
    }

    @Override
    public void setVisible(boolean b) {
        try {
            mensagem = new Mensagens();
            formatoData = new SimpleDateFormat("dd/MM/yyyy");

            controleTelaEmprestimo = new ControleTelaEmprestimo();

            matrizColaborador = controleTelaEmprestimo.getMatrizColaboradores();
            preencherJTableColaboradores(matrizColaborador);
            preencherJTableColaboradorDetalhe();

            matrizLivro = controleTelaEmprestimo.getMatrizLivros();
            preencherJTableLivro(matrizLivro);
            preencherJTableLivroDetalhe();

            matrizExemplar = controleTelaEmprestimo.getMatrizExemplares();

            popularControles();

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
    private void preencherJTableColaboradores(String[][] matriz) throws Exception {

        jTableColaborador.setModel(
                new javax.swing.table.DefaultTableModel(
                        matriz,
                        new String[]{"ID", "Matrícula", "Nome"}) {

            boolean[] canEdit = new boolean[]{false, false, false};

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        if (jTableColaborador.getColumnModel().getColumnCount() > 0) {
            jTableColaborador.getColumnModel().getColumn(0).setMinWidth(60);
            jTableColaborador.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTableColaborador.getColumnModel().getColumn(0).setMaxWidth(60);
            jTableColaborador.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTableColaborador.getColumnModel().getColumn(2).setPreferredWidth(250);
        }
    }

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
            mensagem.erro(new Exception("Erro ao pesquisar!" + e.getMessage()));
        }

    }

    private void preencherJTableColaboradorDetalhe() throws Exception {
        linha = jTableColaborador.getSelectedRow();
        if (linha >= 0) {
            idColaborador = Integer.parseInt(jTableColaborador.getValueAt(linha, 0).toString());

            for (int i = 0; i < matrizFiltroColaborador.length; i++) {
                if (Integer.parseInt(matrizFiltroColaborador[i][0]) == idColaborador) {
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
        jTableColaboradorDetalhe.setTableHeader(null);
    }

    private void preencherJTableLivro(String[][] matriz) throws Exception {

        jTableLivro.setModel(new javax.swing.table.DefaultTableModel(
                matriz,
                new String[]{
                    "ID", "Título", "Autor", "Editora", "Edição"
                }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        if (jTableLivro.getColumnModel().getColumnCount() > 0) {
            jTableLivro.getColumnModel().getColumn(0).setMinWidth(60);
            jTableLivro.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTableLivro.getColumnModel().getColumn(0).setMaxWidth(60);
            jTableLivro.getColumnModel().getColumn(1).setMinWidth(300);
            jTableLivro.getColumnModel().getColumn(1).setPreferredWidth(300);
            jTableLivro.getColumnModel().getColumn(1).setMaxWidth(300);
            jTableLivro.getColumnModel().getColumn(2).setMinWidth(140);
            jTableLivro.getColumnModel().getColumn(2).setPreferredWidth(140);
            jTableLivro.getColumnModel().getColumn(2).setMaxWidth(140);
            jTableLivro.getColumnModel().getColumn(3).setMinWidth(140);
            jTableLivro.getColumnModel().getColumn(3).setPreferredWidth(140);
            jTableLivro.getColumnModel().getColumn(3).setMaxWidth(140);
        }
    }

    private void filtrarLivros(int filtro) throws Exception {
        matrizFiltroLivro = matrizLivro;
    }

    private void preencherJTableLivroDetalhe() throws Exception {
        linha = jTableLivro.getSelectedRow();
        if (linha >= 0) {
            idLivro = Integer.parseInt(jTableLivro.getValueAt(linha, 0).toString());

            for (int i = 0; i < matrizFiltroLivro.length; i++) {
                if (Integer.parseInt(matrizFiltroLivro[i][0]) == idLivro) {
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
        jTableLivroDetalhe.setTableHeader(null);
    }

    private void filtrarExemplares(int idLivro) throws Exception {
        if (matrizExemplar != null) {
            if (matrizExemplar.length > 0) {
                if (idLivro >= 0) {
                    resultadoPesquisa = new ArrayList<>();
                    coluna = 0;

                    for (int i = 0; i < matrizExemplar.length; i++) {
                        if (Integer.parseInt(matrizExemplar[i][3]) == idLivro) {
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

    private void preencherJTableExemplares(String[][] matriz) throws Exception {
        jTableExemplares.setModel(new javax.swing.table.DefaultTableModel(
                matriz, new String[]{"ID", "Status", "Motivo da Desativação"}
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        if (jTableExemplares.getColumnModel().getColumnCount() > 0) {
            jTableExemplares.getColumnModel().getColumn(0).setMinWidth(60);
            jTableExemplares.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTableExemplares.getColumnModel().getColumn(0).setMaxWidth(60);
            jTableExemplares.getColumnModel().getColumn(1).setMinWidth(80);
            jTableExemplares.getColumnModel().getColumn(1).setPreferredWidth(80);
            jTableExemplares.getColumnModel().getColumn(1).setMaxWidth(80);
        }
    }

    //--- FIM MÉTODOS PARA GRID -----------------------------------------------|
    //
    //--- MÉTODOS PARA AÇÕES DE BOTÕES ---------------------------------------->
    //
    private void ativarBotoes() {

        enable = false;
        EnumTipoStatus status = null;
        // Botão reserva
        if (jTableColaborador.getRowCount() > 0 && jTableLivro.getRowCount() > 0) { // Existem linhas nos dois grids
            if (jTableColaborador.getSelectedRow() > -1) { // se tem linha selecionada no grid Colaborador
                if (jTableColaboradorDetalhe.getValueAt(0, 1) != "") { // Se já foi preenchido o detalhe do colaborador
                    status = EnumTipoStatus.valueOf(jTableColaboradorDetalhe.getValueAt(8, 1).toString());
                    if (!status.equals(EnumTipoStatus.INATIVO) && !status.equals(EnumTipoStatus.INADIMPLENTE)) {
                        if (jTableLivro.getSelectedRow() > -1) {
                            enable = true;
                        }
                    }
                }
            }
        }
        jButtonReservarLivro.setEnabled(enable);
    }

    //--- FIM MÉTODOS PARA AÇÕES DE BOTÕES  -----------------------------------|
    //
    //--- MÉTODOS PARA PREENCHIMENTO DA TELA ---------------------------------->
    //
    private void popularControles() {
        jComboBoxFiltrarColaborador.removeAllItems();
        jComboBoxFiltrarColaborador.addItem(EnumFiltrosColaborador.TODOS.toString());
        for (EnumCargo c : EnumCargo.values()) {
            jComboBoxFiltrarColaborador.addItem(c.toString());
        }
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

    private void validarPreenchimentoReserva() throws Exception {
        if (jTableColaborador.getRowCount() == 0) {
            throw new Exception("Selecione um colaborador!");
        }
        if (jTableColaborador.getSelectedRow() == -1) {
            throw new Exception("Selecione um colaborador!");
        }

        if (jTableLivro.getRowCount() == 0) {
            throw new Exception("Selecione um livro!");
        }
        if (jTableLivro.getSelectedRow() == -1) {
            throw new Exception("Selecione um livro!");
        }
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
        jTableColaborador = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableColaboradorDetalhe = new javax.swing.JTable();
        jButtonIncluirColaborador = new javax.swing.JButton();
        jPanelLivros = new javax.swing.JPanel();
        jLabelPesquisarLivro = new javax.swing.JLabel();
        jTextFieldPesquisarLivro = new javax.swing.JTextField();
        jLabelPesquisarCDD = new javax.swing.JLabel();
        jTextFieldPesquisarAreaConhecimento = new javax.swing.JTextField();
        jScrollPaneLivro = new javax.swing.JScrollPane();
        jTableLivro = new javax.swing.JTable();
        jLabelLivroDetalhe = new javax.swing.JLabel();
        jScrollPaneLivroDetalhe = new javax.swing.JScrollPane();
        jTableLivroDetalhe = new javax.swing.JTable();
        jLabelExemplaresLivro = new javax.swing.JLabel();
        jScrollPaneExemplares = new javax.swing.JScrollPane();
        jTableExemplares = new javax.swing.JTable();
        jButtonReservarLivro = new javax.swing.JButton();
        jButtonEmprestar = new javax.swing.JButton();

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

        jTableColaborador.setAutoCreateRowSorter(true);
        jTableColaborador.setModel(new javax.swing.table.DefaultTableModel(
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
        jTableColaborador.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableColaborador.getTableHeader().setResizingAllowed(false);
        jTableColaborador.getTableHeader().setReorderingAllowed(false);
        jTableColaborador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableColaboradorMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableColaboradorMouseClicked(evt);
            }
        });
        jScrollPaneColaborador.setViewportView(jTableColaborador);
        if (jTableColaborador.getColumnModel().getColumnCount() > 0) {
            jTableColaborador.getColumnModel().getColumn(0).setMinWidth(60);
            jTableColaborador.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTableColaborador.getColumnModel().getColumn(0).setMaxWidth(60);
            jTableColaborador.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTableColaborador.getColumnModel().getColumn(2).setPreferredWidth(250);
        }

        jTableColaboradorDetalhe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Cargo", null},
                {"OAB", null},
                {"Status", null},
                {"E-mail", null}
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
        jTableColaboradorDetalhe.setTableHeader(null);
        jScrollPane2.setViewportView(jTableColaboradorDetalhe);
        if (jTableColaboradorDetalhe.getColumnModel().getColumnCount() > 0) {
            jTableColaboradorDetalhe.getColumnModel().getColumn(0).setMinWidth(50);
            jTableColaboradorDetalhe.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTableColaboradorDetalhe.getColumnModel().getColumn(0).setMaxWidth(50);
            jTableColaboradorDetalhe.getColumnModel().getColumn(1).setResizable(false);
        }

        jButtonIncluirColaborador.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButtonIncluirColaborador.setText("Incluir Colaborador");
        jButtonIncluirColaborador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIncluirColaboradorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelColaboradoresLayout = new javax.swing.GroupLayout(jPanelColaboradores);
        jPanelColaboradores.setLayout(jPanelColaboradoresLayout);
        jPanelColaboradoresLayout.setHorizontalGroup(
            jPanelColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelColaboradoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPaneColaborador, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelColaboradoresLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonIncluirColaborador, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanelColaboradoresLayout.setVerticalGroup(
            jPanelColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelColaboradoresLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanelColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelFiltrarColaborador)
                    .addComponent(jLabelPesquisarColaborador, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(0, 0, 0)
                .addGroup(jPanelColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxFiltrarColaborador, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPesquisarColaborador, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneColaborador, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelColaboradorDetalhe)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(154, 154, 154)
                .addComponent(jButtonIncluirColaborador)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelLivros.setBorder(javax.swing.BorderFactory.createTitledBorder("Livros"));

        jLabelPesquisarLivro.setText("Pesquisar Livro");

        jTextFieldPesquisarLivro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesquisarLivroKeyReleased(evt);
            }
        });

        jLabelPesquisarCDD.setText("Pesquisar Área de Conhecimento");

        jTextFieldPesquisarAreaConhecimento.setVerifyInputWhenFocusTarget(false);
        jTextFieldPesquisarAreaConhecimento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesquisarAreaConhecimentoKeyReleased(evt);
            }
        });

        jTableLivro.setAutoCreateRowSorter(true);
        jTableLivro.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Título", "Autor", "Editora", "Edicao"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTableLivro.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableLivro.getTableHeader().setReorderingAllowed(false);
        jTableLivro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                jTableLivroMouseReleased(evt);
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableLivroMouseClicked(evt);
            }
        });
        jTableLivro.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jTableLivroPropertyChange(evt);
            }
        });
        jTableLivro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableLivroKeyReleased(evt);
            }
        });
        jScrollPaneLivro.setViewportView(jTableLivro);
        if (jTableLivro.getColumnModel().getColumnCount() > 0) {
            jTableLivro.getColumnModel().getColumn(0).setMinWidth(60);
            jTableLivro.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTableLivro.getColumnModel().getColumn(0).setMaxWidth(60);
            jTableLivro.getColumnModel().getColumn(1).setMinWidth(300);
            jTableLivro.getColumnModel().getColumn(1).setPreferredWidth(300);
            jTableLivro.getColumnModel().getColumn(1).setMaxWidth(300);
            jTableLivro.getColumnModel().getColumn(2).setMinWidth(140);
            jTableLivro.getColumnModel().getColumn(2).setPreferredWidth(140);
            jTableLivro.getColumnModel().getColumn(2).setMaxWidth(140);
            jTableLivro.getColumnModel().getColumn(3).setMinWidth(140);
            jTableLivro.getColumnModel().getColumn(3).setPreferredWidth(140);
            jTableLivro.getColumnModel().getColumn(3).setMaxWidth(140);
        }

        jLabelLivroDetalhe.setText("Detalhes do livro:");

        jTableLivroDetalhe.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"Ano", null},
                {"Área de Conhecimento", null},
                {"ISBN", null},
                {"Descrição do livro", null}
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
        jTableLivroDetalhe.setTableHeader(null);
        jScrollPaneLivroDetalhe.setViewportView(jTableLivroDetalhe);
        if (jTableLivroDetalhe.getColumnModel().getColumnCount() > 0) {
            jTableLivroDetalhe.getColumnModel().getColumn(0).setMinWidth(150);
            jTableLivroDetalhe.getColumnModel().getColumn(0).setPreferredWidth(150);
            jTableLivroDetalhe.getColumnModel().getColumn(0).setMaxWidth(150);
        }

        jLabelExemplaresLivro.setText("Exemplares do livro:");

        jTableExemplares.setAutoCreateRowSorter(true);
        jTableExemplares.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Status", "Motivo da Desativação"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
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
            jTableExemplares.getColumnModel().getColumn(1).setMinWidth(80);
            jTableExemplares.getColumnModel().getColumn(1).setPreferredWidth(80);
            jTableExemplares.getColumnModel().getColumn(1).setMaxWidth(80);
        }

        jButtonReservarLivro.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButtonReservarLivro.setText("Reservar Livro");
        jButtonReservarLivro.setEnabled(false);
        jButtonReservarLivro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReservarLivroActionPerformed(evt);
            }
        });

        jButtonEmprestar.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButtonEmprestar.setText("Emprestar");
        jButtonEmprestar.setEnabled(false);

        javax.swing.GroupLayout jPanelLivrosLayout = new javax.swing.GroupLayout(jPanelLivros);
        jPanelLivros.setLayout(jPanelLivrosLayout);
        jPanelLivrosLayout.setHorizontalGroup(
            jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLivrosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelLivroDetalhe)
                    .addGroup(jPanelLivrosLayout.createSequentialGroup()
                        .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanelLivrosLayout.createSequentialGroup()
                                .addComponent(jButtonReservarLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonEmprestar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jScrollPaneExemplares, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
                                .addComponent(jScrollPaneLivroDetalhe, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanelLivrosLayout.createSequentialGroup()
                                    .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabelPesquisarLivro)
                                        .addComponent(jTextFieldPesquisarLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGap(18, 18, 18)
                                    .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jTextFieldPesquisarAreaConhecimento, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabelPesquisarCDD)))
                                .addComponent(jLabelExemplaresLivro, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPaneLivro, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addContainerGap())))
        );
        jPanelLivrosLayout.setVerticalGroup(
            jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLivrosLayout.createSequentialGroup()
                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPesquisarLivro)
                    .addGroup(jPanelLivrosLayout.createSequentialGroup()
                        .addComponent(jLabelPesquisarCDD)
                        .addGap(0, 0, 0)
                        .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldPesquisarAreaConhecimento, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldPesquisarLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelLivroDetalhe)
                .addGap(0, 0, 0)
                .addComponent(jScrollPaneLivroDetalhe, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabelExemplaresLivro)
                .addGap(1, 1, 1)
                .addComponent(jScrollPaneExemplares, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonReservarLivro)
                    .addComponent(jButtonEmprestar))
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
                .addComponent(jPanelLivros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelLivros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelColaboradores, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTableColaboradorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableColaboradorMouseClicked
        try {
            preencherJTableColaboradorDetalhe();
            ativarBotoes();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jTableColaboradorMouseClicked

    private void jTextFieldPesquisarColaboradorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisarColaboradorKeyReleased
        try {
            pesquisarColaboradores(jTextFieldPesquisarColaborador.getText().toLowerCase());
            ativarBotoes();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jTextFieldPesquisarColaboradorKeyReleased

    private void jComboBoxFiltrarColaboradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFiltrarColaboradorActionPerformed
        try {
            filtrarColaboradores(jComboBoxFiltrarColaborador.getSelectedIndex());
            ativarBotoes();
        } catch (Exception e) {
            mensagem.erro(new Exception("Erro ao filtrar colaboradores!\n" + e.getMessage()));
        }
    }//GEN-LAST:event_jComboBoxFiltrarColaboradorActionPerformed

    private void jTableLivroMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableLivroMouseClicked
        try {
            preencherJTableLivroDetalhe();
            if (jTableLivro.getSelectedRow() > -1) {
                filtrarExemplares(Integer.parseInt(jTableLivro.getValueAt(jTableLivro.getSelectedRow(), 0).toString()));
            }
            ativarBotoes();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jTableLivroMouseClicked

    private void jTableLivroMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableLivroMouseReleased

    }//GEN-LAST:event_jTableLivroMouseReleased

    private void jTableExemplaresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableExemplaresMouseClicked
        if (jTableExemplares.getSelectedRow() > -1) {

            boolean trueFalse = EnumTipoStatus.valueOf(
                    jTableExemplares.getValueAt(jTableExemplares.getSelectedRow(), 1).toString())
                    .equals(EnumTipoStatus.DISPONIVEL);

            jButtonEmprestar.setEnabled(trueFalse);
        }
    }//GEN-LAST:event_jTableExemplaresMouseClicked

    private void jButtonIncluirColaboradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIncluirColaboradorActionPerformed
        try {
            TelaColaborador telaColaborador = new TelaColaborador(null, true);
            telaColaborador.setId(0);
            telaColaborador.setAcao(EnumAcao.Incluir);
            telaColaborador.setVisible(true);
            matrizColaborador = controleTelaEmprestimo.getMatrizColaboradores();
            jComboBoxFiltrarColaborador.setSelectedIndex(0);
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jButtonIncluirColaboradorActionPerformed

    private void jButtonReservarLivroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReservarLivroActionPerformed
        try {
            validarPreenchimentoReserva();
            idColaborador = Integer.parseInt(jTableColaborador.getValueAt(jTableColaborador.getSelectedRow(), 0).toString());
            idLivro = Integer.parseInt(jTableLivro.getValueAt(jTableLivro.getSelectedRow(), 0).toString());
            String titulo = truncar(jTableLivro.getValueAt(jTableLivro.getSelectedRow(), 1).toString(), 40);

            if (mensagem.pergunta(
                    " DADOS DA RESERVA:"
                    + "\n----------------------------------------"
                    + "\nData: " + formatoData.format(new Date())
                    + "\nColaborador: " + jTableColaborador.getValueAt(jTableColaborador.getSelectedRow(), 2).toString()
                    + "\nMatrícula: " + jTableColaborador.getValueAt(jTableColaborador.getSelectedRow(), 1).toString()
                    + "\nLivro: " + jTableLivro.getValueAt(jTableLivro.getSelectedRow(), 0).toString() + " - " + titulo
                    + "\n" + jTableLivroDetalhe.getValueAt(4, 1) + " ª Edição"
                    + "\n\nConfirma a inclusão da reserva?"
                    + "\n") == 0) {

                idReserva = controleTelaEmprestimo.incluirReserva(idColaborador, idLivro);
                Reserva reserva = controleTelaEmprestimo.getReserva(idReserva);

                if (mensagem.pergunta("Reserva incluída com sucesso!\nDeseja imprimir o recibo agora?") == 0) {
                    mensagem.informacao(
                            " RECIBO DE RESERVA nº " + reserva.getIdReserva()
                            + "\n----------------------------------------"
                            + "\nData: " + formatoData.format(reserva.getdataReserva()).toString()
                            + "\nColaborador: " + reserva.getColaborador().getNomeColaborador()
                            + "\nMatrícula: " + reserva.getColaborador().getMatricula()
                            + "\nLivro: " + reserva.getLivro().getIdLivro() + " - " + titulo
                            + "\n" + reserva.getLivro().getEdicao() + " ª Edição"
                            + "\n"
                    );
                }
                visible = false;
                this.dispose();
            }

        } catch (Exception e) {
            mensagem.alerta(e.getMessage());
        }
    }//GEN-LAST:event_jButtonReservarLivroActionPerformed

    private void jTextFieldPesquisarAreaConhecimentoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisarAreaConhecimentoKeyReleased
        try {
            ativarBotoes();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jTextFieldPesquisarAreaConhecimentoKeyReleased

    private void jTextFieldPesquisarLivroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisarLivroKeyReleased
        try {
            ativarBotoes();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jTextFieldPesquisarLivroKeyReleased

    private void jTableColaboradorMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableColaboradorMouseReleased

    }//GEN-LAST:event_jTableColaboradorMouseReleased

    private void jTableLivroPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jTableLivroPropertyChange

    }//GEN-LAST:event_jTableLivroPropertyChange

    private void jTableLivroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableLivroKeyReleased
        jTableLivroMouseClicked(null);
    }//GEN-LAST:event_jTableLivroKeyReleased

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
    private javax.swing.JButton jButtonEmprestar;
    private javax.swing.JButton jButtonIncluirColaborador;
    private javax.swing.JButton jButtonReservarLivro;
    private javax.swing.JComboBox<String> jComboBoxFiltrarColaborador;
    private javax.swing.JLabel jLabelColaboradorDetalhe;
    private javax.swing.JLabel jLabelExemplaresLivro;
    private javax.swing.JLabel jLabelFiltrarColaborador;
    private javax.swing.JLabel jLabelLivroDetalhe;
    private javax.swing.JLabel jLabelPesquisarCDD;
    private javax.swing.JLabel jLabelPesquisarColaborador;
    private javax.swing.JLabel jLabelPesquisarLivro;
    private javax.swing.JPanel jPanelColaboradores;
    private javax.swing.JPanel jPanelLivros;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPaneColaborador;
    private javax.swing.JScrollPane jScrollPaneExemplares;
    private javax.swing.JScrollPane jScrollPaneLivro;
    private javax.swing.JScrollPane jScrollPaneLivroDetalhe;
    private javax.swing.JTable jTableColaborador;
    private javax.swing.JTable jTableColaboradorDetalhe;
    private javax.swing.JTable jTableExemplares;
    private javax.swing.JTable jTableLivro;
    private javax.swing.JTable jTableLivroDetalhe;
    private javax.swing.JTextField jTextFieldPesquisarAreaConhecimento;
    private javax.swing.JTextField jTextFieldPesquisarColaborador;
    private javax.swing.JTextField jTextFieldPesquisarLivro;
    // End of variables declaration//GEN-END:variables
}
