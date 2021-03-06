package telas;

import classes.Colaborador;
import classes.Configuracao;
import controle.ControleArquivoTXT;
import controle.ControleConfiguracao;
import enumeradores.EnumAmbiente;
import enumeradores.EnumArquivosBd;
import enumeradores.EnumCargo;
import enumeradores.EnumPerfil;
import enumeradores.EnumTipoStatus;
import interfaces.IArquivoTXT;
import java.util.ArrayList;
import utilidades.GeradorID;
import utilidades.Hash;
import utilidades.Menutencao;
import static utilidades.StringUtil.*;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vovostudio
 */
public class Vai {

    public static EnumAmbiente AMBIENTE = null;
    public static Configuracao CONFIGURACAO = null;
    public static Colaborador USUARIO = null;
    public static String BARRA = null;
    private static IArquivoTXT controleArquivoTXT = null;
    private static ArrayList<String> linhas = null;
    private static boolean manutencao = true;

    public static void main(String[] args) {

        try {
            BARRA = barra();    // Obtém a barra de divisão de diretórios de acordo com o S.O.
            CONFIGURACAO = new Configuracao();

            AMBIENTE = EnumAmbiente.CLIENTE; // Para criar o arquivo de configuração local
            controleArquivoTXT = new ControleArquivoTXT(
                    CONFIGURACAO.getCaminhoBdCliente(),
                    EnumArquivosBd.CONFIGURACAO.getNomeArquivo()
            );

            linhas = controleArquivoTXT.lerArquivo();
            if (linhas.size() == 0) {
                // Cria o primeiro arquivo de configuração, com os valores da classe Configuracao
                controleArquivoTXT.incluirLinha(CONFIGURACAO.toString());
            } else {
                CONFIGURACAO = new ControleConfiguracao().ler();
            }

            // Se for Ambiente CLIENTE
            //      Abre a comunicação
            //      Baixa todos os arquivos de dados do SERVIDOR
            //      Mescla com os arquivos de dados locais
            // Se for Ambiente SERVIDOR
            //      Abre a comunicação
            //      Evia todos os arquivos de locais para o SERVIDOR
            //      chama o serviço de mescar dados do servidor
            // LOCAL: ABD124
            // REMOTO: CEFG357
            // MESCLADO: ABCDEFG12357
            //
            // DESCOMENTAR PARA EXECUÇÃO EM PRODUÇÃO
            // Alterna o ambiente do banco de dados de acordo com o argumento da inicialização
            if (args.length == 0) {
                AMBIENTE = EnumAmbiente.CLIENTE;
            } else {
                AMBIENTE = (args[0].equals(EnumAmbiente.CLIENTE.toString())) ? EnumAmbiente.CLIENTE : EnumAmbiente.SERVIDOR;
                // Altera o IP e Porta do servidor por agumentos 
                CONFIGURACAO.setCaminhoBdServidor(args[0].equals(EnumAmbiente.SERVIDOR) ? args[1] : CONFIGURACAO.getCaminhoBdServidor());
            }
            // DESCOMENTAR PARA EXECUÇÃO EM PRODUÇÃO
            //
            //PARA TESTE COM IDE NETBEANS: COMENTAR QUANDO EM PRODUÇÃO
//            AMBIENTE = EnumAmbiente.SERVIDOR;
            //PARA TESTE COM IDE NETBEANS: COMENTAR QUANDO EM PRODUÇÃO

            System.out.println("AMBIENTE: " + AMBIENTE.toString());
            System.out.println("ENDERECO: " + CONFIGURACAO.getCaminhoBD());

            for (EnumArquivosBd arquivo : EnumArquivosBd.values()) {
                if (!arquivo.equals(EnumArquivosBd.CONFIGURACAO)) { // Não cria o arquivo de configuração porque ele já existe

                    // Cria os arquivos caso não existam (primeira execução)
                    controleArquivoTXT = new ControleArquivoTXT(CONFIGURACAO.getCaminhoBD(), arquivo.getNomeArquivo());

                    if (arquivo.equals(EnumArquivosBd.COLABORADOR)) {
                        // Cria o arquivo com os dados do proprietário se não existir (primeira excução)
                        linhas = controleArquivoTXT.lerArquivo();
                        if (linhas.size() == 0) {
                            // Cria o cadastro do proprietário da biblioteca como colaborador
                            controleArquivoTXT.incluirLinha(
                                    new Colaborador(
                                            GeradorID.getProximoID(),
                                            "Godofredo das Couves Verdejantes",
                                            EnumPerfil.ADMINISTRADOR,
                                            1,
                                            EnumCargo.PROPRIETARIO,
                                            "2712-GO",
                                            Hash.criptografar("123456", "SHA-256"),
                                            "godofredo@cou.ve",
                                            "(62) 9 8765-4321",
                                            EnumTipoStatus.ATIVO
                                    ).toString()
                            );
                            //manutencao = true;
                        }
                    }
                }
            }
            if (manutencao) {
                new Menutencao().importarCDD();
            }

            //System.out.println(Hash.criptografar("123456", "SHA-256"));
            TelaLogin frmLogin = new TelaLogin();
            frmLogin.setVisible(true);

        } catch (Exception e) {
            System.out.println("Opa!\n" + e.toString());
        }
    }
}
