package hw9;

import java.util.ArrayList;
import java.util.Random;

public class ReservationsSystem {

  public static void main(String[] args) {

    Integer MAX_ROWS = 15;
    Integer MAX_SEATS = 10;

    ReservationsService reservationsService = new ReservationsService();

    Theater theater = generateRandomTheater("This Theater", MAX_ROWS, MAX_SEATS);
    if(theater != null) {
      reservationsService.begin(theater);
    }


  }

  private static Theater generateRandomTheater(String name, int rows, int seats) {
    Theater theater = null;

    try{
    ArrayList<Row> rowsList = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
      ArrayList<Seat> seatsList = new ArrayList<>();
      for (int j = 0; j < seats; j++) {
        seatsList.add(new Seat("Seat-" + i + "-" + j,"Available", false));
      }
      Row row = new Row(i+1,seatsList, new Random().nextBoolean());
      rowsList.add(row);
    }

      theater = new Theater(name, rowsList);
    }catch(Exception e){
      System.out.println("Error generating theater:\n" + e.getMessage());

    }
    return theater;
  }
}
