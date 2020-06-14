/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import java.util.ArrayList;
import enumeradores.EnumPerfil;
import controle.ControleTelaPrincipal;
import classes.ColunaGrid;
import controle.ControleAreaConhecimento;
import controle.ControleAutor;
import controle.ControleColaborador;
import controle.ControleEditora;
import controle.ControleEmprestimo;
import controle.ControleExemplar;
import controle.ControleLivro;
import controle.ControleLog;
import controle.ControleReserva;
import enumeradores.EnumAcao;
import enumeradores.EnumCadastro;
import interfaces.ICRUDAreaConhecimento;
import interfaces.ICRUDAutor;
import interfaces.ICRUDEditora;
import interfaces.ICRUDColaborador;
import interfaces.ICRUDEmprestimo;
import interfaces.ICRUDExemplar;
import interfaces.ICRUDLivro;
import interfaces.ICRUDLog;
import interfaces.ICRUDReserva;
import interfaces.ITelaCadastro;
import utilidades.Mensagens;

/**
 *
 * @author vovostudio
 */
public class TelaPrincipal extends javax.swing.JFrame {

    //--- ATRIBUTOS ----------------------------------------------------------->
    //    
    // Controladores
    private ControleTelaPrincipal controleTela = null;
    private ICRUDAreaConhecimento controleAreaConhecimento = null;
    private ICRUDAutor controleAutor = null;
    private ICRUDColaborador controleColaborador = null;
    private ICRUDEditora controleEditora = null;
    private ICRUDEmprestimo controleEmprestimo = null;
    private ICRUDExemplar controleExemplar = null;
    private ICRUDLivro controleLivro = null;
    private ICRUDReserva controleReserva = null;
    private ICRUDLog controleLog = null;
    private ITelaCadastro telaCadastro = null;
    private Mensagens mensagem = new Mensagens();
    private EnumAcao acao = null;

    // Nome do cadastro atual
    private String cadastro = "";

    // Linhas do grid
    private String[][] linhas = null;

    // Colunas do grid
    private ArrayList<ColunaGrid> colunas = null;

    // Larguras das colunas do grid
    private int[] larguras = null;

    // Objeto genérico para armazenar as coleções de objetos
    private Object colecao = null;

    //--- FIM ATRIBUTOS -------------------------------------------------------|
    //
    //--- MÉTODOS ------------------------------------------------------------->
    //
    //--- MÉTODOS PARA CRUD --------------------------------------------------->
    //
    private void incluirCadastro() throws Exception {
        try {
            if (cadastro.equals("")) {
                mensagem.informacao("Selecione um cadastro!");
                return;
            }
            if (cadastro.equals(EnumCadastro.EMPRESTIMO.toString())) {
                acao = EnumAcao.Incluir_Emprestimo;
            } else if (cadastro.equals(EnumCadastro.RESERVA.toString())) {
                acao = EnumAcao.Incluir_Reserva;
            } else {
                acao = EnumAcao.Incluir;
            }

            telaCadastro.setAcao(acao);
            telaCadastro.setId(0);
            telaCadastro.setVisible(true);

            exibirCadastros();
        } catch (Exception e) {
            throw new Exception("Erro ao incluir o cadastro!\n" + e.getMessage());
        }
    }

    private void editarCadastro() throws Exception {
        try {

            if (cadastro.equals(EnumCadastro.EMPRESTIMO.toString())) {
                acao = EnumAcao.Editar_Emprestimo;
            } else if (cadastro.equals(EnumCadastro.RESERVA.toString())) {
                acao = EnumAcao.Editar_Reserva;
            } else {
                acao = EnumAcao.Editar;
            }

            telaCadastro.setId(getId());
            telaCadastro.setAcao(acao);
            telaCadastro.setVisible(true);

            exibirCadastros();
        } catch (Exception e) {
            throw new Exception("Erro ao editar o cadastro!\n" + e.getMessage());
        }
    }

    private void detalheCadastro() throws Exception {
        editarCadastro();
    }

    private void excluirCadastro() throws Exception {
        try {
            int id = getId();
            String textoPergunta = "Deseja realmente excluir o cadastro selecionado?\n"
                    + "(-) " + cadastro + " ID: " + id;

            if (mensagem.pergunta(textoPergunta) == 0) {

                switch (cadastro.toString()) {
                    case "AREACONHECIMENTO":
                        controleAreaConhecimento.excluir(id);
                        break;

                    case "AUTOR":
                        controleAutor.excluir(id);
                        break;

                    case "COLABORADOR":
                        controleColaborador.excluir(id);
                        break;

                    case "CONFIGURACAO":
                        break;

                    case "EDITORA":
                        controleEditora.excluir(id);
                        break;

                    case "EMPRESTIMO":
                        controleEmprestimo.excluir(id);
                        break;

                    case "EXEMPLAR":
                        controleExemplar.excluir(id);
                        break;

                    case "LIVRO":
                        controleLivro.excluir(id);
                        break;

                    case "RESERVA":
                        controleReserva.excluir(id);
                        break;
                }

                mensagem.sucesso("Cadastro de " + cadastro + " excluído com sucesso!");
            }
            exibirCadastros();
        } catch (Exception e) {
            throw new Exception("Erro ao excluir o cadastro!\n" + e.getMessage());
        }
    }

    //--- FIM MÉTODOS PARA CRUD -----------------------------------------------|
    //
    //--- MÉTODOS PARA GRID --------------------------------------------------->
    //
    // Exibe os cadastros de uma tabela
    private void exibirCadastros() throws Exception {

        try {
            cadastro = buttonGroupCadastros.getSelection().getActionCommand().toUpperCase();
            atualizaColecao();
            colunas = controleTela.getColunasParaGrid(EnumCadastro.valueOf(cadastro));
            if (colecao == null) {
                linhas = new String[][]{{}};
            } else {
                linhas = controleTela.getLinhasParaGrid(colecao, EnumCadastro.valueOf(cadastro));
            }
            popularGrid(colunas, linhas);

            // Limpa o texto de pesquisa e posiciona o cursor
            jTextFieldPesquisar.setText("");
            jTextFieldPesquisar.requestFocus();
        } catch (Exception e) {
            throw new Exception("Erro ao exibir cadastros de " + cadastro + "!\n" + e.getMessage());
        }
    }

    // Popula o grid
    private void popularGrid(ArrayList<ColunaGrid> colunas, String[][] linhas) throws Exception {
        try {
            String nomeCadastro = EnumCadastro.valueOf(cadastro).getNomeCadastro();

            this.setTitle("Biblioteca do G0dô - Cadastro de " + nomeCadastro);

            if (linhas.length == 0 || linhas[0].length == 0) {
                jLabelStatusTop.setText("Nenhum cadastro de " + nomeCadastro + ".");
            } else {
                jLabelStatusTop.setText("");
            }

            // Nomes das colunas do grid
            String[] colunasNomes = new String[colunas.size()];
            for (int i = 0; i < colunas.size(); i++) {
                colunasNomes[i] = colunas.get(i).getNome();
            }

            // Bloqueia edição pelo grid de todas as colunas
            boolean[] permiteEdicao = new boolean[colunas.size()];
            for (boolean p : permiteEdicao) {
                p = false;
            }

            // Preenche e configura o grid com as linhas e colunas
            jTableLista.setModel(new javax.swing.table.DefaultTableModel(linhas, colunasNomes) {
                boolean[] canEdit = permiteEdicao;

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit[columnIndex];
                }
            });
            jTableLista.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
            jScrollPane1.setViewportView(jTableLista);

            larguras = controleTela.getLarguraDasColunasParaGrid(EnumCadastro.valueOf(cadastro));

            if (jTableLista.getColumnModel().getColumnCount() > 0) {
                // Formatação das colunas
                for (int i = 0; i < colunas.size() - 1; i++) {
                    jTableLista.getColumnModel().getColumn(i).setCellRenderer(colunas.get(i).getAlinhamento());
                    jTableLista.getColumnModel().getColumn(i).setMinWidth(larguras[i]);
                    jTableLista.getColumnModel().getColumn(i).setPreferredWidth(larguras[i]);
                    jTableLista.getColumnModel().getColumn(i).setMaxWidth(larguras[i]);
                }
                // Alinhamento da última coluna do grid
                jTableLista.getColumnModel().getColumn(
                        jTableLista.getColumnModel().getColumnCount() - 1).setCellRenderer(
                        colunas.get(jTableLista.getColumnModel().getColumnCount() - 1).getAlinhamento());
            }

            jButtonIncluir.setEnabled(true);
            
            // Desabilita botões Editar e Excluir
            jButtonEditar.setEnabled(false);
            jButtonExcluir.setEnabled(false);
            
            jTextFieldPesquisar.setEnabled(true);
            jTextFieldPesquisar.requestFocus();
        } catch (Exception e) {
            throw new Exception("Erro ao popular o grid de " + cadastro + "!\n" + e.getMessage());
        }
    }

    // Pesquisa nas linhas do grid
    private void pesquisar(String texto) throws Exception {
        try {
            if (linhas != null) {
                if (texto.trim().length() == 0) {
                    popularGrid(colunas, linhas);
                } else {
                    ArrayList<String[]> resultadoPesquisa = new ArrayList<>();
                    for (String[] linha : linhas) {
                        for (String coluna : linha) {
                            if (coluna.toLowerCase().contains(texto)) {
                                resultadoPesquisa.add(linha);
                                break; // Pára de pesquisar na linha atual
                            }
                        }
                    }

                    String[][] resultadoLinhas = new String[resultadoPesquisa.size()][colunas.size()];
                    for (int i = 0; i < resultadoPesquisa.size(); i++) {
                        resultadoLinhas[i] = resultadoPesquisa.get(i);
                    }

                    popularGrid(colunas, resultadoLinhas);
                }
            }
        } catch (Exception e) {
            throw new Exception("Erro ao pesquisar " + cadastro + "!\n" + e.getMessage());
        }
    }

    // Retorna o id do cadastro selecionado no grid
    private int getId() throws Exception {
        try {
            // Índice da coluna ID
            int indiceID = 0;
            
            // Atualiza o índice da coluna com o ID
            for (int i = 0; i < jTableLista.getColumnCount(); i++) {
                if (jTableLista.getColumnName(i).equals("ID")) {
                    indiceID = i;
                    break;
                }
            }
            return Integer.parseInt(jTableLista.getValueAt(jTableLista.getSelectedRow(), indiceID).toString());

        } catch (Exception e) {
            throw new Exception("Erro ao obter ID!\n" + e.getMessage());
        }
    }

    // Atualiza a coleção de objetos para exibição
    private void atualizaColecao() throws Exception {
        try {
            this.colecao = null;

            switch (cadastro.toString()) {
                case "AREACONHECIMENTO":
                    colecao = controleAreaConhecimento.listar();
                    break;

                case "AUTOR":
                    colecao = controleAutor.listar();
                    break;

                case "COLABORADOR":
                    colecao = controleColaborador.listar();
                    break;

                case "CONFIGURACAO":
                    break;

                case "EDITORA":
                    colecao = controleEditora.listar();
                    break;

                case "EMPRESTIMO":
                    colecao = controleEmprestimo.listar();
                    break;

                case "EXEMPLAR":
                    break;

                case "LIVRO":
                    colecao = controleLivro.listar();
                    break;

                case "RESERVA":
                    colecao = controleReserva.listar();
                    break;

                case "LOG":
                    colecao = controleLog.listar();
                    break;

            }
        } catch (Exception e) {
            throw new Exception("Erro ao atualizar a coleção!\n" + e.getMessage());
        }
    }

    //--- FIM MÉTODOS PARA GRID -----------------------------------------------|
    //
    //--- FIM MÉTODOS ---------------------------------------------------------|
    //
    //--- CONSTRUTOR ---------------------------------------------------------->
    //
    /**
     * Creates new form JFramePrincipal
     */
    public TelaPrincipal() {

        initComponents();

        // Centraliza o form na tela
        this.setLocationRelativeTo(rootPane);

        // Oculta controles restritos a administradores
        boolean visible = Vai.USUARIO.getPerfil().equals(EnumPerfil.ADMINISTRADOR);
        jButtonConfiguracoes.setVisible(visible);
        jRadioButtonLog.setVisible(visible);

        try {
            // Controladores
            controleTela = new ControleTelaPrincipal();
            controleAreaConhecimento = new ControleAreaConhecimento();
            controleAutor = new ControleAutor();
            controleColaborador = new ControleColaborador();
            controleEditora = new ControleEditora();
            controleEmprestimo = new ControleEmprestimo();
            controleExemplar = new ControleExemplar();
            controleLivro = new ControleLivro();
            controleReserva = new ControleReserva();
            controleLog = new ControleLog();

            // aqui
            jLabelStatusTop.setText("");
            jLabelStatusBottom.setText(String.format("USUÁRIO: - %d", Vai.USUARIO.getIdColaborador(), Vai.USUARIO.getNomeColaborador()));
        } catch (Exception e) {
            mensagem.erro(new Exception("Erro ao construir a tela principal!\n" + e.getMessage()));
        }
    }
    //    
    //--- FIM CONSTRUTOR ------------------------------------------------------|

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroupCadastros = new javax.swing.ButtonGroup();
        jPanelCadastros = new javax.swing.JPanel();
        jRadioButtonLivros = new javax.swing.JRadioButton();
        jRadioButtonEmprestimos = new javax.swing.JRadioButton();
        jRadioButtonReservas = new javax.swing.JRadioButton();
        jRadioButtonAreas = new javax.swing.JRadioButton();
        jRadioButtonEditoras = new javax.swing.JRadioButton();
        jRadioButtonAutores = new javax.swing.JRadioButton();
        jRadioButtonColaboradores = new javax.swing.JRadioButton();
        jButtonSair = new javax.swing.JButton();
        jButtonConfiguracoes = new javax.swing.JButton();
        jRadioButtonLog = new javax.swing.JRadioButton();
        jSeparatorCadastrosBotoes = new javax.swing.JSeparator();
        jPanelBotoes = new javax.swing.JPanel();
        jButtonIncluir = new javax.swing.JButton();
        jButtonEditar = new javax.swing.JButton();
        jButtonExcluir = new javax.swing.JButton();
        jLabelStatusTop = new javax.swing.JLabel();
        jLabelPesquisar = new javax.swing.JLabel();
        jTextFieldPesquisar = new javax.swing.JTextField();
        jButtonComprovante = new javax.swing.JButton();
        jButtonDevolver = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableLista = new javax.swing.JTable();
        jLabelStatusBottom = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Biblioteca Godofredo");
        setResizable(false);
        setSize(new java.awt.Dimension(1366, 768));

        buttonGroupCadastros.add(jRadioButtonLivros);
        jRadioButtonLivros.setText("Livros");
        jRadioButtonLivros.setActionCommand("Livro");
        jRadioButtonLivros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonLivrosActionPerformed(evt);
            }
        });

        buttonGroupCadastros.add(jRadioButtonEmprestimos);
        jRadioButtonEmprestimos.setText("Empréstimos");
        jRadioButtonEmprestimos.setActionCommand("Emprestimo");
        jRadioButtonEmprestimos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonEmprestimosActionPerformed(evt);
            }
        });

        buttonGroupCadastros.add(jRadioButtonReservas);
        jRadioButtonReservas.setText("Reservas");
        jRadioButtonReservas.setActionCommand("Reserva");
        jRadioButtonReservas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonReservasActionPerformed(evt);
            }
        });

        buttonGroupCadastros.add(jRadioButtonAreas);
        jRadioButtonAreas.setText("Áreas de conhecimento");
        jRadioButtonAreas.setActionCommand("AreaConhecimento");
        jRadioButtonAreas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonAreasActionPerformed(evt);
            }
        });

        buttonGroupCadastros.add(jRadioButtonEditoras);
        jRadioButtonEditoras.setText("Editoras");
        jRadioButtonEditoras.setActionCommand("Editora");
        jRadioButtonEditoras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonEditorasActionPerformed(evt);
            }
        });

        buttonGroupCadastros.add(jRadioButtonAutores);
        jRadioButtonAutores.setText("Autores");
        jRadioButtonAutores.setActionCommand("Autor");
        jRadioButtonAutores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonAutoresActionPerformed(evt);
            }
        });

        buttonGroupCadastros.add(jRadioButtonColaboradores);
        jRadioButtonColaboradores.setText("Colaboradores");
        jRadioButtonColaboradores.setActionCommand("Colaborador");
        jRadioButtonColaboradores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonColaboradoresActionPerformed(evt);
            }
        });

        jButtonSair.setText("Sair");
        jButtonSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSairActionPerformed(evt);
            }
        });

        jButtonConfiguracoes.setText("Configurações");
        jButtonConfiguracoes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConfiguracoesActionPerformed(evt);
            }
        });

        buttonGroupCadastros.add(jRadioButtonLog);
        jRadioButtonLog.setText("Logs");
        jRadioButtonLog.setActionCommand("Log");
        jRadioButtonLog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioButtonLogActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelCadastrosLayout = new javax.swing.GroupLayout(jPanelCadastros);
        jPanelCadastros.setLayout(jPanelCadastrosLayout);
        jPanelCadastrosLayout.setHorizontalGroup(
            jPanelCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelCadastrosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jRadioButtonLivros)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButtonEmprestimos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButtonReservas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButtonAreas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButtonEditoras)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButtonAutores)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButtonColaboradores)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButtonLog)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonConfiguracoes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSair)
                .addGap(0, 0, 0))
        );
        jPanelCadastrosLayout.setVerticalGroup(
            jPanelCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelCadastrosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelCadastrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioButtonLivros)
                    .addComponent(jRadioButtonAreas)
                    .addComponent(jRadioButtonEditoras)
                    .addComponent(jRadioButtonAutores)
                    .addComponent(jRadioButtonColaboradores)
                    .addComponent(jButtonConfiguracoes)
                    .addComponent(jButtonSair)
                    .addComponent(jRadioButtonEmprestimos)
                    .addComponent(jRadioButtonReservas)
                    .addComponent(jRadioButtonLog))
                .addGap(0, 0, 0))
        );

        jSeparatorCadastrosBotoes.setMinimumSize(new java.awt.Dimension(50, 5));
        jSeparatorCadastrosBotoes.setPreferredSize(new java.awt.Dimension(50, 5));

        jButtonIncluir.setText("Incluir");
        jButtonIncluir.setEnabled(false);
        jButtonIncluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonIncluirActionPerformed(evt);
            }
        });

        jButtonEditar.setText("Editar");
        jButtonEditar.setEnabled(false);
        jButtonEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonEditarActionPerformed(evt);
            }
        });

        jButtonExcluir.setText("Excluir");
        jButtonExcluir.setEnabled(false);
        jButtonExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonExcluirActionPerformed(evt);
            }
        });

        jLabelStatusTop.setText("jLabelStatusTop");

        jLabelPesquisar.setText("Pesquisar");

        jTextFieldPesquisar.setEnabled(false);
        jTextFieldPesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldPesquisarKeyReleased(evt);
            }
        });

        jButtonComprovante.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButtonComprovante.setText("Comprovante");
        jButtonComprovante.setEnabled(false);

        jButtonDevolver.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N
        jButtonDevolver.setText("Devolver");
        jButtonDevolver.setEnabled(false);

        javax.swing.GroupLayout jPanelBotoesLayout = new javax.swing.GroupLayout(jPanelBotoes);
        jPanelBotoes.setLayout(jPanelBotoesLayout);
        jPanelBotoesLayout.setHorizontalGroup(
            jPanelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBotoesLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jButtonIncluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonExcluir)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonComprovante)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonDevolver)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelStatusTop)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPesquisar))
                .addGap(0, 0, 0))
        );
        jPanelBotoesLayout.setVerticalGroup(
            jPanelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelBotoesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonIncluir)
                        .addComponent(jButtonEditar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBotoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButtonExcluir)
                        .addComponent(jLabelStatusTop)
                        .addComponent(jButtonComprovante)
                        .addComponent(jButtonDevolver))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelBotoesLayout.createSequentialGroup()
                .addComponent(jLabelPesquisar)
                .addGap(0, 4, Short.MAX_VALUE)
                .addComponent(jTextFieldPesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTableLista.setAutoCreateRowSorter(true);
        jTableLista.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTableLista.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTableLista.setRowHeight(18);
        jTableLista.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTableLista.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTableListaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTableLista);

        jLabelStatusBottom.setText("jLabelStatusBottom");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jSeparatorCadastrosBotoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanelCadastros, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelBotoes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(20, 20, 20))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelStatusBottom)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanelCadastros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparatorCadastrosBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanelBotoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 566, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabelStatusBottom)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRadioButtonEmprestimosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonEmprestimosActionPerformed
        try {
            telaCadastro = null;
            exibirCadastros();
            telaCadastro = new TelaEmprestimo(this, true);
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jRadioButtonEmprestimosActionPerformed

    private void jRadioButtonAreasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonAreasActionPerformed
        try {
            telaCadastro = null;
            exibirCadastros();
            telaCadastro = new TelaAreaConhecimento(this, true);
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jRadioButtonAreasActionPerformed

    private void jRadioButtonEditorasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonEditorasActionPerformed
        try {
            telaCadastro = null;
            exibirCadastros();
            telaCadastro = new TelaEditora(this, true);
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jRadioButtonEditorasActionPerformed

    private void jRadioButtonAutoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonAutoresActionPerformed
        try {
            telaCadastro = null;
            exibirCadastros();
            telaCadastro = new TelaAutor(this, true);
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jRadioButtonAutoresActionPerformed

    private void jRadioButtonColaboradoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonColaboradoresActionPerformed
        try {
            telaCadastro = null;
            exibirCadastros();
            telaCadastro = new TelaColaborador(this, true);
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jRadioButtonColaboradoresActionPerformed

    private void jButtonConfiguracoesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConfiguracoesActionPerformed
        try {
            new TelaConfiguracao(this, true).setVisible(true);
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jButtonConfiguracoesActionPerformed

    private void jRadioButtonLogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonLogActionPerformed
        try {
            telaCadastro = null;
            exibirCadastros();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jRadioButtonLogActionPerformed

    private void jButtonSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSairActionPerformed
        try {
            if (mensagem.pergunta("Deseja realmente sair?") == 0) {
                System.exit(0);
            }
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jButtonSairActionPerformed

    private void jButtonIncluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonIncluirActionPerformed
        try {
            incluirCadastro();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jButtonIncluirActionPerformed

    private void jButtonEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonEditarActionPerformed
        try {
            editarCadastro();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jButtonEditarActionPerformed

    private void jButtonExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonExcluirActionPerformed
        try {
            excluirCadastro();
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jButtonExcluirActionPerformed

    private void jTableListaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTableListaMouseClicked
        try {
            boolean enabled = (jTableLista.getRowCount() > 0);

            jButtonEditar.setEnabled(enabled);

            // Verifica se está na tela de cadastro de Colaboradores
            if (cadastro.equals(EnumCadastro.COLABORADOR.toString())) {
                enabled = (Vai.USUARIO.getPerfil().equals(EnumPerfil.ADMINISTRADOR)
                        && Vai.USUARIO.getIdColaborador() != getId());
            }

            jButtonExcluir.setEnabled(enabled);

            // Clique duplo, mostra detalhes do cadastro
            if (evt.getClickCount() == 2) {
                detalheCadastro();
            }
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jTableListaMouseClicked

    private void jTextFieldPesquisarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPesquisarKeyReleased
        try {
            pesquisar(jTextFieldPesquisar.getText().toLowerCase());
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jTextFieldPesquisarKeyReleased

    private void jRadioButtonReservasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonReservasActionPerformed
        try {
            telaCadastro = null;
            exibirCadastros();
            telaCadastro = new TelaEmprestimo(this, true);
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jRadioButtonReservasActionPerformed

    private void jRadioButtonLivrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioButtonLivrosActionPerformed
        try {
            telaCadastro = null;
            exibirCadastros();
            telaCadastro = new TelaLivro(this, true);
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jRadioButtonLivrosActionPerformed

    //--- FIM EVENTOS ---------------------------------------------------------|
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
            java.util.logging.Logger.getLogger(TelaPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroupCadastros;
    private javax.swing.JButton jButtonComprovante;
    private javax.swing.JButton jButtonConfiguracoes;
    private javax.swing.JButton jButtonDevolver;
    private javax.swing.JButton jButtonEditar;
    private javax.swing.JButton jButtonExcluir;
    private javax.swing.JButton jButtonIncluir;
    private javax.swing.JButton jButtonSair;
    private javax.swing.JLabel jLabelPesquisar;
    private javax.swing.JLabel jLabelStatusBottom;
    private javax.swing.JLabel jLabelStatusTop;
    private javax.swing.JPanel jPanelBotoes;
    private javax.swing.JPanel jPanelCadastros;
    private javax.swing.JRadioButton jRadioButtonAreas;
    private javax.swing.JRadioButton jRadioButtonAutores;
    private javax.swing.JRadioButton jRadioButtonColaboradores;
    private javax.swing.JRadioButton jRadioButtonEditoras;
    private javax.swing.JRadioButton jRadioButtonEmprestimos;
    private javax.swing.JRadioButton jRadioButtonLivros;
    private javax.swing.JRadioButton jRadioButtonLog;
    private javax.swing.JRadioButton jRadioButtonReservas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparatorCadastrosBotoes;
    public javax.swing.JTable jTableLista;
    private javax.swing.JTextField jTextFieldPesquisar;
    // End of variables declaration//GEN-END:variables
}
