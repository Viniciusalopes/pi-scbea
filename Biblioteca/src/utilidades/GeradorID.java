/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import classes.ArquivoTXT;
import controle.ControleArquivoTXT;
import interfaces.IArquivoTXT;
import java.util.ArrayList;
import telas.Vai;

/**
 *
 * @author vovostudio
 */
public class GeradorID {

    public static int getProximoId() throws Exception {
        ArquivoTXT arquivoID = new ArquivoTXT(Vai.CONFIGURACAO.getCaminhoBdCliente(), "id");
        IArquivoTXT controle = new ControleArquivoTXT(arquivoID);
        ArrayList<String> linhas = controle.lerArquivo(arquivoID);
        if (linhas.size() == 0) {
            // Arquivo não existe
            
            linhas.add("1");
            arquivoID.setLinhas(linhas);
            controle.escreverArquivo(arquivoID);
        }
        return Integer.parseInt(linhas.get(0).trim());
    }

    public static void setProximoId() throws Exception {
        int id = getProximoId() + 1;
        ArquivoTXT arquivoID = new ArquivoTXT(Vai.CONFIGURACAO.getCaminhoBdCliente(), "id");
        IArquivoTXT controle = new ControleArquivoTXT(arquivoID);
        ArrayList<String> linhas = new ArrayList<>();
        linhas.add("" + id);
        arquivoID.setLinhas(linhas);
        controle.escreverArquivo(arquivoID);
    }
}
