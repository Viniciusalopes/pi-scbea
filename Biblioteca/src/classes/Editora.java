/*
Editora
- idEditora : int
- nomeEditora : String
- linhas : ArrayList<String>
--GETERS-&-SETERS--
+ get() : void
+ set() : void
 */
package classes;

/**
 *  lucas 
 */
public class Editora {

    //Atributos 
    //-------------------------------------
    private int idEditora = 0 ;
    private String nomeEditora = "";

    //------------------------------------
    //costrutores 
    public Editora() {

    }

    public Editora(Editora editora) {
        idEditora = editora.idEditora;
        nomeEditora = editora.nomeEditora;
    }

    public Editora(int idEditora, String nomeEditora) {
        this.idEditora = idEditora;
        this.nomeEditora = nomeEditora;
    }

    //-------------------------------------
    //geters e sters 
    //-------------------------------------
    public int getIdEditora() {
        return idEditora;
    }

    public void setIdEditora(int idEditora) {
        this.idEditora = idEditora;
    }

    public String getNomeEditora() {
        return nomeEditora;
    }

    public void setNomeEditora(String nomeEditora) {
        this.nomeEditora = nomeEditora;
    }


    @Override 
    public String toString(){//nome da editora + id da editora 
        return (idEditora + " ; "+ nomeEditora);
        
    }
}
