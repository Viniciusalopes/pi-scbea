/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Emprestimo;
import interfaces.ICRUDEmprestimo;
import java.util.ArrayList;
import persistencia.PersistenciaEmprestimo;

/**
 *
 * @author vovostudio
 */
public class ControleEmprestimo implements ICRUDEmprestimo {

    private ICRUDEmprestimo persistencia = null;
    private ArrayList<Emprestimo> colecao = null;

    public ControleEmprestimo() throws Exception {
        persistencia = new PersistenciaEmprestimo();
    }

    @Override
    public ArrayList<Emprestimo> listar() throws Exception {
        return persistencia.listar();
    }

    @Override
    public Emprestimo buscarPeloId(int idEmprestimo) throws Exception {
        return persistencia.buscarPeloId(idEmprestimo);
    }

    @Override
    public void incluir(Emprestimo emprestimo) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void alterar(Emprestimo emprestimo) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void excluir(int idEmprestimo) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
