package abvtools.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Numeros {

    public static double round(double valor, int qtd) {
        if ((Double.isNaN(valor)) || (Double.isInfinite(valor))) {
            valor = 0.0D;
        }

        BigDecimal valorExato = new BigDecimal(String.valueOf(valor)).setScale(qtd, RoundingMode.HALF_UP);
        return valorExato.doubleValue();
    }

}
