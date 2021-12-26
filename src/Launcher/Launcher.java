package Launcher;

import Views.*;
import Data.*;
import javax.net.ssl.HttpsURLConnection;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.Arrays;

public class Launcher implements ActionListener, Runnable {
    // Initialize GUIs

    // DONE: Search player, display profile with level, both rank queues and LP
    // TODO: Win/loss
    //  Match history
    //  Champion mastery
    //  Using data to track other stats (warding, cs average, winrate, champion performance...)

    final Color victory = Color.cyan;
    final Color defeat = Color.red;

    StartingGUI startingGUI;
    PlayerProfileGUI playerProfileGUI;

    String API_key = APIKey.getAPIKey();

    //  API key hidden in another class for security reason.
    //  Replace APIKey.getAPIKey() with "api_key=(your api key)" to test the application with your own key.

    Font font;

    public Launcher() {
        try {
            font = Font.createFont
                    (Font.TRUETYPE_FONT, new File("Christmas.ttf"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        startingGUI = new StartingGUI();
        playerProfileGUI = new PlayerProfileGUI();

        // StartingGUI
        startingGUI.getSearchButton().addActionListener(this);
    }

    public static void main(String[] args) {
        new Launcher();
    }

    public void run() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // StartingPage
        if (e.getSource() == startingGUI.getSearchButton()) {
            System.out.println("StartingGUI/Search summoners");

            // Connects to Rito's API
            try {

                Summoner player = new Summoner(openConnection("https://na1.api.riotgames.com/lol/summoner/v4/" +
                        "summoners/by-name/" + startingGUI.getUsername().getText().replaceAll(" ", "") + "?"));
                //Refer to the search method

                if (player != null) {

                    System.out.println("Player's profileIconID is " + player.getProfileIconId());
                    System.out.println("Player's level is " + player.getSummonerLevel());

                    startingGUI.getFrame().setVisible(false);
                    playerProfileGUI.getNameAndLevel().setText(player.getName() + "\n");
                    playerProfileGUI.getNameAndLevel().append("<Level " + player.getSummonerLevel() + ">");


                    ImageIcon imageIcon = new ImageIcon(new URL("http://ddragon.leagueoflegends.com/cdn/" +
                                "11.16.1/img/profileicon/" + player.getProfileIconId() + ".png"));
                    Image image = imageIcon.getImage().getScaledInstance(75, 75,
                                java.awt.Image.SCALE_SMOOTH);

                    imageIcon = new ImageIcon(image);

                    playerProfileGUI.getProfilePic().setIcon(imageIcon);


                    //set player variable's rank data. See rankData method for more information

                    player.setRankedData(openConnection("https://na1.api.riotgames.com/lol/league/v4/entries/" +
                            "by-summoner/" + player.getId() + "?"));

                    System.out.println(player.getFlex().toString());
                    System.out.println(player.getSoloQ().toString());

                    //display player's ranked data

                    Image soloQ = new ImageIcon(String.format("Emblem_%s.png",player.getSoloQ().getTier())).getImage()
                            .getScaledInstance(75, 75, Image.SCALE_SMOOTH);
                    Image flexQ = new ImageIcon(String.format("Emblem_%s.png",player.getFlex().getTier())).getImage()
                            .getScaledInstance(75, 75, Image.SCALE_SMOOTH);

                    playerProfileGUI.getSoloQPic().setIcon(new ImageIcon(soloQ));
                    playerProfileGUI.getSoloQText().setText(String.format("%s %s\n%d LP", player.getSoloQ().getTier(),
                            player.getSoloQ().getRank(), player.getSoloQ().getLeaguePoints()));

                    playerProfileGUI.getFlexQPic().setIcon(new ImageIcon(flexQ));
                    playerProfileGUI.getFlexQText().setText(String.format("%s %s\n%d LP", player.getFlex().getTier(),
                            player.getFlex().getRank(), player.getFlex().getLeaguePoints()));

                    soloQ = null;
                    flexQ = null;

                    playerProfileGUI.getFrame().setVisible(true);

                    //get match history ids

                    String[] matchIDs = getMatchId(player.getPuuid(), 0, 5);

                    for (int i = 0; i < matchIDs.length; i++) {
                        MatchHistoryData mhd = new MatchHistoryData(openConnection("https://americas.api.riotgames.com/lol/match/v5/" +
                                "matches/" + matchIDs[i] + "?"));
                        MatchSummary ms = new MatchSummary(mhd, player);
                        System.out.println(ms.toString());
                        MatchSummaryGUI msg = playerProfileGUI.matchList.get(i);
                        msg.setData(ms);
                    }

                } else {
                    startingGUI.getErrorLabel().setText("An Error Occured.");
                    startingGUI.getErrorLabel().setFont(font.deriveFont(18f));
                }

            } catch (FileNotFoundException exception) {
                startingGUI.getErrorLabel().setText("Username doesn't exists");
                startingGUI.getErrorLabel().setFont(font.deriveFont(18f));
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public HttpsURLConnection openConnection(String urlString) throws Exception {
        URL url = new URL(urlString + API_key);
        return (HttpsURLConnection) url.openConnection();
    }

    public String[] getMatchId(String puuid, int start, int end) throws Exception {
        HttpsURLConnection connection = openConnection(String.format("https://americas.api.riotgames.com/lol/match/" +
                "v5/matches/by-puuid/%s/ids?start=%d&count=%d&", puuid, start, end));
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String rawData = reader.readLine();

        rawData = rawData.substring(1, rawData.length() - 1).replaceAll("\"", "");

        reader.close();

        return rawData.split(",");
    }

    public static void skipLine(Scanner scanner, int lines) {

        for (int i = 0; i < lines; i++) {
            scanner.nextLine();
        }

    }  //this is to skip multiple lines


}
