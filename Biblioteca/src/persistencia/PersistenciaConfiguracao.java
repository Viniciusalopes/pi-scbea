/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import classes.ArquivoTXT;
import classes.Configuracao;
import controle.ControleArquivoTXT;
import enumeradores.EnumArquivosBd;
import interfaces.IArquivoTXT;
import interfaces.ICRUDConfiguracao;
import java.util.ArrayList;
import telas.Vai;
import static telas.Vai.CONFIGURACAO;

/**
 *
 * @author vovostudio
 */
public class PersistenciaConfiguracao implements ICRUDConfiguracao {

    private ArquivoTXT arquivoTXT = null;
    private IArquivoTXT controleArquivoTXT = null;

    public PersistenciaConfiguracao() throws Exception {
        arquivoTXT = new ArquivoTXT(CONFIGURACAO.getCaminhoBdCliente(), EnumArquivosBd.CONFIGURACAO.getNomeArquivo());
        controleArquivoTXT = new ControleArquivoTXT(arquivoTXT);
    }

    @Override
    public Configuracao ler() throws Exception {
        arquivoTXT.setLinhas(controleArquivoTXT.lerArquivo(arquivoTXT));
        String[] dados = arquivoTXT.getLinhas().get(0).split(";");

        Configuracao configuracao = new Configuracao();
        configuracao.setLimiteDeLivros(Integer.parseInt(dados[0]));
        configuracao.setDiasDeEmprestimo(Integer.parseInt(dados[1]));
        configuracao.setValorMultaDiaria(Float.parseFloat(dados[2].replace(",", ".")));
        configuracao.setCaminhoBdServidor(dados[4]);
        configuracao.setLerId(dados[5].equals("0") ? false : true);
        return configuracao;
    }

    @Override
    public void atualizar(Configuracao configuracao) throws Exception {
        // Linhas do arquivo de configuração
        ArrayList<String> linhas = new ArrayList<>();
        linhas.add(configuracao.toString());

        // Atualizo o arquivo padrão com a nova configuração
        Configuracao padrao = new Configuracao();
        arquivoTXT = new ArquivoTXT(padrao.getCaminhoBdCliente(), EnumArquivosBd.CONFIGURACAO.getNomeArquivo());
        arquivoTXT.setLinhas(linhas);
        controleArquivoTXT.escreverArquivo(arquivoTXT);

        // Crio o novo arquivo de configuração caso seja em outro local
        if (!padrao.getCaminhoBdCliente().equals(configuracao.getCaminhoBdCliente())) {
            arquivoTXT = new ArquivoTXT(configuracao.getCaminhoBdCliente(), EnumArquivosBd.CONFIGURACAO.getNomeArquivo());
            arquivoTXT.setLinhas(linhas);
            controleArquivoTXT.escreverArquivo(arquivoTXT);
        }

        // Atualizo a configuração global
        Vai.CONFIGURACAO = new Configuracao(configuracao);
    }

    @Override
    public String getCaminhoArquivoBd() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
