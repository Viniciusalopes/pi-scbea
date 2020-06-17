/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import classes.AreaConhecimento;
import classes.Autor;
import classes.Colaborador;
import classes.Editora;
import classes.Emprestimo;
import classes.Exemplar;
import classes.Livro;
import classes.Reserva;
import java.text.Collator;
import java.util.Comparator;
import java.util.Locale;

/**
 *
 * @author vovostudio
 *
 * // FONTES:
 * https://www.guj.com.br/t/ordenando-um-arraylist-de-objetos-por-mais-de-um-atributo/73074/6
 * https://www.guj.com.br/t/ignorar-acentuacao-e-tamanho-de-letra/137868
 * http://www.feltex.com.br/felix/fazer-ordenacao-string-acentos-java/
 */
public class ColecaoUtil {

    private static Collator collator = Collator.getInstance(new Locale("pt", "BR"));

    public static Comparator<AreaConhecimento> getComparadorAreaConhecimentoDescricaoCresc() {

        collator.setStrength(Collator.PRIMARY);
        return new Comparator<AreaConhecimento>() {

            @Override
            public int compare(AreaConhecimento o1, AreaConhecimento o2) {
                return collator.compare(o1.getDescricaoAreaConhecimento(), o2.getDescricaoAreaConhecimento());
            }
        };
    }

    public static Comparator<Autor> getComparadorAutorNomeCresc() {

        collator.setStrength(Collator.PRIMARY);
        return new Comparator<Autor>() {

            @Override
            public int compare(Autor o1, Autor o2) {
                return collator.compare(o1.getNomeAutor(), o2.getNomeAutor());
            }
        };
    }

    public static Comparator<Colaborador> getComparadorColaboradorNomeCresc() {

        collator.setStrength(Collator.PRIMARY);
        return new Comparator<Colaborador>() {

            @Override
            public int compare(Colaborador o1, Colaborador o2) {
                return collator.compare(o1.getNomeColaborador(), o2.getNomeColaborador());
            }
        };
    }

    public static Comparator<Editora> getComparadorEditoraNomeCresc() {

        collator.setStrength(Collator.PRIMARY);
        return new Comparator<Editora>() {

            @Override
            public int compare(Editora o1, Editora o2) {
                return collator.compare(o1.getNomeEditora(), o2.getNomeEditora());
            }
        };
    }

    public static Comparator<Exemplar> getComparadorExemplarTituloCresc() {

        collator.setStrength(Collator.PRIMARY);
        return new Comparator<Exemplar>() {

            @Override
            public int compare(Exemplar o1, Exemplar o2) {
                return collator.compare(o1.getTitulo(), o2.getTitulo());
            }
        };
    }

    public static Comparator<Livro> getComparadorLivroTituloCresc() {

        collator.setStrength(Collator.PRIMARY);
        return new Comparator<Livro>() {

            @Override
            public int compare(Livro o1, Livro o2) {
                return collator.compare(o1.getTitulo(), o2.getTitulo());
            }
        };
    }

    public static Comparator<Reserva> getComparadorReservaColaboradorCresc() {
        collator.setStrength(Collator.PRIMARY);
        return new Comparator<Reserva>() {

            @Override
            public int compare(Reserva o1, Reserva o2) {
                return collator.compare(o1.getColaborador().getNomeColaborador(), o2.getColaborador().getNomeColaborador());
            }
        };
    }
    
     public static Comparator<Emprestimo> getComparadorEmprestimoColaboradorCresc() {
        collator.setStrength(Collator.PRIMARY);
        return new Comparator<Emprestimo>() {

            @Override
            public int compare(Emprestimo o1, Emprestimo o2) {
                return collator.compare(o1.getColaborador().getNomeColaborador(), o2.getColaborador().getNomeColaborador());
            }
        };
    }
}
