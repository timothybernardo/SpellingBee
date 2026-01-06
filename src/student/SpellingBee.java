package student;

import edu.willamette.cs1.spellingbee.SpellingBeeGraphics;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class SpellingBee {
  private static final String ENGLISH_DICTIONARY = "res/EnglishWords.txt";
  public static final int NUM_CHARS = 7;
  public static final int ALPHABET_COUNT = 26;
  public static final int MIN_VALID_WORD_LENGTH = 4;
  public static final int PANGRAM_BONUS = 7;
  private SpellingBeeGraphics sbg;
  private Set<String> foundWords = new HashSet<>();
  private int totalScore = 0;
  private int totalWords = 0;

  public void run() {
    sbg = new SpellingBeeGraphics();
    sbg.addField("Puzzle", (s) -> puzzleAction(s));
    sbg.addField("Word", (s) -> wordAction(s));
    sbg.addButton("Solve", (s) -> solveAction());
    sbg.addButton("Shuffle", (s) -> shuffleAction());
  }

  private void puzzleAction(String s) {
    String puzzle = s.toUpperCase();
    String validationMessage = validatePuzzle(puzzle);
    if (!validationMessage.isEmpty()) {
      sbg.showMessage(validationMessage, Color.RED);
    } else {
      sbg.setBeehiveLetters(puzzle);
    }
  }

  private String validatePuzzle(String puzzle) {
    if (puzzle.length() != NUM_CHARS) {
      return "Puzzle must contain exactly " + NUM_CHARS + " characters.";
    }
    if (!isAllLetters(puzzle)) {
      return "Puzzle can only contain letters (A-Z).";
    }
    if (hasDuplicateLetters(puzzle)) {
      return "Puzzle cannot contain duplicate letters.";
    }
    return "";
  }

  private boolean isAllLetters(String puzzle) {
    for (int i = 0; i < puzzle.length(); i++) {
      if (puzzle.charAt(i) < 'A' || puzzle.charAt(i) > 'Z') {
        return false;
      }
    }
    return true;
  }

  private boolean hasDuplicateLetters(String puzzle) {
    boolean[] seen = new boolean[ALPHABET_COUNT];
    for (char c : puzzle.toCharArray()) {
      int index = c - 'A';
      if (seen[index]) {
        return true;
      }
      seen[index] = true;
    }
    return false;
  }

  private void solveAction() {
    sbg.clearWordList();
    List<String> dictionary = loadDictionary();
    String puzzle = sbg.getBeehiveLetters();
    char centerLetter = puzzle.charAt(0);

    for (String word : dictionary) {
      if (isValidWord(word, puzzle, centerLetter)) {
        foundWords.add(word);
        int score = calculateScore(word, puzzle);
        String formattedWord = String.format("%s (%d)", word.toLowerCase(), score);
        if (isPangram(word, puzzle)) {
          sbg.addWord(formattedWord, Color.BLUE);
        } else {
          sbg.addWord(formattedWord);
        }
      }
    }
    updateScore();
  }

  private int calculateScore(String word, String puzzle) {
    int score = (word.length() == MIN_VALID_WORD_LENGTH) ? 1 : word.length();
    if (isPangram(word, puzzle)) {
      score += PANGRAM_BONUS;
    }
    return score;
  }

  private boolean isPangram(String word, String puzzle) {
    for (int i = 0; i < puzzle.length(); i++) {
      char puzzleChar = puzzle.charAt(i);
      boolean containsChar = false;
      for (char c : word.toCharArray()) {
        if (c == puzzleChar) {
          containsChar = true;
          break;
        }
      }
      if (!containsChar) {
        return false;
      }
    }
    return true;
  }

  private List<String> loadDictionary() {
    List<String> words = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(ENGLISH_DICTIONARY))) {
      String word;
      while ((word = br.readLine()) != null) {
        words.add(word.toUpperCase());
      }
    } catch (IOException e) {
      sbg.showMessage("Error loading dictionary!", Color.RED);
    }
    return words;
  }

  private boolean isValidWord(String word, String puzzle, char centerLetter) {
    if (word.length() < MIN_VALID_WORD_LENGTH) {
      return false;
    }
    boolean containsCenter = false;
    for (char c : word.toCharArray()) {
      if (c == centerLetter) {
        containsCenter = true;
      }
      if (puzzle.indexOf(c) == -1) {
        return false;
      }
    }
    return containsCenter;
  }

  private void wordAction(String word) {
    word = word.toUpperCase();
    String puzzle = sbg.getBeehiveLetters();
    char centerLetter = puzzle.charAt(0);
    String validationMessage = validateUserWord(word, puzzle, centerLetter);
    if (validationMessage.isEmpty()) {
      int score = calculateScore(word, puzzle);
      boolean isPangram = isPangram(word, puzzle);
      String formattedWord = String.format("%s (%d)", word.toLowerCase(), score);
      if (isPangram) {
        sbg.addWord(formattedWord, Color.BLUE);
      } else {
        sbg.addWord(formattedWord);
      }
      foundWords.add(word);
      updateScore();
    } else {
      sbg.showMessage(validationMessage, Color.RED);
    }
  }

  private String validateUserWord(String word, String puzzle, char centerLetter) {
    if (word.length() < MIN_VALID_WORD_LENGTH) {
      return "The word does not include at least four letters.";
    }
    if (!loadDictionary().contains(word)) {
      return "The word is not in the dictionary.";
    }
    boolean containsCenterLetter = false;
    for (char c : word.toCharArray()) {
      if (c == centerLetter) {
        containsCenterLetter = true;
      }
      if (puzzle.indexOf(c) == -1) {
        return "The word includes letters not in the beehive.";
      }
    }
    if (!containsCenterLetter) {
      return "The word does not include the center letter.";
    }
    if (foundWords.contains(word)) {
      return "The word has already been found.";
    }
    return "";
  }

  private void updateScore() {
    totalWords = foundWords.size();
    totalScore = 0;
    for (String word : foundWords) {
      totalScore += calculateScore(word, sbg.getBeehiveLetters());
    }
    String finalMessage = String.format("%d words; %d points", totalWords, totalScore);
    sbg.showMessage(finalMessage);
  }

  private void shuffleAction() {
    String puzzle = sbg.getBeehiveLetters();
    char centerLetter = puzzle.charAt(0);
    List<Character> puzzleList = new ArrayList<>();

    for (int i = 1; i < puzzle.length(); i++) {
      puzzleList.add(puzzle.charAt(i));
    }
    Collections.shuffle(puzzleList);
    StringBuilder shuffledPuzzle = new StringBuilder();
    shuffledPuzzle.append(centerLetter);
    for (char c : puzzleList) {
      shuffledPuzzle.append(c);
    }
    sbg.setBeehiveLetters(shuffledPuzzle.toString());
  }


  public static void main(String[] args) {
    new SpellingBee().run();
  }
}
