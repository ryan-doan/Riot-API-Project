package Views;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import Data.MatchHistoryData;
import Data.MatchSummary;

public class MatchSummaryGUI extends GUI implements Runnable {
    JPanel matchHistory;
    JTextArea kda;
    JTextArea champion;
    JLabel championPic;
    JPanel items;
    JLabel[] itemSlots = new JLabel[7];

    public void run() {}

    public MatchSummaryGUI(int x, int y, int w, int h, Color color) {
        matchHistory = newJPanel(x, y, w, h, color);

        kda = newJTextArea(100, 20,100,20,12, Color.black, color, "");
        champion = newJTextArea(20, 20,50,20,12, Color.black, color, "");
        championPic = newJLabel(20, 45, 50, 50);
        items = new JPanel();
        items.setLayout(new BoxLayout(items, BoxLayout.LINE_AXIS));
        items.setBounds(100, 55, 140, 20);
        matchHistory.add(items);
        matchHistory.add(kda);
        matchHistory.add(champion);
        matchHistory.add(championPic);
    }

    public void setData(MatchSummary ms) throws Exception {
        if (ms.getWin()) {
            matchHistory.setBackground(Color.CYAN);
            kda.setBackground(Color.CYAN);
            champion.setBackground(Color.CYAN);
        } else {
            matchHistory.setBackground(Color.red);
            kda.setBackground(Color.red);
            champion.setBackground(Color.red);
        }
        setChampion(ms.getChampion());
        setKda(ms.getKills(), ms.getDeaths(), ms.getAssists(), ms.getCs());
        for (int i = 0; i < 7; i++) {
            setItemSlots(i,setItemImage(ms.getItem(i)));
        }
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

    public void setKda(int k, int d, int a, int cs) {
        kda.setText(String.format("%d / %d / %d   %d cs", k, d, a, cs));
    }

    public void setChampion(String champion) throws Exception {
        this.champion.setText(champion);
        ImageIcon imageIcon = new ImageIcon(new URL("http://ddragon.leagueoflegends.com/cdn/11.24.1/img/champion/"
                + champion + ".png"));
        Image image = imageIcon.getImage().getScaledInstance(50, 50,
                java.awt.Image.SCALE_SMOOTH);

        imageIcon = new ImageIcon(image);

        championPic.setIcon(imageIcon);
    }

    public void setItemSlots(int index, ImageIcon image) {
        itemSlots[index] = new JLabel();
        itemSlots[index].setIcon(image);
        items.add(itemSlots[index]);
    }

    public ImageIcon setItemImage(int item) throws Exception {
        ImageIcon imageIcon = new ImageIcon(new URL("http://ddragon.leagueoflegends.com/cdn/11.24.1/img/item/"
                + item + ".png"));
        Image image = imageIcon.getImage().getScaledInstance(20, 20,
                java.awt.Image.SCALE_SMOOTH);

        return new ImageIcon(image);
    }
}
