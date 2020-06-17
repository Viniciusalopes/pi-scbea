/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Colaborador;
import classes.Emprestimo;
import classes.Exemplar;
import classes.Livro;
import classes.Reserva;
import enumeradores.EnumOpcaoComprovante;
import enumeradores.EnumTipoStatus;
import interfaces.ICRUDColaborador;
import interfaces.ICRUDExemplar;
import interfaces.ICRUDLivro;
import interfaces.ICRUDReserva;
import interfaces.IControleEmprestimo;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author vovostudio
 */
public class ControleTelaEmprestimo {

    //--- ATRIBUTOS ----------------------------------------------------------->
    // Controles
    private ICRUDColaborador controleColaborador = null;
    private ICRUDLivro controleLivro = null;
    private ICRUDExemplar controleExemplar = null;
    private IControleEmprestimo controleEmprestimo = null;
    private ICRUDReserva controleReserva = null;

    // Listas
    private ArrayList<Colaborador> colecaoColaborador = null;
    private ArrayList<Livro> colecaoLivro = null;
    private ArrayList<Exemplar> colecaoExemplar = null;

    // Objetos
    private Colaborador colaborador = null;
    private Livro livro = null;
    private Exemplar exemplar = null;
    private Emprestimo emprestimo = null;
    private Reserva reserva = null;

    // Matrizes e vetores
    private String[][] matriz = null;
    private String[] vetor = null;

    // Variáveis
    private int qtdLinhas = 0;
    private int qtdColunas = 0;
    private int cont = 0;

    // Utilidades
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
        formatoData = new SimpleDateFormat("dd/MM/yyyy");
    }

    //--- FIM CONSTRUTOR ------------------------------------------------------|
    //
    //--- MATRIZES E VETORES -------------------------------------------------->
    //
    public String[][] getMatrizColaboradores() throws Exception {
        colecaoColaborador = controleColaborador.listar();
        preencherMatrizColaborador();
        return matriz;
    }

    private void preencherMatrizColaborador() throws Exception {
        qtdLinhas = colecaoColaborador.size();
        qtdColunas = 12;

        matriz = new String[qtdLinhas][qtdColunas];
        cont = 0;
        for (Colaborador c : colecaoColaborador) {

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
                String.format("%.2f", controleEmprestimo.calcularSaldoDevedor(qtdColunas, new Date())) // 9 
            };
            matriz[cont] = vetor;
            cont++;
        }
    }

    public String[][] getMatrizLivros() throws Exception {
        colecaoLivro = controleLivro.listar();
        preencherMatrizLivro();
        return matriz;
    }

    private void preencherMatrizLivro() throws Exception {
        qtdLinhas = colecaoLivro.size();
        qtdColunas = 9;

        matriz = new String[qtdLinhas][qtdColunas];
        cont = 0;
        for (Livro livro : colecaoLivro) {
            vetor = new String[]{
                livro.getIdLivro() + "", // 0
                livro.getTitulo(), // 1
                livro.getAutor().getNomeAutor(), // 2
                livro.getEditora().getNomeEditora(), // 3
                livro.getEdicao() + "", // 4
                livro.getAnoPublicacao() + "", // 5
                livro.getAreaConhecimento().getCdd() + " - " + livro.getAreaConhecimento().getDescricaoAreaConhecimento(), // 6
                livro.getIsbn(), // 7
                livro.getDescricaoLivro() // 8
            };

            matriz[cont] = vetor;
            cont++;
        }
    }

    public String[][] getMatrizExemplares(int idLivro) throws Exception {
        colecaoExemplar = new ControleExemplar().exemplaresDoLivro(idLivro);
        preencherMatrizExemplar();
        return matriz;
    }

    private void preencherMatrizExemplar() throws Exception {

        qtdLinhas = colecaoExemplar.size();
        qtdColunas = 3;

        matriz = new String[qtdLinhas][qtdColunas];
        cont = 0;
        for (Exemplar exemplar : colecaoExemplar) {

            vetor = new String[]{
                exemplar.getIdExemplar() + "",
                exemplar.getStatusExemplar().toString(),
                exemplar.getMotivoDesativado(),
                exemplar.getIdLivro() + ""
            };
            matriz[cont] = vetor;
            cont++;
        }
    }

    //--- FIM MATRIZES E VETORES ----------------------------------------------|
    //
    //-- CONSULTAS ------------------------------------------------------------>
    //
    public Reserva buscarReserva(int idReserva) throws Exception {
        return controleReserva.buscarPeloId(idReserva);
    }

    public String comprovanteReserva(Reserva reserva) throws Exception {
        return new ControleReserva().comprovante(reserva);
    }

    public String comprovante(Emprestimo emprestimo, EnumOpcaoComprovante opcaoComprovante) throws Exception {
        return new ControleEmprestimo().comprovante(emprestimo, opcaoComprovante);
    }

    public Emprestimo buscarEmprestimo(int idEmprestimo) throws Exception {
        return controleEmprestimo.buscarPeloId(idEmprestimo);
    }

    private float saldoDevedor(int idColaborador) throws Exception {
        return controleEmprestimo.calcularSaldoDevedor(idColaborador, new Date());
    }

    //--- FIM CONSULTAS -------------------------------------------------------|
    //
    //--- AÇÕES --------------------------------------------------------------->
    //
    public int incluirReserva(int idColaborador, int idLivro) throws Exception {

        colaborador = controleColaborador.buscarPeloId(idColaborador);
        livro = controleLivro.buscarPeloId(idLivro);
        reserva = new Reserva(0, livro, colaborador, new Date());
        return controleReserva.incluir(reserva);
    }

    public void editarReserva(Reserva reserva, int idColaborador, int idLivro) throws Exception {
        reserva.setColaborador(controleColaborador.buscarPeloId(idColaborador));
        reserva.setLivro(controleLivro.buscarPeloId(idLivro));
        controleReserva.alterar(reserva);
    }

    public int incluirEmprestimo(int idColaborador, int idExemplar, Date dataEmprestimo) throws Exception {
        colaborador = controleColaborador.buscarPeloId(idColaborador);
        exemplar = controleExemplar.buscarPeloId(idExemplar);
        emprestimo = new Emprestimo(0, exemplar, colaborador, dataEmprestimo, null, EnumTipoStatus.PENDENTE, 0, 0);
        return controleEmprestimo.incluir(emprestimo);
    }

    public void editarEmprestimo(Emprestimo emprestimo) throws Exception {

    }

    public boolean excluirReservaDeLivroEmprestado(int idEmprestimo) throws Exception {
        return controleEmprestimo.excluirReservaDeLivroEmprestado(controleEmprestimo.buscarPeloId(idEmprestimo));
    }
    //
    //--- FIM AÇÕES -----------------------------------------------------------|
    //
}
