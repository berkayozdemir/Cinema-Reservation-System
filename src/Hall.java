public class Hall {
    String currentfilm;
    String hallname;
    float price;
    int row;
    int column;
    Seat[][] seatsHall;

    public Hall(String currentfilm, String hallname, float price, int row, int column) {
        this.currentfilm = currentfilm;
        this.hallname = hallname;
        this.price = price;
        this.row = row;
        this.column = column;
        seatsHall=new Seat[row][column];


    }

    public void updateSeats() {
        for(int i=0;i<BackupReader.seats.size();i++) {


            if(BackupReader.seats.get(i).hall.equals(hallname)&& BackupReader.seats.get(i).movie.equals(currentfilm))
            {
                int x= BackupReader.seats.get(i).row;
                int y=BackupReader.seats.get(i).column;
                seatsHall[x][y]=BackupReader.seats.get(i);

            }

        }


    }

    public void createSeats() {

        for(int i=0;i<row;i++) {
            for(int y=0;y<column;y++) {
                seatsHall[i][y]=new Seat(hallname,currentfilm,i,y,"null",0);
            }
        }
    }

}
