/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import classes.ArquivoTXT;
import classes.Colaborador;
import controle.ControleArquivoTXT;
import enumeradores.EnumArquivosBd;
import enumeradores.EnumCargo;
import enumeradores.EnumPerfil;
import enumeradores.EnumTipoStatus;
import interfaces.IArquivoTXT;
import interfaces.ICRUDColaborador;
import java.util.ArrayList;
import static telas.Vai.CONFIGURACAO;
import utilidades.GeradorID;

/**
 *
 * @author vovostudio
 */
public class PersistenciaColaborador implements ICRUDColaborador {

    // Atributos
    private IArquivoTXT controleArquivoTXT = null;
    private ArrayList<Colaborador> colecao = null;
    private Colaborador colaborador = null;
    private ArrayList<String> linhas = null;

    public PersistenciaColaborador() throws Exception {
        controleArquivoTXT = new ControleArquivoTXT(
                CONFIGURACAO.getCaminhoBdCliente(),
                EnumArquivosBd.COLABORADOR.getNomeArquivo());
    }

    @Override
    public ArrayList<Colaborador> listar() throws Exception {
        colecao = new ArrayList<>();
        linhas = controleArquivoTXT.lerArquivo();

        for (String linha : linhas) {
            String[] dados = linha.split(";");

            colaborador = new Colaborador(
                    Integer.parseInt(dados[0]),
                    dados[1],
                    EnumPerfil.values()[Integer.parseInt(dados[2])],
                    Integer.parseInt(dados[3]),
                    EnumCargo.values()[Integer.parseInt(dados[4])],
                    dados[5],
                    dados[6],
                    dados[7],
                    dados[8],
                    EnumTipoStatus.values()[Integer.parseInt(dados[9])]
            );
            colecao.add(colaborador);
        }
        return colecao;
    }

    @Override
    public Colaborador buscarPeloId(int id) throws Exception {
        listar();
        colaborador = new Colaborador();
        for (Colaborador c : colecao) {
            if (c.getIdColaborador() == id) {
                colaborador = new Colaborador(c);
            }
        }
        return colaborador;
    }

    @Override
    public void incluir(Colaborador colaborador) throws Exception {
        colaborador.setIdColaborador(GeradorID.getProximoID());
        controleArquivoTXT.incluirLinha(colaborador.toString());
    }

    @Override
    public void alterar(Colaborador colaborador) throws Exception {
        linhas = controleArquivoTXT.lerArquivo();
        for (String linha : linhas) {
            if (Integer.parseInt(linha.split(";")[0]) == colaborador.getIdColaborador()) {
                controleArquivoTXT.alterarLinha(linha, colaborador.toString());
                break;
            }
        }
    }

    @Override
    public void excluir(int idColaborador) throws Exception {
        linhas = controleArquivoTXT.lerArquivo();
        for (String linha : linhas) {
            if (Integer.parseInt(linha.split(";")[0]) == idColaborador) {
                controleArquivoTXT.excluirLinha(linha);
                break;
            }
        }
    }
}
