package problem1.cli;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Executor {

  private DocGen docGen; //replace with Siya's class
  private FileIO fileIO; //replace with Segun's class

  /**
   * Constructor for Executor
   */
  public Executor() {
    this.docGen = new DocGen();
    this.fileIO = new FileIO();
  }

  /**
   * Helper method Generates a batch of documents and saves to disk.
   * Documents are saved in text format and will be named with incremental fileTitle#.txt
   * @param template
   * @param fileDir
   * @param fileTitle
   * @param recordLocation
   */
  public void generateABatchOfDocuments(String template, String fileDir, String fileTitle, String recordLocation){

    String docTemplate = fileIO.getFileContents(template); // replace with fileIO method
    ArrayList<Client> clients = fileIO.getRecords(recordLocation); //replace with fileIO method

    ArrayList<String> documents = docGen.getGeneratedDocuments(docTemplate,clients); //replace with docGenerator method
    batchSave(fileTitle,fileDir,documents);

  }

  /**
   * Helper method. Saves a list of documents to disk. File names are incremented.
   * @param fileName
   * @param filePath
   * @param documents
   */
  private void batchSave(String fileName, String filePath,ArrayList<String> documents) {
    int counter = 0;

    System.out.println("\nBatch saving...");
    for (String document : documents) {
      counter++;
      String fullFileName = filePath + fileName + counter + ".txt";
      fileIO.saveFileContents(fullFileName, document);
    }
  }

  @Override
  public String toString() {
    return "Executor{" +
        "docGen=" + docGen +
        ", fileIO=" + fileIO +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Executor executor = (Executor) o;
    return Objects.equals(docGen, executor.docGen) && Objects.equals(fileIO,
        executor.fileIO);
  }

  @Override
  public int hashCode() {
    return Objects.hash(docGen, fileIO);
  }
}

//****************** PLACEHOLDER CLASSES ************************
class Client extends HashMap<String,String> {
  public Client(){}
}

class DocGen{

  public DocGen(){}

  public ArrayList<String> getGeneratedDocuments(String fileName, ArrayList<Client> clients) {
    System.out.println("Generating documents...");
    ArrayList<String> documents = new ArrayList<>();
    for (Client client : clients) {
      documents.add(client.get("firstName"));
    }
    return documents;
  }
}


class FileIO{

  public FileIO(){}

  public String getFileContents(String fileName) {
    System.out.println("Reading file: " + fileName);
    return "This is a template";
  }
  public ArrayList<Client> getRecords(String fileName) {
    System.out.println("Reading records from " + fileName);
    ArrayList<Client> clients = new ArrayList<Client>();
    Client client = new Client();
    client.put("firstName", "John");
    clients.add(client);
    return clients;
  }

  public void saveFileContents(String fileName, String fileContents) {
    // WRITE FILE METHOD GOES HERE
    System.out.println("Saving: " + fileName);
  }
}