package qa.pageObjects;

public class VehicleRegistrationPO extends BasePO {

    public enum VehicleRegistrationLocators {
        REGISTRATION_INPUT("input#input-number-plates"),
        SELECT_YEAR("select#select-year"),
        SUBMIT_BUTTON("button#btn-submit"),
        REGISTRATION_RESULT_MSG("div.alert[style='display: block;']");

        public final String locator;

        private VehicleRegistrationLocators(String locator) {
            this.locator = locator;
        }
    }

    /**
     * Enters given registration number (if not null) to the relative ui input
     * @param given_registration_number
     */
    public void enterRegistrationNumberToInput(String given_registration_number) {
        if(given_registration_number!=null && !given_registration_number.isEmpty()) {
            input(VehicleRegistrationLocators.REGISTRATION_INPUT.locator, given_registration_number);
        }
    }

    /**
     * Selects given year (if not null) from relative ui selector
     * @param given_registration_year
     */
    public void selectYear(String given_registration_year) {
        if(given_registration_year!=null && !given_registration_year.isEmpty()) {
            select(VehicleRegistrationLocators.SELECT_YEAR.locator, given_registration_year);
        }
    }

    /**
     * Presses submit button
     */
    public void pressSubmitButton() {
        pressButton(VehicleRegistrationLocators.SUBMIT_BUTTON.locator);
    }

    /**
     * Returns the registration result message displayed
     * @return
     */
    public String getRegistrationResultMessageDisplayed() {
        return getText(VehicleRegistrationLocators.REGISTRATION_RESULT_MSG.locator);
    }

}
