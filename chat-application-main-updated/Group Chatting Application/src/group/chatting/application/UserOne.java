package group.chatting.application;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.net.*;
import java.io.*;
import java.util.Calendar;


public class UserOne implements ActionListener, Runnable {
    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    String flag1 = "";
    ImageIcon user1Icon;

    BufferedReader reader;
    BufferedWriter writer;
    String name = "Praveen";

    public UserOne() {

        f.setLayout(null);

        JPanel p1 = new JPanel();
        p1.setBackground(new Color(5, 35, 54));
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
        f.add(p1);

        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);

        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {

                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/group-chat-logo.png"));
        Image i5 = i4.getImage().getScaledInstance(60,60, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(40, 5, 60, 60);
        p1.add(profile);

        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(300, 20, 30, 30);
        p1.add(video);

        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35, 30, Image.SCALE_DEFAULT);
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360, 20, 35, 30);
        p1.add(phone);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10, 25, Image.SCALE_DEFAULT);
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(420, 20, 10, 25);
        p1.add(morevert);

        JLabel nameLabel = new JLabel("GroupChat");
        nameLabel.setBounds(110, 15, 100, 18);
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        p1.add(nameLabel);

        nameLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent ae) {
                new InformationPanel();

            }
        });

        JLabel status = new JLabel("Shriya, Sudtida, Pranjal, Praveen");
        status.setBounds(110, 35, 160, 18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        p1.add(status);

        a1 = new JPanel();
        a1.setBounds(5, 75, 440, 570);
        a1.setBackground(Color.WHITE);
        f.add(a1);

        // Create a JScrollPane that contains a1 and configure it
        JScrollPane scrollPane = new JScrollPane(a1);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(5, 75, 440, 570);
        scrollPane.setBorder(null);

        f.add(scrollPane);  // Add the scroll pane to the frame instead of a1 directly


        text = new PlaceholderTextField("Enter your message");
        text.setBounds(5, 655, 281, 40);
        text.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(text);

        //Emoji
        JButton emojiButton = new JButton("\uD83D\uDE42");
        emojiButton.setBounds(282, 655, 40, 40);
        emojiButton.addActionListener(e -> new EmojiPicker(f, text).setVisible(true));
        f.add(emojiButton);
        //Emoji end


        JButton send = new JButton("Send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(36, 64, 75));
//        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF", Font.PLAIN, 16));
        f.add(send);

        f.setSize(450, 700);
        f.setLocation(20, 50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);

        f.setVisible(true);

        String username = JOptionPane.showInputDialog(f, "Enter your username:");

        JPasswordField passwordField = new JPasswordField();
        int option = JOptionPane.showConfirmDialog(f, passwordField, "Enter your password:", JOptionPane.OK_CANCEL_OPTION);


        char[] passwordChars = passwordField.getPassword();
        String password = new String(passwordChars);

        // Authenticate the user
        if (UserAuthentication.authenticate(username, password)) {
            try {
                Socket socket = new Socket("localhost", 2003);
                writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Send the username to the server
                writer.write(username);
                writer.write("\r\n");
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(f, "Invalid username or password.", "Authentication Failed", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

        user1Icon = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));

    }

    public void actionPerformed(ActionEvent ae) {
        try {
            String message = text.getText().trim(); // I am doing Trim to remove leading/trailing whitespace it was giving error while reading message correctly


            // Checking if the message is empty after trimming, since the we dont want to send empty message (it was sending latest message again and again)
            if (message.equals("Type a message...") || message.isEmpty()) {
                JOptionPane.showMessageDialog(f, "Please enter a message before sending.", "Empty Message", JOptionPane.WARNING_MESSAGE);
                return; // Exit the method without sending the placeholder text or empty message
            }

            String out = "<html><p>" + name + "</p><p>" + text.getText() + "</p></html>";
            flag1 = "1";
            // Clearing the text field after sending the message
            text.setText("");

            JPanel p2 = formatLabel(out);

            a1.setLayout(new BorderLayout());

            JPanel right = new JPanel(new BorderLayout());
            right.setBackground(Color.WHITE);
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));

            a1.add(vertical, BorderLayout.PAGE_START);

            try {
                writer.write(out);
                writer.write("\r\n");
                writer.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }

            text.setText("");

            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public static JPanel formatLabel(String out) {
//        System.out.println(" got message from format label");

        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel( out);
        output.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));
        output.setBackground(new Color(32, 97, 121));
        output.setForeground(Color.WHITE);
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(10, 15, 10, 40));

        panel.add(output);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));

        panel.add(time);

        return panel;
    }



    public void run() {
        try {
            String msg = "";
            while(true) {
                msg = reader.readLine();
                if (flag1 == "1") {
                    flag1 = "";
                    continue;
                }

                getMessage(formatLabel(msg), vertical, a1, f, msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void getMessage(JPanel jPanel, Box vertical, JPanel a1, JFrame f, String msg) {

//        System.out.println("got message from in get message");
        JPanel panel = jPanel;

        JPanel left = new JPanel(new BorderLayout());
        left.setBackground(Color.WHITE);
        left.add(panel, BorderLayout.LINE_START);

        vertical.add(left);
        a1.add(vertical, BorderLayout.PAGE_START);
        a1.setAlignmentX( Component.LEFT_ALIGNMENT );

        //NEWLY ADDED FOR ALIGNMENT
        vertical.setAlignmentY(Component.TOP_ALIGNMENT);
        f.repaint();
        f.invalidate();
        f.validate();
    }
    public static void main(String[] args) {
        UserOne one = new UserOne();
        Thread t1 = new Thread(one);
        t1.start();
    }
    static class PlaceholderTextField extends JTextField {
        private String placeholder;

    public PlaceholderTextField(String placeholder) {
        this.placeholder = placeholder;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (getText().isEmpty()) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.GRAY);
                g2.setFont(getFont().deriveFont(Font.ITALIC));
                g2.drawString(placeholder, getInsets().left, g.getFontMetrics().getMaxAscent() + getInsets().top);
                g2.dispose();
            }
        }
    }
    static class EmojiPicker extends JDialog {
        private static final String[] EMOJI_CODES = {
                "\uD83D\uDE00", "\uD83D\uDE03", "\uD83D\uDE04", // Smileys
                "\uD83D\uDE1D", "\uD83D\uDE0D", "\uD83D\uDE18", // Emotions
                "❤", "\uD83D\uDC96", "\uD83C\uDF89"  // Miscellaneous
        };

        public EmojiPicker(JFrame parent, JTextField textField) {
            super(parent, "Select Emoji", true);
            setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            setSize(200, 200);
            setLocationRelativeTo(parent);

            JPanel emojiPanel = new JPanel(new GridLayout(3, 3));
            for (String emojiCode : EMOJI_CODES) {
                JButton emojiButton = new JButton(emojiCode);
                emojiButton.addActionListener(e -> {
                    textField.replaceSelection(emojiCode);
                    dispose();
                });
                emojiPanel.add(emojiButton);
            }

            add(emojiPanel);
        }
    }

}
