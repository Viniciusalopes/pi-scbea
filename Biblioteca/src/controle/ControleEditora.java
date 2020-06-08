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
import persistencia.PersistenciaEditora;

/**
 *
 * @author lucas
 */
public class ControleEditora implements ICRUDEditora {
     private ICRUDEditora persistencia ;
     private ArrayList<Editora> colecao;

    public ControleEditora() throws Exception {
        persistencia = new PersistenciaEditora();
        colecao = new ArrayList<>();
    }
     
     
    
    @Override
    public ArrayList<Editora> listar() throws Exception {//ok
    return persistencia.listar();
    
    }

    @Override
    public Editora buscarPeloId(int idEditora) throws Exception {
        return persistencia.buscarPeloId(idEditora);
    }

    @Override
    public void incluir(Editora editora) throws Exception {//ok
         if (editora.getNomeEditora().trim().length() < 2) {
            throw new Exception("Nome não pode ficar em branco!");
        }

        if (editora.getNomeEditora().toLowerCase().charAt(0) == editora.getNomeEditora().toLowerCase().charAt(1)) {
            throw new Exception("Nome com duas letras , e ainda iguais?? ta de brinks...");
        }
         colecao = persistencia.listar();
        for (Editora objAutor : colecao) {
            if (objAutor.getNomeEditora().equals(editora.getNomeEditora())) {
                throw new Exception("Já existe um autor cadastrado com este nome!");
            }
        }

        persistencia.incluir(editora);
    }

    @Override
    public void alterar(Editora editora) throws Exception {//ok
       colecao = listar(); 
       persistencia.alterar(editora);
    }

    @Override
    public void excluir(int idEditora) throws Exception {//ok
      
    }

}
