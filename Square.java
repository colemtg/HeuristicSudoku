import java.util.ArrayList;
import java.util.Arrays;

//Variable
//Sudoku is 9x9
public class Square implements Comparable{
    private int value;
    private ArrayList<Integer> possibilities = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9)); //Domain
    private final boolean given; //provide value
    private int numPossibilities=possibilities.size(); //MRV
    private int affectedSquares=24; //Degree: starts at 24 (8*3)
    private final int row; //1-9
    private final int col; //1-9

    public Square(int row, int col, int value)
    {
        if(row<1 || row>9 || col<1 || col>9 || value<0 || value>9)
        {
            System.err.println("Incorrect initialization: " + row + ", " + col);
        }
        this.row=row;
        this.col=col;
        this.value=value;
        given = (value!=0);
    }

    //when a square is set to a value and affects this square
    public void removePossibility(int value)
    {
        possibilities.remove((Integer)value);
        numPossibilities = possibilities.size();
        affectedSquares--;
    }

    //when a square loses its value via backtracking
    public void addPossibility(int value)
    {
        possibilities.add(value);
        numPossibilities = possibilities.size();
        affectedSquares++;
    }

    public boolean getGiven()
    {
        return given;
    }

    public int getValue() {
        return value;
    }

    public int getNumPossibilities() {
        return numPossibilities;
    }

    public int getAffectedSquares() {
        return affectedSquares;
    }

    public ArrayList<Integer> getPossibilities() {
        return possibilities;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public int hashCode() {
        return (Integer.toString(row) + Integer.toString(col)).hashCode();
    }
    @Override
    public boolean equals(Object square)
    {
        return square.getClass()==this.getClass()
                &&((Square) square).getCol() == this.col && ((Square) square).getRow() == this.row;

    }

    // Want the fewest possibilities, if tie want most affected
    @Override
    public int compareTo(Object o) {
        if(this.numPossibilities<((Square) o).numPossibilities) return -1;
        else if (this.numPossibilities>((Square) o).numPossibilities) return 1;
        else
        {
            if(this.affectedSquares<((Square) o).affectedSquares) return 1;
            else if (this.affectedSquares>((Square) o).affectedSquares) return -1;
        }
        return 0;
    }
}
