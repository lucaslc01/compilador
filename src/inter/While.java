/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inter;

import tabelaDeSimbolos.Tipo;

/**
 *
 * @author pedro
 */
public class While extends Stmt{
    Expr expr;
    Stmt stmt;
    public While(){
        expr = null;
        stmt = null;
    }
    public void init(Expr x, Stmt s){
        expr = x;
        stmt = s;
        if (expr.tipo != Tipo.BOOL) expr.error("O WHILE precisa de um booleano");
    }
    public void gen(int b, int a) {
        after = a;
        expr.jumping(0, a);
        int label = newlabel();
        emitlabel(label);
        stmt.gen(label, b);
        emit("goto L"+b);
    }
}
