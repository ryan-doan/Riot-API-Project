import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.imageio.*;

/*
 * GUI
 *
 * Main application
 */

public class GUI {
    static final String API_key = "?api_key=RGAPI-61e24667-0dba-41a6-a866-532ad8226a7f";
    static JFrame frame = new JFrame();
    static JPanel home;
    static JTextField searchField = new JTextField();
    static JButton searchButton = new JButton();
    static String summonerName;
    static int profileIconId = 0;
    static int level = 0;
    static boolean validName = false;

    static ActionListener search = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            summonerName = searchField.getText();

            try {
                //Connects to Rito's API
                URL url = new URL ("https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" +
                        summonerName.replaceAll(" ", "") + API_key);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                //Get response code

                int responseCode = connection.getResponseCode();

                //Get player data

                String summonerData = reader.readLine();

                System.out.println(responseCode);

                if (responseCode == 200) {  //Making sure the player exists before we do anything else
                    validName = true;

                    // Formats data and put it into a txt file so its easier to read
                    // TODO delete txt files at the end to save space, might have to find a better way to store data on
                    // RAM but still makes it easier to read out from

                    summonerData = summonerData.substring(1, summonerData.length() - 1).replaceAll("\"",
                            "").replaceAll(",", "\n");

                    PrintWriter writer = new PrintWriter(new FileWriter(summonerName + ".txt"));

                    writer.println(summonerData);
                    writer.flush();
                    writer.close();

                    //Read the important bits of the code

                    BufferedReader fileReader = new BufferedReader(new FileReader(summonerName + ".txt"));

                    skipLine(fileReader, 4);
                    profileIconId = Integer.parseInt(fileReader.readLine().replaceAll("profileIconId:",
                            ""));

                    fileReader.readLine();

                    level = Integer.parseInt(fileReader.readLine().replaceAll("summonerLevel:", ""));
                    fileReader.close();

                    //Player found, display data
                    System.out.println("Valid");
                    JPanel profile = new JPanel();
                    profile.setSize(450, 800);
                    profile.setLayout(new GridBagLayout());
                    JLabel name = new JLabel(summonerName);
                    name.setVisible(true);
                    JLabel levelLabel = new JLabel(" Level: " + level);
                    levelLabel.setVisible(true);
                    JLabel profilePic = new JLabel();

                    try {
                        BufferedImage image = ImageIO.read(new URL("http://ddragon.leagueoflegends.com/cdn/" +
                                "11.16.1/img/profileicon/" + profileIconId + ".png"));
                        profilePic = new JLabel(new ImageIcon(image));
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }  //using the profileIconId, fetch profile image from league's database

                    GridBagConstraints gbc = new GridBagConstraints();
                    gbc.anchor = GridBagConstraints.NORTH;
                    profile.add(name, gbc);
                    profile.add(levelLabel, gbc);
                    gbc.anchor = GridBagConstraints.CENTER;
                    profile.add(profilePic, gbc);
                    frame.remove(home);
                    frame.add(profile);
                    frame.setVisible(true);
                }

                reader.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    };

    public static void main(String[] args) {
        // Initialises JFrame

        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(850, 800);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());

        // Create and add objects to JFrame

        home = new JPanel();
        home.setSize(450,800);
        home.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        searchButton.setSize(200, 150);
        searchButton.setText("Search");
        searchButton.setBackground(Color.cyan);
        searchButton.setVisible(true);
        searchButton.addActionListener(search);
        gbc.anchor = GridBagConstraints.CENTER;
        home.add(searchButton, gbc);

        searchField.setSize(500,150);
        searchField.setColumns(20);
        searchField.setVisible(true);
        gbc.anchor = GridBagConstraints.PAGE_START;
        home.add(searchField, gbc);

        home.setVisible(true);
        frame.add(home);
        frame.setVisible(true);
    }

    public static void skipLine(BufferedReader reader, int lines) throws Exception {
        for (int i = 0; i < lines; i++) {
            reader.readLine();
        }
    }  //this is to skip multiple lines on a txt file
}
