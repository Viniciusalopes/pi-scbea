package persistencia;

import interfaces.IArquivoTXT;
import classes.Autor;
import controle.ControleArquivoTXT;
import controle.ControleLog;
import enumeradores.EnumAcao;
import enumeradores.EnumArquivosBd;
import enumeradores.EnumCadastro;
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
    private IArquivoTXT controleArquivoTXT = null;
    private ArrayList<Autor> colecao = null;
    private Autor autor = null;
    private ArrayList<String> linhas = null;

    public PersistenciaAutor() throws Exception {
        controleArquivoTXT = new ControleArquivoTXT(CONFIGURACAO.getCaminhoBdCliente(), EnumArquivosBd.AUTOR.getNomeArquivo());
    }

    @Override
    public ArrayList<Autor> listar() throws Exception {
        colecao = new ArrayList<>();  // Lista para retorno do listar
        linhas = controleArquivoTXT.lerArquivo(); // Lê e escreve no arquivo respectivel na bd

        for (String linha : linhas) {
            String[] dados = linha.split(";");
            autor = new Autor(Integer.parseInt(dados[0]), dados[1]);
            colecao.add(autor);
        }
        return colecao;
    }

    @Override
    public Autor buscarPeloId(int idAutor) throws Exception {
        listar();
        for (Autor a : colecao) {
            if (a.getIdAutor() == idAutor) {
                return a;
            }
        }
        return null;
    }

    @Override
    public void incluir(Autor autor) throws Exception {
        autor.setIdAutor(GeradorID.getProximoID());
        controleArquivoTXT.incluirLinha(autor.toString());
    }

    @Override
    public void alterar(Autor autor) throws Exception {
        // obter as linhas do arquivo.
        linhas = controleArquivoTXT.lerArquivo();
        // loop, chama linhas de linhas e compara se o ID é igual a que eu quero digitar, pra evitar editar uma outra linha)
        for (String linha : linhas) {
            if (Integer.parseInt(linha.split(";")[0]) == autor.getIdAutor()) {
                controleArquivoTXT.alterarLinha(linha, autor.toString());
                break;
            }
        }
    }

    @Override
    public void excluir(int idAutor) throws Exception {
        // obter as linhas do arquivo.
        linhas = controleArquivoTXT.lerArquivo();
        // loop, chama linhas de linhas e compara se o ID é igual a que eu quero digitar, pra evitar excluirr uma outra linha)
        for (String linha : linhas) {
            if (Integer.parseInt(linha.split(";")[0]) == idAutor) {
                controleArquivoTXT.excluirLinha(linha);
                new ControleLog().incluir(EnumAcao.Excluir, EnumCadastro.AUTOR, linha);
                break;
            }
        }
    }
}
