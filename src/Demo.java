import java.util.Scanner;

public class Demo {
    public Scanner scan = new Scanner(System.in);
    public static void main(String[] args) {
        Board board = new Board();
        board.setRowConstraints();
        board.setColumnConstraints();


        Scanner scan = new Scanner(System.in);
        boolean playing = true;
        int gameChoice;
        while(playing){
            board.printBoard();
            System.out.print("\n\nChoose option - \n1.Flip Card\n2.End Game\n");
            gameChoice = scan.nextInt();

            switch(gameChoice){
                case 1:
                    break;
                case 2:
                    playing = false;
                    break;
            }
        }

    }
}
