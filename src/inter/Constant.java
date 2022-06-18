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
    public Constant(int i) {
        super(new Inteiro(i), Tipo.INT);
    }
    public static final Constant True = new Constant(Palavra.TRUE, Tipo.BOOL);
    public static final Constant False = new Constant(Palavra.FALSE, Tipo.BOOL);
    
    public void jumping(int t, int f){
        if (this == True && t != 0){
            emit("goto L"+t);
        } else if (this == False && f != 0){
            emit("goto L"+f);
        }
    }
}
