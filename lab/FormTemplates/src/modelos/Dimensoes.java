/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelos;

import java.awt.Dimension;
import javax.swing.JFrame;
import interfaces.IDimensoes;

/**
 *
 * @author vovostudio
 */
public class Dimensoes implements IDimensoes {

    private int largura = 1366;
    private int altura = 768;

    @Override
    public void dimensionarJanelaPrincipal(JFrame janela) {
        janela.setSize(new Dimension(largura, altura));
    }
}
