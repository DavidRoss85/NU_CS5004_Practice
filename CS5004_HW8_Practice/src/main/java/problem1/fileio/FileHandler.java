package problem1.fileio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class FileHandler {

  public FileHandler() {
  }

  /**
   * Read string contents from a file
   * @param fileName
   * @return
   */
  public String readFile(String fileName) {
    StringBuilder fileContent = new StringBuilder();
    BufferedReader inputFile = null;

    try {
      inputFile = new BufferedReader(new FileReader(fileName));

      String line;
      line = inputFile.readLine();
      fileContent.append(line);

      while ((line = inputFile.readLine()) != null) {
        fileContent.append("\n");
        fileContent.append(line);
      }

    } catch (FileNotFoundException ex) {
      throw new RuntimeException(ex);
    }catch(IOException e){
      System.out.println(e);
    }finally{
      if(inputFile != null){
        try{
          inputFile.close();
        }catch(IOException e){
          System.out.println(e);
        }
      }
    }

    return fileContent.toString();
  }

  /**
   * Write string contents to a file
   * @param fileName
   * @param content
   */
  public void writeFile(String fileName, String content) {
    BufferedWriter outputFile = null;

    try{
      outputFile = new BufferedWriter(new FileWriter(fileName));
      outputFile.write(content);

    }catch(IOException e){
      System.out.println(e);
    } finally {
      if(outputFile != null){
        try{
          outputFile.close();
        }catch(IOException e){
          System.out.println(e);
        }
      }
    }
  }
}
