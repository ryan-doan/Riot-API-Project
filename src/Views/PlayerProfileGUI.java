package Views;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class PlayerProfileGUI extends GUI implements Runnable {
    Font font;
    Color black, grey;
    static JFrame frame;
    static JPanel panel;

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

    public ArrayList<MatchSummaryGUI> matchList = new ArrayList<MatchSummaryGUI>();

    //  Scroll pane;

    static JScrollPane scroll = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

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
        panel.setVisible(true);

        infoPanel = newJPanel(20, 20, 245, 120, grey);
        panel.add(infoPanel);

        profilePic = new JLabel();
        profilePic.setBounds(20, 20, 75, 75);
        infoPanel.add(profilePic);

        nameAndLevel = newJTextArea(105, 20, 200, 100,25, Color.white, grey, "");
        infoPanel.add(nameAndLevel);

        rankedPanel = newJPanel(20, 150, 245, 150, grey);
        panel.add(rankedPanel);

        soloQPic.setBounds(20, 30, 75, 75);
        rankedPanel.add(soloQPic);

        JTextArea soloQHeader = newJTextArea(20,10,100,20,11,Color.white, grey,
                "Solo/Duo");
        rankedPanel.add(soloQHeader);

        soloQText = newJTextArea(20,110,100,50,11, Color.white, grey,"");
        rankedPanel.add(soloQText);

        flexQPic.setBounds(150, 30, 75, 75);
        rankedPanel.add(flexQPic);

        flexQText = newJTextArea(150,110, 100,50,11, Color.white, grey,"");
        rankedPanel.add(flexQText);

        JTextArea flexQHeader = newJTextArea(150,10,100,20,11, Color.white, grey,
                "Flex");
        rankedPanel.add(flexQHeader);

        int offset = 320;

        for (int i = 0; i < 5; i++) {
            MatchSummaryGUI msg = new MatchSummaryGUI(20,offset,245, 100, grey);
            matchList.add(msg);
            offset += 110;
            panel.add(msg.matchHistory);
        }

        panel.setBounds(0,0,300,1000);
        scroll.setViewportView(panel);
        frame.add(scroll);
        frame.setSize(320, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.setVisible(false);
    }

    public void run() {

    }

    public String getDim() {
        return panel.getBounds().getSize().toString();
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
}
