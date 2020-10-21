import com.corrientazo.app.ConsoleAppFactory;
import com.corrientazo.core.AppFactoryPort;
import com.corrientazo.inbound.FileWatcherAdapter;
import com.corrientazo.outbound.FileWriterAdapter;
import com.corrientazo.services.DeliveryServiceAdapter;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ConsoleAppFactoryTest {

    @Test
    public void callFactoryMethods() {
        AppFactoryPort appFactoryPort = new ConsoleAppFactory();
        assertTrue(appFactoryPort.createDeliveryService() instanceof DeliveryServiceAdapter);
        assertTrue(appFactoryPort.createFileWatcher() instanceof FileWatcherAdapter);
        assertTrue(appFactoryPort.createFileWriter() instanceof FileWriterAdapter);
    }
}
