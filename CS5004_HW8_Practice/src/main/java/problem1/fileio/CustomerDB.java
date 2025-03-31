package problem1.fileio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CustomerDB {

  private ArrayList<String> fieldList = new ArrayList<String>();
  private String fileName;
  private ArrayList<HashMap<String,String>> customerList = new ArrayList<>();

  public CustomerDB(String fileName) {
    this.fileName = fileName;
  }

  public void loadDB(){

    String fileContents = new FileHandler().readFile(fileName);


    try(BufferedReader reader = new BufferedReader(new StringReader(fileContents))) {

      String line;
      //get field categories:
      line = reader.readLine();
      fieldList = parseLineByQuotes(line);

      //Read rest of file
      while ((line = reader.readLine()) != null) {
        //parse line:
        ArrayList<String> values = parseLineByQuotes(line);
        HashMap<String,String> map = new HashMap<>();
        for (int i = 0; i < values.size(); i++) {
          map.put(fieldList.get(i), values.get(i));
        }
        customerList.add(map);
      }
    }catch(IOException e){
      System.out.println(e);
    }
  }

  public ArrayList<String> getFieldList() {
    return fieldList;
  }

  public ArrayList<HashMap<String,String>> getCustomerList() {
    return customerList;
  }

  private ArrayList<String> parseLineByQuotes(String line){
    String regex = "\"(.*?)\"";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(line);
    ArrayList<String> values = new ArrayList<>();

    while (matcher.find()) {
      values.add(matcher.group());
    }
    for(int i=0;i<values.size();i++){
      values.set(i, values.get(i).replaceAll("\"",""));
    }
    return values;
  }

  public static void main(String[] args) {
    ArrayList<String> fieldValues;
    CustomerDB db = new CustomerDB("C:\\Users\\David\\Desktop\\MySDE\\Java\\CS5004_HW8_Practice\\src\\main\\java\\problem1\\insurance-company-members.csv");

    db.loadDB();
    for(HashMap<String,String> map : db.getCustomerList()){
      System.out.println(map.values());
    }
  }

}


