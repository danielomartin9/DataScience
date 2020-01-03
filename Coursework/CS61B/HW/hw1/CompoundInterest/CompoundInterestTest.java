import static org.junit.Assert.*;

import com.google.common.collect.ComputationException;
import org.junit.Test;

public class CompoundInterestTest {

    @Test
    public void testNumYears() {
        /** Sample assert statement for comparing integers.

        assertEquals(0, 0); */
        assertEquals(1, CompoundInterest.numYears(2019)); // Test when targetYear > THIS_YEAR
        assertEquals(0, CompoundInterest.numYears(2018)); // Test when targetYear == THIS_YEAR
    }

    @Test
    public void testFutureValue() {
        double tolerance = 0.01;

        assertEquals(12.544, CompoundInterest.futureValue(10, 12, 2020), tolerance);
        assertEquals(10, CompoundInterest.futureValue(10, 12, 2018), tolerance);
        assertEquals(15.735, CompoundInterest.futureValue(10, 12, 2022), tolerance);
        assertEquals(9.025, CompoundInterest.futureValue(10, -5, 2020), tolerance);
        assertEquals(8.852, CompoundInterest.futureValue(10,-3, 2022), tolerance);
    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;

        assertEquals(11.802, CompoundInterest.futureValueReal(10, 12, 2020, 3), tolerance);
        assertEquals(13.930, CompoundInterest.futureValueReal(10, 12, 2022, 3), tolerance);
        assertEquals(8.491, CompoundInterest.futureValueReal(10,-5, 2020, 3), tolerance);
        assertEquals(7.836, CompoundInterest.futureValueReal(10, -3, 2022, 3), tolerance);
    }

    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;

        assertEquals(16550, CompoundInterest.totalSavings(5000, 2020, 10), tolerance);
        assertEquals(61051, CompoundInterest.totalSavings(10000, 2022, 10), tolerance);
        assertEquals(8557.5, CompoundInterest.totalSavings(3000, 2020, -5), tolerance);
        assertEquals(11772.162, CompoundInterest.totalSavings(2500, 2022, -3), tolerance);
    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;

        assertEquals(15571.895, CompoundInterest.totalSavingsReal(5000, 2020, 10, 3), tolerance);
        assertEquals(54048.011, CompoundInterest.totalSavingsReal(10000, 2022, 10, 3), tolerance);
        assertEquals(8051.751, CompoundInterest.totalSavingsReal(3000, 2020, -5, 3), tolerance);
        assertEquals(10421.810, CompoundInterest.totalSavingsReal(2500, 2022, -3, 3), tolerance);
    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}
