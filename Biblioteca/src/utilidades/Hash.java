/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author vovostudio FONTE:
 * https://codare.aurelio.net/2007/02/02/java-gerando-codigos-hash-md5-sha/
 */
public class Hash {

    public static String criptografar(String texto, String algoritmo) throws Exception {
        return stringHexa(gerarHash(texto, algoritmo));
    }

    private static byte[] gerarHash(String frase, String algoritmo) throws Exception {
        try {
            if (algoritmo.trim().length() == 0) {
                throw new Exception("Informe o algoritmo!");
            }
            if (!algoritmo.trim().equals("MD5")
                    && !algoritmo.trim().equals("SHA-1")
                    && !algoritmo.trim().equals("SHA-256")) {
                throw new Exception("Algoritmo inválido!");
            }

            MessageDigest md = MessageDigest.getInstance(algoritmo);
            md.update(frase.getBytes());
            return md.digest();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    private static String stringHexa(byte[] bytes) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            int parteAlta = ((bytes[i] >> 4) & 0xf) << 4;
            int parteBaixa = bytes[i] & 0xf;
            if (parteAlta == 0) {
                s.append('0');
            }
            s.append(Integer.toHexString(parteAlta | parteBaixa));
        }
        return s.toString();
    }
}
