/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import classes.Colaborador;
import classes.Emprestimo;
import classes.Exemplar;
import classes.Livro;
import classes.Reserva;
import controle.ControleColaborador;
import enumeradores.EnumAcao;
import enumeradores.EnumCargo;
import enumeradores.EnumPerfil;
import enumeradores.EnumTipoStatus;
import enumeradores.EnumUF;
import interfaces.ICRUDColaborador;
import interfaces.ICRUDEmprestimo;
import interfaces.ICRUDExemplar;
import interfaces.ICRUDLivro;
import interfaces.ICRUDReserva;
import interfaces.ITelaCadastro;
import static utilidades.Email.isValidEmailAddressRegex;
import utilidades.Mensagens;
import static utilidades.StringUtil.soTemLetras;
import static utilidades.StringUtil.telefoneValido;

/**
 *
 * @author Vinicius
 */
public class TelaEmprestimo extends javax.swing.JDialog implements ITelaCadastro {

    private int id;
    private Mensagens mensagem = new Mensagens();
    private EnumAcao acao = null;
    private ICRUDColaborador controleColaborador = null;
    private Colaborador colaborador = null;
    private ICRUDLivro controleLivro = null;
    private Livro livro = null;
    private ICRUDExemplar controleExemplar = null;
    private Exemplar exemplar = null;
    private ICRUDEmprestimo controleEmprestimo = null;
    private Emprestimo emprestimo = null;
    private ICRUDReserva controleReserva = null;
    private Reserva reserva = null;
    private boolean visible = false;

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
            //controleReserva = new ControleColaborador();
            //emprestimo = controleReserva.buscarPeloId(id);
            //popularControles();

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

    private void popularControles() {
//        jComboBoxCargo.removeAllItems();
//        for (EnumCargo c : EnumCargo.values()) {
//            jComboBoxCargo.addItem(c.toString());
//        }
//
//        jComboBoxPerfil.removeAllItems();
//        for (EnumPerfil p : EnumPerfil.values()) {
//            jComboBoxPerfil.addItem(p.toString());
//        }
//
//        jComboBoxStatus.removeAllItems();
//        for (EnumTipoStatus t : EnumTipoStatus.values()) {
//            if (t.equals(EnumTipoStatus.ATIVO) || t.equals(EnumTipoStatus.INATIVO)) {
//                jComboBoxStatus.addItem(t.toString());
//            }
//        }
//
//        jComboBoxUF.removeAllItems();
//        for (EnumUF u : EnumUF.values()) {
//            jComboBoxUF.addItem(u.toString());
//        }

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

    private void validarPreenchimento() throws Exception {

//        if (jComboBoxCargo.getSelectedIndex() == -1) {
//            jComboBoxCargo.requestFocus();
//            throw new Exception("Selecione o cargo!");
//
//        }
//
//        String campo = new String(jFormattedTextFieldMatricula.getText().trim());
//        if (campo.length() == 0) {
//            jFormattedTextFieldMatricula.requestFocus();
//            throw new Exception("Informe o número da matrícula!");
//        }
//
//        if (Integer.parseInt(campo) == 0) {
//            jFormattedTextFieldMatricula.requestFocus();
//            throw new Exception("O número da matrícula deve ser maior que 0 (zero)");
//        }
//
//        campo = new String(jTextFieldNome.getText().trim());
//        if (campo.length() == 0) {
//            jTextFieldNome.requestFocus();
//            throw new Exception("Informe o nome do colaborador!");
//        }
//
//        if (campo.length() < 2) {
//            jTextFieldNome.requestFocus();
//            jTextFieldNome.selectAll();
//            throw new Exception("O nome do colaborador precisa ter pelo menos duas letras!");
//        }
//
//        if (!soTemLetras(campo)) {
//            jTextFieldNome.requestFocus();
//            jTextFieldNome.selectAll();
//            throw new Exception("O nome do colaborador deve ter apenas letras e espaços!");
//        }
//
//        campo = new String(jTextFieldOAB.getText().trim());
//        if (campo.length() == 0) {  // Se o campo estiver vazio
//            if (jComboBoxCargo.getSelectedItem().equals(EnumCargo.ADVOGADO.toString())) { // e for advogado
//                throw new Exception("Informe o número da OAB");
//            } else {
//                if (jComboBoxUF.getSelectedIndex() != -1) { // ou selecionou a UF
//                    if (mensagem.pergunta("Deseja informar o número da OAB ?") == 0) {
//                        jTextFieldOAB.requestFocus();
//                    } else {
//                        jComboBoxUF.setSelectedIndex(-1);
//                    }
//                }
//            }
//        } else { // Se o campo não está vazio
//
//            if (Integer.parseInt(jTextFieldOAB.getText()) == 0) {
//                jTextFieldOAB.requestFocus();
//                jTextFieldOAB.selectAll();
//                throw new Exception("O campo nº OAB precisa ser diferente de 0 (zero)");
//            }
//
//            if (jComboBoxUF.getSelectedIndex() == -1) { // e não selecionou UF
//                jTextFieldOAB.requestFocus();
//                jTextFieldOAB.selectAll();
//                throw new Exception("Selecione a UF da OAB!");
//            }
//        }
//
//        campo = new String(jTextFieldEmail.getText().trim());
//        if (campo.trim().length() > 0 && !isValidEmailAddressRegex(campo)) {
//            jTextFieldEmail.requestFocus();
//            jTextFieldEmail.selectAll();
//            throw new Exception("Informe um e-mail válido!");
//        }
//
//        campo = new String(jFormattedTextFieldTelefone.getText().trim());
//        if (campo.length() > 0 && !telefoneValido(campo)) {
//            jFormattedTextFieldTelefone.requestFocus();
//            jFormattedTextFieldTelefone.selectAll();
//            throw new Exception("Informe um número de telefone válido!\n\n"
//                    + "Formato válidos:\n"
//                    + "9999-8888\n"
//                    + "9 8888-7777\n"
//                    + "(99) 8888-7777\n"
//                    + "(99) 8 7777-6666");
//        }
//
//        if (jComboBoxPerfil.getSelectedIndex() == -1) {
//            jComboBoxPerfil.requestFocus();
//            throw new Exception("Selecione o perfil!");
//        }
//
//        if (jComboBoxStatus.getSelectedIndex() == -1) {
//            throw new Exception("Selecione o status do colaborador!");
//        }
    }

    private void salvar() throws Exception {
//        validarPreenchimento();
//
//        String senha = (acao.equals(EnumAcao.Editar)) ? emprestimo.getSenha() : "";
//
//        emprestimo = new Colaborador();
//
//        if (acao.equals(EnumAcao.Incluir)) {
//            emprestimo.setIdColaborador(id);
//        } else if (acao.equals(EnumAcao.Editar)) {
//            emprestimo.setIdColaborador(id);
//        }
//
//        emprestimo.setNomeColaborador(jTextFieldNome.getText());
//        emprestimo.setPerfil(EnumPerfil.valueOf(jComboBoxPerfil.getSelectedItem().toString()));
//        emprestimo.setMatricula(Integer.parseInt(jFormattedTextFieldMatricula.getText()));
//        emprestimo.setCargo(EnumCargo.valueOf(jComboBoxCargo.getSelectedItem().toString()));
//        emprestimo.setOab((jTextFieldOAB.getText().trim().length() == 0) ? ""
//                : String.format("%s-%s", jTextFieldOAB.getText(), jComboBoxUF.getSelectedItem().toString()));
//        emprestimo.setSenha(senha);
//        emprestimo.setEmail(jTextFieldEmail.getText());
//        emprestimo.setTelefone(jFormattedTextFieldTelefone.getText());
//        emprestimo.setStatus(EnumTipoStatus.valueOf(jComboBoxStatus.getSelectedItem().toString()));
//
//        if (acao.equals(EnumAcao.Incluir)) {
//            controleReserva.incluir(emprestimo);
//            mensagem.sucesso("Colaborador incluído com sucesso!");
//        } else if (acao.equals(EnumAcao.Editar)) {
//            controleReserva.alterar(emprestimo);
//            mensagem.sucesso("Colaborador atualizado com sucesso!");
//        }
//        visible = false;
//        this.dispose();
    }

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
        jButtonCancelar = new javax.swing.JButton();
        jButtonEmprestar = new javax.swing.JButton();
        jButtonDevolver = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        setSize(new java.awt.Dimension(1366, 768));

        jPanelColaboradores.setBorder(javax.swing.BorderFactory.createTitledBorder("Colaboradores"));

        jLabelPesquisarColaborador.setText("Pesquisar");

        jLabelFiltrarColaborador.setText("Filtrar por");

        jComboBoxFiltrarColaborador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Ativos", "Inativos", "Advogado", "Estagiário", "Secretária", "Adimplente", "Inadimplente", " " }));

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
                {"Nome", null},
                {"Perfil", null},
                {"Matrícula", null},
                {"Cargo", null},
                {"OAB", null},
                {"Email", null},
                {"Telefone", null},
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
                    .addComponent(jScrollPaneColaborador, javax.swing.GroupLayout.DEFAULT_SIZE, 364, Short.MAX_VALUE)
                    .addGroup(jPanelColaboradoresLayout.createSequentialGroup()
                        .addGroup(jPanelColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldPesquisarColaborador, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelPesquisarColaborador))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelFiltrarColaborador)
                            .addComponent(jComboBoxFiltrarColaborador, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabelStatus)
                    .addComponent(jLabelColaboradorDetalhe)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelColaboradoresLayout.setVerticalGroup(
            jPanelColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelColaboradoresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPesquisarColaborador)
                    .addComponent(jLabelFiltrarColaborador))
                .addGap(0, 0, 0)
                .addGroup(jPanelColaboradoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldPesquisarColaborador, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxFiltrarColaborador, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneColaborador, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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

        jComboBoxFiltrarLivro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Ativos", "Inativos", "Emprestados", "Reservados" }));

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
                    .addGroup(jPanelLivrosLayout.createSequentialGroup()
                        .addComponent(jScrollPaneLivroDetalhe, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPaneExemplares, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(jPanelLivrosLayout.createSequentialGroup()
                                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabelExemplaresLivro)
                                    .addComponent(jLabelMotivoDesativacao))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jTextFieldMotivoDesativacao)))
                    .addComponent(jScrollPaneLivroDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanelLivrosLayout.createSequentialGroup()
                        .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldPesquisarLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelPesquisarLivro))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextFieldPesquisarCDD, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelPesquisarCDD))
                        .addGap(18, 18, 18)
                        .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBoxFiltrarLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelFliltrarLivro)))
                    .addComponent(jScrollPaneLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 640, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelLivroDetalhe))
                .addContainerGap())
        );
        jPanelLivrosLayout.setVerticalGroup(
            jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLivrosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPesquisarLivro)
                    .addGroup(jPanelLivrosLayout.createSequentialGroup()
                        .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelPesquisarCDD)
                            .addComponent(jLabelFliltrarLivro))
                        .addGap(0, 0, 0)
                        .addGroup(jPanelLivrosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldPesquisarCDD, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxFiltrarLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldPesquisarLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPaneLivro, javax.swing.GroupLayout.PREFERRED_SIZE, 242, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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

        jButtonCancelar.setText("Cancelar");

        jButtonEmprestar.setText("Emprestar");

        jButtonDevolver.setText("Devolver");

        javax.swing.GroupLayout jPanelComandosLayout = new javax.swing.GroupLayout(jPanelComandos);
        jPanelComandos.setLayout(jPanelComandosLayout);
        jPanelComandosLayout.setHorizontalGroup(
            jPanelComandosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelComandosLayout.createSequentialGroup()
                .addGroup(jPanelComandosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButtonDevolver, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonEmprestar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButtonReservar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                    .addComponent(jButtonCancelar, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );
        jPanelComandosLayout.setVerticalGroup(
            jPanelComandosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelComandosLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(jButtonReservar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonCancelar)
                .addGap(271, 271, 271)
                .addComponent(jButtonEmprestar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonDevolver)
                .addGap(103, 103, 103))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelComandos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelLivros, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelColaboradores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanelComandos, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(TelaEmprestimo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaEmprestimo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaEmprestimo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaEmprestimo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
