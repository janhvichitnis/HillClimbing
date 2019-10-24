public class QueenState {
    private int row;
    private int column;

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public QueenState(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public void moveQueen () {
        row++;
    }

    public boolean checkConflict(QueenState q){
        //  Check rows and columns
        if(row == q.getRow() || column == q.getColumn())
            return true;
            //  Check diagonals
        else if(Math.abs(column-q.getColumn()) == Math.abs(row-q.getRow()))
            return true;
        return false;
    }
    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

}
