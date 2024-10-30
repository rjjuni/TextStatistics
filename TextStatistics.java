import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TextStatistics implements TextStatisticsInterface{
	
	private static final String DELIMITERS = "[\\W\\d_]+";
	private File file;
	private int charCount;
	private int wordCount;
	private int lineCount;
	//stores the amount of letters
	private int[] letterCount = new int[26];
	//stores the length of words
	private int[] wordLengthCount = new int[24];
	private double averageWordLength;
	//stores the lines of the longest words
	private ArrayList<Integer> lineOfLongestWord = new ArrayList<Integer>();
	private int lengthOfLongestWord;
	//stores a map of the letter and its corresponding index for sorting
	private Map<String, Integer> alphabetMap = new HashMap<>();

	public TextStatistics(File file) {
		
		//if the file can be used, continue
		if(file.exists() && file.isFile() && file.canRead()) {
			this.file = file;
			
			Scanner sc;
			try {
				
				sc = new Scanner(file);
				
				//initialize the variables used
				this.lineCount = 0;
				this.charCount = 0;
				this.wordCount = 0;
				this.alphabetMap = buildAlphabetMap();
				int currentLine = 1;
				int wordLengthSum = 0;
				
				//continues while the scanner has another line to read in the file 
				while(sc.hasNextLine()) {
					String line = sc.nextLine();
					charCount += line.length();
					//adding one for the new lines
					charCount++;
					lineCount ++;
					
					//creates a secondary scanner to scan the words within a line
					Scanner ls = new Scanner(line);
					ls.useDelimiter(DELIMITERS);
				
		            while(ls.hasNext()){  //looping through words
		            	String word = ls.next();
		            	int wordLength = word.length();
		            	wordLengthSum += wordLength;
		            	
		            	//if word length is larger than 23, set the index to 23
		            	int wordLengthIndex = wordLength > 23 ? 23: wordLength;
		            	//increment the word length index when there is a word length that matches
		            	wordLengthCount[wordLengthIndex] = wordLengthCount[wordLengthIndex] + 1;
		            	
		            	String lettersInword = word.toLowerCase();
		            	
		            	for(char ch : lettersInword.toCharArray()) {
		            		if(Character.isLetter(ch)) {
		            			int letterIndex = alphabetMap.get(String.valueOf(ch));
		            			letterCount[letterIndex] = letterCount[letterIndex] + 1;
		            		}
		            	}
		            	
		            	//sets the length of the longest word to the current word if it is larger than the previous largest
		            	if(wordLength > lengthOfLongestWord) {
		            		lengthOfLongestWord = wordLength;
		            		lineOfLongestWord.clear();
		            		lineOfLongestWord.add(currentLine);
		            	}
		            	else if(wordLength == lengthOfLongestWord) {
		            		if(!lineOfLongestWord.contains(currentLine)) {
		            			lineOfLongestWord.add(currentLine);
		            		}
		            	}
		                wordCount++; 
		            }  
		            
		            ls.close();
		            
		            currentLine++;	
				}
				//gets the average word length from dividing the sum off all the word's length by the total word count
				double dWordCount = wordCount;
				double dWordLengthSum = wordLengthSum;
				this.averageWordLength = dWordLengthSum / dWordCount;
				
				sc.close();
				
			} catch (Exception e) {
				//prints any exception that the program may throw during scanning
				e.printStackTrace();
			}
			
			
		}
		
	}

	@Override
	/**
	 * @return the number of characters in the text file
	 */
	public int getCharCount() {
		return charCount;
	}

	@Override
	/**
	 * @return the number of words in the text file
	 */
	public int getWordCount() {
		return wordCount;
	}

	@Override
	/**
	 * @return the number of lines in the text file
	 */
	public int getLineCount() {
		return lineCount;
	}

	@Override
	/**
	 * @return the letterCount array with locations [0]..[25] for 'a' through 'z'
	 */
	public int[] getLetterCount() {
		return letterCount;
	}

	@Override
	/**
	 * @return the wordLengthCount array with locations [0]..[23] with location [i]
	 * storing the number of words of length i in the text file. Location [0] is not used.
	 * Location [23] holds the count of words of length 23 and higher.
	 */
	public int[] getWordLengthCount() {
		return wordLengthCount;
	}

	@Override
	/**
	 * @return the average word length in the text file
	 */
	public double getAverageWordLength() {
		return averageWordLength;
	}
	
	public Object[] getLinesOfLongestWord() {
		//changes the arayList to an array
		Object[] arr = this.lineOfLongestWord.toArray();
		return arr;
	}
	
	public String toString() {
		String wordLengthTable = "";
			//creates a string of the word length frequencies
		for(int i = 0; i < this.lengthOfLongestWord; i++) {
			if(i >= 10) { // if the index is larger than 10, removes a space for formating purposes
				wordLengthTable += "    " + i + "          " + wordLengthCount[i] + "\n";
			}else {
			wordLengthTable += "     " + i + "          " + wordLengthCount[i] + "\n";
			}
		}
		//puts the lines of the longest words into a string to print
		String longestWordLine = "";
		for(int i = 0; i < getLinesOfLongestWord().length; i++) {
			longestWordLine += getLinesOfLongestWord()[i] + " " ;
		}
			

		return "Statistics for " + this.file + "\n"
			+ "==========================================================" + "\n"
		+ getLineCount() + " Lines" + "\n" + getWordCount() + " Words" + "\n" + getCharCount() + " Characters" + "\n" +
		"------------------------------" + "\n" + " a = " + letterCount[0] + "         " + " n = " + letterCount[13] + "\n" +
		" b = " + letterCount[1] + "          " + " o = " + letterCount[14] + "\n" +
		" c = " + letterCount[2] + "          " + " p = " + letterCount[15] + "\n" +
		" d = " + letterCount[3] + "          " + " q = " + letterCount[16] + "\n" + 
		" e = " + letterCount[4] + "          " + " r = " + letterCount[17] + "\n" +
		" f = " + letterCount[5] + "          " + " s = " + letterCount[18] + "\n" +
		" g = " + letterCount[6] + "          " + " t = " + letterCount[19] + "\n" +
		" h = " + letterCount[7] + "          " + " u = " + letterCount[20] + "\n" +
		" i = " + letterCount[8] + "          " + " v = " + letterCount[21] + "\n" +
		" j = " + letterCount[9] + "          " + " w = " + letterCount[22] + "\n" +
		" k = " + letterCount[10] + "          " + " x = " + letterCount[23] + "\n" +
		" l = " + letterCount[11] + "          " + " y = " + letterCount[24] + "\n" +
		" m = " + letterCount[12] + "          " + " z = " + letterCount[25] + "\n" +
		"------------------------------" + "\n" + 
		"length " + " frequency" + "\n" +
		"------ " + " ---------" + "\n" +
		wordLengthTable + "\n" +
		"Average Word Length: " + getAverageWordLength() + "\n" +
		"Lines of longest word: " + longestWordLine + "\n" +
		"Length of the longest word: " + this.lengthOfLongestWord;

	}

	/** 
	 * Creates a map with the letter and its corresponding index value
	 * in order to put it into the array
	 * 
	 * @return the map of the alphabet and the index
	 */
	public Map<String, Integer> buildAlphabetMap() {
		
		Map<String, Integer> map = new HashMap<>();
		
		map.put("a",  0);
		map.put("b",  1);
		map.put("c",  2);
		map.put("d",  3);
		map.put("e",  4);
		map.put("f",  5);
		map.put("g",  6);
		map.put("h",  7);
		map.put("i",  8);
		map.put("j",  9);
		map.put("k",  10);
		map.put("l",  11);
		map.put("m",  12);
		map.put("n",  13);
		map.put("o",  14);
		map.put("p",  15);
		map.put("q",  16);
		map.put("r",  17);
		map.put("s",  18);
		map.put("t",  19);
		map.put("u",  20);
		map.put("v",  21);
		map.put("w",  22);
		map.put("x",  23);
		map.put("y",  24);
		map.put("z",  25);
		
		return map;
		
	}
	
}
