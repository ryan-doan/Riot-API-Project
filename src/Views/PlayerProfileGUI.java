package Views;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PlayerProfileGUI {
    Font font;
    Color black, grey;
    JFrame frame;
    JPanel panel;

    //  Basic player info panel

    JPanel infoPanel;
    JLabel profilePic;
    JTextArea nameAndLevel;

    //  Ranked panel

    JPanel rankedPanel;
    JLabel soloQPic = new JLabel();
    JTextArea soloQText;
    JLabel flexQPic = new JLabel();
    JTextArea flexQText;

    //  Match history panel;

    JPanel matchHistory;
    JTextArea kda;
    JTextArea champion;
    JLabel championPic = new JLabel();


    public static void main(String[] args) {
        new PlayerProfileGUI();
    }

    public PlayerProfileGUI() {
        try {
            font = Font.createFont
                    (Font.TRUETYPE_FONT, new File("Christmas.ttf"));
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        black = new Color(59, 11, 59);
        grey = new Color(86, 24, 86);

        frame = new JFrame("Profile");
        panel = new JPanel(null);
        panel.setBackground(black);

        infoPanel = newJPanel(20, 20, 245, 120, grey);
        frame.add(infoPanel);

        profilePic = new JLabel();
        profilePic.setBounds(20, 20, 75, 75);
        infoPanel.add(profilePic);

        nameAndLevel = newJTextArea(105, 20, 200, 100,25f, Color.white, grey, "");
        infoPanel.add(nameAndLevel);

        rankedPanel = newJPanel(20, 150, 245, 150, grey);
        frame.add(rankedPanel);

        soloQPic.setBounds(20, 30, 75, 75);
        rankedPanel.add(soloQPic);

        JTextArea soloQHeader = newJTextArea(20,10,100,20,11f,Color.white, grey,
                "Solo/Duo");
        rankedPanel.add(soloQHeader);

        soloQText = newJTextArea(20,110,100,50,11f, Color.white, grey,"");
        rankedPanel.add(soloQText);

        flexQPic.setBounds(150, 30, 75, 75);
        rankedPanel.add(flexQPic);

        flexQText = newJTextArea(150,110, 100,50,11f, Color.white, grey,"");
        rankedPanel.add(flexQText);

        JTextArea flexQHeader = newJTextArea(150,10,100,20,11f,Color.white, grey,
                "Flex");
        rankedPanel.add(flexQHeader);

        matchHistory = newJPanel(20,320,245, 120, grey);
        frame.add(matchHistory);

        kda = newJTextArea(100, 20,100,12,11f, Color.white, grey, "");
        champion = newJTextArea(20, 20,50,12,11f, Color.white, grey, "");
        championPic = newJLabel(20, 40, 50, 50);
        matchHistory.add(kda);
        matchHistory.add(champion);
        matchHistory.add(championPic);

        frame.setSize(300, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(panel);
        frame.setResizable(false);
        //frame.setVisible(false);
    }

    public JTextArea newJTextArea(int x, int y, int width, int height, float fontSize, Color foreground,
                                  Color background, String text) {
        JTextArea temp = new JTextArea();

        temp.setBounds(x, y, width, height);
        temp.setFont(font.deriveFont(fontSize));
        temp.setForeground(foreground);
        temp.setBackground(background);
        temp.setText(text);
        temp.setEditable(false);

        return temp;
    }

    public JLabel newJLabel(int x, int y, int width, int height) {
        JLabel temp = new JLabel();

        temp.setBounds(x, y, width, height);


        return temp;
    }

    public JPanel newJPanel(int x, int y, int width, int height, Color background) {
        JPanel temp = new JPanel(null);

        temp.setBounds(x, y, width, height);
        temp.setBackground(background);

        return temp;
    }

    public JFrame getFrame() {
        return frame;
    }

    public JPanel getPanel() {
        return panel;
    }

    public JTextArea getNameAndLevel() {
        return nameAndLevel;
    }

    public JLabel getProfilePic() {
        return profilePic;
    }

    public JLabel getSoloQPic() {
        return soloQPic;
    }

    public JTextArea getSoloQText() {
        return soloQText;
    }

    public JLabel getFlexQPic() {
        return flexQPic;
    }

    public JTextArea getFlexQText() {
        return flexQText;
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
