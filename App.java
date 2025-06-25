package org.example;

import io.javalin.Javalin;
import io.javalin.http.Context;

import java.time.Instant;
import java.util.*;

public class App {

    ///  Exemplos de uso de Desenvolvimento REST com Javalin

    private static final List<Produto> produtos = new ArrayList<>(); // Lista para armazenar os produtos

    public static void main(String[] args) {

        Javalin app = Javalin.create();

        app.get("/hello", ctx -> ctx.result("Hello, Javalin!")); // Retorna o "Hello Javalin" na porta 7000
        app.get("/status", App::statusHandler); // Usa o método statusHandler e posta  o status + hora
        app.post("/echo", App::echoHandler); // Usa o método echoHandler e retorna o JSON que lhe for dado
        app.get("/saudacao/{nome}", App::saudacaoHandler); // Usa o método saudacaoHandler para retornar um "Olá, <nome>!"

        app.post("/tarefas", App::criarProdutoHandler); // Cria o endpoint
        app.get("/tarefas", App::listarProdutosHandler); // Lista os produtos existentes de uma lista
        app.get("/tarefas/{nome}", App::buscarProdutoPorNomeHandler); // Busca entre os produtos de dita lista
    }


    private static void statusHandler(Context ctx) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "ok");
        response.put("timestamp", Instant.now().toString());

        ctx.json(response);
    }

    private static void echoHandler(Context ctx) {
        Map<String, String> body = ctx.bodyAsClass(Map.class);
        ctx.json(body);
    }

    private static void saudacaoHandler(Context ctx) {
        String nome = ctx.pathParam("nome");
        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Olá, " + nome + "!");
        ctx.json(response);
    }

    private static void criarProdutoHandler(Context ctx) {
        Produto novoProduto = ctx.bodyAsClass(Produto.class);
        produtos.add(novoProduto);
        ctx.status(201).json(novoProduto);
    }

    private static void listarProdutosHandler(Context ctx) {
        ctx.json(produtos);
    }

    private static void buscarProdutoPorNomeHandler(Context ctx) {
        String nome = ctx.pathParam("nome");

        Optional<Produto> produto = produtos.stream()
                .filter(p -> p.nome.equalsIgnoreCase(nome))
                .findFirst();

        if (produto.isPresent()) {
            ctx.json(produto.get());
        } else {
            ctx.status(404).json(Map.of("erro", "Produto não encontrado")); // <-- Em caso de erro
        }
    }
}
