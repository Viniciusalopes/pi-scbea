/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.ArrayList;

/**
 *
 * @author vovostudio
 */
public class ArquivoTXT {

    private String caminho = "";
    private String arquivo = "";
    private ArrayList<String> linhas = new ArrayList<>();

    public ArquivoTXT() {

    }

    public ArquivoTXT(String caminho, String arquivo) throws Exception {

        if (caminho.trim().length() == 0) {
            throw new Exception("Caminho inválido!");
        }

        if (arquivo.trim().length() == 0) {
            throw new Exception("Nome do arquivo inválido!");
        }

        this.caminho = caminho;
        this.arquivo = arquivo;
    }

    public String getCaminho() {
        return caminho;
    }

    public void setCaminho(String caminho) {
        this.caminho = caminho;
    }

    public String getArquivo() {
        return arquivo;
    }

    public void setArquivo(String arquivo) {
        this.arquivo = arquivo;
    }

    public ArrayList<String> getLinhas() {
        return linhas;
    }

    public void setLinhas(ArrayList<String> linhas) {
        this.linhas = linhas;
    }

}
