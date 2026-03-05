import java.util.Scanner;

public class Demo {
    public Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        Board board = new Board();
        board.setRowConstraints();
        board.setColumnConstraints();
        board.printBoard();

    }
}
