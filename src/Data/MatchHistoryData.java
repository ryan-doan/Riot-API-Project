package Data;
import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MatchHistoryData {
    String[] metadata;
    String[] info;
    String[] participants;

    public MatchHistoryData(HttpsURLConnection connection) throws Exception {
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

        this.metadata = metadataArr;
        this.info = infoArr;
        this.participants = participantsArr;
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
