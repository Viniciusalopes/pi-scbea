package utilidades;

import classes.ArquivoTXT;
import controle.ControleArquivoTXT;
import enumeradores.EnumArquivosBd;
import interfaces.IArquivoTXT;
import java.io.File;
import java.util.ArrayList;
import telas.Vai;
import static utilidades.StringUtil.soTemNumeros;

public class GeradorID {

    private static int id = 0;
    private static ArquivoTXT arquivoID = null;
    private static ArquivoTXT arquivoDados = null;
    private static IArquivoTXT controleID = null;
    private static IArquivoTXT controleDados = null;
    private static ArrayList<String> linhasID = null;
    private static ArrayList<String> linhasDados = null;
    private static File file = null;

    public static int getProximoID() throws Exception {
        try {
            arquivoID = new ArquivoTXT(Vai.CONFIGURACAO.getCaminhoBdCliente(), "id");
            file = new File(arquivoID.getCaminho() + Vai.BARRA + arquivoID.getArquivo());

            if (!file.exists()) {                                           // Se não existe o arquivo 'id'
                file.createNewFile();                                       //    Cria o arquivo
                reindexarID();                                              //    Reindexa o ID
            } else {
                if (Vai.CONFIGURACAO.getLerId()) {                          // Sistema configurado para ler o arquivo com ID
                    String primeiraLinha = "";
                    int idNoArquivo = 0;

                    controleID = new ControleArquivoTXT(arquivoID);         //  Instancia o controle
                    linhasID = controleID.lerArquivo(arquivoID);            //  Lê o arquivo
                    primeiraLinha = linhasID.get(0).trim();                 //  Armazena a primeira linha

                    if (primeiraLinha.length() == 0) {                      // Se a linha está em branco
                        reindexarID();                                      //    Reindexa o ID
                    } else {

                        if (soTemNumeros(primeiraLinha)) {                  // Se a linha só tem números
                            idNoArquivo = Integer.parseInt(primeiraLinha);  //    Converte a linha para inteiro
                            if (idNoArquivo == 0) {                         //    Se o idNoArquivo é 0 (zero)    
                                reindexarID();                              //    Reindexa o ID
                            } else {
                                id = idNoArquivo;                           //    Recupera o 'id' do arquivo
                            }
                        } else {                                            // Se a linha não tem só números
                            reindexarID();                                  //    Reindexa o ID
                        }
                    }
                } else {                                                    // Sistema configurado para reindexar ID
                    reindexarID();                                          //  Reindexa o ID
                }
            }
        } catch (Exception e) {                                             // Erro de leitura do arquivo ou contrução de classes
            reindexarID();
        }
        return id + 1;                                                      // Retorna o próximo id válido
    }

    public static void confirmaID() throws Exception {
        salvaNoArquivo(getProximoID());
    }

    private static void salvaNoArquivo(int proximoId) throws Exception {
        try {
            arquivoID = new ArquivoTXT(Vai.CONFIGURACAO.getCaminhoBdCliente(), "id");
            controleID = new ControleArquivoTXT(arquivoID);
            linhasID = new ArrayList<>();
            linhasID.add("" + proximoId);
            arquivoID.setLinhas(linhasID);
            controleID.escreverArquivo(arquivoID);
        } catch (Exception e) {
            throw new Exception("Erro ao gravar o próximo ID!\n" + e);
        }
    }

    public static void reindexarID() throws Exception {
        try {
            id = 0;
            for (EnumArquivosBd arquivo : EnumArquivosBd.values()) {                                                // Para cada arqivo de dados
                arquivoDados = new ArquivoTXT(Vai.CONFIGURACAO.getCaminhoBdCliente(), arquivo.getNomeArquivo());    // Cria uma instâcia do arquivo
                if (!arquivo.getNomeArquivo().equals(EnumArquivosBd.CONFIGURACAO.getNomeArquivo())) {               // Se não for o arquivo de configuração
                    controleDados = new ControleArquivoTXT(arquivoDados);                                           // Controle de dados
                    linhasDados = controleDados.lerArquivo(arquivoDados);                                           // Linhas do arquivo de dados
                    for (String linha : linhasDados) {                                                              // Para cada linha do arquivo
                        if (linha.trim().length() > 1 && linha.contains(";")) {                                     // Verifica se o tem pelo menos uma coluna
                            String texto = linha.split(";")[0];                                                     // Armazena o texto da posição [0]
                            if (soTemNumeros(texto)) {                                                              // Se o texto só tem números
                                int maior = Integer.parseInt(texto);                                                // Converte para inteiro
                                id = (maior > id) ? maior : id;                                                     // Maior id encontrado
                            }
                        }
                    }
                }
            }
            salvaNoArquivo(id);     // Grava o próximo ID no arquivo
        } catch (Exception e) {
            throw new Exception("Erro ao reindexar o ID!\n" + e);
        }
    }
}
