package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
        Boolean valid = true;
        if (email.contains("@")){
            if (email.startsWith("@")){
                return false;
            }
            String prefix = email.split("@")[0].toString();
            if (prefix.endsWith("-")||prefix.startsWith("-")) {
                return false;
            }
            if (prefix.endsWith("!")||prefix.startsWith("!")) {
                return false;
            }
            if (prefix.endsWith(".")||prefix.startsWith(".")) {
                return false;
            }
            if (prefix.contains("#")) {
                return false;
            }
        
            if (email.indexOf('@') == -1){
                return false;
            } 

            //check domain
            String domain = email.split("@")[1].toString();
            if (domain.contains("#")) {
                return false;
            }
            if (domain.contains(".")){
                if (domain.length()-domain.indexOf(".") <= 2){
                    return false;
                }
            }else{
                return false;
            }
            return valid;
        }else{
            return false;
        }
    }
}
