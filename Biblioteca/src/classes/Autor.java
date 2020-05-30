/*
Autor
- idAutor : int
- nomeAutor : String
--SETRS-&-GETERS--
+ get() : void
+ set() : void
--To String----
+ toString() : String

 */
package classes;

/**
 * classe autor
 */
public class Autor {

    // Atributos
    private int idAutor;
    private String nomeAutor;

    // Construtores
    public Autor() {
    }

    public Autor(Autor autor) {
        idAutor = autor.idAutor;
        nomeAutor = autor.nomeAutor;
    }

    public Autor(int idAutor, String nomeAutor) {
        this.idAutor = idAutor;
        this.nomeAutor = nomeAutor;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public String getNomeAutor() {
        return nomeAutor;
    }

    public void setNomeAutor(String nomeAutor) {
        this.nomeAutor = nomeAutor;
    }

    @Override
    public String toString() {
        return (idAutor + ";" +  nomeAutor);
    }
}
