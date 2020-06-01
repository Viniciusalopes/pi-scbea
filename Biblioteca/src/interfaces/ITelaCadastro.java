/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import enumeradores.EnumAcao;

/**
 *
 * @author vovostudio
 */
public interface ITelaCadastro {

    void setId(int id);

    void setAcao(EnumAcao acao);

    public void setVisible(boolean b);
}
