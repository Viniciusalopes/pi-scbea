/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Colaborador;
import enumeradores.EnumAcao;
import enumeradores.EnumCargo;
import interfaces.ICRUDColaborador;
import java.util.ArrayList;
import java.util.Collections;
import persistencia.PersistenciaColaborador;
import static utilidades.ColecaoUtil.getComparadorColaboradorNomeCresc;

/**
 *
 * @author vovostudio
 */
public class ControleColaborador implements ICRUDColaborador {

    private ICRUDColaborador persistencia = null;
    private ArrayList<Colaborador> colecao = null;

    public ControleColaborador() throws Exception {
        persistencia = new PersistenciaColaborador();
    }

    @Override
    public ArrayList<Colaborador> listar() throws Exception {
        colecao = persistencia.listar();
        Collections.sort(colecao, getComparadorColaboradorNomeCresc());
        return colecao;
    }

    @Override
    public Colaborador buscarPeloId(int idColaborador) throws Exception {
        return persistencia.buscarPeloId(idColaborador);
    }

    @Override
    public void incluir(Colaborador colaborador) throws Exception {
        validarColaborador(colaborador, EnumAcao.Incluir);
        persistencia.incluir(colaborador);
    }

    @Override
    public void alterar(Colaborador colaborador) throws Exception {
        validarColaborador(colaborador, EnumAcao.Editar);
        persistencia.alterar(colaborador);
    }

    @Override
    public void excluir(int idColaborador) throws Exception {
        // tem emprestimo
        // tem livro
        // é o proprio

        persistencia.excluir(idColaborador);
    }

    private void validarColaborador(Colaborador colaborador, EnumAcao enumAcao) throws Exception {
        colecao = listar();
        for (Colaborador c : colecao) {
            if (c.getMatricula() == colaborador.getMatricula()) {
                if (enumAcao.equals(EnumAcao.Incluir)
                        || enumAcao.equals(EnumAcao.Editar)
                        && c.getIdColaborador() != colaborador.getIdColaborador()) {
                    throw new Exception("Já existe um colaborador com esse número de matrícula!");
                }
            }

            if (colaborador.getCargo().equals(EnumCargo.ADVOGADO) && c.getOab().equals(colaborador.getOab())) {
                if (enumAcao.equals(EnumAcao.Incluir)
                        || enumAcao.equals(EnumAcao.Editar)
                        && c.getIdColaborador() != colaborador.getIdColaborador()) {

                    throw new Exception("Já existe um colaborador com esse número de OAB!");
                }
            }

            if ((colaborador.getEmail().trim().length() > 0) && (c.getEmail().equals(colaborador.getEmail()))) {
                if (enumAcao.equals(EnumAcao.Incluir)
                        || enumAcao.equals(EnumAcao.Editar)
                        && c.getIdColaborador() != colaborador.getIdColaborador()) {
                    throw new Exception("Já existe um colaborador com esse endereço de e-mail!");
                }
            }
        }
    }
}
