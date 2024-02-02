
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    static String[] histories;
    static int historyIndex;
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
        histories=new String[100];
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
        System.out.println("-+".repeat(50));
        System.out.println("Daily show time of CSTAD Hall : ");
        System.out.println("A) Morning (10:00 AM - 12:30 PM)");
        System.out.println("B) Afternoon (03:00 PM - 05:30 PM)");
        System.out.println("C) Night (10:00 AM - 12:30 PM)");
        System.out.println("-+".repeat(50));
    }
    private static void option() {
        do{
            System.out.println("-+".repeat(50));
            System.out.println("[[ Application Menu ]]");
            System.out.println("< A >  Booking");
            System.out.println("< B >  Hall");
            System.out.println("< C >  Show time");
            System.out.println("< D >  Reboot");
            System.out.println("< E >  History");
            System.out.println("< F >  Exit");
            System.out.println("-+".repeat(50));
            String op=validateInput("Please Select Menu no :  ","^[a-fA-F]$");
            switch (op){
                case "a","A" -> hallBooking();
                case "b","B"-> hallShow();
                case "c","C"-> timeShow();
                case "d","D"-> reboot();
                case "e","E"-> history();
                case "f","F"->{
                    System.out.println("Program has been Exit");
                    System.exit(0);
                }
                default -> System.out.println("We don't have this option");
            }
        }while (true);

    }
    private static void history() {
        if(!(histories[0]==null)){
            for (String history : histories){
                if(!(history==null)){
                    System.out.println("-+".repeat(50));
                    System.out.println(history);
                    System.out.println("-+".repeat(50));
                }
            }
        }else {
            System.out.println("-+".repeat(50));
            System.out.println("There is no history !!!!");
            System.out.println("-+".repeat(50));
        }
    }

    private static void reboot() {
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
            histories=new String[100];
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
        System.out.println("-+".repeat(50));
        System.out.println("Start Booking Process ");
        System.out.println("-+".repeat(50));
        timeShow();
        System.out.println("-+".repeat(50));
        String confirm=validateInput("Please select show time ( A | B | C ) :  ","^(?i)[abc]$");
        System.out.println("-+".repeat(50));
        switch (confirm){
            case "a","A" -> {
                hallEachShift(morningShift,"Morning");
                eachShiftBooking(morningShift,"Morning");
            }
            case "b","B" ->{
                hallEachShift(afternoonShift,"Afternoon");
                eachShiftBooking(afternoonShift,"Afternoon");
            }
            case "c","C" -> {
                hallEachShift(nightShift,"Night");
                eachShiftBooking(nightShift,"Night");
            }
        }
        System.out.println("-+".repeat(50));
    }
    //Rename Array To format
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
        renameArray(seat);
        System.out.println("Hall "+shift+" shift");
        for(String[] row: seat){
            for(String col: row){
                System.out.print("| "+col+" | \t");
            }
            System.out.println();
        }
    }

    private static void eachShiftBooking(String[][] seat ,String shift) {
        System.out.println("-+".repeat(50));
        System.out.println("INSTRUCTION");
        System.out.println("Single : A-1");
        System.out.println("Multiple (separate by comma )  : A-1,A-2");
        System.out.println("-+".repeat(50));
        String seatBooking = validateInput("Please select available seat :  ", "^[A-Za-z]\\s*-\\s*\\d+(?:,\\s*[A-Za-z]\\s*-\\s*\\d+)*$");
        String[] seatCode=new String[100];
        int index=0;
        Scanner scanner = new Scanner(seatBooking);
        scanner.useDelimiter(",");
        while (scanner.hasNext()) {
            String token = scanner.next();
            seatCode[index]=token.toUpperCase().trim();
            index++;
        }
        scanner.close();
        boolean available=true;
        int dex1=0,dex2=0;
        //Available Checking
        for (int i = 0; i < seat.length; i++){
            for (int j = 0; j < seat[i].length; j++){
                if(!(seatCode[dex1]==null)){
                    if(seat[i][j].contains(seatCode[dex1].trim())){
                        if(!(seat[i][j].contains("AV"))){
                            available=false;

                        }else{
                            dex1++;
                        }

                    }
                }
            }
        }
        if(available)
        {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDateTime = currentDateTime.format(formatter);
            System.out.println("-+".repeat(50));
            int  studentID = Integer.parseInt(validateInput("Please Enter Student ID : ","^\\s*\\d+\\s*$"));
            System.out.println("-+".repeat(50));
            String confirm=validateInput("Are you sure to book ? (Y/N) : ","^(?i)[yn]$");
            System.out.println("-+".repeat(50));
            if(confirm.equals("y")|| confirm.equals("Y")){
                String messageBlock="";
                for (int i = 0; i < seat.length; i++) {
                    for (int j = 0; j < seat[i].length; j++) {
                        if(!(seatCode[dex2]==null)){
                            if(seat[i][j].contains(seatCode[dex2].trim())){
                                if(seat[i][j].contains("AV")){
                                    seat[i][j]="booking";
                                    messageBlock+="["+seatCode[dex2]+"] ";
                                    dex2++;
                                }
                            }
                        }
                    }
                }
                histories[historyIndex]=("Hall "+shift+" shift: \t"+messageBlock+" Booking by Student ID :"+studentID+" \t ( "+formattedDateTime+" )");
                historyIndex++;
                System.out.println("-+".repeat(50));
                System.out.println(messageBlock+" Booking Successfully");
                System.out.println("-+".repeat(50));
            }else {
                System.out.println("-+".repeat(50));
                System.out.println("Booking Unsuccessfully !!");
                System.out.println("-+".repeat(50));
            }

        }
        else {
            System.out.println("-+".repeat(50));
            System.out.println("Unavailable seat!!!!!");

        }

    }
}