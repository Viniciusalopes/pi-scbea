/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import classes.Colaborador;
import classes.Log;
import controle.ControleArquivoTXT;
import controle.ControleColaborador;
import enumeradores.EnumAcao;
import enumeradores.EnumArquivosBd;
import enumeradores.EnumCadastro;
import interfaces.IArquivoTXT;
import interfaces.ICRUDColaborador;
import interfaces.ICRUDLog;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import telas.Vai;

/**
 *
 * @author vovostudio
 */
public class PersistenciaLog implements ICRUDLog {

    private IArquivoTXT controleArquivoTXT = null;
    private ICRUDColaborador controleColaborador = null;

    private ArrayList<Log> colecao = null;
    private ArrayList<String> linhas = null;
    private SimpleDateFormat formatoData = null;

    private Log log = null;

    // CONSTRUTOR
    public PersistenciaLog() throws Exception {
        try {
            String caminho = Vai.CONFIGURACAO.getCaminhoBdCliente();

            controleArquivoTXT = new ControleArquivoTXT(caminho, EnumArquivosBd.LOG.getNomeArquivo());
            controleColaborador = new ControleColaborador();
            formatoData = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        } catch (Exception e) {
            throw new Exception("Erro ao construir a classe PersistenciaLog!\n" + e.getMessage());
        }
    }

    @Override
    public ArrayList<Log> listar() throws Exception {
        try {
            colecao = new ArrayList<>();
            linhas = controleArquivoTXT.lerArquivo();

            for (String linha : linhas) {
                String[] dados = linha.split("_");

                log = new Log(
                        formatoData.parse(dados[0]),
                        ((dados[1].equals("0")) ? new Colaborador() : controleColaborador.buscarPeloId(Integer.parseInt(dados[1]))),
                        EnumAcao.values()[Integer.parseInt(dados[2])],
                        EnumCadastro.values()[Integer.parseInt(dados[3])],
                        dados[4],
                        dados[5]
                );
                colecao.add(log);
            }
            return colecao;
        } catch (Exception e) {
            throw new Exception("Erro ao listar os logs!\n" + e.getMessage());
        }
    }

    @Override
    public void incluir(EnumAcao acao, EnumCadastro cadastro, String registro, String observacao) throws Exception {
        try {
            log = new Log(new Date(), Vai.USUARIO, acao, cadastro, registro, observacao);
            String linha = log.toString();
            controleArquivoTXT.incluirLinha(linha);
        } catch (Exception e) {
            throw new Exception("Erro ao incluir o registro de log!\n" + e.getMessage());
        }
    }
}
