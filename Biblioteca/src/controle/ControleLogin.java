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

    public Colaborador autenticar(String login, String senha) throws Exception {

        if (login.trim().length() == 0) {
            throw new Exception("O campo Login é obrigatório!");
        }

        if (senha.trim().length() == 0) {
            throw new Exception("O campo Senha é obrigatório!");
        }

        ArrayList<Colaborador> colecao = new ControleColaborador().listar();

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
                break;
            }
        }

        if (colaborador.getNomeColaborador() == "") {
            throw new Exception("Login inválido!");
        }

        if (colaborador.getStatus().equals(EnumTipoStatus.INATIVO)) {
            throw new Exception("Colaborador inativo!");
        }

        if (!colaborador.getSenha().equals(Hash.criptografar(senha, "SHA-256"))) {
            throw new Exception("Senha incorreta!");
        }

        return colaborador;
    }
}
