/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import classes.Log;
import enumeradores.EnumAcao;
import enumeradores.EnumCadastro;
import java.util.ArrayList;

/**
 *
 * @author vovostudio
 */
public interface ICRUDLog {

    ArrayList<Log> listar() throws Exception;

    void incluir(EnumAcao acao, EnumCadastro cadastro, String registro, String observacao) throws Exception;
}
