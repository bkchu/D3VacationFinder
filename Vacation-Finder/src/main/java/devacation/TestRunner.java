package devacation;

/**
 * Created by tantan on 3/22/16.
 */
import org.junit.runner.*;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(Algorithm.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println("Tests succeeded: " + result.wasSuccessful());
    }
}