/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import enumeradores.EnumTipoStatus;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author vovostudio
 */
public class Exemplar extends Livro {

    //atributos 
    //-------------------------------------
    private int idExemplar = 0;
    private EnumTipoStatus statusExemplar = null;
    private Date dataAquisicao = null;
    private float precoCompra = 0;
    private String motivoDesativado = "";
    //-------------------------------------

    //-------------------------------------
    //contrutores 
    public Exemplar() {

    }

    public Exemplar(Exemplar exemplar) {
        idExemplar = exemplar.idExemplar;
        statusExemplar = exemplar.statusExemplar;
        dataAquisicao = exemplar.dataAquisicao;
        precoCompra = exemplar.precoCompra;
        motivoDesativado = exemplar.motivoDesativado;
    }

    public Exemplar(int idExemplar,
            Livro livro,
            EnumTipoStatus statusExemplar,
            Date dataAquisicao,
            float precoCompra,
            String motivoDesativado
    ) {
        this.idExemplar = idExemplar;
        idLivro = livro.idLivro;
        editora = livro.editora;
        autor = livro.autor;
        areaConhecimento = livro.areaConhecimento;
        titulo = livro.titulo;
        descricaoLivro = livro.descricaoLivro;
        edicao = livro.edicao;
        isbn = livro.isbn;
        anoPublicacao = livro.anoPublicacao;
        this.statusExemplar = statusExemplar;
        this.dataAquisicao = dataAquisicao;
        this.precoCompra = precoCompra;
        this.motivoDesativado = motivoDesativado;
    }

    public int getIdExemplar() {
        return idExemplar;
    }

    public void setIdExemplar(int idExemplar) {
        this.idExemplar = idExemplar;
    }

    public EnumTipoStatus getStatusExemplar() {
        return statusExemplar;
    }

    public void setStatusExemplar(EnumTipoStatus statusExemplar) {
        this.statusExemplar = statusExemplar;
    }

    public Date getDataAquisicao() {
        return dataAquisicao;
    }

    public void setDataAquisicao(Date dataAquisicao) {
        this.dataAquisicao = dataAquisicao;
    }

    public float getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(float precoCompra) {
        this.precoCompra = precoCompra;
    }

    public String getMotivoDesativado() {
        return motivoDesativado;
    }

    public void setMotivoDesativado(String motivoDesativado) {
        this.motivoDesativado = motivoDesativado;
    }

    @Override
    public String toString() {
        return String.format("%d;%d;%d;%s;%.2f;%s;",
                idExemplar,
                idLivro,
                statusExemplar.ordinal(),
                new SimpleDateFormat("dd/MM/yyyy").format(dataAquisicao),
                precoCompra,
                motivoDesativado
        );
    }
}
