/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import classes.Autor;
import java.util.ArrayList;

/**
 *
 * @author vovostudio
 */
public interface ICRUDAutor {

    ArrayList<Autor> listar() throws Exception;

    void incluir(Autor autor) throws Exception;
    
    void atualizar(Autor autor) throws Exception;
    
    void excluir(int idAutor) throws Exception;
}
