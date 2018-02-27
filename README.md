SlideGame is a [2048]( https://gabrielecirulli.github.io/2048/ "2048") clone written in java using swing and awt for its GUI. Later revisions may implement this GUI with the javafx and/or curses. It was initially an assignment for the introduction to programming in java class at Case Western Reserve University. The controls for SlideGame are intended to be simple (but different from those of 2048).

To run Slidegame, clone the repository, compile SlideGame.java

`javac SlideGame.class`

then run the program

`java SlideGame`

this creates a window with a game of size 4 tiles by 4 tiles. If you wish to create a game with a different number of rows or columns, you may do so by specifying the number of rows and columns with command line arguments

`java Slidegame 5 7`

To compile the the JUnit tests, run

 `javac -d ./ -cp ./junit-4.10.jar ./SlideGameTester.java ./SlideGame.java`

  from the root directory of the repository (for example on my MacOS computer the root directory of this repository is `/Users/Max/GitHub/SlideGame`), then

  `java -cp .:junit-4.10.jar org.junit.runner.JUnitCore SlideGameTester`

  from the same root directory of the repository


Slidegame depends on a connection to the world wide web for button coloring.

|Operation           |Control |
|--------------------|--------|
|slide tiles right   |click on a tile in the the right column of tiles, not at the top or bottom of the column |
|slide tiles up      |click on a tile in the the top row of tiles, not at the left or right of the row|
|slide tiles left    |click on a tile in the the left column of tiles, not at the top or bottom of the column|
|slide tiles down    |click on a tile in the the bottom row of tiles, not at the left or right of the row|
|slide tiles diagonally|click in the corner corresponding to diagonal direction you want to slide (e.g. top left corner to slide up and left)

SlideGame was written as a part of an introductory programming class. As a result, it had some undesirable logic and style which has subsequently been changed. All lines should now be limited to 120 characters at maximum and it should be fairly clear what every method does and how it operates.

Note that Slidegame does not seek to mimic 2048's behavior exactly. If you are familiar with 2048, you may notice that the sliding in this game is subtly different. 2048 uses non-greedy sliding, whereas this game intentionally has greedy sliding (i.e. [1,1,2,4] slid right becomes [0,0,0,8])

If you have any questions or comments, please email me at blachmanmax at google mail dot com
