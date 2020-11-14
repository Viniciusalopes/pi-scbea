package utilidades;

/**
 * Classe para validação do ISBN com 13 dígitos
 *
 * @author vovostudio FONTE: https://www.moreofless.co.uk/validate-isbn-13-java/
 */
public class ValidadorISBN {

    /**
     * Médodo para validação.
     * @param isbn String com o código do ISBN.
     * @return true: ISBN válido; 
     * @return false: ISBN inválido.
     */
    public static boolean validarIsbn13(String isbn) {
        try {
            if (isbn == null) {
                return false;
            }
            //remove os hífens
            isbn = isbn.replaceAll("-", "");

            // verifica se tem 13 dígitos
            if (isbn.length() != 13) {
                return false;
            }

            int tot = 0;
            for (int i = 0; i < 12; i++) {
                int digit = Integer.parseInt(isbn.substring(i, i + 1));
                tot += (i % 2 == 0) ? digit * 1 : digit * 3;
            }

            //checksum must be 0-9. If calculated as 10 then = 0
            int checksum = 10 - (tot % 10);
            if (checksum == 10) {
                checksum = 0;
            }

            return checksum == Integer.parseInt(isbn.substring(12));
        } catch (Exception e) {
            throw e;
        }
    }
}
