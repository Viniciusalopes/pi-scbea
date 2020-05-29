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
 * CLASSE Editora
 */
public class Editora {

    public int idEditora;
    public String nomeEditora;

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

}
