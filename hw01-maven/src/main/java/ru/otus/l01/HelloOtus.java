package ru.otus.l01;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.Arrays;

public class HelloOtus {
  public Multiset<String> wordCounter;
  public HelloOtus(String wordsToCount){
    wordCounter = HashMultiset.create();

    String[] words = wordsToCount.split(" ");
    wordCounter.addAll(Arrays.asList(words));

    System.out.println("Here we count different words, divided by spaces, in tue following string:");
    System.out.println(wordsToCount);
    System.out.println("Result:");
    System.out.println(wordCounter);

  }
}
