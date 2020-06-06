/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import controle.ControleArquivoTXT;
import enumeradores.EnumAcao;
import enumeradores.EnumArquivosBd;
import enumeradores.EnumCadastro;
import interfaces.IArquivoTXT;
import java.util.Date;
import telas.Vai;

/**
 *
 * @author vovostudio
 */
public class Log {

    private Date dataExclusao = null;
    private IArquivoTXT controleArquivoTXT = null;

    public Log() throws Exception {
        controleArquivoTXT = new ControleArquivoTXT(
                Vai.CONFIGURACAO.getCaminhoBdCliente(),
                EnumArquivosBd.LOG.getNomeArquivo()
        );
    }

    public void gravarLog(EnumAcao acao, EnumCadastro cadastro, String registro) throws Exception {
        controleArquivoTXT.incluirLinha(
                String.format("%s|%d|%d|%d|%s",
                        new Date(),
                        Vai.USUARIO.getIdColaborador(),
                        acao.ordinal(),
                        cadastro.ordinal(),
                        registro.replace("|", "_")
                )
        );
    }
}
