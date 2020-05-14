package trainticketbooking;


import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Amruta
 */
public final class LoginFrame extends JFrame implements ActionListener{
    //This value stores username of the person logged in.
    private static String loginusername; 
    
    
    Container container=getContentPane();        
    private final JLabel welcomeLabel= new JLabel("WELCOME TO MMTS TRAIN TICKET BOOKING");
    private final JLabel label= new JLabel("Please sign in to continue. ");
    private final JLabel otherlabel= new JLabel("New user?  Then please register.");
    private final JLabel userLabel = new JLabel("USERNAME");
    private final JLabel passwordLabel = new JLabel("PASSWORD");
    private final JTextField userTextField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton loginButton = new JButton("LOGIN");
    private final JButton resetButton = new JButton("RESET");
    private final JButton registerButton = new JButton("REGISTER");    
    private final JCheckBox showPassword = new JCheckBox("Show Password");
 
    
    LoginFrame() {
        setLayoutManager();
        this.welcomeLabel.setForeground(Color.black);
        Font welcomefont=this.welcomeLabel.getFont();
        this.welcomeLabel.setFont(new Font(welcomefont.getName(),Font.BOLD,15));
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
 
    }
    //Set and get methods for login username. Can be accessed anywhere in/out this package.
    public static void setLoggedInUser(String name){
        loginusername=name;
    }
    public static String getLoggedInUsername(){
        return loginusername;
    }
     
    public void setLayoutManager() {
        container.setLayout(null);
    }
 
    public void setLocationAndSize() {
        welcomeLabel.setBounds(10, 50, 350,30 );
        label.setBounds(50, 100, 200, 30);
        userLabel.setBounds(50, 150, 100, 30);
        passwordLabel.setBounds(50, 220, 100, 30);
        userTextField.setBounds(150, 150, 150, 30);
        passwordField.setBounds(150, 220, 150, 30);
        showPassword.setBounds(150, 250, 150, 30);
        loginButton.setBounds(50, 300, 100, 30);
        resetButton.setBounds(200, 300, 100, 30);
        otherlabel.setBounds(50, 350, 200, 30);
        registerButton.setBounds(100,400 , 100, 30);
 
    }
 
    public void addComponentsToContainer() {
        container.add(welcomeLabel);
        container.add(label);
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(showPassword);
        container.add(loginButton);
        container.add(resetButton);
        container.add(otherlabel);
        container.add(registerButton);
    }
 
    public void addActionEvent() {
        loginButton.addActionListener(this);
        resetButton.addActionListener(this);
        showPassword.addActionListener(this);
        registerButton.addActionListener(this);
    }
 
 
   
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == loginButton) {
            String userText;
            String pwdText;
            userText = userTextField.getText();
            pwdText = String.valueOf(passwordField.getPassword());
            UserDetails loginuser;
            try{
                TrainTicketBooking.loadData();
                loginuser=TrainTicketBooking.getUserDetails(userText);
                
                if (pwdText.equals(loginuser.getPassword())) {                
                    setLoggedInUser(userText);
                    
                    HomeFrame frame=new HomeFrame();
                    frame.setTitle("Home Form");
                    frame.setVisible(true);
                    frame.setBounds(10,10,370,500);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setResizable(false);
                    this.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid Username or Password");
                }
            }catch(UnsupportedOperationException n){
                JOptionPane.showMessageDialog(this, "Username does not exist.");
            }catch(Exception ex){
                System.err.print(ex);
            }
            
 
        }
       
        else if (e.getSource() == resetButton) {
            userTextField.setText("");
            passwordField.setText("");
        }
       
        else if (e.getSource() == showPassword) {
            if (showPassword.isSelected()) {
                passwordField.setEchoChar((char) 0);
            } else {
                passwordField.setEchoChar('*');
            }
        }
        else if(e.getSource()==registerButton){
            RegisterFrame frame;
            try {
                frame = new RegisterFrame();
                frame.setTitle("Register Form");
                frame.setVisible(true);
                frame.setBounds(10,10,370,500);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                this.dispose();
            } catch (ParseException ex) {
                Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
            } catch(Exception ex){
                System.err.print(ex);
            }
            
        
        }
        else{
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }   
    
}
