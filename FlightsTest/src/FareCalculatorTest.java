import org.junit.*;
import static org.junit.Assert.*;

/**
 * The class <code>FareCalculatorTest</code> contains tests for the class <code>{@link FareCalculator}</code>.
 *
 * @generatedBy CodePro at 4/28/14 8:57 AM
 * @author DMR
 * @version $Revision: 1.0 $
 */
public class FareCalculatorTest {
	/**
	 * Run the double calculateFare(double,double,boolean,long,int) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/28/14 8:57 AM
	 */
	@Test
	public void testCalculateFare_1()
		throws Exception {
		FareCalculator fixture = new FareCalculator();
		double price1 = -4.9E-324;
		double price2 = 1.0;
		boolean isFreqFlier = true;
		long departureTime = 1L;
		int duration = 1;

		double result = fixture.calculateFare(price1, price2, isFreqFlier, departureTime, duration);

		// add additional test code here
		assertEquals(-1.0, result, 0.1);
	}

	/**
	 * Run the double calculateFare(double,double,boolean,long,int) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/28/14 8:57 AM
	 */
	@Test
	public void testCalculateFare_2()
		throws Exception {
		FareCalculator fixture = new FareCalculator();
		double price1 = 1.0;
		double price2 = -4.9E-324;
		boolean isFreqFlier = true;
		long departureTime = 1L;
		int duration = 1;

		double result = fixture.calculateFare(price1, price2, isFreqFlier, departureTime, duration);

		// add additional test code here
		assertEquals(-1.0, result, 0.1);
	}

	/**
	 * Run the double calculateFare(double,double,boolean,long,int) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/28/14 8:57 AM
	 */
	@Test
	public void testCalculateFare_3()
		throws Exception {
		FareCalculator fixture = new FareCalculator();
		double price1 = 1.0;
		double price2 = 1.0;
		boolean isFreqFlier = true;
		long departureTime = -1L;
		int duration = 1;

		double result = fixture.calculateFare(price1, price2, isFreqFlier, departureTime, duration);

		// add additional test code here
		assertEquals(-1.0, result, 0.1);
	}

	/**
	 * Run the double calculateFare(double,double,boolean,long,int) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/28/14 8:57 AM
	 */
	@Test
	public void testCalculateFare_4()
		throws Exception {
		FareCalculator fixture = new FareCalculator();
		double price1 = 1.0;
		double price2 = 1.0;
		boolean isFreqFlier = true;
		long departureTime = 1L;
		int duration = -1;

		double result = fixture.calculateFare(price1, price2, isFreqFlier, departureTime, duration);

		// add additional test code here
		assertEquals(-1.0, result, 0.1);
	}

	/**
	 * Run the double calculateFare(double,double,boolean,long,int) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/28/14 8:57 AM
	 */
	@Test
	public void testCalculateFare_5()
		throws Exception {
		FareCalculator fixture = new FareCalculator();
		double price1 = 1.0;
		double price2 = 1.0;
		boolean isFreqFlier = true;
		long departureTime = 1L;
		int duration = 1;

		double result = fixture.calculateFare(price1, price2, isFreqFlier, departureTime, duration);

		// add additional test code here
		assertEquals(1619101.9, result, 0.1);
	}

	/**
	 * Run the double calculateFare(double,double,boolean,long,int) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/28/14 8:57 AM
	 */
	@Test
	public void testCalculateFare_6()
		throws Exception {
		FareCalculator fixture = new FareCalculator();
		double price1 = 1.0;
		double price2 = 0;
		boolean isFreqFlier = true;
		long departureTime = 1L;
		int duration = 1;

		double result = fixture.calculateFare(price1, price2, isFreqFlier, departureTime, duration);

		// add additional test code here
		assertEquals(1619101.0, result, 0.1);
	}

	/**
	 * Run the double calculateFare(double,double,boolean,long,int) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/28/14 8:57 AM
	 */
	@Test
	public void testCalculateFare_7()
		throws Exception {
		FareCalculator fixture = new FareCalculator();
		double price1 = 1.0;
		double price2 = 0;
		boolean isFreqFlier = false;
		long departureTime = 1L;
		int duration = 1;

		double result = fixture.calculateFare(price1, price2, isFreqFlier, departureTime, duration);

		// add additional test code here
		assertEquals(1619501.0, result, 0.1);
	}

	/**
	 * Run the double calculateFare(double,double,boolean,long,int) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/28/14 8:57 AM
	 */
	@Test
	public void testCalculateFare_8()
		throws Exception {
		FareCalculator fixture = new FareCalculator();
		double price1 = 1.0;
		double price2 = 1.0;
		boolean isFreqFlier = false;
		long departureTime = 1L;
		int duration = 1;

		double result = fixture.calculateFare(price1, price2, isFreqFlier, departureTime, duration);

		// add additional test code here
		assertEquals(1619501.9, result, 0.1);
	}

	/**
	 * Run the double calculateFare(double,double,boolean,long,int) method test.
	 *
	 * @throws Exception
	 *
	 * @generatedBy CodePro at 4/28/14 8:57 AM
	 */
	@Test
	public void testCalculateFare_9()
		throws Exception {
		FareCalculator fixture = new FareCalculator();
		double price1 = 0;
		double price2 = 1.0;
		boolean isFreqFlier = true;
		long departureTime = 1L;
		int duration = 9;

		double result = fixture.calculateFare(price1, price2, isFreqFlier, departureTime, duration);

		// add additional test code here
		assertEquals(1619100.9, result, 0.1);
	}

	/**
	 * Perform pre-test initialization.
	 *
	 * @throws Exception
	 *         if the initialization fails for some reason
	 *
	 * @generatedBy CodePro at 4/28/14 8:57 AM
	 */
	@Before
	public void setUp()
		throws Exception {
		// add additional set up code here
	}

	/**
	 * Perform post-test clean-up.
	 *
	 * @throws Exception
	 *         if the clean-up fails for some reason
	 *
	 * @generatedBy CodePro at 4/28/14 8:57 AM
	 */
	@After
	public void tearDown()
		throws Exception {
		// Add additional tear down code here
	}

	/**
	 * Launch the test.
	 *
	 * @param args the command line arguments
	 *
	 * @generatedBy CodePro at 4/28/14 8:57 AM
	 */
	public static void main(String[] args) {
		new org.junit.runner.JUnitCore().run(FareCalculatorTest.class);
	}
}