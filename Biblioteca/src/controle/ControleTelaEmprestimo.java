/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Colaborador;
import classes.Emprestimo;
import classes.EmprestimosDoColaborador;
import classes.Exemplar;
import classes.Livro;
import classes.Reserva;
import classes.ReservasDoColaborador;
import interfaces.ICRUDColaborador;
import interfaces.ICRUDEmprestimo;
import interfaces.ICRUDExemplar;
import interfaces.ICRUDLivro;
import interfaces.ICRUDReserva;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author vovostudio
 */
public class ControleTelaEmprestimo {

    //--- ATRIBUTOS ----------------------------------------------------------->
    private ICRUDColaborador controleColaborador = null;
    private ICRUDLivro controleLivro = null;
    private ICRUDExemplar controleExemplar = null;

    private ICRUDEmprestimo controleEmprestimo = null;
    private ICRUDReserva controleReserva = null;

    private ArrayList<Colaborador> colecaoColaborador = null;
    private ArrayList<Livro> colecaoLivro = null;
    private ArrayList<Exemplar> colecaoExemplar = null;

    private ArrayList<Emprestimo> colecaoEmprestimo = null;
    private ArrayList<Reserva> colecaoReserva = null;

    private EmprestimosDoColaborador emprestimosDoColaborador = null;
    private ReservasDoColaborador reservasDoColaborador = null;

    private Emprestimo emprestimo = null;
    public Colaborador colaborador = null;

    private String[][] matriz = null;
    private String[] vetor = null;

    private int qtdLinhas = 0;
    private int qtdColunas = 0;
    private int cont = 0;

    private SimpleDateFormat formatoData = null;

    //--- FIM ATRIBUTOS -------------------------------------------------------|
    //
    //--- CONSTRUTOR ---------------------------------------------------------->
    //
    public ControleTelaEmprestimo() throws Exception {
        controleColaborador = new ControleColaborador();
        controleLivro = new ControleLivro();
        controleExemplar = new ControleExemplar();

        controleEmprestimo = new ControleEmprestimo();
        controleReserva = new ControleReserva();

        formatoData = new SimpleDateFormat("dd/MM/yyy");
    }

    //--- FIM CONSTRUTOR ------------------------------------------------------|
    //
    //--- MATRIZES E VETORES -------------------------------------------------->
    //
    public String[][] getMatrizColaboradores() throws Exception {
        colecaoEmprestimo = controleEmprestimo.listar();
        colecaoColaborador = controleColaborador.listar();

        qtdLinhas = colecaoColaborador.size();
        qtdColunas = 12;

        matriz = new String[qtdLinhas][qtdColunas];
        cont = 0;
        for (Colaborador c : colecaoColaborador) {

            getEmprestimosDoColaborador(c);
            getReservasDoColaborador(c);

            vetor = new String[]{ // indice
                c.getIdColaborador() + "", //  0
                c.getMatricula() + "", //  1
                c.getNomeColaborador(), //  2
                c.getCargo().toString(), //  3
                c.getOab(), //  4
                c.getEmail(), //  5
                c.getTelefone(), //  6
                c.getPerfil().toString(), //  7
                c.getStatus().toString(), //  8
                emprestimosDoColaborador.getQuantidadeEmprestimos() + "", //  9
                String.format("%.2f", emprestimosDoColaborador.getSaldoDevedor()), //  10
                reservasDoColaborador.getQuantidadeReservas() + "" //  11
            };
            matriz[cont] = vetor;
            cont++;
        }
        return matriz;
    }

    public String[][] getMatrizLivros() throws Exception {

        colecaoLivro = controleLivro.listar();

        qtdLinhas = colecaoLivro.size();
        qtdColunas = 8;

        matriz = new String[qtdLinhas][qtdColunas];
        cont = 0;
        for (Livro livro : colecaoLivro) {
            vetor = new String[]{
                livro.getIdLivro() + "",
                livro.getTitulo(),
                livro.getEditora().getNomeEditora(),
                livro.getAutor().getNomeAutor(),
                livro.getEdicao() + "",
                livro.getAnoPublicacao() + "",
                livro.getAreaConhecimento().getCdd() + " - " + livro.getAreaConhecimento().getDescricaoAreaConhecimento(),
                livro.getIsbn()
            };

            matriz[cont] = vetor;
            cont++;
        }
        return matriz;
    }

    public String[][] getMatrizExemplares() throws Exception {
        colecaoExemplar = controleExemplar.listar();

        qtdLinhas = colecaoExemplar.size();
        qtdLinhas = 14;

        matriz = new String[qtdLinhas][qtdColunas];
        cont = 0;
        for (Exemplar exemplar : colecaoExemplar) {
            vetor = new String[]{
                exemplar.getIdExemplar() + "",
                exemplar.getStatusExemplar().toString(),
                formatoData.format(exemplar.getDataAquisicao()),
                String.format("%.f2", exemplar.getPrecoCompra()),
                exemplar.getIdLivro() + "",
                exemplar.getTitulo(),
                exemplar.getEditora().getNomeEditora(),
                exemplar.getAutor().getNomeAutor(),
                exemplar.getEdicao() + "",
                formatoData.format(exemplar.getAnoPublicacao()) + "",
                exemplar.getAreaConhecimento().getCdd() + " - " + exemplar.getAreaConhecimento().getDescricaoAreaConhecimento(),
                exemplar.getIsbn()
            };
            matriz[cont] = vetor;
            cont++;
        }
        return matriz;
    }

    //--- FIM MATRIZES E VETORES ----------------------------------------------|
    //
    //-- CONSULTAS ------------------------------------------------------------>
    //
    private void getEmprestimosDoColaborador(Colaborador colaborador) {
        emprestimosDoColaborador = new EmprestimosDoColaborador(colaborador);
        for (Emprestimo e : colecaoEmprestimo) {
            if (e.getColaborador().getIdColaborador() == colaborador.getIdColaborador()) {
                emprestimosDoColaborador.incluirEmprestimo(e);
            }
        }
    }

    private void getReservasDoColaborador(Colaborador colaborador) {
        reservasDoColaborador = new ReservasDoColaborador();
    }
    //--- FIM CONSULTAS -------------------------------------------------------|
}
