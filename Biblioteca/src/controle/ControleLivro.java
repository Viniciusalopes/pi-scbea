/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Emprestimo;
import classes.Livro;
import classes.Reserva;
import interfaces.ICRUDEmprestimo;
import interfaces.ICRUDLivro;
import interfaces.ICRUDReserva;
import java.util.ArrayList;
import java.util.Collections;
import persistencia.PersistenciaLivro;
import static utilidades.ColecaoUtil.getComparadorLivroTituloCresc;
import static utilidades.StringUtil.textoSoComNumeros;
import static utilidades.StringUtil.truncar;

/**
 *
 * @author vovostudio
 */
public class ControleLivro implements ICRUDLivro {

    private ICRUDLivro persistencia = null;
    private ICRUDEmprestimo controleEmprestimo = null;
    private ICRUDReserva controleReserva = null;
    private ArrayList<Livro> colecao = null;

    public ControleLivro() throws Exception {
        persistencia = new PersistenciaLivro();
        colecao = new ArrayList<>();
    }

    @Override
    public ArrayList<Livro> listar() throws Exception {
        colecao = persistencia.listar();
        Collections.sort(colecao, getComparadorLivroTituloCresc());
        return colecao;
    }

    @Override
    public Livro buscarPeloId(int idLivro) throws Exception {
        return persistencia.buscarPeloId(idLivro);
    }

    @Override

    public int incluir(Livro livro) throws Exception {
        validarDuplicidade(livro);
        return persistencia.incluir(livro);
    }

    @Override
    public void alterar(Livro livro) throws Exception {
        validarDuplicidade(livro);
        persistencia.alterar(livro);

    }

    @Override
    public void excluir(int idLivro) throws Exception {

        controleEmprestimo = new ControleEmprestimo();
        for (Emprestimo e : controleEmprestimo.listar()) {
            if (e.getExemplar().getIdLivro() == idLivro) {
                throw new Exception("O livro já foi emprestado e não pode ser excluído!\n"
                        + "Empréstimo: " + e.getIdEmprestimo() + "-" + e.getColaborador().getNomeColaborador());
            }
        }
        controleReserva = new ControleReserva();
        for (Reserva r : controleReserva.listar()) {
            if (r.getLivro().getIdLivro() == idLivro) {
                throw new Exception("O livro está reservado e não pode ser excluído!\n"
                        + "Reserva: " + r.getIdReserva() + "-" + r.getColaborador().getNomeColaborador());
            }
        }

        persistencia.excluir(idLivro);
    }

    private void validarDuplicidade(Livro livro) throws Exception {

        for (Livro l : persistencia.listar()) {
            String identificacaoLivro = l.getIdLivro() + "-" + truncar(l.getTitulo(), 40);

            if (textoSoComNumeros(l.getIsbn()).equals(textoSoComNumeros(livro.getIsbn()))
                    && l.getIdLivro() != livro.getIdLivro()) {
                throw new Exception("Já existe um livro cadastrado com esse ISBN!\n"
                        + "Livro: " + identificacaoLivro);
            }

            if (l.toString().replace(l.getIsbn(), "").equalsIgnoreCase(livro.toString().replace(livro.getIsbn(), ""))
                    && l.getIdLivro() != livro.getIdLivro()) {
                throw new Exception("Já existe um livro cadastrado com esses dados!\n"
                        + "Livro: " + identificacaoLivro);
            }
        }
    }
}
