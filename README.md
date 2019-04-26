# OOAD-Project
Project members: Matt Niemiec and Mehmet Karaoglu
Project name: Command-line chess

# Project Overview
GUIs are a pain, but chess is cool. So, we decided to get the best of both worlds and allow a user to play chess through a command-line application, entering algebraic notation to signal which move they want to make. Thus, we have a "chess engine," which keeps track of the state of a game and checks for game ends and legal moves. It's recommended that the user follow along on a physical board.

# Files
Because we worked with Java, the files follow the class names, as seen in the UML diagram. We have one package, the "Pieces" package. This helps keep all of the pieces, as well as the classes they work with, separate. The rest of the classes are simply in the main folder. A brief description of major files can be found below:
- Main: Creates a GameManager and starts the program. 2 lines
- GameManager: Doesn't know details about the game, but ties together the MVC. Limited in scope to reduce central logic
- UserInputHandler: Gets and gives input to/from the user. The view in MVC
- DatabaseHandler: Handles all database stuff. The model in MVC
- Game: An instance represents a literal game of chess. Manages everything about the state of the game
- Logic: Delegates some of the tedious logic from Game
- Piece: The abstract superclass of all the pieces. Each instance represents a piece on the board
- MovingService: The abstract superclass of piece implementations (Wrap*.java
- pieceFactory: Generates and returns pieces in an appropriate manner
- Move: Holds data related to a player/piece move. Relied on to pass lots of data around

# Setup
Our project is a Java 8 application, and therefore is run like many Java8 applications.
You can optionally install a MySQL Driver for your computer if you wish to use the database functionality. The following links should aid in this: http://www.ntu.edu.sg/home/ehchua/programming/java/jdbc_basic.html  https://www.makeuseof.com/tag/connect-mysql-database-java/
However, use of MySQL is not required to run the application since it is designed to work both with and without a database.
The main() function is intuitively placed in the Main class in the Main folder, so please run this file

Thank you, and we hope you enjoy our project!
Matt and Mehmet
