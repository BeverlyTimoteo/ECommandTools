package ecommandtools.utils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Formatacao {

    public static String getFormatDecimal2(double valor) {
        return new DecimalFormat("#,##0.00").format(valor);
    }

    public static String numberLeft(String valor, int quantidadeColunas) {
        String retorno = "";

        for (int i = 0; i < quantidadeColunas - valor.length(); i++) {
            retorno += "0";
        }

        return retorno + valor;
    }

    public static String numberLeft(long valor, int i_tamanho) {
        return numberLeft(String.valueOf(valor), i_tamanho);
    }

    public static String numberLeft(Object valor, int i_tamanho) {
        return numberLeft(String.valueOf(valor), i_tamanho);
    }

    public static String getDataDB(Date data) {
        return new SimpleDateFormat("yyyy-MM-dd").format(data);
    }

    public static String getDataDB(String data) {
        return new SimpleDateFormat("yyyy-MM-dd").format(data);
    }

    public static Date getDate(String data) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(data);
    }

    public static String getDateView(String data) throws ParseException {
        LocalDate ld = LocalDate.parse(data);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/uuuu");
        return dtf.format(ld);
    }

    public static String getDataDBTimeStamp(String data) {
        return data + " " + "00:00:00";
    }

    public static String cpf(Object value) {
        long cpf = (Long) value;

        return cpf(cpf);
    }

    public static String cpf(long value) {
        String cpf = new DecimalFormat("00000000000").format(value);
        return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
    }
    
     public static String cnpj(long value) {
        String cpf = new DecimalFormat("00000000000000").format(value);
        return cpf.substring(0, 2) + "." + cpf.substring(2, 5) + "." + cpf.substring(5, 8) + "/" + cpf.substring(8, 12) + "-" + cpf.substring(12, 14);
    }

}
