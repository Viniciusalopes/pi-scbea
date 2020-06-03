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
    private ArquivoTXT arquivoTXT;
    private IArquivoTXT controleArquivoTXT;

// Construtor padrão de persistência (direct caminho e nome arquivo)    
    public PersistenciaAreaConhecimento() throws Exception {
        arquivoTXT = new ArquivoTXT(Vai.CONFIGURACAO.getCaminhoBdCliente(), EnumArquivosBd.AREACONHECIMENTO.getNomeArquivo());
        controleArquivoTXT = new ControleArquivoTXT(arquivoTXT);
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
        //Criar Array list para linhas do arquivo, lendo o arquivo
        ArrayList<String> linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
        //Incluir objeto do parametro no array de linhas
        areaConhecimento.setIdAreaConhecimento(GeradorID.getProximoId());
        linhas.add(areaConhecimento.toString());
        //Atualizar as linhas do arquivoTXT
        arquivoTXT.setLinhas(linhas);
        //Escrever o aqruivoTXT novamente
        controleArquivoTXT.escreverArquivo(arquivoTXT);
        GeradorID.setProximoId();
    }

    @Override
    public void alterar(AreaConhecimento areaConhecimento) throws Exception {
        // Criar Array list para novas linhas do arquivo
        ArrayList<String> novasLinhas = new ArrayList<>();
        // ler arquivo
        ArrayList<String> linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
        // percorrer linhas do arquivo // if = incluir parametro na nova lista de linhas
        for (String linha : linhas) {
            if (Integer.parseInt(linha.split(";")[0]) == areaConhecimento.getIdAreaConhecimento()) {
                novasLinhas.add(areaConhecimento.toString());
            } else { // else incluir linha atual na nova lista de linhas
                novasLinhas.add(linha);
            }
        }
        // Atualizar as linhas do arquivoTXT
        arquivoTXT.setLinhas(novasLinhas);
        // Escrever o arquivoTXT novamente  
        controleArquivoTXT.escreverArquivo(arquivoTXT);
    }

    @Override
    public void excluir(int idAreaConhecimento) throws Exception {
        // Criar Array list para novas linhas do arquivo
        ArrayList<String> novasLinhas = new ArrayList<>();
        // ler arquivo
        ArrayList<String> linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
        // percorrer linhas do arquivo // if != incluir linha atual na nova lista de linhas
        for (String linha : linhas) {
            if (Integer.parseInt(linha.split(";")[0]) != idAreaConhecimento) {
                novasLinhas.add(linha);
            }
        }
        // Atualizar as linhas do arquivoTXT
        arquivoTXT.setLinhas(novasLinhas);
        // Escrever o arquivoTXT novamente  
        controleArquivoTXT.escreverArquivo(arquivoTXT);
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
}
