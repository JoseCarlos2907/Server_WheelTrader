package es.iesfernandoaguilar.perezgonzalez.wheeltrader.paypal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

import java.io.IOException;
import java.util.Base64;

public class PayPalClient {
    public static final String CLIENT_ID = "AVpePipYuxBydHxxK9ntOE0gyd2TLgd6Xyf9LRZQmtTj1x4uZ2VJSNwYqxN4I7UKEahmeclqGNLAguCz";
    private static final String CLIENT_SECRET = "EGfjNVs87_zJylpb6yMr2n9_Xe7GzR6ezuxwLSgcCBqoH9BguKTnUq5ICGHPxx00kZQ_Yc6xbAx15FVl";
    public static final String PAYPAL_API_URL = "https://api-m.sandbox.paypal.com";

    public static String obtenerAccessToken() throws IOException {
        try {
            String auth = CLIENT_ID + ":" + CLIENT_SECRET;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());

            HttpResponse<String> response = Unirest.post(PAYPAL_API_URL + "/v1/oauth2/token")
                    .header("Authorization", "Basic " + encodedAuth)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .body("grant_type=client_credentials")
                    .asString();

            if (response.getStatus() == 200) {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode json = mapper.readTree(response.getBody());
                return json.get("access_token").asText();
            } else {
                throw new IOException("Error en autenticación: " + response.getStatusText());
            }
        } catch (UnirestException e) {
            throw new IOException("Error al conectar con PayPal: " + e.getMessage(), e);
        }
    }
}
