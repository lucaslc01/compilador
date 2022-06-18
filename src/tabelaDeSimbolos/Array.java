/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tabelaDeSimbolos;

import lexico.Tag;

/**
 *
 * @author pedro
 */
public class Array extends Tipo{
    public Tipo of;
    public int size = 1;
    public Array (int size, Tipo tipo) {
        super("[]", Tag.INDEX, size*tipo.width);
        this.size = size;
        of = tipo;
    }
    public String toString(){
        return "["+size+"] "+of.toString();
    }
}
