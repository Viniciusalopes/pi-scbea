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
import utilidades.Mensagens;

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
        if (jTableLivro.getColumnModel().getColumnCount() > 0) {
            jTableLivro.getColumnModel().getColumn(0).setMinWidth(60);
            jTableLivro.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTableLivro.getColumnModel().getColumn(0).setMaxWidth(60);
            jTableLivro.getColumnModel().getColumn(1).setMinWidth(250);
            jTableLivro.getColumnModel().getColumn(1).setPreferredWidth(250);
            jTableLivro.getColumnModel().getColumn(1).setMaxWidth(250);
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
                    jTextAreaLivroDescricao.setText(dados[8]);
                    break;
                }
            }
        } else {
            for (int i = 0; i < jTableLivroDetalhe.getRowCount(); i++) {
                jTableLivroDetalhe.setValueAt("", i, 1);
            }

        }
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
        if (jTableExemplares.getColumnModel().getColumnCount() > 0) {
            jTableExemplares.getColumnModel().getColumn(0).setMinWidth(60);
            jTableExemplares.getColumnModel().getColumn(0).setPreferredWidth(60);
            jTableExemplares.getColumnModel().getColumn(0).setMaxWidth(60);
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
        jComboBoxFiltrarColaborador.addItem(EnumTipoStatus.ATIVO.toString());
        jComboBoxFiltrarColaborador.addItem(EnumTipoStatus.INATIVO.toString());
        jComboBoxFiltrarColaborador.addItem(EnumTipoStatus.ADIMPLENTE.toString());
        jComboBoxFiltrarColaborador.addItem(EnumTipoStatus.INADIMPLENTE.toString());
        jComboBoxFiltrarColaborador.addItem(EnumFiltrosColaborador.COM_EMPRÉSTIMOS.toString().replace("_", " "));
        jComboBoxFiltrarColaborador.addItem(EnumFiltrosColaborador.COM_RESERVAS.toString().replace("_", " "));

        for (EnumCargo c : EnumCargo.values()) {
            jComboBoxFiltrarColaborador.addItem(c.toString());
        }
        jComboBoxFiltrarColaborador.setSelectedIndex(0);

        jComboBoxFiltrarLivro.removeAllItems();
        jComboBoxFiltrarLivro.addItem(EnumTipoStatus.TODOS.toString());
        jComboBoxFiltrarLivro.addItem(EnumTipoStatus.ATIVO.toString());
        jComboBoxFiltrarLivro.addItem(EnumTipoStatus.INATIVO.toString());
        jComboBoxFiltrarLivro.addItem(EnumTipoStatus.DISPONIVEL.toString());
        jComboBoxFiltrarLivro.addItem(EnumTipoStatus.RESERVADO.toString());
        jComboBoxFiltrarLivro.addItem(EnumTipoStatus.EMPRESTADO.toString());
        jComboBoxFiltrarLivro.setSelectedIndex(0);
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
        jTableColaborador = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableColaboradorDetalhe = new javax.swing.JTable();
        jLabelStatus = new javax.swing.JLabel();
        jButtonIncluirColaborador = new javax.swing.JButton();
        jButtonReceberMulta = new javax.swing.JButton();
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
        jLabel1 = new javax.swing.JLabel();
        jButtonReservarLivro = new javax.swing.JButton();
        jButtonCancelarReserva = new javax.swing.JButton();
        jButtonComprovante = new javax.swing.JButton();
        jButtonEmprestar = new javax.swing.JButton();
        jButtonDevolver = new javax.swing.JButton();

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

        jButtonIncluirColaborador.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButtonIncluirColaborador.setText("Incluir Colaborador");
        jButtonIncluirColaborador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIncluirColaboradorActionPerformed(evt);
            }
        });

        jButtonReceberMulta.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButtonReceberMulta.setText("Receber Multa");
        jButtonReceberMulta.setEnabled(false);

        javax.swing.GroupLayout jPanelColaboradoresLayout = new javax.swing.GroupLayout(jPanelColaboradores);
        jPanelColaboradores.setLayout(jPanelColaboradoresLayout);
        jPanelColaboradoresLayout.setHorizontalGroup(
            jPanelColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelColaboradoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelColaboradoresLayout.createSequentialGroup()
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
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanelColaboradoresLayout.createSequentialGroup()
                        .addComponent(jButtonIncluirColaborador)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButtonReceberMulta, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelStatus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonIncluirColaborador)
                    .addComponent(jButtonReceberMulta))
                .addContainerGap())
        );

        jPanelLivros.setBorder(javax.swing.BorderFactory.createTitledBorder("Livros"));

        jLabelPesquisarLivro.setText("Pesquisar Livro");

        jTextFieldPesquisarLivro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesquisarLivroKeyReleased(evt);
            }
        });

        jLabelPesquisarCDD.setText("Pesquisar CDD");

        jTextFieldPesquisarCDD.setVerifyInputWhenFocusTarget(false);
        jTextFieldPesquisarCDD.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesquisarCDDKeyReleased(evt);
            }
        });

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
        jTextAreaLivroDescricao.setLineWrap(true);
        jTextAreaLivroDescricao.setRows(4);
        jTextAreaLivroDescricao.setWrapStyleWord(true);
        jTextAreaLivroDescricao.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTextAreaLivroDescricao.setEnabled(false);
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
        }

        jLabelMotivoDesativacao.setText("Motivo da desativação:");

        jTextFieldMotivoDesativacao.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTextFieldMotivoDesativacao.setEnabled(false);

        jLabel1.setText("Descrição do livro:");

        jButtonReservarLivro.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButtonReservarLivro.setText("Reservar Livro");
        jButtonReservarLivro.setEnabled(false);
        jButtonReservarLivro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonReservarLivroActionPerformed(evt);
            }
        });

        jButtonCancelarReserva.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButtonCancelarReserva.setText("Cancelar Reserva");
        jButtonCancelarReserva.setEnabled(false);

        jButtonComprovante.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButtonComprovante.setText("Comprovante");
        jButtonComprovante.setEnabled(false);

        jButtonEmprestar.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButtonEmprestar.setText("Emprestar");
        jButtonEmprestar.setEnabled(false);

        jButtonDevolver.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButtonDevolver.setText("Devolver");
        jButtonDevolver.setEnabled(false);

        javax.swing.GroupLayout jPanelLivrosLayout = new javax.swing.GroupLayout(jPanelLivros);
        jPanelLivros.setLayout(jPanelLivrosLayout);
        jPanelLivrosLayout.setHorizontalGroup(
            jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLivrosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneLivro)
                    .addComponent(jScrollPaneLivroDescricao)
                    .addGroup(jPanelLivrosLayout.createSequentialGroup()
                        .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPaneLivroDetalhe, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelLivroDetalhe))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldMotivoDesativacao, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPaneExemplares, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanelLivrosLayout.createSequentialGroup()
                                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelMotivoDesativacao)
                                    .addComponent(jLabelExemplaresLivro))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(jPanelLivrosLayout.createSequentialGroup()
                        .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(jPanelLivrosLayout.createSequentialGroup()
                                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelFliltrarLivro)
                                    .addComponent(jComboBoxFiltrarLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanelLivrosLayout.createSequentialGroup()
                                        .addComponent(jLabelPesquisarCDD)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabelPesquisarLivro))
                                    .addGroup(jPanelLivrosLayout.createSequentialGroup()
                                        .addComponent(jTextFieldPesquisarCDD, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jTextFieldPesquisarLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(jButtonReservarLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(7, 7, 7)
                                .addComponent(jButtonCancelarReserva))
                            .addGroup(jPanelLivrosLayout.createSequentialGroup()
                                .addComponent(jButtonEmprestar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonDevolver, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71)
                                .addComponent(jButtonComprovante, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanelLivrosLayout.setVerticalGroup(
            jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLivrosLayout.createSequentialGroup()
                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPesquisarCDD)
                    .addComponent(jLabelPesquisarLivro)
                    .addGroup(jPanelLivrosLayout.createSequentialGroup()
                        .addComponent(jLabelFliltrarLivro)
                        .addGap(0, 0, 0)
                        .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxFiltrarLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldPesquisarCDD, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldPesquisarLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonReservarLivro)
                            .addComponent(jButtonCancelarReserva))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(0, 0, 0)
                .addComponent(jScrollPaneLivroDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelExemplaresLivro)
                    .addComponent(jLabelLivroDetalhe))
                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneLivroDetalhe, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPaneExemplares, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLivrosLayout.createSequentialGroup()
                        .addComponent(jLabelMotivoDesativacao)
                        .addGap(5, 5, 5)
                        .addComponent(jTextFieldMotivoDesativacao, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 40, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelLivrosLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButtonDevolver)
                            .addComponent(jButtonEmprestar)
                            .addComponent(jButtonComprovante))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jPanelColaboradores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelLivros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void jComboBoxFiltrarLivroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFiltrarLivroActionPerformed
        try {
            filtrarLivros(jComboBoxFiltrarLivro.getSelectedIndex());
            if (jTableLivro.getSelectedRow() > -1) {
                filtrarExemplares(Integer.parseInt(jTableLivro.getValueAt(jTableLivro.getSelectedRow(), 0).toString()));
            }
            ativarBotoes();
        } catch (Exception e) {
            mensagem.erro(new Exception("Erro ao filtrar livros!\n" + e.getMessage()));
        }
    }//GEN-LAST:event_jComboBoxFiltrarLivroActionPerformed

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
            if (jTableColaborador.getRowCount() == 0) {
                throw new Exception("Selecione um colaborador!");
            }
            if (jTableColaborador.getSelectedRow() == -1) {
                throw new Exception("Selecione um colaborador!");
            }

            idColaborador = Integer.parseInt(jTableColaborador.getValueAt(jTableColaborador.getSelectedRow(), 0).toString());

            if (jTableLivro.getRowCount() == 0) {
                throw new Exception("Selecione um livro!");
            }
            if (jTableLivro.getSelectedRow() == -1) {
                throw new Exception("Selecione um livro!");
            }
            idLivro = Integer.parseInt(jTableLivro.getValueAt(jTableLivro.getSelectedRow(), 0).toString());

            idReserva = controleTelaEmprestimo.incluirReserva(idColaborador, idLivro);
            if (mensagem.pergunta("Reserva incluída com sucesso!\nDeseja imprimir o recibo agora?") == 0) {
                Reserva reserva = controleTelaEmprestimo.getReserva(idReserva);
                String titulo = reserva.getLivro().getTitulo();
                if (titulo.length() > 40) {
                    titulo = titulo.substring(0, 40) + "...";
                }
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

        } catch (Exception e) {
            mensagem.alerta(e.getMessage());
        }
    }//GEN-LAST:event_jButtonReservarLivroActionPerformed

    private void jTextFieldPesquisarCDDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisarCDDKeyReleased
        try {
            ativarBotoes();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jTextFieldPesquisarCDDKeyReleased

    private void jTextFieldPesquisarLivroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisarLivroKeyReleased
        try {
            ativarBotoes();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jTextFieldPesquisarLivroKeyReleased

    private void jTableColaboradorMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableColaboradorMouseReleased

    }//GEN-LAST:event_jTableColaboradorMouseReleased

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
    private javax.swing.JButton jButtonCancelarReserva;
    private javax.swing.JButton jButtonComprovante;
    private javax.swing.JButton jButtonDevolver;
    private javax.swing.JButton jButtonEmprestar;
    private javax.swing.JButton jButtonIncluirColaborador;
    private javax.swing.JButton jButtonReceberMulta;
    private javax.swing.JButton jButtonReservarLivro;
    private javax.swing.JComboBox<String> jComboBoxFiltrarColaborador;
    private javax.swing.JComboBox<String> jComboBoxFiltrarLivro;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JPanel jPanelLivros;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPaneColaborador;
    private javax.swing.JScrollPane jScrollPaneExemplares;
    private javax.swing.JScrollPane jScrollPaneLivro;
    private javax.swing.JScrollPane jScrollPaneLivroDescricao;
    private javax.swing.JScrollPane jScrollPaneLivroDetalhe;
    private javax.swing.JTable jTableColaborador;
    private javax.swing.JTable jTableColaboradorDetalhe;
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
