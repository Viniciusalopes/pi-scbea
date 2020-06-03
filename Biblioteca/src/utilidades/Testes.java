/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import enumeradores.EnumCDD;

/**
 *
 * @author vovostudio
 */
public class Testes {

    public static void main(String[] args) {

        for (EnumCDD cdd : EnumCDD.values()) {
            System.out.println(cdd.toString()
                    + " - " + cdd.getId()
                    + " - " + cdd.getDescr());
        }
    }
}
