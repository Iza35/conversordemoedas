import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class ConversaoDeMoedas {

    private static final String API_KEY = "8a6fef8b200c218fcfdda055";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(" **************************************** \n");
        System.out.println(" Seja bem vindo(a) ao Conversor de Moedas! :) \n");
        System.out.println("1) Dólar ===> Peso argentino" );
        System.out.println("2) Peso argentino ===> Dólar" );
        System.out.println("3) Dólar ===> Real brasileiro" );
        System.out.println("4) Real brasileiro ===> Dólar" );
        System.out.println("5) Dólar ===> Peso colombiano" );
        System.out.println("6) Peso colombiano ===> Dólar" );
        System.out.println(" Escolha uma opção válida: ");

        int opcao = scanner.nextInt();
        System.out.println(" Opção escolhida: " + opcao);

        String deMoeda, paraMoeda;
        switch (opcao) {
            case 1:
                deMoeda = "USD";
                paraMoeda = "ARS";
                break;
            case 2:
                deMoeda = "ARS";
                paraMoeda = "USD";
                break;
            case 3:
                deMoeda = "USD";
                paraMoeda = "BRL";
            case 4:
                deMoeda = "BRL";
                paraMoeda = "USD";
                break;
            case 5:
                deMoeda = "USD";
                paraMoeda = "COP";
                break;
            case 6:
                deMoeda = "COP";
                paraMoeda = "USD";
                break;

            default:
                System.out.println(" Opção inválida! ");
                return;
        }

        System.out.println(" **************************************** \n");
        System.out.println(" Digite o valor que deseja converter: \n ");

        double valor = scanner.nextDouble();
        double valorConvertido = converterMoeda(valor, deMoeda, paraMoeda);
        System.out.println(" O valor convertido é de : " + valorConvertido);
    }

    private static double converterMoeda(double valor, String deMoeda, String paraMoeda) {
        try {
            URL url = new URL("https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/" + deMoeda);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream()));
            String linha;
            StringBuilder response = new StringBuilder();
            while ((linha = reader.readLine()) != null) {
                response.append(linha);
            }
            reader.close();

            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            double valorConversaoResponse = jsonResponse.getAsJsonObject("conversion_rates").get(paraMoeda).getAsDouble();
            return valor * valorConversaoResponse;

        } catch (Exception e) {
            System.out.println("Ocorreu algum erro." + e.getMessage());
            return 0;
        }
    }
}
