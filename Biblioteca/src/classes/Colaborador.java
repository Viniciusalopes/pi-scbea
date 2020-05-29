/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import enumeradores.EnumCargo;
import enumeradores.EnumPerfil;
import enumeradores.EnumTipoStatus;

/**
 *
 * @author vovostudio
 */
public class Colaborador {

    private int idColaborador = 0;
    private String nomeColaborador;
    private EnumPerfil perfil = EnumPerfil.ADMINISTRADOR;
    private int matricula = 0;
    private EnumCargo cargo = EnumCargo.DESENVOLVEDOR;
    private String oab = "";
    private String senha = "";
    private String email = "";
    private String telefone = "";
    private EnumTipoStatus status = EnumTipoStatus.ATIVO;

    public Colaborador() {

    }

    public Colaborador(Colaborador colaborador) {
        idColaborador = colaborador.idColaborador;
        nomeColaborador = colaborador.nomeColaborador;
        perfil = colaborador.perfil;
        matricula = colaborador.matricula;
        cargo = colaborador.cargo;
        oab = colaborador.oab;
        senha = colaborador.senha;
        email = colaborador.email;
        telefone = colaborador.telefone;
        status = colaborador.status;
    }

    public Colaborador(
            int idColaborador,
            String nomeColaborador,
            EnumPerfil perfil,
            int matricula,
            EnumCargo cargo,
            String oab,
            String senha,
            String email,
            String telefone,
            EnumTipoStatus status
    ) {
        this.idColaborador = idColaborador;
        this.nomeColaborador = nomeColaborador;
        this.perfil = perfil;
        this.matricula = matricula;
        this.cargo = cargo;
        this.oab = oab;
        this.senha = senha;
        this.email = email;
        this.telefone = telefone;
        this.status = status;
    }

    public int getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(int idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getNomeColaborador() {
        return nomeColaborador;
    }

    public void setNomeColaborador(String nomeColaborador) {
        this.nomeColaborador = nomeColaborador;
    }

    public EnumPerfil getPerfil() {
        return perfil;
    }

    public void setPerfil(EnumPerfil perfil) {
        this.perfil = perfil;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public EnumCargo getCargo() {
        return cargo;
    }

    public void setCargo(EnumCargo cargo) {
        this.cargo = cargo;
    }

    public String getOab() {
        return oab;
    }

    public void setOab(String oab) {
        this.oab = oab;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public EnumTipoStatus getStatus() {
        return status;
    }

    public void setStatus(EnumTipoStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return String.format("%d;%s;%d;%d;%d;%s;%s;%s;%s;%d",
                idColaborador,
                nomeColaborador,
                perfil.ordinal(),
                matricula,
                cargo.ordinal(),
                oab,
                senha,
                email,
                telefone,
                status.ordinal()
        );
    }
}
