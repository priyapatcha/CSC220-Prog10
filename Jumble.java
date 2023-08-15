package prog10;

import prog02.GUI;
import prog02.UserInterface;
import prog09.BTree;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Jumble {
  /**
   * Sort the letters in a word.
   * @param word Input word to be sorted, like "computer".
   * @return Sorted version of word, like "cemoptru".
   */
  public static String sort (String word) {
    char[] sorted = new char[word.length()];
    for (int n = 0; n < word.length(); n++) {
      char c = word.charAt(n);
      int i = n;

      while (i > 0 && c < sorted[i-1]) {
        sorted[i] = sorted[i-1];
        i--;
      }

      sorted[i] = c;
    }

    return new String(sorted, 0, word.length());
  }

  public static void main (String[] args) {
    UserInterface ui = new GUI("Jumble");
    // UserInterface ui = new ConsoleUI();

    //Map<String,String> map = new TreeMap<String,String>();
    //Map<String,String> map = new PDMap();
    //Map<String,String> map = new LinkedMap<String,String>();
    //Map<String,String> map = new SkipMap<String,String>();
    Map<String,List<String>> map = new BTree<String,List<String>>();
    //Map<String,String> map = new HashTable<String,String>();

    Scanner in = null;
    do {
      try {
        in = new Scanner(new File(ui.getInfo("Enter word file.")));
      } catch (Exception e) {
        System.out.println(e);
        System.out.println("Try again.");
      }
    } while (in == null);
	    
    int n = 0;
    while (in.hasNextLine()) {
      String word = in.nextLine();
      if (n++ % 1000 == 0)
	      System.out.println(word + " sorted is " + sort(word));
      
      // EXERCISE: Insert an entry for word into map.
      // What is the key?  What is the value?

      //map.put(sort(word), word);

      if (!map.containsKey(sort(word))) {
        List<String> list = new ArrayList<>();
        list.add(word);
        map.put(sort(word), list);
      }
      else if (map.containsKey(sort(word))) {
        List<String> list = map.get(sort(word));
        list.add(word);
        map.put(sort(word), list);
      }

    }

    //while (true) {
    String jumble = ui.getInfo("Enter jumble.");
    while (jumble != null) {
      List<String> words = map.get(sort(jumble));
      String str;

      if (words == null)
        ui.sendMessage("No match for " + jumble);
      else {
        str = jumble + " unjumbled is " + words.get(0);

        for (int i = 1; i < words.size(); i++) {
          str += ", " + words.get(i);
        }
        ui.sendMessage(str);
      }
      jumble = ui.getInfo("Enter jumble");


      // EXERCISE:  Look up the jumble in the map.
      // What key do you use?

    }
    int numLetters = 0;
    String sortedLetters = "";
    String stringKey2 = "";

    do {
      try {
        sortedLetters = sort(ui.getInfo("Enter letters from clues"));
        numLetters = Integer.parseInt(ui.getInfo("How many letter are in the first pun word?"));
      }
      catch (Exception e) {
        System.out.println(e);
        System.out.println("Try again");
      }
    }
    while (in == null);

    for (String key1 : map.keySet()) {
      if (key1.length() == numLetters) {
        StringBuilder key2 = new StringBuilder(sortedLetters.length());
        int key1Index = 0;

        for (int i = 0; i < sortedLetters.length(); i++) {
          if (key1Index < numLetters && sortedLetters.charAt(i) == key1.charAt(key1Index)) {
            key1Index++;
          }
          else {
            key2.append(sortedLetters.charAt(i));
          }
        }
        if (key1Index == key1.length()) {
          stringKey2 = key2.toString();
          if (map.containsKey(stringKey2)) {
            System.out.println(map.get(key1) + " " + map.get(stringKey2));
          }
        }
      }
    }
  }
}

        
    

