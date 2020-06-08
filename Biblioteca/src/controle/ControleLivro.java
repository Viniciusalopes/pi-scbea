/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Livro;
import interfaces.ICRUDLivro;
import java.util.ArrayList;

/**
 *
 * @author vovostudio
 */
public class ControleLivro implements ICRUDLivro {

    @Override
    public ArrayList<Livro> listar() {
        return new ArrayList<>();
    }

    @Override
    public Livro buscarPeloId(int id) throws Exception {
        return new Livro();
    }

    @Override
    public void incluir(Livro livro) throws Exception {

    }

    @Override
    public void alterar(Livro livro) throws Exception {

    }

    @Override
    public void excluir(Livro livro) throws Exception {

    }

}
