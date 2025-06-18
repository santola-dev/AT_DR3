package org.example;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;

public class StatusClienteJUnit {
        public static void main(String[] args) throws IOException, InterruptedException {
            String url = "http://localhost:7070/status";

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
