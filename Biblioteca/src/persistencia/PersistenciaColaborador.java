/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import classes.Colaborador;
import controle.ControleArquivoTXT;
import controle.ControleLog;
import enumeradores.EnumAcao;
import enumeradores.EnumArquivosBd;
import enumeradores.EnumCadastro;
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
        try {
            controleArquivoTXT = new ControleArquivoTXT(
                    CONFIGURACAO.getCaminhoBdCliente(),
                    EnumArquivosBd.COLABORADOR.getNomeArquivo());
        } catch (Exception e) {
            throw new Exception("Erro ao construir a classe PersistenciaColaborador!\n" + e.getMessage());
        }

    }

    @Override
    public ArrayList<Colaborador> listar() throws Exception {
        try {
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
        } catch (Exception e) {
            throw new Exception("Erro ao listar os colaboradores! (Persistência)\n" + e.getMessage());
        }

    }

    @Override
    public Colaborador buscarPeloId(int idColaborador) throws Exception {
        try {
            listar();
            colaborador = new Colaborador();
            for (Colaborador c : colecao) {
                if (c.getIdColaborador() == idColaborador) {
                    colaborador = new Colaborador(c);
                }
            }
            return colaborador;
        } catch (Exception e) {
            throw new Exception("Erro ao buscar colaborador pelo ID! (Persistência)\n" + e.getMessage());
        }
    }

    @Override
    public void incluir(Colaborador colaborador) throws Exception {
        try {
            colaborador.setIdColaborador(GeradorID.getProximoID());
            controleArquivoTXT.incluirLinha(colaborador.toString());
        } catch (Exception e) {
            throw new Exception("Erro ao incluir o colaborador! (Persistência)\n" + e.getMessage());
        }
    }

    @Override
    public void alterar(Colaborador colaborador) throws Exception {
        try {
            linhas = controleArquivoTXT.lerArquivo();
            for (String linha : linhas) {
                if (Integer.parseInt(linha.split(";")[0]) == colaborador.getIdColaborador()) {
                    controleArquivoTXT.alterarLinha(linha, colaborador.toString());
                    break;
                }
            }
        } catch (Exception e) {
            throw new Exception("Erro ao alterar o colaborador! (Persistência)\n" + e.getMessage());
        }

    }

    @Override
    public void excluir(int idColaborador) throws Exception {
        try {
            linhas = controleArquivoTXT.lerArquivo();

            for (String linha : linhas) {
                if (Integer.parseInt(linha.split(";")[0]) == idColaborador) {
                    controleArquivoTXT.excluirLinha(linha);
                    new ControleLog().incluir(EnumAcao.Excluir, EnumCadastro.COLABORADOR, linha);
                    break;
                }
            }
        } catch (Exception e) {
            throw new Exception("Erro ao excluir o colaborador! (Persistência)\n" + e.getMessage());
        }
    }
}
