
package interfaces;

import classes.Autor;
import java.util.ArrayList;

/**
 *
 * @author João Pedro
 */
public interface ICRUDAutor {

    ArrayList<Autor> listar() throws Exception;
    
    Autor getAutorPeloId(int id) throws Exception;

    void incluir(Autor autor) throws Exception;
    
    void atualizar(Autor autor) throws Exception;
    
    void excluir(int idAutor) throws Exception;
}
