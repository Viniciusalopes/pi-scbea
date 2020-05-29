/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Configuracao;
import interfaces.ICRUDConfiguracao;
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
        persistencia.atualizar(configuracao);
    }

    @Override
    public String getCaminhoArquivoBd() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean validar(Configuracao configuracao) throws Exception{
        return true;
    }
}
