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
public class Expr extends Node {
    public Token op;    // Representa o operador de um Nó
    public Tipo tipo;   // Representa o tipo de um Nó

    public Expr(Token op, Tipo tipo) {
        this.op = op;
        this.tipo = tipo;
    }
    // Retorna um termo que pode caber no lado direito de 
    // um comando de três endereços
    public Expr gen() {
        return this;
    }
    // Calcula ou "reduz" uma expressão a um único endereço,
    // ou seja, retorna uma constante, um identificador ou
    // um nome temporário
    public Expr reduce() {
        return this;
    }
    // Gera o código de desvio para expressões boleanas
    public void jumping (int t, int f) {
        emitjumps(toString(), t, f);
    }
    // Gera o código de desvio para expressões boleanas
    public void emitjumps(String test, int t, int f) {
        if (t != 0 && f != 0) {
            emit("if "  + test + "goto L" + t);
            emit("goto L" +  f);
        }
        else if (t != 0) emit("if " + test + "goto L" + t);
        else if (f != 0) emit ("iffalse " + test + " goto L"+f);
        else;
    }
    public String toString() {
        return op.toString();
    }
}
