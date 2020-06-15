/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import telas.Vai;

/**
 *
 * @author vovostudio
 */
public class Configuracao {

    private int limiteDeLivros = 5;
    private int diasDeEmprestimo = 7;
    private float valorMultaDiaria = 2;
    private String caminhoBdCliente = System.getProperty("user.dir") + Vai.BARRA + "BibliotecaBd" + Vai.BARRA;
    private String caminhoBdServidor = "192.168.15.3:4567";
    
    public Configuracao() {
    }

    public Configuracao(Configuracao configuracao) {
        limiteDeLivros = configuracao.limiteDeLivros;
        diasDeEmprestimo = configuracao.diasDeEmprestimo;
        valorMultaDiaria = configuracao.valorMultaDiaria;
        caminhoBdCliente = configuracao.caminhoBdCliente;
        caminhoBdServidor = configuracao.caminhoBdServidor;
    }

    public Configuracao(int limiteDeLivros, int diasDeEmprestimo, float valorMultaDiaria,
            String caminhoBdCliente, String caminhoBdServidor) {
        this.limiteDeLivros = limiteDeLivros;
        this.diasDeEmprestimo = diasDeEmprestimo;
        this.valorMultaDiaria = valorMultaDiaria;
        this.caminhoBdCliente = caminhoBdCliente;
        this.caminhoBdServidor = caminhoBdServidor;
    }

    public int getLimiteDeLivros() {
        return limiteDeLivros;
    }

    public void setLimiteDeLivros(int limiteDeLivros) {
        this.limiteDeLivros = limiteDeLivros;
    }

    public int getDiasDeEmprestimo() {
        return diasDeEmprestimo;
    }

    public void setDiasDeEmprestimo(int diasDeEmprestimo) {
        this.diasDeEmprestimo = diasDeEmprestimo;
    }

    public float getValorMultaDiaria() {
        return valorMultaDiaria;
    }

    public void setValorMultaDiaria(float valorMultaDiaria) {
        this.valorMultaDiaria = valorMultaDiaria;
    }

    public String getCaminhoBdCliente() {
        return caminhoBdCliente;
    }

    public void setCaminhoBdCliente(String caminhoBdCliente) {
        this.caminhoBdCliente = caminhoBdCliente;
    }

    public String getCaminhoBdServidor() {
        return caminhoBdServidor;
    }

    public void setCaminhoBdServidor(String caminhoBdServidor) {
        this.caminhoBdServidor = caminhoBdServidor;
    }

    @Override
    public String toString() {
        return String.format("%d;%d;%.2f;%s;%s",
                limiteDeLivros,
                diasDeEmprestimo,
                valorMultaDiaria,
                caminhoBdCliente,
                caminhoBdServidor
        );
    }
}
