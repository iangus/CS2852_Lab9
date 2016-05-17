package lab9.guswilerib;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * CS2852 - 041
 * Spring 2016
 * Lab
 * Name: Ian Guswiler
 * Created: 5/10/2016
 */
public class DNS{
    private File storageFile;
    private Map<DomainName,IPAddress> map;
    private boolean started = false;

    public DNS(String filename){
        storageFile = new File(filename);
    }

    public boolean start(){

        if(!started) {
            map = new HashMap<>();
            try(Scanner fileScan = new Scanner(new File("dnsentries.txt"))){
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
                System.err.println("The input dns file could not be found.");
            }
        }

        return started;
    }

    public boolean stop(){
        if(started){
            try(FileWriter writer = new FileWriter(storageFile)){
                for(Map.Entry entry: map.entrySet()){
                    DomainName domainName = (DomainName) entry.getKey();
                    IPAddress ipAddress = (IPAddress) entry.getValue();
                    writer.write(ipAddress.toString() + "  " + domainName.toString());
                }
                started = false;
            } catch (IOException e){
                System.err.println(e.getMessage());
            }
        }
        return !started;
    }

    public IPAddress lookup(DomainName domainName){
        IPAddress result = null;
        if(started && map.containsKey(domainName)){
            result = map.get(domainName);
        }
        return result;
    }

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
               if(!action.equals("ADD") || !action.equals("DEL")){
                   throw new IllegalArgumentException("The command line '" + command + "' was not processed because '" +
                           action + "' is not a valid action.");
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
                        throw new InputMismatchException("THIS NEEDS TO BE CHANGED");
                    }
            }
        }
        return previousIP;
    }
}
