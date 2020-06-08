/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Livro;
import interfaces.ICRUDLivro;
import java.util.ArrayList;
import persistencia.PersistenciaLivro;

/**
 *
 * @author vovostudio
 */
public class ControleLivro implements ICRUDLivro {

    private ICRUDLivro persistencia = null;
    private ArrayList<Livro> colecao = null;

    public ControleLivro() throws Exception {
        persistencia = new PersistenciaLivro();
    }

    @Override
    public ArrayList<Livro> listar() throws Exception {
        return persistencia.listar();
    }

    @Override
    public Livro buscarPeloId(int idLivro) throws Exception {
        return persistencia.buscarPeloId(idLivro);
    }

    @Override

    public void incluir(Livro livro) throws Exception {
        // Fazer a validação
        //validarPreenchimento(areaConhecimento.getCdd(), areaConhecimento.getDescricaoAreaConhecimento());
        //colecao = listar();
        //validarDuplicidade(areaConhecimento);
        persistencia.incluir(livro);
    }

    @Override
    public void alterar(Livro livro) throws Exception {
        // Fazer validação
        persistencia.alterar(livro);

    }

    @Override
    public void excluir(int idLivro) throws Exception {

        // Fazer validação para excluir
        persistencia.excluir(idLivro);
    }
}
