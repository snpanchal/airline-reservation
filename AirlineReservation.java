import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author Shyam Panchal
 * Old Scona Academic High School - Computer Science IB
 *
 * This class represents an airline reservation
 * system that people can use to reserve spots on
 * flights.
 */
public class AirlineReservation {
    private static Scanner in = new Scanner(System.in);
    private static int firstClass;//Number of reservations in first class
    private static int economyClass;//Number of reservations in economy class
    private static int vegMeal;//Number of reservations that booked a vegetarian meal
    private static int bags;//Total number of bags of all reservations
    private static ArrayList<Passenger> passengers = new ArrayList<>();//ArrayList of passengers

    /**
     * This method adds a reservation to the airline
     * and checks against all restrictions.
     */
    private static void addReservation() {
        //If airline is full
        if (passengers.size() == 10) {
            System.out.println("Sorry for the inconvenience, but this flight is full. The next flight is three days after this one.");
            return;
        }

        //Passenger instantiation
        System.out.print("What is your first name? ");
        String firstName = in.next();
        System.out.print("What is your last name? ");
        String lastName = in.next();
        System.out.print("What is your age? ");
        int age = in.nextInt();
        System.out.print("What section would you like to book (type 'first' or 'economy')? ");
        String section = in.next().toLowerCase();
        System.out.print("What meal would you like to reserve (type in 'vegetarian' or 'normal')? ");
        String mealType = in.next().toLowerCase();
        System.out.print("How many bags will you be carrying with you? ");
        int numBags = in.nextInt();

        Passenger passenger = new Passenger(firstName, lastName, age, section, mealType, numBags);
        boolean seatSelected = seatSelection(passenger);//True if there is space on the flight or false if there is no space

        //If there is a seat available
        if (seatSelected) {
            mealSelection(passenger);
            setBags(passenger);
            passengers.add(passenger);
        }
    }

    /**
     * This method deletes a passenger from the current
     * reservations.
     * @param passengerName first and last name of the
     *                      passenger who's reservation
     *                      needs to be deleted
     */
    private static void deleteReservation(String passengerName) {
        //Binary search to search for the passenger to remove
        passengerName = passengerName.toLowerCase();
        int lowerBound = 0;
        int upperBound = passengers.size() - 1;
        int indexToRemove = 0;

        while (lowerBound <= upperBound) {
            int midPosition = (lowerBound + upperBound) / 2;
            String currentPassengerName = passengers.get(midPosition).firstName.toLowerCase() + " " + passengers.get(midPosition).lastName.toLowerCase();

            if (passengerName.compareTo(currentPassengerName) < 0) {
                upperBound = midPosition - 1;
            }
            else if (passengerName.compareTo(currentPassengerName) > 0) {
                lowerBound = midPosition + 1;
            }
            else if (passengerName.equals(currentPassengerName)) {
                indexToRemove = midPosition;
                break;
            }
            else {
                System.out.println("This name is not found in the reservations.");
                return;
            }
        }

        passengers.remove(indexToRemove);
        System.out.println("Your reservation has been deleted.");
    }

    /**
     * Method to check if there are seats available for
     * a passenger
     * @param passenger passenger who needs to have get
     *                  a seat
     * @return true if there is space or false if there
     * is none
     */
    private static boolean seatSelection(Passenger passenger) {

        //If the passenger wants to have a first class seat
        if (passenger.section.equals("first")) {
            //If first class is full
            if (firstClass == 2) {
                //If economy class is full
                if (economyClass == 8) {
                    System.out.println("Sorry for any inconveniences but our flight is full. The next flight will be in three days.");
                    passenger = null;
                    return false;
                }

                //If economy class has space, change reservation to economy class
                return changeSection(passenger, "first", "economy");
            }
            //If first class is not full
            else {
                firstClass++;
                System.out.println("Your first class seat has been confirmed.");
                return true;
            }
        }
        //If passenger wants an economy class seat
        else {
            //If economy class is full
            if (economyClass == 8) {
                //If first class is full
                if (firstClass == 2) {
                    System.out.println("Sorry for any inconveniences but our flight is full. The next flight will be in three days.");
                    passenger = null;
                    return false;
                }

                //If there is space in first class
                return changeSection(passenger, "economy", "first");
            }
            //If economy class is not full
            else {
                economyClass++;
                System.out.println("Your economy class seat has been confirmed.");
                return true;
            }
        }
    }

    /**
     * In case of a section being full and the other
     * section having space, this method will assign
     * a seat in the section other than the one the
     * passenger chose initially.
     * @param passenger passenger who's seat needs to
     *                  be changed
     * @param currentSection current section of the
     *                       passenger
     * @param otherSection the section the passenger
     *                     will be moved to
     * @return true if the change is successful or false
     * if the passenger does not want to move to another
     * section
     */
    private static boolean changeSection(Passenger passenger, String currentSection, String otherSection) {
        //Asks user if they would like to switch or wait for the next flight
        System.out.println("Our " + currentSection + " section is full. Please change to " + otherSection + " or wait three days for he next flight.");
        System.out.print("What would you like to do (enter 'change' or 'wait')? ");
        String decision = in.next().toLowerCase();

        switch (decision) {
            //If they want to change the section
            case "change":
                passenger.section = otherSection;
                System.out.println("Your section has been successfully changed.");
                return true;
            default:
                System.out.print("The next flight will be in three days.");
                passenger = null;
                return false;
        }
    }

    /**
     * This method takes into account a passenger's meal
     * choice and checks it against restrictions.
     * @param passenger passenger who's meal choice needs
     *                  to be taken into account
     */
    private static void mealSelection(Passenger passenger) {
        //If the passenger wants a vegetarian meal
        if (passenger.mealType.equals("vegetarian")) {
            //If vegetarian meal capacity is filled
            if (vegMeal == 1) {
                System.out.println("Sorry for any inconveniences but we have no more vegetarian meals left.");
                System.out.println("You must bring your own food or share with the passenger that has already reserved a vegetarian meal.");
            }
            else {
                vegMeal++;
                System.out.println("Your choice of a vegetarian meal has been recorded.");
            }
        }
        else {
            System.out.println("Your choice of a normal meal has been recorded.");
        }
    }

    /**
     * This method takes a passenger and updates
     * the total number of bags according to what
     * they are going to carry, and these are tested
     * against restrictions.
     * @param passenger passenger who's bags need to
     *                  be added to the airline
     */
    private static void setBags(Passenger passenger) {
        //If baggage capacity is already full
        if (bags == 10) {
            System.out.println("Currently, our luggage capacity of 10 bags is full.");
            System.out.println("Your luggage will reach your destination on the next flight three days after this one.");
        }
        //If there is enough space
        else {
            int extraBags;
            bags+= passenger.numBags;

            //If the new total of bags is above 10
            if (bags > 10) {
                extraBags = bags - 10;
                bags = 10;

                //Any extra bags of the passenger will reach on a later flight
                System.out.println("Sorry for any inconveniences, however, our luggage capacity has reached its maximum of 10 bags.");
                System.out.println(extraBags + " of your bags will reach the destination on the next flight three days after this one.");
                return;
            }

            System.out.println("Space for your bags has been reserved in our compartments.");
        }
    }

    /**
     * This method uses insertion sort to sort
     * the passengers according to first names.
     */
    private static void firstNameSort() {
        for (int i = 1; i < passengers.size(); i++) {
            String key = passengers.get(i).firstName.toLowerCase();
            int j = i - 1;

            while (j >= 0 && key.compareTo(passengers.get(j).firstName.toLowerCase()) < 0) {
                Passenger temp = passengers.get(j);
                passengers.set(j, passengers.get(j + 1));
                passengers.set(j + 1, temp);
                j--;
            }
        }
    }

    /**
     * This method uses insertion sort to sort
     * the passengers according to last names.
     */
    private static void lastNameSort() {
        for (int i = 1; i < passengers.size(); i++) {
            String key = passengers.get(i).lastName.toLowerCase();
            int j = i - 1;

            while (j >= 0 && key.compareTo(passengers.get(j).lastName.toLowerCase()) < 0) {
                Passenger temp = passengers.get(j);
                passengers.set(j, passengers.get(j + 1));
                passengers.set(j + 1, temp);
                j--;
            }
        }
    }

    /**
     * This method uses bubble sort to sort
     * the passengers according to their ages.
     */
    private static void ageSort() {
        boolean sorted = false;

        while (!sorted) {
            sorted = true;

            for (int i = 0; i < passengers.size(); i++) {
                if (passengers.get(i).age > passengers.get(i + 1).age) {
                    Passenger temp = passengers.get(i);
                    passengers.set(i, passengers.get(i + 1));
                    passengers.set(i, temp);

                    sorted = false;
                }
            }
        }
    }

    /**
     * This method uses bubble sort to sort
     * the passengers according to their section
     * on the flight.
     */
    private static void sectionSort() {
        boolean sorted = false;

        while (!sorted) {
            sorted = true;

            for (int i = 0; i < passengers.size(); i++) {
                if (passengers.get(i).section.equals("economy class") && passengers.get(i + 1).section.equals("first class")) {
                    Passenger temp = passengers.get(i);
                    passengers.set(i, passengers.get(i + 1));
                    passengers.set(i + 1, temp);

                    sorted = false;
                }
            }
        }
    }

    /**
     * This method uses bubble sort to sort
     * the passengers according to the meal
     * they have ordered.
     */
    private static void mealSort() {
        boolean sorted = false;

        while (!sorted) {
            sorted = true;

            for (int i = 0; i < passengers.size() - 1; i++) {
                if (passengers.get(i).mealType.equals("normal") && passengers.get(i + 1).mealType.equals("vegetarian")) {
                    Passenger temp = passengers.get(i);
                    passengers.set(i, passengers.get(i + 1));
                    passengers.set(i + 1, temp);

                    sorted = false;
                }
            }
        }
    }

    /**
     * This method uses bubble sort to sort
     * the passengers according to the number
     * of bags they will carry.
     */
    private static void bagsSort() {
        boolean sorted = false;

        while (!sorted) {
            sorted = true;

            for (int i = 0; i < passengers.size() - 1; i++) {
                if (passengers.get(i).numBags > passengers.get(i + 1).numBags) {
                    Passenger temp = passengers.get(i);
                    passengers.set(i, passengers.get(i + 1));
                    passengers.set(i + 1, temp);

                    sorted = false;
                }
            }
        }
    }

    public static void main(String[] args) {
        //Asks user what they want to do
        System.out.println("Welcome to the airline reservation program for Milk Crate Air.");
        System.out.println("What would you like to do? Type: ");
        System.out.println("'Add' to add a reservation");
        System.out.println("'Delete' delete a reservation");
        System.out.println("'Sort' to sort our reservations");
        System.out.println("'Exit' to exit the program");
        System.out.print("Enter what you would like to do here: ");
        String decision = in.next().toLowerCase();
        System.out.println();

        //While they are not finished
        while (!decision.equals("exit")) {
            //Checks what user wants to do and takes actions accordingly
            switch (decision){
                case "add":
                    addReservation();
                    break;
                case "delete":
                    System.out.print("Enter the first name of the person who's reservation you would like to delete: ");
                    String firstName = in.next().toLowerCase();
                    System.out.print("Enter their last name: ");
                    String lastName = in.next().toLowerCase();
                    deleteReservation(firstName + " " + lastName);
                    break;
                case "sort":
                    in.nextLine();
                    //Asks how the reservations need to be sorted
                    System.out.println("You can sort the reservations using the following information:");
                    System.out.println("First name");
                    System.out.println("Last name");
                    System.out.println("Age");
                    System.out.println("Section");
                    System.out.println("Meal");
                    System.out.println("Bags");
                    System.out.print("Enter one of the above categories to sort the reservations: ");
                    String sortChoice = in.nextLine().toLowerCase();

                    //Checks how to sort reservations
                    switch (sortChoice) {
                        case "first name":
                            firstNameSort();
                            break;
                        case "last name":
                            lastNameSort();
                            break;
                        case "age":
                            ageSort();
                            break;
                        case "section":
                            sectionSort();
                            break;
                        case "meal":
                            mealSort();
                            break;
                        case "bags":
                            bagsSort();
                            break;
                    }

                    //Prints out sorted reservations
                    for (Passenger passenger : passengers) {
                        System.out.println(passenger.firstName + " " + passenger.lastName);
                    }
                    break;
                case "exit":
                    decision = "exit";
                    break;
            }

            //If user still wants to continue
            if (!decision.equals("exit")) {
                System.out.print("Enter what you would like to do next: ");
                decision = in.next().toLowerCase();
            }
        }

        System.out.println("Thank you for using our reservation system.");
    }
}
