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
public class Or extends Logical{
    public Or (Token token, Expr x1, Expr x2) {
        super(token, x1, x2);
    }
    // Gera o Código de desvio para a expressão
    // B := B_1 or B_2
    public void jumping(int t, int f) {
        int label = t != 0 ? t : newlabel();
        expr1.jumping(label, 0);
        expr2.jumping(t, f);
        if (t == 0) emitlabel(label);
    }
}
