package trainticketbooking;


import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Amruta
 * User registration stores username, password, mobile number and email address. 
 */
public final class RegisterFrame extends JFrame implements ActionListener{
    Container container=getContentPane();
    private final JLabel confirmLabel= new JLabel("CONFIRM PASSWORD");
    private final JLabel mobilelabel= new JLabel("MOBILE NUMBER");
    private final JLabel emaillabel= new JLabel("EMAIL ADDRESS");
    private final JLabel userLabel = new JLabel("USERNAME");
    private final JLabel passwordLabel = new JLabel("PASSWORD");
    private final JTextField userTextField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JPasswordField confirmField = new JPasswordField();
    private final JFormattedTextField mobileTextField;
    private final JTextField emailTextField = new JTextField();
    private final JButton backButton= new JButton("BACK");
    private final JButton resetButton = new JButton("RESET");
    private final JButton registerButton = new JButton("REGISTER");
 
 
    RegisterFrame() throws ParseException {
        //It makes sure mobile number entered is 10-digit. 
        this.mobileTextField = new JFormattedTextField(new MaskFormatter("##########"));
        
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
 
    }
 
    public void setLayoutManager() {
        container.setLayout(null);
    }
 
    public void setLocationAndSize() {
        userLabel.setBounds(50, 50, 100, 30);
        passwordLabel.setBounds(50, 100, 100, 30);
        confirmLabel.setBounds(50, 150, 150,30 );
        mobilelabel.setBounds(50, 200, 100, 30);
        emaillabel.setBounds(50, 250, 100, 30);
        userTextField.setBounds(150, 50, 150, 30);
        passwordField.setBounds(150, 100, 150, 30);
        confirmField.setBounds(200, 150, 150, 30);
        mobileTextField.setBounds(150, 200, 150, 30);
        emailTextField.setBounds(150, 250, 200, 30);
        resetButton.setBounds(200, 300, 75, 30);
        registerButton.setBounds(100,350, 100, 30);
        backButton.setBounds(50,300,75,30);
 
    }
 
    public void addComponentsToContainer() {
        container.add(confirmLabel);
        container.add(emaillabel);
        container.add(userLabel);
        container.add(passwordLabel);
        container.add(userTextField);
        container.add(passwordField);
        container.add(mobilelabel);
        container.add(emailTextField);
        container.add(resetButton);
        container.add(mobileTextField);
        container.add(registerButton);
        container.add(confirmField);
        container.add(backButton);
    }
 
    public void addActionEvent() {
        resetButton.addActionListener(this);
        registerButton.addActionListener(this);
        backButton.addActionListener(this);
    }
    
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==resetButton){
            userTextField.setText("");
            passwordField.setText("");
            confirmField.setText("");
            mobileTextField.setValue("");
            emailTextField.setText("");        
        }
        else if(e.getSource()==registerButton){
            String pwdtext = "",cnftext = "";
            String username = "",email = "";
            long mobileno = 0;
            try{
                pwdtext=String.valueOf(passwordField.getPassword());
                cnftext=String.valueOf(confirmField.getPassword());
                username=userTextField.getText();
                email=emailTextField.getText();
                mobileno=Long.parseLong(String.valueOf(mobileTextField.getValue()));
               
            }catch(NumberFormatException | NullPointerException n){
                System.err.print(n);
            }
            
            if("".equals(pwdtext)||"".equals(cnftext)||"".equals(username)||"".equals(email)||mobileno==0){
                JOptionPane.showMessageDialog(this,"Enter values in all the fields.");
            }else{
                if(TrainTicketBooking.checkUsername(username)){
                    JOptionPane.showMessageDialog(this,"Username already exists.");
                }
                else if(!pwdtext.equals(cnftext)){
                    JOptionPane.showMessageDialog(this, "Confirm password field and password field don't match.");
                }else if(!Pattern.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$",email)){
                    JOptionPane.showMessageDialog(this,"Email is invalid.");
                }else{
                    
                    UserDetails u=new UserDetails();
                    u.setUsername(username);
                    u.setPassword(pwdtext);
                    u.setMobileNumber(mobileno);
                    u.setEmailAddress(email);
                    TrainTicketBooking.loadData();
                    TrainTicketBooking.addUserDetails(u);
                    TrainTicketBooking.saveData();
                    
                    JOptionPane.showMessageDialog(this, "Successfully registered!");
                    LoginFrame frame=new LoginFrame();
                    frame.setTitle("Login Form");
                    frame.setVisible(true);
                    frame.setBounds(10,10,370,500);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setResizable(false);
                    this.dispose();
                    
                    
                }
            }
        }
        else if(e.getSource()==backButton){
            LoginFrame frame=new LoginFrame();
            frame.setTitle("Login Form");
            frame.setVisible(true);
            frame.setBounds(10,10,370,500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            this.dispose();
        }
        else{
            throw new UnsupportedOperationException("Not supported yet."); 
        }
    }        
}
