
package interfaces;

import classes.Autor;
import java.util.ArrayList;

/**
 *
 * @author João Pedro
 */
public interface ICRUDAutor {

    ArrayList<Autor> listar() throws Exception;
    
    Autor buscarPeloId(int id) throws Exception;

    void incluir(Autor autor) throws Exception;
    
    void alterar(Autor autor) throws Exception;
    
    void excluir(int idAutor) throws Exception;
}
