CS1006 P2: John Conway's Game Of Life

There are two ways to run the program

1. In terminal, change into the src directory and then use the following commands:
    javac -cp .:../lib/* *.java
    java -cp .:../lib/* Main

2. Running on linux is preferable. On linux, going to the CS1006-P2 directory and running the command 'bash ./lib/build-run-all' will run the program

Once the program's started, there will be a textfield representing the grid size you can enter any non zero, positive integer to change the grid size.
When ready click Start Game to initialise the grid and start the game.

Once the game has started all the cells will be initialised as dead
you click on the cells on the grid to change their state.
    Black is alive
    White is dead

There is a run button which will run the simulation infinitly till it has been clicked again
There is a clear button which will kill all the cells
There is a new generation button which will run the simulation one step at a time with each click
There are two save buttons one will save a .gol file and the other will save a .json file
There is a load button to load a previous .gol or .json save
There is a regenerate grid button which will go back to the main menu so grid size can be changed
There are three text fields in which any integers between 0 and 8 can be entered to alter the default game rules. However, you must remeber to hit enter after changing these fields.
The final component is a slider, which allows the user to change the speed at which the simulation runs infinitely.