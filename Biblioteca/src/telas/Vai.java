package telas;

import classes.ArquivoTXT;
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
import utilidades.StringUtil;

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

    public static void main(String[] args) {

        try {

            BARRA = StringUtil.barra();
            CONFIGURACAO = new Configuracao();

            ArquivoTXT arquivoTXT = new ArquivoTXT(CONFIGURACAO.getCaminhoBdCliente(), EnumArquivosBd.CONFIGURACAO.getNomeArquivo());
            IArquivoTXT controleArquivoTXT = new ControleArquivoTXT(arquivoTXT);
            ArrayList<String> linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
            if (linhas.size() == 0) {
                // Cria o primeiro arquivo de configuração, com os valores da classe Configuracao
                linhas.add(CONFIGURACAO.toString());
                arquivoTXT.setLinhas(linhas);
                controleArquivoTXT.escreverArquivo(arquivoTXT);
            } else {
                CONFIGURACAO = new ControleConfiguracao().ler();
            }

            for (EnumArquivosBd arquivo : EnumArquivosBd.values()) {
                if (!arquivo.equals(EnumArquivosBd.CONFIGURACAO)) { // Não cria os arquivo de configuração porque ele já existe

                    // Cria os arquivos caso não existam (primeira execução)
                    arquivoTXT = new ArquivoTXT(CONFIGURACAO.getCaminhoBdCliente(), arquivo.getNomeArquivo());
                    controleArquivoTXT = new ControleArquivoTXT(arquivoTXT);

                    if (arquivo.equals(EnumArquivosBd.COLABORADOR)) {
                        // Cria o arquivo com os dados do proprietário se não existir (primeira excução)
                        linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
                        if (linhas.size() == 0) {
                            // Cria os colaboradores do grupo do pi para testes
                            Colaborador colaborador = new Colaborador(
                                    GeradorID.getProximoId(),
                                    "Godofredo das Couves Verdejantes",
                                    EnumPerfil.ADMINISTRADOR,
                                    1,
                                    EnumCargo.PROPRIETARIO,
                                    "2712-GO",
                                    Hash.criptografar("123456", "SHA-256"),
                                    "godofredo@cou.ve",
                                    "(62) 9 8765-4321",
                                    EnumTipoStatus.ATIVO
                            );
                            linhas.add(colaborador.toString());
                            arquivoTXT.setLinhas(linhas);
                            controleArquivoTXT.escreverArquivo(arquivoTXT);
                            GeradorID.setProximoId();
                        }
                    }
                }
            }

            //System.out.println(Hash.criptografar("123456", "SHA-256"));
            TelaLogin frmLogin = new TelaLogin();
            frmLogin.setVisible(true);

        } catch (Exception e) {
            System.out.println("Opa!\n" + e.toString());
        }
    }
}
