package src.view;


import src.model.Maze;
import src.model.Position;
import src.model.Question;
import src.model.Room;

public interface GameView {
    public void displayMaze(Maze theMaze, Position theCurrentPosition);

    public void displayRoom(Room theRoom);

    public void displayQuestion(Question theQuestion);

    public void displayMessage(String theMessage);

    public void displayGameOver(boolean isWon);

    public String getAnswer();
}
