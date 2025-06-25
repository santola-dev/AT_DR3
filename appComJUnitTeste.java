package org.example;

import static org.junit.jupiter.api.Assertions.*;
import io.javalin.Javalin;
import org.junit.jupiter.api.*;
import java.net.http.*;
import java.net.URI;
import java.io.IOException;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class appComJUnitTeste { // Testa o endpoint com um simples "Hello Javalin!"

    private Javalin app;
    private final int port = 7070;
    private HttpClient client;

    @BeforeAll
    public void startServer() {
        app = AppComJUnit.HelloApp.createApp();
        app.start(port);
        client = HttpClient.newHttpClient();
    }

    @AfterAll
    public void stopServer() {
        app.stop();
    }

    @Test
    public void testHelloEndpoint() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/tarefas"))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        assertEquals(200, response.statusCode());
        assertEquals("Hello, Javalin!", response.body());
    }

    //------------------------------------------------------------------------------------------------------

    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class ItemAppTest { // Imprime os itens recebidos de um GET do endpoint

        private Javalin app;
        private final int port = 7071;
        private HttpClient client;

        @BeforeAll
        public void startServer() {
            app = AppComJUnit.ItemApp.createApp();
            app.start(port);
            client = HttpClient.newHttpClient();
        }

        @AfterAll
        public void stopServer() {
            app.stop();
        }

        @Test
        public void testCreateItem() throws IOException, InterruptedException {
            String json = """
            {
                "name": "Notebook da Miaoxi",
                "price": 2449.99
            }
            """;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:" + port + "/tarefas"))
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .header("Content-Type", "application/json")
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            assertEquals(201, response.statusCode());
        }
// -----------------------------------------------------------------------------------------------------------------
        @Test
        public void testGetItemById() throws IOException, InterruptedException { // Pega o item pelo ID
            // Criação do item
            String json = """
            {
                "name": "Mouse Gamer",
                "price": 199.99
            }
            """;

            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:" + port + "/tarefas"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
            assertEquals(201, postResponse.statusCode());

            // Extração do ID do JSON retornado (sem usar lib externa)
            String body = postResponse.body();
            int idStart = body.indexOf("\"id\":") + 5;
            int id = Integer.parseInt(body.substring(idStart).split("[,}]")[0].trim());

            // Busca o item recém-criado
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:" + port + "/tarefas" + id))
                    .GET()
                    .build();

            HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, getResponse.statusCode());
            assertTrue(getResponse.body().contains("\"name\":\"Mouse Gamer\""));
            assertTrue(getResponse.body().contains("\"price\":199.99"));
            assertTrue(getResponse.body().contains("\"id\":" + id));
        }
//--------------------------------------------------------------------------------------------------------------------
        @Test
        public void testGetAllItems() throws IOException, InterruptedException { // Lista todos os elementos
            // Garante que pelo menos 1 item exista
            String json = """
    {
        "name": "Cadeira Gamer",
        "price": 999.99
    }
    """;

            HttpRequest postRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:" + port + "/tarefas"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
            assertEquals(201, postResponse.statusCode());

            // Testa o GET /items
            HttpRequest getRequest = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:" + port + "/tarefas"))
                    .GET()
                    .build();

            HttpResponse<String> getResponse = client.send(getRequest, HttpResponse.BodyHandlers.ofString());

            assertEquals(200, getResponse.statusCode());
            assertTrue(getResponse.body().startsWith("["));
            assertTrue(getResponse.body().contains("\"name\":\"Cadeira Gamer\""));
        }

    }
}
