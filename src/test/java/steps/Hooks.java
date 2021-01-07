package steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import utils.db.DataBaseUtils;

public class Hooks {

    //Implement DB Connection Steps here
    private static boolean isExecuted = false;
    @Before
    public void testExecutionSetup() throws Exception {
        if (!isExecuted) {
            DataBaseUtils.connectToDatabase();
            isExecuted = true;
        }
    }
    @After
    public void testFinishExecutionSetup() throws Exception {
        if (isExecuted) {
            DataBaseUtils.closeConnection();
            isExecuted = false;
        }
    }
}
