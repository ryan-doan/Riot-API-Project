/*
 *  Store a player's stats from a match. Not to be confused with summoner, which stores basic account info
 */
package Data;

import java.io.*;

public class MatchSummary {
    //TODO since we can't access match history service right now, some variable types are speculative and might change
    // later on

    static final int dataOffset = 135;

    //  Basic stats

    String matchID;
    String champion;
    String ban;
    boolean team; // True for red, false for blue.
    boolean win;  //  1 for win, 0 for loss.
    int kills;
    int deaths;
    int assists;
    int gold;
    int cs;

    //  Summoner spell

    String spell1;
    String spell2;

    //  Runes

    public class Runes {
        String primary;
        String keystone;
        String[] runes;
        String secondary;

        public Runes(String primary, String secondary, String keystone, String[] runes) {
            this.primary = primary;
            this.secondary = secondary;
            this.keystone = keystone;
            this.runes = runes;
        }
    }

    Runes runes;

    //  Damage dealt

    //  Damage taken

    //  Items

    int itemslots[] = new int[7];

    //  Ability order

    char[] abilityOrder = new char[18];

    public MatchSummary() { }

    public MatchSummary(MatchHistoryData mhd, Summoner summoner) throws Exception {
        this.matchID = mhd.getMetadata()[1];
        int playerIndex = dataOffset * findPlayer(summoner, mhd.getMetadata());

        boolean team = false;  //  True for red, false for blue.

        if (playerIndex > 5) {
            team = true;
        }

        this.champion = mhd.getParticipants()[6 + playerIndex];
        this.team = team;
        this.win = Boolean.parseBoolean(mhd.getParticipants()[134 + playerIndex]);
        this.kills = Integer.parseInt(mhd.getParticipants()[38 + playerIndex]);
        this.deaths = Integer.parseInt(mhd.getParticipants()[13 + playerIndex]);
        this.assists = Integer.parseInt(mhd.getParticipants()[0 + playerIndex]);
        this.cs =  Integer.parseInt(mhd.getParticipants()[118 + playerIndex]) +
                Integer.parseInt(mhd.getParticipants()[47 + playerIndex]);
        this.gold = Integer.parseInt(mhd.getParticipants()[23 + playerIndex]);
        for (int i = 0; i < 7; i ++) {
            itemslots[i] = Integer.parseInt(mhd.getParticipants()[29 + i + playerIndex]);
        }
        this.runes = null;

        this.saveToCache();
    }

    public MatchSummary(BufferedReader br) throws Exception {
        champion = br.readLine();
        team = Boolean.parseBoolean(br.readLine());
        win = Boolean.parseBoolean(br.readLine());
        kills = Integer.parseInt(br.readLine());
        deaths = Integer.parseInt(br.readLine());
        assists = Integer.parseInt(br.readLine());
        cs = Integer.parseInt(br.readLine());
        gold = Integer.parseInt(br.readLine());
        for (int i = 0; i < 7; i++) {
            itemslots[i] = Integer.parseInt(br.readLine());
        }
        runes = null;
    }

    public int findPlayer(Summoner summoner, String[] metadata) {
        for (int i = 0; i < 10; i++) {
            if (metadata[i + 2].equals(summoner.getPuuid())) {
                return i;
            }
        }

        return 0;
    }  //  find and returns the index of the player to access him/her in the participants array
    //  return 0 if not found (though this probably will never happen)

    public String toString() {
        return String.format("%s, %b, %b, %d, %d, %d, %d, %d", this.champion, this.team, this.win, this.kills,
                this.deaths, this.assists, this.cs, this.gold);
    }

    public void saveToCache() {
        try {
            File file = new File("C:\\Users\\Ryan\\IdeaProjects\\test\\src\\Cache\\"+ this.matchID);
            PrintWriter pw = new PrintWriter(new FileWriter(file));

            pw.println(this.champion);
            pw.println(this.team);
            pw.println(this.win);
            pw.println(this.kills);
            pw.println(this.deaths);
            pw.println(this.assists);
            pw.println(this.cs);
            pw.println(this.gold);

            for (int i = 0; i < 7; i++) {
                pw.println(itemslots[i]);
            }

            pw.flush();

            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getGold() {
        return gold;
    }

    public int getCs() {
        return cs;
    }

    public boolean getWin() {
        return win;
    }

    public String getChampion() {
        return champion;
    }

    public boolean getTeam() {
        return team;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getAssists() {
        return assists;
    }

    public int getItem(int index) {
        return itemslots[index];
    }
}
