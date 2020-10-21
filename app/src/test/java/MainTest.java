import com.corrientazo.app.Main;
import org.junit.Test;

import java.io.IOException;

public class MainTest {

    @Test
    public void runningMainTest() throws InterruptedException, IOException {
        String[] args = {"3","10","20","1000"};
        Main.main(args);
    }
}
