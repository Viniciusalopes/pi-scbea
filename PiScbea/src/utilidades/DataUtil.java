/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author vovostudio
 */
public class DataUtil {

    private static Calendar dataI = Calendar.getInstance();
    private static Calendar dataF = Calendar.getInstance();

    public static int intervaloEmDias(Date dataInicial, Date dataFinal) {
        dataI.setTime(dataInicial);
        dataF.setTime(dataFinal);
        return dataI.get(Calendar.DAY_OF_YEAR) - dataF.get(Calendar.DAY_OF_YEAR);
    }

    public static Date adicionarDias(Date dataReferencia, int dias) {
        dataI.setTime(dataReferencia);
        dataI.add(Calendar.DATE, dias);
        return dataI.getTime();
    }
}
