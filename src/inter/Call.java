/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inter;

import lexico.Token;

/**
 *
 * @author pedro
 */
public class Call extends Stmt{
    Expr expr;
    Token tok;
    public Call(Token tok, Expr expr) {
        this.tok = tok;
        this.expr = expr;
    }
    
    public void gen(){
        emit("Call "+tok.tag+", "+ expr);
    }
    
}
