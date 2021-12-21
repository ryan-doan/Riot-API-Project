/*
 * Stores data from a match
 */

public class Match {
    //TODO since we can't access match history service right now, some variable types are speculative and might change
    // later on

    //  Constants
    final boolean redTeam = true;
    final boolean blueTeam = false;

    //  Match stats

    boolean winner;  //  True for red, false for blue. For sake of clarity, use defined constants above
    String gameID;
    String queueType;
    Player[] players;
    long matchTime;  //  In milliseconds
    long matchDate;

    //  Team stats
    String[] redBans = new String[5];
    String[] blueBans = new String[5];

    int redGold;
    int blueGold;

    int redKills;
    int blueKills;

    int redDeaths;
    int blueDeaths;

    int redAssists;
    int blueAssists;

    int redTowers;
    int blueTowers;

    int redDragons;
    int blueDragons;

    int redBarons;
    int blueBarons;

    int redHeralds;
    int blueHeralds;

    int redInhibs;
    int blueInhibs;

    public Match() {

    }

    public long getMatchTime() {
        return matchTime;
    }

    public long getMatchDate() {
        return matchDate;
    }

    public void setMatchTime(long value) {
        matchTime = value;
    }
}
