import java.util.EnumSet;

public class Card {

    private EnumSet<CardState> possibleStates;

    public Card() {
        possibleStates = EnumSet.allOf(CardState.class);
    }

    public EnumSet<CardState> getPossibleStates() {
        return possibleStates;
    }

    public void removeState(CardState state) {
        possibleStates.remove(state);
    }

    public void setState(CardState state) {
        possibleStates.clear();
        possibleStates.add(state);
    }

    public boolean isSolved() {
        return possibleStates.size() == 1;
    }

    public boolean canBe(CardState state) {
        return possibleStates.contains(state);
    }

    @Override
    public String toString() {
        return possibleStates.toString();
    }
}