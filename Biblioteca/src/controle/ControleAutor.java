/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Autor;
import interfaces.ICRUDAutor;
import java.util.ArrayList;
import persistencia.PersistenciaAutor;

/**
 *
 * @author vovostudio
 */
public class ControleAutor implements ICRUDAutor {

    private ICRUDAutor persistencia;
    private ArrayList<Autor> colecao;

    public ControleAutor() throws Exception {
        persistencia = new PersistenciaAutor();
        colecao = new ArrayList<>();
    }

    @Override
    public ArrayList<Autor> listar() throws Exception {
        return persistencia.listar();
    }

    @Override
    public Autor buscarPeloId(int idAutor) throws Exception {
        colecao = listar();
        for (Autor autor : colecao) {
            if (autor.getIdAutor() == idAutor) {
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
