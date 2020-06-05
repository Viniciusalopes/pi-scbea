/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import classes.Editora;
import java.util.ArrayList;

/**
 *
 * @author lucas
 */
public interface ICRUDEditora {

    ArrayList<Editora> listar() throws Exception;
    
    Editora buscarPeloId(int id) throws Exception;
    
    void incluir(Editora editora) throws Exception;

    void alterar(Editora editora) throws Exception;

    void excluir(int idEditora) throws Exception;

}
