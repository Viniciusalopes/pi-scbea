/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Colaborador;
import enumeradores.EnumTipoStatus;
import interfaces.ICRUDColaborador;
import java.util.ArrayList;
import persistencia.PersistenciaColaborador;
import utilidades.Hash;

/**
 *
 * @author vovostudio
 */
public class ControleColaborador implements ICRUDColaborador {

    private ICRUDColaborador persistencia = null;
    private ArrayList<Colaborador> colecao = null;

    public ControleColaborador() throws Exception {
        persistencia = new PersistenciaColaborador();
        colecao = persistencia.listar();
    }

    @Override
    public ArrayList<Colaborador> listar() throws Exception {
        return persistencia.listar();
    }

    @Override
    public void incluir(Colaborador objColaborador) throws Exception {
    }

    @Override
    public void atualizar(Colaborador objColaborador) {
    }

    @Override
    public void excluir(int idColaborador) {
    }

    public Colaborador autenticar(String login, String senha) throws Exception {

        if (login.trim().length() == 0) {
            throw new Exception("O campo Login é obrigatório!");
        }

        if (senha.trim().length() == 0) {
            throw new Exception("O campo Senha é obrigatório!");
        }

        colecao = listar();

        if (colecao.isEmpty()) {
            throw new Exception("Nenhum colaborador cadastrado!");
        }

        Colaborador colaborador = new Colaborador();

        for (Colaborador c : colecao) {
            if (("" + c.getMatricula()).equals(login)
                    || ("" + c.getOab()).equals(login)
                    || (c.getEmail()).equals(login)) {
                // Colaborador está cadastrado
                colaborador = new Colaborador(c);
            }
        }

        if (colaborador.getNomeColaborador() == null) {
            throw new Exception("Login inválido!");
        }

        if (colaborador.getStatus().equals(EnumTipoStatus.INATIVO)) {
            throw new Exception("Colaborador inativo!");
        }

        String senhaHash = Hash.criptografar(senha, "SHA-256");
        if (!colaborador.getSenha().equals(senhaHash)) {
            throw new Exception("Senha incorreta!");
        }
        
        return colaborador;
    }
}
