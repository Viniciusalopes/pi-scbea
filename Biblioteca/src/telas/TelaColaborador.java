/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import classes.Colaborador;
import controle.ControleColaborador;
import enumeradores.EnumAcao;
import enumeradores.EnumCaracteres;
import enumeradores.EnumCargo;
import enumeradores.EnumPerfil;
import enumeradores.EnumTipoStatus;
import enumeradores.EnumUF;
import interfaces.ICRUDColaborador;
import interfaces.ITelaCadastro;
import static utilidades.Email.isValidEmailAddressRegex;
import utilidades.Mensagens;
import static utilidades.StringUtil.*;

/**
 *
 * @author Vinicius
 */
public class TelaColaborador extends javax.swing.JDialog implements ITelaCadastro {

    private int id;
    private Mensagens mensagem = new Mensagens();
    private EnumAcao acao = null;
    private ICRUDColaborador controleColaborador = null;
    private Colaborador colaborador = null;
    private boolean visible = false;

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public void setAcao(EnumAcao acao) {
        this.acao = acao;
        this.setTitle(acao.toString() + " cadastro de colaborador");
    }

    @Override
    public void setVisible(boolean b) {
        try {
            controleColaborador = new ControleColaborador();
            colaborador = controleColaborador.buscarPeloId(id);
            popularControles();

            if (acao.equals(EnumAcao.Incluir)) {
                limparCampos();
            } else if (acao.equals(EnumAcao.Editar)) {
                preencherCampos();
            }
        } catch (Exception e) {
            mensagem.erro(e);
        }
        visible = true;
        super.setVisible(b);
    }

    private void popularControles() {
        jComboBoxCargo.removeAllItems();
        for (EnumCargo c : EnumCargo.values()) {
            jComboBoxCargo.addItem(c.toString());
        }

        jComboBoxPerfil.removeAllItems();
        for (EnumPerfil p : EnumPerfil.values()) {
            jComboBoxPerfil.addItem(p.toString());
        }

        jComboBoxStatus.removeAllItems();
        for (EnumTipoStatus t : EnumTipoStatus.values()) {
            if (t.equals(EnumTipoStatus.ATIVO) || t.equals(EnumTipoStatus.INATIVO)) {
                jComboBoxStatus.addItem(t.toString());
            }
        }

        jComboBoxUF.removeAllItems();
        for (EnumUF u : EnumUF.values()) {
            jComboBoxUF.addItem(u.toString());
        }

    }

    private void limparCampos() {
        jTextFieldID.setText("");
        jComboBoxCargo.setSelectedIndex(-1);
        jFormattedTextFieldMatricula.setText("");
        jTextFieldNome.setText("");
        jTextFieldOAB.setText("");
        jComboBoxUF.setSelectedIndex(-1);
        jTextFieldEmail.setText("");
        jFormattedTextFieldTelefone.setText("");
        jComboBoxPerfil.setSelectedIndex(-1);
        jComboBoxStatus.setSelectedIndex(-1);
    }

    private void preencherCampos() {
        jTextFieldID.setText(String.format("%04d", colaborador.getIdColaborador()));
        jComboBoxCargo.setSelectedIndex(colaborador.getCargo().ordinal());
        jFormattedTextFieldMatricula.setText(String.format("%d", colaborador.getMatricula()));
        jTextFieldNome.setText(colaborador.getNomeColaborador());

        if (colaborador.getCargo().equals(EnumCargo.ADVOGADO)) {
            String[] oab = colaborador.getOab().split("-");
            jTextFieldOAB.setText(oab[0]);
            jComboBoxUF.setSelectedIndex(EnumUF.valueOf(oab[1]).ordinal());
        }
        jComboBoxUF.setSelectedIndex(-1);
        jTextFieldEmail.setText(colaborador.getEmail());
        jFormattedTextFieldTelefone.setText(colaborador.getTelefone());
        jComboBoxPerfil.setSelectedIndex(colaborador.getPerfil().ordinal());
        jComboBoxStatus.setSelectedIndex(colaborador.getStatus().ordinal());

        boolean oProprio = (Vai.USUARIO.getIdColaborador() == colaborador.getIdColaborador());
        jLabelPerfil.setEnabled(!oProprio);
        jComboBoxPerfil.setEnabled(!oProprio);
        jLabelStatus.setEnabled(!oProprio);
        jComboBoxStatus.setEnabled(!oProprio);

        jButtonAlterarSenha.setEnabled(oProprio);
    }

    private void validarPreenchimento() throws Exception {

        if (jComboBoxCargo.getSelectedIndex() == -1) {
            jComboBoxCargo.requestFocus();
            throw new Exception("Selecione o cargo!");

        }

        String campo = new String(jFormattedTextFieldMatricula.getText().trim());
        if (campo.length() == 0) {
            jFormattedTextFieldMatricula.requestFocus();
            throw new Exception("Informe o número da matrícula!");
        }

        if (Integer.parseInt(campo) == 0) {
            jFormattedTextFieldMatricula.requestFocus();
            throw new Exception("O número da matrícula deve ser maior que 0 (zero)");
        }

        campo = new String(jTextFieldNome.getText().trim());
        if (campo.length() == 0) {
            jTextFieldNome.requestFocus();
            throw new Exception("Informe o nome do colaborador!");
        }

        if (campo.length() < 2) {
            jTextFieldNome.requestFocus();
            jTextFieldNome.selectAll();
            throw new Exception("O nome do colaborador precisa ter pelo menos duas letras!");
        }

        if (!soTemLetras(campo)) {
            jTextFieldNome.requestFocus();
            jTextFieldNome.selectAll();
            throw new Exception("O nome do colaborador deve ter apenas letras e espaços!");
        }

        campo = new String(jTextFieldOAB.getText().trim());
        if (campo.length() == 0) {  // Se o campo estiver vazio
            if (jComboBoxCargo.getSelectedItem().equals(EnumCargo.ADVOGADO.toString())) { // e for advogado
                throw new Exception("Informe o número da OAB");
            } else {
                if (jComboBoxUF.getSelectedIndex() != -1) { // ou selecionou a UF
                    if (mensagem.pergunta("Deseja informar o número da OAB ?") == 0) {
                        jTextFieldOAB.requestFocus();
                    } else {
                        jComboBoxUF.setSelectedIndex(-1);
                    }
                }
            }
        } else { // Se o campo não está vazio

            if (Integer.parseInt(jTextFieldOAB.getText()) == 0) {
                jTextFieldOAB.requestFocus();
                jTextFieldOAB.selectAll();
                throw new Exception("O campo nº OAB precisa ser diferente de 0 (zero)");
            }

            if (jComboBoxUF.getSelectedIndex() == -1) { // e não selecionou UF
                jTextFieldOAB.requestFocus();
                jTextFieldOAB.selectAll();
                throw new Exception("Selecione a UF da OAB!");
            }
        }

        campo = new String(jTextFieldEmail.getText().trim());
        if (campo.trim().length() > 0 && !isValidEmailAddressRegex(campo)) {
            jTextFieldEmail.requestFocus();
            jTextFieldEmail.selectAll();
            throw new Exception("Informe um e-mail válido!");
        }

        campo = new String(jFormattedTextFieldTelefone.getText().trim());
        if (campo.length() > 0 && !telefoneValido(campo)) {
            jFormattedTextFieldTelefone.requestFocus();
            jFormattedTextFieldTelefone.selectAll();
            throw new Exception("Informe um número de telefone válido!\n\n"
                    + "Formato válidos:\n"
                    + "9999-8888\n"
                    + "9 8888-7777\n"
                    + "(99) 8888-7777\n"
                    + "(99) 8 7777-6666");
        }

        if (jComboBoxPerfil.getSelectedIndex() == -1) {
            jComboBoxPerfil.requestFocus();
            throw new Exception("Selecione o perfil!");
        }

        if (jComboBoxStatus.getSelectedIndex() == -1) {
            throw new Exception("Selecione o status do colaborador!");
        }
    }

    private void salvar() throws Exception {
        validarPreenchimento();

        String senha = (acao.equals(EnumAcao.Editar)) ? colaborador.getSenha() : "";

        colaborador = new Colaborador();

        if (acao.equals(EnumAcao.Incluir)) {
            colaborador.setIdColaborador(id);
        } else if (acao.equals(EnumAcao.Editar)) {
            colaborador.setIdColaborador(id);
        }

        colaborador.setNomeColaborador(jTextFieldNome.getText());
        colaborador.setPerfil(EnumPerfil.valueOf(jComboBoxPerfil.getSelectedItem().toString()));
        colaborador.setMatricula(Integer.parseInt(jFormattedTextFieldMatricula.getText()));
        colaborador.setCargo(EnumCargo.valueOf(jComboBoxCargo.getSelectedItem().toString()));
        colaborador.setOab((jTextFieldOAB.getText().trim().length() == 0) ? ""
                : String.format("%s-%s", jTextFieldOAB.getText(), jComboBoxUF.getSelectedItem().toString()));
        colaborador.setSenha(senha);
        colaborador.setEmail(jTextFieldEmail.getText());
        colaborador.setTelefone(jFormattedTextFieldTelefone.getText());
        colaborador.setStatus(EnumTipoStatus.valueOf(jComboBoxStatus.getSelectedItem().toString()));

        if (acao.equals(EnumAcao.Incluir)) {
            controleColaborador.incluir(colaborador);
            mensagem.sucesso("Colaborador incluído com sucesso!");
        } else if (acao.equals(EnumAcao.Editar)) {
            controleColaborador.alterar(colaborador);
            mensagem.sucesso("Colaborador atualizado com sucesso!");
        }
        visible = false;
        this.dispose();
    }

    /**
     * Creates new form TelaColaborador
     */
    public TelaColaborador(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(rootPane);
        if (acao != null) {
            if (acao.equals(EnumAcao.Incluir)) {
                id = 0;
                jButtonAlterarSenha.setText("Senha");
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabelId = new javax.swing.JLabel();
        jTextFieldID = new javax.swing.JTextField();
        jPanelDadosEmpresa = new javax.swing.JPanel();
        jLabelCargo = new javax.swing.JLabel();
        jComboBoxCargo = new javax.swing.JComboBox<>();
        jLabelMatricula = new javax.swing.JLabel();
        jFormattedTextFieldMatricula = new javax.swing.JFormattedTextField();
        jPanelDadosPessoais = new javax.swing.JPanel();
        jLabelNome = new javax.swing.JLabel();
        jTextFieldNome = new javax.swing.JTextField();
        jLabelOAB = new javax.swing.JLabel();
        jLabelUF = new javax.swing.JLabel();
        jComboBoxUF = new javax.swing.JComboBox<>();
        jLabelEmail = new javax.swing.JLabel();
        jTextFieldEmail = new javax.swing.JTextField();
        jLabelTelefone = new javax.swing.JLabel();
        jFormattedTextFieldTelefone = new javax.swing.JFormattedTextField();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldOAB = new javax.swing.JTextField();
        jPanelAcessoSistema = new javax.swing.JPanel();
        jLabelPerfil = new javax.swing.JLabel();
        jComboBoxPerfil = new javax.swing.JComboBox<>();
        jLabelStatus = new javax.swing.JLabel();
        jComboBoxStatus = new javax.swing.JComboBox<>();
        jButtonAlterarSenha = new javax.swing.JButton();
        jButtonSalvar = new javax.swing.JButton();

        jLabel2.setText("jLabel2");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabelId.setText("ID:");

        jTextFieldID.setFont(new java.awt.Font("Dialog", 3, 12)); // NOI18N
        jTextFieldID.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextFieldID.setDisabledTextColor(new java.awt.Color(102, 102, 102));
        jTextFieldID.setEnabled(false);

        jPanelDadosEmpresa.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados para a empresa"));

        jLabelCargo.setText("Cargo");

        jComboBoxCargo.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        jLabelMatricula.setText("Matrícula");

        jFormattedTextFieldMatricula.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("#"))));
        jFormattedTextFieldMatricula.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jFormattedTextFieldMatriculaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanelDadosEmpresaLayout = new javax.swing.GroupLayout(jPanelDadosEmpresa);
        jPanelDadosEmpresa.setLayout(jPanelDadosEmpresaLayout);
        jPanelDadosEmpresaLayout.setHorizontalGroup(
            jPanelDadosEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDadosEmpresaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDadosEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jComboBoxCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelCargo))
                .addGap(18, 18, 18)
                .addGroup(jPanelDadosEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelMatricula)
                    .addComponent(jFormattedTextFieldMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(198, Short.MAX_VALUE))
        );
        jPanelDadosEmpresaLayout.setVerticalGroup(
            jPanelDadosEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDadosEmpresaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDadosEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCargo)
                    .addComponent(jLabelMatricula))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDadosEmpresaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jFormattedTextFieldMatricula, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxCargo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanelDadosPessoais.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados pessoais"));

        jLabelNome.setText("Nome");

        jTextFieldNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldNomeKeyTyped(evt);
            }
        });

        jLabelOAB.setText("nº OAB");

        jLabelUF.setText("UF OAB");

        jComboBoxUF.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        jLabelEmail.setText("E-mail");

        jTextFieldEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jTextFieldEmailKeyReleased(evt);
            }
        });

        jLabelTelefone.setText("Telefone");

        jFormattedTextFieldTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat(""))));
        jFormattedTextFieldTelefone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jFormattedTextFieldTelefoneFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jFormattedTextFieldTelefoneFocusLost(evt);
            }
        });
        jFormattedTextFieldTelefone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jFormattedTextFieldTelefoneKeyTyped(evt);
            }
        });

        jLabel1.setText("-");

        jTextFieldOAB.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldOABKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanelDadosPessoaisLayout = new javax.swing.GroupLayout(jPanelDadosPessoais);
        jPanelDadosPessoais.setLayout(jPanelDadosPessoaisLayout);
        jPanelDadosPessoaisLayout.setHorizontalGroup(
            jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDadosPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabelEmail)
                    .addComponent(jTextFieldEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 305, Short.MAX_VALUE)
                    .addComponent(jLabelNome)
                    .addComponent(jTextFieldNome))
                .addGap(18, 18, 18)
                .addGroup(jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jFormattedTextFieldTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTelefone)
                    .addGroup(jPanelDadosPessoaisLayout.createSequentialGroup()
                        .addGroup(jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanelDadosPessoaisLayout.createSequentialGroup()
                                .addComponent(jTextFieldOAB, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1))
                            .addComponent(jLabelOAB))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelUF)
                            .addComponent(jComboBoxUF, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanelDadosPessoaisLayout.setVerticalGroup(
            jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelDadosPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelNome)
                    .addComponent(jLabelOAB)
                    .addComponent(jLabelUF))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldNome, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxUF, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jTextFieldOAB, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelEmail)
                    .addComponent(jLabelTelefone))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanelDadosPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jFormattedTextFieldTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanelAcessoSistema.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados de acesso ao sistema"));

        jLabelPerfil.setText("Perfil");

        jComboBoxPerfil.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        jLabelStatus.setText("Status");

        jComboBoxStatus.setFont(new java.awt.Font("Dialog", 0, 12)); // NOI18N

        jButtonAlterarSenha.setText("Alterar Senha");
        jButtonAlterarSenha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonAlterarSenhaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelAcessoSistemaLayout = new javax.swing.GroupLayout(jPanelAcessoSistema);
        jPanelAcessoSistema.setLayout(jPanelAcessoSistemaLayout);
        jPanelAcessoSistemaLayout.setHorizontalGroup(
            jPanelAcessoSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAcessoSistemaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAcessoSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelPerfil)
                    .addComponent(jComboBoxPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanelAcessoSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelStatus)
                    .addComponent(jComboBoxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButtonAlterarSenha)
                .addContainerGap())
        );
        jPanelAcessoSistemaLayout.setVerticalGroup(
            jPanelAcessoSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelAcessoSistemaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelAcessoSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelAcessoSistemaLayout.createSequentialGroup()
                        .addGroup(jPanelAcessoSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelPerfil)
                            .addComponent(jLabelStatus))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanelAcessoSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBoxPerfil, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jButtonAlterarSenha, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

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
                        .addComponent(jLabelId)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButtonSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanelAcessoSistema, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelDadosPessoais, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanelDadosEmpresa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(20, 20, 20))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelId)
                    .addComponent(jTextFieldID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelDadosEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelDadosPessoais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanelAcessoSistema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButtonSalvar)
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

    private void jTextFieldNomeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldNomeKeyTyped
        try {
            if (jTextFieldNome.getText().length() >= 50) {
                evt.consume();
                jTextFieldNome.setText(jTextFieldNome.getText().substring(0, 50));
            }
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jTextFieldNomeKeyTyped

    private void jFormattedTextFieldMatriculaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextFieldMatriculaKeyTyped
        try {
            if (jFormattedTextFieldMatricula.getText().length() >= 6) {
                evt.consume();
                jFormattedTextFieldMatricula.setText(jFormattedTextFieldMatricula.getText().substring(0, 6));
            }
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jFormattedTextFieldMatriculaKeyTyped

    private void jTextFieldOABKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldOABKeyTyped
        try {
            if (jTextFieldOAB.getText().length() >= 9) {
                evt.consume();
                jTextFieldOAB.setText(jTextFieldOAB.getText().substring(0, 6));
            } else {
                if (!EnumCaracteres.Numeros.getCaracteres().contains(evt.getKeyChar() + "")) {
                    evt.consume();
                }
            }
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jTextFieldOABKeyTyped

    private void jFormattedTextFieldTelefoneFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFormattedTextFieldTelefoneFocusGained
        jFormattedTextFieldTelefone.setFormatterFactory(null);
    }//GEN-LAST:event_jFormattedTextFieldTelefoneFocusGained

    private void jFormattedTextFieldTelefoneFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_jFormattedTextFieldTelefoneFocusLost
        try {
            mudaMascaraTelefone(jFormattedTextFieldTelefone);
        } catch (Exception e) {
            jFormattedTextFieldTelefone.requestFocus();
            jFormattedTextFieldTelefone.selectAll();
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jFormattedTextFieldTelefoneFocusLost

    private void jFormattedTextFieldTelefoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jFormattedTextFieldTelefoneKeyTyped
        try {
            if (textoSoComNumeros(jFormattedTextFieldTelefone.getText()).length() >= 11) {
                evt.consume();
                jFormattedTextFieldTelefone.setText(jFormattedTextFieldTelefone.getText().substring(0, 11));
            } else {
                if (!EnumCaracteres.Numeros.getCaracteres().contains(evt.getKeyChar() + "")) {
                    evt.consume();
                }
            }
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jFormattedTextFieldTelefoneKeyTyped

    private void jButtonAlterarSenhaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonAlterarSenhaActionPerformed
        mensagem.informacao("Implementar...");
    }//GEN-LAST:event_jButtonAlterarSenhaActionPerformed

    private void jTextFieldEmailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldEmailKeyReleased
        try {
            jTextFieldEmail.setText(jTextFieldEmail.getText().toLowerCase());
        } catch (Exception e) {
            mensagem.erro(e);
        }
    }//GEN-LAST:event_jTextFieldEmailKeyReleased

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
            java.util.logging.Logger.getLogger(TelaColaborador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaColaborador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaColaborador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaColaborador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                TelaColaborador dialog = new TelaColaborador(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton jButtonAlterarSenha;
    private javax.swing.JButton jButtonSalvar;
    private javax.swing.JComboBox<String> jComboBoxCargo;
    private javax.swing.JComboBox<String> jComboBoxPerfil;
    private javax.swing.JComboBox<String> jComboBoxStatus;
    private javax.swing.JComboBox<String> jComboBoxUF;
    private javax.swing.JFormattedTextField jFormattedTextFieldMatricula;
    private javax.swing.JFormattedTextField jFormattedTextFieldTelefone;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabelCargo;
    private javax.swing.JLabel jLabelEmail;
    private javax.swing.JLabel jLabelId;
    private javax.swing.JLabel jLabelMatricula;
    private javax.swing.JLabel jLabelNome;
    private javax.swing.JLabel jLabelOAB;
    private javax.swing.JLabel jLabelPerfil;
    private javax.swing.JLabel jLabelStatus;
    private javax.swing.JLabel jLabelTelefone;
    private javax.swing.JLabel jLabelUF;
    private javax.swing.JPanel jPanelAcessoSistema;
    private javax.swing.JPanel jPanelDadosEmpresa;
    private javax.swing.JPanel jPanelDadosPessoais;
    private javax.swing.JTextField jTextFieldEmail;
    private javax.swing.JTextField jTextFieldID;
    private javax.swing.JTextField jTextFieldNome;
    private javax.swing.JTextField jTextFieldOAB;
    // End of variables declaration//GEN-END:variables
}
