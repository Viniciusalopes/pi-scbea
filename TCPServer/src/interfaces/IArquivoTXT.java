/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import classes.ArquivoTXT;
import java.util.ArrayList;

/**
 *
 * @author vovostudio
 */
public interface IArquivoTXT {

    ArrayList<String> lerArquivo() throws Exception;

    void incluirLinha(String linha) throws Exception;

    void alterarLinha(String linhaAntes, String linhaDepois) throws Exception;

    void excluirLinha(String linha) throws Exception;
}
