package virusclient.util;

import virusclient.VirusClient;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.logging.Level;

public class AppContext {

    private static AppContext INSTANCE = null;
    private static HashMap<String, Object> context = new HashMap<>();

    private AppContext() {
        cargarPropiedades();
    }

    private static void createInstance() {
        synchronized (AppContext.class) {
            if (INSTANCE == null) {
                INSTANCE = new AppContext();
            }
        }
    }

    public static AppContext getInstance() {
        if (INSTANCE == null) {
            createInstance();
        }
        return INSTANCE;
    }

    private void cargarPropiedades() {
        try {
            Properties appProperties = new Properties();
            appProperties.load(VirusClient.class.getResourceAsStream("/configuration/RedConfig.ini"));
            if (appProperties.getProperty("serverIP") != null) {
                this.set("ServerIP", appProperties.getProperty("serverIP"));
                this.set("selfIp", appProperties.getProperty("selfIp"));
            }
        } catch (IOException io) {
            System.out.println("Archivo de configuración de red no encontrado.");
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    public Object get(String parameter) {

        Object object = context.get(parameter);
        if (object == null && parameter.contains("Service")) {
            synchronized (AppContext.class) {
                object = context.get(parameter);
                if (object == null) {
                    try {
                        try {
                            object = Class.forName("unaplanilla2.service." + parameter).newInstance();
                            context.put(parameter, object);
                        } catch (InstantiationException | IllegalAccessException ex) {
                            java.util.logging.Logger.getLogger(AppContext.class.getName()).log(Level.SEVERE, "Creando Service [" + parameter + "].", ex);
                        }
                    } catch (ClassNotFoundException ex) {
                        java.util.logging.Logger.getLogger(AppContext.class.getName()).log(Level.SEVERE, "Creando Service [" + parameter + "].", ex);
                    }
                }
            }
        }
        return object;
    }

    public void set(String nombre, Object valor) {
        context.put(nombre, valor);
    }

    public void delete(String parameter) {
        context.remove(parameter);
    }

}
