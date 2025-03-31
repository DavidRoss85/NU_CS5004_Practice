package problem1.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ArgParser {

  private static final String EMAIL_TAG = "--email";
  private static final String EMAIL_TEMPLATE_TAG = "--email-template";
  private static final String LETTER_TAG = "--letter";
  private static final String LETTER_TEMPLATE_TAG = "--letter-template";
  private static final String OUTPUT_DIR_TAG = "--output-dir";
  private static final String CSV_FILE_TAG = "--csv-file";

  private static final String REGEX_VALID_FILE = "(([A-Za-z]:)|(\\.|\\.\\.))((\\/|\\\\)\\w+)+\\.\\w+";
  private static final String REGEX_VALID_DIRECTORY = "(([A-Za-z]:)|(\\.|\\.\\.))((\\/|\\\\)\\w+)+(\\\\|\\/)";

  private Boolean successfulParse; // Keeps track if last parse was successful

  private HashMap<String,Boolean> argCheck = new HashMap<>(); //Keeps track of which arguments were input
  private HashMap<String, ArrayList<String>> argRequirements = new HashMap<>(); //List of what arguments must be present for others

  private HashMap<String,String> argsThatRequireAFileName = new HashMap<>(); //Stores the filename for the specified arg
  private HashMap<String,String> argsThatRequireAPath = new HashMap<>(); //stores the path for the specified arg

  /**
   * Constructor for ArgParser
   */
  public ArgParser() {
    successfulParse = false;
  }

  /**
   * Checks if last parse was successful. In this case it is ok to begin execution.
   * @return parse success as {@code Boolean}
   */
  public Boolean isParseSuccessful() {
    return successfulParse;
  }

  /**
   * Resets this object to its original state
   */
  private void resetValues() {
    this.successfulParse = false;

    this.argCheck.clear();
    argCheck.put(EMAIL_TAG,false);
    argCheck.put(EMAIL_TEMPLATE_TAG,false);
    argCheck.put(LETTER_TAG,false);
    argCheck.put(LETTER_TEMPLATE_TAG,false);
    argCheck.put(OUTPUT_DIR_TAG,false);
    argCheck.put(CSV_FILE_TAG,false);

    argsThatRequireAFileName.clear();
    argsThatRequireAFileName.put(EMAIL_TEMPLATE_TAG,null);
    argsThatRequireAFileName.put(LETTER_TEMPLATE_TAG,null);
    argsThatRequireAFileName.put(CSV_FILE_TAG,null);

    argsThatRequireAPath.clear();
    argsThatRequireAPath.put(OUTPUT_DIR_TAG,null);

    argRequirements.clear();
    argRequirements.put(EMAIL_TAG,new ArrayList<>(Arrays.asList(EMAIL_TEMPLATE_TAG,OUTPUT_DIR_TAG,CSV_FILE_TAG)));
    argRequirements.put(LETTER_TAG,new ArrayList<>(Arrays.asList(LETTER_TEMPLATE_TAG,OUTPUT_DIR_TAG,CSV_FILE_TAG)));
  }

  /**
   * Parse the provided String array and determine whether arguments are valid.
   * Parsed values are retained in the object after execution.
   * @param args arguments as {@code String[]}
   * @return success as {@code Boolean}
   */
  public Boolean parse(String[] args) {
    resetValues();

    if (args.length == 0) {
      this.successfulParse = false;
      return this.successfulParse;
    }

    String outputMessage = "";
    String lastArg = "";

    for(String arg : args) {
      if(requiresFileName(lastArg)) {assignFileName(lastArg,arg);}
      if(requiresPath(lastArg)) {assignPath(lastArg,arg);}

      //Checks for unrecognized arguments:
      if(!requiresFileName(lastArg) && !requiresPath(lastArg) & !argCheck.containsKey(arg)) {
        outputMessage += "Argument \"" + arg + "\" not recognised.\n";
      }

      updateArgumentExistence(arg);
      lastArg = arg;
    }

    // For checking... delete later
    for(String arg : argCheck.keySet()) {
      System.out.println(arg + ": " + argCheck.get(arg));
    }
    System.out.println("Values:");
    for(String arg : argsThatRequireAFileName.keySet()) {
      System.out.println(arg + ": " + argsThatRequireAFileName.get(arg));
    }

    outputMessage += checkForErrors();

    if(outputMessage.isEmpty()){
      successfulParse = true;
      System.out.println("Successfully parsed arguments!");
    }else {
      successfulParse = false;
      System.out.println("\nErrors were encountered when attempting to parse arguments:\n" + outputMessage);
    }
    return successfulParse;
  }

  /**
   * Helper method Check entered values for errors
   * @return a {@code String} containing any errors encountered
   */
  private String checkForErrors(){
    String outputMessage = "";

    outputMessage += verifyRequiredArgs();
    outputMessage += validatePathNames();

    return outputMessage;
  }

  /**
   * Helper method. Verifies if complimentary required arguments are provided
   * @return a {@code String} containing any errors encountered
   */
  private String verifyRequiredArgs(){
    String outputMessage = "";

    for(String arg : argRequirements.keySet()) {
      if(argCheck.get(arg)) {
        for(String requirement : argRequirements.get(arg)) {
          if(!argCheck.get(requirement)) {
            outputMessage += "A \"" + arg + "\" argument was provided, but no \"" + requirement + "\" requirement.\n";
          }
        }
      }
    }
    return outputMessage;
  }

  /**
   * Helper method. Validates any paths or filenames provided as arguments
   * @return a {@code String} containing any errors encountered
   */
  private String validatePathNames(){
    String outputMessage = "";
    for(String arg : argCheck.keySet()) {
      if(argCheck.get(arg)) {
        if(argsThatRequireAFileName.containsKey(arg) && notValidFileName(argsThatRequireAFileName.get(arg))) {
          outputMessage +=  ("\"" + argsThatRequireAFileName.get(arg) + "\"" + " is not a valid filename for the " + "\"" +  arg + "\"" + " argument.\n");
        }
        if(argsThatRequireAPath.containsKey(arg) && notValidPath(argsThatRequireAPath.get(arg))) {
          outputMessage +=  ("\"" + argsThatRequireAPath.get(arg) + "\"" + " is not a valid path for the " + "\"" +  arg + "\"" + " argument.\n");
        }
      }
    }

    return outputMessage;
  }

  /**
   * Checks if an argument requires a fileName
   * @param arg
   * @return
   */
  private boolean requiresFileName(String arg) {
    return argsThatRequireAFileName.containsKey(arg);
  }

  private boolean requiresPath(String arg) {
    return argsThatRequireAPath.containsKey(arg);
  }

  private void updateArgumentExistence(String arg) {
    if(argCheck.containsKey(arg)) {
      argCheck.put(arg,true);
    }
  }

  private void assignFileName(String arg, String path) {
    if(argsThatRequireAFileName.containsKey(arg)) {
      argsThatRequireAFileName.put(arg,path);
    }
  }

  private void assignPath(String arg, String path) {
    if(argsThatRequireAPath.containsKey(arg)) {
      argsThatRequireAPath.put(arg,path);
    }
  }


  private boolean notValidFileName(String path) {
    if (path == null || isAnArgument(path)) return true;
    return !Pattern.compile(REGEX_VALID_FILE).matcher(path).matches();
  }

  private boolean notValidPath(String path) {
    if (path == null || isAnArgument(path)) return true;
    return !Pattern.compile(REGEX_VALID_DIRECTORY).matcher(path).matches();
  }

  private boolean isAnArgument(String value) {
    return argCheck.containsKey(value);
  }
}
