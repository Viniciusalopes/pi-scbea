/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persistencia;

import classes.ArquivoTXT;
import classes.Colaborador;
import controle.ControleArquivoTXT;
import enumeradores.EnumArquivosBd;
import enumeradores.EnumCargo;
import enumeradores.EnumPerfil;
import enumeradores.EnumTipoStatus;
import interfaces.IArquivoTXT;
import interfaces.ICRUDColaborador;
import java.util.ArrayList;
import static telas.Vai.CONFIGURACAO;

/**
 *
 * @author vovostudio
 */
public class PersistenciaColaborador implements ICRUDColaborador {

    private ArquivoTXT arquivoTXT = null;
    private IArquivoTXT controleArquivoTXT = null;

    public PersistenciaColaborador() throws Exception {
        arquivoTXT = new ArquivoTXT(CONFIGURACAO.getCaminhoBdCliente(), EnumArquivosBd.COLABORADOR.getNomeArquivo());
        controleArquivoTXT = new ControleArquivoTXT(arquivoTXT);
    }

    @Override
    public ArrayList<Colaborador> listar() throws Exception {
        ArrayList<Colaborador> colaboradores = new ArrayList<>();
        ArrayList<String> linhas = controleArquivoTXT.lerArquivo(arquivoTXT);

        for (String linha : linhas) {
            String[] dados = linha.split(";");

            Colaborador colaborador = new Colaborador(
                    Integer.parseInt(dados[0]),
                    dados[1],
                    EnumPerfil.values()[Integer.parseInt(dados[2])],
                    Integer.parseInt(dados[3]),
                    EnumCargo.values()[Integer.parseInt(dados[4])],
                    dados[5],
                    dados[6],
                    dados[7],
                    dados[8],
                    EnumTipoStatus.values()[Integer.parseInt(dados[9])]
            );
            colaboradores.add(colaborador);
        }
        return colaboradores;
    }

    @Override
    public void incluir(Colaborador objColaborador) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void atualizar(Colaborador objColaborador) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void excluir(int idColaborador) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
