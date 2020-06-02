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
import enumeradores.EnumAcao;
import interfaces.ICRUDEditora;
import java.util.ArrayList;
import persistencia.PersistenciaEditora;

/**
 *
 * @author lucas
 */
public class ControleEditora implements ICRUDEditora {
    private ICRUDEditora persistencia = null;
    private ArrayList<Editora> colecao = null ; 
    
    public ControleEditora() throws Exception{
        persistencia = new PersistenciaEditora();
        
    }
    @Override
    public ArrayList<Editora> listar() throws Exception {//ok
        return persistencia.listar();
        
    }
  
    @Override
    public void incluir(Editora editora) throws Exception {
        validarEditora(editora, EnumAcao.Incluir);
        persistencia.incluir(editora);
    }
    @Override
    public void alterar(Editora editora) throws Exception {//ok
        validarEditora(editora, EnumAcao.Editar);
        persistencia.alterar(editora);
    }

    @Override
    public void excluir(int idEditora) throws Exception {//ok
        //esta cadastrada essa editora  
        persistencia.excluir(idEditora);
        
    }

    private void validarEditora(Editora editora, EnumAcao enumAcao) throws  Exception{
        colecao = listar();
        for(Editora e : colecao){
            if(e.getNomeEditora() == editora.getNomeEditora()){
                if (enumAcao.equals(enumAcao.Incluir)
                        || enumAcao.equals(enumAcao.Editar)
                        && e.getIdEditora() != editora.getIdEditora()) {
                    throw new Exception("ja existe uma editora com esse nome!");                    
                }
            }
          
        }
        
    }

}
