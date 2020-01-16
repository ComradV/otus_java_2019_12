package ru.otus.l01;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        HelloOtus wordCounter = new HelloOtus("USD USD USD JPY AUD SGD HKD CAD CHF GBP EURO INR");
        wordCounter.countWords();

    }
}
