/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inter;

import lexico.*;
import tabelaDeSimbolos.Tipo;

/**
 *
 * @author pedro
 */
// Implementa os operadores +, -, *, /
public class Arith extends Op{
    public Expr expr1, expr2;
    public Arith(Token token, Expr expr1, Expr expr2) {
        // token -> token do operador
        // null -> marcador de lugar para tipo
        super(token, null);
        this.expr1 = expr1;
        this.expr2 = expr2;
        // cofere os tipos
        tipo = Tipo.max(this.expr1.tipo, this.expr2.tipo);
        if (tipo == null) error("Erro de Tipo");
    }
    // Constrói o lado direito de uma instrução de 3 endereços
    // Reduzindo as subexpressões para endereços e
    // aplicando o operador à eles
    public Expr gen() {
        return new Arith(op, expr1.reduce(), expr2.reduce());
    }
    public String toString(){
        return expr1.toString()+" "+op.toString()+" "+expr2.toString();
    }
}
