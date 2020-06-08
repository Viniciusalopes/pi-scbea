/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.ArrayList;

/**
 *
 * @author vovostudio
 */
public class EmprestimosDoColaborador {

    private Colaborador colaborador = null;
    private ArrayList<Emprestimo> emprestimos = null;
    private int quantidadeEmprestimos = 0;
    private float saldoDevedor = 0;

    public EmprestimosDoColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
        emprestimos = new ArrayList<>();
        quantidadeEmprestimos = 0;
        saldoDevedor = 0;
    }

    public void incluirEmprestimo(Emprestimo emprestimo) {
        quantidadeEmprestimos++;
        saldoDevedor += calcularMulta(emprestimo);
        emprestimos.add(emprestimo);
    }

    public int getQuantidadeEmprestimos() {
        return quantidadeEmprestimos;
    }

    public float getSaldoDevedor() {
        return saldoDevedor;
    }

    private float calcularMulta(Emprestimo emprestimo) {
        return 0;
    }
}
