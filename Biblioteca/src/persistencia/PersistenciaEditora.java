/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import classes.ArquivoTXT;
import classes.Autor;
import classes.Editora;
import controle.ControleArquivoTXT;
import enumeradores.EnumArquivosBd;
import interfaces.IArquivoTXT;
import interfaces.ICRUDEditora;
import java.util.ArrayList;
import static telas.Vai.CONFIGURACAO;

/**
 *
 * @author vovostudio
 */
public class PersistenciaEditora implements ICRUDEditora {

    private ArquivoTXT arquivoTXT = null;
    private IArquivoTXT controleArquivoTXT = null;

    public PersistenciaEditora() throws Exception {
        arquivoTXT = new ArquivoTXT(CONFIGURACAO.getCaminhoBdCliente(), EnumArquivosBd.EDITORA.getNomeArquivo());
        controleArquivoTXT = new ControleArquivoTXT(arquivoTXT);
    }

    @Override
    public ArrayList<Editora> listar() throws Exception {
        ArrayList<Editora> editoras = new ArrayList<>();
        ArrayList<String> linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
        for (String linha : linhas) {
            String[] dados = linha.split(";");
            Editora editora = new Editora(Integer.parseInt(dados[0]), dados[1]);
            editoras.add(editora);
        }
        return editoras;
    }

    @Override
    public void incluir(Editora editora) throws Exception {
        persistir("incluir", editora, 0);
    }

    @Override
    public void alterar(Editora editora) throws Exception {
        persistir("alterar", editora, 0);
    }

    @Override
    public void excluir(int idEditora) throws Exception {
        persistir("excluir", null, idEditora);
    }

    private void persistir(String operacao, Editora editora, int idEditora) throws Exception {
        ArrayList<String> linhas = new ArrayList<>();
        ArrayList<Editora> colecao = listar();

        for (Editora e : colecao) {
            if (operacao.equals("incluir")) {
                linhas.add(e.toString());
            } else if (operacao.equals("alterar")) {
                if (e.getIdEditora() == editora.getIdEditora()) {
                    linhas.add(editora.toString());
                } else {
                    linhas.add(e.toString());
                }
            } else if (operacao.equals("excluir")) {
                if (e.getIdEditora() != idEditora) {
                    linhas.add(e.toString());
                }
            }
        }
        if (operacao.equals("incluir")) {
            linhas.add(editora.toString());
        }
        arquivoTXT.setLinhas(linhas);
        controleArquivoTXT.escreverArquivo(arquivoTXT);
    }
}
