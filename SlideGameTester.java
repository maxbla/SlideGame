import org.junit.*;
import static org.junit.Assert.*;

/*
 * @author Max Blachman <msb166@case.edu>
 * tests the SlideGame class
 */

public class SlideGameTester {
  
  /**
   * Helper method to compare two dimensional arrays.
   */
  private void equals2Darrays(int[][] expected, int[][] received) {
    assertEquals(expected.length, received.length);
    for (int i = 0; i < expected.length; i++) {
      assertArrayEquals(expected[i], received[i]);
    }
  }
  
  /**
   * test the slideLeft method
   */
  @Test
  public void testSlideLeft() {
    
    int[][] initialArray = 
     {{0,0,0,0,0}, //test zero
      {1,0,0,0,0}, //test one, test first
      {0,0,1,0,0}, //test middle
      {0,0,0,0,1}, //test last
      {1,1,1,1,1}, //test many
      {1,1,2,4,8},}; //test many (and "cascading combination")
    
    int[][] outputArray =
     {{0,0,0,0,0},
      {1,0,0,0,0},
      {1,0,0,0,0},
      {1,0,0,0,0},
      {2,2,1,0,0},
     {16,0,0,0,0},};
    
    for (int i=0; i<initialArray.length; i++) 
      SlideGame.slideLeft(initialArray, i);
    equals2Darrays(initialArray, outputArray);
  }
  
  /**
   * tests the slideRight method
   */
  @Test
  public void testSlideRight() {
    
    int[][] initialArray = 
     {{0,0,0,0,0}, //test zero
      {1,0,0,0,0}, //test one, test first
      {0,0,1,0,0}, //test middle
      {0,0,0,0,1}, //test last
      {1,1,1,1,1}, //test many
      {8,4,2,1,1},}; //test many (and "cascading combination")
    
    int[][] outputArray =
     {{0,0,0,0,0}, 
      {0,0,0,0,1},
      {0,0,0,0,1},
      {0,0,0,0,1},
      {0,0,1,2,2},
      {0,0,0,0,16},};
    
    for (int i=0; i<initialArray.length; i++)
      SlideGame.slideRight(initialArray, i);
    equals2Darrays(outputArray, initialArray);
  }

  /**
   * test the slideUp method
   */
  @Test
  public void testSlideUp () {
    
    int[][] array =
     {{0,1,0,0,1,1},
      {0,0,0,0,1,1},
      {0,0,1,0,1,2},
      {0,0,0,0,1,4},
      {0,0,0,1,1,8}};
     //0,1,2,3,4,5
    //0: test zero
    //1: test one, test first
    //2: test middle
    //3: test last
    //4: test many,
    //5: test many (and "cascading combination")
    int[][] array2 = 
     {{0,1,1,1,2,16},
      {0,0,0,0,2,0},
      {0,0,0,0,1,0},
      {0,0,0,0,0,0},
      {0,0,0,0,0,0}};
    
    for (int i=0; i<array[0].length; i++) {
      if (i >= 2)
        assertTrue(SlideGame.slideUp(array, i));
      else
        assertFalse(SlideGame.slideUp(array, i));
    }
      equals2Darrays(array2, array); 
  }
  
  /**
   * tests the slideDown method
   */
  @Test
  public void testSlideDown () {
    
    int[][] initialArray =
     {{0,1,0,0,1,8},
      {0,0,0,0,1,4},
      {0,0,1,0,1,2},
      {0,0,0,0,1,1},
      {0,0,0,1,1,1}};
     //0,1,2,3,4,5
    
     //0: test zero
     //1: test one, test last
     //2: test middle
     //3: test first
     //4: test many,
     //5: test many (and "cascading combination")
    
    int[][] finalArray =
     {{0,0,0,0,0,0},
      {0,0,0,0,0,0},
      {0,0,0,0,1,0},
      {0,0,0,0,2,0},
      {0,1,1,1,2,16}};
    
    for (int i=0; i<initialArray[0].length; i++) {
      if (i==1 || i==2 || i>3)
        assertTrue(i + " arrayDidNotChangeError", SlideGame.slideDown(initialArray,i));
      else
        assertFalse(i + " arrayDidChangeError", SlideGame.slideDown(initialArray,i));
    }
      equals2Darrays (finalArray, initialArray);
    
  }
  
  /**
   * tests the slide down, left method (diagonal slide)
   */
  @Test
  public void testSlideDownRight () {
    
    int[][] initialArray =
     {{8,1,1,1,1}, //8
      {0,4,0,1,1}, //7
      {0,0,2,0,1}, //6
      {1,1,0,1,0}, //5
      {0,0,0,1,1}};//4
    /* 0 1 2 3 4 */
    
 /* 0: test zero
  * 1: test one, test last
  * 2: test middle
  * 3: test first
  * 4: test many (and "cascading combination")
  * 5: test last
  * 6: test many
  * 7: test many
  * 8: test one
  */
    int[][] finalArray =
     {{0,0,0,0,1},
      {0,0,0,1,2},
      {0,0,0,0,2},
      {0,0,0,0,1},
      {0,1,1,1,16}};
    
    for(int i=0; i<initialArray.length+initialArray[0].length-1; i++) {
      if (i==0 || i ==3 || i==8) 
        assertFalse(i + " arrayDidChangeError", SlideGame.slideDownRight(initialArray,i));
      else
        assertTrue(i + " arrayDidNotChangeError", SlideGame.slideDownRight(initialArray,i));
    }
    equals2Darrays(finalArray, initialArray);
  
  }
  
  /**
   * tests the slide up, right method (diagonal slide)
    */
  @Test
  public void testSlideUpLeft () {
  
    int[][] initialArray =
    /* 4 5 6 7 8  <--diagonal number*/
/*4*/{{1,0,1,1,1},
/*3*/ {1,1,0,1,1},
/*2*/ {0,0,2,0,1},
/*1*/ {0,1,0,4,1},
/*0*/ {0,1,0,0,8}};
    
 /* 0: test zero
  * 1: test one, test last
  * 2: test middle
  * 3: test first
  * 4: test many (and "cascading combination")
  * 5: test last
  * 6: test many
  * 7: test many
  * 8: test one
  */
    int[][] finalArray =
    {{16,1,2,2,1},
      {1,0,0,1,0},
      {1,0,0,0,0},
      {1,0,0,0,0},
      {0,0,0,0,0}};
    
    for(int i=0; i<initialArray.length+initialArray[0].length-1; i++) {
      if ( i==0 || i ==3 || i==8 ) 
        assertFalse(i + " arrayDidChangeError", SlideGame.slideUpLeft(initialArray,i));
      else
        assertTrue(i + " arrayDidNotChangeError", SlideGame.slideUpLeft(initialArray,i));
    }
    equals2Darrays(finalArray, initialArray);
    
  }
  
  /**
   * tests the slide down, left method (diagonal slide)
   */
  @Test
  public void testSlideDownLeft () {
  
    int[][] initialArray = 
/*0*/{{0,0,0,1,8},
/*1*/ {1,1,0,4,0},
/*2*/ {0,0,2,0,1},
/*3*/ {0,1,0,1,1},
/*4*/ {1,1,1,1,1}};
    /* 4 5 6 7 8  <-- diagonal number*/
    
 /* 0: test zero
  * 1: test one, test first
  * 2: test middle
  * 3: test last
  * 4: test many (and "cascading combination")
  * 5: test first
  * 6: test many
  * 7: test many
  * 8: test one
  */
    int[][] finalArray =
     {{0,0,0,0,0},
      {1,0,0,0,0},
      {1,0,0,0,0},
      {1,0,0,1,0},
     {16,1,2,2,1}};
    
    for(int i = 0; i < (initialArray.length + initialArray[0].length - 1); i++) {
      if ( i==0 || i ==1 || i ==5 || i==8 ) 
        assertFalse(i + " arrayDidChangeError", SlideGame.slideDownLeft(initialArray,i));
      else
        assertTrue(i + " arrayDidNotChangeError", SlideGame.slideDownLeft(initialArray,i));
    }
    equals2Darrays (finalArray, initialArray);
  }
  
  /**
   * tests the slide up, right method (diagonal slide)
   */
  @Test
  public void testSlideUpRight () {
  
    int[][] initialArray = 
     /*0 1 2 3 4  <-- diagonal number*/
     {{0,0,0,1,1},//4
      {1,1,0,1,1},//5
      {0,0,2,0,1},//6
      {0,4,0,1,1},//7
      {8,0,1,1,1}};//8
    
 /* 0: test zero
  * 1: test one, test first
  * 2: test middle
  * 3: test last
  * 4: test many (and "cascading combination")
  * 5: test first
  * 6: test many
  * 7: test many
  * 8: test one
  */
    
    int[][] finalArray = 
     {{0,1,1,1,16},
      {0,0,0,0,1},
      {0,0,0,0,2},
      {0,0,0,1,2},
      {0,0,0,0,1}};
    
    for(int i = 0; i<initialArray.length+initialArray[0].length-1; i++) {
      if ( i==0 || i ==3 || i ==5 || i==8 ) 
        assertFalse(i + " arrayDidChangeError", SlideGame.slideUpRight(initialArray,i));
      else
        assertTrue(i + " arrayDidNotChangeError", SlideGame.slideUpRight(initialArray,i));
    }
    equals2Darrays (finalArray, initialArray);
  }

}