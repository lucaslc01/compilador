/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexico;

/**
 *
 * @author pedro
 */
public class Palavra extends Token{
    public String lexema = "";
    
    public Palavra(String lexema, Tag tag) {
        super(tag);
        this.lexema = lexema;
    }
    public static final Palavra MAIOR_QUE = new Palavra(">", Tag.MAIOR_QUE);
    public static final Palavra MAIOR_IGUAL = new Palavra(">=", Tag.MAIOR_IGUAL);
    public static final Palavra MENOR_QUE = new Palavra("<", Tag.MENOR_QUE);
    public static final Palavra MENOR_IGUAL = new Palavra("<=", Tag.MENOR_IGUAL);
    public static final Palavra DIFERENTE = new Palavra("<>", Tag.DIFERENTE);
    
    @Override
    public String toString() {
        return super.toString() + " Valor = " + lexema;
    }
    
    
}
