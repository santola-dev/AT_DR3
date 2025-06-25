package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


// TESTES USANDO GET
public class StatusCliente {
    public static void main(String[] args) {
        try {
            // Acessa o endpoint /status
            URL url = new URL("http://localhost:7070/tarefas");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            // Lê o JSON retornado
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );
            String line;
            System.out.println("Status do servidor:");
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
