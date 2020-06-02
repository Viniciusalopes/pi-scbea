/*
ICRUDObjeto
--------METODOS-----------------
+ listar() : ArrayList<Objeto>
+ incluir(objeto : Objeto) : void
+ atualizar(objeto : Objeto) : void
+ excluir(idObjeto : int) : void
 */
package persistencia;

import classes.ArquivoTXT;
import classes.Editora;
import controle.ControleArquivoTXT;
import enumeradores.EnumArquivosBd;
import interfaces.IArquivoTXT;
import interfaces.ICRUDEditora;
import java.util.ArrayList;
import telas.Vai;
import utilidades.GeradorID;

/**
 *
 * @author lucas
 */
public class PersistenciaEditora implements ICRUDEditora {

    private ArquivoTXT arquivoTXT = null;
    private IArquivoTXT controleArquivoTXT = null;
    //ele e um arquivo txt 
    private ArrayList<String> linhas = null;
    private ArrayList<Editora> editoras = null;

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
        editora.setIdEditora(GeradorID.getProximoId());
        linhas.add(editora.toString());
        arquivoTXT.setLinhas(linhas);
        controleArquivoTXT.escreverArquivo(arquivoTXT);
        GeradorID.getProximoId();

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
