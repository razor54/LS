package pt.isel.ls;

import org.junit.Test;
import pt.isel.ls.control.Ints;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class IntsTests {

    private static final int MANY_VALUES_LENGTH = 10000;
    private static int[] manyValues;

    static {
        manyValues = new int[MANY_VALUES_LENGTH];
        for (int i = 0; i < MANY_VALUES_LENGTH; i++)
            manyValues[i] = i + 1;
    }

    @Test
    public void max_returns_greatest() {
        assertEquals(1, Ints.max(1, -2));
        assertEquals(1, Ints.max(-2, 1));
        assertEquals(-1, Ints.max(-1, -2));
        assertEquals(-1, Ints.max(-2, -1));
    }

    /**
     * testar inicio e fim
     */
    @Test
    public void indexOfBinary_returns_negative_if_not_found() {
        // Arrange
        int[] v = {1, 2, 3};

        // Act
        int ix = Ints.indexOfBinary(v, 0, 2, 4);

        // Assert
        assertTrue(ix < 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void indexOfBinary_throws_IllegalArgumentException_if_indexes_are_not_valid() {
        // Arrange
        int[] v = {1, 2, 3};

        // Act
        int ix = Ints.indexOfBinary(v, 2, 1, 4);

        // Assert
        assertTrue(ix < 0);
    }

    @Test
    public void indexOfBinary_right_bound_parameter_is_exclusive() {
        // Arrange
        int[] v = {2, 2, 2};

        // Act
        int ix = Ints.indexOfBinary(v, 1, 1, 2);

        // Assert
        assertTrue(ix >= 0);
    }

    @Test
    public void indexOfBinary_array_of_negative_values() {
        // Arrange
        int[] v = {-3, -2, -1};

        // Act
        int ix = Ints.indexOfBinary(v, 0, 2, -1);

        // Assert
        assertTrue(ix >= 0);
    }

    @Test
    public void indexOfBinary_array_with_many_elements_get_fist_element() {
        // Arrange
        int[] v = manyValues;

        // Act
        int ix = Ints.indexOfBinary(v, 0, MANY_VALUES_LENGTH - 1, 1);

        // Assert
        assertTrue(ix >= 0);
    }

    @Test
    public void array_of_ten_values() {
        // Arrange
        int[] v = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        // Act
        int ix = Ints.indexOfBinary(v, 0, 0, 1);

        // Assert
        assertTrue(ix >= 0);
    }

}
