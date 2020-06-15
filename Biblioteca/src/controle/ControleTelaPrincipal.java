/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.AreaConhecimento;
import classes.Autor;
import classes.Colaborador;
import classes.ColunaGrid;
import classes.Editora;
import classes.Emprestimo;
import classes.Livro;
import classes.Log;
import classes.Renderer;
import classes.Reserva;
import enumeradores.EnumCadastro;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author vovostudio
 */
public class ControleTelaPrincipal {

    private String[][] linhas = null;
    private String linha[] = null;
    private ArrayList<ColunaGrid> colunas = null;
    private Renderer renderer = new Renderer();
    private int cont = 0;
    private int[] larguras = null;

    //--- COLUNAS DO GRID ----------------------------------------------------->
    public ArrayList<ColunaGrid> getColunasParaGrid(EnumCadastro cadastro) {

        // Colunas do grid
        colunas = new ArrayList<>();
        switch (cadastro.toString()) {
            case "AREACONHECIMENTO":
                addColunasAreaConhecimento();
                break;

            case "AUTOR":
                addColunasAutor();
                break;

            case "COLABORADOR":
                addColunasColaborador();
                break;

            case "CONFIGURACAO":
                break;

            case "EDITORA":
                addColunasEditora();
                break;

            case "EMPRESTIMO":
                addColunasEmprestimo();
                break;

            case "EXEMPLAR":
                break;

            case "LIVRO":
                addColunasLivro();
                break;

            case "RESERVA":
                addColunasReserva();
                break;

            case "LOG":
                addColunasLog();
        }

        return colunas;
    }

    private void addColunasLivro() {
        colunas.add(new ColunaGrid("ID", renderer.getRendererDireita()));
        colunas.add(new ColunaGrid("Título"));
        colunas.add(new ColunaGrid("Autor"));
        colunas.add(new ColunaGrid("Editora"));
        colunas.add(new ColunaGrid("Edição", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Ano", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Área de Conhecimento"));
        colunas.add(new ColunaGrid("ISBN", renderer.getRendererCentro()));
    }

    private void addColunasEmprestimo() {
        colunas.add(new ColunaGrid("ID", renderer.getRendererDireita()));
        colunas.add(new ColunaGrid("Colaborador"));
        colunas.add(new ColunaGrid("Livro"));
        colunas.add(new ColunaGrid("Data Empréstimo ", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Status", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Data Devolução", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Multa", renderer.getRendererDireita()));
    }

    private void addColunasReserva() {
        colunas.add(new ColunaGrid("ID", renderer.getRendererDireita()));
        colunas.add(new ColunaGrid("Data", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Colaborador"));
        colunas.add(new ColunaGrid("Livro"));
        colunas.add(new ColunaGrid("Autor"));
        colunas.add(new ColunaGrid("Editora"));
        colunas.add(new ColunaGrid("Edição", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Ano", renderer.getRendererCentro()));

    }

    private void addColunasAreaConhecimento() {
        colunas.add(new ColunaGrid("ID", renderer.getRendererDireita()));
        colunas.add(new ColunaGrid("CDD"));
        colunas.add(new ColunaGrid("Descrição da área de conhecimento"));

    }

    private void addColunasEditora() {
        colunas.add(new ColunaGrid("ID", renderer.getRendererDireita()));
        colunas.add(new ColunaGrid("Nome"));
    }

    private void addColunasAutor() {
        colunas.add(new ColunaGrid("ID", renderer.getRendererDireita()));
        colunas.add(new ColunaGrid("Nome"));
    }

    private void addColunasColaborador() {
        colunas.add(new ColunaGrid("ID", renderer.getRendererDireita()));
        colunas.add(new ColunaGrid("Nome"));
        colunas.add(new ColunaGrid("Perfil", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Matrícula", renderer.getRendererDireita()));
        colunas.add(new ColunaGrid("Cargo", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("OAB", renderer.getRendererDireita()));
        colunas.add(new ColunaGrid("E-mail"));
        colunas.add(new ColunaGrid("Telefone", renderer.getRendererDireita()));
        colunas.add(new ColunaGrid("Status", renderer.getRendererCentro()));
    }

    private void addColunasLog() {
        colunas.add(new ColunaGrid("Data", renderer.getRendererDireita()));
        colunas.add(new ColunaGrid("Usuário"));
        colunas.add(new ColunaGrid("Ação", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Cadastro", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Registro"));
        colunas.add(new ColunaGrid("Observação"));
    }

    //--- FIM COLUNAS DO GRID -------------------------------------------------|
    //
    //--- LARGURA DAS COLUNAS DO GRID ----------------------------------------->
    //
    public int[] getLarguraDasColunasParaGrid(EnumCadastro cadastro) {

        // Colunas do grid
        switch (cadastro.toString()) {
            case "AREACONHECIMENTO":
                addLargurasColunasAreaConhecimento();
                break;

            case "AUTOR":
                addLargurasColunasAutor();
                break;

            case "COLABORADOR":
                addLargurasColunasColaborador();
                break;

            case "CONFIGURACAO":
                break;

            case "EDITORA":
                addLargurasColunasEditora();
                break;

            case "EMPRESTIMO":
                addLargurasColunasEmprestimo();
                break;

            case "EXEMPLAR":
                break;

            case "LIVRO":
                addLargurasColunasLivro();
                break;

            case "RESERVA":
                addLargurasColunasReserva();
                break;

            case "LOG":
                addLargurasColunasLog();
        }

        return larguras;
    }

    private void addLargurasColunasLivro() {
        larguras = new int[8];
        larguras[0] = 60;   // ID
        larguras[1] = 200;  // Título
        larguras[2] = 200;  // Autor
        larguras[3] = 100;  // Editora
        larguras[4] = 50;   // Edição
        larguras[5] = 50;   // Ano
        larguras[6] = 150;  // Área de Conhecimento
        //larguras[7] = 60; // ISBN (está pegando o resto do tamanho total do grid)
    }

    private void addLargurasColunasEmprestimo() {
        larguras = new int[7];
        larguras[0] = 60;   // ID
        larguras[1] = 200;   // Colaborador
        larguras[2] = 200;   // Livro
        larguras[3] = 120;   // Data Empréstimo
        larguras[4] = 120;   // Status
        larguras[5] = 120;   // Data Devolução
        //larguras[6] = 60;   // Multa (está pegando o resto do tamanho total do grid)
    }

    private void addLargurasColunasReserva() {
        larguras = new int[8];
        larguras[0] = 60;   // ID
        larguras[1] = 80;   // Data
        larguras[2] = 200;  // Colaborador
        larguras[3] = 200;  // Livro
        larguras[4] = 150;  // Autor
        larguras[5] = 100;  // Editora
        larguras[6] = 60;   // Edição
      //  larguras[7] = 50;   // Ano (está pegando o resto do tamanho total do grid)

    }

    private void addLargurasColunasAreaConhecimento() {
        larguras = new int[3];
        larguras[0] = 60;   // ID
        larguras[1] = 80;   // CDD
        //larguras[2] = 300;   // Descrição (está pegando o resto do tamanho total do grid)
    }

    private void addLargurasColunasEditora() {
        larguras = new int[2];
        larguras[0] = 60;   // ID
        //larguras[1] = 60;   // Nome (está pegando o resto do tamanho total do grid)
    }

    private void addLargurasColunasAutor() {
        larguras = new int[2];
        larguras[0] = 60;   //ID
        //larguras[1] = 60;   // Nome (está pegando o resto do tamanho total do grid)
    }

    private void addLargurasColunasColaborador() {
        larguras = new int[9];
        larguras[0] = 60;    // ID
        larguras[1] = 180;   // Nome
        larguras[2] = 100;   // Perfil
        larguras[3] = 80;    // Matrícula
        larguras[4] = 100;   // Cargo
        larguras[5] = 100;   // OAB
        larguras[6] = 120;   // E-mail
        larguras[7] = 120;   // Telefone
        //larguras[8] = 60;   // Status (está pegando o resto do tamanho total do grid)
    }

    private void addLargurasColunasLog() {
        larguras = new int[6];
        larguras[0] = 200;   // Data completa
        larguras[1] = 100;   // Usuário
        larguras[2] = 90;    // Ação
        larguras[3] = 150;   // Cadastro
        larguras[4] = 200;   // Registro
        //larguras[5] = 60(está pegando o resto do tamanho total do grid)
    }

    //--- FIM LARGURA DAS COLUNAS DO GRID ------------------------------------->
    //
    //--- LINHAS DO GRID ------------------------------------------------------>
    public String[][] getLinhasParaGrid(Object colecao, EnumCadastro cadastro) throws Exception {

        switch (cadastro.toString()) {
            case "AREACONHECIMENTO":
                addLinhasAreaConhecimento(colecao);
                break;

            case "AUTOR":
                addLinhasAutor(colecao);
                break;

            case "COLABORADOR":
                addLinhasColaborador(colecao);
                break;

            case "CONFIGURACAO":
                break;

            case "EDITORA":
                addLinhasEditora(colecao);
                break;

            case "EMPRESTIMO":
                addLinhasEmprestimo(colecao);
                break;

            case "EXEMPLAR":
                break;

            case "LIVRO":
                addLinhasLivro(colecao);
                break;

            case "RESERVA":
                addLinhasReserva(colecao);
                break;

            case "LOG":
                addLinhasLog(colecao);
                break;
        }

        return linhas;
    }

    private void addLinhasLivro(Object colecao) {
        ArrayList<Livro> livros = (ArrayList<Livro>) colecao;

        // Linhas do grid
        linhas = new String[livros.size()][colunas.size()];

        cont = 0;
        for (Livro l : livros) {
            linha = new String[]{
                l.getIdLivro() + "",
                l.getTitulo(),
                l.getAutor().getNomeAutor(),
                l.getEditora().getNomeEditora(),
                l.getEdicao() + "",
                l.getAnoPublicacao() + "",
                l.getAreaConhecimento().getCdd() + "-" + l.getAreaConhecimento().getDescricaoAreaConhecimento(),
                l.getIsbn()
            };
            linhas[cont] = linha;
            cont++;
        }
    }

    private void addLinhasEmprestimo(Object colecao) {
        ArrayList<Emprestimo> emprestimos = (ArrayList<Emprestimo>) colecao;
        linhas = new String[emprestimos.size()][colunas.size()];
        cont = 0;

        for (Emprestimo e : emprestimos) {
            linha = new String[]{
                String.format("%s", e.getDataEmprestimo()),
                e.getColaborador().getNomeColaborador(),
                "e.getExemplar().getLivro().getTitulo()",
                String.format("%s", e.getDataEmprestimo()),
                e.getStatusEmprestimo().toString(),
                String.format("%s", e.getDataDevolucao()),
                String.format("%.2f", e.getValorMulta())
            };
            linhas[cont] = linha;
            cont++;
        }
    }

    private void addLinhasReserva(Object colecao) {
        ArrayList<Reserva> reservas = (ArrayList<Reserva>) colecao;
        linhas = new String[reservas.size()][colunas.size()];
        cont = 0;
        SimpleDateFormat formatoData = new SimpleDateFormat("dd/MM/yyyy");

        for (Reserva r : reservas) {
            linha = new String[]{
                r.getIdReserva() + "",
                formatoData.format(r.getdataReserva()).toString(),
                r.getColaborador().getNomeColaborador(),
                r.getLivro().getTitulo(),
                r.getLivro().getAutor().getNomeAutor(),
                r.getLivro().getEditora().getNomeEditora(),
                r.getLivro().getEdicao() + "",
                r.getLivro().getAnoPublicacao() + ""
            };
            linhas[cont] = linha;
            cont++;
        }
    }

    private void addLinhasAreaConhecimento(Object colecao) {

        ArrayList<AreaConhecimento> areasConhecimento = (ArrayList<AreaConhecimento>) colecao;

        // Linhas do grid
        linhas = new String[areasConhecimento.size()][colunas.size()];

        cont = 0;
        for (AreaConhecimento a : areasConhecimento) {
            linha = new String[]{
                a.getIdAreaConhecimento() + "",
                a.getCdd() + "",
                a.getDescricaoAreaConhecimento()
            };
            linhas[cont] = linha;
            cont++;
        }
    }

    private void addLinhasEditora(Object colecao) {
        ArrayList<Editora> editoras = (ArrayList<Editora>) colecao;
        linhas = new String[editoras.size()][colunas.size()];

        cont = 0;
        for (Editora a : editoras) {
            linha = new String[]{
                a.getIdEditora() + "",
                a.getNomeEditora()
            };
            linhas[cont] = linha;
            cont++;
        }
    }

    private void addLinhasAutor(Object colecao) {
        ArrayList<Autor> autores = (ArrayList<Autor>) colecao;
        linhas = new String[autores.size()][colunas.size()];

        cont = 0;
        for (Autor a : autores) {
            linha = new String[]{
                a.getIdAutor() + "",
                a.getNomeAutor()
            };
            linhas[cont] = linha;
            cont++;
        }
    }

    private void addLinhasColaborador(Object colecao) {

        ArrayList<Colaborador> colaboradores = (ArrayList<Colaborador>) colecao;

        // Linhas do grid
        linhas = new String[colaboradores.size()][colunas.size()];

        cont = 0;
        for (Colaborador c : colaboradores) {
            linha = new String[]{
                c.getIdColaborador() + "",
                c.getNomeColaborador(),
                c.getPerfil().toString(),
                c.getMatricula() + "",
                c.getCargo().toString(),
                c.getOab(),
                c.getEmail(),
                c.getTelefone(),
                c.getStatus().toString()
            };
            linhas[cont] = linha;
            cont++;
        }
    }

    private void addLinhasLog(Object colecao) {

        ArrayList<Log> logs = (ArrayList<Log>) colecao;

        // Linhas do grid
        linhas = new String[logs.size()][colunas.size()];

        cont = 0;
        for (Log l : logs) {
            linha = new String[]{
                String.format("%s", l.getDataLog()),
                l.getUsuario().getIdColaborador() + " - " + l.getUsuario().getNomeColaborador().split(" ")[0],
                l.getAcao().toString(),
                l.getCadastro().getNomeCadastro(),
                l.getRegistro(),
                l.getObservacao()
            };
            linhas[cont] = linha;
            cont++;
        }
    }
    //--- FIM LINHAS DO GRID --------------------------------------------------|

}
