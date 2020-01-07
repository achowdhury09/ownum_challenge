package com.company;
import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        //Initialize all variables needed for tracking
        String path = System.getenv("PATH");
        String[] words = null;
        ArrayList<String> listOfSentences = new ArrayList<>();
        int sentenceIndex = -1;
        int wordCount = 0;
        HashMap<String, Integer> wordCountMap = new HashMap<>();
        HashMap<String, Integer> lastSentenceMap = new HashMap<>();
        //Parse file into something usable
        File file = new File(path);
        Scanner sc = new Scanner(file);
        //Separate .txt file by sentence
        sc.useDelimiter("[.?!]");
        while (sc.hasNext()) {
            //Add sentence to sentenceList and track the last sentence each word appears in.
            String currSentence= sc.next();
            sentenceIndex += 1;
            listOfSentences.add(currSentence);
            //Split up the sentence by word
            words= currSentence.split(" ");
            //Convert each word to lowerCase and add to dictionary if not there and increment counter or increment counter if there.
            for (String word: words) {
                String currWord = word.replaceAll("[^a-zA-Z0-9_-]", "");
                currWord = currWord.toLowerCase();
                //Check to make sure empty strings don't get added to dictionary.
                if (currWord.equals("")) {
                    continue;
                } else {
                    if (!wordCountMap.containsKey(currWord)) {
                        wordCountMap.put(currWord, 1);
                        lastSentenceMap.put(currWord, sentenceIndex);
                        wordCount += 1;
                    } else {
                        int count = wordCountMap.get(currWord);
                        wordCountMap.put(currWord, count + 1);
                        lastSentenceMap.put(currWord, sentenceIndex);
                        wordCount += 1;
                    }
                }
            }
        }
        //Sort the wordCountMap
        wordCountMap = sortByValue(wordCountMap);
        //Only get the top 10 values of the wordCountMap and get mostUsedWord
        Set<Map.Entry<String, Integer>> entrySet = wordCountMap.entrySet();
        ArrayList<Map.Entry<String, Integer>> listOfEntries = new ArrayList<>(entrySet);
        int numUniqueWords = listOfEntries.size();
        String mostUsedWord = listOfEntries.get(numUniqueWords - 1).getKey();
        List<Map.Entry<String, Integer>> subListOfEntries = listOfEntries.subList(numUniqueWords - 10, numUniqueWords);
        //Retrieve last sentence mostUsedWord appeared in
        String lastAppearance = listOfSentences.get(lastSentenceMap.get(mostUsedWord)) + ".";
        //Output all outputs to console
        System.out.println("Top 10 Most Used words: " + subListOfEntries.toString());
        System.out.println("Last appearance of most used word: " + lastAppearance);
        System.out.println("Number of words: " + wordCount);
    }

    //Comparator for comparing hashMap values
    private static HashMap<String, Integer> sortByValue(HashMap<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list = new LinkedList<>(hm.entrySet());

        // Sort the list
        Collections.sort(list, Comparator.comparing(Map.Entry::getValue));

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
