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
public class Id extends Expr{
    public int offset; // Endereço Relativo

    public Id(Palavra id, Tipo tipo, int offset) {
        super(id, tipo);
        this.offset = offset;
    }
    
}
