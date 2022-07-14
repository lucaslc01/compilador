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
public class If extends Stmt{
    Expr expr;
    Stmt stmt;
    
    // Condicionais If
    public If (Expr x, Stmt s){    
        expr = x;
        stmt = s;
        if (expr.tipo != Tipo.BOOL) expr.error("O If precisa de um booleano");
    }
    // Consiste em código de desvio para Expr seguido pelo código para Stmt
    public void gen(int b, int a) {
        int label = newlabel();
        // Deve seguir o código de expr, se expr for verdadeiro
        // caso contrário deve seguir para o rótulo 'a'
        expr.jumping(0, a);
        emitlabel(label);
        stmt.gen(label, a);
    }
}
