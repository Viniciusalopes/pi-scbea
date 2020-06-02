/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author vovostudio
 */
public class Mensagens extends JFrame {

    // Exibe mensagem de erro
    public void erro(Exception e) {
        JOptionPane.showMessageDialog(rootPane,
                ((e.getMessage() == null) ? e : e.getMessage()), "Opa!",
                JOptionPane.ERROR_MESSAGE);
    }

    // Exibe mensagem de informação
    public void informacao(String mensagem) {
        JOptionPane.showMessageDialog(rootPane, mensagem, "Informação!",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Exibe mensagem de alerta
    public void alerta(String mensagem) {
        JOptionPane.showMessageDialog(rootPane, mensagem, "Eita!",
                JOptionPane.WARNING_MESSAGE);
    }

    // Exibe uma mensagem de sucesso
    public void sucesso(String mensagem) {
        JOptionPane.showMessageDialog(rootPane, mensagem, "Sucesso!",
                JOptionPane.INFORMATION_MESSAGE);
    }

    // Exibe pergunta e retorna a resposta do usuário
    public int pergunta(String textoPergunta) {
        return JOptionPane.showConfirmDialog(rootPane, textoPergunta, "Confirmação:",
                JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
    }

    public int escolher(String mensagem, String[] opcoes) {
        return JOptionPane.showOptionDialog(rootPane, mensagem, "Escolha uma opção:",
                JOptionPane.CLOSED_OPTION, JOptionPane.QUESTION_MESSAGE, null, opcoes, 0);
    }
}
