/*
 * Summoner
 *
 * For storing data while app is open, replacing txt files in the future
 */
package Data;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Summoner {
    String id;
    String accountId;
    String puuid;
    String name;
    String revisionDate;
    int profileIconId;
    int summonerLevel;
    RankQueue soloQ;
    RankQueue flex;

    public Summoner(HttpsURLConnection connection) throws Exception {
        //Connects to Rito's API

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        if (connection.getResponseCode() == 200) {  //Making sure the player exists before we do anything else
            //Get player data

            String rawData = reader.readLine();
            reader.close();

            // Formats data

            rawData = rawData.substring(1, rawData.length() - 1).replaceAll("\"",
                    "").replaceAll("[a-zA-Z]*:", "");

            System.out.println(rawData);

            String[] summonerData = rawData.split(",");

            this.id = summonerData[0];
            this.accountId = summonerData[1];
            this.puuid = summonerData[2];
            this.name = summonerData[3];
            this.profileIconId = Integer.parseInt(summonerData[4]);
            this.revisionDate = summonerData[5];
            this.summonerLevel = Integer.parseInt(summonerData[6]);
        }

        reader.close();

    }

    public void setRankedData(HttpsURLConnection connection) throws Exception {
        //Connects to Rito's API
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String rawData = reader.readLine();

        reader.close();

        if (rawData.length() < 5) {  //player doesn't play ranked

            this.setSoloQ(new RankQueue("RANKED_SOLO_5x5"));
            this.setFlex(new RankQueue("RANKED_FLEX_SR"));

        } else {

            rawData = rawData.substring(1, rawData.length() - 1).replaceAll("[\"{}]",
                    "").replaceAll("[a-zA-Z]*:", "");

            String[] rankedData = rawData.split(",");

            if (rankedData.length < 14) {  //only 1 rank queue

                if (rankedData[1].equals("RANKED_SOLO_5x5")) {
                    //  see rankQueue method in this class

                    this.setSoloQ(new RankQueue(rankedData, 1));
                    this.setFlex(new RankQueue("RANKED_FLEX_SR"));
                } else {
                    this.setSoloQ(new RankQueue("RANKED_SOLO_5x5"));
                    this.setFlex(new RankQueue(rankedData, 1));
                }

            } else {  //both rank queue

                if (rankedData[1].equals("RANKED_SOLO_5x5")) {
                    this.setSoloQ(new RankQueue(rankedData, 1));
                    this.setFlex(new RankQueue(rankedData, 14));
                } else {
                    this.setFlex(new RankQueue(rankedData, 1));
                    this.setSoloQ(new RankQueue(rankedData, 14));
                }  //  Sometimes Riot's API just decide to swap Flex and SoloQ around, requiring this check to make
                //  sure we don't swap the queues with each other.

            }

        }

    }

    public String getPuuid() {
        return puuid;
    }

    public String getName() {
        return name;
    }

    public int getProfileIconId() {
        return profileIconId;
    }

    public int getSummonerLevel() {
        return summonerLevel;
    }

    public String getId() {
        return id;
    }

    public void setSoloQ(RankQueue soloQ) {
        this.soloQ = soloQ;
    }

    public void setFlex(RankQueue flex) {
        this.flex = flex;
    }

    public RankQueue getSoloQ() {
        return this.soloQ;
    }

    public RankQueue getFlex() {
        return this.flex;
    }
}
