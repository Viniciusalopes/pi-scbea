package persistencia;

import classes.ArquivoTXT;
import classes.Autor;
import controle.ControleArquivoTXT;
import enumeradores.EnumArquivosBd;
import interfaces.IArquivoTXT;
import interfaces.ICRUDAutor;
import java.util.ArrayList;
import static telas.Vai.CONFIGURACAO;

/**
 *
 * @author vovostudio
 */
public class PersistenciaAutor implements ICRUDAutor {

    private ArquivoTXT arquivoTXT;
    private IArquivoTXT controleArquivoTXT;

    public PersistenciaAutor() throws Exception {
        arquivoTXT = new ArquivoTXT(CONFIGURACAO.getCaminhoBdCliente(), EnumArquivosBd.AUTOR.getNomeArquivo());
        controleArquivoTXT = new ControleArquivoTXT(arquivoTXT);
    }

    @Override
    public ArrayList<Autor> listar() throws Exception {
        ArrayList<Autor> autores = new ArrayList<>();  // Lista para retorno
        ArrayList<String> linhas = controleArquivoTXT.lerArquivo(arquivoTXT); // Lista de linhas do arquivo

        for (String linha : linhas) {
            String[] dados = linha.split(";");
            Autor autor = new Autor(Integer.parseInt(dados[0]), dados[1]);
            autores.add(autor);
        }
        return autores;
    }

    @Override
    public void incluir(Autor autor) throws Exception {
        ArrayList<String> linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
        linhas.add(autor.toString());
        controleArquivoTXT.escreverArquivo(arquivoTXT);
    }

    @Override
    public void atualizar(Autor autor) throws Exception {
        //cria uma operação na memoria que tem finalidade substituir o arquivo em disco, esta é chamada de "novas linhas" que é um Arraylist STRING
          ArrayList<String> novaslinhas = new ArrayList<>();
        // percorrer as linhas do arquivo.
        ArrayList<String> linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
        // loop, chama linhas de linhas e compara se é igual a que eu quero digitar, pra evitar editar uma outra linha)
        for (String linha : linhas) {
            int id = Integer.parseInt(linha.split(";")[0]); //pra comparar apenas o 1 elemento do array que é o ID o split foi usado.
            if (id == autor.getIdAutor()) {
                linhas.add(autor.toString());
            } else {
                linhas.add(linha);
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
        ArrayList<String> linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
        // loop, chama linhas de linhas e compara se é igual a que eu quero digitar, pra evitar editar uma outra linha)
       //.
        for (String linha : linhas) {
            int id = Integer.parseInt(linha.split(";")[0]); //pra comparar apenas o 1 elemento do array que é o ID o split foi usado.
            if (id != idAutor) {
                linhas.add(linha);
            }
            
        }
        arquivoTXT.setLinhas(novaslinhas);
        controleArquivoTXT.escreverArquivo(arquivoTXT);
    }
}
