package trainticketbooking;


import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Amruta
 */
public final class WalletFrame extends JFrame implements ActionListener{
    Container container=getContentPane();
    private final JLabel balanceLabel= new JLabel("Current wallet balance is: ");
    private final JLabel walletlabel= new JLabel();
    private final JLabel addlabel= new JLabel("To add money, first select mode of payment: ");
    private final JRadioButton credit = new JRadioButton("CREDIT");
    private final JRadioButton debit = new JRadioButton("DEBIT");
    private final JButton backButton = new JButton("BACK");
    private final JButton addButton = new JButton("ADD MONEY TO WALLET");
    private final ButtonGroup g=new ButtonGroup();
   
 
    WalletFrame() {
        TrainTicketBooking.loadData();
        UserDetails u=TrainTicketBooking.getUserDetails(LoginFrame.getLoggedInUsername());
        walletlabel.setText(String.valueOf(u.getWalletBalance()));
        setLayoutManager();
        this.g.add(debit);
        this.g.add(credit);
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        
    }
 
    public void setLayoutManager() {
        container.setLayout(null);
    }
 
    public void setLocationAndSize() {
        balanceLabel.setBounds(50, 50, 150, 30);
        walletlabel.setBounds(200, 50, 100,30 );
        addlabel.setBounds(50, 100, 300, 30);       
        credit.setBounds(50, 150, 100, 30);
        debit.setBounds(50, 200, 100, 30);
        backButton.setBounds(50, 300, 75, 30);
        addButton.setBounds(150, 300, 200, 30); 
    }
 
    public void addComponentsToContainer() {
        container.add(balanceLabel);
        container.add(walletlabel);
        container.add(addlabel);
        container.add(credit);
        container.add(debit);
        container.add(backButton);
        container.add(addButton);
        
    }
 
    public void addActionEvent() {
        backButton.addActionListener(this);
        addButton.addActionListener(this);
    }
    
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
         //To change body of generated methods, choose Tools | Templates.
        if(e.getSource()==backButton){
            HomeFrame frame=new HomeFrame();
            frame.setTitle("Home Form");
            frame.setVisible(true);
            frame.setBounds(10,10,370,500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            this.dispose();
        
        }
        else if(e.getSource()==addButton){
            if(!debit.isSelected() && !credit.isSelected()){
                JOptionPane.showMessageDialog(this, "Please select type of payment.");
            }else{
                try {
                    AddMoneyFrame frame;
                    frame = new AddMoneyFrame();
                    frame.setTitle("Verification Form");
                    frame.setVisible(true);
                    frame.setBounds(10,10,370,500);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setResizable(false);
                    this.dispose();
                } catch (ParseException ex) {
                    Logger.getLogger(WalletFrame.class.getName()).log(Level.SEVERE, null, ex);
                } catch(Exception ex){
                    System.err.print(ex);
                }
            }
            
        
        }
        else{
            throw new UnsupportedOperationException("Not supported yet.");
        
        }
    
    
    }
    
    
    
}
