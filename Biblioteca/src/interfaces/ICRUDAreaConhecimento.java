package interfaces;

import classes.AreaConhecimento;
import java.util.ArrayList;

public interface ICRUDAreaConhecimento {
    ArrayList<AreaConhecimento> listar() throws Exception;
    void incluir(AreaConhecimento areaConhecimento) throws Exception;
    void alterar(AreaConhecimento areaConhecimento) throws Exception;
    void excluir(int idAreaConhecimento) throws Exception;
    AreaConhecimento buscarPeloId(int idAreaConhecimento) throws Exception;
}
