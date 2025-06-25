package org.example;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostItemCliente {
    public static void main(String[] args) {
        try {
            // URL do endpoint POST para criar item
            URL url = new URL("http://localhost:7071/tarefas");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            // Configura método POST
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // Corpo da requisição em JSON
            String jsonInput = """
                {
                    "name": "Teclado Mecânico",
                    "price": 349.90
                }
            """;

            // Envia o corpo
            try (OutputStream os = conn.getOutputStream()) {
                os.write(jsonInput.getBytes());
            }

            // Exibe o status HTTP da resposta
            System.out.println("POST status: " + conn.getResponseCode());

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
