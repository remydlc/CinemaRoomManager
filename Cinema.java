package cinema;

import java.util.Arrays;
import java.util.Scanner;

public class Cinema {

    static int currentIncome = 0;

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = sc.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = sc.nextInt();
        String[][] twoDimArray = createArray(rows, seats);
        int action = cinemaMenu(sc);

        while (action !=0){
            if (action == 1) {
                printArray(twoDimArray);
                action = cinemaMenu(sc);
            } else if ( action == 2) {
                selectSeat(twoDimArray, rows, seats, sc);
                action = cinemaMenu(sc);
            } else if (action == 3) {
                printStats(twoDimArray, rows, seats);
                action = cinemaMenu(sc);
            } else {
                action = cinemaMenu(sc);
            }
        }
    }

    static String[][] createArray(int rows, int seats) {
        // Creates an array of a given size.
        String[][] twoDimArray = new String[rows+1][seats+1];
        int rowcount = 1;
        for (int i = 0; i < twoDimArray.length; i++) {
            int count = 1;
            for (int j = 0; j < twoDimArray[i].length; j++) {
                twoDimArray[i][j] = "S";
                if (i == 0) {
                    if (j == 0) {
                        twoDimArray[i][j] = " ";
                    } else {
                        twoDimArray[i][j] = Integer.toString(count);
                        count++;
                    }
                }
                else if (j== 0){
                    //System.out.println("i and j are " + i + " " + j + " count is " + rowcount);
                    twoDimArray[i][j] = Integer.toString(rowcount);
                    rowcount = rowcount +1;
                }
            }
        }
        return twoDimArray;
    }

    static void printArray(String[][] twoDimArray) throws IndexOutOfBoundsException{
        System.out.println();
        System.out.println("Cinema:");
        for (int i = 0; i < twoDimArray.length; i++) {
            for (int j = 0; j < twoDimArray[i].length; j++) {
                System.out.print(twoDimArray[i][j] + " ");
            }
            System.out.println();
        }
    }

    static void selectSeat(String[][] twoDimArray, int rows, int seats, Scanner sc){
        //This method presents the users with the queries to select and purchase a seat
        // It updates the currentIncome once the sell is done. 
        boolean buyTicket = true;
        while (buyTicket) {

            try {
                System.out.println("Enter a row number:");
                int row = sc.nextInt();
                System.out.println("Enter a seat number in that row:");
                int seatNumber = sc.nextInt();
                if ("B".equals(twoDimArray[row][seatNumber])) {
                    System.out.println("\nThat ticket has already been purchased!\n");
                } else {
                    System.out.println("ticket price: $" + getTicket(rows, seats, row));
                    assignSeat(twoDimArray, row, seatNumber);
                    currentIncome += getTicket(rows, seats, row);
                    buyTicket = false;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("\nWrong input!\n");
            }
        }
    }

    static void assignSeat (String[][] twoDimArray, int row, int seat) {
        twoDimArray[row][seat] = "B";
    }

    static int getTicket(int rows, int seats, int row){
        // This method return the ticket price when the customer asks for a specific seat.
        int ticketPrice;
        int frontSeatPrice = 10;
        int backSeatPrice = 8;
        int rowsHalf =  rows / 2;
        if (rows*seats < 60){
            ticketPrice = frontSeatPrice;
        } else {
            if (row <= rowsHalf){
                ticketPrice = frontSeatPrice;
            }else {
                ticketPrice = backSeatPrice;
            }
        }
        return ticketPrice;
    }

    static int cinemaMenu (Scanner sc) {
        System.out.println();
        System.out.println("1. Show the seats");
        System.out.println("2. Buy a ticket");
        System.out.println("3. Statistics");
        System.out.println("0. Exit");
        return sc.nextInt();
    }

    static void printStats (String[][] twoDimArray, int rows, int seats) {

        System.out.println("\nNumber of purchased tickets: " + ticketCount(twoDimArray) );
        System.out.println("Percentage: " + getPercentageTickets(twoDimArray, rows, seats) + "%");
        System.out.println("Current income: $" + getCurrentIncome());
        System.out.println("Total income: $" + getTotalIncome(rows, seats));
    }

    static int ticketCount(String[][] twoDimArray) throws IndexOutOfBoundsException{
        // Total count of tickets sold
        int ticketCount = 0;
        for (int i = 0; i < twoDimArray.length; i++) {
            for (int j = 0; j < twoDimArray[i].length; j++) {
                if ("B".equals(twoDimArray[i][j])){
                    ticketCount++;
                }
            }
        }
        return ticketCount;
    }

    static String getPercentageTickets(String[][] twoDimArray, int rows, int seats){
        // This method calculate the percentage of ticket sold in relevance to the total seats available
        int ticketCount = ticketCount(twoDimArray);
        int totalSeats = rows * seats;
        double percentage = ((double) ticketCount / totalSeats) * 100;

        return String.format("%.2f", percentage);
    }

    static int getTotalIncome(int rows, int seats) {
        //* This method is to get the full amount you would received should all the tickets sell.
        // It takes into account the different pricing
        int frontSeatPrice = 10;
        int backSeatPrice = 8;
        int totalIncome = 0;
        if ((rows*seats) < 60){
            totalIncome = rows*seats*frontSeatPrice;
        } else {
            if(rows %2 == 0){
                totalIncome += ((rows/2) * seats * frontSeatPrice);
                totalIncome += ((rows/2) * seats * backSeatPrice);
                System.out.println("Total Inc even rows: "+ totalIncome);
            } else {
                totalIncome += ((rows/2) * seats * frontSeatPrice);
                totalIncome += (((rows/2) + 1) * seats * backSeatPrice);
                System.out.println("Total Inc uneven rows: "+ totalIncome);
            }
        }
        return totalIncome;
    }

    static int getCurrentIncome() {
        // this will return the Int variable with the total amount of $ from ticket sales
        return Cinema.currentIncome;
    }

}
