/*
 * CS2852 - 041
 * Spring 2016
 * Lab 9
 * Name: Ian Guswiler
 * Created: 5/10/2016
 */

package lab9.guswilerib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

/**
 * Domain Name System. Utilizes a map to store domain name and ip address pairs. Domain names are used as keys.
 *
 * @author Ian Guswiler
 * @version 5/19/2016
 */
public class DNS{
    private File storageFile;
    private Map<DomainName,IPAddress> map;
    private boolean started = false;

    /**
     * Constructs a new domain name system with a specified file to load files from and store the entries in when the system is stopped.
     * @param filename file to load from and store to
     */
    public DNS(String filename){
        storageFile = new File(filename);
    }

    /**
     * Starts the system.
     * @return returns true if the system was started successfully or was already started
     */
    public boolean start(){

        if(!started) {
            map = new HashMap<>();
            try(Scanner fileScan = new Scanner(storageFile)){
                while(fileScan.hasNext()){
                    try {
                        IPAddress address = new IPAddress(fileScan.next());
                        DomainName name = new DomainName(fileScan.next());
                        map.put(name,address);
                    } catch (IllegalArgumentException e) {
                        System.err.println(e.getMessage() + " The entry was ignored.");
                    }
                }
                started = true;
            } catch (FileNotFoundException e){
                System.err.println("The input dns file '" + storageFile + "' could not be found.");
            }
        }

        return started;
    }

    /**
     * Stops the system. Writes current entries to the storage file.
     * @return returns true if the system was stopped successfully or was already stopped
     */
    public boolean stop(){
        if(started){
            try(FileWriter writer = new FileWriter(storageFile)){
                for(Map.Entry entry: map.entrySet()){
                    DomainName domainName = (DomainName) entry.getKey();
                    IPAddress ipAddress = (IPAddress) entry.getValue();
                    writer.write(ipAddress.toString() + "  " + domainName.toString() + "\n");
                }
                started = false;
            } catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
        return !started;
    }

    /**
     * Searches the dns for a domain name
     * @param domainName domain name to be searched for
     * @return IP address value associated with the specified domain name. Returns null if the domain name isn't found.
     */
    public IPAddress lookup(DomainName domainName){
        IPAddress result = null;
        if(started && map.containsKey(domainName)){
            result = map.get(domainName);
        }
        return result;
    }

    /**
     * Updates the dns based on a string command
     * @param command action to be performed on the dns
     * @return The IP address value previously associated with the domain name that was either changed or removed
     */
    public IPAddress update(String command){
        IPAddress previousIP = null;
        String action;
        IPAddress address;
        DomainName name;
        if(started){
           try(Scanner commandScan = new Scanner(command)){
               action = commandScan.next().toUpperCase();
               address = new IPAddress(commandScan.next());
               name = new DomainName(commandScan.next());
               if(!action.equals("ADD") && !action.equals("DEL")){
                   throw new IllegalArgumentException("'" + action + "' is not a valid action.");
               }
           } catch (IllegalArgumentException e){
               throw new IllegalArgumentException("The command line '" + command + "' was not processed because the " +
                       "error \"" + e.getMessage() + "\" was thrown.");
           }

            switch (action){
                case "ADD":
                    previousIP = map.put(name,address);
                    break;
                case "DEL":
                    boolean removed = map.remove(name,address);
                    if(removed){
                        previousIP = address;
                    }else if(!map.get(name).equals(address)){
                        throw new InputMismatchException("There is no such Domain/IP address pair '" + name + ", " +
                                address + "' in the system. The command line '" + command + "' was skipped.");
                    }
                    break;
            }
        }
        return previousIP;
    }
}
/*
I chose the HashMap because implementing a hashCode() method for the DomainName class was simpler
than making it implement the comparable interface.
 */
