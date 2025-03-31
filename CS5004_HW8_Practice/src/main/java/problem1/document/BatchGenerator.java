package problem1.document;

import java.util.ArrayList;
import java.util.HashMap;
import problem1.fileio.FileHandler;

public class BatchGenerator {

  private ArrayList<HashMap<String,String>> customerList = new ArrayList<>();
  private ArrayList<String> generatedLetters = new ArrayList<>();
  DocGenerator docGenerator;
  private String template;

  public BatchGenerator(ArrayList<HashMap<String, String>> customerList, String template) {
    this.customerList = customerList;
    this.template = template;
    docGenerator = new DocGenerator(template,new HashMap<>());
  }

  public ArrayList<HashMap<String, String>> getCustomerList() {
    return customerList;
  }

  public String getTemplate() {
    return template;
  }

  public void generateLetters(){
    this.generatedLetters.clear();
    for(HashMap<String,String> customer: customerList){
      docGenerator.setFields(customer);
      this.generatedLetters.add(docGenerator.getDocument());
    }
  }

  public ArrayList<String> getGeneratedLetters() {
   return generatedLetters;
  }

  public static void main(String[] args) {
    FileHandler fileHandler = new FileHandler();

    ArrayList<HashMap<String,String>> customerList = new ArrayList<>();


    for(int i=0;i<10;i++){
      HashMap<String, String> fields = new HashMap<>();
      fields.put("first_name", "John" + i);
      fields.put("last_name", "Smith" + i);
      fields.put("email", "john.smith@gmail.com" + i);
      fields.put("phoneNumber", "555-555-5555" + i);
      fields.put("address", "123 Main St" + i);
      fields.put("city", "New York" + i);
      fields.put("state", "NY");
      fields.put("county", "US");
      fields.put("zip", "12345" + i);
      customerList.add(fields);
    }

    String template = fileHandler.readFile("C:\\Users\\David\\Desktop\\MySDE\\Java\\CS5004_HW8_Practice\\src\\main\\java\\problem1\\letter-template.txt");
//    String template = """
//    This is the beginning of the text.
//    Name: [[firstName]] [[lastName]]
//
//
//    Email: [[email]]
//    Phone: [[phoneNumber]]
//    Address: [[address]]
//    This is the end of the text.
//    """;

    BatchGenerator batchGenerator = new BatchGenerator(customerList, template);
    batchGenerator.generateLetters();
    int counter = 0;
    for(String letter: batchGenerator.getGeneratedLetters()){
      counter++;
      fileHandler.writeFile("C:\\Users\\David\\Desktop\\MySDE\\Java\\CS5004_HW8_Practice\\src\\main\\java\\problem1\\letter" + counter + ".txt", letter);
    }
  }
}
