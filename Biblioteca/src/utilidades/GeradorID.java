package utilidades;

import controle.ControleArquivoTXT;
import enumeradores.EnumArquivosBd;
import interfaces.IArquivoTXT;
import telas.Vai;
import static utilidades.StringUtil.soTemNumeros;

public class GeradorID {

    private static int id = 0;
    private static int maior = 0;
    private static String caminho = Vai.CONFIGURACAO.getCaminhoBdCliente();
    private static IArquivoTXT controleDados = null;

    public static int getProximoID() throws Exception {
        try {
            for (EnumArquivosBd arquivo : EnumArquivosBd.values()) {                                    // Para cada arqivo de dados
                if (!arquivo.getNomeArquivo().equals(EnumArquivosBd.CONFIGURACAO.getNomeArquivo())) {   // Se não for o arquivo de configuração
                    controleDados = new ControleArquivoTXT(caminho, arquivo.getNomeArquivo());          // Controle de dados
                    for (String linha : controleDados.lerArquivo()) {                                   // Para cada linha do arquivo
                        if (linha.trim().length() > 1 && linha.contains(";")) {                         // Verifica se o tem pelo menos uma coluna
                            String texto = "";
                            if (arquivo.getNomeArquivo().equals(EnumArquivosBd.LOG)) {                  // Verifica se é o arquivo de log
                                texto = linha.split("_")[4].split(";")[0];                              // Armazena o texto com o ID do registro do log
                            } else {
                                texto = linha.split(";")[0];                                            // Armazena o texto com o ID do registro
                            }
                            if (texto.trim().length() > 0) {                                            // Verifica se o texto não está vazio
                                if (soTemNumeros(texto)) {                                              // Se o texto só tem números
                                    maior = Integer.parseInt(texto);                                    // Converte o texto para inteiro
                                    id = (maior > id) ? maior : id;                                     // Maior id encontrado
                                }
                            }
                        }
                    }
                }
            }
            return id + 1;  // Retorna o próximo ID único não utilizado

        } catch (Exception e) {
            throw new Exception("Erro ao gerar o ID!\n" + e.getMessage());
        }
    }
}
