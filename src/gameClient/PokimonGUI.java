package gameClient;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class PokimonGUI extends JFrame implements ActionListener {

    JPanel panel;
    JLabel user_label, level_label, message;
    JTextField userName_text, level_text;
    JButton submit, cancel;

    PokimonGUI() {





        // User Label
        user_label = new JLabel();
        user_label.setText("Your ID :");
        userName_text = new JTextField();

        // Level

        level_label = new JLabel();
        level_label.setText("Level :");
        level_text = new JTextField();

        // Submit

        submit = new JButton("Start game");

        panel = new JPanel(new GridLayout(3, 1));

        panel.add(user_label);
        panel.add(userName_text);
        panel.add(level_label);
        panel.add(level_text);

        message = new JLabel();
        panel.add(message);
        panel.add(submit);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Adding the listeners to components..
        submit.addActionListener(this);
        add(panel, BorderLayout.CENTER);
        setTitle("Pokemon Go !");
        setSize(400, 100);
        setVisible(true);

    }

    public static void main(String[] args) {
        new PokimonGUI();
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String userName = userName_text.getText();
        String password = level_text.getText();

        int levelNum = -1;

        try {
            levelNum = Integer.parseInt(password.trim());
        }catch (Exception e){
            JOptionPane.showMessageDialog(panel,
                    "Invalid level number, Please try again",
                    "Inane warning",
                    JOptionPane.WARNING_MESSAGE);
        }

        if (levelNum >= 0 && levelNum < 24) {
            message.setText(" Hello " + userName
                    + ", level: " + levelNum );
        } else {
            message.setText(" Invalid user.. ");
        }

    }

}
