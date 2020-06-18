/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.util;

import virusclient.model.Jugador;

/**
 *
 * @author roberth 😊
 */
public class Lineamientos {

    private static boolean haJugado = false;
    private static boolean botoCartas = false;
    private static boolean enTurno = false;

    public static boolean puedeTomarCartas(Jugador jugador) {
        if (enTurno) {
            if (jugador.getCartasActuales().size() < 3) {
                return true;
            }
            new MensajePopUp().notifyMensajeInformacion("Noop", "Ya tienes cartas suficinetes.");
            return false;
        }
        new MensajePopUp().notifyMensajeInformacion("No pudes hacer eso", "No es tu turno aún");
        return false;
    }

    public static boolean puedeDesecharCartas() {
        return !haJugado && enTurno;
    }

    public static boolean esMomentoDeCambiarTurno(Jugador jugador) {
        return jugador.getCartasActuales().size() == 3;
    }

    public static boolean puedeJugar() {
        return enTurno && !botoCartas;
    }

    public static void enTurno(boolean turno) {
        enTurno = turno;
        haJugado = false;
        botoCartas = false;
    }

    public static void setJugando(boolean jugando) {
        haJugado = jugando;
    }

    public static void setBotoCartas(boolean haBotadoCartas) {
        botoCartas = haBotadoCartas;
    }

    public static boolean isJugando() {
        return haJugado;
    }
    
}
