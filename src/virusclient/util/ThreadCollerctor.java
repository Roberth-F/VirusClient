/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Esta clase funcona como colector de hilos, de manera que es posible
 * mantenerlos reunidos todos en un mismo punto.
 *
 * @author Roberth 😊
 */
public class ThreadCollerctor {

    private static ThreadCollerctor INSTANCE;
    private static final List<Thread> THCOLLECT = new ArrayList<>();
    private static final List<Timer> TMCOLLECT = new ArrayList<>();

    private ThreadCollerctor() {
    }

    private static void createInstance() {
        synchronized (ThreadCollerctor.class) {
            if (INSTANCE == null) {
                INSTANCE = new ThreadCollerctor();
            }
        }
    }

    public static ThreadCollerctor getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    public void addThread(Thread th) {
        THCOLLECT.add(th);
        THCOLLECT.removeIf(hilo -> !hilo.isAlive());
    }

    public void addTimer(Timer tm) {
        TMCOLLECT.add(tm);
    }

    public void stopTimers() {
        TMCOLLECT.forEach(tm -> tm.cancel());
        TMCOLLECT.clear();
    }

    public void stopThreads() {
        for (Thread th : THCOLLECT) {
            try {
                th.interrupt();
            } catch (SecurityException SE) {
                System.err.println("Erro al finalizar hilo");
                continue;
            }
        }
        THCOLLECT.removeIf(th -> !th.isAlive());
    }

    public void pauseThreads() {
        for (Thread th : THCOLLECT) {
            try {
                th.interrupt();
            } catch (SecurityException SE) {
                System.err.println("Erro al finalizar hilo");
                continue;
            }
        }
        THCOLLECT.removeIf(th -> !th.isAlive());
    }
}
