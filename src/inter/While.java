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
// While Expr do Stmt end
public class While extends Stmt{
    Expr expr;
    Stmt stmt;
    public While(){
        expr = null;
        stmt = null;
    }
    // Define o filho como Expr e Stmt
    public void init(Expr x, Stmt s){
        expr = x;
        stmt = s;
        if (expr.tipo != Tipo.BOOL) expr.error("O WHILE precisa de um booleano");
    }
    // Gera o código de Três endereços
    public void gen(int b, int a) {
        after = a; // Guarda 'a' em 'after'
        expr.jumping(0, a);
        int label = newlabel(); // rótulo para comando
        emitlabel(label);
        stmt.gen(label, b);
        // Código de Stmt é seguido para b para um próxima iteração do loop
        emit("goto L"+b);
    }
}
