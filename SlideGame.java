import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.ImageIcon;
//formerly was importing javax.swing.*, now only importing what is necessary

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
//formerly was importing java.awt.event.*

import java.awt.Color;
import java.util.Random;

import java.net.URL;
import java.net.MalformedURLException;

/** 
 * SlideGame is a clone of 2048 
 * it adds diagonal sliding and customizable board sizes
 * its slide behavior is also slightly different,
 * allowing multiple combinations per tile per move
 * @author Max Blachman <msb166@case.edu>
 */

public class SlideGame /*implements ActionListener*/ {
  
  /**
   * number of rows on the grid
   */
  private int rows;
  
  /**
   * number of columns on the grid
   */
  private int columns;
  
  /**
   * represents the board upon which the game is played
   */
  private JPanel board;
  
  /**
   * represents the game window
   */
  private JFrame gameFrame = new JFrame();
  
  /**
   * represents the values on the board (determines JButton text)
   */
  private int[][] boardValues;
  
  /**
   * represents individual tiles on the board (JPanel)
   */
  private JButton[][] boardButtons;
  
  /** 
    * constructor that takes the number of rows and columns as input
    * @param rows the number of rows the resulting grid will have
    * @param columns the number of columns resulting grid will have
    */
  public SlideGame (int rows, int columns) {
    /* ensures 3<=rows<=20 and 3<=columns<=20
     * at least 3 so digonal slides have unique button
     * at most 20 because more might take up the whole the screen
     */
    if (rows>=3 && rows<=20 )
      this.rows = rows;
    else {
      this.rows = 4;
      System.out.println("Number of rows must be in interval [3,20]");
    }
    
    if (columns>=3 && rows<=20)
      this.columns = columns;
    else {
      this.columns = 4;
      System.out.println("Number of columns must be in interval [3,20]");
    }
    
    board = new JPanel(new GridLayout(rows, columns));
    gameFrame.getContentPane().add(board, "Center");
    boardValues = new int[rows][columns];
    boardButtons = new JButton[rows][columns];
    ImageIcon image = new ImageIcon();
    String urlString = "http://s3.timetoast.com/public/uploads/photos/"+
                       "4194844/Light-Blue-3_small_square.png?1372813360";
    try {
      image = new ImageIcon(new URL(urlString));
    }
    catch (MalformedURLException e) {
      System.out.println("invalid image url for JButton background");
    }
    
    for (int i=0; i<boardButtons.length; i++) {
      for (int j=0; j<boardValues[0].length; j++) {
        boardButtons[i][j] = new JButton(image);
        boardButtons[i][j].setBackground(Color.BLACK);
        boardButtons[i][j].setOpaque(true);
        boardButtons[i][j].addActionListener(new ActionListener() {
          public void actionPerformed (ActionEvent e) {
            //stores the adderss of the (clicked) JButton;
            JButton b = (JButton)e.getSource();
            int rowOfB = -1;
            int columnOfB = -1;
            //finds JButton b in boardButtons, saves indicies
            for (int k=0; k<boardButtons.length; k++) {
              for (int l=0; l<boardButtons[0].length; l++) {
                if (b == boardButtons[k][l]) { //equality by address
                  rowOfB = k;
                  columnOfB = l;
                }
              }
            }
            //index of last row in boardButtons
            int lastRow = boardButtons.length-1; 
            //index of last row in boardButtons
            int lastCol = boardButtons[0].length-1; 
            //number of diagonals in boardButtons
            int diagLength = lastRow + lastCol + 1;
            //did boardValues change after selected slide method
            boolean didChange = false;
            
            /*applies corresponding slide method for JButton b
             based on location of JButton b
             */
            if (rowOfB==lastRow && columnOfB==lastCol) {//bottom right
              for (int m=0; m<diagLength; m++)
                didChange |= SlideGame.slideDownRight(boardValues,m);
              if (didChange)
                addOne();
            }
            else if (rowOfB==0 && columnOfB==0) {//top left
              for (int m=0; m<diagLength; m++)
                didChange |= SlideGame.slideUpLeft(boardValues,m);
              if (didChange)
                addOne();
            }
            else if (rowOfB==lastRow && columnOfB==0) {//bottom left
              for (int m=0; m<diagLength; m++)
                didChange |= SlideGame.slideDownLeft(boardValues,m);
              if (didChange)
                addOne();
            }
            else if (rowOfB==0 && columnOfB==lastCol) {//top right
              for (int m=0; m<diagLength; m++)
                didChange |= SlideGame.slideUpRight(boardValues,m);
              if (didChange)
                addOne();
            }
            else if (rowOfB == 0) { //top row, not corner
              for (int m=0; m < boardValues[0].length; m++)
                didChange |= SlideGame.slideUp(boardValues,m);
              if (didChange)
                addOne();
            }
            else if (rowOfB == lastRow) { //bottom row, not corner
              for (int m=0; m < boardValues[0].length; m++)
                didChange |= SlideGame.slideDown(boardValues,m);
              if (didChange)
                addOne();
            }
            else if (columnOfB == 0) { //left col, not corner
              for (int m=0; m < boardValues.length; m++) {
                didChange |= SlideGame.slideLeft(boardValues,m);
              }
              if (didChange)
                addOne();
            }
            else if (columnOfB == lastCol) {//right col, not corner
              for (int m=0; m < boardValues.length; m++)
                didChange |= SlideGame.slideRight(boardValues,m);
              if (didChange)
                addOne();
            }
            updateBoard();
          }
        }
                                            );
      }
    }
    
    addOne(); //add first non-zero tile to the game
    
    /* Initalizes gameFrame's size, and makes it visible
     * 65 because the image is 60px*60px 
     * plus 20 because the top bar is counted in window height
    */
    gameFrame.setSize(65*columns,65*rows+20);
    gameFrame.setVisible(true);
  }
  
  /**
   * getter method for boardValues array (address)
   */
  public int[][] getBoardValues() {
    return boardValues;
  }
  
  /**
   *places boardValues as text in boardButtons (zeroes not displayed)
   * then displays buttons
   */
  public void updateBoard() {
    for (int i=0; i<boardValues.length; i++) {
      for (int k=0; k<boardValues[0].length; k++) {
        //display digit if and only if it is non-zero
        if (boardValues[i][k] != 0) {
          boardButtons[i][k].setText(Integer.toString(boardValues[i][k]));
          boardButtons[i][k].setHorizontalTextPosition(JButton.CENTER);
          boardButtons[i][k].setVerticalTextPosition(JButton.CENTER);
        }
        else /*if (boardValues[i][k] == 0)*/
          boardButtons[i][k].setText("");
        board.add(boardButtons[i][k]);
      }
    }
  }
  
  /**
   * places 1 in a randomly chosen empty position in boardValues,
   * then updates the board
   */
  private void addOne() {
    int emptyCount = 0;
    for (int row=0; row<boardValues.length; row++) {
      for (int col=0; col<boardValues[0].length; col++) {
        if (boardValues[row][col] == 0)
          emptyCount += 1;
      }
    }
    Random rn = new Random();
    int randEmptyNum = rn.nextInt(emptyCount);
    int totalCount = 0;
    for (int row=0; row<boardValues.length; row++) {
      for (int col=0; col<boardValues[0].length; col++) {
        if (boardValues[row][col]==0 && randEmptyNum==totalCount++)
          boardValues[row][col] = 1;
      }
    }
    updateBoard();
  }
  
  /* slightly modified old addOne method, 
   * included to demonstrate improvement
  public void addOne() {
    int randomRow;
    int randomColumn;
    int[] isEmpty = new int[rows*columns];
    int arrayCounter = 0;
    for (int i = 0; i < boardValues.length; i++) {
      for (int j = 0; j < boardValues[0].length; j++) {
        if (boardValues[i][j] == 0) {
          isEmpty[arrayCounter++] = i*(boardValues[0].length) + j;
        }
      }
    }
    int randomIndex = (int)(Math.random()*arrayCounter);
    randomRow = isEmpty[randomIndex]/(boardValues[0].length);
    randomCol = isEmpty[randomIndex]%boardValues[0].length;
    boardValues [randomRow][randomCol] = 1;
    updateBoard();
  }*/
  
  /**slides elements left, combining ajacent elements of the same value
    * @param array the array that will be shifted
    * @param row the number of the row to be shifted
    * @return represents whether or not array has changed
    */
  public static boolean slideLeft (int[][] array, int row) {
    boolean arrayHasChanged = false; 
    for (int i=1; i<array[row].length; i++) {
      int k = i; //k used as temporary counter, in if statement for combining terms
      //if there is a 0 immediately left of i and the value at i is not 0
      if (array[row][i-1]==0 && array[row][i]!=0){ 
        //loop back from i until there is something to the left or the start of the array
        for (; k>0 && array[row][k-1]==0; k--) {
        }
        array[row][k] = array[row][i]; //copy value at i to place k
        array[row][i] = 0; //set value at i to zero
        if (i != k)
          arrayHasChanged = true;
      }
      if (array [row][k]!=0 && k!=0) { //if shifted element is not 0 and the index is not 0
        if (array[row][k] == array[row][k-1]) { //element at k is the same as element to left
          array[row][k-1] = 2*array[row][k]; //add them and shift left
          array[row][k] = 0; //deletes old value at k
          arrayHasChanged = true;
        }
      }
    }
    return arrayHasChanged;
  }
  
  /**slides elements right, combining ajacent elements of the same value
    * @param array the array that will be shifted
    * @param row the number of the row to be shifted
    * @return represents whether or not array has changed
    */
  public static boolean slideRight (int[][]array, int row) {
    boolean arrayHasChanged = false;
    for (int i=array[row].length-1; i>=0; i--) {
      int k = i;
      if (array[row][k]!=0) {
        //loops forward from i until a tile is right of i or array ends
        for (; k<array[row].length-1 && array[row][k+1]==0; k++) {
        }
        if ( i != k ) {
          array[row][k] = array[row][i]; //copy element at i to k
          array[row][i] = 0; //set element at i to zero
          arrayHasChanged = true; //array has changed
        }
        //element at k not 0, k not at end of array and element at k equals element right of k
        if (array[row][k]!=0 && k!=array[row].length-1 && array[row][k]==array[row][k+1]) {
          array[row][k+1] = 2*array[row][k]; //add the elements at k and k+1, place at k+1
          array[row][k] = 0; //reset the value of the element at k to 0
          arrayHasChanged = true;
        }
      }
    }
    return arrayHasChanged;
  }
  
  /**slides elements up, combining ajacent elements of the same value
    * precondition: array is rectangular
    * @param array the array that will be shifted
    * @param column the number of the column to be shifted
    * @return represents whether or not array has changed
   */
  public static boolean slideUp(int[][] array, int column) {
    if (array.length<=0 || array[0].length<=0)
      return false;
    boolean hasChanged = false;
    for ( int i=1; i<array.length; i++) {
      int k = i;
      if (array[i][column] != 0) {
        for ( ; k>0 && array[k-1][column]==0; k--) {
        }
        if (i != k) {
        array[k][column] = array[i][column];
        array[i][column] = 0;
        hasChanged = true;
        }
        if ( k!=0 && array[k-1][column]==array[k][column]) {
          array[k-1][column] = array[k-1][column]*2;
          array[k][column] = 0;
          hasChanged = true;
        }
      }
    }
    return hasChanged;
  }
  
  /**slides elements up and combines ajacent elements of the same value
    * precondition: array is rectangular
    * @param array the array that will be shifted
    * @param column the number of the column to be shifted
    * @return represents whether or not array has changed
    */
  public static boolean slideDown(int[][] array, int column) {
    if (array.length<=0 || array[0].length<=0)
      return false;
    boolean hasChanged = false;
    for (int i=array.length-1; i>=0; i--) {
      int k = i;
      if (array[i][column] != 0) {
        for (; k<array.length-1 && array[k+1][column]==0; k++) {
        }
        if (i != k) {
        array[k][column] = array[i][column];
        array[i][column] = 0;
        hasChanged = true;
        }
        if (k!=array.length-1 && array[k+1][column]==array[k][column]) {
          array[k+1][column] = array[k+1][column]*2;
          array[k][column] = 0;
          hasChanged = true;
        }
      }
    }
    return hasChanged;
  }
  
  /**helper method to verify rectangularity of 2D arrays
   * returns true if the array is rectangular otherwise false
   * @param array the 2D array to be checked
   */
  private static boolean isRectangular ( int[][] array ) {
    if (array.length != 0 && array[0].length != 0) {
      int elementsPerRow = array[0].length;
      for (int i = 1; i < array.length; i++) {
        if (elementsPerRow != array[i].length) {
          return false;
        }
      }
    }
    return true;
  }
  
  /**shifts the diagonalNum th diagonal of a 2D array down and to the right
   * @param array input array
   * @param diagonalNum index of diagonal, with 0 at the bottom left
   * @return represents whether or not array has changed
   */
  public static boolean slideDownRight (int[][] array, int diagonalNum ) {
    //number of rows in array
    int rows = array.length;
    int cols = -1;
    if (isRectangular(array)) {
      //number of columns in array
      cols = array[0].length;
    }
    else {
      throw new IllegalArgumentException("diagonal slides only operate on rectangular arrays");
    }
    if (rows==0 || cols==0) //if array has no elements
      return false;
    boolean hasChanged = false;
    int currentRow;
    int currentColumn;
    int tempRow;
    int tempColumn;
    if (diagonalNum < cols-1) { //0th diagonal is in bottom left corner
      currentRow = rows - 1;
      currentColumn = diagonalNum;
    }
    else /*if (diagonalNum >= cols -1 )*/ {
      currentRow = rows+cols-2-diagonalNum;
      currentColumn = cols-1;
    }
    while( currentRow>=0 && currentColumn>=0 ) {
      tempRow = currentRow+1;
      tempColumn = currentColumn+1;
      if (array[currentRow][currentColumn] != 0) {
        while(tempRow<rows && tempColumn<cols && array[tempRow][tempColumn]==0) {
          tempRow++;
          tempColumn++;
        }//loop forwards until the element is not zero or the end of the array
          tempRow-=1;
          tempColumn-=1;
        if (tempRow != currentRow) {
          hasChanged = true;
          array[tempRow][tempColumn] = array[currentRow][currentColumn];
          array[currentRow][currentColumn] = 0;
        }
      }
      if (tempRow<rows-1 && tempColumn<cols-1 && array[tempRow][tempColumn]!=0 && 
          array[tempRow][tempColumn]==array[tempRow+1][tempColumn+1]) {
        array[tempRow+1][tempColumn+1] = 2*array[tempRow][tempColumn];
        array[tempRow][tempColumn] = 0;
        hasChanged = true;
      }
      currentRow--;
      currentColumn--;
    }
    return hasChanged;
  }
  
  /**shifts the diagonalNum th diagonal of a 2D array up and to the left
   * @param array input array
   * @param diagonalNum index of the diagonal, with 0 at the bottom left
   * @return represents whether or not array has changed
   */
  public static boolean slideUpLeft (int[][] array, int diagonalNum) {
    if (array.length==0 || array[0].length==0) //if array has no elements
        return false;
      boolean hasChanged =false;
      int currentRow;
      int currentColumn;
      int tempColumn;
      int tempRow;
      if (diagonalNum < array.length) { //0th diagonal is in the bottom left corner
        currentRow = array.length - 1 - diagonalNum;
        currentColumn = 0;
      }
      else /*if (diagonalNum >= array.length)*/ {
        currentRow = 0;
        currentColumn = diagonalNum - array.length + 1;
      }
      while(currentRow<array.length && currentColumn<array[0].length) {
      tempRow = currentRow-1;
      tempColumn = currentColumn-1;
      if (array[currentRow][currentColumn] != 0) {
        while(tempRow>=0 && tempColumn>=0 && array[tempRow][tempColumn]==0) {
          tempRow--;
          tempColumn--;
        }//loop forwards until the element is not zero or the end of the array
          tempRow+=1;
          tempColumn+=1;
        if (tempRow != currentRow) {
          hasChanged = true;
          array[tempRow][tempColumn] = array[currentRow][currentColumn];
          array[currentRow][currentColumn] = 0;
        }
      }
      if (tempRow>0 && tempColumn>0 && array[tempRow][tempColumn]!=0 && 
          array[tempRow][tempColumn]==array[tempRow-1][tempColumn-1]) {
        array[tempRow-1][tempColumn-1] = 2*array[tempRow][tempColumn];
        array[tempRow][tempColumn] = 0;
        hasChanged = true;
      }
      currentRow++;
      currentColumn++;
    }
    return hasChanged;
  }
  
  /**shifts the diagonalNum th diagonal of a 2D array down and to the left
   * @param array input array
   * @param diagonalNum index of the diagonal, with 0 at the top left
   * @return represents whether or not array has changed
   */
  public static boolean slideDownLeft (int[][] array, int diagonalNum) {
    if (array.length==0 || array[0].length==0) //empty array
        return false;
    boolean hasChanged =false;
      int currentRow;
      int currentColumn;
      int tempColumn;
      int tempRow;
      if (diagonalNum < array.length) { //0th diagonal is in the top left corner
        currentRow = diagonalNum;
        currentColumn = 0;
      }
      else /*if (diagonalNum >= array.length)*/ {
        currentRow = array.length-1;
        currentColumn = diagonalNum-array.length+1;
      }
      while(currentRow>=0 && currentColumn<array[0].length) {
      tempRow = currentRow+1;
      tempColumn = currentColumn-1;
      if (array[currentRow][currentColumn] != 0) {
        while(tempRow<array.length && tempColumn>=0 && array[tempRow][tempColumn]== 0) {
          tempRow++;
          tempColumn--;
        }//loop until the element is non-zero or end of array
          tempRow-=1;
          tempColumn+=1;
        if (tempRow != currentRow) {
          hasChanged = true;
          array[tempRow][tempColumn] = array[currentRow][currentColumn];
          array[currentRow][currentColumn] = 0;
        }
      }
      if (tempRow<array.length-1 && tempColumn>0 && array[tempRow][tempColumn]!=0 && 
          array[tempRow][tempColumn]==array[tempRow+1][tempColumn-1]) {
        array[tempRow+1][tempColumn-1] = 2*array[tempRow][tempColumn];
        array[tempRow][tempColumn] = 0;
        hasChanged = true;
      }
      currentRow--;
      currentColumn++;
    }
    return hasChanged;
  }
  
  /**shifts the diagonalNum th diagonal of a 2D array up and to the right
   * @param array input array
   * @param diagonalNum index of the diagonal, with 0 at the top left
   * @return represents whether or not array has changed
   */
  public static boolean slideUpRight (int[][] array, int diagonalNum) {
    if (array.length==0 || array[0].length==0) //if array has no elements
        return false;
    boolean hasChanged =false;
      int currentRow;
      int currentColumn;
      int tempColumn;
      int tempRow;
      //0th diagonal is in the top left corner
      if (diagonalNum < array[0].length) { 
        currentRow = 0;
        currentColumn = diagonalNum;
      }
      else /*if (diagonalNum >= array[0].length)*/ {
        currentRow = diagonalNum-array[0].length+1;
        currentColumn = array[0].length-1;
      }
      while(currentRow<array.length && currentColumn>=0) {
      tempRow = currentRow-1;
      tempColumn = currentColumn+1;
      if (array[currentRow][currentColumn] != 0) {
        while(tempRow>=0 && tempColumn<array[0].length && array[tempRow][tempColumn]==0) {
          tempRow--;
          tempColumn++;
        }//loop until the element is not zero or the end of the array
          tempRow+=1;
          tempColumn-=1;
        if (tempRow != currentRow) {
          hasChanged = true;
          array[tempRow][tempColumn] = array[currentRow][currentColumn];
          array[currentRow][currentColumn] = 0;
        }
      }
      if (tempRow>0 && tempColumn<array[0].length-1 && array[tempRow][tempColumn]!=0 && 
          array[tempRow][tempColumn]==array[tempRow-1][tempColumn+1]) {
        array[tempRow-1][tempColumn+1] = 2*array[tempRow][tempColumn];
        array[tempRow][tempColumn] = 0;
        hasChanged = true;
      }
      currentRow++;
      currentColumn--;
    }
    return hasChanged;
  }
  
  
  /**main method starts the game. 
    * only job is to parse input, and start game,
    * since all game logic is handled by other methods in the class
    * @param args[] args[0] is number of rows and args[1] is the number of columns
    */
  public static void main (String[] args) {
    SlideGame s;
    int numRows;
    int numCols;
    if (args.length == 1)
      numRows = numCols = Integer.parseInt(args[0]);
    else if (args.length >= 2) {
      numRows = Integer.parseInt(args[0]);
      numCols = Integer.parseInt(args[1]);
    }
    else {
      numRows = 4;
      numCols = 4;
    }
    s = new SlideGame(numRows, numCols);
  }
}