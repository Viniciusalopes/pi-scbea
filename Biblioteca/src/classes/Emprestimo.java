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

    // ATRIBUTOS
    private int idEmprestimo = 0;
    private Exemplar exemplar = null;
    private Colaborador colaborador = null;
    private Date dataEmprestimo = null;
    private Date dataDevolucao = null;
    private EnumTipoStatus statusEmprestimo = null;
    private float valorMulta = 0;
    private float valorPago = 0;
    private SimpleDateFormat formatoData = null;

    // CONSTRUTORES
    public Emprestimo() {
        formatoData = new SimpleDateFormat("dd/MM/yyyy");
    }

    public Emprestimo(Emprestimo emprestimo) {
        idEmprestimo = emprestimo.idEmprestimo;
        exemplar = emprestimo.exemplar;
        colaborador = emprestimo.colaborador;
        dataEmprestimo = emprestimo.dataEmprestimo;
        dataDevolucao = emprestimo.dataDevolucao;
        valorMulta = emprestimo.valorMulta;
        valorPago = emprestimo.valorPago;
        formatoData = new SimpleDateFormat("dd/MM/yyyy");
    }

    public Emprestimo(int idEmprestimo,
            Exemplar exemplar,
            Colaborador colaborador,
            Date dataEmprestimo,
            Date dataDevolucao,
            EnumTipoStatus statusEmprestimo,
            float valorMulta,
            float valorPago) {
        this.idEmprestimo = idEmprestimo;
        this.exemplar = exemplar;
        this.colaborador = colaborador;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.valorMulta = valorMulta;
        this.valorPago = valorPago;
        formatoData = new SimpleDateFormat("dd/MM/yyyy");
    }

    // MÉTODOS
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

    public float getValorPago() {
        return valorPago;
    }

    public void setValorPago(float valorPago) {
        this.valorPago = valorPago;
    }

    public float getSaldoDevedor() {
        return valorPago - valorMulta;
    }

    @Override
    public String toString() {
        return String.format("%d;%d;%d;%s;%s;%d;%.2f",
                idEmprestimo,
                exemplar,
                colaborador,
                formatoData.format(dataEmprestimo),
                formatoData.format(dataDevolucao),
                statusEmprestimo,
                valorMulta
        );

    }
}
