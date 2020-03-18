package ru.otus.l03;

import java.util.ArrayList;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] args)
    {
        List<Integer> myList = new DIYArrayList<>();
        myList.add(10);
        myList.add(11);
        myList.add(20);
        myList.add(25);
        myList.add(-4);
        System.out.println(myList);
        myList.remove(1);
        System.out.println(myList);
        myList.remove(Integer.valueOf(25));
        System.out.println(myList);


        List<Integer> testList = new ArrayList<>();

    }
}
