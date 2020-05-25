/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

/**
 *
 * @author vovostudio
 */
public class Configuracao {

    private int limiteDeLivros = 5;
    private int diasDeEmprestimo = 7;
    private float valorMultaDiaria = 2;
    private String caminhoBdCliente = System.getProperty("user.dir") + "/BibliotecaBd/";
    private String caminhoBdServidor = "_";

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

//    public void setCaminhoBdCliente(String caminhoBdCliente) {
//        this.caminhoBdCliente = caminhoBdCliente;
//    }
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
