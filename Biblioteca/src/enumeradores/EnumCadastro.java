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
public enum EnumCadastro {
    AREACONHECIMENTO("Área de Conhecimento"),
    AUTOR("Autor"),
    COLABORADOR("Colaborador"),
    CONFIGURACAO("Configuração"),
    EDITORA("Editora"),
    EMPRESTIMO("Empréstimo"),
    EXEMPLAR("Exemplar"),
    LIVRO("Livro"),
    RESERVA("Reserva");

    private String nomeCadastro;

    /**
     * Guarda as relacoes entre o tipo e o valor de um elemento da enum
     *
     * FONTE:
     * https://www.guj.com.br/t/enum-obter-o-valor-do-atributo-do-enum-atraves-do-metodos/109311/2
     */
    private static Map<String, EnumCadastro> relations;

    /**
     * Bloco estatico que popula o hashmap com as relacoes entre tipo e
     * elementos da enum
     */
    static {
        relations = new HashMap<String, EnumCadastro>();
        for (EnumCadastro nome : values()) {
            relations.put(nome.getNomeCadastro(), nome);
        }
    }

    EnumCadastro(String nomeCadastro) {
        this.nomeCadastro = nomeCadastro;
    }

    public String getNomeCadastro() {
        return nomeCadastro;
    }

    public static EnumCadastro getNomeCadastro(String nomeCadastro) {
        return relations.get(nomeCadastro);
    }
}
