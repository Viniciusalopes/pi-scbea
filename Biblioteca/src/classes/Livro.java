/*
Livro
- idLivro : int
- editora : Editora
- autor : Autor
- areaConhecimento : AreaConhecimento
- titulo : String
- descricaoLivro : String
- isbn : String
- anoPublicacao : int
--GETERS--&--SETERS--
+ get() : void
+ set() : void
---TO-SSTRING----
+ toString() : void
 */
package classes;
/**
 * CLASSE LIVRO
 */
public class Livro {
//-------------------------------------
    //atributos 
    private int idLivro;
    private Editora editora;
    private Autor autor;
    private AreaConhecimento areaConhecimento;
    private String titulo;
    private String descricaoLivro;
    private String isbn;
    private int anoPublicacao;
 //-------------------------------------
 //construtores 
 //-------------------------------------
    public Livro(){
        
    }
    public Livro (Livro livro){
        //-------------------------------------
        idLivro = livro.idLivro;
        Editora = livro.Editora;
        Autor = livro.Autor;
        AreaConhecimento = livro.AreaConhecimento;
        titulo = livro.titulo;
        descricaoLivro = livro.descricaoLivro;
        isbn = livro.isbn;
        anoPublicacao = livro.anoPublicacao;
        //-------------------------------------
        
        
    }
    public Livro (int idLivro , editora Editora ,Autor auto , AreaConhecimento areaConhecimento
    String titulo , String descricaoLivro , String isbn , int anoPublicacao ){
    
    
}
    public int getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }

    public editora getEditora() {
        return Editora;
    }

    public void setEditora(editora Editora) {
        this.Editora = Editora;
    }

    public autor getAutor() {
        return Autor;
    }

    public void setAutor(autor Autor) {
        this.Autor = Autor;
    }

    public areaConhecimento getAreaConhecimento() {
        return AreaConhecimento;
    }

    public void setAreaConhecimento(areaConhecimento AreaConhecimento) {
        this.AreaConhecimento = AreaConhecimento;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricaoLivro() {
        return descricaoLivro;
    }

    public void setDescricaoLivro(String descricaoLivro) {
        this.descricaoLivro = descricaoLivro;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public void setAnoPublicacao(int anoPublicacao) {
        this.anoPublicacao = anoPublicacao;
    }
    
}
