package com.inaki.shiftguard;

import com.inaki.shiftguard.console.Consola;
import com.inaki.shiftguard.console.MenuCentrosTrabajo;
import com.inaki.shiftguard.console.MenuPrincipal;
import com.inaki.shiftguard.console.MenuTurnos;
import com.inaki.shiftguard.console.MenuVigilantes;
import com.inaki.shiftguard.service.CentroTrabajoService;
import com.inaki.shiftguard.service.TurnoService;
import com.inaki.shiftguard.service.VigilanteService;

public class Main {

    public static void main(String[] args) {

        Consola consola = new Consola();

        VigilanteService vigilanteService = new VigilanteService();

        CentroTrabajoService centroTrabajoService = new CentroTrabajoService();

        TurnoService turnoService = new TurnoService(vigilanteService, centroTrabajoService);

        MenuVigilantes menuVigilantes = new MenuVigilantes(vigilanteService, consola);

        MenuCentrosTrabajo menuCentrosTrabajo = new MenuCentrosTrabajo(centroTrabajoService, consola);

        MenuTurnos menuTurnos = new MenuTurnos(turnoService, consola);

        MenuPrincipal menuPrincipal = new MenuPrincipal(menuVigilantes, menuCentrosTrabajo, menuTurnos, consola);

        menuPrincipal.iniciar();

        consola.cerrar();
    }
}