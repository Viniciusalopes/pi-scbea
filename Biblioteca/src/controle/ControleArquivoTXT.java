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

/**
 *
 * @author vovostudio
 */
public class ControleArquivoTXT implements IArquivoTXT {

    public ControleArquivoTXT() {

    }

    public ControleArquivoTXT(ArquivoTXT arquivoTXT) throws Exception {
        File arquivo = new File(arquivoTXT.getCaminho());
        if (!arquivo.exists()) {
            arquivo.mkdirs();
            System.out.println("Criando diretório [" + arquivo.getAbsolutePath() + "]...");
        }
        arquivo = new File(arquivoTXT.getCaminho() + arquivoTXT.getArquivo());
        if (!arquivo.exists()) {
            arquivo.createNewFile();
            System.out.println("Criando arquivo [" + arquivo.getAbsolutePath() + "]...");
        }
    }

    @Override
    public ArrayList<String> lerArquivo(ArquivoTXT arquivoTXT) throws Exception {
        try {
            ArrayList<String> linhas = new ArrayList<String>();
            FileReader fr = new FileReader(arquivoTXT.getCaminho() + arquivoTXT.getArquivo());
            BufferedReader br = new BufferedReader(fr);
            String linha = "";
            while ((linha = br.readLine()) != null) {
                linhas.add(linha);
            }
            br.close();
            return linhas;
        } catch (Exception e) {
            throw new Exception("Erro ao ler o arquivo [" + arquivoTXT.getArquivo() + "]\n" + e);
        }
    }

    @Override
    public void escreverArquivo(ArquivoTXT arquivoTXT) throws Exception {
        FileWriter fw = new FileWriter(arquivoTXT.getCaminho() + arquivoTXT.getArquivo());
        BufferedWriter bw = new BufferedWriter(fw);
        for (String linha : arquivoTXT.getLinhas()) {
            bw.write(linha + "\n");
        }
        bw.close();
    }
}
