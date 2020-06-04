package utilidades;

import classes.ArquivoTXT;
import controle.ControleArquivoTXT;
import enumeradores.EnumArquivosBd;
import interfaces.IArquivoTXT;
import java.io.File;
import java.util.ArrayList;
import telas.Vai;

public class GeradorID {

    private static int id = 1;
    private static ArquivoTXT arquivoID = null;
    private static ArquivoTXT arquivoTabela = null;
    private static IArquivoTXT controleID = null;
    private static IArquivoTXT controleTabela = null;
    private static ArrayList<String> linhasID = null;
    private static ArrayList<String> linhasTabela = null;
    private static File file = null;

    public static int getProximoId() throws Exception {
        arquivoID = new ArquivoTXT(Vai.CONFIGURACAO.getCaminhoBdCliente(), "id");
        file = new File(arquivoID.getCaminho() + Vai.BARRA + arquivoID.getArquivo());
        controleID = new ControleArquivoTXT(arquivoID);

        int i = 0;
        do {
            if (!file.exists()) {
                file.createNewFile();
                reindexarId(); // Encontra o último número utilizado nas tabelas
            }

            linhasID = controleID.lerArquivo(arquivoID);

            if (linhasID.size() == 0) {
                reindexarId();
            }
            i++;
        } while (linhasID.size() == 0 && i < 3);
        return Integer.parseInt(linhasID.get(0).trim()) + 1;
    }

    public static void setProximoId() throws Exception {
        id = getProximoId() + 1;
        arquivoID = new ArquivoTXT(Vai.CONFIGURACAO.getCaminhoBdCliente(), "id");
        controleID = new ControleArquivoTXT(arquivoID);
        linhasID = new ArrayList<>();
        linhasID.add("" + id);
        arquivoID.setLinhas(linhasID);
        controleID.escreverArquivo(arquivoID);
    }

    private static void reindexarId() throws Exception {
        int maior = 1;
        
        for (EnumArquivosBd arquivo : EnumArquivosBd.values()) {
            arquivoTabela = new ArquivoTXT(Vai.CONFIGURACAO.getCaminhoBdCliente(), arquivo.getNomeArquivo());
            if (!arquivo.getNomeArquivo().equals(EnumArquivosBd.CONFIGURACAO.getNomeArquivo())) {
                controleTabela = new ControleArquivoTXT(arquivoTabela);
                linhasTabela = controleTabela.lerArquivo(arquivoTabela);
                for (String linha : linhasTabela) {
                    maior = Integer.parseInt(linha.split(";")[0]);
                    if (maior > id) {
                        id = maior;
                    }
                }
            }
        }
        linhasID.add(id + "");
        arquivoID.setLinhas(linhasID);
        controleID.escreverArquivo(arquivoID);
    }
}
