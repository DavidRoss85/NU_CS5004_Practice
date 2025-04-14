package hw9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Theater {


  private String theaterName;
  private ArrayList<Row> rowList = new ArrayList<>();
  private ArrayList<Integer> wheelChairRowsList;

  public Theater(String theaterName, ArrayList<Row> rows) throws IllegalArgumentException {
    validateTheaterName(theaterName);
    validateRowList(rows);

    this.theaterName = theaterName;
    this.rowList = rows;
    this.wheelChairRowsList = copyWheelChairRowsList(rows);
  }

  private void validateTheaterName(String theaterName) throws IllegalArgumentException {
    if (theaterName == null || theaterName.isEmpty())
      throw new IllegalArgumentException("Invalid theater name");
  }

  private void validateRowList(ArrayList<Row> rowList) throws IllegalArgumentException {
    if(rowList == null || rowList.isEmpty())
      throw new IllegalArgumentException("A theater should have at least one row");

    //Ensure no duplicate rows:
    ArrayList<Integer> importedRowNums = new ArrayList<>();
    for (Row row : rowList) {
      if(importedRowNums.contains(row.getRowNumber())){
        throw new IllegalArgumentException("Duplicate row number in row list");
      }
      importedRowNums.add(row.getRowNumber());
    }
  }

  private ArrayList<Integer> copyWheelChairRowsList(ArrayList<Row> rowList) {
    ArrayList<Integer> wheelChairRowsList = new ArrayList<>();
    for (Row row : rowList) {
      if(row.getWheelChairAccessible()){
        wheelChairRowsList.add(row.getRowNumber());
      }
    }
    return wheelChairRowsList;
  }

  public ArrayList<Integer> getWheelChairRowsList() {
    return wheelChairRowsList;
  }

  public String getTheaterName() {
    return theaterName;
  }

  public ArrayList<Row> getRowList() {
    return rowList;
  }

  public Boolean isRowWheelChairAccessible(Integer rowNumber) {
    return wheelChairRowsList.contains(rowNumber);
  }

  public void printSeats(){
    for(Row row : rowList){
      //print row
    }
  }

  @Override
  public String toString() {
    return "Theater{" +
        "wheelChairRowsList=" + wheelChairRowsList +
        ", theaterName='" + theaterName + '\'' +
        ", rowList=" + rowList +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Theater theater = (Theater) o;
    return Objects.equals(wheelChairRowsList, theater.wheelChairRowsList)
        && Objects.equals(theaterName, theater.theaterName) && Objects.equals(
        rowList, theater.rowList);
  }

  @Override
  public int hashCode() {
    return Objects.hash(wheelChairRowsList, theaterName, rowList);
  }
}
