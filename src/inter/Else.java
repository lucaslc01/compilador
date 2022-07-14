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
public class Else extends Stmt {
    Expr expr;
    Stmt stmt1, stmt2;
    
    // Condicionais If-Else
    public Else(Expr expr, Stmt stmt1, Stmt stmt2) {
        this.expr = expr;
        this.stmt1 = stmt1;
        this.stmt2 = stmt2;
        if (expr.tipo != Tipo.BOOL) expr.error("O IF precisa de um booleano");
    }
    public void gen(int b, int a){
        int label1 = newlabel(); // Label para o primeiro stmt
        int label2 = newlabel(); // label para o segundo stmt
        expr.jumping(0, label2); // se expr for verdadeira segue para stmt1
        emitlabel(label1);
        stmt1.gen(label1, a);
        emit("goto L"+a);
        emitlabel(label2);
        stmt2.gen(label2, a);
    }
    
}
