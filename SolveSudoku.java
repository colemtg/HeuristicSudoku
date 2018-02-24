import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Driver;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

public class SolveSudoku {
    public static void main (String args[]) throws IOException {

        //URL path = Driver.class.getResource("Puzzles\\Easy1.txt"); //Change to new
        //FileReader file =  new FileReader(path.getFile());
        FileReader file =  new FileReader("C:\\Users\\colemtg\\IdeaProjects\\HeuristicSudoku\\src\\Puzzles\\Easy1.txt");
        BufferedReader buffer = new BufferedReader(file);
        String tempLine;

        int row=1;
        while ((tempLine=buffer.readLine())!=null)
        {

            for(int col=1; col<=9; col++)
            {
                Grid.addSquare(new Square(row,col, tempLine.charAt((col-1)*2)-'0'));
            }
            row++;
        }
        Grid.printPuzzle();
        Grid.printMRV();
        //Grid.printDegree();
        //Grid.printArrayConfiguration();
        while(!Grid.getQueue().isEmpty())
        {
            Square pop = Grid.getQueue().poll();
            System.out.println(pop.getRow() + "," + pop.getCol());
            if (pop.getNumPossibilities()!=1) break;
            pop.assign();
            Grid.addUpdate(pop, true);
            Grid.printPuzzle();
            Grid.printMRV();
        }
        Grid.printPuzzle();

    }
}
