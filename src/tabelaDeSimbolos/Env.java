/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabelaDeSimbolos;

import java.util.Hashtable;
import lexico.*;
import inter.*;
/**
 *
 * @author pedro
 */
public class Env {
    private Hashtable table;
    protected Env prev;
    public Env (Env n) {
        table = new Hashtable();
        prev = n;
    }
    
    public void put (Token token, Id i) {
        table.put(token, i);
    }
    public Id get(Token token) {
        for (Env e = this; e != null; e = e.prev) {
            Id found = (Id) e.table.get(token);
            if (found != null) return found;
        }
        return null;
    }
}
