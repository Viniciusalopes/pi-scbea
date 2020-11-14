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

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * CLASSE RESERVA
 *
 */
public class Reserva {

    //atributos 
    private int idReserva;
    private Livro livro;
    private Colaborador colaborador;
    private Date dataReserva;

    public Reserva() {
        //construtor padrao 

    }

    public Reserva(Reserva reserva) {
        idReserva = reserva.idReserva;
        livro = reserva.livro;
        colaborador = reserva.colaborador;
        dataReserva = reserva.dataReserva;
    }

    public Reserva(int idReserva, Livro livro, Colaborador colaborador, Date dataReserva) {
        this.idReserva = idReserva;
        this.livro = livro;
        this.colaborador = colaborador;
        this.dataReserva = dataReserva;
    }

    //geters e sters 
    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro Livro) {
        this.livro = Livro;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador Colaborador) {
        this.colaborador = Colaborador;
    }

    public Date getdataReserva() {
        return dataReserva;
    }

    public void setdataReserva(Date dataReserva) {
        this.dataReserva = dataReserva;
    }

    @Override
    public String toString() {
        return String.format("%d;%d;%d;%s",
                idReserva,
                livro.getIdLivro(),
                colaborador.getIdColaborador(),
                new SimpleDateFormat("dd/MM/yyyy").format(dataReserva).toString()
        );
    }
}
