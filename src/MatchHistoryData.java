public class MatchHistoryData {
    String[] metadata;
    String[] info;
    String[] participants;

    public MatchHistoryData(String[] metadata, String[] info, String[] participants) {
        this.metadata = metadata;
        this.info = info;
        this.participants = participants;
    }

    public String[] getMetadata() {
        return metadata;
    }

    public String[] getInfo() {
        return info;
    }

    public String[] getParticipants() {
        return participants;
    }
}
