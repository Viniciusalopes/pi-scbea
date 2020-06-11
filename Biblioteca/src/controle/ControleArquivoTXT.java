/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.ArquivoTXT;
import interfaces.IArquivoTXT;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import telas.Vai;

/**
 *
 * @author vovostudio
 */
public class ControleArquivoTXT implements IArquivoTXT {

    private ArrayList<String> linhas = null;
    private ArquivoTXT arquivoTXT = null;

    public ControleArquivoTXT() {

    }

    public ControleArquivoTXT(String caminho, String arquivo) throws Exception {
        try {
            File dir = new File(caminho);
            if (!dir.exists()) {
                dir.mkdirs();
                System.out.println("Criando diretório [" + dir.getAbsolutePath() + "]...");
            }

            File file = new File(caminho + Vai.BARRA + arquivo);
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("Criando arquivo [" + file.getAbsolutePath() + "]...");
            }

            arquivoTXT = new ArquivoTXT(caminho, arquivo);
        } catch (Exception e) {
            throw new Exception("Erro ao construir a classe ControleArquivoTXT[" + arquivo + "]!\n" + e.getMessage());
        }
    }

    @Override
    public void incluirLinha(String linha) throws Exception {
        try {
            linhas = lerArquivo();
            linhas.add(linha);
            arquivoTXT.setLinhas(linhas);
            escreverArquivo();
        } catch (Exception e) {
            throw new Exception("Erro ao incluir linha no arquivo [" + arquivoTXT.getArquivo() + "]! (ControleArquivoTXT)\n" + e.getMessage());
        }
    }

    @Override
    public void alterarLinha(String linhaAntes, String linhaDepois) throws Exception {
        try {

            linhas = lerArquivo();
            for (String linha : linhas) {
                if (linha.equals(linhaAntes)) {
                    linhas.set(linhas.indexOf(linhaAntes), linhaDepois);
                    arquivoTXT.setLinhas(linhas);
                    break;
                }
            }
            escreverArquivo();

        } catch (Exception e) {
            throw new Exception("Erro ao alterar linha do arquivo [" + arquivoTXT.getArquivo() + "]! (ControleArquivoTXT)\n" + e.getMessage());
        }
    }

    @Override
    public void excluirLinha(String linha) throws Exception {
        try {
            linhas = lerArquivo();
            for (String l : linhas) {
                if (l.equals(linha)) {
                    linhas.remove(linha);
                    break;
                }
            }
            arquivoTXT.setLinhas(linhas);
            escreverArquivo();
        } catch (Exception e) {
            throw new Exception("Erro ao excluir linha do arquivo [" + arquivoTXT.getArquivo() + "]! (ControleArquivoTXT)\n" + e.getMessage());
        }
    }

    @Override
    public ArrayList<String> lerArquivo() throws Exception {
        try {
            linhas = new ArrayList<String>();
            FileReader fr = new FileReader(arquivoTXT.getCaminho() + Vai.BARRA + arquivoTXT.getArquivo());
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
            br.close();
            return linhas;
        } catch (Exception e) {
            throw new Exception("Erro ao ler o arquivo [" + arquivoTXT.getArquivo() + "]!\n" + e.getMessage());
        }
    }

    public void escreverArquivo() throws Exception {
        try {
            FileWriter fw = new FileWriter(arquivoTXT.getCaminho() + Vai.BARRA + arquivoTXT.getArquivo());
            BufferedWriter bw = new BufferedWriter(fw);
            for (String linha : arquivoTXT.getLinhas()) {
                bw.write(linha + "\n");
            }
            bw.close();
        } catch (Exception e) {
            throw new Exception("Erro ao escrever no arquivo [" + arquivoTXT.getArquivo() + "]!\n" + e.getMessage());
        }
    }
}
