import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//Sudoku is 9x9, size 81
public class Grid {
    private static final int size=81;
    private static HashMap<String,Square> squareLookup = new HashMap<>(size, 1); //never expand, constant space
    private static Square[] arrayRepresentation = new Square[size];

    public static void addSquare(Square square)
    {
        squareLookup.put(Integer.toString(square.getRow())+Integer.toString(square.getCol()), square);
    }

    //Returns value at a given location, -1 if location out of bounds
    public static int getValueAt(int row, int col)
    {
        if(squareLookup.containsKey(Integer.toString(row)+Integer.toString(col)))
        {
            return squareLookup.get(Integer.toString(row)+Integer.toString(col)).getValue();
        }
        return -1;
    }

    public static void initialUpdate()
    {
        for(int row=1; row<=9; row++)
        {
            for (int col=1; col<=9; col++)
            {
                if(squareLookup.get(Integer.toString(row) + Integer.toString(col)).getGiven()) {
                    addUpdate(squareLookup.get(Integer.toString(row) + Integer.toString(col)), true);
                }
            }
        }
    }

    //call this with true when a square has been updated with a value
    // or false when value is removed via backtracking
    public static void addUpdate(Square square, boolean add)
    {
        if(square.getValue()!=0)
        {
            updateRow(square.getRow(), square.getValue(), add);
            updateCol(square.getCol(),square.getValue(),add);
            updateBox(square.getRow(),square.getCol(),square.getValue(),add);
        }
        else
        {
            System.err.println("Updated out of order");
        }
    }

    // update col with add/removal of value
    // if add decrease possibilities and affected
    // if remove add back the value and increase the affected
    private static void updateBox(int row, int col, int value, boolean add) {
        int rowStart = ((row - 1) / 3) * 3 + 1;
        int colStart = ((col - 1) / 3) * 3 + 1;

        for (int i = rowStart; i < rowStart + 3; i++) {
            for (int j = colStart; j < colStart + 3; j++) {
                if (add) {
                    squareLookup.get(Integer.toString(i) + Integer.toString(j)).removePossibility(value);
                } else squareLookup.get(Integer.toString(i) + Integer.toString(j)).addPossibility(value);
            }
        }
    }

    // update col with add/removal of value
    // if add decrease possibilities and affected
    // if remove add back the value and increase the affected
    private static void updateCol(int col, int value, boolean add) {
        for (int row = 1; row <= 9; row++) {
            if (add) {
                squareLookup.get(Integer.toString(row) + Integer.toString(col)).removePossibility(value);
            } else squareLookup.get(Integer.toString(row) + Integer.toString(col)).addPossibility(value);
        }
    }

    //update row with add/removal of value
    // if add decrease possibilities and affected
    // if remove add back the value and increase the affected
    private static void updateRow(int row, int value, boolean add) {
        for (int col = 1; col <= 9; col++) {
            if (add) {
                squareLookup.get(Integer.toString(row) + Integer.toString(col)).removePossibility(value);
            } else squareLookup.get(Integer.toString(row) + Integer.toString(col)).addPossibility(value);
        }
    }

    public static void printPuzzle()
    {
        for(int row=1; row<=9; row++)
        {
            for (int col=1; col<=9; col++)
            {
                System.out.print(squareLookup.get(Integer.toString(row) + Integer.toString(col)).getValue());
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }
    public static void printMRV()
    {
        for(int row=1; row<=9; row++)
        {
            for (int col=1; col<=9; col++)
            {
                System.out.print(squareLookup.get(Integer.toString(row) + Integer.toString(col)).getNumPossibilities());
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printDegree()
    {
        for(int row=1; row<=9; row++)
        {
            for (int col=1; col<=9; col++)
            {
                System.out.print(squareLookup.get(Integer.toString(row) + Integer.toString(col)).getAffectedSquares());
                System.out.print(" ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printArrayConfiguration()
    {
        updateArray();
        for(int i=0; i<arrayRepresentation.length; i++)
        {
            System.out.println("(" + arrayRepresentation[i].getRow() + "," + arrayRepresentation[i].getCol() + "): "
                    + "Num Choices = " + arrayRepresentation[i].getNumPossibilities() + ", Num Affect = " +
                    arrayRepresentation[i].getAffectedSquares());
        }
    }

    private static void updateArray()
    {
        Iterator it = squareLookup.entrySet().iterator();
        int index=0;
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            arrayRepresentation[index] = (Square)pair.getValue();
            it.remove(); // avoids a ConcurrentModificationException
            index++;
        }
        Arrays.sort(arrayRepresentation);
    }
    public static Square[] getArrayRepresentation()
    {
        updateArray();
        return arrayRepresentation;
    }

    public static HashMap<String, Square> getSquareLookup() {
        return squareLookup;
    }
}
