/**
 * @author Shyam Panchal
 * Old Scona Academic High School - Computer Science IB
 *
 * An object of type Passenger represents
 * a passenger reservation on the airline,
 * with various characteristics, as displayed
 * by the fields.
 */
public class Passenger {
    String firstName;//First name of passenger
    String lastName;//Last name of passenger
    int age;//Age of person
    String section;//Section they would like to book (first class or economy class)
    String mealType;//Meal they would like to reserve (vegetarian or normal)
    int numBags;//Number of bags they will carry

    /**
     * Constructor method for the Passenger object
     */
    public Passenger(String firstName, String lastName, int age, String section, String mealType, int numBags) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.section = section;
        this.mealType = mealType;
        this.numBags = numBags;
    }
}
