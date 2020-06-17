/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import classes.Emprestimo;
import enumeradores.EnumOpcaoComprovante;
import java.util.Date;

/**
 *
 * @author vovostudio
 */
public interface IControleEmprestimo extends ICRUDEmprestimo {

    float calcularSaldoDevedor(int idColaborador, Date dataDevolucao) throws Exception;

    int calcularDiasDeAtraso(Date dataEmprestimo, Date dataDevolucao) throws Exception;

    void validarEmprestimo(Emprestimo emprestimo) throws Exception;

    boolean excluirReservaDeLivroEmprestado(Emprestimo emprestimo) throws Exception;

    String comprovante(Emprestimo emprestimo, EnumOpcaoComprovante opcaoComprovante);
}
