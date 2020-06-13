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
    private int[][] larguras = null;

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
        colunas.add(new ColunaGrid("ID", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Título", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Autor", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Editora", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Edição", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Ano", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Área de Conhecimento", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("ISBN", renderer.getRendererCentro()));
    }

    private void addColunasAreaConhecimento() {
        colunas.add(new ColunaGrid("ID", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("CDD", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Descrição da área de conhecimento"));

    }

    private void addColunasEditora() {
        colunas.add(new ColunaGrid("ID", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Nome"));
    }

    private void addColunasAutor() {
        colunas.add(new ColunaGrid("ID", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Nome"));
    }

    private void addColunasColaborador() {
        colunas.add(new ColunaGrid("ID", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Nome"));
        colunas.add(new ColunaGrid("Perfil", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Matrícula", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Cargo", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("OAB", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("E-mail"));
        colunas.add(new ColunaGrid("Telefone", renderer.getRendererDireita()));
        colunas.add(new ColunaGrid("Status", renderer.getRendererCentro()));
    }

    private void addColunasEmprestimo() {
        colunas.add(new ColunaGrid("ID", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Colaborador"));
        colunas.add(new ColunaGrid("Livro"));
        colunas.add(new ColunaGrid("Data Empréstimo ", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Status", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Data Devolução", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Multa", renderer.getRendererCentro()));
    }

    private void addColunasReserva() {
        colunas.add(new ColunaGrid("ID", renderer.getRendererDireita()));
        colunas.add(new ColunaGrid("Livro"));
        colunas.add(new ColunaGrid("Colaborador"));
        colunas.add(new ColunaGrid("Data"));
    }

    private void addColunasLog() {
        colunas.add(new ColunaGrid("Data", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Usuário"));
        colunas.add(new ColunaGrid("Ação", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Cadastro", renderer.getRendererCentro()));
        colunas.add(new ColunaGrid("Registro"));
    }

    //--- FIM COLUNAS DO GRID -------------------------------------------------|
    //
    //--- LARGURA DAS COLUNAS DO GRID ----------------------------------------->
    //
    public int[][] getLarguraDasColunasParaGrid(EnumCadastro cadastro) {

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
        larguras = new int[8][3];
        larguras[0] = new int[]{60, 60, 60};
        larguras[1] = new int[]{60, 60, 60};
        larguras[2] = new int[]{60, 60, 60};
        larguras[3] = new int[]{60, 60, 60};
        larguras[4] = new int[]{60, 60, 60};
        larguras[5] = new int[]{60, 60, 60};
        larguras[6] = new int[]{60, 60, 60};
        larguras[7] = new int[]{60, 60, 60};
    }

    private void addLargurasColunasAreaConhecimento() {
        larguras = new int[3][3];
        larguras[0] = new int[]{60, 60, 60};
        larguras[1] = new int[]{60, 60, 60};
        larguras[2] = new int[]{60, 60, 60};
    }

    private void addLargurasColunasEditora() {
        larguras = new int[2][3];
        larguras[0] = new int[]{60, 60, 60};
        larguras[1] = new int[]{60, 60, 60};
    }

    private void addLargurasColunasAutor() {
        larguras = new int[2][3];
        larguras[0] = new int[]{60, 60, 60};
        larguras[1] = new int[]{60, 60, 60};
    }

    private void addLargurasColunasColaborador() {
        larguras = new int[9][3];
        larguras[0] = new int[]{60, 60, 60};
        larguras[1] = new int[]{60, 60, 60};
        larguras[2] = new int[]{60, 60, 60};
        larguras[3] = new int[]{60, 60, 60};
        larguras[4] = new int[]{60, 60, 60};
        larguras[5] = new int[]{60, 60, 60};
        larguras[6] = new int[]{60, 60, 60};
        larguras[7] = new int[]{60, 60, 60};
        larguras[8] = new int[]{60, 60, 60};
    }

    private void addLargurasColunasEmprestimo() {
        larguras = new int[7][3];
        larguras[0] = new int[]{60, 60, 60};
        larguras[1] = new int[]{60, 60, 60};
        larguras[2] = new int[]{60, 60, 60};
        larguras[3] = new int[]{60, 60, 60};
        larguras[4] = new int[]{60, 60, 60};
        larguras[5] = new int[]{60, 60, 60};
        larguras[6] = new int[]{60, 60, 60};
    }

    private void addLargurasColunasReserva() {
        larguras = new int[4][3];
        larguras[0] = new int[]{60, 60, 60};
        larguras[1] = new int[]{60, 60, 60};
        larguras[2] = new int[]{60, 60, 60};
        larguras[3] = new int[]{60, 60, 60};
    }

    private void addLargurasColunasLog() {
        larguras = new int[5][3];
        larguras[0] = new int[]{60, 60, 60};
        larguras[1] = new int[]{60, 60, 60};
        larguras[2] = new int[]{60, 60, 60};
        larguras[3] = new int[]{60, 60, 60};
        larguras[4] = new int[]{60, 60, 60};
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
                String.format("%06d", c.getMatricula()),
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
                r.getLivro().getTitulo(),
                r.getColaborador().getNomeColaborador(),
                formatoData.format(r.getdataReserva()).toString()
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
                l.getRegistro()
            };
            linhas[cont] = linha;
            cont++;
        }
    }
    //--- FIM LINHAS DO GRID --------------------------------------------------|

}
