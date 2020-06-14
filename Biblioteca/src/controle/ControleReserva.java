/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Reserva;
import enumeradores.EnumTipoStatus;
import interfaces.ICRUDReserva;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import persistencia.PersistenciaReserva;
import static utilidades.ColecaoUtil.getComparadorReservaColaboradorCresc;

/**
 *
 * @author vovostudio
 */
public class ControleReserva implements ICRUDReserva {

    private ICRUDReserva persistencia = null;
    private ArrayList<Reserva> colecao = null;

    public ControleReserva() throws Exception {
        persistencia = new PersistenciaReserva();

    }

    @Override
    public ArrayList<Reserva> listar() throws Exception {
        colecao = persistencia.listar();
        Collections.sort(colecao, getComparadorReservaColaboradorCresc());
        return colecao;
    }

    @Override
    public Reserva buscarPeloId(int idReserva) throws Exception {
        return persistencia.buscarPeloId(idReserva);
    }

    @Override
    public int incluir(Reserva reserva) throws Exception {
        validarReserva(reserva);
        return persistencia.incluir(reserva);
    }

    @Override
    public void alterar(Reserva reserva) throws Exception {
        validarReserva(reserva);
        persistencia.alterar(reserva);
    }

    @Override
    public void excluir(int idReserva) throws Exception {
        persistencia.excluir(idReserva);
    }

    private void validarReserva(Reserva reserva) throws Exception {
        if (reserva.getColaborador().getStatus().equals(EnumTipoStatus.INATIVO)) {
            throw new Exception("Para fazer uma reserva, o colaborador precisa estar ATIVO!");
        }
        if (reserva.getColaborador().getStatus().equals(EnumTipoStatus.INADIMPLENTE)) {
            throw new Exception("Para fazer uma reserva, o colaborador precisa estar ADIMPLENTE!");
        }
        colecao = persistencia.listar();
        for (Reserva r : colecao) {
            if (reserva.getColaborador().getIdColaborador() == r.getColaborador().getIdColaborador()
                    && reserva.getLivro().getIdLivro() == r.getLivro().getIdLivro()) {
                throw new Exception("Já existe uma reserva em nome desse colaborador para este livro!\n"
                        + "Reserva nº " + r.getIdReserva()
                        + " - Data: " + new SimpleDateFormat("dd/MM/yyyy").format(reserva.getdataReserva()));
            }
        }
    }

}
