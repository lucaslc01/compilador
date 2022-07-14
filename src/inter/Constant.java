/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inter;
import lexico.*;
import tabelaDeSimbolos.*;
/**
 *
 * @author pedro
 */
public class Constant extends Expr{

    public Constant(Token token, Tipo tipo) {
        super(token, tipo);
    }
    // Sobrecarregado para criar um objeto de tipo inteiro
    public Constant(int i) {
        super(new Inteiro(i), Tipo.INT);
    }
    public static final Constant True = new Constant(Palavra.TRUE, Tipo.BOOL);
    public static final Constant False = new Constant(Palavra.FALSE, Tipo.BOOL);
    
    public void jumping(int t, int f){
        // se a constant for true e f != 0 -> Desvio para t
        if (this == True && t != 0){
            emit("goto L"+t);
        } else if (this == False && f != 0){
            // se a constant for false e f != 0 -> Desvio para f
            emit("goto L"+f);
        }
    }
}
