/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import classes.Emprestimo;
import classes.Log;
import controle.ControleArquivoTXT;
import controle.ControleColaborador;
import controle.ControleExemplar;
import controle.ControleLog;
import enumeradores.EnumAcao;
import enumeradores.EnumArquivosBd;
import enumeradores.EnumCadastro;
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

    private IArquivoTXT controleArquivoTXT = null;
    private ArrayList<Emprestimo> colecao = null;
    private Emprestimo emprestimo = null;
    private ArrayList<String> linhas = null;
    private ICRUDExemplar controleExemplar = null;
    private ICRUDColaborador controleColaborador = null;
    private SimpleDateFormat formatoData = null;

    public PersistenciaEmprestimo() throws Exception {
        try {
            controleArquivoTXT = new ControleArquivoTXT(
                    Vai.CONFIGURACAO.getCaminhoBD(),
                    EnumArquivosBd.EMPRESTIMO.getNomeArquivo()
            );
            controleExemplar = new ControleExemplar();
            controleColaborador = new ControleColaborador();
            formatoData = new SimpleDateFormat("dd/MM/yyyy");
        } catch (Exception e) {
            throw new Exception("Erro ao construir a classe PersistenciaEmprestimo!\n" + e.getMessage());
        }
    }

    @Override
    public ArrayList<Emprestimo> listar() throws Exception {
        try {
            colecao = new ArrayList<>();
            linhas = controleArquivoTXT.lerArquivo();

            for (String linha : linhas) {
                String[] dados = linha.split(";");
                emprestimo = new Emprestimo(
                        Integer.parseInt(dados[0]),
                        controleExemplar.buscarPeloId(Integer.parseInt(dados[1])),
                        controleColaborador.buscarPeloId(Integer.parseInt(dados[2])),
                        formatoData.parse(dados[3]),
                        (dados[4].equals("")) ? null : formatoData.parse(dados[4]),
                        EnumTipoStatus.values()[Integer.parseInt(dados[5])],
                        Float.parseFloat(dados[6].replace(",", ".")),
                        Float.parseFloat(dados[7].replace(",", "."))
                );
                colecao.add(emprestimo);
            }
            return colecao;
        } catch (Exception e) {
            throw new Exception("Erro ao listar os empréstimos! (Persistência)\n" + e.getMessage());
        }
    }

    @Override
    public Emprestimo buscarPeloId(int idEmprestimo) throws Exception {
        try {
            listar();
            for (Emprestimo emprestimo : colecao) {
                if (emprestimo.getIdEmprestimo() == idEmprestimo) {
                    return emprestimo;
                }
            }
            return null;
        } catch (Exception e) {
            throw new Exception("Erro ao buscar empréstimo pelo ID! (Persistência)\n" + e.getMessage());
        }
    }

    @Override
    public int incluir(Emprestimo emprestimo) throws Exception {
        try {
            emprestimo.setIdEmprestimo(GeradorID.getProximoID());
            controleArquivoTXT.incluirLinha(emprestimo.toString());
            return emprestimo.getIdEmprestimo();
        } catch (Exception e) {
            throw new Exception("Erro ao incluir o empréstimo! (Persistência)\n" + e.getMessage());
        }
    }

    @Override
    public void alterar(Emprestimo emprestimo) throws Exception {
        try {
            linhas = controleArquivoTXT.lerArquivo();
            for (String linha : linhas) {
                if (Integer.parseInt(linha.split(";")[0]) == emprestimo.getIdEmprestimo()) {
                    controleArquivoTXT.alterarLinha(linha, emprestimo.toString());
                    break;
                }
            }
        } catch (Exception e) {
            throw new Exception("Erro ao alterar o empréstimo! (Persistência)\n" + e.getMessage());
        }
    }

    @Override
    public void excluir(int idEmprestimo) throws Exception {
        try {
            linhas = controleArquivoTXT.lerArquivo();
            for (String linha : linhas) {
                if (Integer.parseInt(linha.split(";")[0]) == idEmprestimo) {
                    controleArquivoTXT.excluirLinha(linha);
                    new ControleLog().incluir(EnumAcao.Excluir, EnumCadastro.EMPRESTIMO, linha, "PersistenciaEmprestimo, excluir");
                    break;
                }
            }
        } catch (Exception e) {
            throw new Exception("Erro ao excluir o empréstimo! (Persistência)\n" + e.getMessage());
        }
    }
}
