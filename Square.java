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
    private int bypass=0; //change this if want to set sqare without changing possibilities

    public Square(int row, int col, int value)
    {
        if(row<1 || row>9 || col<1 || col>9 || value<0 || value>9)
        {
            System.err.println("Incorrect initialization: " + row + ", " + col);
        }
        this.row=row;
        this.col=col;
        this.value=0;
        given = (value!=0);
        if(given)
        {
            possibilities.clear();
            possibilities.add(value);
            numPossibilities=possibilities.size();
            //Grid.queuePush(this);
        }
    }

    public void assign()
    {
        if(possibilities.isEmpty()) System.out.print("Error in logic/backtracking needed");
        else if(bypass!=0) value=bypass; //assign to bypass if not 0
        else value=possibilities.get(0); //assign to first element
    }

    //when a square is set to a value and affects this square
    public void removePossibility(int value)
    {
        int tempPossibility = numPossibilities;
        possibilities.remove((Integer)value);
        numPossibilities = possibilities.size();
        affectedSquares--;
        //only add if isn't already added
        //if(numPossibilities==1 && tempPossibility!=numPossibilities ) Grid.queuePush(this);
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

    public int getBypass() {
        return bypass;
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
        //if already has a value, don't want to consider
        if(this.value!=0 && ((Square)o).value==0) return 1;
        else if(this.value==0 && ((Square)o).value!=0) return -1;
        else //both have a value or both don't have a value
        {
            if (this.bypass != 0 && ((Square) o).bypass == 0) return -1;
            else if (this.bypass == 0 && ((Square) o).bypass != 0) return 1;
            else //bypass =
            {
                if (this.numPossibilities < ((Square) o).numPossibilities) return -1;
                else if (this.numPossibilities > ((Square) o).numPossibilities) return 1;
                else //MRV =
                {
                    if (this.affectedSquares < ((Square) o).affectedSquares) return 1;
                    else if (this.affectedSquares > ((Square) o).affectedSquares) return -1;
                }
            }
        }
        //all heuristics =
        return 0;
    }
    @Override
    public String toString()
    {
        return ("(" + row + "," + col + "): Num Choices = " + numPossibilities + ", Num Affect = " + affectedSquares
        + ", Bypass = " + bypass);
    }
}
