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

import classes.AreaConhecimento;
import java.util.ArrayList;

/**
 *
 * @author lucas
 */
public interface ICRUDAreaConhecimento {

    ArrayList<AreaConhecimento> listar() throws Exception;

    void incluir(AreaConhecimento areaConhecimento) throws Exception;

    void alterar(AreaConhecimento areaConhecimento) throws Exception;

    void excluir(AreaConhecimento areaConhecimento) throws Exception;
}
