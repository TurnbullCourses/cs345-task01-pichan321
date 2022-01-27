package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);
    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300));
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

        assertFalse(BankAccount.isEmailValid("1.@mail.cc"));
        assertFalse(BankAccount.isEmailValid("1-@mail.cc"));
        assertFalse(BankAccount.isEmailValid("1!@mail.cc"));
        assertFalse(BankAccount.isEmailValid("1#@mail.cc"));

        assertFalse(BankAccount.isEmailValid(".1@mail.cc"));
        assertFalse(BankAccount.isEmailValid("-1@mail.cc"));
        assertFalse(BankAccount.isEmailValid("!1@mail.cc"));
        assertFalse(BankAccount.isEmailValid("#1@mail.cc"));

        assertFalse(BankAccount.isEmailValid("1@mail.c"));
        assertTrue(BankAccount.isEmailValid("1@mail.cc"));
        assertTrue(BankAccount.isEmailValid("1@mail.ccc"));
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