package ecommandtools.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequisicaoWeb {

    public String getStringUrl(String enderecoURL) throws Exception {
        InputStream is = null;

        URL url = new URL(enderecoURL);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setRequestMethod("GET");
        connection.setReadTimeout(15000);
        connection.setConnectTimeout(15000);
        connection.connect();

        int codigoResposta = connection.getResponseCode();

        if (codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST) {
            is = connection.getInputStream();

        } else {
            is = connection.getErrorStream();
        }

        String retorno = converterInputStreamToString(is);

        is.close();

        connection.disconnect();

        return retorno;
    }

    private static String converterInputStreamToString(InputStream is) throws Exception {
        StringBuilder buffer = new StringBuilder();

        String linha;

        try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
            while ((linha = br.readLine()) != null) {
                buffer.append(linha);
            }
        }

        return buffer.toString();
    }

}
