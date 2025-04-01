package problem1.cli;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ArgParser {

  private static final String REGEX_VALID_FILE = "(([A-Za-z]:)|(\\.|\\.\\.))((\\/|\\\\)\\w+)+\\.\\w+";
  private static final String REGEX_VALID_DIRECTORY = "(([A-Za-z]:)|(\\.|\\.\\.))((\\/|\\\\)\\w+)+(\\\\|\\/)";

  private static final String HELP_TAG = "--help";
  private static final String CAPTURE_INPUT_TAG = "--show-captured-input";
  private static final String EMAIL_TAG = "--email";
  private static final String EMAIL_TEMPLATE_TAG = "--email-template";
  private static final String LETTER_TAG = "--letter";
  private static final String LETTER_TEMPLATE_TAG = "--letter-template";
  private static final String OUTPUT_DIR_TAG = "--output-dir";
  private static final String CSV_FILE_TAG = "--csv-file";

  //Help Texts
  private static HashMap<String, String> helpTextCommands;
  static {
    helpTextCommands = new HashMap<>() {{
      put("FIRST_MESSAGE",
          "This program processes document templates by inserting personalized details from a csv file. \n"
          + "For each record, it automatically fills in designated placeholders with names and other relevant information, \n"
          + "creating fully customized documents efficiently.");
      put(CSV_FILE_TAG, "Designates the location of the CSV file containing records. Requires a Filename argument immediately following.");
      put(EMAIL_TAG, "Indicates that email documents will be generated. When " + EMAIL_TAG + " is used, an " + EMAIL_TEMPLATE_TAG + " must be used as well.");
      put(EMAIL_TEMPLATE_TAG, "Used to specify the location of the email template. Requires a Filename argument immediately following.");
      put(HELP_TAG, "Prints this help message.");
      put(LETTER_TAG, "Indicates that letter documents will be generated. When " + LETTER_TAG + " is used, a " + LETTER_TEMPLATE_TAG + " must be used as well.");
      put(LETTER_TEMPLATE_TAG, "Used to specify the location of the letter template. Requires a Filename argument immediately following.");
      put(OUTPUT_DIR_TAG, "The output directory used to write the generated documents. Requires a Filename argument immediately following.");
      put("FINAL_MESSAGE", "\n ** Please note that " +EMAIL_TAG + " and " + LETTER_TAG + " require a " + OUTPUT_DIR_TAG + " directory specified. **");
    }};
  }

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
   * Returns true if email argument was specified
   * @return {@code Boolean}
   */
  public Boolean isGeneratingEmailTemplate() {
    return argCheck.get(EMAIL_TAG);
  }

  /**
   * Returns true if letter argument was specified
   * @return {@code Boolean}
   */
  public Boolean isGeneratingLetterTemplate() {
    return argCheck.get(LETTER_TAG);
  }

  /**
   * Returns the filename for the email template if provided
   * @return template file name as {@code String}
   */
  public String getEmailTemplateFileName(){
    return argsThatRequireAFileName.get(EMAIL_TEMPLATE_TAG);
  }

  /**
   * Returns the filename for the letter template if provided
   * @return template file name as {@code String}
   */
  public String getLetterTemplateFileName(){
    return argsThatRequireAFileName.get(LETTER_TEMPLATE_TAG);
  }

  /**
   * Returns the filename for the csv file containing the records if provided
   * @return file name as {@code String}
   */
  public String getCsvFile(){
    return argsThatRequireAFileName.get(CSV_FILE_TAG);
  }

  /**
   * Returns the output directory where files will be saved if provided
   * @return output directory as {@code String}
   */
  public String getOutputDir(){
    return argsThatRequireAPath.get(OUTPUT_DIR_TAG);
  }

  /**
   * Helper method. Resets this object to its original state.
   *
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
    argCheck.put(HELP_TAG,false);
    argCheck.put(CAPTURE_INPUT_TAG,false);

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

    //Exit if no args were given.
    if (args.length == 0) {
      this.successfulParse = false;
      return this.successfulParse;

    //Display help text and ignore rest if --help is first argument
    }else if(args[0].equals(HELP_TAG)) {
      displayHelpText();
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
        outputMessage += "Argument \"" + arg + "\" not recognized.\n";
      }

      if(!updateArgumentExistence(arg)){
        outputMessage += "Argument \"" + arg + "\" was provided multiple times.\n";
      }
      lastArg = arg;
    }

    outputMessage += checkForErrors();

    // Display captured input if specified:
    if(argCheck.get(CAPTURE_INPUT_TAG)) {
      showCapturedInput();
    }

    // Assess (Based on generated errors) whether parse was successful. Display messages.
    if(outputMessage.isEmpty()){
      successfulParse = true;
      System.out.println("Successfully parsed arguments!");
    }else {
      successfulParse = false;
      System.out.println("\nErrors were encountered when attempting to parse arguments:\n" + outputMessage);
      System.out.println("For help on using program and viable commands, use --help as first argument.");
    }
    return successfulParse;
  }

  /**
   * Helper method. Displays captured input. Useful for debugging
   */
  private void showCapturedInput(){
    System.out.println("\n\n********** CAPTURED INPUT **********\n");
    for(String arg : argCheck.keySet()) {
      System.out.println(arg + ": " + argCheck.get(arg));
    }
    System.out.println("Values:");
    for(String arg : argsThatRequireAFileName.keySet()) {
      System.out.println(arg + ": " + argsThatRequireAFileName.get(arg));
    }
  }

  /**
   * Helper method. Displays help text.
   */
  private void displayHelpText() {

    // Sort commands alphabetically
    ArrayList<String> alphabeticCommands = new ArrayList<>(argCheck.keySet());
    Collections.sort(alphabeticCommands);

    //Print help text using helpTextCommands hashmap:
    System.out.println("\n\n********** HELP TEXT **********\n");
    System.out.println(helpTextCommands.get("FIRST_MESSAGE"));
    System.out.println("\nValid Tags:");
    for(String tag : alphabeticCommands) {
      System.out.printf("%-20s %s\n", tag, helpTextCommands.get(tag));
    }
    System.out.println(helpTextCommands.get("FINAL_MESSAGE"));
    System.out.println("\n********** END HELP **********\n\n");
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
          argsThatRequireAFileName.put(arg,null);
        }
        if(argsThatRequireAPath.containsKey(arg) && notValidPath(argsThatRequireAPath.get(arg))) {
          outputMessage +=  ("\"" + argsThatRequireAPath.get(arg) + "\"" + " is not a valid path for the " + "\"" +  arg + "\"" + " argument.\n");
          argsThatRequireAPath.put(arg,null);
        }
      }
    }

    return outputMessage;
  }

  /**
   * Helper method. Checks if a specific argument requires a fileName to be specified.
   * @param arg argument to query
   * @return true if arg requires a fileName, false otherwise as {@code boolean}
   */
  private boolean requiresFileName(String arg) {
    return argsThatRequireAFileName.containsKey(arg);
  }

  /**
   * Helper method. Checks if a specific argument requires a path or directory to be specified.
   * @param arg argument to query
   * @return true if arg requires a path, false otherwise as {@code boolean}
   */
  private boolean requiresPath(String arg) {
    return argsThatRequireAPath.containsKey(arg);
  }

  /**
   * Helper method. Updates hashMap to track that a command was given.
   * Ignores if arg does not exist in Map.
   * @param arg argument to track
   * @return false if argument was already given, true otherwise.
   */
  private Boolean updateArgumentExistence(String arg) {
    if(argCheck.containsKey(arg)) {
      if(argCheck.get(arg)) {
        return false;
      }
      argCheck.put(arg,true);
    }
    return true;
  }

  /**
   * Helper method. Some arguments require a filename. If one is provided, saves the provided
   * @param arg argument
   * @param path filename
   */
  private void assignFileName(String arg, String path) {
    if(argsThatRequireAFileName.containsKey(arg)) {
      argsThatRequireAFileName.put(arg,path);
    }
  }

  /**
   * Helper method. Some arguments require a path. If one is provided, saves the provided
   * @param arg argument
   * @param path path
   */
  private void assignPath(String arg, String path) {
    if(argsThatRequireAPath.containsKey(arg)) {
      argsThatRequireAPath.put(arg,path);
    }
  }

  /**
   * Helper method. Uses regex to check if a given string is an invalid file name
   * @param path file name to evaluate
   * @return true if not valid, false otherwise as {@code boolean}
   */
  private boolean notValidFileName(String path) {
    if (path == null || isAnArgument(path)) return true;
    return !Pattern.compile(REGEX_VALID_FILE).matcher(path).matches();
  }

  /**
   * Helper method. Uses regex to check if a given string is an invalid path name
   * @param path path name to evaluate
   * @return true if not valid, false otherwise as {@code boolean}
   */
  private boolean notValidPath(String path) {
    if (path == null || isAnArgument(path)) return true;
    return !Pattern.compile(REGEX_VALID_DIRECTORY).matcher(path).matches();
  }

  /**
   * Helper method. Used to determine if a given argument is in the list of reserved arguments.
   * Useful when differentiating if inputs are valid
   * @param value argument to evaluate
   * @return true if value is a reserved argument, false otherwise as {@code boolean}
   */
  private boolean isAnArgument(String value) {
    return argCheck.containsKey(value);
  }
}
