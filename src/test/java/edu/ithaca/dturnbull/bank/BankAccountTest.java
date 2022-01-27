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
}