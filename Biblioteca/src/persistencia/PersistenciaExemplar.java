/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import classes.Exemplar;
import controle.ControleArquivoTXT;
import controle.ControleLivro;
import controle.ControleLog;
import enumeradores.EnumAcao;
import enumeradores.EnumArquivosBd;
import enumeradores.EnumCadastro;
import enumeradores.EnumTipoStatus;
import interfaces.IArquivoTXT;
import interfaces.ICRUDExemplar;
import interfaces.ICRUDLivro;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import telas.Vai;
import utilidades.GeradorID;

/**
 *
 * @author vovostudio
 */
public class PersistenciaExemplar implements ICRUDExemplar {

    private IArquivoTXT controleArquivoTXT = null;
    private ArrayList<Exemplar> colecao = null;
    private Exemplar exemplar = null;
    private ArrayList<String> linhas = null;
    private ICRUDLivro controleLivro = null;

    public PersistenciaExemplar() throws Exception {
        controleArquivoTXT = new ControleArquivoTXT(Vai.CONFIGURACAO.getCaminhoBdCliente(),
                EnumArquivosBd.EXEMPLAR.getNomeArquivo()
        );
        controleLivro = new ControleLivro();
    }

    @Override
    public ArrayList<Exemplar> listar() throws Exception {
        colecao = new ArrayList<>();
        linhas = controleArquivoTXT.lerArquivo();
        for (String linha : linhas) {
            String[] dados = linha.split(";");
            exemplar = new Exemplar(
                    Integer.parseInt(dados[0]),
                    controleLivro.buscarPeloId(Integer.parseInt(dados[1])),
                    EnumTipoStatus.values()[Integer.parseInt(dados[2])],
                    new SimpleDateFormat("dd/MM/yyyy").parse(dados[3].replace(",", ".")),
                    Float.parseFloat(dados[4].toString().replace(",", ".")),
                    dados[5].replaceAll("____", "\n").replace("_", "")
            );
            colecao.add(exemplar);
        }
        return colecao;
    }

    @Override
    public Exemplar buscarPeloId(int idExemplar) throws Exception {
        listar();
        for (Exemplar exemplar : colecao) {
            if (exemplar.getIdExemplar() == idExemplar) {
                return exemplar;
            }
        }
        return null;

    }

    @Override
    public void incluir(Exemplar exemplar) throws Exception {
        exemplar.setIdExemplar(GeradorID.getProximoID());
        exemplar.setMotivoDesativado((exemplar.getMotivoDesativado().trim().length() == 0) ? "-" : exemplar.getMotivoDesativado().replace(";", ","));
        controleArquivoTXT.incluirLinha(exemplar.toString().replaceAll("\n", "____"));
    }

    @Override
    public void alterar(Exemplar exemplar) throws Exception {
        linhas = controleArquivoTXT.lerArquivo();
        for (String linha : linhas) {
            if (Integer.parseInt(linha.split(";")[0]) == exemplar.getIdExemplar()) {
                exemplar.setMotivoDesativado((exemplar.getMotivoDesativado().trim().length() == 0) ? "-" : exemplar.getMotivoDesativado().replace(";", ","));
                controleArquivoTXT.alterarLinha(linha, exemplar.toString().replaceAll("\n", "____"));
                break;
            }
        }
    }

    @Override
    public void excluir(int IdExemplar) throws Exception {
        linhas = controleArquivoTXT.lerArquivo();
        for (String linha : linhas) {
            if (Integer.parseInt(linha.split(";")[0]) == IdExemplar) {
                controleArquivoTXT.excluirLinha(linha);
                new ControleLog().incluir(EnumAcao.Excluir, EnumCadastro.EXEMPLAR, linha, "PersistenciaExemplar, excluir");
                break;
            }
        }
    }
}
