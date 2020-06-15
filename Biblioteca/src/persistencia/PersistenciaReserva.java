/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import classes.Reserva;
import controle.ControleArquivoTXT;
import controle.ControleColaborador;
import controle.ControleLivro;
import controle.ControleLog;
import enumeradores.EnumAcao;
import enumeradores.EnumArquivosBd;
import enumeradores.EnumCadastro;
import interfaces.IArquivoTXT;
import interfaces.ICRUDColaborador;
import interfaces.ICRUDLivro;
import interfaces.ICRUDReserva;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import static telas.Vai.CONFIGURACAO;
import utilidades.GeradorID;

/**
 *
 * @author vovostudio
 */
public class PersistenciaReserva implements ICRUDReserva {

    private IArquivoTXT controleArquivoTXT = null;
    private ArrayList<Reserva> colecao = null;
    private Reserva reserva = null;
    private ArrayList<String> linhas = null;
    private ICRUDLivro controleLivro = null;
    private ICRUDColaborador controleColaborador = null;

    public PersistenciaReserva() throws Exception {
        try {
            controleArquivoTXT = new ControleArquivoTXT(
                    CONFIGURACAO.getCaminhoBdCliente(),
                    EnumArquivosBd.RESERVA.getNomeArquivo());
            controleLivro = new ControleLivro();
            controleColaborador = new ControleColaborador();
        } catch (Exception e) {
            throw new Exception("Erro ao construir a classe PersistenciaReserva!\n" + e.getMessage());
        }

    }

    @Override
    public ArrayList<Reserva> listar() throws Exception {
        colecao = new ArrayList<>();
        linhas = controleArquivoTXT.lerArquivo();
        for (String linha : linhas) {
            String[] dados = linha.split(";");
            reserva = new Reserva(
                    Integer.parseInt(dados[0]),
                    controleLivro.buscarPeloId(Integer.parseInt(dados[1])),
                    controleColaborador.buscarPeloId(Integer.parseInt(dados[2])),
                    new SimpleDateFormat("dd/MM/yyyy").parse(dados[3])
            );

            colecao.add(reserva);
        }
        return colecao;
    }

    @Override
    public Reserva buscarPeloId(int idReserva) throws Exception {
        listar();
        for (Reserva reserva : colecao) {
            if (reserva.getIdReserva() == idReserva) {
                return reserva;
            }
        }
        return null;
    }

    @Override
    public int incluir(Reserva reserva) throws Exception {
        reserva.setIdReserva(GeradorID.getProximoID());
        controleArquivoTXT.incluirLinha(reserva.toString());
        return reserva.getIdReserva();
    }

    @Override
    public void alterar(Reserva reserva) throws Exception {
        linhas = controleArquivoTXT.lerArquivo();
        for (String linha : linhas) {
            if (Integer.parseInt(linha.split(";")[0]) == reserva.getIdReserva()) {
                controleArquivoTXT.alterarLinha(linha, reserva.toString());
                break;
            }
        }
    }

    @Override
    public void excluir(int idReserva) throws Exception {
        linhas = controleArquivoTXT.lerArquivo();
        for (String linha : linhas) {
            if (Integer.parseInt(linha.split(";")[0]) == idReserva) {
                controleArquivoTXT.excluirLinha(linha);
                new ControleLog().incluir(EnumAcao.Excluir, EnumCadastro.RESERVA, linha, "PersistenciaReserva, excluir");
                break;
            }
        }
    }
}
