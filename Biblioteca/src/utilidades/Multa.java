/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import classes.Emprestimo;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import telas.Vai;

/**
 *
 * @author vovostudio
 */
public class Multa {

    public float saldoDevedor(int idColaborador) throws Exception {
        float saldo = 0;
        int dias = 0;
        colecaoEmprestimo = controleEmprestimo.listar();
        for (Emprestimo emprestimo : colecaoEmprestimo) {
            if (emprestimo.getColaborador().getIdColaborador() == idColaborador
                    && emprestimo.getDataDevolucao() != null) {
                dias = diasDeAtraso(emprestimo.getDataEmprestimo());
                for (int i = 0; i < dias; i++) {
                    saldo += (dias * Vai.CONFIGURACAO.getValorMultaDiaria());
                }
            }
        }
        return saldo;
    }

    public int diasDeAtraso(Date dataEmprestimo) throws Exception {
        formatoData = new SimpleDateFormat("dd/MM/yyyy");
        Calendar dataPrevista = Calendar.getInstance();
        Calendar hoje = Calendar.getInstance();

        dataPrevista.setTime(formatoData.parse(dataEmprestimo.toString()));
        hoje.setTime(formatoData.parse(new Date().toString()));

        return hoje.get(Calendar.DAY_OF_YEAR)
                + Vai.CONFIGURACAO.getDiasDeEmprestimo()
                - dataPrevista.get(Calendar.DAY_OF_YEAR);
    }
}
