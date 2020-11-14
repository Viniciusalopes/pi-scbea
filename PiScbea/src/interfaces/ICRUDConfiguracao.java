/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import classes.Configuracao;

/**
 *
 * @author vovostudio
 */
public interface ICRUDConfiguracao {

    Configuracao ler() throws Exception;

    void atualizar(Configuracao configuracao) throws Exception;

    String getCaminhoArquivoBd() throws Exception;

}
