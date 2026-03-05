public class LineConstraint {

    private int bombCount;
    private int pointTotal;

    public LineConstraint() {
        this.bombCount = 0;
        this.pointTotal = 0;
    }

    public int getBombCount() {
        return bombCount;
    }

    public int getPointTotal() {
        return pointTotal;
    }

    public void setBombCount(int bombCount) {
        this.bombCount = bombCount;
    }

    public void setPointTotal(int pointTotal) {
        this.pointTotal = pointTotal;
    }

    @Override
    public String toString() {
        return "[ Points: " +pointTotal + " Bombs: " + bombCount + " ]";
    }
}