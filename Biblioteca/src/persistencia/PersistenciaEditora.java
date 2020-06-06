/*
ICRUDObjeto
--------METODOS-----------------
+ listar() : ArrayList<Objeto>
+ incluir(objeto : Objeto) : void
+ alterar(objeto : Objeto) : void
+ excluir(idObjeto : int) : void
 */
package persistencia;

import classes.Editora;
import classes.Log;
import controle.ControleArquivoTXT;
import enumeradores.EnumAcao;
import enumeradores.EnumArquivosBd;
import enumeradores.EnumCadastro;
import interfaces.IArquivoTXT;
import interfaces.ICRUDEditora;
import java.util.ArrayList;
import telas.Vai;
import utilidades.GeradorID;

/**
 *
 * @author lucas
 */
public class PersistenciaEditora implements ICRUDEditora {

    private IArquivoTXT controleArquivoTXT = null;
    private ArrayList<Editora> colecao = null;
    private Editora editora = null;
    private ArrayList<String> linhas = null;

    public PersistenciaEditora() throws Exception {
        controleArquivoTXT = new ControleArquivoTXT(
                Vai.CONFIGURACAO.getCaminhoBdCliente(),
                EnumArquivosBd.EDITORA.getNomeArquivo()
        );
    }

    @Override
    public ArrayList<Editora> listar() throws Exception {
        colecao = new ArrayList<>();
        linhas = controleArquivoTXT.lerArquivo();
        for (String linha : linhas) {
            String[] dados = linha.split(";");
            editora = new Editora(Integer.parseInt(dados[0]), dados[1]);
            colecao.add(editora);
        }
        return colecao;
    }

    @Override
    public Editora buscarPeloId(int idEditora) throws Exception {
        listar();
        for (Editora e : colecao) {
            if (e.getIdEditora() == idEditora) {
                return e;
            }
        }
        return null;
    }

    @Override
    public void incluir(Editora editora) throws Exception {//OK
        editora.setIdEditora(GeradorID.getProximoID());
        controleArquivoTXT.incluirLinha(editora.toString());
    }

    @Override
    public void alterar(Editora editora) throws Exception {//OK
        linhas = controleArquivoTXT.lerArquivo();
        for (String linha : linhas) {
            if (Integer.parseInt(linha.split(";")[0]) == editora.getIdEditora()) {
                controleArquivoTXT.alterarLinha(linha, editora.toString());
                break;
            }
        }
            }

    @Override
    public void excluir(int idEditora) throws Exception {//OK
        linhas = controleArquivoTXT.lerArquivo();
        for (String linha : linhas) {
            if (Integer.parseInt(linha.split(";")[0]) == idEditora) {
                controleArquivoTXT.excluirLinha(linha);
                new Log().gravarLog(EnumAcao.Excluir, EnumCadastro.EDITORA, linha);
                break;
            }
        }
    }
}