/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trainticketbooking;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Amruta
 * Objects of this class are details of tickets of a particular user.
 */
public class Tickets  implements Serializable{
    private int ticketid;
    private String fromstation;
    private String tostation;
    private final SimpleDateFormat dateformat=new SimpleDateFormat("dd-MM-yyyy");
    private final SimpleDateFormat timeformat=new SimpleDateFormat("HH:mm");
    private String dateofjourney;
    private String timeofjourney;
    
    public void setTicketID(int id){
        this.ticketid=id;
    }
    public void setFromStation(String station){
        this.fromstation=station;
    }
    public void setToStation(String station){
        this.tostation=station;
    }
    public void setDateofJourney(Date date){
        this.dateofjourney=dateformat.format(date);
    }
    public void setTimeofJourney(Date time){
        this.timeofjourney=timeformat.format(time);
    }
    
    public int getTicketID(){
        return ticketid;
    }
    public String getFromStation(){
        return fromstation;
    }
    public String getToStation(){
        return tostation;
    }
    public String getStringDateofJourney(){
        return dateofjourney;
    }
    public Date getDateofJourney() throws ParseException{
        return dateformat.parse(this.dateofjourney);
    }
    public Date getTimeofJourney() throws ParseException{
        return timeformat.parse(this.dateofjourney);
    }
    
    public String getStringTimeofJourney(){
        return timeofjourney;
    }
}
