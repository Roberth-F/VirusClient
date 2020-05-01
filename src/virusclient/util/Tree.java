/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virusclient.util;

import java.util.function.Consumer;

/**
 *
 * @author Roberth Fallas 😊
 * @param <Tipo> Tipo de dato qe va almacenar el árbol.
 * 
 */
public class Tree<Tipo> {

    private TreeNode raiz;
    private int numNodes;

    public Tree() {
        this.raiz = null;
        numNodes = 0;
    }

    public void insert(String clave, Object dato) {
        if (raiz == null) {
            raiz = new TreeNode(dato, clave);
        } else {
            this.internalInsertion(clave, dato, raiz);
        }
        this.numNodes++;
    }

    private void internalInsertion(String clave, Object dato, TreeNode node) {
        if (clave.equals(node.getKey())) {
            raiz.setDato(dato);
        } else if (clave.compareTo(raiz.getKey()) < 0) {
            if (node.getHijoIzq() != null) {
                internalInsertion(clave, dato, node.getHijoIzq());
            } else {
                node.setHijoIzq(new TreeNode(dato, clave));
            }
        } else {
            if (node.getHijoDer() != null) {
                internalInsertion(clave, dato, node.getHijoDer());
            } else {
                node.setHijoDer(new TreeNode(dato, clave));
            }
        }
    }

    public void delete(String clave) {
        if (!this.isEmpty()) {
            if (this.raiz.getKey().equals(clave)) {
                if (this.raiz.getHijoIzq() != null) {
                    TreeNode aux = this.raiz.getHijoDer();
                    this.raiz = this.raiz.getHijoIzq();
                    this.reacomodar(aux);
                } else {
                    raiz = raiz.getHijoDer();
                }
                this.numNodes--;
            } else {
                this.internalDeletion(clave, raiz);
            }
        }
    }

    private void internalDeletion(String clave, TreeNode nodo) {
        if (nodo != null) {
            if (nodo.getHijoIzq() != null) {
                if (nodo.getHijoIzq().getKey().equals(clave)) {
                    TreeNode izq = nodo.getHijoIzq();
                    if (izq.getHijoIzq() != null) {
                        TreeNode aux = izq.getHijoDer();
                        nodo.setHijoIzq(izq.getHijoIzq());
                        izq.resetChidren();
                        this.reacomodar(aux);
                        this.numNodes--;
                        return;
                    }
                    nodo.setHijoIzq(nodo.getHijoIzq().getHijoDer());
                    this.numNodes--;
                    return;
                }
            }
            if (nodo.getHijoDer() != null) {
                if (nodo.getHijoDer().getKey().equals(clave)) {
                    TreeNode der = nodo.getHijoDer();
                    if (der.getHijoIzq() != null) {
                        TreeNode aux = der.getHijoDer();
                        nodo.setHijoDer(der.getHijoIzq());
                        der.resetChidren();
                        this.reacomodar(aux);
                        return;
                    }
                    nodo.setHijoDer(nodo.getHijoDer().getHijoDer());
                    return;
                }
            }
            this.internalDeletion(clave, nodo.getHijoIzq());
            this.internalDeletion(clave, nodo.getHijoDer());
        }
    }

    private void reacomodar(TreeNode subRaiz) {
        if (subRaiz != null) {
            this.insert(subRaiz.getKey(), subRaiz.getDato());
            this.numNodes--;
            reacomodar(subRaiz.getHijoIzq());
            reacomodar(subRaiz.getHijoDer());
        }
    }

    public Tipo find(String clave) {
        TreeNode toReturn = new TreeNode();
        this.internalFind(clave, raiz, toReturn);
        return ("ok".equals(toReturn.getKey())) ? (Tipo)toReturn.getDato() : null;
    }

    private void internalFind(String clave, TreeNode nodo, TreeNode toReturn) {
        if (nodo != null) {
            if (nodo.getKey().equals(clave)) {
                toReturn.toMoveData(nodo.getDato());
                return;
            }
            this.internalFind(clave, nodo.getHijoIzq(), toReturn);
            this.internalFind(clave, nodo.getHijoDer(), toReturn);
        }
    }

    public int getHeight() {
        throw new UnsupportedOperationException("Not supported yet."); // <----------
    }

    public int getNumNodes() {
        return this.numNodes;
    }

    public boolean isEmpty() {
        return this.raiz == null;
    }

    public void clear() {
        raiz = null;
        numNodes = 0;
    }

    public void forEach(Consumer<Tipo> action) {
        this.internalForEach(action, this.raiz);
    }

    private void internalForEach(Consumer<Tipo> action, TreeNode node){
        if(node != null){
            action.accept((Tipo)node.getDato());
            this.internalForEach(action, node.getHijoIzq());
            this.internalForEach(action, node.getHijoDer());
        }
    }

}
