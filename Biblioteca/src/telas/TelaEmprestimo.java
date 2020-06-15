/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import classes.Emprestimo;
import classes.Renderer;
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
    // Variáveis
    private int id = 0;
    private int idColaborador = 0;
    private int idLivro = 0;
    private int idReserva = 0;
    private int idEmprestimo = 0;
    private int linha = 0;
    private boolean incluirNoResultado = true;
    private EnumAcao acao = null;

    // Controles
    private ControleTelaEmprestimo controleTelaEmprestimo = null;

    // Listas
    private ArrayList<String[]> resultadoPesquisa = null;

    // Objetos
    private Emprestimo emprestimo = null;
    private Reserva reserva = null;

    // Utilidades
    private Mensagens mensagem = new Mensagens();
    private SimpleDateFormat formatoData = null;
    private Renderer renderer = null;

    // Matrizes e vetores
    private String[][] matrizColaborador = null;
    private String[][] matrizFiltroColaborador = null;
    private String[][] matrizPesquisaColaborador = null;
    private String[][] matrizLivro = null;
    private String[][] matrizPesquisaLivro = null;
    private String[][] matrizExemplar = null;
    //
    //--- FIM ATRIBUTOS -------------------------------------------------------|

    //--- MÉTODOS ------------------------------------------------------------->
    //
    //--- MÉTODOS OVERRIDE ---------------------------------------------------->
    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setAcao(EnumAcao acao
    ) {
        this.acao = acao;
        if (acao.equals(EnumAcao.Editar_Emprestimo)) {
            idEmprestimo = id;
        } else if (acao.equals(EnumAcao.Editar_Reserva)) {
            idReserva = id;
        }
        String textoAcao = (acao.toString().contains("Incluir")) ? "Incluir" : "Editar";

        this.setTitle(textoAcao + " cadastro de Empréstimos e Reservas");
    }

    @Override
    public void setVisible(boolean b) {
        try {
            mensagem = new Mensagens();
            formatoData = new SimpleDateFormat("dd/MM/yyyy");
            renderer = new Renderer();

            controleTelaEmprestimo = new ControleTelaEmprestimo();

            popularControles();

            matrizColaborador = controleTelaEmprestimo.getMatrizColaboradores();
            preencherJTableColaboradores(matrizColaborador);
            jComboBoxFiltrarColaboradorActionPerformed(null);
            jTextFieldPesquisarColaboradorKeyReleased(null);

            matrizLivro = controleTelaEmprestimo.getMatrizLivros();
            preencherJTableLivro(matrizLivro);
            jTextFieldPesquisarLivroKeyReleased(null);

            if (acao.equals(EnumAcao.Editar_Emprestimo)) {
                preencherCamposEmprestimo();
            } else if (acao.equals(EnumAcao.Editar_Reserva)) {
                preencherCamposReserva();
            }
        } catch (Exception e) {
            mensagem.erro(e);
        }
        super.setVisible(b);
    }
    //--- FIM MÉTODOS OVERRIDE ------------------------------------------------|

    //
    //--- MÉTODOS PARA CRUD --------------------------------------------------->
    //
    private void salvarReserva() throws Exception {

        idColaborador = Integer.parseInt(jTableColaborador.getValueAt(jTableColaborador.getSelectedRow(), 0).toString());
        idLivro = Integer.parseInt(jTableLivro.getValueAt(jTableLivro.getSelectedRow(), 0).toString());
        String titulo = truncar(jTableLivro.getValueAt(jTableLivro.getSelectedRow(), 1).toString(), 40);
        linha = Integer.parseInt(jTableColaborador.getSelectedRow() + "");
        int linhaLivro = Integer.parseInt(jTableLivro.getSelectedRow() + "");

        if (mensagem.pergunta(
                " DADOS DA RESERVA:"
                + "\n----------------------------------------"
                + "\nData: " + formatoData.format(new Date())
                + "\nColaborador: " + jTableColaborador.getValueAt(linha, 2).toString()
                + "\nMatrícula: " + jTableColaborador.getValueAt(linha, 1).toString()
                + "\nLivro: " + jTableLivro.getValueAt(linhaLivro, 0).toString() + " - " + titulo
                + "\n" + jTableLivro.getValueAt(linhaLivro, 4) + " ª Edição"
                + "\n\nConfirma a inclusão da reserva?"
                + "\n") == 0) {

            idReserva = controleTelaEmprestimo.incluirReserva(idColaborador, idLivro);
            reserva = controleTelaEmprestimo.buscarReserva(idReserva);

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
            this.dispose();
        }
    }

    private void salvarEmprestimo() throws Exception {
    }

    //
    //--- FIM MÉTODOS PARA CRUD -----------------------------------------------|
    //
    //--- MÉTODOS PARA GRID --------------------------------------------------->
    //
    //--- MÉTODOS PARA GRID - COLABORADOR ------------------------------------->
    //
    private void filtrarColaboradores(int filtro) throws Exception {
        if (matrizColaborador != null) {
            if (matrizColaborador.length > 0) {
                if (filtro >= 0) {
                    resultadoPesquisa = new ArrayList<>();

                    for (int i = 0; i < matrizColaborador.length; i++) {
                        incluirNoResultado = false;
                        switch (filtro) {
                            case -1: // Não tem filtro selecionado
                                break;

                            case 0: // TODOS_OS_REGISTROS
                                incluirNoResultado = true;
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
                            for (int j = 0; j < matrizFiltroColaborador[i].length; j++) {
                                if (j != 3 && matrizFiltroColaborador[i][j].toLowerCase().contains(texto)) {
                                    resultadoPesquisa.add(matrizFiltroColaborador[i]);
                                }
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
            jTableColaborador.getColumnModel().getColumn(1).setMinWidth(100);
            jTableColaborador.getColumnModel().getColumn(1).setPreferredWidth(100);
            jTableColaborador.getColumnModel().getColumn(1).setMaxWidth(100);

            // Alinhamento de colunas
            jTableColaborador.getColumnModel().getColumn(0).setCellRenderer(renderer.getRendererDireita());
            jTableColaborador.getColumnModel().getColumn(1).setCellRenderer(renderer.getRendererDireita());
        }
    }

    private void preencherJTableColaboradorDetalhe() throws Exception {
        linha = jTableColaborador.getSelectedRow();
        if (linha >= 0) {
            idColaborador = Integer.parseInt(jTableColaborador.getValueAt(linha, 0).toString());

            for (int i = 0; i < matrizFiltroColaborador.length; i++) {
                if (Integer.parseInt(matrizFiltroColaborador[i][0]) == idColaborador) {
                    String[] dados = matrizFiltroColaborador[i];

                    jTableColaboradorDetalhe.setValueAt(matrizFiltroColaborador[i][3], 0, 1);
                    jTableColaboradorDetalhe.setValueAt(matrizFiltroColaborador[i][4], 1, 1);
                    jTableColaboradorDetalhe.setValueAt(matrizFiltroColaborador[i][8], 2, 1);
                    jTableColaboradorDetalhe.setValueAt(matrizFiltroColaborador[i][5], 3, 1);
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

    //--- FIM MÉTODOS PARA GRID - COLABORADOR ---------------------------------|
    //
    //--- MÉTODOS PARA GRID - LIVRO-------------------------------------------->
    //
    private void pesquisarLivros(String texto) throws Exception {
        try {
            if (matrizLivro != null) {
                if (matrizLivro.length > 0) {
                    texto = texto.toLowerCase().trim();
                    if (texto.length() == 0) {
                        matrizPesquisaLivro = matrizLivro;
                    } else {
                        resultadoPesquisa = new ArrayList<>();

                        for (int i = 0; i < matrizLivro.length; i++) {
                            for (int j = 0; j < matrizLivro[i].length; j++) {
                                if (matrizLivro[i][j].toLowerCase().contains(texto)) {
                                    resultadoPesquisa.add(matrizLivro[i]);
                                    break;
                                }
                            }
                        }

                        matrizPesquisaLivro = new String[resultadoPesquisa.size()][matrizLivro[0].length];

                        for (int i = 0; i < resultadoPesquisa.size(); i++) {
                            matrizPesquisaLivro[i] = resultadoPesquisa.get(i);
                        }
                    }
                }
                preencherJTableLivro(matrizPesquisaLivro);
                preencherJTableLivroDetalhe();
            }

        } catch (Exception e) {
            mensagem.erro(new Exception("Erro ao pesquisar!" + e.getMessage()));
        }

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

            // Alinhamento de colunas
            jTableLivro.getColumnModel().getColumn(0).setCellRenderer(renderer.getRendererDireita());
            jTableLivro.getColumnModel().getColumn(4).setCellRenderer(renderer.getRendererDireita());
        }
    }

    private void preencherJTableLivroDetalhe() throws Exception {
        linha = jTableLivro.getSelectedRow();
        if (linha >= 0) {
            idLivro = Integer.parseInt(jTableLivro.getValueAt(linha, 0).toString());

            for (int i = 0; i < matrizPesquisaLivro.length; i++) {
                if (Integer.parseInt(matrizPesquisaLivro[i][0]) == idLivro) {
                    String[] dados = matrizPesquisaLivro[i];

                    jTableLivroDetalhe.setValueAt(matrizPesquisaLivro[i][5], 0, 1);
                    jTableLivroDetalhe.setValueAt(matrizPesquisaLivro[i][6], 1, 1);
                    jTableLivroDetalhe.setValueAt(matrizPesquisaLivro[i][7], 2, 1);
                    jTableLivroDetalhe.setValueAt(matrizPesquisaLivro[i][8], 3, 1);
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

    //--- FIM MÉTODOS PARA GRID - LIVRO ---------------------------------------|
    //
    //--- MÉTODOS PARA GRID - EXEMPLAR ---------------------------------------->
    //
    private void filtrarExemplares(int idLivro) throws Exception {
        if (idLivro >= 0) {
            matrizExemplar = controleTelaEmprestimo.getMatrizExemplares(idLivro);
            preencherJTableExemplares(matrizExemplar);
        }
    }

    private void preencherJTableExemplares(String[][] matriz) throws Exception {
        jTableExemplares.setModel(new javax.swing.table.DefaultTableModel(
                matriz, new String[]{"ID", "Status", "Motivo da Desativação"}
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false
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

            // Alinhamento de colunas
            jTableExemplares.getColumnModel().getColumn(0).setCellRenderer(renderer.getRendererDireita());
            jTableExemplares.getColumnModel().getColumn(1).setCellRenderer(renderer.getRendererCentro());
        }
    }

    //--- FIM MÉTODOS PARA GRID - EXEMPLAR ------------------------------------|
    //
    //--- FIM MÉTODOS PARA GRID -----------------------------------------------|
    //
    //--- MÉTODOS PARA BOTÕES  ------------------------------------------------>
    //
    private void ativarBotoes() {
        boolean reservar = false;
        boolean emprestar = false;
        EnumTipoStatus status = null;

        if (jTableColaborador.getRowCount() > 0 && jTableLivro.getRowCount() > 0) {                      // Existem linhas nos dois grids
            if (jTableColaborador.getSelectedRow() > -1) {                                               // Se selecionou um colaborador
                if (jTableColaboradorDetalhe.getValueAt(0, 1) != "") {                                   // Se já foi preenchido o detalhe do colaborador
                    status = EnumTipoStatus.valueOf(jTableColaboradorDetalhe.getValueAt(2, 1).toString());
                    if (!status.equals(EnumTipoStatus.INATIVO) && !status.equals(EnumTipoStatus.INADIMPLENTE)) {
                        if (jTableLivro.getSelectedRow() > -1) {                                         // Se selecionou um livro
                            reservar = true;                                                             // Ativa o botão
                        }
                        if (jTableExemplares.getRowCount() > 0) {                                        // Se existem exemplares
                            if (jTableExemplares.getSelectedRow() > -1) {                                // Se selecionou um exemplar
                                status = EnumTipoStatus.valueOf(jTableExemplares.getValueAt(jTableExemplares.getSelectedRow(), 1).toString());
                                if (status.equals(EnumTipoStatus.DISPONIVEL)) {                          // Se está disponível
                                    emprestar = true;                                                    // Ativa o botão
                                }
                            }
                        }
                    }
                }
            }
        }
        jButtonReservarLivro.setEnabled(reservar);
        jButtonIncluirExemplar.setEnabled(reservar);
        jButtonEmprestar.setEnabled(emprestar);

        // Botão emprestar
    }

    private void exibirTelaColaborador() throws Exception {
        TelaColaborador telaColaborador = new TelaColaborador(null, true);
        telaColaborador.setId(0);
        telaColaborador.setAcao(EnumAcao.Incluir);
        telaColaborador.setVisible(true);
        matrizColaborador = controleTelaEmprestimo.getMatrizColaboradores();
        preencherJTableColaboradores(matrizColaborador);
    }

    private void exibirTelaLivro() throws Exception {
        TelaLivro telalivro = new TelaLivro(null, true);
        telalivro.setId(0);
        telalivro.setAcao(EnumAcao.Incluir);
        telalivro.setVisible(true);
        matrizLivro = controleTelaEmprestimo.getMatrizLivros();
        preencherJTableLivro(matrizLivro);
    }

    private void exibirTelaExemplar() throws Exception {
        TelaLivro telalivro = new TelaLivro(null, true);
        telalivro.setId(Integer.parseInt(jTableLivro.getValueAt(jTableLivro.getSelectedRow(), 0).toString()));
        telalivro.setAcao(EnumAcao.Editar);
        telalivro.setVisible(true);
        matrizLivro = controleTelaEmprestimo.getMatrizLivros();
        preencherJTableExemplares(matrizLivro);
    }

    //--- FIM MÉTODOS PARA BOTÕES ---------------------------------------------|
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

    private void preencherCamposReserva() throws Exception {
        reserva = controleTelaEmprestimo.buscarReserva(idReserva);

        jComboBoxFiltrarColaboradorActionPerformed(null);

        jTextFieldPesquisarColaborador.setText(reserva.getColaborador().getNomeColaborador());
        jTextFieldPesquisarColaboradorKeyReleased(null);

        for (int i = 0; i < jTableColaborador.getRowCount(); i++) {
            if (Integer.parseInt(jTableColaborador.getValueAt(i, 0).toString()) == reserva.getColaborador().getIdColaborador()) {
                jTableColaborador.getSelectionModel().setSelectionInterval(i, i);
                jTableColaboradorMouseClicked(null);
                break;
            }
        }

        jTextFieldPesquisarLivro.setText(reserva.getLivro().getIsbn());
        jTextFieldPesquisarLivroKeyReleased(null);

        for (int i = 0; i < matrizLivro.length; i++) {
            if (matrizLivro[i][7].equals(reserva.getLivro().getIsbn())) {
                for (int j = 0; j < jTableLivro.getRowCount(); j++) {
                    if (Integer.parseInt(jTableLivro.getValueAt(j, 0).toString()) == reserva.getLivro().getIdLivro()) {
                        jTableLivro.getSelectionModel().setSelectionInterval(j, j);
                        jTableLivroMouseClicked(null);
                        break;
                    }
                }
            }
        }
    }

    private void preencherCamposEmprestimo() throws Exception {
        mensagem.alerta("Implementar");
        emprestimo = controleTelaEmprestimo.buscarEmprestimo(idEmprestimo);

    }

    //--- FIM MÉTODOS PARA PREENCHIMENTO DA TELA ------------------------------|
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
        jButtonIncluirExemplar = new javax.swing.JButton();
        jButtonIncluirLivro = new javax.swing.JButton();

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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableColaboradorMouseClicked(evt);
            }
        });
        jTableColaborador.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTableColaboradorKeyReleased(evt);
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
                .addComponent(jButtonIncluirColaborador)
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
                .addGap(18, 18, 18)
                .addComponent(jButtonIncluirColaborador)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelLivros.setBorder(javax.swing.BorderFactory.createTitledBorder("Livros"));

        jLabelPesquisarLivro.setText("Pesquisar");

        jTextFieldPesquisarLivro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesquisarLivroKeyReleased(evt);
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
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableLivroMouseClicked(evt);
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

        jButtonIncluirExemplar.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButtonIncluirExemplar.setText("Incluir Exemplar");
        jButtonIncluirExemplar.setEnabled(false);
        jButtonIncluirExemplar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIncluirExemplarActionPerformed(evt);
            }
        });

        jButtonIncluirLivro.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButtonIncluirLivro.setText("Incluir Livro");
        jButtonIncluirLivro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIncluirLivroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelLivrosLayout = new javax.swing.GroupLayout(jPanelLivros);
        jPanelLivros.setLayout(jPanelLivrosLayout);
        jPanelLivrosLayout.setHorizontalGroup(
            jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLivrosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPaneLivro, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanelLivrosLayout.createSequentialGroup()
                        .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelLivrosLayout.createSequentialGroup()
                                .addComponent(jTextFieldPesquisarLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonIncluirLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabelLivroDetalhe)
                            .addComponent(jLabelPesquisarLivro)
                            .addComponent(jScrollPaneLivroDetalhe, javax.swing.GroupLayout.PREFERRED_SIZE, 708, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelLivrosLayout.createSequentialGroup()
                                .addComponent(jLabelExemplaresLivro)
                                .addGap(18, 18, 18)
                                .addComponent(jButtonIncluirExemplar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPaneExemplares, javax.swing.GroupLayout.PREFERRED_SIZE, 708, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanelLivrosLayout.createSequentialGroup()
                                .addComponent(jButtonReservarLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButtonEmprestar, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelLivrosLayout.setVerticalGroup(
            jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLivrosLayout.createSequentialGroup()
                .addComponent(jLabelPesquisarLivro)
                .addGap(0, 0, 0)
                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPesquisarLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonIncluirLivro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelLivroDetalhe)
                .addGap(0, 0, 0)
                .addComponent(jScrollPaneLivroDetalhe, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelExemplaresLivro)
                    .addComponent(jButtonIncluirExemplar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneExemplares, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButtonReservarLivro)
                    .addComponent(jButtonEmprestar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    //
    //--- EVENTOS ------------------------------------------------------------->
    //
    //--- EVENTOS - JTABLE COLABORADOR ---------------------------------------->
    //
    private void jTableColaboradorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableColaboradorMouseClicked
        try {
            preencherJTableColaboradorDetalhe();
            ativarBotoes();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jTableColaboradorMouseClicked

    private void jTableColaboradorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableColaboradorKeyReleased
        jTableColaboradorMouseClicked(null);
    }//GEN-LAST:event_jTableColaboradorKeyReleased
    //
    //--- FIM EVENTOS - JTABLE COLABORADOR ------------------------------------|
    //
    //--- EVENTOS - JTABLE LIVRO ---------------------------------------------->
    //
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

    private void jTableLivroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableLivroKeyReleased
        jTableLivroMouseClicked(null);
    }//GEN-LAST:event_jTableLivroKeyReleased
    //
    //--- FIM EVENTOS - JTABLE LIVRO ------------------------------------------|
    //
    //--- EVENTOS - JTABLE EXEMPLAR ------------------------------------------->
    //
    private void jTableExemplaresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableExemplaresMouseClicked
        try {
            ativarBotoes();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jTableExemplaresMouseClicked

    private void jTableExemplaresKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTableExemplaresKeyReleased
        jTableExemplaresMouseClicked(null);
    }//GEN-LAST:event_jTableExemplaresKeyReleased
    //
    //--- FIM EVENTOS - JTABLE EXEMPLAR ---------------------------------------|
    //
    //--- EVENTOS - BOTÕES ---------------------------------------------------->
    //
    private void jButtonIncluirColaboradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIncluirColaboradorActionPerformed
        try {
            exibirTelaColaborador();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jButtonIncluirColaboradorActionPerformed

    private void jButtonIncluirExemplarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIncluirExemplarActionPerformed
        try {
            exibirTelaExemplar();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jButtonIncluirExemplarActionPerformed

    private void jButtonIncluirLivroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIncluirLivroActionPerformed
        try {
            exibirTelaLivro();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jButtonIncluirLivroActionPerformed

    private void jButtonReservarLivroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonReservarLivroActionPerformed
        try {
            salvarReserva();
        } catch (Exception e) {
            mensagem.alerta(e.getMessage());
        }
    }//GEN-LAST:event_jButtonReservarLivroActionPerformed
    //
    //--- FIM EVENTOS - BOTÕES ------------------------------------------------|
    //
    //--- EVENTOS - FILTRO E PESQUISA ----------------------------------------->
    //
    private void jComboBoxFiltrarColaboradorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxFiltrarColaboradorActionPerformed
        try {
            filtrarColaboradores(jComboBoxFiltrarColaborador.getSelectedIndex());
            ativarBotoes();
        } catch (Exception e) {
            mensagem.erro(new Exception("Erro ao filtrar colaboradores!\n" + e.getMessage()));
        }
    }//GEN-LAST:event_jComboBoxFiltrarColaboradorActionPerformed

    private void jTextFieldPesquisarColaboradorKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisarColaboradorKeyReleased
        try {
            pesquisarColaboradores(jTextFieldPesquisarColaborador.getText().toLowerCase());
            ativarBotoes();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jTextFieldPesquisarColaboradorKeyReleased

    private void jTextFieldPesquisarLivroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisarLivroKeyReleased
        try {
            pesquisarLivros(jTextFieldPesquisarLivro.getText().toLowerCase());
            ativarBotoes();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jTextFieldPesquisarLivroKeyReleased

    //
    //--- FIM EVENTOS - FILTRO E PESQUISA -------------------------------------|
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
    private javax.swing.JButton jButtonIncluirExemplar;
    private javax.swing.JButton jButtonIncluirLivro;
    private javax.swing.JButton jButtonReservarLivro;
    private javax.swing.JComboBox<String> jComboBoxFiltrarColaborador;
    private javax.swing.JLabel jLabelColaboradorDetalhe;
    private javax.swing.JLabel jLabelExemplaresLivro;
    private javax.swing.JLabel jLabelFiltrarColaborador;
    private javax.swing.JLabel jLabelLivroDetalhe;
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
    private javax.swing.JTextField jTextFieldPesquisarColaborador;
    private javax.swing.JTextField jTextFieldPesquisarLivro;
    // End of variables declaration//GEN-END:variables
}
