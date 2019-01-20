package com.example.demo.test.map;

import com.google.common.collect.*;
import org.assertj.core.util.Lists;

import java.util.Collection;
import java.util.List;

public class MultimapsTest {


    private static void baseTest(){

        Multimap<String, String> myMultimap = ArrayListMultimap.create();

        myMultimap.put("Fruits", "Bannana");
        myMultimap.put("Fruits", "Apple");
        myMultimap.put("Fruits", "Pear");
        myMultimap.put("Fruits", "Pear");
        myMultimap.put("Vegetables", "Carrot");

        // Getting the size
        int size = myMultimap.size();
        System.out.println(size); // 5

        // Getting values
        Collection<String> fruits = myMultimap.get("Fruits");
        System.out.println(fruits); //  [Bannana, Apple, Pear, Pear]
        System.out.println(ImmutableSet.copyOf(fruits));// [Bannana, Apple, Pear]

        Collection<String> vegetables = myMultimap.get("Vegetables");
        System.out.println(vegetables); // [Carrot]

        // Iterating over entire Mutlimap
        myMultimap.values().forEach(s -> System.out.println(s));

        // Removing a single value
        myMultimap.remove("Fruits", "Pear");
        System.out.println(myMultimap.get("Fruits")); // [Bannana, Apple, Pear]

        // Remove all values for a key
        myMultimap.removeAll("Fruits");
        System.out.println(myMultimap.get("Fruits")); // [] (Empty Collection!)
    }


    public static void  indexTest(){

        System.out.println("---------------index test ---------------------");

        List<String> list = Lists.newArrayList("Bannana", "Apple", "Pear", "Pear", "Carrot");

        ImmutableListMultimap<String, String> index = Multimaps.index(list, String::toUpperCase);

        index.entries().parallelStream()
                .forEach(stringStringEntry -> System.out.println("key:"+ stringStringEntry.getKey()+" value:"+stringStringEntry.getValue()));
    }

    public static void main(String[] args) {
        baseTest();
        indexTest();
    }


}
