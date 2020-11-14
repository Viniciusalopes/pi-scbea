package interfaces;

import classes.AreaConhecimento;
import java.util.ArrayList;

public interface ICRUDAreaConhecimento {

//------------------------------------------------------------------------------
//ASSINATURA DOS MÉTODOS PARA IMPLEMENTAÇÃO EM CONTROLE E PERSISTENCIA 
//------------------------------------------------------------------------------ 

    ArrayList<AreaConhecimento> listar() throws Exception;

    AreaConhecimento buscarPeloId(int idAreaConhecimento) throws Exception;

    void incluir(AreaConhecimento areaConhecimento) throws Exception;

    void alterar(AreaConhecimento areaConhecimento) throws Exception;

    void excluir(int idAreaConhecimento) throws Exception;

}
