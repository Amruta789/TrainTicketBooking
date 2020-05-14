package trainticketbooking;


import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Amruta
 */
public final class AddMoneyFrame extends JFrame implements ActionListener{
    Container container=getContentPane();
    private final JLabel cardnoLabel= new JLabel("Enter 16-digit card number: ");
    private final JLabel cvvlabel= new JLabel("Enter 3-digit CVV: ");
    private final JLabel expdatelabel= new JLabel("Enter expiry date (MM/YY): ");
    private final JLabel pinLabel = new JLabel("Enter 4-digit PIN: ");
    private final JLabel addmoneyLabel = new JLabel("ENTER AMOUNT TO BE ADDED: ");
    private final JFormattedTextField cardnoTextField;
    private final JPasswordField pinField = new JPasswordField(4);
    private final JFormattedTextField cvvField;
    private final JFormattedTextField amountTextField;
    private final JFormattedTextField expdateTextField;
    private final JButton verifyButton = new JButton("VERIFY AND ADD");
    private final JButton backButton=new JButton("BACK");
    private final NumberFormat amountformat;
    
 
    AddMoneyFrame() throws ParseException {
        this.cardnoTextField = new JFormattedTextField(new MaskFormatter("################"));
        this.cvvField = new JFormattedTextField(new MaskFormatter("###"));
        this.expdateTextField = new JFormattedTextField(new SimpleDateFormat("MM/yy"));
        
        this.amountformat = NumberFormat.getNumberInstance();
        this.amountformat.setMaximumIntegerDigits(5);
        this.amountformat.setMinimumIntegerDigits(2);
        this.amountformat.setMaximumFractionDigits(2);
        this.amountTextField=new JFormattedTextField(this.amountformat);
        
        setLayoutManager();
        setLocationAndSize();
        addComponentsToContainer();
        addActionEvent();
        
        PlainDocument document = (PlainDocument) pinField.getDocument();
        document.setDocumentFilter(new DocumentFilter() {

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String string = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;

                if (string.length() <= 4) {
                    super.replace(fb, offset, length, text, attrs); //To change body of generated methods, choose Tools | Templates.
                }
            }

        });
    }
 
    public void setLayoutManager() {
        container.setLayout(null);
    }
 
    public void setLocationAndSize() {
        cardnoLabel.setBounds(30, 50, 175, 30);
        cvvlabel.setBounds(30, 100, 150, 30);
        expdatelabel.setBounds(30, 150, 150, 30);
        pinLabel.setBounds(30, 200, 150, 30);
        addmoneyLabel.setBounds(30, 250, 200,30 );        
        cardnoTextField.setBounds(200, 50, 150, 30);
        cvvField.setBounds(200, 100, 50, 30);
        expdateTextField.setBounds(200, 150, 50, 30);
        pinField.setBounds(200, 200, 50, 30);        
        amountTextField.setBounds(220, 250, 100, 30);
        backButton.setBounds(30,300, 75, 30);
        
        verifyButton.setBounds(150,300, 130, 30); 
    }
 
    public void addComponentsToContainer() {
        container.add(cardnoLabel);
        container.add(cvvlabel);
        container.add(pinLabel);
        container.add(addmoneyLabel);
        container.add(cardnoTextField);
        container.add(pinField);
        container.add(expdatelabel);
        container.add(expdateTextField);
        container.add(amountTextField);
        container.add(verifyButton);
        container.add(cvvField);
        container.add(backButton);
    }
 
    public void addActionEvent() {
        
        verifyButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
         //To change body of generated methods, choose Tools | Templates.
        if(e.getSource()==backButton){
            WalletFrame frame=new WalletFrame();
                frame.setTitle("Recharge wallet Form");
                frame.setVisible(true);
                frame.setBounds(10,10,370,500);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                this.dispose();
        
        }
        else if(e.getSource()==verifyButton){
           try{
                Date exp= (Date) expdateTextField.getValue();
                SimpleDateFormat dateformat=new SimpleDateFormat("MM/yy");
                String expdate=dateformat.format(exp);
                YearMonth ym=YearMonth.parse(expdate, DateTimeFormatter.ofPattern("MM/yy"));
                LocalDate expirydate=ym.atEndOfMonth();
                LocalDate currentdate=LocalDate.now();
                
                if(currentdate.isAfter(expirydate)){
                    JOptionPane.showMessageDialog(this,"Your card has expired");
                }else if(expirydate.isAfter(currentdate.plusYears(10))){
                    JOptionPane.showMessageDialog(this, "Invalid expiry date.");
                }else{
                    TrainTicketBooking.loadData();
                    UserDetails u=TrainTicketBooking.getUserDetails(LoginFrame.getLoggedInUsername());
                    u.addWalletBalance(Float.parseFloat(String.valueOf(amountTextField.getValue())));
                    TrainTicketBooking.replaceUserDetails(u, LoginFrame.getLoggedInUsername());
                    TrainTicketBooking.saveData();
                    JOptionPane.showMessageDialog(this,"Successfully verified and wallet has been recharged.");
                }
           }catch(NumberFormatException |NullPointerException ex){
               JOptionPane.showMessageDialog(this, "Enter values in all text fields.");
           }catch(UnsupportedOperationException ex){
                System.err.print(ex);
            }catch(Exception ex){
                System.err.print(ex);
            }
        }
        else{
            throw new UnsupportedOperationException("Not supported yet.");
        }
    
    
    
    }
    
}
