/*
=====================================
ICRUDObjeto
--------METODOS----------------------
+ listar() : ArrayList<Objeto>
+ incluir(objeto : Objeto) : void
+ atualizar(objeto : Objeto) : void
+ excluir(idObjeto : int) : void
-------------------------------------
-------------------------------------
 */
package interfaces;

import classes.Reserva;
import java.util.ArrayList;

/**
 *
 * @author lucas
 */
public interface ICRUDReserva {

    ArrayList<Reserva> listar() throws Exception;

    Reserva buscarPeloId(int idReserva) throws Exception;

    int incluir(Reserva reserva) throws Exception;

    void alterar(Reserva reserva) throws Exception;

    void excluir(int idReserva) throws Exception;

}
