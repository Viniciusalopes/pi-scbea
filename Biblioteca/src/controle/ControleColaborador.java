/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Colaborador;
import interfaces.ICRUDColaborador;
import java.util.ArrayList;
import persistencia.PersistenciaColaborador;

/**
 *
 * @author vovostudio
 */
public class ControleColaborador implements ICRUDColaborador {

    private ICRUDColaborador persistencia = null;
    private ArrayList<Colaborador> colecao = null;

    public ControleColaborador() throws Exception {
        persistencia = new PersistenciaColaborador();
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
}
