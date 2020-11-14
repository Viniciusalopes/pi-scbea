/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Configuracao;
import interfaces.ICRUDConfiguracao;
import java.io.File;
import persistencia.PersistenciaConfiguracao;

/**
 *
 * @author vovostudio
 */
public class ControleConfiguracao implements ICRUDConfiguracao {

    private ICRUDConfiguracao persistencia = null;

    public ControleConfiguracao() throws Exception {
        persistencia = new PersistenciaConfiguracao();
    }

    @Override
    public Configuracao ler() throws Exception {
        return persistencia.ler();
    }

    @Override
    public void atualizar(Configuracao configuracao) throws Exception {

        if (configuracao.getLimiteDeLivros() < 0) {
            throw new Exception("O limite de livros emprestados deve ser maior ou igual a 0!");
        }

        if (configuracao.getDiasDeEmprestimo() < 0) {
            throw new Exception("A quantidade de dias de empréstimo deve ser maior ou igual a 0!");
        }

        if (configuracao.getValorMultaDiaria() < 0) {
            throw new Exception("O valor da multa diária deve ser maior ou igual a R$ 0,00!");
        }

        if (configuracao.getCaminhoBD().trim().length() == 0) {
            throw new Exception("O caminho da base de dados local não pode ficar em branco");
        }

        if (!new File(configuracao.getCaminhoBD()).exists()) {
            throw new Exception("O caminho da base de dados local é inválido!");
        }

        if (configuracao.getCaminhoBdServidor().trim().length() == 0) {
            throw new Exception("O caminho da base de dados remota não pode ficar em branco!");
        }

        if (!configuracao.getCaminhoBdServidor().contains(".") || !configuracao.getCaminhoBdServidor().contains(":")) {
            throw new Exception("O caminho da base de dados remota é inválido!");
        }

        persistencia.atualizar(configuracao);
    }

    @Override
    public String getCaminhoArquivoBd() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
