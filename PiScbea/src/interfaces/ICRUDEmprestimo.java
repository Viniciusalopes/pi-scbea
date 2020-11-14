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

import classes.Emprestimo;
import java.util.ArrayList;

/**
 *
 * @author lucas
 */
public interface ICRUDEmprestimo {

    ArrayList<Emprestimo> listar() throws Exception;

    Emprestimo buscarPeloId(int idEmprestimo) throws Exception;

    int incluir(Emprestimo emprestimo) throws Exception;

    void alterar(Emprestimo emprestimo) throws Exception;

    void excluir(int idEmprestimo) throws Exception;

}
