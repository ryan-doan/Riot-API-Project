import java.io.*;
import java.net.*;
import java.util.Locale;
import java.util.Scanner;

/*
 * Test
 *
 * For testing random stuff
 */

public class test {
    public static void main(String[] args) {
        final String API_key = "?api_key=RGAPI-61e24667-0dba-41a6-a866-532ad8226a7f";
        String id = "";

        String summonerName = "I Laplace I";
        String summonerData = "";

        try {
            URL url = new URL ("https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" +
                    summonerName.replaceAll(" ", "") + API_key);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            int responseCode = connection.getResponseCode();
            summonerData = reader.readLine();

            System.out.println(responseCode);
            System.out.println(summonerData);

            summonerData = summonerData.substring(1, summonerData.length() - 1).replaceAll("\"", "")
                    .replaceAll(",", "\n");

            PrintWriter writer = new PrintWriter(new FileWriter(new File(summonerName + ".txt")));

            writer.println(summonerData);
            writer.flush();
            writer.close();
            reader.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
