/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import classes.Colaborador;
import java.util.ArrayList;

/**
 *
 * @author vovostudio
 */
public interface ICRUDColaborador {

    ArrayList<Colaborador> listar() throws Exception;

    Colaborador buscarPeloId(int idColaborador) throws Exception;

    void incluir(Colaborador objColaborador) throws Exception;

    void alterar(Colaborador objColaborador) throws Exception;

    void excluir(int idColaborador) throws Exception;

}
