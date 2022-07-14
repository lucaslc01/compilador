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
public class Unary extends Op{
    public Expr expr;
    // Também trata a subtração
    public Unary(Token token, Expr expr) {
        super(token, null);
        this.expr = expr;
        tipo = Tipo.max(Tipo.INT, expr.tipo);
        if (tipo == null) error("Erro de Tipo");
    }
    public Expr gen() {
        return new Unary(op, expr.reduce());
    }
    public String toString() {
        return op.toString()+" "+expr.toString();
    }
}
