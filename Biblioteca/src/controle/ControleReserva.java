/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Reserva;
import interfaces.ICRUDReserva;
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
        return persistencia.incluir(reserva);
    }

    @Override
    public void alterar(Reserva reserva) throws Exception {
        throw new UnsupportedOperationException("Método não implementado: ICRUDReserva, alterar()."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void excluir(int idReserva) throws Exception {
        throw new UnsupportedOperationException("Método não implementado: ICRUDReserva, excluir()"); //To change body of generated methods, choose Tools | Templates.
    }

}
