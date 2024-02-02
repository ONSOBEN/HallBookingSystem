
/*System Feature :
    Set up Hall Seat (Row and Column)
    Hall Booking :
        -Users can search for available halls based on show times such as morning, afternoon,and night and capacity.
        - Users can select a hall and make a booking by providing relevant details such as showtime, seat no, and student card ID.
        -The system should handle conflicts and ensure that double bookings cannot occur.
    Hall Checking:
        -Users can access hall seats within a beautiful display as chairs.
        -AV means AVAILABLE, and BO means BOOKED
    Showtime Checking:
        -Users can access a showtime schedule to check the availability of halls.
    Booking History:
        -Users can view the booking history of the system, including details such as the hall name, seat no, student card ID, date, and time of the booking.
    Rebooting Hall:
        -Users can initiate the process of rebooting a hall a day.
        -The system should automatically update the hall's availability status once the rebooting process is complete.
*
* */



import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {

    static Scanner input=new Scanner(System.in);
    static int row;
    static int seat;
    static String[][] seats;
    static char[] alphabet;
    static String[][] morningShift;
    static String[][] afternoonShift;
    static String[][] nightShift;
    static String validateInput(String message,String regex){
        while (true){
            System.out.print(message);
            String userInput=input.nextLine();
            Pattern pattern =Pattern.compile(regex);
            if(pattern.matcher(userInput).matches()){
                return userInput;
            }else System.out.println("Error : Invalid Format");
        }

    }
    public static void main(String[] args) {
        alphabet=new char[26];
        int index=0;
        for(char ch='A';ch<='Z';ch++){
            alphabet[index]=ch;
            index++;
        }
        System.out.println("-+".repeat(50));
        System.out.println("Hall Booking System");
        System.out.println("-+".repeat(50));
        hallSetUp();
        option();

    }

    private static void hallSetUp() {
        row= Integer.parseInt(validateInput("Config total rows in hall :","[0-9]+"));
        seat= Integer.parseInt(validateInput("Config total seat per row in hall :","[0-9]+"));
        seats=new String[row][seat];
        morningShift=new String[row][seat];
        afternoonShift=new String[row][seat];
        nightShift=new String[row][seat];
    }
    private static void timeShow(){
        System.out.println("Daily show time of CSTAD Hall : ");
        System.out.println("A) Morning (10:00 AM - 12:30 PM)");
        System.out.println("B) Afternoon (03:00 PM - 05:30 PM)");
        System.out.println("C) Night (10:00 AM - 12:30 PM)");
    }

    private static void option() {
        do{
            System.out.println("[[ Application Menu ]]");
            System.out.println("< A >  Booking");
            System.out.println("< B >  Hall");
            System.out.println("< C >  Show time");
            System.out.println("< D >  Reboot");
            System.out.println("< E >  History");
            System.out.println("< F >  Exit");
            System.out.print("Please Select Menu no :  ");
            char op=input.next().charAt(0);
            switch (op){
                case 'a','A' -> hallBooking();
                case 'b','B'-> hallShow();
                case 'c','C'-> timeShow();
                case 'd','D'-> reboot();
                case 'e','E'-> history();
                case 'f','F'->{
                    System.out.println("Program has been Exit");
                    System.exit(0);
                }
                default -> System.out.println("We don't have this option");
            }
        }while (true);

    }
    private static void history() {
    }

    private static void reboot() {
        input.nextLine();
        String confirm=validateInput("Do you want to reboot hall system ? (Y/N) : ","^(?i)[yn]$");
        if(confirm.equals("y")|| confirm.equals("Y")){
            renameArray(morningShift);
            for(int i=0;i<seats.length;i++){
                for(int j=0;j<seats[i].length;j++){
                    seats[i][j]=alphabet[i]+"-"+(j+1)+"::AV";
                }
            }
            morningShift=seats;
            afternoonShift=seats;
            nightShift=seats;
        }else {
            System.out.println("Reboot Unsuccessfully!!!!!!!!");
        }
    }

    private static void hallShow() {
        System.out.println("-+".repeat(50));
        System.out.println("Hall Information");
        renameArray(morningShift);
        renameArray(afternoonShift);
        renameArray(nightShift);
        System.out.println("-+".repeat(50));
        hallEachShift(morningShift,"Morning");
        System.out.println("-+".repeat(50));
        hallEachShift(afternoonShift,"Afternoon");
        System.out.println("-+".repeat(50));
        hallEachShift(nightShift,"Night");
        System.out.println("-+".repeat(50));
    }
    private static void hallBooking() {
    }
    private static void renameArray(String[][] seat){
        for(int i=0;i<seat.length;i++){
            for(int j=0;j<seat[i].length;j++){
                if(seat[i][j]==null || seat[i][j].contains("AV")){
                    seat[i][j]=alphabet[i]+"-"+(j+1)+"::AV";
                }else{
                    seat[i][j]=alphabet[i]+"-"+(j+1)+"::BO";
                }
            }
        }
    }
    private static void hallEachShift(String[][] seat,String shift){
        System.out.println("Hall "+shift+" shift");
        for(String[] row: seat){
            for(String col: row){
                System.out.print(col+" \t");
            }
            System.out.println();
        }
    }
}
