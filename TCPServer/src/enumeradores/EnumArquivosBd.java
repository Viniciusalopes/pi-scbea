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
public enum EnumArquivosBd {
    AREACONHECIMENTO("bibliotecaAreaconhecimento.txt"),
    AUTOR("bibliotecaAutores.txt"),
    COLABORADOR("bibliotecaColaboradores.txt"),
    CONFIGURACAO("bibliotecaConfiguracao.txt"),
    EDITORA("bibliotecaEditoras.txt"),
    EMPRESTIMO("bibliotecaEmprestimos.txt"),
    EXEMPLAR("bibliotecaExemplares.txt"),
    LIVRO("bibliotecaLivros.txt"),
    RESERVA("bibliotecaReservas.txt"),
    LOG("log.txt");
    
    private String nomeArquivo;

    /**
     * Guarda as relacoes entre o tipo e o valor de um elemento da enum
     *
     * FONTE:
     * https://www.guj.com.br/t/enum-obter-o-valor-do-atributo-do-enum-atraves-do-metodos/109311/2
     */
    private static Map<String, EnumArquivosBd> relations;

    /**
     * Bloco estatico que popula o hashmap com as relacoes entre tipo e
     * elementos da enum
     */
    static {
        relations = new HashMap<String, EnumArquivosBd>();
        for (EnumArquivosBd nome : values()) {
            relations.put(nome.getNomeArquivo(), nome);
        }
    }
    
    EnumArquivosBd(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }
    
    public static EnumArquivosBd getNomeArquivo(String nomeArquivo){
        return relations.get(nomeArquivo);
    }
}
