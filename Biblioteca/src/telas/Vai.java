package telas;

import classes.ArquivoTXT;
import classes.Colaborador;
import classes.Configuracao;
import controle.ControleArquivoTXT;
import controle.ControleConfiguracao;
import enumeradores.EnumArquivosBd;
import interfaces.IArquivoTXT;
import java.util.ArrayList;
import utilidades.Hash;

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
    
    public static void main(String[] args) {

        try {

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

            arquivoTXT = new ArquivoTXT(CONFIGURACAO.getCaminhoBdCliente(), EnumArquivosBd.COLABORADOR.getNomeArquivo());
            controleArquivoTXT = new ControleArquivoTXT(arquivoTXT);
            linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
            if (linhas.size() == 0) {
                // Cria os colaboradores do grupo do pi para testes
                Colaborador colaborador = new Colaborador();
                
                colaborador.setIdColaborador(1);
                colaborador.setNomeColaborador("João Pedro");
                colaborador.setMatricula(1);
                colaborador.setSenha(Hash.criptografar("jp", "SHA-256"));
                linhas.add(colaborador.toString());
                
                colaborador.setIdColaborador(2);
                colaborador.setNomeColaborador("Lucas Araujo");
                colaborador.setMatricula(2);
                colaborador.setSenha(Hash.criptografar("lucas", "SHA-256"));
                linhas.add(colaborador.toString());
                
                colaborador.setIdColaborador(3);
                colaborador.setNomeColaborador("Marcos Job");
                colaborador.setMatricula(3);
                colaborador.setSenha(Hash.criptografar("job", "SHA-256"));
                linhas.add(colaborador.toString());
                
                
                colaborador.setIdColaborador(4);
                colaborador.setNomeColaborador("Vinicius Lopes");
                colaborador.setMatricula(4);
                colaborador.setSenha(Hash.criptografar("vovo", "SHA-256"));
                linhas.add(colaborador.toString());
                
                
                arquivoTXT.setLinhas(linhas);
                controleArquivoTXT.escreverArquivo(arquivoTXT);
            }
            //System.out.println(Hash.criptografar("123456", "SHA-256"));

            TelaLogin frmLogin = new TelaLogin();
            frmLogin.setVisible(true);

        } catch (Exception e) {
            System.out.println("Opa!\n" + e.toString());
        }
    }
}
