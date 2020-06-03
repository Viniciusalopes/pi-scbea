/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enumeradores;

import java.util.HashMap;
import java.util.Map;
import static javax.management.Query.gt;
import static javax.management.Query.lt;

/**
 *
 * @author vovostudio
 */
public enum EnumCDD {

    n504501(504501, "Civel"),
    n504602(504602, "Criminal"),
    n504703(504703, "Familia"),
    n504804(504804, "Tributária"),
    n504905(504905, "Todos");

    private int id;
    private String descr;

    /**
     * Guarda as relacoes entre a descricao e o valor de um elemento da enum
     */
    private static Map<String, EnumCDD> relations;

    private EnumCDD() {
    }

    private EnumCDD(int id, String descr) {
        this.id = id;
        this.descr = descr;
    }

    public int getId() {
        return id;
    }

    public String getDescr() {
        return descr;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    /**
     * Recupera um elemento da enum a partir de uma string
     *
     * @param descr A string de descricao do elemento
     * @return Um elemento da enum ou null caso nenhum elemento seja encontrado
     * para a descricao pesquisada.
     */
    public static EnumCDD getCDDPorDescr(String descr) {
        return relations.get(descr);
    }

    /**
     * Bloco estatico que popula o hashmap com as relacoes entre descricao e
     * elementos da enum
     */
    static {
        relations = new HashMap<String, EnumCDD>();
        for (EnumCDD s : values()) {
            relations.put(s.getDescr(), s);
        }
    }

}
