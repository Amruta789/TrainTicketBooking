package trainticketbooking;


import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
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
public final class BookTicketFrame extends JFrame implements ActionListener {
    Container container=getContentPane();
    private final String stations[]={"Lingampalli","Chandanagar","Hafeezpet","Hitech City","Borabanda","Bharatnagar",
    "Fatehnagar","Nature Cure Hospital","Begumpet","Necklace Road","Khairatabad","Lakdikapul","Hyderabad",
    "Sanjeevaiah Park","James Street","Secunderabad","Sitaphalmandi","Arts College",
    "Jamai Osmania","Vidyanagar","Kacheguda","Malakpet","Dabirpura","Yakutpura","Huppuguda","Falaknuma"};
    
    private final JLabel image=new JLabel(new ImageIcon(getClass().getResource("images/mmtsstations.png"))); 
    private final JLabel fromLabel= new JLabel("FROM STATION");
    private final JLabel tolabel= new JLabel("TO STATION");
    private final JLabel datelabel = new JLabel("DATE OF JOURNEY (DD-MM-YYYY)");
    private final JLabel timeLabel = new JLabel("TIME OF JOURNEY (HH(6-22):MM)");
    private final JComboBox fromstation= new JComboBox(stations);
    private final JComboBox tostation=new JComboBox(stations);
    private final JFormattedTextField dateTextField;
    private final JFormattedTextField timeTextField;
    private final JButton backButton = new JButton("BACK");
    private final JButton bookButton = new JButton("BOOK TICKET");
    private final JButton resetButton = new JButton("RESET");
 
 
    BookTicketFrame() {
        this.dateTextField = new JFormattedTextField(new SimpleDateFormat("dd-MM-yyyy"));
        this.timeTextField = new JFormattedTextField(new SimpleDateFormat("HH:mm"));
        
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
 
    }
 
    public void setLayoutManager() {
        container.setLayout(null);
    }
 
    public void setLocationAndSize() {        
        image.setBounds(10, 10,  350,230);
        fromLabel.setBounds(50, 240, 100,30 );
        fromstation.setBounds(150,240, 150, 30);
        tolabel.setBounds(50, 270, 100, 30);
        tostation.setBounds(150,270, 150, 30);
        datelabel.setBounds(50, 310, 200, 30);
        dateTextField.setBounds(250,310, 75, 30);
        timeLabel.setBounds(50, 350, 200, 30);
        timeTextField.setBounds(250,350, 75, 30);
        backButton.setBounds(50, 390, 75, 30);
        resetButton.setBounds(200,390,75,30);
        bookButton.setBounds(100,430, 150, 30); 
    }
 
    public void addComponentsToContainer() {
        container.add(image);
        container.add(timeLabel);
        container.add(tolabel);
        container.add(fromLabel);
        container.add(fromstation);
        container.add(tostation);
        container.add(datelabel);
        container.add(timeTextField);
        container.add(backButton);
        container.add(dateTextField);
        container.add(bookButton);        
        container.add(resetButton);
    }
 
    public void addActionEvent() {
        
        backButton.addActionListener(this);
        bookButton.addActionListener(this);
        resetButton.addActionListener(this);
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
        else if(e.getSource()==resetButton){
            fromstation.setSelectedIndex(0);
            tostation.setSelectedIndex(0);
            dateTextField.setText("");
            timeTextField.setText("");
        }
        else if(e.getSource()==bookButton){
            Date curdate=new Date();
            Calendar cal=Calendar.getInstance();
            cal.setTime(curdate);
            cal.add(Calendar.DAY_OF_MONTH,15);
            Date dateafterfortnight=cal.getTime();
            SimpleDateFormat dateformat=new SimpleDateFormat("dd-MM-yyyy");
            Date currentdate = null;
            try {
                currentdate=dateformat.parse((dateformat.format(curdate)));
            } catch (ParseException ex) {
                Logger.getLogger(BookTicketFrame.class.getName()).log(Level.SEVERE, null, ex);
            }catch(Exception ex){
                System.err.print(ex);
            }
            
            LocalTime morning=LocalTime.parse("06:00");
            LocalTime evening=LocalTime.parse("22:00");
            SimpleDateFormat timeformat=new SimpleDateFormat("HH:mm");
            LocalTime inputtime=LocalTime.parse(timeformat.format((Date)timeTextField.getValue()));
                        
            if(fromstation.getSelectedItem()==tostation.getSelectedItem()){
                JOptionPane.showMessageDialog(this, "From station and to station should be different.");            
            }else if(inputtime.isBefore(morning) || inputtime.isAfter(evening) ){
                JOptionPane.showMessageDialog(this, "Trains run only between 6:00 am and 10:00 pm");
            }                
            else if(((Date) dateTextField.getValue()).before(currentdate)){
                JOptionPane.showMessageDialog(this,"Date must be >= current date.");                                
            }else if(((Date)dateTextField.getValue()).after(dateafterfortnight)){
                JOptionPane.showMessageDialog(this, "Tickets can only be booked 15 days in advance.");                    
            }else if(currentdate.compareTo(((Date) dateTextField.getValue()))==0 && (inputtime.isBefore(LocalTime.now()))){            
                    JOptionPane.showMessageDialog(this, "Time should be > current time.");
            }            
            else{                
                UserDetails u=TrainTicketBooking.getUserDetails(LoginFrame.getLoggedInUsername());
                try{
                    u.deductTicketPrice();
                    TrainTicketBooking.loadData();
                    TrainTicketBooking.setTrainTicketID();
                    Tickets t=new Tickets();
                    t.setTicketID(TrainTicketBooking.getTrainTicketID());
                    t.setFromStation((String) fromstation.getSelectedItem());
                    t.setToStation((String)tostation.getSelectedItem());
                    t.setDateofJourney((Date) dateTextField.getValue());
                    t.setTimeofJourney((Date) timeTextField.getValue());
                    u.addTicket(t);
                    TrainTicketBooking.replaceUserDetails(u, LoginFrame.getLoggedInUsername());
                    TrainTicketBooking.saveData();
                    
                    ShowTicket frame=new ShowTicket(TrainTicketBooking.getTrainTicketID());
                    frame.setTitle("Ticket");
                    frame.setVisible(true);
                    frame.setBounds(10,10,370,500);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setResizable(false);
                    this.dispose();
                }catch(UnsupportedOperationException n){
                    JOptionPane.showMessageDialog(this, "Insufficient Wallet Balance.");
                }catch(NullPointerException ex){
                    JOptionPane.showMessageDialog(this,"Enter values in all the text fields.");
                }catch(IllegalArgumentException ex){
                    System.err.print(ex);
                }catch(Exception ex){
                    System.err.print(ex);
                }               
            }        
        }
        
        else{
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }    
}
