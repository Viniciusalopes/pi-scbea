/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import classes.Exemplar;
import controle.ControleArquivoTXT;
import controle.ControleLog;
import enumeradores.EnumAcao;
import enumeradores.EnumArquivosBd;
import enumeradores.EnumCadastro;
import interfaces.IArquivoTXT;
import interfaces.ICRUDExemplar;
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

    public PersistenciaExemplar() throws Exception {
        controleArquivoTXT = new ControleArquivoTXT(Vai.CONFIGURACAO.getCaminhoBdCliente(),
                EnumArquivosBd.EXEMPLAR.getNomeArquivo()
        );
    }

    @Override
    public ArrayList<Exemplar> listar() throws Exception {
          colecao = new ArrayList<>();
        linhas = controleArquivoTXT.lerArquivo();
        for (String linha : linhas) {
            String[] dados = linha.split(";");
            exemplar = new Exemplar(Integer.parseInt(dados[0]), dados[1]);
            colecao.add(exemplar);
        }
        return colecao;
    }

    @Override
    public Exemplar buscarPeloId(int idExmplar) throws Exception {
    listar();
        for (Exemplar e : colecao) {
            if (e.getIdExemplar()== idExmplar) {
                return e;
            }
        }
        return null;
    
    }

    @Override
    public void incluir(Exemplar exemplar) throws Exception {
       exemplar.setIdExemplar(GeradorID.getProximoID());
        controleArquivoTXT.incluirLinha(exemplar.toString());
    }

    @Override
    public void alterar(Exemplar exemplar) throws Exception {
      linhas = controleArquivoTXT.lerArquivo();
        for (String linha : linhas) {
            if (Integer.parseInt(linha.split(";")[0]) == exemplar.getIdExemplar()) {
                controleArquivoTXT.alterarLinha(linha, exemplar.toString());
                break;
            }
        }
    }
    
    
    public void excluir(int IdExemplar) throws Exception {
       linhas = controleArquivoTXT.lerArquivo();
        for (String linha : linhas) {
            if (Integer.parseInt(linha.split(";")[0]) == IdExemplar) {
                controleArquivoTXT.excluirLinha(linha);
                new ControleLog().incluir(EnumAcao.Excluir, EnumCadastro.EXEMPLAR, linha);
                break;
            }
        }

    }
}

  