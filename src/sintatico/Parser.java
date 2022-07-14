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
    Env top = null; // Tabela de símbolos corrente ou do Topo
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
        Stmt s = body();
        // Geração do Código intermediário
        int begin = s.newlabel();
        int after = s.newlabel();
        s.emitlabel(begin);
        s.gen(begin, after);
        s.emitlabel(after);

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
        s.returnErros();
        System.out.println("Terminou");
    }
    public Stmt body() throws IOException{
        // body ::= [decl-list] **begin** stmt-list **end**
        Env savedEnv = top;
        top = new Env(top); // Elo entre as tabelas de símbolos
        decl_list();
        match(Tag.BEGIN);
        Stmt s = stmt_list();
        match(Tag.END);
        top = savedEnv;
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
        Tipo p = (Tipo)look;
        match(Tag.BASIC);
        ident_list(p);
    }
    public void ident_list(Tipo p) throws IOException{
        // ident-list ::= identifier {"," identifier}
        Token token = look;
        match(Tag.IDENTIFICADOR);
        Id id = new Id((Palavra) token, p, used); // Cria o elemento da tabela
        used = used + p.width; // Desloca o offset
        while(look.tag == Tag.VIRGULA) {
            top.put(token, id); // Adiciona o token na tabela de símbolos
            match(Tag.VIRGULA);
            token = look;
            match(Tag.IDENTIFICADOR);
            id = new Id((Palavra) token, p, used);
            used = used + p.width;
        }
        top.put(token, id); // Adiciona o token na tabela de símbolos
    }
    public Stmt stmt_list() throws IOException{
        // stmt-list ::= stmt {";" stmt}
        Stmt s = stmt();
        if (look.tag == Tag.PONTO_VIRGULA){
            match(Tag.PONTO_VIRGULA);
            return new Seq(s, stmt_list());
        }
        return s;
//        stmt();
//        
//        while(look.tag == Tag.PONTO_VIRGULA){
//            match(Tag.PONTO_VIRGULA);
//            stmt();
//        }
//        
//        return null;
    }
    public Stmt stmt() throws IOException{
        // stmt ::= assign-stmt | if-stmt | while-stmt |
        //          repeat-stmt | read-stmt | write-stmt
        
        
        if (look.tag == Tag.IDENTIFICADOR){
            return assign_stmt();
        } else if (look.tag == Tag.IF){
            return if_stmt();
        } else if (look.tag == Tag.WHILE){
            Stmt savedStmt = Stmt.Enclosing;// Guarda o loop
            return while_stmt(savedStmt);
        } else if (look.tag == Tag.REPEAT){
            Stmt savedStmt = Stmt.Enclosing;// Guarda o loop
            return repeat_stmt(savedStmt);
        } else if (look.tag == Tag.READ){
            read_stmt();
            return Stmt.Null;
        } else if (look.tag == Tag.WRITE){
            return write_stmt();
        } else {
            error("Esperava um IF, WHILE, REPEAT, READ, WRITE");
            return Stmt.Null;
        }
    }
    public Stmt assign_stmt() throws IOException{
        // assign-stmt ::= identifier ":=" simple_expr
        Stmt stmt;
        Token token = look;
        match(Tag.IDENTIFICADOR);
        Id id = top.get(token);
        if (id == null) {
            error(token.toString()+ " Não declarado");
            id = new Id((Palavra) token, Tipo.ERROR, 0);
            top.put(token, id);
        }
        match(Tag.ATRIBUICAO);
        stmt = new Set(id, simple_expr());
        return stmt;
        
    }
    public Stmt if_stmt() throws IOException{
        // if-stmt ::= if condition then stmt-list end | 
        //             if condition then stmt-list else stmt-list end
        match(Tag.IF);
        Expr expr = condition();
        match(Tag.THEN);
        Stmt s1 = stmt_list();
        if (look.tag == Tag.ELSE) {
            match(Tag.ELSE);
            Stmt s2 = stmt_list();
            match(Tag.END);
            return new Else(expr, s1, s2);
        }
        match(Tag.END);
        return new If(expr, s1);
    }
    public Expr condition() throws IOException{
        // condition ::= expression
        return expression();
    }
    public Stmt repeat_stmt(Stmt savedStmt) throws IOException{
        // repeat-stmt ::= **repeat** stmt-list stmt-suffix
        Repeat repeatnode = new Repeat();
        Stmt.Enclosing = repeatnode;
        match(Tag.REPEAT);
        Stmt s1 = stmt_list();
        Expr expr = stmt_suffix();
        repeatnode.init(s1, expr);
        Stmt.Enclosing = savedStmt;
        return repeatnode;
    }
    public Expr stmt_suffix() throws IOException{
        // stmt-suffix ::= **until** condition
        match(Tag.UNTIL);
        Expr expr = condition();
        return expr;
    }
    public Stmt while_stmt(Stmt savedStmt) throws IOException{
        // while-stmt ::= stmt-prefix stmt-list **end**
        While whilenode = new While();
        Stmt.Enclosing = whilenode;
        Expr expr = stmt_prefix();
        Stmt s1 = stmt_list();
        whilenode.init(expr, s1);
        match(Tag.END);
        Stmt.Enclosing = savedStmt;
        return whilenode;
    }
    
    public Expr stmt_prefix() throws IOException{
        // stmt-prefix ::= **while** condition **do**
        match(Tag.WHILE);
        Expr expr = condition();
        match(Tag.DO);
        return expr;
    }
    public Stmt read_stmt() throws IOException{
        // read-stmt ::= **read** "(" identifier ")
        match(Tag.READ);
        match(Tag.ABRE_PARENTESES);
        Token tok = look;
        Id id = top.get(tok);
        if (id == null) {
            error(tok.toString()+ " Não declarado");
            id = new Id((Palavra) tok, Tipo.ERROR, 0);
            top.put(tok, id);
        }
        match(Tag.IDENTIFICADOR);
        match(Tag.FECHA_PARENTESES);
        Stmt stmt = new Call(tok, id);
        return stmt;
        
    }
    
    public Stmt write_stmt() throws IOException{
        // write-stmt ::= write "(" writable ")"
        match(Tag.WRITE);
        match(Tag.ABRE_PARENTESES);
        Token tok = look;
        Expr expr = writable();
        match(Tag.FECHA_PARENTESES);
        Stmt stmt = new Call(tok, expr);
        return stmt;
        
    }
    
    public Expr writable() throws IOException{
        // writable ::= simple-expr | literal
        if (look.tag == Tag.LITERAL){
            Token tok = look;
            match(Tag.LITERAL);
            return new Constant((Palavra) tok, null);
        }
        return simple_expr();
    }
    
    public Expr expression() throws IOException{
        // expression ::= simple-expr | simple-expr relop simple-expr
        Expr expr = simple_expr();
        if (relop.contains(look.tag)){
            Token tok = look;
            match(look.tag);
            Expr expr1 = new Rel(tok, expr, simple_expr());
            return expr1;
        }
        return expr;
    }

    public Expr simple_expr() throws IOException{
        // simple-expr ::= term | simple-expr addop term
        // simple-expr ::= term simple-expr-linha
//        term();
//        simple_expr_linha()
        return simple_expr_linha(term());
    }
    
    public Expr simple_expr_linha(Expr expr1) throws IOException{
        // simple-expr ::= term | simple-expr addop term
        // simple-expr-linha ::= addop term simple-expr-linha
        Expr expr;
        if (addop.contains(look.tag)){
            Token tok = look;
            match(look.tag);
            if (tok.tag != Tag.OR) {
                expr = new Arith(tok, expr1, term());
            } else {
                expr = new Logical(tok, expr1, term());
            }
            return simple_expr_linha(expr);
            
        }
        return expr1;
    }
    
    public Expr term() throws IOException{
        // term ::= factor-a | term mulop factor-a
        // term ::= factor-a term-linha
//        factor_a();
//        term_linha();
        return term_linha(factor_a());
    }
    public Expr term_linha(Expr expr1) throws IOException{
        // term ::= factor-a | term mulop factor-a
        // term-linha ::= mulop factor-a term_linha
        Expr expr;
        if (mulop.contains(look.tag)){
            Token tok = look;
            match(look.tag);
            if (tok.tag != Tag.AND){
                expr = new Arith(tok, expr1, factor_a());
            } else {
                expr = new Logical(tok, expr1, factor_a());
            }
            return term_linha(expr);
        }
        return expr1;
         
    }
    public Expr factor_a() throws IOException{
        // fator-a ::= factor | **not** factor | "-" factor
        if (look.tag == Tag.NOT){
            Token tok = look;
            match(Tag.NOT);
            return new Unary((Palavra) tok, factor_a());
        } else if (look.tag == Tag.SUB_HIFEN){
            Token tok = look;
            match(Tag.SUB_HIFEN);
            return new Unary((Palavra) tok, factor_a());
        }
        return factor();
        
    }
    public Expr factor() throws IOException{
        Token token = look;
        Expr val;
        if (look.tag == Tag.IDENTIFICADOR){
            val = top.get(token);
            if (val == null) {
                error(token.toString()+ " Não declarado");
                val = new Constant(token, Tipo.ERROR);
            }
            match(Tag.IDENTIFICADOR);
        } else if (look.tag == Tag.CHAR_CONST) {
            val = new Constant(token, Tipo.CHAR);
            match(Tag.CHAR_CONST);
        } else if (look.tag == Tag.FLOAT_CONST) {
            val = new Constant(token, Tipo.FLOAT);
            match(Tag.FLOAT_CONST);
        } else if (look.tag == Tag.INT_CONST) {
            val = new Constant(token, Tipo.INT);
            match(Tag.INT_CONST);
        } else if (look.tag == Tag.ABRE_PARENTESES) {
            match(Tag.ABRE_PARENTESES);
            val = expression();
            match(Tag.FECHA_PARENTESES);
            
        } else {
            error("Esperava um IDENTIFICADOR, CONSTANTE OU ABRE PARENTESES");
            return null;
        }
        return val;
    }
}
