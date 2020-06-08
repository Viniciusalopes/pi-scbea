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
import classes.Livro;
import interfaces.ICRUDEditora;
import interfaces.ICRUDLivro;
import java.util.ArrayList;
import persistencia.PersistenciaEditora;
import utilidades.StringUtil;

/**
 *
 * @author lucas
 */
public class ControleEditora implements ICRUDEditora {

    private ICRUDEditora persistencia = null;
    private ArrayList<Editora> colecao = null;
    private ICRUDLivro controleLivro = null;

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
        validarPreenchimento(editora);
        validarDuplicidade(editora);
        persistencia.incluir(editora);
    }

    @Override
    public void alterar(Editora editora) throws Exception {//ok
        validarPreenchimento(editora);
        validarDuplicidade(editora);
        persistencia.alterar(editora);
    }

    @Override
    public void excluir(int idEditora) throws Exception {//ok
        colecao = listar();
        controleLivro = new ControleLivro();
        for (Livro livro : controleLivro.listar()) {
            if (livro.getEditora().getIdEditora() == idEditora) {
                throw new Exception("A editora não pode ser excluída porque está "
                        + "vinculada ao cadastro do livro " + livro.getIdLivro() + "-" + livro.getTitulo());
            }
        }
        persistencia.excluir(idEditora);
    }

    private void validarPreenchimento(Editora editora) throws Exception {
        if (editora.getNomeEditora().trim().length() < 2) {
            throw new Exception("Nome não pode ficar em branco!");
        }

        if (editora.getNomeEditora().toLowerCase().charAt(0) == editora.getNomeEditora().toLowerCase().charAt(1)) {
            throw new Exception("Nome com duas letras , e ainda iguais?? ta de brinks...");
        }

        if (!StringUtil.nomeEditoraValido(editora.getNomeEditora())) {
            throw new Exception("Nome da editora possui caracteres inválidos!");
        }
    }

    private void validarDuplicidade(Editora editora) throws Exception {
        colecao = listar();
        for (Editora e : colecao) {
            if (e.getNomeEditora().equalsIgnoreCase(editora.getNomeEditora())
                    && e.getIdEditora() != editora.getIdEditora()) {
                throw new Exception("Já existe uma editora cadastrada com este nome!");
            }
        }
    }
}
