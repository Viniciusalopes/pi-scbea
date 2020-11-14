/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import classes.AreaConhecimento;
import controle.ControleAreaConhecimento;
import controle.ControleLog;
import enumeradores.EnumAcao;
import enumeradores.EnumCadastro;
import interfaces.ICRUDAreaConhecimento;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author vovostudio
 */
public class Menutencao {

    private SimpleDateFormat formatoData = null;
    private String dataHora = "";

    public void importarCDD() throws Exception {
        formatoData = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        dataHora = formatoData.format(new Date()).toString();
        System.out.println("MANUTENÇẪO DO SISTEMA: Importação da tabela de Áreas de Conhecimento");
        System.out.println("[" + dataHora + "] Iniciando.");

        ICRUDAreaConhecimento controleAreaConhecimento = null;
        AreaConhecimento area = null;

        String[][] tabelaInicial = tabelaCdd.tabelaInicial;

        for (int i = 0; i < tabelaInicial.length; i++) {
            area = new AreaConhecimento(
                    GeradorID.getProximoID(),
                    Integer.parseInt(tabelaInicial[i][0]),
                    tabelaInicial[i][1]
            );
            try {
                new ControleAreaConhecimento().incluir(area);
                System.out.println("Importando CDD " + area.getCdd() + "...");

            } catch (Exception e) {
                new ControleLog().incluir(EnumAcao.ImportarCDD, EnumCadastro.AREACONHECIMENTO, area.toString(), e.getMessage());
                System.out.println("CDD " + area.getCdd() + " não foi importada!\nMotivo: " + e.getMessage());
            }
        }
        dataHora = formatoData.format(new Date()).toString();
        System.out.println("[" + dataHora + "] Manutenção concluída.");
    }
}
