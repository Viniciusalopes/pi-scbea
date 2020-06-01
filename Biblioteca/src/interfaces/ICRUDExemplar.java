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

import classes.Exemplar;
import java.util.ArrayList;

/**
 *
 * @author lucas
 */
public interface ICRUDExemplar {

    ArrayList<Exemplar> listar() throws Exception;

    void incluir(Exemplar exemplar) throws Exception;

    void alterar(Exemplar exemplar) throws Exception;

    void excluir(Exemplar exemplar) throws Exception;
}
