/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;
import modelos.classes.Editora;
import modelos.interfaces.ICRUDEditora;
/**
 *
 * @author eugeniojulio
 */
public class EditoraPersistencia implements ICRUDEditora{
    private String nomeDoArquivoNoDisco;
    
    public EditoraPersistencia(String nomeArquivo){
        this.nomeDoArquivoNoDisco = nomeArquivo;
    }

    @Override
    public void incluir(Editora objeto) {
        //Colocar os comandos para gravar no arquivo texto
        System.out.println("Estou Gravando no Arquivo" + nomeDoArquivoNoDisco);
    }
    
}
