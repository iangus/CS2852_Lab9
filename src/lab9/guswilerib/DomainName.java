/*
 * CS2852 - 041
 * Spring 2016
 * Lab 9
 * Name: Ian Guswiler
 * Created: 5/10/2016
 */

package lab9.guswilerib;

/**
 * Represents a legal domain name
 */
public class DomainName {
    private String name;

    /**
     * Constructs a new DomainName with a specified string
     * @param domainName String representation of the DomainName to be created
     */
    public DomainName(String domainName){
        if(!Character.isLetterOrDigit(domainName.charAt(0))){
            throw new IllegalArgumentException("The domain name " + domainName + " is invalid. Invalid start character: "
                    + domainName.charAt(0));
        }
        for(int i = 0; i<domainName.length();i++){
            char character = domainName.charAt(i);
            if(!Character.isLetterOrDigit(character) && character != '.' && character != '-'){
                throw new IllegalArgumentException("The domain name " + domainName + " is invalid. Invalid character: "
                        + character);
            } else if(character == '.' && (domainName.charAt(i - 1) == '.' || domainName.charAt(i + 1) == '.')){
                throw  new IllegalArgumentException("The domain name " + domainName + " is invalid. Consecutive '.' characters");
            }
        }
        name = domainName.toLowerCase();
    }

    /**
     * Returns the DomainName as a string
     * @return DomainName as a string
     */
    public String toString(){
        return name;
    }

    /**
     * Checks if the DomainName is equal to the specified Object
     * @param o object to be equated to the DomainName
     * @return returns true if the object cast as a DomainName has a name value equal to that of the original DomainName
     */
    public boolean equals(Object o){
        boolean result = false;
        try{
            DomainName object = (DomainName) o;
            result = name.equals(object.name);
        } catch (ClassCastException e){
            //catching to escape
        }
        return result;
    }

    public int hashCode(){
        return name.hashCode();
    }
}
