package br.org.faculdadesalvadorarena.analisador_sintatico.lexer;

/**
 * Enumeração dos tipos de tokens reconhecidos pelo analisador léxico.
 */
public enum TipoToken {
    // Operadores
    MAIS("+"),       // +
    MENOS("-"),      // -
    MULT("*"),       // *
    DIV("/"),        // /
    INCREMENTO("++"), // ++
    DECREMENTO("--"), // --
    
    // Parênteses
    ABRE_PAREN("("),  // (
    FECHA_PAREN(")"), // )
    
    // Identificador
    ID("id"),         // identificador
    
    // Fim de entrada
    EOF(""),          // fim de arquivo
    
    // Token inválido
    INVALIDO("");     // token inválido
    
    private final String texto;
    
    TipoToken(String texto) {
        this.texto = texto;
    }
    
    public String getTexto() {
        return texto;
    }
}
