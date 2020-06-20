/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package telas;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author vovostudio
 */
public class ComunicadorTCP {

    private Socket conexao;
    private ServerSocket ouvir;
    private DataInputStream receberMsg;
    private DataOutputStream enviarMsg;

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //Construtor Cliente
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    public ComunicadorTCP(String ipServer, int porta) throws IOException {
        this.conexao = new Socket(ipServer, porta);
        this.receberMsg = new DataInputStream(this.conexao.getInputStream());
        this.enviarMsg = new DataOutputStream(this.conexao.getOutputStream());
    }

    //Construtor Servidor
    public ComunicadorTCP(int porta) throws Exception {
        this.ouvir = new ServerSocket(porta);
        this.conexao = this.ouvir.accept();
        this.receberMsg = new DataInputStream(this.conexao.getInputStream());
        this.enviarMsg = new DataOutputStream(this.conexao.getOutputStream());
    }
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //Get and Set
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

    public Socket getSocket() throws Exception {
        this.conexao = ouvir.accept();
        return this.conexao;
    }

    public ServerSocket getServerSocket() throws IOException {
        return this.ouvir;
    }

    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //Metodos
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //envia uma mensagem texto utilizando o objeto DataOutpuStream
    public void enviarMensagem(String mensagem) throws Exception {
        this.enviarMsg.writeUTF(mensagem);
        this.enviarMsg.flush();
    }

    //recebe uma mensagem texto utilizando o objeto DataInputStream
    public String receberMensagem() throws Exception {
        String msg = this.receberMsg.readUTF();
        return msg;
    }

    public void fecharConexao() throws Exception {
        this.conexao.close();
    }
}
