/*
Emprestimo
- idEmprestimo : int
- exemplar : Exemplar
- colaborador : Colaborador
- dataEmprestimo : Date
- dataDevolucao : Date
- statusEmprestimo : EnumTipoStatus
- valorMulta : int
+ get() : void
+ set() : void
+ toString() : void
 */
package classes;

import enumeradores.EnumTipoStatus;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * classe emprestimo
 */
public class Emprestimo {

    private int idEmprestimo;

    private Exemplar exemplar;

    private Colaborador colaborador;

    private Date dataEmprestimo;

    private Date dataDevolucao;

    private EnumTipoStatus statusEmprestimo;

    private float valorMulta;

    public int getIdEmprestimo() {
        return idEmprestimo;
    }

    public void setIdEmprestimo(int idEmprestimo) {
        this.idEmprestimo = idEmprestimo;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public void setDataEmprestimo(Date dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public EnumTipoStatus getStatusEmprestimo() {
        return statusEmprestimo;
    }

    public void setStatusEmprestimo(EnumTipoStatus statusEmprestimo) {
        this.statusEmprestimo = statusEmprestimo;
    }

    public float getValorMulta() {
        return valorMulta;
    }

    public void setValorMulta(float valorMulta) {
        this.valorMulta = valorMulta;
    }

    @Override
    public String toString() {

        return String.format("%d;%d;%d%;%s;%s;%d;%.2f",
                idEmprestimo, exemplar.getIdExemplar(), colaborador.getIdColaborador(),
                new SimpleDateFormat("dd/MM/yyyy").format(dataEmprestimo),
                new SimpleDateFormat("dd/MM/yyyy").format(dataDevolucao),
                statusEmprestimo, valorMulta);

    }
}
