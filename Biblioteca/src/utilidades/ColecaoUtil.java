/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import classes.AreaConhecimento;
import classes.Autor;
import classes.Editora;
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

    public static Comparator<AreaConhecimento> getComparatorAreaConhecimentoDescricaoCresc() {

        collator.setStrength(Collator.PRIMARY);
        return new Comparator<AreaConhecimento>() {

            @Override
            public int compare(AreaConhecimento o1, AreaConhecimento o2) {
                return collator.compare(o1.getDescricaoAreaConhecimento(), o2.getDescricaoAreaConhecimento());
            }
        };
    }

    public static Comparator<Autor> getComparatorAutorNomeCresc() {

        collator.setStrength(Collator.PRIMARY);
        return new Comparator<Autor>() {

            @Override
            public int compare(Autor o1, Autor o2) {
                return collator.compare(o1.getNomeAutor(), o2.getNomeAutor());
            }
        };
    }

    public static Comparator<Editora> getComparatorEditoraNomeCresc() {

        collator.setStrength(Collator.PRIMARY);
        return new Comparator<Editora>() {

            @Override
            public int compare(Editora o1, Editora o2) {
                return collator.compare(o1.getNomeEditora(), o2.getNomeEditora());
            }
        };
    }

}
