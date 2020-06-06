package persistencia;

import classes.AreaConhecimento;
import classes.Log;
import controle.ControleArquivoTXT;
import enumeradores.EnumAcao;
import enumeradores.EnumArquivosBd;
import enumeradores.EnumCadastro;
import interfaces.IArquivoTXT;
import interfaces.ICRUDAreaConhecimento;
import java.util.ArrayList;
import telas.Vai;
import utilidades.GeradorID;

public class PersistenciaAreaConhecimento implements ICRUDAreaConhecimento {

// Atributos
    private IArquivoTXT controleArquivoTXT = null;
    private ArrayList<AreaConhecimento> colecao = null;
    private AreaConhecimento areaConhecimento = null;
    private ArrayList<String> linhas = null;

// Construtor padrão de persistência (direct caminho e nome arquivo)    
    public PersistenciaAreaConhecimento() throws Exception {
        controleArquivoTXT = new ControleArquivoTXT(
                Vai.CONFIGURACAO.getCaminhoBdCliente(),
                EnumArquivosBd.AREACONHECIMENTO.getNomeArquivo()
        );
    }

    @Override
    public ArrayList<AreaConhecimento> listar() throws Exception {
        colecao = new ArrayList<>();
        linhas = controleArquivoTXT.lerArquivo();
        for (String linha : linhas) {
            String[] dados = linha.split(";");
            areaConhecimento = new AreaConhecimento(
                    Integer.parseInt(dados[0]),
                    Integer.parseInt(dados[1]),
                    dados[2]);

            colecao.add(areaConhecimento);
        }
        return (colecao);
    }

    @Override
    public AreaConhecimento buscarPeloId(int idAreaConhecimento) throws Exception {
        listar();
        for (AreaConhecimento a : colecao) {
            if (a.getIdAreaConhecimento() == idAreaConhecimento) {
                return a;
            }
        }
        return null;
    }

    @Override
    public void incluir(AreaConhecimento areaConhecimento) throws Exception {
        areaConhecimento.setIdAreaConhecimento(GeradorID.getProximoID());       //Obter o próximo ID único
        controleArquivoTXT.incluirLinha(areaConhecimento.toString());           //Gravar a linha no arquivoTXT
    }

    @Override
    public void alterar(AreaConhecimento areaConhecimento) throws Exception {
        linhas = controleArquivoTXT.lerArquivo();                               // ler linhas do arquivo
        for (String linha : linhas) {                                           // percorrer linhas do arquivo // if = incluir parametro na nova lista de linhas
            if (Integer.parseInt(linha.split(";")[0]) == areaConhecimento.getIdAreaConhecimento()) {
                controleArquivoTXT.alterarLinha(linha, areaConhecimento.toString());
                break;
            }
        }
    }

    @Override
    public void excluir(int idAreaConhecimento) throws Exception {
        linhas = controleArquivoTXT.lerArquivo();                               // ler arquivo
        for (String linha : linhas) {                                           // percorrer linhas do arquivo // if != incluir linha atual na nova lista de linhas
            if (Integer.parseInt(linha.split(";")[0]) == idAreaConhecimento) {
                controleArquivoTXT.excluirLinha(linha);
                new Log().gravarLog(EnumAcao.Excluir, EnumCadastro.AREACONHECIMENTO, linha);
                break;
            }
        }
    }
}
