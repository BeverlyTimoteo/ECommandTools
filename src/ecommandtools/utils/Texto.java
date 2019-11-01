package ecommandtools.utils;

import java.text.Normalizer;

public class Texto {

    public static String removerCaracteresInvalidos(String str) throws Exception {
        String text = Normalizer.normalize(str, Normalizer.Form.NFD);
        return text.replaceAll("[^\\p{ASCII}]", "");
    }

}
