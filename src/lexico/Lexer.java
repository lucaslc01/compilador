/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexico;
import java.io.*;
import java.util.*;
import tabelaDeSimbolos.Tipo;

/**
 *
 * @author pedro
 */
public class Lexer {
    public InputStreamReader arquivo;
    public static int linha = 1;
    public static List<String> erros = new ArrayList<String>();
    public char ponteiro = ' ';
    HashMap<String, Palavra> palavras = new HashMap<String, Palavra>();
    void reserva(Palavra palavra) {
        palavras.put(palavra.lexema, palavra);
    }
    public Lexer(InputStreamReader arquivo){
        reserva(new Palavra("routine", Tag.ROUTINE));
        reserva(new Palavra("begin", Tag.BEGIN));
        reserva(new Palavra("end", Tag.END));
        reserva(new Palavra("declare", Tag.DECLARE));
        reserva(new Palavra("if", Tag.IF));
        reserva(new Palavra("then", Tag.THEN));
        reserva(new Palavra("else", Tag.ELSE));
        reserva(new Palavra("repeat", Tag.REPEAT));
        reserva(new Palavra("until", Tag.UNTIL));
        reserva(new Palavra("while", Tag.WHILE));
        reserva(new Palavra("do", Tag.DO));
        reserva(new Palavra("read", Tag.READ));
        reserva(new Palavra("write", Tag.WRITE));
        reserva(new Palavra("and", Tag.AND));
        reserva(new Palavra("or", Tag.OR));
        reserva(new Palavra("not", Tag.NOT));
        reserva(Tipo.INT);
        reserva(Tipo.FLOAT);
        reserva(Tipo.CHAR);
        this.arquivo = arquivo;
    }
    
    public void analiseLexica(InputStreamReader arquivo) throws IOException{
        this.arquivo = arquivo;
        do {
            Token retorno = scan();
            if (retorno != null) {
                System.out.println(retorno.toString());
            }
        } while(ponteiro != (char)-1);
        
        System.out.println("Análise Léxica Terminada");
        System.out.println(erros.toString());
        for (HashMap.Entry node : palavras.entrySet()){
            System.out.println(node);
        }
        
    }
    
    public void erroLexico(Tag tag){
        erros.add(tag.toString()+" Linha = "+linha+"\n");
    }
    
    
    public void readch() throws IOException {
        ponteiro = ' ';
        ponteiro = (char)arquivo.read();
    }
    public boolean readch(char letra) throws IOException{
        readch();
        // Se o novo valor NÃO bater, guarda o valor do ponteiro
        if (ponteiro != letra) return false;
        // Se o novo valor bater limpa o valor do ponteiro
        ponteiro = ' ';
        return true;
    }
    
    public Token scan() throws IOException{
        for(;;readch()){
            if (ponteiro == '\n') linha = linha + 1;
            else if (ponteiro != ' ' && ponteiro != '\t' && (int)ponteiro != 13) break;
        } // Leia até encontrar algo diferente de espaço e tabulação
        // Construção de operadores relacionais
        switch(ponteiro){
            case (char)-1:
                return new Token(Tag.FIM_DE_ARQUIVO);
            case '<':
                if (readch('=')) return Palavra.MENOR_IGUAL;
                if (ponteiro == '>') {
                    ponteiro = ' ';
                    return Palavra.DIFERENTE;
                }
                else return Palavra.MENOR_QUE;
            case '>':
                if (readch('=')) return Palavra.MAIOR_IGUAL;
                else return Palavra.MAIOR_QUE;
            case '=':
                ponteiro = ' ';
                return new Token(Tag.IGUAL);
            case ',':
                ponteiro = ' ';
                return new Token(Tag.VIRGULA);
            case ';':
                ponteiro = ' ';
                return new Token(Tag.PONTO_VIRGULA);
            case '(':
                ponteiro = ' ';
                return new Token(Tag.ABRE_PARENTESES);
            case ')':
                ponteiro = ' ';
                return new Token(Tag.FECHA_PARENTESES);
            case '+':
                ponteiro = ' ';
                return new Token(Tag.SOMA);
            case '-':
                ponteiro = ' ';
                return new Token(Tag.SUB_HIFEN);
            case '*':
                ponteiro = ' ';
                return new Token(Tag.MULTIPLICACAO);
            case '/':
                ponteiro = ' ';
                return new Token(Tag.DIVISAO);
            case ':':
                if (readch('=')) return new Token(Tag.ATRIBUICAO);
                erroLexico(Tag.ATRIBUICAO_MAL_FORMADA);
                return null;
            case '"':
                StringBuffer buffer = new StringBuffer();
                do{
                    if (ponteiro == '\n') {
                    // ERRO literal mal formado
                        erroLexico(Tag.LITERAL_MAL_FORMADO);
                        return null;
                    }
                    if (ponteiro == (char)-1){
                        erroLexico(Tag.FIM_INESPERADO_DE_ARQUIVO);
                        return null;
                    }
                    buffer.append(ponteiro);
                } while(!readch('"'));
                buffer.append('"');
                String literal = buffer.toString();
                Palavra palavra = new Palavra(literal, Tag.LITERAL);
                palavras.put(literal, palavra);
                
                return palavra;
            case '\'':
                StringBuffer buff = new StringBuffer();
                do{
                    if (ponteiro == (char)-1){
                        erroLexico(Tag.FIM_INESPERADO_DE_ARQUIVO);
                        return null;
                    }
                    buff.append(ponteiro);
                } while(!readch('\''));
                buff.append('\'');
                String char_const = buff.toString();
                Palavra constante_char = new Palavra(char_const, Tag.CHAR_CONST);
                palavras.put(char_const, constante_char);
                return constante_char;
            case '%':
                while(!readch('%')){
                    if (ponteiro == '\n') linha += 1;
                    if (ponteiro == (char)-1) {
                        // ERRO final inesperado do arquivo
                        erroLexico(Tag.FIM_INESPERADO_DE_ARQUIVO);
                        return null;
                    }
                }
                return null;
        }
        // Construção de Tokens Inteiros e Reais
        if (Character.isDigit(ponteiro)){
            int value = 0;
            do {
                value = 10 * value + Character.digit(ponteiro, 10);
                readch();
            }while(Character.isDigit(ponteiro));
            if (ponteiro != '.') return new Inteiro(value);
            float real = value;
            float denominador = 10;
            readch();
            if (!Character.isDigit(ponteiro)){
                erroLexico(Tag.FLOAT_MAL_FORMADO);
                return null;
            }
            for (;;){
                if (!Character.isDigit(ponteiro)) break;
                real += Character.digit(ponteiro, 10) / denominador;
                denominador *= 10;
                readch();
            }
            return new Real(real);
        }
        if (Character.isLetter(ponteiro)){
            StringBuffer buffer = new StringBuffer();
            do{
                buffer.append(ponteiro);
                readch();
                
            } while(Character.isLetterOrDigit(ponteiro));
            
            String lexema = buffer.toString();
            Palavra palavra = (Palavra)palavras.get(lexema);
            if (palavra != null) return palavra;
            palavra = new Palavra(lexema, Tag.IDENTIFICADOR);
            palavras.put(lexema, palavra);
            return palavra;
        }
        erroLexico(Tag.TOKEN_MAL_FORMADO);
        ponteiro = ' ';
        return null;
        
    }
}
