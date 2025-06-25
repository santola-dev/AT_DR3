package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// TESTES USANDO GET
public class GetTodosItensCliente {
    public static void main(String[] args) {
        try {
            // Endpoint que retorna todos os itens
            URL url = new URL("http://localhost:7071/tarefas");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            // Lê a resposta
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );
            String line;
            System.out.println("Itens cadastrados:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
