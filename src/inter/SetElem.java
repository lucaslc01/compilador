/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inter;

import tabelaDeSimbolos.*;

/**
 *
 * @author pedro
 */
public class SetElem extends Stmt {
    public Id array;
    public Expr index;
    public Expr expr;
    public SetElem (Access x , Expr expr){
        array = x.array;
        index = x.index;
        this.expr = expr;
        if (check(x.tipo, expr.tipo) == null) error("Erro de tipo");
    }
    public Tipo check(Tipo p1, Tipo p2) {
        if (p1 instanceof Array || p2 instanceof Array) return null;
        if (Tipo.numerico(p1) && Tipo.numerico(p2)) return p2;
        if (p1 == Tipo.BOOL && p2 == Tipo.BOOL) return p2;
        return null;
    }
    public void gen(int b, int a){
        String s1 = index.reduce().toString();
        String s2 = expr.reduce().toString();
        emit(array.toString()+" ["+s1+"] = "+s2);
    }
}
