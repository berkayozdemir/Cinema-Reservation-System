public class Seat {


String hall;
String movie;
int row;
int column;
String owner;
float bought_price;

    public Seat(String movie,String hall, int row, int column, String owner, float bought_price) {

        this.movie=movie;
        this.hall = hall;
        this.row = row;
        this.column = column;
        this.owner = owner;
        this.bought_price = bought_price;
    }
}
