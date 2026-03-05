import java.util.Scanner;
public class Board {

    private Card[][] grid;
    private LineConstraint[] rowConstraints;
    private LineConstraint[] columnConstraints;
    private Scanner scan = new Scanner(System.in);

    public Board() {

        grid = new Card[5][5];

        for (int r = 0; r < 5; r++) {
            for (int c = 0; c < 5; c++) {
                grid[r][c] = new Card();
            }
        }

        rowConstraints = new LineConstraint[5];
        columnConstraints = new LineConstraint[5];
        for (int i = 0; i < 5; i++) {
            LineConstraint row = new LineConstraint();
            LineConstraint col = new LineConstraint();
            rowConstraints[i] = row;
            columnConstraints[i] = col;
        }
    }

    public void setRowConstraints(){
        System.out.println("Please input row constraints 1 through 5");
        for (int i = 0; i < 5; i++) {
            System.out.println("Point Total:");
            int pointCount = scan.nextInt();
            System.out.println("bomb Count:");
            int bombCount = scan.nextInt();
            rowConstraints[i].setBombCount(bombCount);
            rowConstraints[i].setPointTotal(pointCount);
        }
    }

    public void setColumnConstraints(){
        System.out.println("Please input column constraints 1 through 5");
        for (int i = 0; i < 5; i++) {
            System.out.println("Point Total:");
            int pointCount = scan.nextInt();
            System.out.println("bomb Count:");
            int bombCount = scan.nextInt();
            columnConstraints[i].setBombCount(bombCount);
            columnConstraints[i].setPointTotal(pointCount);
        }
    }


    public void printBoard(){
        System.out.println("");
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.print(rowConstraints[i] + " ");
            System.out.println("");
        }
        for (int i = 0; i < 5; i++) {
            System.out.print(columnConstraints[i] + " ");
        }
    }

}