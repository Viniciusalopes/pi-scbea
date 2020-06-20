/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.service;

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

    // Construtor Servidor
    public ComunicadorTCP(int porta) throws Exception {
        this.ouvir = new ServerSocket(porta);
        this.conexao = ouvir.accept(); // Retorna o socket da conexão do cliente com o servidor
        this.receberMsg = new DataInputStream(conexao.getInputStream());
        this.enviarMsg = new DataOutputStream(conexao.getOutputStream());
    }

    // Envia uma mensagem de texto utilizando o objeto DataOutputStream
    public void enviarMensagem(String mensagem) throws Exception {
        this.enviarMsg.writeUTF(mensagem);
        this.enviarMsg.flush();
    }

    // Recebe uma mensagem de texto utilizando o objeto DataInputStream
    public String receberMensagem() throws IOException {
        return this.receberMsg.readUTF();
    }

    public ServerSocket getServerSocket() throws Exception {
        return this.ouvir;
    }
}
