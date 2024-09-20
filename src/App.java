import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {

    public static void main(String[] args) throws Exception {
        // fazer uma conexão HTTP e buscar os Top 250 filmes
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        var client = HttpClient.newHttpClient();
        var request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();
        System.out.println(body);

        // pegar somente os dados que interessam (titulo, poster, classificação)
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados
        for (Map<String, String> filme : listaDeFilmes) {
            System.out.println(filme.get("title"));
            System.out.println(filme.get("image"));

            double rating = Double.parseDouble(filme.get("imDbRating"));
            int estrelas = (int) Math.round(rating / 2); // Converte de 0-10 para 0-5 estrelas
            String estrelasVisual = "\u001b[33m" + "★".repeat(estrelas) + "\u001b[0m" + "☆".repeat(5 - estrelas); // Corrigido

            System.out.println("\u001b[1m\u001b[3m Classificação: \u001b[0m" + "\u001b[1m\u001b[3m" + filme.get("imDbRating") + "\u001b[0m " + estrelasVisual);
        }
    }
}