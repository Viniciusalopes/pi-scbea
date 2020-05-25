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

        return configuracao;
    }

    @Override
    public void atualizar(Configuracao configuracao) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCaminhoArquivoBd() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
