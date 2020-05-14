/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trainticketbooking;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Amruta
 * Objects of this class are details of a particular user.
 */
public class UserDetails implements Serializable {
    private String username;
    private String password;
    private Long mobilenumber;
    private String emailaddress;
    private float walletbalance=0;
    private final ArrayList<Tickets> tickets=new ArrayList<>();
    
    public void setUsername(String name){
        this.username=name;
    }
    public String getUsername(){
        return this.username;
    } 
    public void setPassword(String password){
        this.password=password;
    }
    public String getPassword(){
        return this.password;
    }
    public void setMobileNumber(Long mobilenum){
        this.mobilenumber=mobilenum;
    }
    public long getMobileNumber(){
        return this.mobilenumber;
    }
    public void setEmailAddress(String emailaddress){
        this.emailaddress=emailaddress;
    }
    public String getEmailAddress(){
        return this.emailaddress; 
    }
    
    public void addWalletBalance(float amount){
        this.walletbalance+=amount;
    }
    
    public void deductTicketPrice() throws UnsupportedOperationException{
        if(this.walletbalance<5.0){
            throw new UnsupportedOperationException("Insufficient Wallet balance.");
        }
        this.walletbalance-=5.0;
    }
    public float getWalletBalance(){
        return this.walletbalance;
    }
    /**
     *
     * @param tic
     */
    public void addTicket(Tickets tic){
        this.tickets.add(tic);
    }
    public void removeTicket(Tickets tic){
        this.tickets.remove(tic);
    }
    public Tickets getTicket(int id) throws IllegalArgumentException{
        for (Tickets tic : this.tickets) {
            if(tic.getTicketID()!=id){
                continue;
            }            
            return tic;            
        }
        throw new IllegalArgumentException("The given ID is not available.");
        
    }
}
