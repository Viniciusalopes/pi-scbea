/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void excluir(int idAutor) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
