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
// Repeat Stmt Until Expr
public class Repeat extends Stmt{
    Expr expr;
    Stmt stmt;
    public Repeat() {
        expr = null;
        stmt = null;
    }
    public void init(Stmt s, Expr x) {
        expr = x;
        stmt = s;
        if (expr.tipo != Tipo.BOOL) expr.error("O REPEAT precisa de um booleano");
    }
    public void gen(int b, int a) {
        after = a;
        int label = newlabel(); // Rótulo para Expr
        stmt.gen(b, label);
        emitlabel(label);
        expr.jumping(b, 0);
    }
}
