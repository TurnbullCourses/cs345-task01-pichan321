package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (!isAmountValid(startingBalance)) {
            throw new IllegalArgumentException("The amount entered should be postive or have 2 decimal places or less.");
        }

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
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("The amount entered should be postive or have 2 decimal places or less.");
        }

        if (amount < 0){
            throw new IllegalArgumentException();
        }
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email){
        Boolean valid = true;

        if (email == "") {
            return false;
        }

        if (email.contains("@")){
            if (email.startsWith("@")){
                return false;
            }

            String prefix = email.split("@")[0].toString();

            if (prefix.endsWith("-") || prefix.startsWith("-")) {
                return false;
            }
            if (prefix.endsWith("!") || prefix.startsWith("!")) {
                return false;
            }
            if (prefix.endsWith(".") || prefix.startsWith(".")) {
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
            if (!domain.contains(".")){
                return false;  
            }
          
            
            String domainFirstPortion = domain.split("\\.")[0].toString();
            String domainLastPortion = domain.split("\\.")[1].toString();
            //check domain first portion
            if (domainFirstPortion == "") {
                return false;
            }

            //check domain last portion
            if (domainLastPortion.length() < 2){
                return false;
            }
        } else {
            return false;
        }
        return valid;
    }

    /***
     * @return false, if the amount is negative
     * @return false, if the amount has more than two decimal places
     */
    public static Boolean isAmountValid(double amount) {
        if (amount < 0) {
            return false;
        }

        String amountString = Double.toString(amount);
        String decimalPlaces = amountString.split("\\.")[1].toString();
        
        if (decimalPlaces.length() > 2) {
            return false;
        }

        return true;
    }

    /***
     * @param amount to be deposited to the account
     * @throws IllegalArgumentException if the amount entered is invalid (negative or have more than two decimal places)
     * @post the amount is deposited into the account
     */
    public void deposit(double amount) {
        if (!isAmountValid(amount)) {
            throw new IllegalArgumentException("The amount entered should be postive or have 2 decimal places or less.");
        } else {
            balance += amount;
        }
    }

    /***
     * @param recipientEmail of the account you are transferring to
     * @param amount of the money to be transferred
     * @throws IllegalArgumentException if either the email or amount entered is invalid
     * @throws InsufficientFundsException if the amount entered is more than the account's remaining balance
     * @post the amount is deducted from your account and transferred to the account associated with the email entered
     */
    public void transfer(String recipientEmail, double amount) throws IllegalAccessException, InsufficientFundsException {
        if (!isEmailValid(recipientEmail)) {
            throw new IllegalArgumentException("The recipient's email you entered is invalid.");
        }

        withdraw(amount);
    }

    /***
     * @param recipientAccount you are transferring to
     * @throws IllegalArgumentException if the amount to be transferred entered is invalid
     * @throws InsufficientFundsException if the amount to be transferred is more than the account's remaining balance
     * @post the amount is deducted from your account and transferred to the account associated with the email entered
     */
    public void transfer(BankAccount recipientAccount, double amount) throws IllegalAccessException, InsufficientFundsException {
        withdraw(amount);
        recipientAccount.deposit(amount);
    }
}
