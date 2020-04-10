package ru.otus.l06;

import java.util.ArrayList;
import java.util.List;

public class App
{
    public static void main(String[] args) throws InterruptedException {
        List<TestClass> myList = new ArrayList<>();
        for(;;){
            myList.add(new TestClass());
            myList.add(new TestClass());
            myList.add(new TestClass());
            myList.remove(0);
            Thread.sleep(1);
        }

    }
}
