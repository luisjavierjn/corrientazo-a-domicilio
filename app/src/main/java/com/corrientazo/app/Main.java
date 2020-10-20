package com.corrientazo.app;

import com.corrientazo.domain.Drone;
import com.corrientazo.domain.Mapa;
import com.corrientazo.domain.Ruta;
import com.corrientazo.inbound.FileAdapter;
import com.corrientazo.inbound.FileEvent;
import com.corrientazo.inbound.FileWatcher;
import com.corrientazo.outbound.FileOutput;
import com.corrientazo.services.EntregaService;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class Main {

    private static final int NUMERO_DE_DRONES = 20;
    private static final int CUADRAS_A_LA_REDONDA = 10; // se puede ampliar o reducir
    private static final int ALMUERZOS_POR_VEZ = 3; // se puede ampliar o reducir
    private static final String RUTA_DE_ARCHIVOS_IN = "app/src/test/resources/in";
    private static final String RUTA_DE_ARCHIVOS_OUT = "app/src/test/resources/out";
    private static final String HEADER = "== Reporte de entregas ==";

    public static void main(String[] args) throws InterruptedException {

        List<Drone> drones = new ArrayList<>();
        int idx = 0;
        while (idx++ < NUMERO_DE_DRONES) {
            drones.add(new Drone(new Mapa(CUADRAS_A_LA_REDONDA)));
        }
        final EntregaService entregaService = new EntregaService(drones);
        final FileOutput fileOutput = new FileOutput(RUTA_DE_ARCHIVOS_OUT, HEADER);

        File folder = new File(RUTA_DE_ARCHIVOS_IN);
        FileWatcher watcher = new FileWatcher(folder);
        watcher.addListener(new FileAdapter() {
            public void onCreated(FileEvent event) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String number = event.getFile().getName().split("\\.",2)[0].substring(2);
                String ext = ".".concat(event.getFile().getName().split("\\.",2)[1]);
                int n = Integer.parseInt(number);
                try (Scanner sc = new Scanner(event.getFile())) {

                    while (sc.hasNextLine()) {
                        entregaService.addNewRuta(n, new Ruta(sc.nextLine()));
                    }
                    entregaService.hacerLasEntregas();
                    entregaService.getDrones().get(n).getPosiciones().forEach(fileOutput::addNewLine);
                    fileOutput.write("out".concat(number).concat(ext));

                } catch (InterruptedException | ExecutionException | IOException e) {
                    e.printStackTrace();
                }

                event.getFile().delete();
            }
        }).watch();

        Main m = new Main();
        System.out.println("Su Corrientazo a Domicilio");
        synchronized (m) {
            m.wait();
        }
        System.out.println("Se terminaron los domicilios por hoy");
    }
}
