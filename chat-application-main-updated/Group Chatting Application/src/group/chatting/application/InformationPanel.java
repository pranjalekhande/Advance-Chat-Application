package group.chatting.application;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InformationPanel extends JFrame {
    private JButton backButton;
    public InformationPanel() {

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Add group name label
        JLabel groupNameLabel = new JLabel("GroupChat");
        groupNameLabel.setFont(new Font("Arial", Font.BOLD, 20));
        groupNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(groupNameLabel);

        infoPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Add vertical spacing

        // Add user information
        ImageIcon userIcon = new ImageIcon(ClassLoader.getSystemResource("icons/group-chat-logo.png"));
        Image scaledImage = userIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel profilePicLabel = new JLabel(scaledIcon);
        profilePicLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        infoPanel.add(profilePicLabel);

//        infoPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add vertical spacing
//
//        JTextArea groupDescription = new JTextArea("This is a group chat for the\ncharacters of the popular\nweb series Mirzapur.");
//        groupDescription.setFont(new Font("Arial", Font.PLAIN, 14));
//        groupDescription.setLineWrap(true);
//        groupDescription.setWrapStyleWord(true);
//        groupDescription.setEditable(false);
//        groupDescription.setBackground(null);
//        groupDescription.setAlignmentX(Component.CENTER_ALIGNMENT);
//        infoPanel.add(groupDescription);

        infoPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Add vertical spacing


        // Add user information
        addUserInfo(infoPanel, "icons/User_Icon_1.png", "Praveen");
        infoPanel.add(createSeparator());

        addUserInfo(infoPanel, "icons/User_Icon_2.png", "Pranjal");
        infoPanel.add(createSeparator());
        addUserInfo(infoPanel, "icons/User_Icon_1.png", "Shriya");
        infoPanel.add(createSeparator());

        addUserInfo(infoPanel, "icons/User_Icon_2.png", "Sudtida");


        add(infoPanel, BorderLayout.CENTER);





        // Create the back button
        backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Switch back to the messaging screen
                dispose(); // Close the information screen
                new UserOne(); // Open the messaging screen

            }
        });
        add(backButton, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 700);
        setLocationRelativeTo(null);
        setVisible(true);
    }


    private Component createSeparator() {
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.GRAY); // Set the separator color
        separator.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the separator
        return separator;
    }

    private void addUserInfo(JPanel panel, String iconPath, String userName) {
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS));
        userPanel.setBackground(Color.WHITE);
        userPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        ImageIcon userIcon = new ImageIcon(ClassLoader.getSystemResource(iconPath));
        Image scaledImage = userIcon.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        JLabel profilePicLabel = new JLabel(scaledIcon);
        userPanel.add(profilePicLabel);

        userPanel.add(Box.createRigidArea(new Dimension(10, 0))); // Add horizontal spacing
        JLabel userNameLabel = new JLabel(userName);
        userNameLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        userPanel.add(userNameLabel);



        panel.add(userPanel);
        panel.add(Box.createRigidArea(new Dimension(0, 10))); // Add vertical spacing
    }

}
