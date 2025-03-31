package problem1;

import problem1.cli.ArgParser;

public class Main {

  public static void main(String[] args) {

    ArgParser parser = new ArgParser();
    if(!parser.parse(args)) return;

    if(parser.isGeneratingEmailTemplate()){
      System.out.println("Generating email template...");
      String emailTemplate = parser.getEmailTemplateFileName();
    }
    if(parser.isGeneratingLetterTemplate()){
      System.out.println("Generating letter template...");
    }
  }
}
