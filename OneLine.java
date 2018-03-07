import java.io.*;

//only works with java or any language where spacing is not needed and comments are // and /* */
// Takes a file and overwrites the file with everything on one line, with the code still able to compile
public class OneLine {
    public static void main (String args[]) throws IOException {

        String fileName = "C:\\Users\\colemtg\\IdeaProjects\\Testing\\ATest\\src\\Qs.java";
        FileReader file = new FileReader(fileName);
        BufferedReader buffer = new BufferedReader(file);
        String tempLine;
        String output="";
        while ((tempLine=buffer.readLine())!=null)
        {
            if(tempLine.contains("//"))
            {
                tempLine=tempLine.replace("//", "/*");
                output=output+tempLine+"*/";
            }
            else output = output + tempLine.replace("    ", "") + " ";
        }
        file.close();
        BufferedWriter out = new BufferedWriter(new FileWriter(fileName, false));
        out.write(output);
        out.close();


    }
}
