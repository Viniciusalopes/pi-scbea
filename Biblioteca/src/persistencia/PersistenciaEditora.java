/*
ICRUDObjeto
--------METODOS-----------------
+ listar() : ArrayList<Objeto>
+ incluir(objeto : Objeto) : void
+ alterar(objeto : Objeto) : void
+ excluir(idObjeto : int) : void
 */
package persistencia;

import classes.ArquivoTXT;
import classes.Editora;
import controle.ControleArquivoTXT;
import enumeradores.EnumArquivosBd;
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

    private ArquivoTXT arquivoTXT = null;
    private IArquivoTXT controleArquivoTXT = null;
    private ArrayList<Editora> colecao = null;
    private Editora editora = null;
    private ArrayList<String> linhas = null;

    public PersistenciaEditora() throws Exception {
        arquivoTXT = new ArquivoTXT(Vai.CONFIGURACAO.getCaminhoBdCliente(),
                EnumArquivosBd.EDITORA.getNomeArquivo());
        controleArquivoTXT = new ControleArquivoTXT(arquivoTXT);
    }

    @Override
    public ArrayList<Editora> listar() throws Exception {
        colecao = new ArrayList<>();
        linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
        for (String linha : linhas) {
            String[] dados = linha.split(";");
            editora = new Editora(Integer.parseInt(dados[0]), dados[1]);
            colecao.add(editora);
        }
        return colecao;
    }

    @Override
    public Editora buscarPeloID(int idEditora) throws Exception {
        colecao = listar();
        for (Editora e : colecao) {
            if (e.getIdEditora() == idEditora) {
                return e;
            }
        }
        return null;
    }

    @Override
    public void incluir(Editora editora) throws Exception {//OK
        linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
        editora.setIdEditora(GeradorID.getProximoID());
        linhas.add(editora.toString());
        arquivoTXT.setLinhas(linhas);
        controleArquivoTXT.escreverArquivo(arquivoTXT);
        GeradorID.confirmaID();

    }

    @Override
    public void alterar(Editora editora) throws Exception {//OK
       ArrayList<String> novasLinhas = new ArrayList<>();
       linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
        for (String linha : linhas ) {
            if (Integer.parseInt(linha.split(";")[0])== editora.getIdEditora()) {
            novasLinhas.add(editora.toString());
            }else {
                novasLinhas.add(linha);
            }
        }
        arquivoTXT.setLinhas(linhas);
        controleArquivoTXT.escreverArquivo(arquivoTXT);
    }

    @Override
    public void excluir(int idEditora) throws Exception {//OK
        ArrayList<String> novasLinhas = new ArrayList<>();
        linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
        for (String l : linhas) {
            if (Integer.parseInt(l.split(";")[0]) != idEditora) {
                novasLinhas.add(l);
            }
        }
        arquivoTXT.setLinhas(novasLinhas);
        controleArquivoTXT.escreverArquivo(arquivoTXT);
    }

}