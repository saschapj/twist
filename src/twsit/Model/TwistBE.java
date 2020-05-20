package twsit.Model;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;


/**
 * @author jungs
 */
public class TwistBE {

    private File wordListFile = null;
    private ArrayList<String> listOfWords = null;


    public ArrayList<String> getListOfWords() {
        return listOfWords;
    }

    /**
     * twist a single word
     *
     * @param string word to be twisted
     * @return twisted word
     */
    private String twistWord(String string) {
        if (string.length() < 4) {
            return string;
        }
        char[] toCharArray = string.toCharArray();

        int start = excludeSpecialChars(false, toCharArray)[0];
        int end = excludeSpecialChars(false, toCharArray)[1];

        for (int i = start; i < end; i++) {

            int swapSource = ThreadLocalRandom.current().nextInt(start, end);
            int swapDrain = ThreadLocalRandom.current().nextInt(start, end);
            char temp;

            temp = toCharArray[swapSource];
            toCharArray[swapSource] = toCharArray[swapDrain];
            toCharArray[swapDrain] = temp;
        }
        String result = new String(toCharArray);
        return result;
    }


    /**
     * Read in a wordlist and return it ordered alphabetically
     *
     * @param file wordlist to compare text against
     * @return alphabetically ordered wordlist
     */
    public void readInAndOrderDictionary(File file) {
        listOfWords = new ArrayList<String>();
        String line = "";
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                listOfWords.add(line);

            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        listOfWords.sort(Comparator.naturalOrder());

    }


    public int[] excludeSpecialChars(boolean considerFirstChar, char[] charArray) {
        int start = 0;
        if (!considerFirstChar) {
            start++;
        }
        int[] indices = {start, charArray.length - 1};

        while (!(charArray[indices[0]] >= 65 && charArray[indices[0]] <= 90 || charArray[indices[0]] >= 97 && charArray[indices[0]] <= 122)) {
            indices[0]++;
        }

        while (!(charArray[indices[1]] >= 65 && charArray[indices[1]] <= 90 || charArray[indices[1]] >= 97 && charArray[indices[1]] <= 122)) {
            indices[1]--;
        }
        return indices;
    }


    /**
     * @param word word to be detwisted
     * @return detwisted word
     */
    public String detwistWord(String word) {
        String endSpecial = "";
        String startSpecial = "";
        if (word.length() < 4) { //no need to do anything to the word if its less than 4 chars long
            return word;
        }
        List<String> wordlist = getListOfWords();
        List<String> newWordList = new ArrayList<>(); //placeholder for possible correct words

        char[] toCharArray = word.toCharArray();

        int start = excludeSpecialChars(true, toCharArray)[0];
        int end = excludeSpecialChars(true, toCharArray)[1];


        if (end < word.length() - 1) {
            endSpecial = word.substring(end + 1);
        }
        startSpecial = word.substring(0, start);
        word = word.substring(start, end + 1);

        for (String wordInList : wordlist) {
            if (word.charAt(0) == wordInList.charAt(0)) //if the word in the original wordlist has the same character in the first and last place and the same amount of chars add it to possible solutions
            {

                if (word.charAt(word.length() - 1) == wordInList.charAt(wordInList.length() - 1)) {

                    if (wordInList.length() == word.length()) {

                        if (containsAllChars(word, wordInList)) {
                            newWordList.add(wordInList);
                        }
                        for (String s : newWordList) {
                            System.out.println(s);
                        }
                    }
                }

            }
        }

        if (start > 0) {
            end = end - start;
            start = 0;
        }
        for (String wordnew : newWordList) { // check middle part of word for same characters if yes return as correct word otherwise do nothing
            String substringWord = word.substring(start + 1, end);
            String substringWordNew = wordnew.substring(start + 1, end);

            if (containsAllChars(substringWord,substringWordNew)) {
                return startSpecial + wordnew + endSpecial;
            }

        }
        return startSpecial + word + endSpecial;
    }


    public String twistText(String text) {
        String result = "";
        String[] words = text.split(" ");

        for (int i = 0; i < words.length; i++) {
            if (words[i].length() > 3 && words[i].substring(1, words[i].length() - 1).contains("-")) {
                String[] split = words[i].split("-");

                String dashResult = "";

                for (int j = 0; j < split.length; j++) {
                    dashResult += twistWord(split[j]);

                    if (j < split.length - 1) { //put a dash behind every splitted word part
                        dashResult += "-";
                    }
                }
                words[i] = dashResult;
            } else {
                words[i] = twistWord(words[i]);
            }
        }

        StringBuilder builder = new StringBuilder();
        for (String word : words) {
            builder.append(word);
            builder.append(" ");

        }
        result = builder.toString();
        result = result.trim();
        result += "\n";
        return result;
    }


    public String detwistText(String text) {

        String result = "";

        String[] words = text.split("\\s");

        for (int i = 0; i < words.length; i++) {
            if (words[i].length() > 3 && words[i].substring(1, words[i].length() - 1).contains("-")) {
                String resultDash = "";
                String[] split = words[i].split("-");
                for (int j = 0; j < split.length; j++) {
                    resultDash += detwistWord(split[j]);
                    resultDash += "-";
                }
                resultDash = resultDash.substring(0, resultDash.length() - 1);
                words[i] = resultDash;
            } else {
                words[i] = detwistWord(words[i]);
            }
        }

        StringBuilder builder = new StringBuilder();
        for (String word : words) {
            builder.append(word);
            builder.append(" ");

        }
        result = builder.toString();
        result = result.trim();
        result += "\n";
        return result;
    }


    public File getWordListFile() {
        return wordListFile;
    }

    public void setWordListFile(File wordListFile) {
        this.wordListFile = wordListFile;
        readInAndOrderDictionary(wordListFile);
    }

    public static Set<Character> stringToCharacterSet(String s) {
        Set<Character> set = new HashSet<>();
        for (char c : s.toCharArray()) {
            set.add(c);
        }
        return set;
    }

    public static boolean containsAllChars(String container, String containee) {
        Set<Character> characters = stringToCharacterSet(container);
        Set<Character> characters1 = stringToCharacterSet(containee);
        if(characters1.containsAll(characters)) {
            return true;
        } else {
            return false;
        }

    }


}

