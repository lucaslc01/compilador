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
// Atribuições com identificador no lado esquerdo e uma expressão à direita
public class Set extends Stmt {
    public Id id;
    public Expr expr;
    public Set (Id id , Expr expr){
        this.id = id;
        this.expr = expr;
        if (check(id.tipo, expr.tipo) == null) error("Erro de tipo");
    }
    public Tipo check(Tipo p1, Tipo p2) {
        if (Tipo.numerico(p1) && Tipo.numerico(p2)) return p2;
        if (p1 == Tipo.BOOL && p2 == Tipo.BOOL) return p2;
        return null;
    }
    // Gera uma instrução de Três endereços
    public void gen(int b, int a){
        emit(id.toString()+" = "+expr.gen().toString());
    }
}
