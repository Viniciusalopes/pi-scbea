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
 */
public enum EnumUF {
    AC("Acre"),
    AL("Alagoas"),
    AP("Amapá"),
    AM("Amazonas"),
    BA("Bahia"),
    CE("Ceará"),
    DF("Distrito Federal"),
    ES("Espírito Santo"),
    GO("Goiás"),
    MA("Maranhão"),
    MT("Mato Grosso"),
    MS("Mato Grosso do Sul"),
    MG("Minas Gerais"),
    PA("Pará"),
    PB("Paraíba"),
    PR("Paraná"),
    PE("Pernanmbuco"),
    PI("Piauí"),
    RJ("Rio de Janeiro"),
    RN("Rio grande do norte"),
    RS("Rio Grande do Sul"),
    RO("Rondônia"),
    RR("Roraima"),
    SC("Santa Catarina"),
    SP("São Paulo"),
    SE("Sergipe"),
    TO("Tocantins");

    private String nomeUF;

    EnumUF(String nomeUF) {
        this.nomeUF = nomeUF;
    }

    private static Map<String, EnumUF> relations;

    public String getNomeUF() {
        return nomeUF;
    }

    public static EnumUF getNomeUF(String nomeUF) {
        return relations.get(nomeUF);
    }

    static {
        relations = new HashMap<String, EnumUF>();
        for (EnumUF nome : values()) {
            relations.put(nome.getNomeUF(), nome);
        }
    }
}
