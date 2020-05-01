/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.util;

/**
 *
 * @author Roberth Fallas 😊
 */
public class TreeNode {

    private Object dato;
    private String key;
    private TreeNode hijoIzq;
    private TreeNode hijoDer;

    public TreeNode(Object dato, String key) {
        this.dato = dato;
        this.key = key;
        this.hijoDer = null;
        this.hijoIzq = null;
    }

    public TreeNode() {
    }

    public void toMoveData(Object data) {
        this.dato = data;
        this.key = "ok";
        this.hijoDer = null;
        this.hijoIzq = null;
    }

    public Object getDato() {
        return dato;
    }

    public void setDato(Object dato) {
        this.dato = dato;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public TreeNode getHijoIzq() {
        return hijoIzq;
    }

    public void setHijoIzq(TreeNode hijoIzq) {
        this.hijoIzq = hijoIzq;
    }

    public TreeNode getHijoDer() {
        return hijoDer;
    }

    public void setHijoDer(TreeNode hijoDer) {
        this.hijoDer = hijoDer;
    }

    public void resetChidren() {
        this.hijoDer = null;
        this.hijoIzq = null;
    }
}
