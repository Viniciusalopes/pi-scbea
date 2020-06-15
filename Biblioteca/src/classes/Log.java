/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import enumeradores.EnumAcao;
import enumeradores.EnumCadastro;
import java.text.SimpleDateFormat;
import java.util.Date;
import telas.Vai;

/**
 *
 * @author vovostudio
 */
public class Log {

    // ATRIBUTOS
    private Date dataLog = null;
    private Colaborador usuario = null;
    private EnumAcao acao = null;
    private EnumCadastro cadastro = null;
    private String registro = "";
    private String observacao = "";

    // CONSTRUTORES
    public Log() {

    }

    public Log(Log log) {
        dataLog = log.dataLog;
        usuario = log.usuario;
        acao = log.acao;
        cadastro = log.cadastro;
        registro = log.registro;
        observacao = log.observacao;
    }

    public Log(Date dataLog, Colaborador usuario, EnumAcao acao, EnumCadastro cadastro, String registro, String observacao) {
        this.dataLog = dataLog;
        this.usuario = usuario;
        this.acao = acao;
        this.cadastro = cadastro;
        this.registro = registro;
        this.observacao = observacao;
    }

    // MÉTODOS
    public Date getDataLog() {
        return dataLog;
    }

    public void setDataLog(Date dataLog) {
        this.dataLog = dataLog;
    }

    public Colaborador getUsuario() {
        return usuario;
    }

    public void setUsuario(Colaborador usuario) {
        this.usuario = usuario;
    }

    public EnumAcao getAcao() {
        return acao;
    }

    public void setAcao(EnumAcao acao) {
        this.acao = acao;
    }

    public EnumCadastro getCadastro() {
        return cadastro;
    }

    public void setCadastro(EnumCadastro cadastro) {
        this.cadastro = cadastro;
    }

    public String getRegistro() {
        return registro;
    }

    public void setRegistro(String registro) {
        this.registro = registro;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    @Override
    public String toString() {
        String retorno = String.format("%s_%d_%d_%d_%s_%s",
                new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(dataLog),
                Vai.USUARIO == null ? 0 : Vai.USUARIO.getIdColaborador(),
                acao.ordinal(),
                cadastro.ordinal(),
                registro.replace("_", "-"),
                observacao.replace("_", "-")
        );

        return retorno;
    }
}
