package assignment1;

/**
 * This class keeps track of the number of multiplications/additions for a certain
 * operations defined in the {@link assignment1.Number} class.
 */
public class Counter {

    private int multiplications;
    private int additions;
    private int subtractions;

    /**
     * Increments the addition counter by 1.
     */
    public void addition() {
        this.additions++;
    }

    /**
     * Increments the subtraction counter by 1.
     */
    public void subtraction() {
        this.subtractions++;
    }

    /**
     * Increments the multiplication counter by 1.
     */
    public void multiplication() {
        this.multiplications++;
    }

    /**
     * Gets the number of additions happened.
     * @return The number of additions.
     */
    public int getAdditionCount() {
        return this.additions;
    }

    /**
     * Gets the number of multiplications happened.
     * @return The number of multiplications.
     */
    public int getMultiplicationCount() {
        return this.multiplications;
    }

    /**
     * Gets the number of subtractions happened.
     * @return The number of subtractions.
     */
    public int getSubtractionCount() {
        return this.subtractions;
    }
}
