package trainticketbooking;


import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 */
public final class ShowTicket extends JFrame implements ActionListener{
    UserDetails u;
    Tickets t;
    
    Container container=getContentPane();
    JLabel welcomeLabel= new JLabel("MMTS TRAIN TICKET");
    JLabel idLabel= new JLabel("TICKET ID: ");
    JLabel idlabel;
    JLabel nameLabel= new JLabel("PASSENGER NAME: ");
    JLabel namelabel;
    JLabel dateLabel= new JLabel("DATE OF JOURNEY: ");
    JLabel datelabel;
    JLabel timeLabel= new JLabel("TIME OF JOURNEY: ");
    JLabel timelabel;
    JLabel fromLabel= new JLabel("FROM STATION: ");
    JLabel fromlabel;
    JLabel toLabel= new JLabel("TO STATION: ");
    JLabel tolabel;
    JButton BackButton = new JButton("BACK");
    JButton printButton = new JButton("PRINT TICKET");
    
 
    ShowTicket(int ticketid) throws IllegalArgumentException{
        TrainTicketBooking.loadData();
        u = TrainTicketBooking.getUserDetails(LoginFrame.getLoggedInUsername());
        
        t = u.getTicket(ticketid);
        this.idlabel = new JLabel(String.valueOf(t.getTicketID()));
        this.tolabel = new JLabel(t.getToStation());
        this.fromlabel = new JLabel(t.getFromStation());
        this.timelabel = new JLabel(t.getStringTimeofJourney());
        this.datelabel = new JLabel(t.getStringDateofJourney());
        this.namelabel = new JLabel(u.getUsername());
        
        Font welcomefont=this.welcomeLabel.getFont();
        this.welcomeLabel.setFont(new Font(welcomefont.getName(),Font.BOLD,15));
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
 
    }
 
    public void setLayoutManager() {
        container.setLayout(null);
    }
 
    public void setLocationAndSize() {
        welcomeLabel.setBounds(50, 50, 300, 30);      
        idLabel.setBounds(50, 100, 100, 30);
        idlabel.setBounds(150,100 , 100, 30);
        nameLabel.setBounds(50,150 , 120, 30);
        namelabel.setBounds(170,150,200, 30);
        dateLabel.setBounds(50,200 , 120, 30);
        datelabel.setBounds(170,200, 100, 30);
        timeLabel.setBounds(50,250 , 120, 30);
        timelabel.setBounds(170,250, 100, 30);
        fromLabel.setBounds(50,300 , 100, 30);
        fromlabel.setBounds(150,300, 200, 30);
        toLabel.setBounds(50,350, 100, 30);
        tolabel.setBounds(150,350, 200, 30);
        BackButton.setBounds(50,400 , 75, 30);
        printButton.setBounds(150,400, 150, 30);
    }
 
    public void addComponentsToContainer() {
        container.add(welcomeLabel);
        container.add(idLabel);        
        container.add(idlabel);
        container.add(nameLabel);
        container.add(namelabel);
        container.add(dateLabel);
        container.add(datelabel);
        container.add(timeLabel);
        container.add(timelabel);
        container.add(fromLabel);
        container.add(fromlabel);
        container.add(toLabel);
        container.add(tolabel);
        container.add(BackButton);
        container.add(printButton);
        
    }
 
    public void addActionEvent() {
        BackButton.addActionListener(this);
        printButton.addActionListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==BackButton){
                HomeFrame frame=new HomeFrame();
                frame.setTitle("Home Form");
                frame.setVisible(true);
                frame.setBounds(10,10,370,500);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                this.dispose();
                
        }
        else if(e.getSource()==printButton){
            String ticketfileinfo="MMTS TRAIN TICKET\n"+
                    "TICKET ID: "+idlabel.getText()+"\n"+
                    "USERNAME: "+namelabel.getText()+"\n"+
                    "FROM STATION: "+fromlabel.getText()+"\n"+
                    "TO STATION: "+tolabel.getText()+"\n"+
                    "DATE OF JOURNEY: "+datelabel.getText()+"\n"+
                    "TIME OF JOURNEY: "+timelabel.getText();
            FileWriter fw = null; 
            try {
                fw = new FileWriter("Ticket.txt");
            } catch (IOException ex) {
                Logger.getLogger(ShowTicket.class.getName()).log(Level.SEVERE, null, ex);
            } catch(Exception ex){
                System.err.print(ex);
            }         
            for (int i = 0; i <ticketfileinfo.length(); i++) 
                try {
                    fw.write(ticketfileinfo.charAt(i));
                } catch (IOException ex) {
                    Logger.getLogger(ShowTicket.class.getName()).log(Level.SEVERE, null, ex);
                } catch(Exception ex){
                    System.err.print(ex);
                }
  
            JOptionPane.showMessageDialog(this,"Ticket file printed successfully."); 
          
            try { 
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(ShowTicket.class.getName()).log(Level.SEVERE, null, ex);
            }catch(Exception ex){
                System.err.print(ex);
            }
        }
        else{
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

        }
    
    
    
    }
    
}
