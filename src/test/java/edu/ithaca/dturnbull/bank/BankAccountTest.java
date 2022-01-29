package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() throws InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        
        assertEquals(200, bankAccount.getBalance(), 0.001);
        assertNotEquals(100, bankAccount.getBalance());

        bankAccount.withdraw(100);
        assertEquals(100, bankAccount.getBalance());
        
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(5000));
        assertEquals(100, bankAccount.getBalance());

        BankAccount bankAccount2 = new BankAccount("a@c.com", -5);

        assertEquals(-5, bankAccount2.getBalance());
        assertThrows(InsufficientFundsException.class, () -> bankAccount2.withdraw(5));
        assertEquals(-5, bankAccount2.getBalance());
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));

        bankAccount.withdraw(100);
        assertEquals(0, bankAccount.getBalance());

        //throw IllegalArgumentException when a negative amount of money is entered
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-300));
        assertEquals(0, bankAccount.getBalance());
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address
        assertFalse( BankAccount.isEmailValid(""));         // empty string

        assertTrue(BankAccount.isEmailValid("acc@yahoo.com"));
        assertTrue(BankAccount.isEmailValid("z@ithaca.edu"));

        assertFalse(BankAccount.isEmailValid("@"));
        assertFalse(BankAccount.isEmailValid("."));
        assertFalse(BankAccount.isEmailValid("1.e"));

        //email prefixes cannot end with any special characters
        assertFalse(BankAccount.isEmailValid("1.@mail.cc"));
        assertFalse(BankAccount.isEmailValid("1-@mail.cc"));
        assertFalse(BankAccount.isEmailValid("1!@mail.cc"));
        assertFalse(BankAccount.isEmailValid("1#@mail.cc"));

        //emails cannot start with any special characters
        assertFalse(BankAccount.isEmailValid(".1@mail.cc"));
        assertFalse(BankAccount.isEmailValid("-1@mail.cc"));
        assertFalse(BankAccount.isEmailValid("!1@mail.cc"));
        assertFalse(BankAccount.isEmailValid("#1@mail.cc"));

        //checking domain last portion
        assertFalse(BankAccount.isEmailValid("1@mail.c"));
        assertTrue(BankAccount.isEmailValid("1@mail.cc"));
        assertTrue(BankAccount.isEmailValid("1@mail.ccc"));
        assertTrue(BankAccount.isEmailValid("1@mail.cccc"));
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));
    }

    @Test
    void isAmountValidTest() {
        negativeAmount();
        negativeAmountWithDecimals();
        positiveAmount();
        positiveAmountWithDecimals();
    }

    @Test
    void negativeAmount() {
        //negative amount
        assertFalse(BankAccount.isAmountValid(-1));
        assertFalse(BankAccount.isAmountValid(-1000000000));
    }

    @Test
    void negativeAmountWithDecimals() {
        //positive amount with decimals
        assertFalse(BankAccount.isAmountValid(-0.01));
        assertFalse(BankAccount.isAmountValid(-0.2)); //1 decimal place
        assertFalse(BankAccount.isAmountValid(-60.45)); //2 decimal places
        assertFalse(BankAccount.isAmountValid(-75.303)); //3 decimal places
        assertFalse(BankAccount.isAmountValid(-500.9046)); //4 decimal places
    }

    @Test
    void positiveAmount() {
        //positive amount with no decimals
        assertTrue(BankAccount.isAmountValid(0));
        assertTrue(BankAccount.isAmountValid(25000));
        assertTrue(BankAccount.isAmountValid(1000000));
        assertTrue(BankAccount.isAmountValid(7603213));
        assertTrue(BankAccount.isAmountValid(9999999));
    }

    @Test
    void positiveAmountWithDecimals() {
        //positive amount with decimals
        assertTrue(BankAccount.isAmountValid(0.2)); //1 decimal place
        assertTrue(BankAccount.isAmountValid(0.01)); //2 decimal places
        assertTrue(BankAccount.isAmountValid(60.45)); //2 decimal places
        assertFalse(BankAccount.isAmountValid(75.303)); //3 decimal places
        assertFalse(BankAccount.isAmountValid(5000.9046)); //4 decimal places
        assertTrue(BankAccount.isAmountValid(9999999.99));
    }
}