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
            //in here the solver should first iterate through all cards and check what states to remove
            board.solve();
            board.printBoard();
            System.out.print("\n\nChoose option - \n1.Flip Card\n2.End Game\n");
            gameChoice = scan.nextInt();




            switch(gameChoice){
                case 1:
                    System.out.println("Please input card to flip");
                    System.out.println("ROWS and COLS are from 1 to 5");
                    System.out.println("Column:");
                    int inputRow = scan.nextInt();
                    System.out.println("Row:");
                    int inputCol = scan.nextInt();
                    System.out.println("Item under Card: (0[BOMB],1,2,3)");
                    int value = scan.nextInt();

                    if(value == 0){
                        System.out.print("GAME OVER");
                        playing = false;
                    }else{
                        board.setCard(value, inputRow-1,inputCol-1);
                    }
                    break;
                case 2:
                    playing = false;
                    break;
            }
        }

    }
}
