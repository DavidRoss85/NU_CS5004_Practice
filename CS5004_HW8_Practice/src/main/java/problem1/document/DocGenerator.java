package problem1.document;

import java.util.HashMap;
import java.util.regex.Pattern;

class DocGenerator {

  private String template;
  private HashMap<String, String> fields = new HashMap<>();
  private String fieldWrapperOpen = "[[";
  private String fieldWrapperClose = "]]";


  public DocGenerator(String template, HashMap<String, String> fields) {
    this.template = template;
    this.fields = fields;
  }

  public DocGenerator(String template, HashMap<String, String> fields, String fieldWrapperOpen, String fieldWrapperClose) {
    this.template = template;
    this.fields = fields;
    this.fieldWrapperOpen = fieldWrapperOpen;
    this.fieldWrapperClose = fieldWrapperClose;
  }

  public void setFieldWrappers(String open, String close) {
    fieldWrapperOpen = open;
    fieldWrapperClose = close;
  }

  public void setTemplate(String template) {
    this.template = template;
  }

  public void setFields(HashMap<String, String> fields) {
    this.fields = fields;
  }

  public String getFieldWrapperOpen(){
    return fieldWrapperOpen;
  }

  public String getFieldWrapperClose(){
    return fieldWrapperClose;
  }

  public String getDocument() {
    return generateDocument();
  }

  public String getTemplate() {
    return template;
  }

  private String generateDocument(){
    String document = this.template;

    for(String field : fields.keySet()){
      String placeholer = fieldWrapperOpen + field + fieldWrapperClose;
      String value = fields.get(field);
      document = document.replaceAll(Pattern.quote(placeholer), value);
    }
    return document;
  }

  public static void main(String[] args) {
    HashMap<String, String> fields = new HashMap<>();
    fields.put("firstName", "John");
    fields.put("lastName", "Smith");
    fields.put("email", "john.smith@gmail.com");
    fields.put("phoneNumber", "555-555-5555");
    fields.put("address", "123 Main St");

    String template = """
    This is the beginning of the text.
    Name: [[firstName]] [[lastName]]
    
    
    Email: [[email]]
    Phone: [[phoneNumber]]
    Address: [[address]]
    This is the end of the text.
    """;
    DocGenerator docGenerator = new DocGenerator(template, fields);

    System.out.println(docGenerator.getDocument());

  }

}
