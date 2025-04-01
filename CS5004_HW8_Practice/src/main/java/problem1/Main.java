package problem1;

import problem1.cli.ArgParser;
import problem1.cli.Executor;

/**
 * Class represents entry point for application
 */
public class Main {

  /**
   * Main method
   * @param args
   */
  public static void main(String[] args) {

    Executor executor = new Executor();
    ArgParser parser = new ArgParser();

    if(!parser.parse(args)) return; // Parse and check for success. End if fail.

    //Generate emails
    if(parser.isGeneratingEmailTemplate()){
      System.out.println("\n\nGenerating email template...");

      executor.generateABatchOfDocuments(
          parser.getEmailTemplateFileName(),
          parser.getOutputDir(),
          "Email",
          parser.getCsvFile()
      );

    }

    // Generate Letters
    if(parser.isGeneratingLetterTemplate()){
      System.out.println("\n\nGenerating letter template...");

      executor.generateABatchOfDocuments(
          parser.getLetterTemplateFileName(),
          parser.getOutputDir(),
          "Letter",
          parser.getCsvFile()
      );
    }
  }

}


