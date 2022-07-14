import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class WordsFinder {
    static ArrayList<String> words = new ArrayList<>();

    static void getTheWordsOfN(int n) throws Exception {
        Scanner s = new Scanner(new File("C:\\Users\\Aa-Devv\\Desktop/Scrabble word list.txt"));
        while (s.hasNextLine()) {
            String word = s.nextLine();
            if (word.length() == n) {
                words.add(word);
            }
        }

    }

    static ArrayList<Guess> wordsTried = new ArrayList<>();


    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        String introWord;
        String outroWord;
        while (true) {
            System.out.print("Enter the first word: ");
            introWord = s.nextLine().toUpperCase();
            try {
                getTheWordsOfN(introWord.length());
            } catch (Exception e) {

            }
            if (introWord.equalsIgnoreCase("q")) return;
            words.add(introWord);
            break;

        }
        while (true) {
            System.out.print("Enter the last word: ");
            outroWord = s.nextLine().toUpperCase();
            if (outroWord.equalsIgnoreCase("q")) return;
            if (outroWord.length() != introWord.length())
                System.out.println("The two words should be the same length! please try again.");
            else if (outroWord.equalsIgnoreCase(introWord))
                System.out.println("The two words can't be the same! please try again.");
            else {
                words.add(outroWord);
                break;
            }
        }
        System.out.println("Computing...");
        int opt = 1;
        ArrayList<String> solution;
        while (true) {
            try {
                solution = solver(introWord, outroWord, opt, introWord.length());
                System.out.println("\nThe optimal number of words is: " + opt);
                System.out.println("Here are the words: ");
                for (int i = 0; i < solution.size(); i++) {
                    System.out.println(i + ") " + solution.get(i));
                }
                break;

            } catch (java.lang.IndexOutOfBoundsException e) {
                if (opt > words.size()) {
                    System.out.println("Sorry, there aren't enough words in the list to transform " + introWord + " to " + outroWord + ".");
                    return;
                }
                if (opt != 0 && opt % 1000 == 0)
                    System.out.println("If there's a transformation, the optimal number of words is more than " + opt + ".");
                opt++;
                wordsTried = new ArrayList<>();
            }


        }


    }

    static private boolean isTheWordInTheList(String word) {
        for (int i = 0; i < words.size(); i++)
            if (words.get(i).equalsIgnoreCase(word)) return true;
        return false;
    }

    private static Character[] getLetters(ArrayList<Integer> numbers) {
        Character[] seq = new Character[numbers.size()];
        for (int i = 0; i < numbers.size(); i++)
            seq[i] = getLetters0(numbers.get(i));
        return seq;
    }

    private static Character getLetters0(Integer numb) {
        switch (numb) {
            case 0:
                return 'A';
            case 1:
                return 'B';
            case 2:
                return 'C';
            case 3:
                return 'D';
            case 4:
                return 'E';
            case 5:
                return 'F';
            case 6:
                return 'G';
            case 7:
                return 'H';
            case 8:
                return 'I';
            case 9:
                return 'J';
            case 10:
                return 'K';
            case 11:
                return 'L';
            case 12:
                return 'M';
            case 13:
                return 'N';
            case 14:
                return 'O';
            case 15:
                return 'P';
            case 16:
                return 'Q';
            case 17:
                return 'R';
            case 18:
                return 'S';
            case 19:
                return 'T';
            case 20:
                return 'U';
            case 21:
                return 'V';
            case 22:
                return 'W';
            case 23:
                return 'X';
            case 24:
                return 'Y';
            case 25:
                return 'Z';

            default:
                return null;

        }
    }

    private static ArrayList<Integer> getNumbers(String word) {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            numbers.add(getNumbers0(word.charAt(i)));
        }
        return numbers;
    }

    private static Integer getNumbers0(Character letter) {
        switch (letter) {
            case 'A':
                return 0;
            case 'B':
                return 1;
            case 'C':
                return 2;
            case 'D':
                return 3;
            case 'E':
                return 4;
            case 'F':
                return 5;
            case 'G':
                return 6;
            case 'H':
                return 7;
            case 'I':
                return 8;
            case 'J':
                return 9;
            case 'K':
                return 10;
            case 'L':
                return 11;
            case 'M':
                return 12;
            case 'N':
                return 13;
            case 'O':
                return 14;
            case 'P':
                return 15;
            case 'Q':
                return 16;
            case 'R':
                return 17;
            case 'S':
                return 18;
            case 'T':
                return 19;
            case 'U':
                return 20;
            case 'V':
                return 21;
            case 'W':
                return 22;
            case 'X':
                return 23;
            case 'Y':
                return 24;
            case 'Z':
                return 25;

            default:
                return -1;

        }
    }


    static private boolean isTheWordValid(String word0, String word1) {
        boolean aChangeHappened = false;
        for (int i = 0; i < word0.length(); i++)
            if (word0.charAt(i) != word1.charAt(i))
                if (aChangeHappened)
                    return false;
                else
                    aChangeHappened = true;
        return aChangeHappened;
    }

    static private boolean isTheWordNotTriedBefore(String word) {
        if (wordsTried.size() == 0) return true;
        for (int i = 0; i < wordsTried.size(); i++)
            if (wordsTried.get(i).word.equalsIgnoreCase(word)) return false;
        return true;
    }

    static private void removeAllWordsOfATrial(Trial t) {
        if (wordsTried.size() == 0) return;
        for (int i = 0; i < wordsTried.size(); )
            if (wordsTried.get(i).t.equals(t)) {
                wordsTried.remove(i);
                i = 0;
            } else i++;
    }

    static private String getANewWord(Trial t) {
        ArrayList<Integer> numbers = getNumbers(t.word);
        numbers.set(t.which, -1);
        while (true) {
            numbers.set(t.which, (numbers.get(t.which) + 1));
            Character[] b = getLetters(numbers);
            StringBuilder word = new StringBuilder();
            for (int i = 0; i < b.length; i++)
                word.append(b[i]);
            if (isTheWordInTheList(word.toString()) && isTheWordValid(t.word, word.toString()) && isTheWordNotTriedBefore(word.toString())) {
                return word.toString();
            } else {
                for (int i = 0; i < numbers.size(); i++)
                    if (t.which == i && numbers.get(i) == 25) {
                        return null;
                    }
            }
        }
    }

    private static ArrayList<String> solver(String introWord, String outroWord, int opt, int numberOfLetters) {

        ArrayList<Trial> trials = new ArrayList<>();
        int which = numberOfLetters - 1;

        Trial newTrial = new Trial(introWord, which);
        trials.add(newTrial);
        wordsTried.add(new Guess(introWord, newTrial));
        while (true) {
            String newWord = getANewWord(newTrial);
            if (newWord == null) {
                which--;
                if (which != -1) {
                    trials.get(trials.size() - 1).which = which;
                } else {
                    removeAllWordsOfATrial(trials.get(trials.size() - 1));
                    trials.remove(trials.size() - 1);
                    which = trials.get(trials.size() - 1).which;
                    newTrial = trials.get(trials.size() - 1);

                }
            } else {
                wordsTried.add(new Guess(newWord, newTrial));
                which = numberOfLetters - 1;
                if (newWord.equalsIgnoreCase(outroWord)) break;
                introWord = newWord;
                newTrial = new Trial(introWord, which);
                trials.add(newTrial);
                if (trials.size() > opt) {
                    removeAllWordsOfATrial(newTrial);
                    trials.remove(trials.size() - 1);
                    which = trials.get(trials.size() - 1).which;
                    newTrial = trials.get(trials.size() - 1);
                }
            }
        }
        trials.add(new Trial(outroWord, numberOfLetters - 1));
        ArrayList<String> words = new ArrayList<>();
        for (int i = 0; i < trials.size(); i++)
            words.add(trials.get(i).word);
        return words;
    }


}

class Trial {
    String word;
    int which;


    Trial(String word, int which) {
        this.word = word;
        this.which = which;

    }

}

class Guess {
    String word;
    Trial t;

    Guess(String word, Trial t) {
        this.word = word;
        this.t = t;
    }
}
