/*
 * RankQueue
 *
 * Same as Summoner but for different queues, TFT not yet included
 */

public class RankQueue {
    String queueType;
    String tier;
    String rank;
    int leaguePoints;
    int wins;
    int losses;
    boolean veteran;
    boolean inactive;
    boolean freshBlood;
    boolean hotStreak;

    public RankQueue (String[] rankedData, int index) {
        this.queueType = rankedData[index];
        this.tier = rankedData[index + 1];
        this.rank = rankedData[index + 2];
        this.leaguePoints = Integer.parseInt(rankedData[index + 5]);
        this.wins = Integer.parseInt(rankedData[index + 6]);
        this.losses = Integer.parseInt(rankedData[index + 7]);
        this.veteran = Boolean.parseBoolean(rankedData[index + 8]);
        this.inactive = Boolean.parseBoolean(rankedData[index + 9]);
        this.freshBlood = Boolean.parseBoolean(rankedData[index + 10]);
        this.hotStreak = Boolean.parseBoolean(rankedData[index + 11]);
    }  //  create a rankQueue object from the data in the String array

    public RankQueue(String queueType) {
        this.queueType = queueType;
        this.tier = "Unranked";
        this.rank = "";
        this.leaguePoints = 0;
        this.wins = 0;
        this.losses = 0;
    }

    public String getTier() {
        return tier;
    }

    public String getRank() {
        return rank;
    }

    public int getLeaguePoints() {
        return leaguePoints;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    public String toString() {
        return String.format("%s %s %s", queueType, tier, rank);
    }
}
