import java.util.*;
import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.Scanner;
public class BoggleSolver
{
    // Initializes the data structure using the given array of strings as the dictionary.
    
    // (You can assume each word in the dictionary contains only the uppercase letters A - Z.)
    
    HashSet<String> dict = new HashSet<String>();
    public BoggleSolver(String dictionaryName)
    {
        try {
             File file = new File(dictionaryName);
             Scanner myReader = new Scanner(file);
             while (myReader.hasNextLine()) {
               String data = myReader.nextLine();
               dict.add(data);
             }
             myReader.close();
        } catch (FileNotFoundException e) {
             System.out.println("An error occurred.");
             e.printStackTrace();
        }
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable object
    public Iterable<String> getAllValidWords(BoggleBoard board)
    {
        HashSet<String> validWords = new HashSet<String>();
        boolean[][] visited = new boolean[board.rows()][board.cols()];
        for(int r = 0; r<board.rows(); r++){
            for(int c = 0; c<board.cols(); c++){
                getAllValidWordsHelper(board, r, c,visited, "",validWords);
            }
        }

        return validWords;
    }
    
    public void getAllValidWordsHelper(BoggleBoard board, int r, int c, boolean[][] visited, String w, HashSet<String> words){
         if(r<0 || r>=board.rows() || c<0 || c >= board.cols() || visited[r][c]){
             return;
         }
         else{
            visited[r][c] = true;
            if(board.getLetter(r,c) == 'Q')
            {
                w+="QU";
            }
            else
            {
                w+=board.getLetter(r, c);
            }
         }
         if (dict.contains(w) && w.length() >= 3){
               words.add(w);
         }
         if(r-1 >= 0 && c-1>=0){
             getAllValidWordsHelper(board, r-1, c-1, visited, w, words);
         }
         
         getAllValidWordsHelper(board, r-1, c, visited, w, words);
         getAllValidWordsHelper(board, r-1, c+1, visited, w, words);
         getAllValidWordsHelper(board, r, c-1, visited, w, words);
         getAllValidWordsHelper(board, r, c+1, visited, w, words);
         getAllValidWordsHelper(board, r+1, c-1, visited, w, words);
         getAllValidWordsHelper(board, r+1, c, visited, w, words);
         getAllValidWordsHelper(board, r+1, c+1, visited, w, words);
         visited[r][c] = false;
         
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A - Z.)
    public int scoreOf(String word)
    {
        
        
        if(word.length() >= 8){
            return 11;
        }
        else if(word.length() >= 7){
            return 5;
        }
        else if(word.length() >= 6){
            return 3;
        }
        else if(word.length() >= 5){
            return 2;
        }
        else if(word.length() >= 3){
            return 1;
        }
            
        
        return 0;
        
    }

    public static void main(String[] args) {
        System.out.println("WORKING");

        final String PATH   = "./data/";
        BoggleBoard  board  = new BoggleBoard("board-q.txt");
        BoggleSolver solver = new BoggleSolver("dictionary-algs4.txt");

        int totalPoints = 0;

        for (String s : solver.getAllValidWords(board)) {
            System.out.println(s + ", points = " + solver.scoreOf(s));
            totalPoints += solver.scoreOf(s);
        }

        System.out.println("Score = " + totalPoints); //should print 84

        new BoggleGame(4, 4);
    }

}
