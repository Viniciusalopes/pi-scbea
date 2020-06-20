/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.principal;

import br.service.ComunicadorTCP;
import controle.ControleArquivoTXT;
import enumeradores.EnumAcao;
import enumeradores.EnumArquivosBd;
import interfaces.IArquivoTXT;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author vovostudio
 */
public class TCPServer {

    private static EnumAcao acao = null;
    private static String arquivo = "";
    private static String comando = "";
    private static String retorno = "";
    private static IArquivoTXT controleArquivoTXT = null;
    private static ComunicadorTCP comunicacao = null;
    private static String diretorioBD = "BibliotecaBdServer";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        System.out.println("[" + new Date() + "] Servidor Socket iniciado.");

        // Cria todos os arquivos
        for (EnumArquivosBd arquivoBd : EnumArquivosBd.values()) {
            controleArquivoTXT = new ControleArquivoTXT(diretorioBD, arquivoBd.getNomeArquivo());
        }

        acao = EnumAcao.IniciarServidor;

        while (!acao.equals(EnumAcao.EncerrarServidor)) {
            comunicacao = new ComunicadorTCP(6789);
            System.out.println("[" + new Date() + "] Nova comunicação iniciada.");
            comando = comunicacao.receberMensagem();
            System.out.println("[" + new Date() + "] Comando recebido: " + comando);
            if (acao.equals(EnumAcao.EncerrarServidor)) {
                comunicacao.getServerSocket().close();
                System.out.println("[" + new Date() + "] Servidor Socket encerrado.");
            } else {
                retorno = executarComando(comando);
                System.out.println("[" + new Date() + "] RETORNO: " + retorno);
                comunicacao.enviarMensagem(retorno);
            }
            comunicacao.getServerSocket().close();
        }

    }

    private static String executarComando(String mensagem) throws Exception {
        String[] partes = mensagem.split("_");
        acao = EnumAcao.valueOf(partes[0]);
        arquivo = partes[1];
        String retorno = "";

        controleArquivoTXT = new ControleArquivoTXT(diretorioBD, arquivo);

        switch (acao.toString()) {
            case "Incluir":
                controleArquivoTXT.incluirLinha(partes[2]);
                retorno = "Linha incluída com sucesso! ID: " + partes[2].split(";")[0];
                break;
            case "Editar":
                controleArquivoTXT.alterarLinha(partes[2], partes[3]);
                retorno = "Linha alterada com sucesso! ID: " + partes[2].split(";")[0];
                break;
            case "Excluir":
                controleArquivoTXT.excluirLinha(partes[2]);
                retorno = "Linha excluída com sucesso! ID: " + partes[2].split(";")[0];
                break;
            case "LerArquivo":
                ArrayList<String> linhas = controleArquivoTXT.lerArquivo();
                for (String linha : linhas) {
                    retorno += linha + "_";
                }
                break;
        }
        return retorno;
    }
}
