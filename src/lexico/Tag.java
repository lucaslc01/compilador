/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lexico;


/**
 *
 * @author pedro
 */
public enum Tag {
    // Auxiliares
    FIM_DE_ARQUIVO,
    FIM_INESPERADO_DE_ARQUIVO,
    LITERAL_MAL_FORMADO,
    TOKEN_MAL_FORMADO,
    ATRIBUICAO_MAL_FORMADA,
    ERRO,
    // Tipos de Variáveis
    INT,
    FLOAT,
    CHAR,
    
    // Padrões de Tokens
    // integer_const ::= digit+
    INT_CONST,
    // float_const ::= digit+.digit+
    FLOAT_CONST,
    // char_const ::=  ‘  carac  ’ 
    CHAR_CONST,
    // literal ::= " caractere* "
    LITERAL,
    // identifier ::= letter (letter | digit )*
    IDENTIFICADOR,
    
    //Operadores
    // Lógicos
    IGUAL,       // =
    MAIOR_QUE,   // >
    MAIOR_IGUAL, // >=
    MENOR_QUE,   // <
    MENOR_IGUAL, // <=
    DIFERENTE,   // <>
    OR,          // or
    AND,         // and
    NOT,         // not
    // Aritméticos
    SOMA,           // +
    // Tag Subtracao e para a producao de fator-a ::= - factor
    SUB_HIFEN,      
    DIVISAO,        // /
    MULTIPLICACAO,  // *
    // Atribuição
    ATRIBUICAO,     // :=
    
    // Palavras Reservadas
    ROUTINE,
    BEGIN,
    END,
    DECLARE,
    IF,
    THEN,
    ELSE,
    REPEAT,
    UNTIL,
    WHILE,
    DO,
    READ,
    WRITE,
    // Símbolos
    PONTO_VIRGULA,
    VIRGULA,
    ABRE_PARENTESES,
    FECHA_PARENTESES;
    
}
