/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author vovostudio
 */
public class Renderer {

    // Formatação de colunas do grid
    private DefaultTableCellRenderer rendererCentro = null;
    private DefaultTableCellRenderer rendererDireita = null;
    private DefaultTableCellRenderer rendererEsquerda = null;

    public Renderer() {
        // Formatação de colunas
        rendererCentro = new DefaultTableCellRenderer();
        rendererCentro.setHorizontalAlignment(SwingConstants.CENTER);

        rendererDireita = new DefaultTableCellRenderer();
        rendererDireita.setHorizontalAlignment(SwingConstants.RIGHT);

        rendererEsquerda = new DefaultTableCellRenderer();
        rendererEsquerda.setHorizontalAlignment(SwingConstants.LEFT);
    }

    public DefaultTableCellRenderer getRendererCentro() {
        return rendererCentro;
    }

    public void setRendererCentro(DefaultTableCellRenderer rendererCentro) {
        this.rendererCentro = rendererCentro;
    }

    public DefaultTableCellRenderer getRendererDireita() {
        return rendererDireita;
    }

    public void setRendererDireita(DefaultTableCellRenderer rendererDireita) {
        this.rendererDireita = rendererDireita;
    }

    public DefaultTableCellRenderer getRendererEsquerda() {
        return rendererEsquerda;
    }

    public void setRendererEsquerda(DefaultTableCellRenderer rendererEsquerda) {
        this.rendererEsquerda = rendererEsquerda;
    }

}
