package Views;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import Data.MatchHistoryData;

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

    static JScrollPane scroll = new JScrollPane(panel);

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
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.setVisible(true);

        infoPanel = newJPanel(20, 20, 245, 100, grey);
        panel.add(infoPanel);

        profilePic = new JLabel();
        profilePic.setBounds(20, 20, 75, 75);
        infoPanel.add(profilePic);

        nameAndLevel = newJTextArea(105, 20, 200, 100,25, Color.white, grey, "");
        infoPanel.add(nameAndLevel);

        rankedPanel = newJPanel(20, 150, 245, 200, grey);
        panel.add(rankedPanel);

        soloQPic.setBounds(20, 30, 50, 50);
        rankedPanel.add(soloQPic);

        JTextArea soloQHeader = newJTextArea(20,10,100,20,11,Color.white, grey,
                "Solo/Duo");
        rankedPanel.add(soloQHeader);

        soloQText = newJTextArea(20,85,100,50,10, Color.white, grey,"");
        rankedPanel.add(soloQText);

        flexQPic.setBounds(150, 30, 50, 50);
        rankedPanel.add(flexQPic);

        flexQText = newJTextArea(150, 85, 100,50,10, Color.white, grey,"");
        rankedPanel.add(flexQText);

        JTextArea flexQHeader = newJTextArea(150,10,100,20,11, Color.white, grey,
                "Flex");
        rankedPanel.add(flexQHeader);

        int offset = 320;

        for (int i = 0; i < 5; i++) {
            MatchSummaryGUI msg = new MatchSummaryGUI(20,offset,245, 80, grey);
            matchList.add(msg);
            offset += 90;
            panel.add(msg.matchHistory);
        }

        /*
        panel.setMinimumSize(new Dimension(300, 2000));
        scroll.setPreferredSize(new Dimension(300, 900));
        scroll.setMaximumSize(new Dimension(300, 900));
        scroll.setMinimumSize(new Dimension(300, 900));
        */

        panel.setPreferredSize(new Dimension(300, offset));

        scroll.setViewportView(panel);
        System.out.println(panel.getSize().toString());
        System.out.println(scroll.getSize().toString());
        frame.add(scroll);
        scroll.getVerticalScrollBar().setValue(0);
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
