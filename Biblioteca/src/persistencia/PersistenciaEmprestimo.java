/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import classes.ArquivoTXT;
import classes.Emprestimo;
import controle.ControleArquivoTXT;
import controle.ControleColaborador;
import controle.ControleExemplar;
import enumeradores.EnumArquivosBd;
import enumeradores.EnumTipoStatus;
import interfaces.IArquivoTXT;
import interfaces.ICRUDColaborador;
import interfaces.ICRUDEmprestimo;
import interfaces.ICRUDExemplar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import telas.Vai;
import utilidades.GeradorID;

/**
 *
 * @author vovostudio
 */
public class PersistenciaEmprestimo implements ICRUDEmprestimo {

    private ArquivoTXT arquivoTXT = null;
    private IArquivoTXT controleArquivoTXT = null;
    private ArrayList<Emprestimo> colecao = null;
    private Emprestimo emprestimo = null;
    private ArrayList<String> linhas = null;
    private ArrayList<String> novasLinhas = null;
    private ICRUDExemplar controleExemplar = null;
    private ICRUDColaborador controleColaborador = null;
    private SimpleDateFormat formatoData = null;

    public PersistenciaEmprestimo() throws Exception {
        arquivoTXT = new ArquivoTXT(Vai.CONFIGURACAO.getCaminhoBdCliente(), EnumArquivosBd.EMPRESTIMO.getNomeArquivo());
        controleArquivoTXT = new ControleArquivoTXT(arquivoTXT);

        controleExemplar = new ControleExemplar();
        controleColaborador = new ControleColaborador();
        formatoData = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Override
    public ArrayList<Emprestimo> listar() throws Exception {
        colecao = new ArrayList<>();
        linhas = controleArquivoTXT.lerArquivo(arquivoTXT);

        for (String linha : linhas) {
            String[] dados = linha.split(";");
            emprestimo = new Emprestimo(
                    Integer.parseInt(dados[0]),
                    controleExemplar.buscarPeloId(Integer.parseInt(dados[1])),
                    controleColaborador.buscarPeloId(Integer.parseInt(dados[2])),
                    formatoData.parse(dados[3]),
                    formatoData.parse(dados[4]),
                    EnumTipoStatus.values()[Integer.parseInt(dados[5])],
                    Float.parseFloat(dados[6].replace(",", "."))
            );
            colecao.add(emprestimo);
        }
        return colecao;
    }

    @Override
    public Emprestimo buscarPeloId(int idEmprestimo) throws Exception {
        colecao = listar();
        for (Emprestimo e : colecao) {
            if (e.getIdEmprestimo() == idEmprestimo) {
                return e;
            }
        }
        return null;
    }

    @Override
    public void incluir(Emprestimo emprestimo) throws Exception {
        linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
        emprestimo.setIdEmprestimo(GeradorID.getProximoID());
        linhas.add(emprestimo.toString());
        arquivoTXT.setLinhas(linhas);
        controleArquivoTXT.escreverArquivo(arquivoTXT);
        GeradorID.confirmaID();
    }

    @Override
    public void alterar(Emprestimo emprestimo) throws Exception {
        novasLinhas = new ArrayList<>();
        linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
        for (String linha : linhas) {
            if (Integer.parseInt(linha.split(";")[0]) == emprestimo.getIdEmprestimo()) {
                novasLinhas.add(emprestimo.toString());
            } else {
                novasLinhas.add(linha);
            }
        }
        arquivoTXT.setLinhas(novasLinhas);
        controleArquivoTXT.escreverArquivo(arquivoTXT);
    }

    @Override
    public void excluir(int idEmprestimo) throws Exception {
        novasLinhas = new ArrayList<>();
        linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
        for (String linha : linhas) {
            if (Integer.parseInt(linha.split(";")[0]) != emprestimo.getIdEmprestimo()) {
                novasLinhas.add(linha);
            }
        }
        arquivoTXT.setLinhas(novasLinhas);
        controleArquivoTXT.escreverArquivo(arquivoTXT);
    }
}
