// GUI setting up using java

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Mainframe extends JFrame {
    final private Font mainFont = new Font("Segoe print", Font.BOLD, 18);
    JTextField tfFirstName;
    JTextField tfLastName;
    JLabel labelWelcome;



    public void initialize(){
        /********************** Form Panel************************/
        JLabel labelFirstName = new JLabel("FirstName");
        labelFirstName.setFont(mainFont);

        tfFirstName = new JTextField();
        tfFirstName.setFont(mainFont);

        JLabel labelLastName = new JLabel("Last Name");
        labelLastName.setFont(mainFont);

        tfLastName = new JTextField();
        tfLastName.setFont(mainFont);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 1, 5, 5));
        formPanel.setOpaque(false);
        formPanel.add(labelFirstName);
        formPanel.add(tfFirstName);
        formPanel.add(labelLastName);
        formPanel.add(tfLastName);


        /***** Welcome Label ******/
        labelWelcome = new JLabel();
        labelWelcome.setFont(mainFont);

        JButton btnOK = new JButton("OK");
        btnOK.setFont(mainFont);
        btnOK.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                String firstName = tfFirstName.getText();
                String lastName = tfLastName.getText();
                labelWelcome.setText("Hello " + firstName + " " + lastName);
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }
        });

        JButton btnClear = new JButton("Clear");
        btnClear.setFont(mainFont);
        btnClear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
         

                tfFirstName.setText("");
                tfLastName.setText("");
                labelWelcome.setText("");
                throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
            }
            
        });
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 5, 5));
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(btnOK);
        buttonsPanel.add(btnClear);


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(128, 128, 255));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(labelWelcome,BorderLayout.CENTER);
        mainPanel.add(buttonsPanel,BorderLayout.SOUTH);

        add(mainPanel);


        setTitle("Welcome");
        setSize(500, 500);
        setMinimumSize(new Dimension(300, 400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }


    
    /** 
     * @param args
     */
    public static void main(String[] args){
        Mainframe myframe = new Mainframe();
        myframe.initialize();
    }

}
