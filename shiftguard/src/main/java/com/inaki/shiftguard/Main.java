package com.inaki.shiftguard;

import com.inaki.shiftguard.console.Consola;
import com.inaki.shiftguard.console.MenuCentrosTrabajo;
import com.inaki.shiftguard.console.MenuPrincipal;
import com.inaki.shiftguard.console.MenuVigilantes;
import com.inaki.shiftguard.service.CentroTrabajoService;
import com.inaki.shiftguard.service.VigilanteService;

public class Main {

    public static void main(String[] args) {

        Consola consola = new Consola();

        VigilanteService vigilanteService = new VigilanteService();

        CentroTrabajoService centroTrabajoService = new CentroTrabajoService();

        MenuVigilantes menuVigilantes = new MenuVigilantes(vigilanteService, consola);

        MenuCentrosTrabajo menuCentrosTrabajo = new MenuCentrosTrabajo(centroTrabajoService, consola);

        MenuPrincipal menuPrincipal = new MenuPrincipal(menuVigilantes, menuCentrosTrabajo, consola);

        menuPrincipal.iniciar();

        consola.cerrar();
    }
}