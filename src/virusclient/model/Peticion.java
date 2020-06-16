/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.model;

import com.google.gson.annotations.SerializedName;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import virusclient.util.AppContext;

/**
 * Una instancia de esta clase soporta ser enviada por la red en formato Json
 * usando para su serialización la biblioteca Gson de Google. Una vez en el
 * servidor la instancia es capaz de aportar información que le permita al
 * servidor invocar un metodo específico.
 *
 * @author Roberth :)
 *
 */
public class Peticion {

    @SerializedName("invocar_a")
    private String metodo;          //Metodo del servidor que se desea llamar.
    @SerializedName("Ipv4")
    private String ip;              //IP de cliente.
    @SerializedName("puerto")
    private int puerto;             //Puerto en el está escuchando cliente.
    @SerializedName("jugador")
    private String nombreJugador;   //Campo solo nesesario cuando se va a unir o crear partida.
    @SerializedName("puertoImediato")
    private int puertoImadiato;  //En caso de ser petición con respuesta a este puerto se envia la respuerta.
    @SerializedName("avatar")
    private String nombreAvatar;
@SerializedName("jugadores")
    private List<Jugador> jugadores;
    public Peticion() {
    }

    /**
     * Su finalidad es crear una petición para agregar al jugador a una partida
     * en espera o bien crear una partida.
     *
     * @param ServMethod Método del servidor que va ser llamado, una vez la
     * petición llegue a su destino.
     * @param puertoEspera Puerto de espera del jugador, (este puerto no debe
     * variar a lo largo del juego o se perderá la conexión).
     * @param nombreJugador Nombre del jugador que desea inscribirse en la
     * partida.
     * @param puertoImediato Puerto al de cliente al que se le enviará de
     * imadiato la respuesta de si puede o no unirse a la partida.
     * @param avatar Nombre de la imagen del jugador
     *
     */
    public void addToGame(String ServMethod, int puertoEspera, String nombreJugador, int puertoImediato, String avatar) {
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException UE) {
            System.err.println("ERROR OBTENIENDO DIRECCIÓN IP DEL EQUIPO");
            Logger.getLogger(Peticion.class.getName()).log(Level.SEVERE, UE.getMessage(), UE);
        }
        this.puerto = puertoEspera;
        this.metodo = ServMethod;
        this.nombreJugador = nombreJugador;
        this.puertoImadiato = puertoImediato;
        this.nombreAvatar = avatar;
    }

    /**
     * Genera una peteción que le dirá al servidor que el jugdor está listo para
     * jugar.
     */
    public void yoEstoyListo() {
        this.nombreJugador = ((Jugador) AppContext.getInstance().get("jugador")).getNombre();
        this.metodo = "nuevoJugadorListo";
    }

    /**
     * Crea una petición que le indica al servidor que este jugador se está
     * retirando de la partida actual.
     */
    public void peticionSalida() {
        try {
            this.metodo = "desconectarJugador";
            this.ip = InetAddress.getLocalHost().getHostAddress();
            this.puerto = (int) AppContext.getInstance().get("mainPort");
        } catch (UnknownHostException ex) {
            Logger.getLogger(Peticion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Genera peteción capaz de pedir al servidor que intente iniciar el juego.
     *
     * @param puertoImediato puerto por el cual se va recibir la respuesta del
     * servidor.
     */
    public void startGame(int puertoImediato) {
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Peticion.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.puertoImadiato = puertoImediato;
        this.metodo = "startGame";
    }

    /**
     * Modifica la peticion de manera que esta ahora solicita al servidor
     * iniciar el juego en todos los equipos conectados.
     */
    public void peticionDeJuego() {
        this.metodo = "forzarInicio";
    }

    /**
     * Obtiene nombre del método que desea ser llamado
     *
     * @return String con el nombre del método que desea ser llamado.
     */
    public String getMetodo() {
        return metodo;
    }

    /**
     * Introduce o cambia nombre de método al que se le llamará en el servidor.
     *
     * @param metodo Nombre del método al que se desea invocar en el servidor.
     */
    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    /**
     * Retorna Ip del equipo del cual proviene la petición.
     *
     * @return String con dirección IPv4 del que proviene la petición o null en
     * caso de que la Ipv4 no esté especificada en esta petición.
     */
    public String getIp() {
        return ip;
    }

    /**
     * Permite introducir Ipv4 de manera manual.
     *
     * @param ip String que represente dirección Ipv4.
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Retorna número de puerto asociado con esta petición.
     *
     * @return Integer con numero de puerto o 0 en el caso de que este no se
     * halla especidicado.
     */
    public int getPuerto() {
        return puerto;
    }

    /**
     * Permite introducir o cambiar de menera manual el número de puerto de
     * escucha del cliente.
     *
     * @param puerto numero que representa el puerto de escucha del cliente.
     */
    public void setPuerto(int puerto) {
        this.puerto = puerto;
    }

    /**
     * Permite obtener el nombre del jugador en caso de que esté especificado en
     * la petición.
     *
     * @return String con el nombre del jugador o null en caso de que este no
     * esté especificado en la petición.
     */
    public String getNombreJugador() {
        return nombreJugador;
    }

    /**
     * Permite introducir o cambiar de manera manual el nombre del jugador
     * asociado a la petición.
     *
     * @param nombreJugador
     */
    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    public int getPuertoImadiato() {
        return puertoImadiato;
    }

    public void setPuertoImadiato(int puertoImadiato) {
        this.puertoImadiato = puertoImadiato;
    }

    public String getNombreAvatar() {
        return nombreAvatar;
    }
       public void addActualizacion(List<Jugador>jugadores) {
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException UE) {
            System.err.println("ERROR OBTENIENDO DIRECCIÓN IP DEL EQUIPO");
            Logger.getLogger(Peticion.class.getName()).log(Level.SEVERE, UE.getMessage(), UE);
        }
  
        this.metodo = "actualizarLista";
        this.jugadores = jugadores;
       // this.puertoImadiato = puertoImediato;
        
    }
   public void solicitarCarta(int puertoImediato) {
        try {
            this.ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Peticion.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.puertoImadiato = puertoImediato;
        this.metodo = "solicitarCarta";
    }
}
