/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import classes.Colaborador;
import classes.ColunaGrid;
import classes.Renderer;
import enumeradores.EnumCadastro;
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

    public ArrayList<ColunaGrid> getColunasParaGrid(EnumCadastro cadastro) {

        // Colunas do grid
        colunas = new ArrayList<>();
        switch (cadastro.toString()) {
            case "AREACONHECIMENTO":
                break;
                
            case "AUTOR":
                break;

            case "COLABORADOR":
                addColunasColaborador();
                break;

            case "CONFIGURACAO":
                break;

            case "EDITORA":
                break;

            case "EMPRESTIMO":
                break;

            case "EXEMPLAR":
                break;

            case "LIVRO":
                break;

            case "RESERVA":
                break;
        }

        return colunas;
    }

    public String[][] getLinhasParaGrid(Object colecao, EnumCadastro cadastro) throws Exception {

        switch (cadastro.toString()) {
            case "AREACONHECIMENTO":
                break;

            case "AUTOR":
                break;

            case "COLABORADOR":
                addLinhasColaborador(colecao);
                break;

            case "CONFIGURACAO":
                break;

            case "EDITORA":
                break;

            case "EMPRESTIMO":
                break;

            case "EXEMPLAR":
                break;

            case "LIVRO":
                break;

            case "RESERVA":
                break;
        }

        return linhas;
    }

    private void addLinhasColaborador(Object colecao) {

        ArrayList<Colaborador> colaboradores = (ArrayList<Colaborador>) colecao;

        // Linhas do grid
        linhas = new String[colaboradores.size()][10];

        int i = 0;
        for (Colaborador c : colaboradores) {
            linha = new String[]{
                String.format("%04d", c.getIdColaborador()),
                c.getNomeColaborador(),
                c.getPerfil().toString(),
                String.format("%06d", c.getMatricula()),
                c.getCargo().toString(),
                c.getOab(),
                c.getEmail(),
                c.getTelefone(),
                c.getStatus().toString(),
                String.format("%.2f", 0.0)
            };
            linhas[i] = linha;
            i++;
        }
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
        colunas.add(new ColunaGrid("Saldo devedor", renderer.getRendererDireita()));
    }
}
