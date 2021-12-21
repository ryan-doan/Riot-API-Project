package Views;

import javax.swing.*;
import java.awt.*;

public class MatchSummaryGUI extends GUI {
    JPanel matchHistory;
    JTextArea kda;
    JTextArea champion;
    JLabel championPic = new JLabel();

    public MatchSummaryGUI(JFrame frame) {
        matchHistory = newJPanel(20,320,245, 120, Color.GRAY);
        frame.add(matchHistory);

        kda = newJTextArea(100, 20,100,12,11,Color.white, Color.GRAY, "");
        champion = newJTextArea(20, 20,50,12,11, Color.white, Color.GRAY, "");
        championPic = newJLabel(20, 40, 50, 50);
        matchHistory.add(kda);
        matchHistory.add(champion);
        matchHistory.add(championPic);
    }

    public JTextArea getKda() {
        return kda;
    }

    public JTextArea getChampion() {
        return champion;
    }

    public JLabel getChampionPic() {
        return championPic;
    }

    public JPanel getMatchHistory() {
        return matchHistory;
    }
}
