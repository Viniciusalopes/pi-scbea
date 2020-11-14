/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author vovostudio
 */
public class ColunaGrid {

    private String nome;
    private DefaultTableCellRenderer alinhamento;

    public ColunaGrid() {
    }

    public ColunaGrid(String nome) {
        this.nome = nome;
    }

    public ColunaGrid(String nome, DefaultTableCellRenderer alinhamento) {
        this.nome = nome;
        this.alinhamento = alinhamento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public DefaultTableCellRenderer getAlinhamento() {
        return alinhamento;
    }

    public void setAlinhamento(DefaultTableCellRenderer alinhamento) {
        this.alinhamento = alinhamento;
    }

}
