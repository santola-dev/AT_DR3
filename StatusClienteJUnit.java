package org.example;
import java.net.URI;
import java.net.http.*;
import java.io.IOException;

public class StatusClienteJUnit {
        public static void main(String[] args) throws IOException, InterruptedException {
            String url = "http://localhost:7070/tarefas";

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Resposta recebida:");
            System.out.println(response.body());
        }
}
