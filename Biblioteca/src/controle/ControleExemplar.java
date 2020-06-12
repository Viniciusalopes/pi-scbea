/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Emprestimo;
import classes.Exemplar;
import interfaces.ICRUDEmprestimo;
import interfaces.ICRUDExemplar;
import java.util.ArrayList;
import java.util.Collections;
import persistencia.PersistenciaExemplar;
import static utilidades.ColecaoUtil.getComparadorExemplarTituloCresc;

/**
 *
 * @author vovostudio
 */
public class ControleExemplar implements ICRUDExemplar {

    private ICRUDExemplar persistencia = null;
    private ArrayList<Exemplar> colecao = null;

    public ControleExemplar() throws Exception {
        persistencia = new PersistenciaExemplar();
        colecao = new ArrayList<>();
    }

    @Override

    public ArrayList<Exemplar> listar() throws Exception {
        colecao = persistencia.listar();
        Collections.sort(colecao, getComparadorExemplarTituloCresc());
        return colecao;
    }

    @Override
    public Exemplar buscarPeloId(int idExmplar) throws Exception {
        return persistencia.buscarPeloId(idExmplar);
    }

    @Override
    public void incluir(Exemplar exemplar) throws Exception {
        persistencia.incluir(exemplar);
    }

    @Override
    public void alterar(Exemplar exemplar) throws Exception {
        persistencia.alterar(exemplar);
    }

    @Override
    public void excluir(int idExemplar) throws Exception {
        ICRUDEmprestimo controleEmprestimo = new ControleEmprestimo();

        ArrayList<Emprestimo> emprestimos = controleEmprestimo.listar();
        //se o livro ja foi alguma vez emprestado nao pode ser excluido 
        for (Emprestimo emprestimo : emprestimos) {
            if (emprestimo.getExemplar().getIdExemplar() == idExemplar) {
                throw new Exception("este exemplar já foi emprestado por isso não pode ser excluido! ");

            }
        }
        persistencia.excluir(idExemplar);
    }
}
