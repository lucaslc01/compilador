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
// Operadores  > >= < <= = <>
public class Rel extends Logical{
    public Rel (Token token, Expr x1, Expr x2){
        super(token, x1, x2);
    }
    // Verifica se dois operadores tem o mesmo tipo e se não são arranjos
    public Tipo check(Tipo tipo1, Tipo tipo2){
        if (tipo1 instanceof Array || tipo2 instanceof Array) return null;
        if (tipo1 == tipo2) return Tipo.BOOL;
        return null;
    }
    // Começa gerando o código da subexpressões 
    public void jumping(int t, int f) {
        Expr a = expr1.reduce();
        Expr b = expr2.reduce();
        String test = a.toString()+" "+ op.toString()+" "+ b.toString();
        // Se t e f não forem 0 -> Executa as linhas 38 a 41 de Expr.Java
        // Se t ou f não forem 0 -> Executa as linhas 42 ou 43 de Expr.Java
        // Caso contrário não faz nada -> fall through
        emitjumps(test, t, f);
    }
}
