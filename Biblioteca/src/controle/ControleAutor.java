/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Autor;
import classes.Livro;
import interfaces.ICRUDAutor;
import interfaces.ICRUDLivro;
import java.util.ArrayList;
import java.util.Collections;
import persistencia.PersistenciaAutor;
import static utilidades.ColecaoUtil.getComparadorAutorNomeCresc;

/**
 *
 * @author vovostudio
 */
public class ControleAutor implements ICRUDAutor {

    private ICRUDAutor persistencia;
    private ICRUDLivro controleLivro;
    private ArrayList<Autor> colecao;

    public ControleAutor() throws Exception {
        persistencia = new PersistenciaAutor();
        colecao = new ArrayList<>();
    }

    @Override
    public ArrayList<Autor> listar() throws Exception {
        colecao = persistencia.listar();
        Collections.sort(colecao, getComparadorAutorNomeCresc());
        return colecao;
    }

    @Override
    public Autor buscarPeloId(int idAutor) throws Exception {
        return persistencia.buscarPeloId(idAutor);
    }

    public Autor buscarPeloNome(String nomeAutor) throws Exception {
        for (Autor autor : listar()) {
            if (autor.getNomeAutor().equalsIgnoreCase(nomeAutor)) {
                return autor;
            }
        }
        return null;
    }

    @Override
    public void incluir(Autor autor) throws Exception {
        validarPreenchimento(autor);
        persistencia.incluir(autor);

    }

    @Override
    public void alterar(Autor autor) throws Exception {
        validarPreenchimento(autor);
        persistencia.alterar(autor);
    }

    @Override
    public void excluir(int idAutor) throws Exception {
        controleLivro = new ControleLivro();
        for (Livro livro : controleLivro.listar()) {
            if (livro.getAutor().getIdAutor() == idAutor) {
                throw new Exception("O livro " + livro.getTitulo() + " está cadastrado para esse autor e não poderá ser excluído!");
            }
        }
        persistencia.excluir(idAutor);
    }

    private void validarPreenchimento(Autor autor) throws Exception {

        String nome = autor.getNomeAutor().trim();
        if (nome.length() < 2) {
            throw new Exception("O nome precisa ter duas letras ou mais!");
        }

        if (nome.toLowerCase().charAt(0) == autor.getNomeAutor().toLowerCase().charAt(1)
                && nome.length() == 2) {
            throw new Exception("Nome com duas letras, e ainda iguais?? ta de brinks...");
        }

        colecao = persistencia.listar();
        for (Autor objAutor : colecao) {
            if (objAutor.getNomeAutor().equals(autor.getNomeAutor())
                    && objAutor.getIdAutor() != autor.getIdAutor()) {
                throw new Exception("Já existe um autor cadastrado com este nome!");
            }
        }
    }
}
