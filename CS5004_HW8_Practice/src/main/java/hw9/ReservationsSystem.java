package hw9;

import java.util.ArrayList;

public class ReservationsSystem {

  public static void main(String[] args) {

    Integer MAX_ROWS = 15;
    Integer MAX_SEATS = 10;

    ArrayList<Row> rows = new ArrayList<>();
    for (int i = 0; i < MAX_ROWS; i++) {
      ArrayList<Seat> seats = new ArrayList<>();
      for (int j = 0; j < MAX_SEATS; j++) {
        seats.add(new Seat("Seat-" + i + "-" + j,"Available", false));
      }
      Row row = new Row(i+1,seats, true);
      rows.add(row);
    }
    Theater theater = new Theater("This Theater",rows);

    ReservationsService reservationsService = new ReservationsService();
    reservationsService.begin(theater);


  }
}
