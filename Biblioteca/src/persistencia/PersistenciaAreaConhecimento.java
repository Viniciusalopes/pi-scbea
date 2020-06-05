package persistencia;

import classes.AreaConhecimento;
import classes.ArquivoTXT;
import controle.ControleArquivoTXT;
import enumeradores.EnumArquivosBd;
import interfaces.IArquivoTXT;
import interfaces.ICRUDAreaConhecimento;
import java.util.ArrayList;
import telas.Vai;
import utilidades.GeradorID;

public class PersistenciaAreaConhecimento implements ICRUDAreaConhecimento {

// Atributos
    private ArquivoTXT arquivoTXT = null;
    private IArquivoTXT controleArquivoTXT = null;
    private ArrayList<AreaConhecimento> colecao = null;
    private AreaConhecimento areaConhecimento = null;
    private ArrayList<String> linhas = null;

// Construtor padrão de persistência (direct caminho e nome arquivo)    
    public PersistenciaAreaConhecimento() throws Exception {
        arquivoTXT = new ArquivoTXT(Vai.CONFIGURACAO.getCaminhoBdCliente(),
                EnumArquivosBd.AREACONHECIMENTO.getNomeArquivo());
        controleArquivoTXT = new ControleArquivoTXT(arquivoTXT);
    }

    @Override
    public AreaConhecimento buscarPeloId(int idAreaConhecimento) throws Exception {

        ArrayList<AreaConhecimento> colecao = listar();
        for (AreaConhecimento a : colecao) {
            if (a.getIdAreaConhecimento() == idAreaConhecimento) {
                return a;
            }
        }
        return null;

    }

    @Override
    public ArrayList<AreaConhecimento> listar() throws Exception {
        ArrayList<AreaConhecimento> areasConhecimento = new ArrayList<>();
        ArrayList<String> linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
        for (String linha : linhas) {
            String[] dados = linha.split(";");
            AreaConhecimento areaConhecimento = new AreaConhecimento(Integer.parseInt(dados[0]),
                    Integer.parseInt(dados[1]), dados[2]);
            areasConhecimento.add(areaConhecimento);
        }
        return (areasConhecimento);
    }

    @Override
    public void incluir(AreaConhecimento areaConhecimento) throws Exception {
        linhas = controleArquivoTXT.lerArquivo(arquivoTXT);                     //Criar Array list para linhas do arquivo, lendo o arquivo
        areaConhecimento.setIdAreaConhecimento(GeradorID.getProximoID());       //Incluir objeto do parametro no array de linhas
        linhas.add(areaConhecimento.toString());                                //Atualizar as linhas do arquivoTXT
        arquivoTXT.setLinhas(linhas);                                           //Escrever o aqruivoTXT novamente
        controleArquivoTXT.escreverArquivo(arquivoTXT);
        GeradorID.confirmaID();
    }

    @Override
    public void alterar(AreaConhecimento areaConhecimento) throws Exception {
        ArrayList<String> novasLinhas = new ArrayList<>();                      // Criar Array list para novas linhas do arquivo
        linhas = controleArquivoTXT.lerArquivo(arquivoTXT);                     // ler arquivo
        for (String linha : linhas) {                                           // percorrer linhas do arquivo // if = incluir parametro na nova lista de linhas
            if (Integer.parseInt(linha.split(";")[0])
                    == areaConhecimento.getIdAreaConhecimento()) {
                novasLinhas.add(areaConhecimento.toString());
            } else {
                novasLinhas.add(linha);                                         // incluir linha atual na nova lista de linhas
            }
        }
        arquivoTXT.setLinhas(novasLinhas);                                      // Atualizar as linhas do arquivoTXT
        controleArquivoTXT.escreverArquivo(arquivoTXT);                         // Escrever o arquivoTXT novamente  
    }

    @Override
    public void excluir(int idAreaConhecimento) throws Exception {
        ArrayList<String> novasLinhas = new ArrayList<>();                      // Criar Array list para novas linhas do arquivo
        linhas = controleArquivoTXT.lerArquivo(arquivoTXT);                     // ler arquivo
        for (String linha : linhas) {                                           // percorrer linhas do arquivo // if != incluir linha atual na nova lista de linhas
            if (Integer.parseInt(linha.split(";")[0])
                    != idAreaConhecimento) {
                novasLinhas.add(linha);
            }
        }
        arquivoTXT.setLinhas(novasLinhas);                                      // Atualizar as linhas do arquivoTXT
        controleArquivoTXT.escreverArquivo(arquivoTXT);                         // Escrever o arquivoTXT novamente  
    }

}
