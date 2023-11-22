package currency.converter;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter currency you are converting from (e.g. USD, EUR, JPY, etc.): ");
        String currencyFrom = scanner.nextLine().toUpperCase();
        System.out.println("Enter currency you are converting to: ");
        String currencyTo = scanner.nextLine().toUpperCase();
        System.out.println("Enter the amount");
        double valueStart = scanner.nextDouble();
        double exchangeRate;

        exchangeRate = getExchangeRate(currencyFrom, currencyTo);
        double finalValue = performConversion(exchangeRate,valueStart);
        NumberFormat formatter = new DecimalFormat("#0.00");
        System.out.println(formatter.format(finalValue));
    }
    public static double getExchangeRate(String currencyA, String currencyB) throws IOException, URISyntaxException {
        //INSERT URL FOR CONVERSION HERE!!! (I used exchangerate-api.com)
        String urlRate = "";
        urlRate=urlRate.concat(currencyA);
        URI url = new URI(urlRate);
        HttpURLConnection request = (HttpURLConnection) url.toURL().openConnection();
        request.connect();
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = root.getAsJsonObject();
        var result = jsonobj.get("conversion_rates").getAsJsonObject();
        return result.get(currencyB).getAsDouble();
    }
    public static double performConversion(double rate, double valueA){
        return valueA*rate;
    }
}