/*
Reserva
- idReserva  : int
- livro : Livro
- colaborador : Colaborador
- dataReserva  : Date
--GETERS-&-SETERS--
+ get() : void
+ set() : void
--TO-STRING--
+ toString() : String
 */
package classes;

import java.util.Date;

/**
 * CLASSE RESERVA
 *
 */
public class Reserva {

    //atributos 
    private int idReserva;
    private livro Livro;
    private colaborador Colaborador;
    private Date dataReserva;

    public Reserva() {
        //construtor padrao 

    }

    public Reserva(Reserva reserva) {
        idReserva = reserva.idReserva;
        Livro = reserva.Livro;
        Colaborador = reserva.Colaborador;
        dataReserva = reserva.dataReserva;
    }

    public Reserva(int idReserva, Livro livro, Colaborador colaborador, Date dataReserva) {
        this.idReserva = idReserva;
        this.Livro = livro;
        this.Colaborador = colaborador;
        this.dataReserva = dataReserva;
    }

    //geters e sters 
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public livro getLivro() {
        return Livro;
    }

    public void setLivro(livro Livro) {
        this.Livro = Livro;
    }

    public colaborador getColaborador() {
        return Colaborador;
    }

    public void setColaborador(colaborador Colaborador) {
        this.Colaborador = Colaborador;
    }

    public Date getdataReserva() {
        return dataReserva;
    }

    public void setdataReserva(Date dataReserva) {
        this.dataReserva = dataReserva;
    }

    @Override
    public String toString() {
        return String.format("%d;%s;$;$s", idReserva, Livro, Colaborador, dataReserva)
        {

        }
    }

}
