package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


// TESTES USANDO GET
public class GetItemPorIdCliente {
    public static void main(String[] args) {
        int itemId = 1; // Altere conforme necessário

        try {
            // Endpoint com path param (ID)
            URL url = new URL("http://localhost:7071/tarefas/" + itemId);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            System.out.println("Código HTTP: " + responseCode);

            // Se encontrou o item (200), imprime o corpo da resposta
            if (responseCode == 200) {
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );
                String line;
                System.out.println("Item retornado:");
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
                reader.close();
            } else {
                System.out.println("Item não encontrado.");
            }

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
