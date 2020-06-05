package persistencia;

import classes.ArquivoTXT;
import interfaces.IArquivoTXT;
import classes.Autor;
import controle.ControleArquivoTXT;
import enumeradores.EnumArquivosBd;
import interfaces.ICRUDAutor;
import java.util.ArrayList;
import static telas.Vai.CONFIGURACAO;
import utilidades.GeradorID;

/**
 *
 * @author João Pedro
 */
public class PersistenciaAutor implements ICRUDAutor {

    //atributos
    private ArquivoTXT arquivoTXT = null;
    private IArquivoTXT controleArquivoTXT = null;
    private ArrayList<Autor> colecao = null;
    private Autor autor = null;
    private ArrayList<String> linhas = null;

    public PersistenciaAutor() throws Exception {
        arquivoTXT = new ArquivoTXT(CONFIGURACAO.getCaminhoBdCliente(), EnumArquivosBd.AUTOR.getNomeArquivo());
        controleArquivoTXT = new ControleArquivoTXT(arquivoTXT);
    }

    @Override
    public ArrayList<Autor> listar() throws Exception {
        colecao = new ArrayList<>();  // Lista para retorno do listar
        linhas = controleArquivoTXT.lerArquivo(arquivoTXT); // Lê e escreve no arquivo respectivel na bd

        for (String linha : linhas) {
            String[] dados = linha.split(";");
            autor = new Autor(Integer.parseInt(dados[0]), dados[1]);
            colecao.add(autor);
        }
        return colecao;
    }

    @Override
    public Autor buscarPeloId(int idAutor) throws Exception {
        colecao = listar();
        for (Autor a : colecao) {
            if (a.getIdAutor() == idAutor) {
                return a;
            }
        }
        return null;
    }

    @Override
    public void incluir(Autor autor) throws Exception {
        linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
        autor.setIdAutor(GeradorID.getProximoID());
        linhas.add(autor.toString());
        arquivoTXT.setLinhas(linhas);
        controleArquivoTXT.escreverArquivo(arquivoTXT);
        GeradorID.confirmaID();
    }

    @Override
    public void alterar(Autor autor) throws Exception {

        //cria uma operação na memoria que tem finalidade substituir o arquivo em disco, esta é chamada de "novas linhas" que é um Arraylist STRING
        ArrayList<String> novaslinhas = new ArrayList<>();
        // percorrer as linhas do arquivo.
        linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
        // loop, chama linhas de linhas e compara se é igual a que eu quero digitar, pra evitar editar uma outra linha)
        for (String linha : linhas) {
            if (Integer.parseInt(linha.split(";")[0]) == autor.getIdAutor()) {
                novaslinhas.add(autor.toString());
            } else {
                novaslinhas.add(linha);
            }
        }
        arquivoTXT.setLinhas(novaslinhas);
        controleArquivoTXT.escreverArquivo(arquivoTXT);
    }

    @Override
    public void excluir(int idAutor) throws Exception {

        //cria uma operação na memoria que tem finalidade substituir o arquivo em disco, esta é chamada de "novas linhas" que é um Arraylist STRING
        ArrayList<String> novaslinhas = new ArrayList<>();
        // percorrer as linhas do arquivo.
        linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
        // loop, chama linhas de linhas e compara se é igual a que eu quero digitar, pra evitar editar uma outra linha)
        for (String linha : linhas) {
            if (Integer.parseInt(linha.split(";")[0]) != idAutor) {
                novaslinhas.add(linha);
            }
        }
        arquivoTXT.setLinhas(novaslinhas);
        controleArquivoTXT.escreverArquivo(arquivoTXT);

        // ler o arquivo
        //percorrer as linhas do arquivo procurando um autor com id igual ao id parametros
        // se achar exclui autor com o mesmo id
    }
}
