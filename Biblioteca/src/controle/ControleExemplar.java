/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Exemplar;
import interfaces.ICRUDExemplar;
import java.util.ArrayList;
import persistencia.PersistenciaExemplar;

/**
 *
 * @author vovostudio
 */
public class ControleExemplar implements ICRUDExemplar {
    private ICRUDExemplar persistencia = null ;
    private ArrayList<Exemplar> colecao;

    public ControleExemplar() throws Exception {
        persistencia = new PersistenciaExemplar();
        this.colecao = colecao;
    }
    

    @Override
    public ArrayList<Exemplar> listar() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Exemplar buscarPeloId(int idExmplar) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void incluir(Exemplar exemplar) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void alterar(Exemplar exemplar) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void excluir(Exemplar exemplar) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
