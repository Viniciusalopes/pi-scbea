/*
ICRUDObjeto
--------METODOS-----------------
+ listar() : ArrayList<Objeto>
+ incluir(objeto : Objeto) : void
+ atualizar(objeto : Objeto) : void
+ excluir(idObjeto : int) : void
 */
package persistencia;

import classes.ArquivoTXT;//importanto a classe do arquivo TXT
import classes.Editora;//Importanto a classe eeditora e seus atribitos 
import controle.ControleArquivoTXT;//importando a classe controle do arquivo TXT
import enumeradores.EnumArquivosBd;//importando o enumerador  do arquivo do gerenciador de banco de dados  
import interfaces.IArquivoTXT;//importando a classe de interface do ia arquivo txt 
import interfaces.ICRUDEditora;// importando a classe interface 
import java.util.ArrayList;
import telas.Vai;
import utilidades.GeradorID;

/**
 *
 * @author lucas
 */
public class PersistenciaEditora implements ICRUDEditora {
    
    //atributos 
    
    private ArquivoTXT arquivoTXT = null;
    private IArquivoTXT controleArquivoTXT = null;
    private ArrayList<Editora> editoras = null;//obejto do tipo editora 
    private Editora editora = null ;
    private ArrayList<String> linhas = null;
    
    public PersistenciaEditora() throws Exception {
        arquivoTXT = new ArquivoTXT(Vai.CONFIGURACAO.getCaminhoBdCliente(),
                EnumArquivosBd.EDITORA.getNomeArquivo());
        controleArquivoTXT = new ControleArquivoTXT(arquivoTXT);
    }

    @Override
    public ArrayList<Editora> listar() throws Exception {
        editoras = new ArrayList<>();
        linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
        for (String linha : linhas) {
            String[] dados = linha.split(";");

            Editora editora = new Editora(Integer.parseInt(dados[0]), dados[1]);
            editoras.add(editora);
        }
        return editoras;
    }

    @Override
    public void incluir(Editora editora) throws Exception {//OK
        linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
        editora.setIdEditora(GeradorID.getProximoID());
        linhas.add(editora.toString());
        arquivoTXT.setLinhas(linhas);
        controleArquivoTXT.escreverArquivo(arquivoTXT);
        GeradorID.getProximoID();

    }

    @Override
    public void alterar(Editora editora) throws Exception {//OK
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void excluir(int idEditora) throws Exception {//OK
        ArrayList<String> novasLinhas = new ArrayList<>();
        linhas = controleArquivoTXT.lerArquivo(arquivoTXT);
        for(String l : linhas){
            if (Integer.parseInt(l.split(";")[0]) != idEditora) {
                novasLinhas.add(l);
            }
        }
        arquivoTXT.setLinhas(novasLinhas);
        controleArquivoTXT.escreverArquivo(arquivoTXT);
            
        
    }

}
