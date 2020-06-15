/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import classes.Configuracao;
import controle.ControleArquivoTXT;
import enumeradores.EnumArquivosBd;
import interfaces.IArquivoTXT;
import interfaces.ICRUDConfiguracao;
import telas.Vai;
import static telas.Vai.CONFIGURACAO;

/**
 *
 * @author vovostudio
 */
public class PersistenciaConfiguracao implements ICRUDConfiguracao {

    private IArquivoTXT controleArquivoTXT = null;
    private Configuracao configuracao = null;

    public PersistenciaConfiguracao() throws Exception {
        try {

            controleArquivoTXT = new ControleArquivoTXT(
                    CONFIGURACAO.getCaminhoBdCliente(),
                    EnumArquivosBd.CONFIGURACAO.getNomeArquivo()
            );
        } catch (Exception e) {
            throw new Exception("Erro ao construir a classe PersistenciaConfiguracao!\n" + e.getMessage());
        }
    }

    @Override
    public Configuracao ler() throws Exception {
        try {
            String[] dados = controleArquivoTXT.lerArquivo().get(0).split(";");

            configuracao = new Configuracao();
            configuracao.setLimiteDeLivros(Integer.parseInt(dados[0]));
            configuracao.setDiasDeEmprestimo(Integer.parseInt(dados[1]));
            configuracao.setValorMultaDiaria(Float.parseFloat(dados[2].replace(",", ".")));
            configuracao.setCaminhoBdServidor(dados[4]);

            return configuracao;
        } catch (Exception e) {
            throw new Exception("Erro ao ler a configuração! (Persistência)\n" + e.getMessage());
        }
    }

    @Override
    public void atualizar(Configuracao configuracao) throws Exception {
        try {
            // Atualizo o arquivo de configuracao
            controleArquivoTXT.alterarLinha(controleArquivoTXT.lerArquivo().get(0), configuracao.toString());

            // Atualizo o arquivo padrão com a nova configuração
            ControleArquivoTXT controlePadrao = new ControleArquivoTXT(
                    new Configuracao().getCaminhoBdCliente(),
                    EnumArquivosBd.CONFIGURACAO.getNomeArquivo()
            );
            controlePadrao.incluirLinha(configuracao.toString());
            controlePadrao.escreverArquivo();

            // Atualizo a configuração global
            Vai.CONFIGURACAO = new Configuracao(configuracao);
        } catch (Exception e) {
            throw new Exception("Erro ao atualizar a configuração! (Persistência)\n" + e.getMessage());
        }
    }

    @Override
    public String getCaminhoArquivoBd() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
