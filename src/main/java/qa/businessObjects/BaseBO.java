package qa.businessObjects;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

public class BaseBO {

    protected SoftAssert softValidator;
    protected Assert validator;

    public BaseBO() {
        softValidator = new SoftAssert();
    }

}
