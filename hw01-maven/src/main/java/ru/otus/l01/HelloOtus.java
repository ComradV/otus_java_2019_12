package ru.otus.l01;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;

import java.util.Arrays;

final class HelloOtus {
  String wordsToCount;
  public Multiset<String> wordCounter;
  public HelloOtus(String wordsToCount){
    this.wordsToCount = wordsToCount;
  }

  public void countWords() {
    wordCounter = HashMultiset.create();

    String[] words = wordsToCount.split(" ");
    wordCounter.addAll(Arrays.asList(words));

    System.out.println("Here we count different words, divided by spaces, in tue following string:");
    System.out.println(wordsToCount);
    System.out.println("Result:");
    System.out.println(wordCounter);
  }
}
