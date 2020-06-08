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

    private int idLivro = 0;
    private Editora editora = null;
    private Autor autor = null;
    private AreaConhecimento areaConhecimento = null;
    private String titulo = "";
    private String descricaoLivro = "";
    private int edicao = 0;
    private String isbn = "";
    private int anoPublicacao = 0;

    //-------------------------------------
    //construtores 
    //-------------------------------------
    public Livro() {

    }

    public Livro(Livro livro) {
        //-------------------------------------
        idLivro = livro.idLivro;
        editora = livro.editora;
        autor = livro.autor;
        areaConhecimento = livro.areaConhecimento;
        titulo = livro.titulo;
        descricaoLivro = livro.descricaoLivro;
        edicao = livro.edicao;
        isbn = livro.isbn;
        anoPublicacao = livro.anoPublicacao;
        //-------------------------------------

    }

    public Livro(int idLivro, Editora editora, Autor autor, AreaConhecimento areaConhecimento,
            String titulo, String descricaoLivro, int edicao, String isbn, int anoPublicacao) {
        //-------------------------------------
        this.idLivro = idLivro;
        this.editora = editora;
        this.autor = autor;
        this.areaConhecimento = areaConhecimento;
        this.titulo = titulo;
        this.descricaoLivro = descricaoLivro;
        this.edicao = edicao;
        this.isbn = isbn;
        this.anoPublicacao = anoPublicacao;
        //-------------------------------------      
    }

    //-------------------------------------
    //getrs e setrs 
    public int getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }

    public Editora getEditora() {
        return editora;
    }

    public void setEditora(Editora editora) {
        this.editora = editora;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public AreaConhecimento getAreaConhecimento() {
        return areaConhecimento;
    }

    public void setAreaConhecimento(AreaConhecimento AreaConhecimento) {
        this.areaConhecimento = AreaConhecimento;
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

    public int getEdicao() {
        return edicao;
    }

    public void setEdicao(int edicao) {
        this.edicao = edicao;
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

    @Override
    public String toString() {
        return idLivro + ";" + editora.getIdEditora() + ";" + autor.getIdAutor() + ";"
                + areaConhecimento.getIdAreaConhecimento() + ";" + titulo + ";"
                + descricaoLivro + ";" + edicao + ";" + isbn + ";" + anoPublicacao;
    }
}
