/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Reserva;
import interfaces.ICRUDReserva;
import java.util.ArrayList;
import persistencia.PersistenciaReserva;

/**
 *
 * @author vovostudio
 */
public class ControleReserva implements ICRUDReserva {

    private ICRUDReserva persistencia = null;

    public ControleReserva() {
        persistencia = new PersistenciaReserva();

    }

    @Override
    public ArrayList<Reserva> listar() throws Exception {
        throw new UnsupportedOperationException("Método não implementado: ICRUDReserva, listar()"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Reserva buscarPeloId(int id) throws Exception {
        throw new UnsupportedOperationException("Método não implementado: ICRUDReserva, buscarPeloId()"); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void incluir(Reserva reserva) throws Exception {
        persistencia.incluir(reserva);
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
