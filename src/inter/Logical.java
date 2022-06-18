/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inter;

import lexico.Token;
import tabelaDeSimbolos.Tipo;

/**
 *
 * @author pedro
 */
public class Logical extends Expr{
    public Expr expr1, expr2;

    public Logical(Token token, Expr expr1, Expr expr2) {
        super(token, null);
        this.expr1 = expr1;
        this.expr2 = expr2;
        tipo = check(expr1.tipo, expr2.tipo);
        if (tipo == null) error("Erro de Tipo");
    }
    public Tipo check(Tipo tipo1, Tipo tipo2) {
        if (tipo1 == Tipo.BOOL && tipo2 == Tipo.BOOL) {
            return Tipo.BOOL;
        }
        else return null;
    }
    public Expr gen() {
        int f = newlabel();
        int a = newlabel();
        Temp temp = new Temp(tipo);
        this.jumping(0, f);
        emit(temp.toString()+ " = true");
        emit("goto L" + a);
        emitlabel(f);
        emit(temp.toString()+ " = false");
        emitlabel(a);
        return temp;
    }
    
    public String toString() {
        return expr1.toString()+" "+op.toString()+" "+expr2.toString();
    }
    
}
