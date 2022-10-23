HD_test_project_Dimitris_Spiliotis

Project Structure.
This project is build with maven including necessary dependencies in POM.xml for Selenium and TESTNG
Report is generated at test-output folder ("emailable-report.html") after run

src\Drivers - Project Drivers
--Here are placed the selenium drivers executables. Already placed is chromedriver.exe

src\Files - Project Files
--Here there is the test html page used for testing by opening it from the local path to the browser for testing, as there is no true web page.
--This is done at TestBase init method executed by beforeSuite (@BeforeSuite annotated), by making the BASE_URL of the application like this:
-- "BASE_URL = "file:///"+System.getProperty("user.dir") + "/src/files/" + properties.getProperty("url");" | TestBase:line:59 in method init called by beforeSuite(@BeforeSuite annotated)
-- That way using System.getProperty("user.dir") can run on any pc instead of hardcoding mine, and with

src\main\java\qa\config - Properties file folder
--here we can specify a properties file for each different suite we have in test project. Here is only ony. This helps having different properties specified exactly
--for each suite specifications and needs. He have only one suite thus only one file "vehicle_registration_suite.properties"
--We init properties name file by getting running testng xml suite name and adjusting it to match properties file name format:
--String propertiesFileName = ctx.getSuite().getName().replace(" ","_").toLowerCase(); | TestBase:line:34 in method:beforeSuite(@BeforeSuite annotated)

src\main\resources - VehicleRegistrationSuite.xml
--this is the testng.xml used to run/debug test it has a suite named "Vehicle Registration Suite" and one test named: "TC_Vehicle_registration"

src\main\java\qa\base\TestBase.java
--This is the base test class from which all tests inherit.
--@BeforeSuite() public void beforeSuite(ITestContext ctx) is the method that runs before suite starts from testng runner as annotated with relative annotation
--We use ITestContext to be able to get current running suite name from current running testng file (ctx.getSuite().getName())
--Loads props file into a properties object to use after to retrieve properties
--Calls init() which inits driver and its details like timeouts, constructs the BASE_URL which hits (driver.get(BASE_URL)) and inits webDriverWait object as well.
--Browser gets init by called getDriver(browserName) where we pass the browser name from properties to init the appropriate browser if we have many to choose (chrome, firefox etc)


Page Object / Business Object Methodology
--I try to follow a POM testing design.

src\java\qa\pageObjects:
--Each page (in our example only one) is represented by a page object which contains the page locators and available actions on it
--Each page object extends Page Object which is the base object encapsuling selenium commands in methods, so these methods to be used by the page objects
--in order to minimize selenium commands writing, for code readability at POs and for customizing actions for common use by any PO if necessary to reduce duplicate code.
--Also that way if a locator or a certain action on a specific page changes, there is only one change need to be done in that PO and wherever that used will be ok

src\java\qa\businessObjects:
--I also use Business Objects (BO) which is a higher level of page objects (PO).
--The logic is to leave the actions on page seperated from the user flow, so at BO each method is a user action (business method)
--which may contain multiple page actions in a specified sequence.
--Each BO has one or more POs as a user flow may happen to need more than one pages (or different parts of the page) to be completed
--Each BO extends BaseBO which can contain common methods across all BOs/ here i've put as an example a SoftAssertor that could be used at verify methods of BOs.
--Verify methods are at the BOs.
--Each test has BOs but no POs so it can perform the user\business actions\flow without having to know the page spcifications for test readability and maintainability

src\test\java\qa\tests - TC_Vehicle_registration
--This is the test script for automating given page functionality testing
--It has @BeforeTest(alwaysRun = true) public void beforeTest() (which runs after beforeSuite run of its parent BaseTest) and inits the VehicleRegistrationBO business object
--It also has a @AfterTest(alwaysRun = true) public void afterTest() which prints a log and run before afterSuite of its parent baseTest which closed the driver
--It has 1 parametrized test_step retrieving data from the specified data provider method:
--@Test(description = "Verify registration result message", dataProvider = "registration_dates_data")
--public void test_case_1(String registration_num, String registration_date, String expected_message)

--the data provider gives 3 string data per run which are registration_number, registration_year and expected_result_message which is the expected result message to be displayed
--The objective of this test in written with javadoc on the test class and is to verify displayed message for given registration number and year.
--I used as test basis requirement the regex and logic I found at the script on html that the registration
$(document).ready(function () {
  $('#hd-form').submit(function () {
    var carreg = new RegExp('^([A-Z]{3})([0-9]{4})$');
    if (!carreg.test($('#input-number-plates').val()) || !$('#select-year').val()) {
      $('.alert-danger').show();
      $('.alert-success').hide();
    } else {
      $('.alert-danger').hide();
      $('.alert-success').text('Success! Selected ' + $('#input-number-plates').val() + ' with year ' + $('#select-year').val());
      $('.alert-success').show();
    }
    return false;
  });
});

--So success message will be only when year is selected and registration number is given and it matched the regex (3 capital letters in [A-Z] followed by 4 numbers)
--So the test in high level language is Given <registration number> And <year> When user presses submit Then <result_message> should be displayed. parametrized with data coming from a dataProvider

--Data provider data Test-Cases:
(used boundary values as A,Z,0,9, repeatable number/letters, added bad non valid chars and less or more letters/numbers) as well as non selecting reg number or/and year.
{"ASZ0239", "2015", "Success! Selected ASZ0239 with year 2015"},
{"BBB5555", "2016", "Success! Selected BBB5555 with year 2016"},
{"ASB1199", "2017", "Success! Selected ASB1199 with year 2017"},
{"aBC1234", "2016", "There was an error!"},
{"AB-1234", "2015", "There was an error!"},
{"ABC/1234", "2016", "There was an error!"},
{"ABC-1234", "2016", "There was an error!"},
{"AB1234", "2015", "There was an error!"},
{"AB11234", "2015", "There was an error!"},
{"AB11234", "2015", "There was an error!"},
{"ABC123", "2016", "There was an error!"},
{"ABC123$", "2016", "There was an error!"},
{"ABC123T", "2016", "There was an error!"},
{"ABC123T", "2016", "There was an error!"},
{"1234ABC", "2016", "There was an error!"},
{null, "2016", "There was an error!"},
{"ABC123T", null, "There was an error!"},
{null, null, "There was an error!"}

--For implementation details I've tried to put javadoc comments in methods inside the classes

--project is configured to output report html at test-output folder (emailable-report.html)