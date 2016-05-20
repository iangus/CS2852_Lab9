/*
 * CS2852 - 041
 * Spring 2016
 * Lab 9
 * Name: Ian Guswiler
 * Created: 5/10/2016
 */

package lab9.guswilerib;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Represents an IPv4 address with an int for each separate segment
 */
public class IPAddress {
    private int segment1;
    private int segment2;
    private int segment3;
    private int segment4;

    /**
     * Constructs a new IPAddress from a string value
     * @param newAddress string representation of the IPAddress to be created
     */
    public IPAddress(String newAddress){
        int counter = 0;
        Scanner ipScanner = new Scanner(newAddress);
        ipScanner.useDelimiter("\\.");
        while(ipScanner.hasNext()){
            try{
                int segment = ipScanner.nextInt();
                counter++;
                if(segment > 255 || segment < 0){
                    throw new IllegalArgumentException("The IP address '" + newAddress + "' is not valid.");
                }
            } catch(InputMismatchException e){
                throw new IllegalArgumentException("The IP address '" + newAddress + "' is not valid.");
            }
        }
        if(counter != 4){
            throw new IllegalArgumentException("The IP address '" + newAddress + "' is not valid.");
        }
        ipScanner = new Scanner(newAddress);
        ipScanner.useDelimiter("\\.");
        segment1 = ipScanner.nextInt();
        segment2 = ipScanner.nextInt();
        segment3 = ipScanner.nextInt();
        segment4 = ipScanner.nextInt();
    }

    /**
     * Concatenates the individual address segments into the ip address pattern
     * @return String representation of the IPAddress.
     */
    public String toString(){
        return "" + segment1 + "." + segment2 + "." + segment3 + "." + segment4;
    }

    public boolean equals(Object address){
        boolean equals = false;
        try{
            IPAddress toCompare = (IPAddress) address;
            if(toCompare.segment1 == segment1 && toCompare.segment2 == segment2 && toCompare.segment3 == segment3 &&
                    toCompare.segment4 == segment4){
                equals = true;
            }
        } catch (ClassCastException e){
            System.err.println(e.getMessage());
        }
        return equals;
    }
}
