/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Colaborador;
import enumeradores.EnumTipoStatus;
import java.util.ArrayList;
import utilidades.Hash;

/**
 *
 * @author vovostudio
 */
public class ControleLogin {

    private ArrayList<Colaborador> colecao = null;
    private Colaborador colaborador = null;

    public Colaborador autenticar(String login, String senha) throws Exception {

        if (login.trim().length() == 0) {
            throw new Exception("O campo Login é obrigatório!");
        }

        validarSenha(senha);

        validarColaborador(login);

        if (!colaborador.getSenha().equals(Hash.criptografar(senha, "SHA-256"))) {
            throw new Exception("Senha incorreta!");
        }

        return colaborador;
    }

    public void validarSenha(String senha) throws Exception {
        if (senha.length() == 0) {
            throw new Exception("Informe a senha!");
        }
        if (senha.length() < 6) {
            throw new Exception("A senha precisa ter 6 caracteres!");
        }
    }

    public Colaborador validarColaborador(String login) throws Exception {
        colecao = new ControleColaborador().listar();

        if (colecao.isEmpty()) {
            throw new Exception("Nenhum colaborador cadastrado!");
        }

        colaborador = new Colaborador();

        for (Colaborador c : colecao) {
            if (("" + c.getMatricula()).equals(login)
                    || ("" + c.getOab()).equals(login)
                    || (c.getEmail()).equals(login)) {
                // Colaborador está cadastrado
                colaborador = new Colaborador(c);
                break;
            }
        }

        if (colaborador.getNomeColaborador() == "") {
            throw new Exception("Login inválido!");
        }

        if (colaborador.getStatus().equals(EnumTipoStatus.INATIVO)) {
            throw new Exception("Colaborador inativo!");
        }

        return colaborador;
    }
}
