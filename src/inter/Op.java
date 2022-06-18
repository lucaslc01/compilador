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
public class Op extends Expr{
    public Op (Token token, Tipo tipo) {
        super(token, tipo);
    }
    public Expr reduce() {
        Expr x = gen();
        Temp t = new Temp(tipo);
        emit( t.toString() + " = " + x.toString());
        return t;
    }
}
