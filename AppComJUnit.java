package org.example;

import io.javalin.Javalin;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class AppComJUnit {

    public class HelloApp { // Testa o endpoint com um simples "Hello Javalin!"
        public static Javalin createApp() {
            Javalin app = Javalin.create();
            app.get("/hello", ctx -> ctx.result("Hello, Javalin!"));
            return app;
        }
    }

    public class ItemApp { // Imprime os itens recebidos de um GET do endpoint

        // Simula um banco de dados na memória
        private static Map<Integer, Item> database = new HashMap<>();
        private static AtomicInteger idCounter = new AtomicInteger(1); // Gerador de IDs

        public static Javalin createApp() {
            Javalin app = Javalin.create();

            // Cria um item a partir de um POST e retorna o JSON com ID
            app.post("/tarefas", ctx -> {
                Item item = ctx.bodyAsClass(Item.class);
                if (item.name == null || item.name.trim().isEmpty()) {
                    ctx.status(400).result("Campo 'name' é obrigatório.");
                    return;
                }
                int id = idCounter.getAndIncrement();
                item.id = id;
                database.put(id, item);
                ctx.status(201).json(item); // Responde com o objeto criado
            });

            // Retorna um item pelo ID, via GET
            app.get("/tarefas/{id}", ctx -> {
                int id = Integer.parseInt(ctx.pathParam("id"));
                Item item = database.get(id);
                if (item != null) {
                    ctx.json(item);
                } else {
                    ctx.status(404).result("Item not found");
                }
            });

            app.get("/tarefas", ctx -> {
                ctx.json(database.values());
            });

            return app;
        }
    }

    class Item { // Criador de itens
        public int id;
        public String name;
        public double price;
    }
}
