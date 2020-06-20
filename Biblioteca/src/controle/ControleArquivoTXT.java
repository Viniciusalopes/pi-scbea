/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.ArquivoTXT;
import enumeradores.EnumAcao;
import enumeradores.EnumAmbiente;
import interfaces.IArquivoTXT;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import telas.Vai;
import persistencia.PersistenciaComunicadorTCP;

/**
 *
 * @author vovostudio
 */
public class ControleArquivoTXT implements IArquivoTXT {

    private ArrayList<String> linhas = null;
    private ArquivoTXT arquivoTXT = null;
    private PersistenciaComunicadorTCP comunicacao = null;
    private String caminho = "";
    private String arquivo = "";
    private String[] dadosServidor = null;

    public ControleArquivoTXT() {

    }

    public ControleArquivoTXT(String caminho, String arquivo) throws Exception {
        try {
            if (Vai.AMBIENTE.equals(EnumAmbiente.CLIENTE)) {
                File dir = new File(caminho);
                if (!dir.exists()) {
                    dir.mkdirs();
                    System.out.println("Criando diretório [" + dir.getAbsolutePath() + "]...");
                }

                File file = new File(caminho + "/" + arquivo);
                if (!file.exists()) {
                    file.createNewFile();
                    System.out.println("Criando arquivo [" + file.getAbsolutePath() + "]...");
                }
                arquivoTXT = new ArquivoTXT(caminho, arquivo);
            } else {
                this.caminho = caminho;
                this.arquivo = arquivo;
                dadosServidor = caminho.split(":");
                arquivoTXT = new ArquivoTXT(caminho, arquivo);

            }
        } catch (Exception e) {
            throw new Exception("Erro ao construir a classe ControleArquivoTXT[" + arquivo + "]!\n" + e.getMessage());
        }
    }

    @Override
    public void incluirLinha(String linha) throws Exception {
        try {
            if (Vai.AMBIENTE.equals(EnumAmbiente.CLIENTE)) {
                linhas = lerArquivo();
                linhas.add(linha);
                arquivoTXT.setLinhas(linhas);
                escreverArquivo();
            } else {
                comunicacao = new PersistenciaComunicadorTCP(dadosServidor[0], Integer.parseInt(dadosServidor[1]));
                comunicacao.enviarMensagem(
                        EnumAcao.Incluir.toString() + "_" + arquivo + "_" + linha
                );
                comunicacao.FecharConexao();
            }
        } catch (Exception e) {
            throw new Exception("Erro ao incluir linha no arquivo [" + arquivoTXT.getArquivo() + "]! (ControleArquivoTXT)\n" + e.getMessage());
        }
    }

    @Override
    public void alterarLinha(String linhaAntes, String linhaDepois) throws Exception {
        try {
            if (Vai.AMBIENTE.equals(EnumAmbiente.CLIENTE)) {
                linhas = lerArquivo();
                for (String linha : linhas) {
                    if (linha.equals(linhaAntes)) {
                        linhas.set(linhas.indexOf(linhaAntes), linhaDepois);
                        arquivoTXT.setLinhas(linhas);
                        break;
                    }
                }
                escreverArquivo();
            } else {
                comunicacao = new PersistenciaComunicadorTCP(dadosServidor[0], Integer.parseInt(dadosServidor[1]));
                comunicacao.enviarMensagem(EnumAcao.Editar.toString() + "_" + arquivo + "_" + linhaAntes + "_" + linhaDepois);
                comunicacao.FecharConexao();
            }
        } catch (Exception e) {
            throw new Exception("Erro ao alterar linha do arquivo [" + arquivoTXT.getArquivo() + "]! (ControleArquivoTXT)\n" + e.getMessage());
        }
    }

    @Override
    public void excluirLinha(String linha) throws Exception {
        try {
            if (Vai.AMBIENTE.equals(EnumAmbiente.CLIENTE)) {
                linhas = lerArquivo();
                for (String l : linhas) {
                    if (l.equals(linha)) {
                        linhas.remove(linha);
                        break;
                    }
                }
            } else {
                comunicacao = new PersistenciaComunicadorTCP(dadosServidor[0], Integer.parseInt(dadosServidor[1]));
                comunicacao.enviarMensagem(
                        EnumAcao.Excluir.toString() + "_" + arquivo + "_" + linha);
                comunicacao.FecharConexao();
            }
        } catch (Exception e) {
            throw new Exception("Erro ao excluir linha do arquivo [" + arquivoTXT.getArquivo() + "]! (ControleArquivoTXT)\n" + e.getMessage());
        }
    }

    @Override
    public ArrayList<String> lerArquivo() throws Exception {
        try {
            if (Vai.AMBIENTE.equals(EnumAmbiente.CLIENTE)) {
                linhas = new ArrayList<String>();
                FileReader fr = new FileReader(arquivoTXT.getCaminho() + "/" + arquivoTXT.getArquivo());
                BufferedReader br = new BufferedReader(fr);
                String linha = "";
                while ((linha = br.readLine()) != null) {
                    linhas.add(linha);
                }
                br.close();
                return linhas;

            } else {
                String mensagem = EnumAcao.LerArquivo.toString() + "_" + arquivo;
                comunicacao = new PersistenciaComunicadorTCP(dadosServidor[0], Integer.parseInt(dadosServidor[1]));
                comunicacao.enviarMensagem(mensagem);
                String retorno = comunicacao.receberMensagem();
                comunicacao.FecharConexao();

                linhas = new ArrayList<String>();
                if (retorno.trim().length() > 0) {
                    String[] retornoLeitura = retorno.split("_");
                    for (String linha : retornoLeitura) {
                        linhas.add(linha);
                    }
                }
                return linhas;
            }
        } catch (Exception e) {
            throw new Exception("Erro ao ler o arquivo [" + arquivoTXT.getArquivo() + "]!\n" + e.getMessage());
        }
    }

    public void escreverArquivo() throws Exception {
        try {
            if (Vai.AMBIENTE.equals(EnumAmbiente.CLIENTE)) {
                FileWriter fw = new FileWriter(arquivoTXT.getCaminho() + "/" + arquivoTXT.getArquivo());
                BufferedWriter bw = new BufferedWriter(fw);
                for (String linha : arquivoTXT.getLinhas()) {
                    bw.write(linha + "\n");
                }
                bw.close();
            } else {
                linhas = arquivoTXT.getLinhas();
                for (String linha : linhas) {
                    incluirLinha(linha);
                }
            }
        } catch (Exception e) {
            throw new Exception("Erro ao escrever no arquivo [" + arquivoTXT.getArquivo() + "]!\n" + e.getMessage());
        }
    }
}
