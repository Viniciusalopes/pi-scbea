/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author vovostudio
 */
public class PersistenciaComunicadorTCP {

    private Socket conexao;
    private ServerSocket ouvir;
    private DataInputStream receberMsg;
    private DataOutputStream enviarMsg;

    // Construtor Cliente
    public PersistenciaComunicadorTCP(String ipServer, int porta) throws Exception {
        this.conexao = new Socket(ipServer, porta);
        this.receberMsg = new DataInputStream(conexao.getInputStream());
        this.enviarMsg = new DataOutputStream(conexao.getOutputStream());
    }

    // Envia uma mensagem de texto utilizando o objeto DataOutputStream
    public void enviarMensagem(String mensagem) throws Exception {
        this.enviarMsg.writeUTF(mensagem);
        this.enviarMsg.flush();
    }

    // Recebe uma mensagem de texto utilizando o objeto DataInputStream
    public String receberMensagem() throws Exception {
        return this.receberMsg.readUTF();
    }
    
    public void FecharConexao() throws Exception{
        this.conexao.close();
    }
}
