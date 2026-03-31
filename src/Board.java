import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.util.EnumSet;

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
        CardState[] allStates = CardState.values(); // BOMB, ONE, TWO, THREE

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                Card c = grid[i][j];
                System.out.print("[");
                for (CardState state : allStates) {
                    if (c.getPossibleStates().contains(state)) {
                        System.out.print(cardStateToInt(state));
                    } else {
                        System.out.print("-");
                    }
                }
                System.out.print("]");
                System.out.print("          ");
            }
            System.out.print("[PTotal:"+rowConstraints[i].getPointTotal() + "BTotal:" + rowConstraints[i].getBombCount()+"]");
            System.out.println();

        }
        for (int i = 0; i < 5; i++) {
            System.out.print("[POINTS:"+columnConstraints[i].getPointTotal() + "BOMB:" + columnConstraints[i].getBombCount()+"]");
        }

    }

    public void solve() {
        //for each row first
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 5; i++) {
                reduceRow(i);
            }
            for (int i = 0; i < 5; i++) {
                reduceCol(i);
            }
        }
    }

    public void reduceCol(int index){
        System.out.println("");
        int bomb = columnConstraints[index].getBombCount();
        int totalPoints = columnConstraints[index].getPointTotal();

        List<List<Integer>> combos = new ArrayList<>();
        int[] fixed = new int[5];
        for (int i = 0; i < 5; i++) {
            if (grid[i][index].isSolved()) {
                // card is already solved, store its value (0 = BOMB, 1-3)
                fixed[i] = cardStateToInt(grid[i][index].getPossibleStates().iterator().next());
            } else {
                fixed[i] = -1; // not solved
            }
        }

        generateCombos(combos, new ArrayList<>(), 0, bomb, totalPoints,fixed);

        for (int i = 0; i < 5; i++) {
            //for the current card we're going to find out what states are possible and put them into a set of "allowed" states
            EnumSet<CardState> allowed = EnumSet.noneOf(CardState.class);

            //to do this we will go through all the generated combos and simply add all the states that appeared for
            //this specific card position i.e if it was a bomb a 1,2,3 and such.
            for (List<Integer> combo : combos) {
                allowed.add(intToCardState(combo.get(i)));
            }

            //retain the states that are in "allowed", that is if .getPossibleStates() return {BOMB,1,2,3} and
            // allowed has {BOMB,1} then only {BOMB,1} are retained
            grid[i][index].getPossibleStates().retainAll(allowed);
        }
    }
    public void reduceRow(int index){
        System.out.println("");
        int bomb = rowConstraints[index].getBombCount();
        int totalPoints = rowConstraints[index].getPointTotal();

        List<List<Integer>> combos = new ArrayList<>();

        int[] fixed = new int[5];
        for (int i = 0; i < 5; i++) {
            if (grid[index][i].isSolved()) {
                // card is already solved, store its value (0 = BOMB, 1-3)
                fixed[i] = cardStateToInt(grid[index][i].getPossibleStates().iterator().next());
            } else {
                fixed[i] = -1; // not solved
            }
        }


        generateCombos(combos, new ArrayList<>(), 0, bomb, totalPoints,fixed);


        for (int i = 0; i < 5; i++) {
            //for the current card we're going to find out what states are possible and put them into a set of "allowed" states
            EnumSet<CardState> allowed = EnumSet.noneOf(CardState.class);

            //to do this we will go through all the generated combos and simply add all the states that appeared for
            //this specific card position i.e if it was a bomb a 1,2,3 and such.
            for (List<Integer> combo : combos) {
                allowed.add(intToCardState(combo.get(i)));
            }

            //retain the states that are in "allowed", that is if .getPossibleStates() return {BOMB,1,2,3} and
            // allowed has {BOMB,1} then only {BOMB,1} are retained
            grid[index][i].getPossibleStates().retainAll(allowed);
        }


    }

    public void generateCombos(List<List<Integer>> results, List<Integer> current, int index, int bombsLeft, int sumLeft,int[] fixed) {
        if (index == 5) {
            if (bombsLeft == 0 && sumLeft == 0) results.add(new ArrayList<>(current));
            return;
        }

        // if this position is fixed (flipped card), we have to keep that in mind since we dont want to recalculate useless number combos
        if (fixed[index] != -1) {
            int val = fixed[index];
            if (val == 0 && bombsLeft > 0) {
                current.add(0);
                generateCombos(results, current, index + 1, bombsLeft - 1, sumLeft, fixed);
                current.remove(current.size() - 1);
            } else if (val != 0 && sumLeft - val >= 0) {
                current.add(val);
                generateCombos(results, current, index + 1, bombsLeft, sumLeft - val, fixed);
                current.remove(current.size() - 1);
            }
            return;
        }

        // Try bomb
        if (bombsLeft > 0) {
            current.add(0);
            generateCombos(results, current, index + 1, bombsLeft - 1, sumLeft,fixed);
            current.remove(current.size() - 1);
        }

        // Try 1, 2, 3
        for (int val = 1; val <= 3; val++) {
            if (sumLeft - val >= 0) {
                current.add(val);
                generateCombos(results, current, index + 1, bombsLeft, sumLeft - val,fixed);
                current.remove(current.size() - 1);
            }
        }
    }

    public CardState intToCardState(int val) {
        switch(val) {
            case 0: return CardState.BOMB;
            case 1: return CardState.ONE;
            case 2: return CardState.TWO;
            case 3: return CardState.THREE;
            default: throw new IllegalArgumentException("Invalid value: " + val);
        }
    }

    public int cardStateToInt(CardState val){
        switch(val) {
            case BOMB: return 0;
            case ONE: return 1;
            case TWO: return 2;
            case THREE: return 3;
            default: throw new IllegalArgumentException("Invalid value: " + val);
        }
    }

    public void setCard(int value, int row, int col){
        grid[col][row].setState(intToCardState(value));
    }
}