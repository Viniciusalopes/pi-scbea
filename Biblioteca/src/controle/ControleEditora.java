/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Editora;
import interfaces.ICRUDEditora;
import java.util.ArrayList;
import persistencia.PersistenciaEditora;

/**
 *
 * @author vovostudio
 */
public class ControleEditora implements ICRUDEditora {

    private ICRUDEditora persistencia = null;
    private ArrayList<Editora> colecao = null;

    public ControleEditora() throws Exception {
        persistencia = new PersistenciaEditora();
        colecao = new ArrayList<>();
    }

    @Override
    public ArrayList<Editora> listar() throws Exception {
        return persistencia.listar();
    }

    @Override
    public void incluir(Editora editora) throws Exception {
        if (editora.getNomeEditora().trim().length() < 2) {
            throw new Exception("O nome da editora precisa ter dois ou mais caracteres.");
        }

        persistencia.incluir(editora);
    }

    @Override
    public void alterar(Editora editora) throws Exception {
        
        persistencia.alterar(editora);
    }

    @Override
    public void excluir(int idEditora) throws Exception {
        
        persistencia.excluir(idEditora);
    }

}
