package telas;

import classes.Colaborador;
import classes.Configuracao;
import controle.ControleArquivoTXT;
import controle.ControleConfiguracao;
import enumeradores.EnumArquivosBd;
import enumeradores.EnumCargo;
import enumeradores.EnumPerfil;
import enumeradores.EnumTipoStatus;
import interfaces.IArquivoTXT;
import java.util.ArrayList;
import utilidades.GeradorID;
import utilidades.Hash;
import static utilidades.StringUtil.*;
import utilidades.Menutencao;

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

    public static Configuracao CONFIGURACAO;
    public static Colaborador USUARIO;
    public static String BARRA;
    private static IArquivoTXT controleArquivoTXT = null;
    private static ArrayList<String> linhas = null;
    private static boolean manutencao = false;

    public static void main(String[] args) {

        try {

            BARRA = barra();    // Obtém a barra de divisão de diretórios de acordo com o S.O.
            CONFIGURACAO = new Configuracao();

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

            for (EnumArquivosBd arquivo : EnumArquivosBd.values()) {
                if (!arquivo.equals(EnumArquivosBd.CONFIGURACAO)) { // Não cria o arquivo de configuração porque ele já existe

                    // Cria os arquivos caso não existam (primeira execução)
                    controleArquivoTXT = new ControleArquivoTXT(CONFIGURACAO.getCaminhoBdCliente(), arquivo.getNomeArquivo());

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
                            manutencao = true;
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
