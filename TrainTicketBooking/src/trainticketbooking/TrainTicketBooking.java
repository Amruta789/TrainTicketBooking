package trainticketbooking;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.*;


/**
 * @author Amruta
 * This project is my own version of UTS train ticket booking application.
 * Here, it is assumed that the ticket price is Rs 5 by default.
 * I have also included my own features such as the username is printed on the ticket as passenger name.
 * This can be used to book MMTS train tickets on Lingampalli-Falaknuma and Lingampalli-Hyderabad train lines.
 * The tickets can also be booked a maximum of 15 days in advance.
 * There is a wallet for every user. I have only only considered credit or debit card modes of payment.
 * 
 */
public class TrainTicketBooking implements Serializable{
       //Every ticket, irrespective of user, has a unique ID.
       //To make sure the ID is unique, this variable is incremented each time a ticket is booked.
       //This variable is serialised to make sure its value is saved. 
       private static int trainticketid=0;
       public static void setTrainTicketID(){
           trainticketid++;
       }
       public static int getTrainTicketID(){
           return trainticketid;
       }

       //An arraylist to store objects of UserDetails class. This arraylist is serialised.
       private static ArrayList<UserDetails> userdetails=new ArrayList<>();       
       public static void addUserDetails(UserDetails u){
           userdetails.add(u);
       }

    /**
     *
     * @param username
     * @return 
     * @throws UnsupportedOperationException
     */
    public static UserDetails getUserDetails(String username) throws UnsupportedOperationException{
           for(UserDetails u: userdetails){
               if(!u.getUsername().equals(username))
                   continue;
               return u;
           }
           throw new UnsupportedOperationException("Username does not exist.");
       }
       public static int getIndexOfUserDetails(UserDetails u){
           return userdetails.indexOf(u);
       }
       
       public static void replaceUserDetails(UserDetails u,String username){
           UserDetails old=getUserDetails(username);
           int index=getIndexOfUserDetails(old);
           userdetails.set(index,u);
       }       
       
       public static boolean checkUsername(String name){
           return userdetails.stream().anyMatch((u) -> (u.getUsername().equals(name)));
       }
       
       //The methods to serialise and deserialise the data is as follows.
       public static void saveData(){
		//Massive object to store all our objects
		ArrayList<Object> data = new ArrayList<>();
		data.add(trainticketid);
		data.add(userdetails);				
		try {
                    try (FileOutputStream fileOut = new FileOutputStream("data.ser"); 
                            ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
                        out.writeObject(data);
                    }
			System.out.println("Serialized data is saved in data.ser");
			
		} catch(IOException i){
			System.err.print(i);
                }catch(Exception ex){
                    System.err.print(ex);
                } 

	}
        public static void loadData(){
		//create arraylist to store deserialized objects
		ArrayList<Object> deserialized = new ArrayList<>();
		
		try {
                    try (FileInputStream fileIn = new FileInputStream("data.ser"); ObjectInputStream in = new ObjectInputStream(fileIn)) {
                        deserialized = (ArrayList<Object>)in.readObject();
                    }
		} catch(IOException | ClassNotFoundException i){
			System.err.print(i);
			return;
		}catch(Exception ex){
                    System.err.print(ex);
                }
		
		//Separate all the objects in the one deserialized object
		trainticketid= (int) deserialized.get(0);
		userdetails = (ArrayList<UserDetails>)deserialized.get(1);
		
        }
       
       
       
       
       public static void main(String[] args) {
        // First frame is login.
            LoginFrame frame=new LoginFrame();
            frame.setTitle("Login Form");
            frame.setVisible(true);
            frame.setBounds(10,10,370,500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
        
    }
    
}
