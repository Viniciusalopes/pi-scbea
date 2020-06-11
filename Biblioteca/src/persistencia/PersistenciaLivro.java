/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import classes.Livro;
import controle.ControleAreaConhecimento;
import controle.ControleArquivoTXT;
import controle.ControleAutor;
import controle.ControleEditora;
import controle.ControleLog;
import enumeradores.EnumAcao;
import enumeradores.EnumArquivosBd;
import enumeradores.EnumCadastro;
import interfaces.IArquivoTXT;
import interfaces.ICRUDAreaConhecimento;
import interfaces.ICRUDAutor;
import interfaces.ICRUDEditora;
import interfaces.ICRUDLivro;
import java.util.ArrayList;
import telas.Vai;
import utilidades.GeradorID;

/**
 *
 * @author vovostudio
 */
public class PersistenciaLivro implements ICRUDLivro {

    private IArquivoTXT controleArquivoTXT = null;
    private ArrayList<Livro> colecao = null;
    private Livro livro = null;
    private ArrayList<String> linhas = null;

    private ICRUDEditora controleEditora = null;
    private ICRUDAutor controleAutor = null;
    private ICRUDAreaConhecimento controleAreaConhecimento = null;

    public PersistenciaLivro() throws Exception {
        try {
            controleArquivoTXT = new ControleArquivoTXT(Vai.CONFIGURACAO.getCaminhoBdCliente(), EnumArquivosBd.LIVRO.getNomeArquivo());
            controleEditora = new ControleEditora();
            controleAutor = new ControleAutor();
            controleAreaConhecimento = new ControleAreaConhecimento();
        } catch (Exception e) {
            throw new Exception("Erro ao construir a classe Livro!\n" + e.getMessage());
        }
    }

    @Override
    public ArrayList<Livro> listar() throws Exception {
        try {
            colecao = new ArrayList<>();
            linhas = controleArquivoTXT.lerArquivo();

            for (String linha : linhas) {
                String[] dados = linha.split(";");

                livro = new Livro(
                        Integer.parseInt(dados[0]),
                        controleEditora.buscarPeloId(Integer.parseInt(dados[1])),
                        controleAutor.buscarPeloId(Integer.parseInt(dados[2])),
                        controleAreaConhecimento.buscarPeloId(Integer.parseInt(dados[3])),
                        dados[4],
                        dados[5].replaceAll("____", "\n"),
                        Integer.parseInt(dados[6]),
                        dados[7],
                        Integer.parseInt(dados[8])
                );

                colecao.add(livro);
            }
            return colecao;

        } catch (Exception e) {
            throw new Exception("Erro ao listar os livros!\n" + e.getMessage());
        }
    }

    @Override
    public Livro buscarPeloId(int idLivro) throws Exception {
        for (Livro livro : listar()) {
            if (livro.getIdLivro() == idLivro) {
                return livro;
            }
        }
        return null;
    }

    @Override
    public int incluir(Livro livro) throws Exception {
        livro.setIdLivro(GeradorID.getProximoID());       //Obter o próximo ID único
        controleArquivoTXT.incluirLinha(livro.toString().replaceAll("\n", "____"));           //Gravar a linha no arquivoTXT
        return livro.getIdLivro();
    }

    @Override
    public void alterar(Livro livro) throws Exception {
        linhas = controleArquivoTXT.lerArquivo();
        for (String linha : linhas) {
            if (Integer.parseInt(linha.split(";")[0]) == livro.getIdLivro()) {
                controleArquivoTXT.alterarLinha(linha, livro.toString().replaceAll("\n", "____"));
                break;
            }
        }
    }

    @Override
    public void excluir(int idLivro) throws Exception {
        linhas = controleArquivoTXT.lerArquivo();
        for (String linha : linhas) {
            if (Integer.parseInt(linha.split(";")[0]) == idLivro) {
                controleArquivoTXT.excluirLinha(linha);
                new ControleLog().incluir(EnumAcao.Excluir, EnumCadastro.LIVRO, linha);
                break;
            }
        }
    }

}
