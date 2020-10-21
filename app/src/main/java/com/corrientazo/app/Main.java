package com.corrientazo.app;

import com.corrientazo.core.AppFactoryPort;
import com.corrientazo.core.DeliveryServicePort;
import com.corrientazo.core.FileWatcherPort;
import com.corrientazo.core.FileWriterPort;
import com.corrientazo.domain.Drone;
import com.corrientazo.domain.Grid;
import com.corrientazo.domain.Route;
import com.corrientazo.support.FileEvent;
import com.corrientazo.support.FileListener;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {

    private static final String FILES_PATH_IN = "app/src/test/resources/in";
    private static final String FILES_PATH_OUT = "app/src/test/resources/out";
    private static final String HEADER = "== Reporte de entregas ==";

    private static AppFactoryPort consoleAppFactory = new ConsoleAppFactory();

    public static void main(String[] args) throws InterruptedException, IOException {

        final int NUMBER_OF_LUNCHES = args.length>0 ? Integer.parseInt(args[0]) : 3;
        final int NUMBER_OF_BLOCKS = args.length>1 ? Integer.parseInt(args[1]) : 10;
        final int NUMBER_OF_DRONES = args.length>2 ? Integer.parseInt(args[2]) : 20;
        final int WAIT_VALUE = args.length>3 ? Integer.parseInt(args[3]) : 0;

        List<Drone> drones = new ArrayList<>();
        int idx = 0;
        while (idx++ < NUMBER_OF_DRONES) {
            drones.add(new Drone(new Grid(NUMBER_OF_BLOCKS)));
        }
        final DeliveryServicePort deliveryService = consoleAppFactory.createDeliveryService();
        deliveryService.setDrones(drones);
        final FileWriterPort fileWriter = consoleAppFactory.createFileWriter()
                .setPathAndHeader(FILES_PATH_OUT, HEADER);

        File folder = new File(FILES_PATH_IN);
        FileWatcherPort watcher = consoleAppFactory.createFileWatcher().setFolder(folder);
        watcher.addListener(new FileListener() {
            public void onCreated(FileEvent event) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String number = event.getFile().getName().split("\\.",2)[0].substring(2);
                String ext = ".".concat(event.getFile().getName().split("\\.",2)[1]);
                int n = Integer.parseInt(number);
                if(n > NUMBER_OF_DRONES) {
                    System.out.println(event.getFile().getName()
                            .concat(" no existe el Drone ")
                            .concat(Integer.toString(n)));
                    return;
                }

                try (Scanner sc = new Scanner(event.getFile())) {

                    List<String> routes = new ArrayList<>();
                    while (sc.hasNextLine()) {
                        routes.add(sc.nextLine());
                    }

                    int i=0;
                    while(i < routes.size()) {
                        int j=0,k=i+j;
                        while(j < NUMBER_OF_LUNCHES && k < routes.size()) {
                            deliveryService.addNewRoute(n, new Route(routes.get(k)));
                            j++;
                            k=i+j;
                        }
                        i=k;

                        deliveryService.makeDeliveries();
                        deliveryService.getDrones().get(n).getPositions().forEach(fileWriter::addNewLine);
                        fileWriter.write("out".concat(number).concat(ext));
                    }

                } catch (InterruptedException | ExecutionException | IOException e) {
                    e.printStackTrace();
                }

                event.getFile().delete();
            }
        }).watch();

        Main m = new Main();
        System.out.println("Su Corrientazo a Domicilio");
        synchronized (m) {
            m.wait(WAIT_VALUE);
        }
        System.out.println("Se terminaron los domicilios por hoy");
    }
}
