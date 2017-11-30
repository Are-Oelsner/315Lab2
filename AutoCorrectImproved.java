import java.util.*;
import java.io.File;
import java.util.HashMap;
import java.io.FileNotFoundException;

public class AutoCorrectImproved{

    public static final int SHORTER_LEXICON_SIZE = 73;
    public static final int DICTIONARY_SIZE = 121806;
    private static HashMap<Character, Integer[]> cMap = new HashMap<Character, Integer[]>();
    private static String[] m_dict = new String[DICTIONARY_SIZE];
    ////////////////////////////////////////////////////////////////////////////
    //* @brief: Constructor
    //* @param filename: name of file passed in
    public AutoCorrectImproved(String filename){
        File file = new File(filename);

        try {
            Scanner sc = new Scanner(file);
            for(int i = 0; i < m_dict.length; i++){
                m_dict[i] = sc.next();
            }
            sc.close();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        initializeCMap();
    }

    ////////////////////////////////////////////////////////////////////////////
    //* @brief: Initialize Character Map. Maps each character
    //* with its coordinates based on its keyboard position.
    public static void initializeCMap(){
        cMap.put('q', new Integer[]{0,2});
        cMap.put('w', new Integer[]{1,2});
        cMap.put('e', new Integer[]{2,2});
        cMap.put('r', new Integer[]{3,2});
        cMap.put('t', new Integer[]{4,2});
        cMap.put('y', new Integer[]{5,2});
        cMap.put('u', new Integer[]{6,2});
        cMap.put('i', new Integer[]{7,2});
        cMap.put('o', new Integer[]{8,2});
        cMap.put('p', new Integer[]{9,2});
        cMap.put('a', new Integer[]{1/3,1});
        cMap.put('s', new Integer[]{4/3,1});
        cMap.put('d', new Integer[]{7/3,1});
        cMap.put('f', new Integer[]{10/3,1});
        cMap.put('g', new Integer[]{13/3,1});
        cMap.put('h', new Integer[]{16/3,1});
        cMap.put('j', new Integer[]{19/3,1});
        cMap.put('k', new Integer[]{22/3,1});
        cMap.put('l', new Integer[]{25/3,1});
        cMap.put('z', new Integer[]{2/3,0});
        cMap.put('x', new Integer[]{5/3,0});
        cMap.put('c', new Integer[]{8/3,0});
        cMap.put('v', new Integer[]{11/3,0});
        cMap.put('b', new Integer[]{14/3,0});
        cMap.put('n', new Integer[]{17/3,0});
        cMap.put('m', new Integer[]{20/3,0});
    }

    ////////////////////////////////////////////////////////////////////////////
    //* @brief: Find edit distance between two given strings
    //* @param key1: first given String
    //* @param key2: second given String
    //* @return a double representing distance between word1 and word2
    public static double editDistance(String word1, String word2){
        double[][] E = new double[word1.length()+1][word2.length()+1];
        for(int i = 0; i <= word1.length(); i++){
            E[i][0] = i*7;
        }
        for(int j = 1; j <= word2.length(); j++){
            E[0][j] = j*7;
        }
        for(int i = 1; i <= word1.length(); i++){
            for(int j = 1; j <= word2.length(); j++){
                char c1 = word1.charAt(i-1);
                char c2 = word2.charAt(j-1);
                double diff = computeDistance(c1,c2);
                E[i][j] = Math.min(E[i-1][j] + 6+(word1.length-i), E[i][j-1] + 7);
                E[i][j] = Math.min(E[i][j], E[i-1][j-1] + diff + (word1.length-i));
                if(c1 == c2)
                  E[i][j] = E[i-1][j-1];
            }
        }
        return E[word1.length()][word2.length()];
    }


    ////////////////////////////////////////////////////////////////////////////
    //* @brief: Find Euclidean distance between two given characters
    //* @param key1: first given character
    //* @param key2: second given character
    //* @return a double representing distance between key1 and key2
    public static double computeDistance(char key1, char key2){
        if(key1 == key2) return 0;
        double xSquared = (cMap.get(key1)[0] - cMap.get(key2)[0])*(cMap.get(key1)[0] - cMap.get(key2)[0]);
        double ySquared = (cMap.get(key1)[1] - cMap.get(key2)[1])*(cMap.get(key1)[1] - cMap.get(key2)[1]);
        return Math.sqrt(xSquared + ySquared);
    }

    ////////////////////////////////////////////////////////////////////////////
    //* @brief: Find closest word (with shortest edit distance)
    //* @param str: given word
    //* @return a string that is the closest word to the given word
    public static String findClosest(String str){
        int idMin = 0;
        double min = Double.POSITIVE_INFINITY;
        for(int i = 0; i < m_dict.length; i++){
            double dist = editDistance(m_dict[i].toLowerCase(),str.toLowerCase());
            if(min >= dist){
                min = dist;
                idMin = i;
            }
        }
        System.out.println("Distance of: " + str + " and " + m_dict[idMin] + " is " + min);
        return m_dict[idMin];
    }
}
