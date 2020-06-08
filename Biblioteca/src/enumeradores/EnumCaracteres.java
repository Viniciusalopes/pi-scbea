/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enumeradores;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author vovostudio
 *
 * FONTE:
 * https://www.guj.com.br/t/enum-obter-o-valor-do-atributo-do-enum-atraves-do-metodos/109311/2
 */
public enum EnumCaracteres {
    Letras("qwertyuiopasdfghjklçzxcvbnmQWERTYUIOPASDFGHJKLÇZXCVBNMàâãáéêíòôõóúÀÂÂÁÉÊÍÒÔÕÓÚ"),
    Numeros("0123456789"),
    Email("@_-." + Numeros.caracteres + Letras.caracteres),
    Telefone("()- " + Numeros.caracteres),
    Editora(Letras.caracteres + Numeros.caracteres + Email.caracteres + "- "),
    EspeciaisLcase("'-=´[~]\\,.;/"),
    EspeciaisLcaseAltGr("¬¹²³£¢¬{[]}\\§/?°®ŧ←↓→øþ´ªæßðđŋħĸł´~º«»©“”µ─·̣̣°°"),
    EspeciaisUcase("\"!@#$%¨&*()_+`{^}|<>:?"),
    EspeciaisUcaseAltGr("¬¡½¾¼⅜¨⅞™±°¿˛/?°®Ŧ¥↑ıØÞ`¯Æ§ÐªŊĦ̛&Ł˝^º˘<>©‘’µ×÷¿"),
    Especiais(EspeciaisLcase.caracteres + EspeciaisLcaseAltGr.caracteres
            + EspeciaisUcase.caracteres + EspeciaisUcaseAltGr.caracteres),
    Todos(Letras.caracteres + Numeros.caracteres + Email.caracteres + Telefone.caracteres + Especiais.caracteres);

    private String caracteres;

    //Guarda as relacoes entre o tipo e o valor de um elemento da enum
    private static Map<String, EnumCaracteres> relations;

    EnumCaracteres(String caracteres) {
        this.caracteres = caracteres;
    }

    public String getCaracteres() {
        return caracteres;
    }

    public static EnumCaracteres getCaracteres(String caracteres) {
        return relations.get(caracteres);
    }

    /**
     * Bloco estatico que popula o hashmap com as relacoes entre tipo e
     * elementos da enum
     */
    static {
        relations = new HashMap<String, EnumCaracteres>();
        for (EnumCaracteres cadeia : values()) {
            relations.put(cadeia.getCaracteres(), cadeia);
        }
    }
}
