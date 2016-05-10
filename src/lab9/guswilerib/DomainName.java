package lab9.guswilerib;

/**
 * CS2852 - 041
 * Spring 2016
 * Lab
 * Name: Ian Guswiler
 * Created: 5/10/2016
 */
public class DomainName {
    private String name;

    public DomainName(String domainName){
        if(!Character.isLetterOrDigit(domainName.charAt(0))){
            throw new IllegalArgumentException("The domain name " + domainName + " is invalid. Invalid start character: "
                    + domainName.charAt(0));
        }
        for(int i = 0; i<domainName.length();i++){
            char character = domainName.charAt(i);
            if(!Character.isLetterOrDigit(character) || character != '.' || character != '-'){
                throw new IllegalArgumentException("The domain name " + domainName + " is invalid. Invalid character: "
                        + character);
            } else if(character == '.' && (domainName.charAt(i - 1) == '.' || domainName.charAt(i + 1) == '.')){
                throw  new IllegalArgumentException("The domain name " + domainName + " is invalid. Consecutive '.' characters");
            }
        }
        name = domainName.toLowerCase();
    }

    public String toString(){
        return name;
    }

    public boolean equals(Object o){
        return name.equals(o.toString().toLowerCase());
    }
}
