package qa.businessObjects;

import qa.pageObjects.VehicleRegistrationPO;


public class VehicleRegistrationBO extends BaseBO{

    private VehicleRegistrationPO vehicleRegistrationPO;

    public VehicleRegistrationBO() {
        vehicleRegistrationPO = new VehicleRegistrationPO();
    }

    /**
     * Performs the user flow of registering a vehicle at the system
     * @param registration_number
     * @param registration_year
     */
    public void registerVehicle(String registration_number, String registration_year) {
        if((registration_number==null || !registration_number.isEmpty()) || (registration_year==null || !registration_year.isEmpty())) {
            vehicleRegistrationPO.refreshPage();
        }
        vehicleRegistrationPO.waitForElementVisibility(VehicleRegistrationPO.VehicleRegistrationLocators.REGISTRATION_INPUT.locator);
        vehicleRegistrationPO.enterRegistrationNumberToInput(registration_number);
        vehicleRegistrationPO.selectYear(registration_year);
        vehicleRegistrationPO.pressSubmitButton();
    }

    /**
     * Verifies the displayed registration result message
     * @param expected_result_message
     */
    public void verifyRegistrationResult(String expected_result_message) {
        String actual_displayed_result_message = vehicleRegistrationPO.getRegistrationResultMessageDisplayed();
        validator.assertEquals(actual_displayed_result_message, expected_result_message,
                "Expected message: "+expected_result_message+ "\nBut found: "+actual_displayed_result_message);
    }
}
