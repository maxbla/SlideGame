SlideGame is a 2048 (see: https://gabrielecirulli.github.io/2048/) clone written as an assignment for EECS 132 (introduction to programming in java) at Case Western Reserve University. SlideGame uses java's swing and awt packages to deliver a GUI. A later revision may implement this GUI with the newer javafx and/or add printing to the command line as a method of representing the state of the game. The controls for SlideGame are intended to be simple (but different from those of 2048).

To run Slidegame, download (at least) SlideGame.class, open a terminal, change directory to the one which contains SlideGame.class and type 'java SlideGame' this will create a (window with) a game which is 4 tiles by 4 tiles in size. If you wish to create a game with a different number of rows or columns, you may do so by specifying the number of rows and columns with command line arguments (eg java Slidegame 5 7).

Slidegame depends on a few packages included in java runtime environments (JREs) as well as a connection to the world wide web.

Operation           Control

slide tiles right    click on a tile in the the right column of tiles, not at the top or bottom of the column

slide tiles up       click on a tile in the the top row of tiles, not at the left or right of the row

slide tiles left     click on a tile in the the left column of tiles, not at the top or bottom of the column

slide tiles down     click on a tile in the the bottom row of tiles, not at the left or right of the row

slide tiles diagonal click in the corner corresponding to diagonal direction you want to slide (eg top left corner to slide up and left)

SlideGame was written as a part of an introductory programming class. As a result, it had some undesirable logic and style which has subsequently been changed. All lines should now be limited to 120 characters at maximum and it should be fairly clear what every method does and how it operates.

Note that this is not a clone like dolly the sheep is a clone. It is inspired by 2048, but does not seek to mimic 2048's behavior exactly. If you are familiar with 2048, you may notice that the sliding in this game is not the same. 2048 implements non-greedy sliding, whereas this game intentionally does not.

If you have any questions or comments, please email me at blachmanmax at google mail dot com (email address slightly obfuscated in an attempt to prevent computers from easily searching for it).