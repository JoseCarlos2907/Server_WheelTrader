package es.iesfernandoaguilar.perezgonzalez.wheeltrader.paypal;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Locale;
import java.util.Map;

public class PayPalService {
    public static boolean realizarPagoACliente(String accessToken, String correoVendedor, double cantidad) throws UnirestException, IOException {
        String jsonPayload = String.format(Locale.US, """
            {
                "sender_batch_header": {
                    "sender_batch_id": "batch_%d",
                    "email_subject": "Has recibido un pago de prueba"
                },
                "items": [{
                    "recipient_type": "EMAIL",
                    "receiver": "%s",
                    "amount": {
                        "value": "%.2f",
                        "currency": "EUR"
                    }
                }]
            }
        """, System.currentTimeMillis(), correoVendedor, cantidad);

        System.out.println(jsonPayload);

        HttpResponse<String> response = Unirest.post(PayPalClient.PAYPAL_API_URL + "/v1/payments/payouts")
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .body(jsonPayload)
                .asString();

        if (response.getStatus() == 201) {
            return true;
        } else {
            return false;
        }
    }

    public static Map<String, Object> realizarPagoABusiness(String accessToken, String correoComprador, double cantidad) throws Exception {
        String orderJson = String.format(Locale.US, """
        {
            "intent": "CAPTURE",
            "payer":{
                "email_address": "%s"
            },
            "purchase_units": [
                {
                    "amount": {
                        "currency_code": "EUR",
                        "value": "%.2f"
                    },
                    "payee": {
                        "email_address": "wheeltrader-app@business.example.com"
                    }
                }
            ]
        }
        """, correoComprador, cantidad);

        HttpResponse<String> response = Unirest.post(PayPalClient.PAYPAL_API_URL + "/v2/checkout/orders")
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .body(orderJson)
                .asString();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(response.getBody());

        // Obtener el order_id
        String orderId = jsonResponse.get("id").asText();

        // Obtener la url de aprobación
        String approveUrl = "";
        for (JsonNode link : jsonResponse.get("links")) {
            if ("approve".equals(link.get("rel").asText())) {
                approveUrl = link.get("href").asText();
                break;
            }
        }

        Map<String, Object> mapa = Map.of(
                "url", approveUrl,
                "response", response,
                "orderId", orderId
        );
        // Enviar el correo al usuario
        return mapa;
    }

    public static boolean isOrderApproved(String orderId, String accessToken) throws Exception {
        String checkUrl = "https://api-m.sandbox.paypal.com/v2/checkout/orders/" + orderId;
        HttpResponse<String> checkResponse = Unirest.get(checkUrl)
                .header("Authorization", "Bearer " + accessToken)
                .header("Content-Type", "application/json")
                .asString();

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonCheckResponse = objectMapper.readTree(checkResponse.getBody());

        String status = jsonCheckResponse.get("status").asText();
        return "APPROVED".equals(status);
    }
}
