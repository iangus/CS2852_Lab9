package lab9.guswilerib;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * CS2852 - 041
 * Spring 2016
 * Lab
 * Name: Ian Guswiler
 * Created: 5/10/2016
 */
public class IPAddress {
    private String address;

    public IPAddress(String newAddress){
        int counter = 0;
        Scanner ipScanner = new Scanner(newAddress);
        ipScanner.useDelimiter(".");
        while(ipScanner.hasNext()){
            try{
                ipScanner.nextInt();
                counter++;
            } catch(InputMismatchException e){
                throw new IllegalArgumentException("The IP address " + newAddress + " is not valid.");
            }
        }
        if(counter != 4){
            throw new IllegalArgumentException("The IP address " + newAddress + " is not valid.");
        }
        address = newAddress;
    }

}
