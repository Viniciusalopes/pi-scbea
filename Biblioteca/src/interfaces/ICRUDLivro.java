/*
=====================================
ICRUDObjeto
--------METODOS----------------------
+ listar() : ArrayList<Objeto>
+ incluir(objeto : Objeto) : void
+ atualizar(objeto : Objeto) : void
+ excluir(idObjeto : int) : void
-------------------------------------
-------------------------------------
 */
package interfaces;

import classes.Livro;
import java.util.ArrayList;

/**
 *
 * @author lucas
 */
public interface ICRUDLivro {

    ArrayList<Livro> listar() throws Exception;

    void incluir(Livro livro) throws Exception;

    void alterar(Livro livro) throws Exception;

    void excluir(Livro livro) throws Exception;
}
