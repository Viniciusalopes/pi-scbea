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
package controle;

import classes.Editora;
import interfaces.ICRUDEditora;
import java.util.ArrayList;

/**
 *
 * @author lucas
 */
public class ControleEditora implements ICRUDEditora {

    @Override
    public ArrayList<Editora> listar() throws Exception {//ok
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void incluir(Editora editora) throws Exception {//ok
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void alterar(Editora editora) throws Exception {//ok
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void excluir(int idEditora) throws Exception {//ok
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
