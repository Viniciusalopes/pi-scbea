 // fonte: https://www.moreofless.co.uk/validate-isbn-13-java/
 
 
 
 public boolean validateIsbn13( String isbn )
    {
        if ( isbn == null )
        {
            return false;
        }

        //remove any hyphens
        isbn = isbn.replaceAll( "-", "" );

        //must be a 13 digit ISBN
        if ( isbn.length() != 13 )
        {
            return false;
        }

        try
        {
            int tot = 0;
            for ( int i = 0; i < 12; i++ )
            {
                int digit = Integer.parseInt( isbn.substring( i, i + 1 ) );
                tot += (i % 2 == 0) ? digit * 1 : digit * 3;
            }

            //checksum must be 0-9. If calculated as 10 then = 0
            int checksum = 10 - (tot % 10);
            if ( checksum == 10 )
            {
                checksum = 0;
            }

            return checksum == Integer.parseInt( isbn.substring( 12 ) );
        }
        catch ( NumberFormatException nfe )
        {
            //to catch invalid ISBNs that have non-numeric characters in them
            return false;
        }
    }
