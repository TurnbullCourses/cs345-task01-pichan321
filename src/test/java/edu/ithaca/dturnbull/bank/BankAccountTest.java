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

        //BankAccount bankAccount2 = new BankAccount("a@c.com", -5);

        //assertEquals(-5, bankAccount2.getBalance());
        //assertThrows(InsufficientFundsException.class, () -> bankAccount2.withdraw(5));
        //assertEquals(-5, bankAccount2.getBalance());
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
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-1));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(-100));

        //throws for decimal places more than 2
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(5.225));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.withdraw(99999999.7575));
    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address
        assertFalse( BankAccount.isEmailValid(""));         // empty email string

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

        //check for too many @ symbols in the email
        assertFalse(BankAccount.isEmailValid("a@b@.com"));
        assertFalse(BankAccount.isEmailValid("a@@ithaca.edu"));
        assertFalse(BankAccount.isEmailValid("abc@@xyz.com"));
        assertFalse(BankAccount.isEmailValid("a@b@c@@xy@z.com"));

        //checking domain first portion
        assertFalse(BankAccount.isEmailValid("abc.def@mail#archive.com"));
        assertFalse(BankAccount.isEmailValid("abc.def@mail..com"));
        assertFalse(BankAccount.isEmailValid("abc.def@mail"));
       
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));

        //throws for negative amount
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("A@B.com", -1));
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("A@B.com", -100));

        //throws for decimal places more than 2
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("A@B.edu", 5.225));
        assertThrows(IllegalArgumentException.class, () -> new BankAccount("A@B.org", 99999999.7575));
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

    @Test
    void depositTest() {
        BankAccount bankAccount = new BankAccount("a@c.com", 0);

        //throws for negative amount deposits
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-5));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-1000));

        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-0.1));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-75.9)); //negative amount with 1 decimal places
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-90.35)); //negative amount with 2 decimal places
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-90.355)); //negative amount with 3 decimal places
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(-999999999.3555)); //negative amount with 4 decimal places

        //asserts for positive amount with more than two decimal places
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(0.001));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(0.0055));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(0.00555));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.deposit(500.0000000000001));

        //test for positive amount deposit
        assertEquals(0, bankAccount.getBalance());
        
        bankAccount.deposit(0);
        assertEquals(0, bankAccount.getBalance());

        bankAccount.deposit(1);
        assertEquals(1, bankAccount.getBalance());

        bankAccount.deposit(500);
        assertEquals(501, bankAccount.getBalance());

        bankAccount.deposit(0.01);
        assertEquals(501.01, bankAccount.getBalance());

        bankAccount.deposit(0.1);
        assertEquals(501.11, bankAccount.getBalance());
    }

    @Test
    void transferTest() throws IllegalAccessException, InsufficientFundsException {
        transferWithEmail();
        transferWithBankAccount();
    }

    @Test
    void transferWithEmail() throws IllegalAccessException, InsufficientFundsException {
        BankAccount bankAccount = new BankAccount("a@c.com", 50);
        //throws for negative amount entered
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(".asdasd@edu", -0.01));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(".asdasd@edu", -99999999));

        //asserts for amount with more than two decimal places
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer("pchan@ithaca.edu", 0.001));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer("pchan@ithaca.edu", 0.0055));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer("pchan@ithaca.edu", 0.00555));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer("pchan@ithaca.edu", 500.0000000000001));

        //asserts to for email checks
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer(".asdasd@edu", 50));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer("@ithaca.edu", 50));
        assertThrows(IllegalArgumentException.class, () -> bankAccount.transfer("karen-@edu", 50));

        //throws when the amount to be transferred is more than the account's remaining balance
        BankAccount bankAccount2 = new BankAccount("austin@ithaca.edu", 0);
        assertThrows(InsufficientFundsException.class, () -> bankAccount2.transfer("pchan@ithaca.edu", 0.01));
        assertThrows(InsufficientFundsException.class, () -> bankAccount2.transfer("pchan@ithaca.edu", 500000));
        assertThrows(InsufficientFundsException.class, () -> bankAccount2.transfer("pchan@ithaca.edu", 9999999));

        //tests for successful transfers
        BankAccount bankAccount3 = new BankAccount("emily@ithaca.edu", 100000);
        assertEquals(100000, bankAccount3.getBalance());

        bankAccount3.transfer("christina@ithaca.edu", 15000);
        assertEquals(85000, bankAccount3.getBalance());

        bankAccount3.transfer("christina@ithaca.edu", 5000);
        assertEquals(80000, bankAccount3.getBalance());

        bankAccount3.transfer("christina@ithaca.edu", 0.01);
        assertEquals(79999.99, bankAccount3.getBalance());

        bankAccount3.transfer("christina@ithaca.edu", 0.1);
        assertEquals(79999.89, bankAccount3.getBalance());

        bankAccount3.transfer("christina@ithaca.edu", 79999.89);
        assertEquals(0, bankAccount3.getBalance());
    }

    @Test
    void transferWithBankAccount() throws IllegalAccessException, InsufficientFundsException {

        BankAccount vattana = new BankAccount("pchan@ithaca.edu", 50000);
        BankAccount doug = new BankAccount("dturnbull@ithaca.edu", 75000);

        //successful transfer transaction test
        assertEquals(50000, vattana.getBalance());
        assertEquals(75000, doug.getBalance());

        vattana.transfer(doug, 25000);

        assertEquals(25000, vattana.getBalance());
        assertEquals(100000, doug.getBalance());

        //failing transfer transaction test
        assertThrows(InsufficientFundsException.class,() -> doug.transfer(vattana, 100000.01));

        //invalid amount test (negative)
        assertThrows(IllegalArgumentException.class,() -> doug.transfer(vattana, -0.1));
        assertThrows(IllegalArgumentException.class, () -> doug.transfer(vattana, -999999999));

        //invalid amount test (decimal places)
        assertThrows(IllegalArgumentException.class,() -> doug.transfer(vattana, 1.001));
        assertThrows(IllegalArgumentException.class,() -> doug.transfer(vattana, 1.0001));
        assertThrows(IllegalArgumentException.class,() -> doug.transfer(vattana, 1.00001));

    }
}