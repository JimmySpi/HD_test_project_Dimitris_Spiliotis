package qa.tests;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import qa.base.TestBase;
import qa.businessObjects.VehicleRegistrationBO;

/**
 * Objective of this test is to verify the vehicle registration functionality.
 * After submiting registration number and year, system should display a message indicating if registration was successful or not.
 * Registration number should match a regular plate number matching reggex ^([A-Z]{3})([0-9]{4})$ meaning starting with exactly 3 capital letters in range [A-Z] followed by exactly 4 numbers in range [0-9]
 * Success message with the given data(registration number and date) should be displayed when registration number is valid
 * and error message should be displayed when this is not following the reggex rule or registration number or/and date are not specified.
 */
public class TC_Vehicle_registration extends TestBase {

    private VehicleRegistrationBO vehicleRegistrationBO;


    @BeforeTest(alwaysRun = true)
    public void beforeTest() {
        log.info("Run Test: Vehicle Registration");
        vehicleRegistrationBO = new VehicleRegistrationBO();
    }

    @Test(description = "Verify registration result message", dataProvider = "registration_dates_data")
    public void test_case_1(String registration_num, String registration_date, String expected_message) {
        log.info("TC1_Data_Provided: "+registration_num+" | "+registration_date+" -> "+expected_message);
        vehicleRegistrationBO.registerVehicle(registration_num, registration_date);
        vehicleRegistrationBO.verifyRegistrationResult(expected_message);
    }

    @AfterTest(alwaysRun = true)
    public void afterTest() {
        log.info("Finished Test: Test Vehicle Registration");
    }

    @DataProvider(name = "registration_dates_data")
    public Object[][] dataProvider() {
        return new Object[][]
        {
            {"ASZ0239", "2015", "Success! Selected ASZ0239 with year 2015"},
            {"BBB5555", "2016", "Success! Selected BBB5555 with year 2016"},
            {"ASB1199", "2017", "Success! Selected ASB1199 with year 2017"},
            {"aBC1234", "2016", "There was an error!"},
            {"AB-1234", "2015", "There was an error!"},
            {"ABC/1234", "2016", "There was an error!"},
            {"ABC-1234", "2016", "There was an error!"},
            {"AB1234", "2015", "There was an error!"},
            {"AB11234", "2015", "There was an error!"},
            {"ABC123", "2016", "There was an error!"},
            {"ABC123$", "2016", "There was an error!"},
            {"ABC123T", "2016", "There was an error!"},
            {"ABC123T", "2016", "There was an error!"},
            {"1234ABC", "2016", "There was an error!"},
            {null, "2016", "There was an error!"},
            {"ABC123T", null, "There was an error!"},
            {null, null, "There was an error!"}
        };
    }

}
