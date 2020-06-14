/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import controle.ControleArquivoTXT;
import interfaces.IArquivoTXT;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import telas.Vai;

/**
 *
 * @author vovostudio
 */
public class CompilaCdd {

    private static String caminho = "/mnt/HD-500/projetos/pi-testes/Biblioteca/BibliotecaBd/";
    private static String origem = null;
    private static String destino = null;
    private static ArrayList<String> linhas = null;
    private static int cont = 0;
    private static int contLinha = 0;
    private static boolean incluir = false;

    private static ArrayList<String> lerArquivo() throws Exception {
        linhas = new ArrayList<String>();
        FileReader fr = new FileReader(caminho + origem);
        BufferedReader br = new BufferedReader(fr);
        String linha = "";
        while ((linha = br.readLine()) != null) {
            linhas.add(linha);
        }
        br.close();
        return linhas;
    }

    public static void main(String[] args) {
        try {
            origem = "cdd.txt";
            destino = "cdd_decimais.txt";
            linhas = lerArquivo();
            separaDecimais();

//            origem = "cdd_decimais.txt";
//            destino = "cdd_n1.txt";
//            linhas = lerArquivo();
//            n1();


        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private static void n1() throws Exception {

        FileWriter fw = new FileWriter(caminho + destino);
        BufferedWriter bw = new BufferedWriter(fw);
        ArrayList<String> linhasAdicionadas = new ArrayList<>();
        contLinha = 0;
        for (String lin : linhas) {
            contLinha++;

            String[] vetor = lin.split(";");
            incluir = true;

            String descricao = vetor[vetor.length - 1].trim();

            if (descricao.equals("Coletas")) {
                System.out.println("opa...");
            }

            if (vetor[1].length() == 0) {
                String novaLinha = vetor[0] + ";" + descricao;

                for (String texto : linhasAdicionadas) {
                    if (texto.trim().equalsIgnoreCase(novaLinha)) {
                        incluir = false;
                        break;
                    }
                }
                if (incluir) {
                    bw.write(novaLinha + "\n");
                    linhasAdicionadas.add(novaLinha);
                    System.out.println("[" + contLinha + "] -> " + novaLinha);
                }
            }
        }
        bw.close();
    }



    private static void separaDecimais() throws Exception {

        FileWriter fw = new FileWriter(caminho + destino);
        BufferedWriter bw = new BufferedWriter(fw);

        int contLinha = 0;
        for (String lin : linhas) {
            contLinha++;
            if (contLinha == 71) {
                System.out.println("Opa...");
            }
            String[] vetor = lin.split(";");
            String novaLinha = vetor[0] + ";";
            int cont = 0;
            String parte = vetor[1].substring(3);
            if (parte.length() > 0) {
                parte = parte.replace(".", "");

                for (int i = 0; i < parte.length(); i++) {
                    novaLinha += parte.substring(i, i + 1) + ";";
                    cont++;
                }
            }
            for (int i = 0; i < 9 - cont; i++) {
                novaLinha += ";";
            }
            novaLinha += vetor[2];
            bw.write(novaLinha + "\n");
            System.out.println("[" + contLinha + "] -> " + novaLinha);
        }

        bw.close();
    }

}
