package trainticketbooking;


import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Amruta
 * This is the home page. It has four options, book ticket, show booked ticket, recharge wallet and cancel ticket. 
 */
public final class HomeFrame extends JFrame implements ActionListener{
    Container container=getContentPane();
    
    private final JLabel image=new JLabel(new ImageIcon(getClass().getResource("images/train.jpg")));
    private final JLabel welcomeLabel= new JLabel("WELCOME TO MMTS TRAIN TICKET BOOKING");    
    private final JButton BookButton = new JButton("BOOK TICKET");
    private final JButton WalletButton = new JButton("RECHARGE WALLET");
    private final JButton ShowButton = new JButton("SHOW BOOKED TICKET");
    private final JButton CancelButton= new JButton("CANCEL TICKET");
 
    HomeFrame() {
    
        setLayoutManager();
        
        this.welcomeLabel.setForeground(Color.BLUE);
        Font welcomefont=this.welcomeLabel.getFont();
        this.welcomeLabel.setFont(new Font(welcomefont.getName(),Font.BOLD,15));
        
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
 
    }
 
    public void setLayoutManager() {
        container.setLayout(null);
    }
 
    public void setLocationAndSize() {
        welcomeLabel.setBounds(10, 30, 350, 30);
        image.setBounds(50,60,250,200);
        BookButton.setBounds(75, 275, 200, 30);
        WalletButton.setBounds(75,325 , 200, 30);
        ShowButton.setBounds(75,375 , 200, 30);
        CancelButton.setBounds(75,425, 200, 30);
    }
 
    public void addComponentsToContainer() {
        container.add(image);
        container.add(welcomeLabel);
        container.add(WalletButton);        
        container.add(ShowButton);
        container.add(BookButton);
        container.add(CancelButton);
    }
 
    public void addActionEvent() {
        BookButton.addActionListener(this);
        WalletButton.addActionListener(this);
        ShowButton.addActionListener(this);
        CancelButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         
        if(e.getSource()==BookButton){
                BookTicketFrame frame=new BookTicketFrame();
                frame.setTitle("Book Ticket Form");
                frame.setVisible(true);
                frame.setBounds(10,10,370,500);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                this.dispose();        
        }
        
        else if(e.getSource()==WalletButton){
            WalletFrame frame=new WalletFrame();
            frame.setTitle("Recharge wallet Form");
            frame.setVisible(true);
            frame.setBounds(10,10,370,500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            this.dispose();
        
        
        }
        else if(e.getSource()==ShowButton){
            //Here previously booked tickets by the user are shown when ID is given as input.
            try{
                int Id=Integer.parseInt(JOptionPane.showInputDialog("Enter the ticket ID: (Press OK to cancel ticket)"));
                ShowTicket frame=new ShowTicket(Id);
                frame.setTitle("Ticket");
                frame.setVisible(true);
                frame.setBounds(10,10,370,500);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                this.dispose();
            
            }catch(NumberFormatException n){
                System.out.println(n);
            }catch(IllegalArgumentException x){
                JOptionPane.showMessageDialog(this, "No booked tickets with given ID.");
            }catch(HeadlessException ex){
                System.err.print(ex);
            }catch(Exception ex){
                System.err.print(ex);
            }        
        }
        else if(e.getSource()==CancelButton){
            //Here, ticket with given ID is cancelled and Rs 5 is added to wallet balance as long as cancellation
            // is within the date or time of journey.
            try{
                int Id=Integer.parseInt(JOptionPane.showInputDialog("Enter the ticket ID: (Press OK to cancel ticket)"));
                TrainTicketBooking.loadData();
                UserDetails u=TrainTicketBooking.getUserDetails(LoginFrame.getLoggedInUsername());
                Tickets t=u.getTicket(Id);
                
                String ticketdate=t.getStringDateofJourney();
                String tickettime=t.getStringTimeofJourney();
                LocalDate currentdate=LocalDate.now();
                LocalDate journeydate=LocalDate.parse(ticketdate,DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                LocalTime currenttime=LocalTime.now();
                LocalTime journeytime=LocalTime.parse(tickettime,DateTimeFormatter.ofPattern("HH:mm"));
                
                if(currentdate.isAfter(journeydate)){
                    JOptionPane.showMessageDialog(this, "Cancelling ticket after journey date is not possible.");
                }else if(currentdate.isEqual(journeydate) && currenttime.isAfter(journeytime)){
                    JOptionPane.showMessageDialog(this, "Cancelling ticket after journey time is not possible.");
                }else{
                    u.removeTicket(t);//The ticket is remove from arraylist of booked tickets.
                    u.addWalletBalance((float) 5);
                    TrainTicketBooking.replaceUserDetails(u, LoginFrame.getLoggedInUsername());
                    TrainTicketBooking.saveData();
                    JOptionPane.showMessageDialog(this,"Your ticket was successfully cancelled.");
                }
            }catch(NumberFormatException n){
                System.out.println(n);
            }catch(IllegalArgumentException ex){
                JOptionPane.showMessageDialog(this, "Ticket with given ID is not available.");
            }
        }
        
        else{
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
    
}
