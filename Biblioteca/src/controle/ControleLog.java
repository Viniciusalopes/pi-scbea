/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Log;
import enumeradores.EnumAcao;
import enumeradores.EnumCadastro;
import interfaces.ICRUDLog;
import java.util.ArrayList;
import persistencia.PersistenciaLog;

/**
 *
 * @author vovostudio
 */
public class ControleLog implements ICRUDLog {

    private ICRUDLog persistencia = null;
    private ArrayList<Log> colecao = null;

    public ControleLog() throws Exception {
        persistencia = new PersistenciaLog();
    }

    @Override
    public ArrayList<Log> listar() throws Exception {
        return persistencia.listar();
    }

    @Override
    public void incluir(EnumAcao acao, EnumCadastro cadastro, String registro) throws Exception {
        persistencia.incluir(acao, cadastro, registro);
    }
}
