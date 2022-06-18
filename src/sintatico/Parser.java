/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sintatico;

import inter.*;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import lexico.*;
import tabelaDeSimbolos.*;

/**
 *
 * @author pedro
 */
public class Parser {
    private Lexer lexico; 
    private Token look;
    Env top = null; // Tabela de símbolos corrente
    int used = 0;   // Memória usada para declarações
    private static Vector<Tag> relop = new Vector<Tag>();
    private static Vector<Tag> addop = new Vector<Tag>();
    private static Vector<Tag> mulop = new Vector<Tag>();
    private static Vector<String> Erros = new Vector<String>();
    
    public Parser(Lexer lexico) throws IOException{
        relop.add(Tag.MAIOR_IGUAL);
        relop.add(Tag.MAIOR_QUE);
        relop.add(Tag.MENOR_IGUAL);
        relop.add(Tag.MENOR_QUE);
        relop.add(Tag.IGUAL);
        relop.add(Tag.DIFERENTE);
        addop.add(Tag.SOMA);
        addop.add(Tag.SUB_HIFEN);
        addop.add(Tag.OR);
        mulop.add(Tag.MULTIPLICACAO);
        mulop.add(Tag.DIVISAO);
        mulop.add(Tag.AND);
        this.lexico = lexico;
        move();
        
    }
    public void move() throws IOException {
        try {
            do {
                look = lexico.scan();
            } while(look == null);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    public void error(String s) throws IOException{
        Erros.add("Próximo à linha "+Lexer.linha+": "+s+"\n");
    }
    public void match(Tag t) throws IOException{
        if ((Lexer.erros.size()>0 && 
                (Lexer.erros.get(Lexer.erros.size()-1).contains(Tag.FIM_INESPERADO_DE_ARQUIVO.name()))) ||
                 look.tag != Tag.FIM_DE_ARQUIVO) {
            if (look.tag != t){
                error("Erro sintático - Era esperado um "+t);
            } 
            move();
        } else {
            System.out.println("Erros Léxicos");
            for(String msg : Lexer.erros){
                System.out.print("\t"+msg);
            }
            System.out.println("Final Inesperado de Arquivo");
            if (Erros.size() > 0) {
                System.out.println("Erros Sintáticos");
                for(String msg : Erros){
                    System.out.print("\t"+msg);
                }
            }
            System.exit(0);
        }
    }
    public void program() throws IOException{
        // program ::= **routine** body
        match(Tag.ROUTINE);
        Env savedEnv = top;
        top = new Env(top);
        Stmt s = body();
        /**top = savedEnv;
        int begin = s.newlabel();
        int after = s.newlabel();
        s.emitlabel(begin);
        s.gen(begin, after);
        s.emitlabel(after);**/
        if (Lexer.erros.size()>0){
            System.out.println("Erros Léxicos");
            for(String msg : Lexer.erros){
                System.out.print("\t"+msg);
            }
        }
        if (Erros.size() > 0) {
            System.out.println("Erros Sintáticos");
            for(String msg : Erros){
                System.out.print(msg);
            }
        }
        System.out.println("Terminou");
    }
    public Stmt body() throws IOException{
        // body ::= [decl-list] **begin** stmt-list **end**
        decl_list();
        match(Tag.BEGIN);
        Stmt s = stmt_list();
        match(Tag.END);
        return s;
    }
    public void decl_list() throws IOException{
        // decl-list ::= **declare** decl ";" { decl ";"}
        match(Tag.DECLARE);
        while(look.tag != Tag.BEGIN) {
            decl();
            match(Tag.PONTO_VIRGULA);
        }
    }
    public void decl() throws IOException{
        // decl ::= [**int** | **float** | **char**] ident-list
        match(Tag.BASIC);
        ident_list();
    }
    public void ident_list() throws IOException{
        // ident-list ::= identifier {"," identifier}
        match(Tag.IDENTIFICADOR);
        while(look.tag == Tag.VIRGULA) {
            match(Tag.VIRGULA);
            match(Tag.IDENTIFICADOR);
        } 
    }
    public Stmt stmt_list() throws IOException{
        // stmt-list ::= stmt {";" stmt}
        stmt();
        
        while(look.tag == Tag.PONTO_VIRGULA){
            match(Tag.PONTO_VIRGULA);
            stmt();
        }
        
        return null;
    }
    public void stmt() throws IOException{
        // stmt ::= assign-stmt | if-stmt | while-stmt |
        //          repeat-stmt | read-stmt | write-stmt
        if (look.tag == Tag.IDENTIFICADOR){
            assign_stmt();
        } else if (look.tag == Tag.IF){
            if_stmt();
        } else if (look.tag == Tag.WHILE){
            while_stmt();
        } else if (look.tag == Tag.REPEAT){
            repeat_stmt();
        } else if (look.tag == Tag.READ){
            read_stmt();
        } else if (look.tag == Tag.WRITE){
            write_stmt();
        } else {
            error("Esperava um IF, WHILE, REPEAT, READ, WRITE");
        }  
    }
    public void assign_stmt() throws IOException{
        // assign-stmt ::= identifier ":=" simple_expr
        match(Tag.IDENTIFICADOR);
        match(Tag.ATRIBUICAO);
        simple_expr();
    }
    public void if_stmt() throws IOException{
        // if-stmt ::= if condition then stmt-list end | 
        //             if condition then stmt-list else stmt-list end
        match(Tag.IF);
        condition();
        match(Tag.THEN);
        stmt_list();
        if (look.tag == Tag.ELSE) {
            match(Tag.ELSE);
            stmt_list();
        }
        match(Tag.END);
    }
    public void condition() throws IOException{
        // condition ::= expression
        expression();
    }
    public void repeat_stmt() throws IOException{
        // repeat-stmt ::= **repeat** stmt-list stmt-suffix
        match(Tag.REPEAT);
        stmt_list();
        stmt_suffix();
    }
    public void stmt_suffix() throws IOException{
        // stmt-suffix ::= **until** condition
        match(Tag.UNTIL);
        condition();
    }
    public void while_stmt() throws IOException{
        // while-stmt ::= stmt-prefix stmt-list **end**
        stmt_prefix();
        stmt_list();
        match(Tag.END);
    }
    
    public void stmt_prefix() throws IOException{
        // stmt-prefix ::= **while** condition **do**
        match(Tag.WHILE);
        condition();
        match(Tag.DO);
    }
    public void read_stmt() throws IOException{
        // read-stmt ::= **read** "(" identifier ")
        match(Tag.READ);
        match(Tag.ABRE_PARENTESES);
        match(Tag.IDENTIFICADOR);
        match(Tag.FECHA_PARENTESES);
        
    }
    
    public void write_stmt() throws IOException{
        // write-stmt ::= write "(" writable ")"
        match(Tag.WRITE);
        match(Tag.ABRE_PARENTESES);
        writable();
        match(Tag.FECHA_PARENTESES);
        
    }
    
    public void writable() throws IOException{
        // writable ::= simple-expr | literal
        if (look.tag == Tag.LITERAL){
            match(Tag.LITERAL);
        }
        else
            simple_expr();
    }
    
    public void expression() throws IOException{
        // expression ::= simple-expr | simple-expr relop simple-expr
        simple_expr();
        while (relop.contains(look.tag)){
            match(look.tag);
            simple_expr();
        }
    }

    public void simple_expr() throws IOException{
        // simple-expr ::= term | simple-expr addop term
        // simple-expr ::= term simple-expr-linha
        term();
        simple_expr_linha();
    }
    
    public void simple_expr_linha() throws IOException{
        // simple-expr ::= term | simple-expr addop term
        // simple-expr-linha ::= addop term simple-expr-linha
        if (addop.contains(look.tag)){
            match(look.tag);
            term();
            simple_expr_linha();
        }
        
    }
    
    public void term() throws IOException{
        // term ::= factor-a | term mulop factor-a
        // term ::= factor-a term-linha
        factor_a();
        term_linha();
    }
    public void term_linha () throws IOException{
        // term ::= factor-a | term mulop factor-a
        // term-linha ::= mulop factor-a term_linha
        if (mulop.contains(look.tag)){
            match(look.tag);
            factor_a();
            term_linha();
        }
         
    }
    public void factor_a() throws IOException{
        // fator-a ::= factor | **not** factor | "-" factor
        if (look.tag == Tag.NOT){
            match(Tag.NOT);
        } else if (look.tag == Tag.SUB_HIFEN){
            match(Tag.SUB_HIFEN);
        }
        factor();
    }
    public void factor() throws IOException{
        if (look.tag == Tag.IDENTIFICADOR){
            match(Tag.IDENTIFICADOR);
        } else if (look.tag == Tag.CHAR_CONST) {
            match(Tag.CHAR_CONST);
        } else if (look.tag == Tag.FLOAT_CONST) {
            match(Tag.FLOAT_CONST);
        } else if (look.tag == Tag.INT_CONST) {
            match(Tag.INT_CONST);
        } else if (look.tag == Tag.ABRE_PARENTESES) {
            match(Tag.ABRE_PARENTESES);
            expression();
            match(Tag.FECHA_PARENTESES);
        } else {
            error("Esperava um IDENTIFICADOR, CONSTANTE OU ABRE PARENTESES");
        }
    }
}
