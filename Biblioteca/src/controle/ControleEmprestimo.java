/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Emprestimo;
import classes.Exemplar;
import classes.Reserva;
import enumeradores.EnumAcao;
import enumeradores.EnumCadastro;
import enumeradores.EnumOpcaoComprovante;
import enumeradores.EnumTipoStatus;
import interfaces.ICRUDEmprestimo;
import interfaces.ICRUDExemplar;
import interfaces.ICRUDReserva;
import java.util.ArrayList;
import persistencia.PersistenciaEmprestimo;
import interfaces.IControleEmprestimo;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import telas.Vai;
import static utilidades.ColecaoUtil.getComparadorEmprestimoColaboradorCresc;
import static utilidades.DataUtil.intervaloEmDias;
import static utilidades.DataUtil.adicionarDias;
import static utilidades.StringUtil.truncar;

/**
 *
 * @author vovostudio
 */
public class ControleEmprestimo implements IControleEmprestimo {

    private ICRUDEmprestimo persistencia = null;
    private ICRUDReserva controleReserva = null;
    private ICRUDExemplar controleExemplar = null;
    private ArrayList<Emprestimo> colecao = null;
    private SimpleDateFormat formatoData = null;
    private EnumAcao acao = null;

    public ControleEmprestimo() throws Exception {
        persistencia = new PersistenciaEmprestimo();
        formatoData = new SimpleDateFormat("dd/MM/yyyy");
    }

    @Override
    public ArrayList<Emprestimo> listar() throws Exception {
        atualizarAtrasoMulta();
        Collections.sort(colecao, getComparadorEmprestimoColaboradorCresc());
        return colecao;
    }

    @Override
    public Emprestimo buscarPeloId(int idEmprestimo) throws Exception {
        return persistencia.buscarPeloId(idEmprestimo);
    }

    @Override
    public int incluir(Emprestimo emprestimo) throws Exception {
        acao = EnumAcao.Incluir_Emprestimo;
        validarEmprestimo(emprestimo);

        int idEmprestimo = persistencia.incluir(emprestimo);
        controleExemplar = new ControleExemplar();

        Exemplar exemplar = controleExemplar.buscarPeloId(emprestimo.getExemplar().getIdExemplar());
        exemplar.setStatusExemplar(EnumTipoStatus.EMPRESTADO);
        controleExemplar.alterar(exemplar);

        return idEmprestimo;
    }

    @Override
    public void alterar(Emprestimo emprestimo) throws Exception {
        acao = EnumAcao.Editar_Emprestimo;
        validarEmprestimo(emprestimo);
        persistencia.alterar(emprestimo);
    }

    @Override
    public void excluir(int idEmprestimo) throws Exception {

    }

    @Override
    public float calcularSaldoDevedor(int idColaborador, Date dataDevolucao) throws Exception {
        float saldo = 0;
        int dias = 0;
        colecao = listar();
        for (Emprestimo emprestimo : colecao) {
            if (emprestimo.getColaborador().getIdColaborador() == idColaborador
                    && emprestimo.getDataDevolucao() != null) {
                dias = calcularDiasDeAtraso(emprestimo.getDataEmprestimo(), dataDevolucao);
                for (int i = 0; i < dias; i++) {
                    saldo += (dias * Vai.CONFIGURACAO.getValorMultaDiaria());
                }
            }
        }
        return saldo;
    }

    @Override
    public int calcularDiasDeAtraso(Date dataEmprestimo, Date dataDevolucao) throws Exception {
        int corridos = intervaloEmDias(dataEmprestimo, dataDevolucao);
        int previsto = intervaloEmDias(dataEmprestimo, adicionarDias(dataEmprestimo, Vai.CONFIGURACAO.getDiasDeEmprestimo()));
        return corridos - previsto;
    }

    @Override
    public void validarEmprestimo(Emprestimo emprestimo) throws Exception {
        if (emprestimo.getColaborador().getStatus().equals(EnumTipoStatus.INATIVO)) {
            throw new Exception("Para fazer um empréstimo, o colaborador precisa estar ATIVO!");
        }
        if (emprestimo.getColaborador().getStatus().equals(EnumTipoStatus.INADIMPLENTE)) {
            throw new Exception("Para fazer um empréstimo, o colaborador precisa estar ADIMPLENTE!");
        }
        colecao = persistencia.listar();

    }

    @Override
    public boolean excluirReservaDeLivroEmprestado(Emprestimo emprestimo) throws Exception {
        controleReserva = new ControleReserva();
        for (Reserva reserva : controleReserva.listar()) {
            if (reserva.getColaborador().getIdColaborador() == emprestimo.getColaborador().getIdColaborador()
                    && reserva.getLivro().getIdLivro() == emprestimo.getExemplar().getIdLivro()) {
                controleReserva.excluir(reserva.getIdReserva());
                new ControleLog().incluir(
                        EnumAcao.Excluir,
                        EnumCadastro.RESERVA,
                        reserva.toString(),
                        "Livro emprestado. ID do Empréstimo: " + emprestimo.getIdEmprestimo());
                return true;
            }
        }
        return false;
    }

    @Override
    public String comprovante(Emprestimo emprestimo, EnumOpcaoComprovante opcaoComprovante) {

        return " COMPROVANTE DE EMPRÉSTIMO nº: " + emprestimo.getIdEmprestimo()
                + "\n-------------------------------------------------------------------------"
                + "\nDADOS DO COLABORADOR:"
                + "\nNome: " + emprestimo.getColaborador().getNomeColaborador()
                + "\nMatrícula: " + emprestimo.getColaborador().getMatricula()
                + "\n-------------------------------------------------------------------------"
                + "\nDADOS DO EXEMPLAR:"
                + "\nLivro: " + truncar(emprestimo.getExemplar().getTitulo(), 40)
                + "\n" + emprestimo.getExemplar().getEdicao() + " ª Edição"
                + "\nEditora: " + emprestimo.getExemplar().getEditora().getNomeEditora()
                + "\nAno de Publicação: " + emprestimo.getExemplar().getAnoPublicacao()
                + "\nExemplar nº " + emprestimo.getExemplar().getIdExemplar()
                + "\n-------------------------------------------------------------------------"
                + "\nDADOS DO EMPRÉSTIMO:"
                + "\nData do empréstimo: " + formatoData.format(emprestimo.getDataEmprestimo())
                + "\nData prevista para devolução: " + formatoData.format(
                        (emprestimo.getDataDevolucao() == null)
                        ? adicionarDias(new Date(), Vai.CONFIGURACAO.getDiasDeEmprestimo())
                        : emprestimo.getDataDevolucao())
                + "\nMulta por atraso: R$ " + String.format("%.2f", emprestimo.getValorMulta())
                + "\nValor pago: R$ " + String.format("%.2f", emprestimo.getValorMulta())
                + "\nStatus: " + emprestimo.getStatusEmprestimo();

    }

    private void atualizarAtrasoMulta() throws Exception {
        colecao = new ArrayList<>();
        for (Emprestimo e : persistencia.listar()) {
            if (!e.getStatusEmprestimo().equals(EnumTipoStatus.DEVOLVIDO)) {
                e.setValorMulta(calcularSaldoDevedor(e.getColaborador().getIdColaborador(), e.getDataDevolucao()));
                persistencia.alterar(e);
                colecao.add(e);
            }
        }
    }
}
