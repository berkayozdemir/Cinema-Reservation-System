import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;

public class BackupReader {
    static ArrayList<User> users;
    static ArrayList<Hall> halls;
    static ArrayList<Film> films;
    static ArrayList<Seat> seats;
    static int blocktime;
    static int discount;




    public  BackupReader() throws FileNotFoundException {

        users=new ArrayList<>();
        halls=new ArrayList<>();
        films=new ArrayList<>();
        seats=new ArrayList<>();

        readBackup();
        readProperties();

    }

    public static String hashPassword(String password) throws NoSuchAlgorithmException {

        byte[] bytesofPassword=password.getBytes(StandardCharsets.UTF_8);
        byte[] md5Digest= new byte[0];
        try
        {md5Digest= MessageDigest.getInstance("MD5").digest(bytesofPassword);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return Base64.getEncoder().encodeToString(md5Digest);
    }

    public static void writeBackup() throws FileNotFoundException, UnsupportedEncodingException {
        File output=new File("assets\\data\\backup.dat");
        PrintWriter printWriter=new PrintWriter(output, "UTF-8");
        for(int i=0;i< users.size();i++) {
            printWriter.write("user\t"+users.get(i).username+"\t"+users.get(i).hashedpass+"\t"+users.get(i).clubmember+"\t"+users.get(i).admin+"\n");
        }
        for(int i=0;i< films.size();i++) {
            printWriter.write("film\t"+films.get(i).filmname+"\t"+films.get(i).filmpath+"\t"+films.get(i).duration+"\n");
            System.out.println("film is writing");
        }

        for(int i=0;i< halls.size();i++) {
            printWriter.write("hall\t"+halls.get(i).currentfilm+"\t"+halls.get(i).hallname+"\t"+halls.get(i).price+"\t"+halls.get(i).row+"\t"+halls.get(i).column+"\n");
            for(int j=0;j<halls.get(i).row;j++) {
                for(int x=0;x<halls.get(i).column;x++) {
                    Seat seat=halls.get(i).seatsHall[j][x];
                    printWriter.write("seat\t"+seat.movie+"\t"+seat.hall+"\t"+seat.row+"\t"+seat.column+"\t"+seat.owner+"\t"+seat.bought_price+"\n");

                }
            }
        }

        printWriter.close();


    }

    private void readProperties() throws FileNotFoundException {

        File myObj = new File("assets\\data\\properties.dat");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();

            if(data.contains("discount-percentage")) {
                String[] data2=data.split("=");
                discount=Integer.parseInt(data2[1]);

            }

            if(data.contains("block-time")) {
                String[] data2=data.split("=");
                blocktime=Integer.parseInt(data2[1]);

            }


        }
        myReader.close();


    }

    private void readBackup() { try {
        File myObj = new File("assets\\data\\backup.dat");
        Scanner myReader = new Scanner(myObj);
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();

           String[] datas=data.split("\t");
           if(datas[0].equals("user")) {
               System.out.println("user");
               User user=new User(datas[1],datas[2],Boolean.parseBoolean(datas[3]),Boolean.parseBoolean(datas[4]));
               users.add(user);
           }

           else if(datas[0].equals("seat")) {
               Seat seat=new Seat(datas[1],datas[2],Integer.parseInt(datas[3]),Integer.parseInt(datas[4]),datas[5],Float.parseFloat(datas[6]));

               seats.add(seat);
           }
           else if(datas[0].equals("hall")) {
               Hall hall=new Hall(datas[1],datas[2],Float.parseFloat(datas[3]),Integer.parseInt(datas[4]),Integer.parseInt(datas[5]));
               halls.add(hall);
           }

           else if(datas[0].equals("film")) {
               System.out.println("film");
               System.out.println(datas[1]);
               Film film=new Film(datas[1],datas[2],Integer.parseInt(datas[3]));
               films.add(film);

           }

        }
        myReader.close();
    } catch (FileNotFoundException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
    }

    for (Hall hall : halls) {
            hall.updateSeats();
        }






    }


}
