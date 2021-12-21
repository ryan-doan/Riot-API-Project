import Views.PlayerProfileGUI;
import Views.StartingGUI;

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

public class Launcher implements ActionListener {
    // Initialize GUIs

    // DONE: Search player, display profile with level, both rank queues and LP
    // TODO: Win/loss
    //  Match history
    //  Champion mastery
    //  Creating tournament (maybe)
    //  Using data to track other stats (warding, cs average, winrate, champion performance...)

    static final int dataOffset = 135;
    final Color victory = Color.cyan;
    final Color defeat = Color.red;

    StartingGUI startingGUI;
    PlayerProfileGUI playerProfileGUI;

    String API_key = "api_key=RGAPI-52cc184b-db40-495b-8c70-2d3d3f731f18";
    Font font;

    public Launcher() {
        init();
    }

    public static void main(String[] args) {
        new Launcher();
    }

    public void init() {
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

    @Override
    public void actionPerformed(ActionEvent e) {
        // StartingPage
        if (e.getSource() == startingGUI.getSearchButton()) {
            System.out.println("StartingGUI/Search summoners");

            // Connects to Rito's API
            try {

                Summoner player = new Summoner(openConnection("https://na1.api.riotgames.com/lol/summoner/v4/" +
                        "summoners/by-name/" + startingGUI.getUsername().getText() + "?"));
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

                    //get match history ids

                    String[] matchIDs = getMatchId(player.getPuuid(), 0, 20);

                    playerProfileGUI.getFrame().setVisible(true);

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

    public Player MatchSummary(String matchId, Summoner summoner) throws Exception {
        MatchHistoryData mhd = matchHistoryData(matchId, summoner);

        int playerIndex = findPlayer(summoner, mhd.getMetadata());

        boolean team = false;  //  True for red, false for blue.

        if (playerIndex > 5) {
            team = true;
        }

        Player player = new Player(mhd.getParticipants()[6 + nthPlayer(playerIndex)],
                team , Boolean.parseBoolean(mhd.getParticipants()[134 + nthPlayer(playerIndex)]),
                Integer.parseInt(mhd.getParticipants()[38 + nthPlayer(playerIndex)]),
                Integer.parseInt(mhd.getParticipants()[13 + nthPlayer(playerIndex)]),
                Integer.parseInt(mhd.getParticipants()[0 + nthPlayer(playerIndex)]),
                Integer.parseInt(mhd.getParticipants()[118 + nthPlayer(playerIndex)]),
                Integer.parseInt(mhd.getParticipants()[23 + nthPlayer(playerIndex)]), null);

        return player;
    }

    public int findPlayer(Summoner summoner, String[] metadata) {
        for (int i = 1; i < 10; i++) {
            if (metadata[i + 2].equals(summoner.getPuuid())) {
                return i;
            }
        }

        return 0;
    }  //  find and returns the index of the player to access him/her in the participants array
       //  return 0 if not found (though this probably will never happen)

    public MatchHistoryData matchHistoryData(String matchId, Summoner summoner) throws Exception {
        URL url = new URL("https://americas.api.riotgames.com/lol/match/v5/matches/"
                + matchId  + "?" + API_key);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String rawData = reader.readLine();
        reader.close();

        //  Split the data into 3 categories (metadata, info, participants), and put them into String arrays.
        //  Refer to the README and the reference file on how to efficiently take data out from the array.

        String metadata = rawData.substring(rawData.indexOf("\"metadata\""), rawData.indexOf("\"]}") + 3);
        metadata = metadata.substring(11).replaceAll("[\\[\\]\"{}]", "");

        String[] metadataArr = metadata.split(",");

        String info = rawData.substring(rawData.indexOf("\"info\":"), nthIndexOf(rawData, ",\"participants",
                2)) + "}";
        info = info.substring(8).replaceAll("[\\[\\]\"{}]", "");

        String[] infoArr = info.split(",");

        String participants = rawData.substring(nthIndexOf(rawData, "\"participants", 2),
                rawData.indexOf(",\"tournamentCode"));
        participants = participants.substring(16).replaceAll("[\\[\\]\"{}]", "");

        String[] participantsArr = participants.split(",");

        for (int i = 0; i < participantsArr.length; i++) {
            participantsArr[i] = participantsArr[i].substring(participantsArr[i].lastIndexOf(":") + 1);
        }

        for (int i = 0; i < infoArr.length; i++) {
            infoArr[i] = infoArr[i].substring(infoArr[i].indexOf(":") + 1);
        }

        for (int i = 0; i < metadataArr.length; i++) {
            metadataArr[i] = metadataArr[i].substring(metadataArr[i].indexOf(":") + 1);
        }

        return new MatchHistoryData(metadataArr, infoArr, participantsArr);
    }

    public static int nthIndexOf(String string, String searchElement, int nth) {
        int result = 0;

        try {
            for (int i = 0; i < nth; i++) {
                result += string.indexOf(searchElement);

                if (i + 1 != nth) {
                    string = string.substring(result + searchElement.length());
                    result += searchElement.length();
                }
            }

            return result;
        } catch (IndexOutOfBoundsException e) {
            return -1;
        }
    }

    public static void skipLine(Scanner scanner, int lines) {

        for (int i = 0; i < lines; i++) {
            scanner.nextLine();
        }

    }  //this is to skip multiple lines

    public static int nthPlayer(int nth) {
        return (nth - 1) * dataOffset;
    }  //used to jump to the nth player in the participants array
}
