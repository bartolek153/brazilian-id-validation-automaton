package br.org.faculdadesalvadorarena.analisador_sintatico.util;

import br.org.faculdadesalvadorarena.analisador_sintatico.lexer.TipoToken;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe utilitária que define os conjuntos First e Follow para a gramática.
 * 
 * Gramática:
 * E → E + T | E - T | T
 * T → T * F | T / F | F
 * F → F++ | F-- | ( E ) | id
 */
public class ConjuntosFirstFollow {
    
    // Conjuntos First
    private static final Set<TipoToken> FIRST_E = new HashSet<>(Arrays.asList(
            TipoToken.ABRE_PAREN, TipoToken.ID
    ));
    
    private static final Set<TipoToken> FIRST_T = new HashSet<>(Arrays.asList(
            TipoToken.ABRE_PAREN, TipoToken.ID
    ));
    
    private static final Set<TipoToken> FIRST_F = new HashSet<>(Arrays.asList(
            TipoToken.ABRE_PAREN, TipoToken.ID
    ));
    
    // Conjuntos Follow
    private static final Set<TipoToken> FOLLOW_E = new HashSet<>(Arrays.asList(
            TipoToken.MAIS, TipoToken.MENOS, TipoToken.FECHA_PAREN, TipoToken.EOF
    ));
    
    private static final Set<TipoToken> FOLLOW_T = new HashSet<>(Arrays.asList(
            TipoToken.MULT, TipoToken.DIV, TipoToken.MAIS, TipoToken.MENOS, 
            TipoToken.FECHA_PAREN, TipoToken.EOF
    ));
    
    private static final Set<TipoToken> FOLLOW_F = new HashSet<>(Arrays.asList(
            TipoToken.INCREMENTO, TipoToken.DECREMENTO, TipoToken.MULT, TipoToken.DIV, 
            TipoToken.MAIS, TipoToken.MENOS, TipoToken.FECHA_PAREN, TipoToken.EOF
    ));
    
    /**
     * Verifica se um token está no conjunto First de E.
     */
    public static boolean estaNoFirstE(TipoToken token) {
        return FIRST_E.contains(token);
    }
    
    /**
     * Verifica se um token está no conjunto First de T.
     */
    public static boolean estaNoFirstT(TipoToken token) {
        return FIRST_T.contains(token);
    }
    
    /**
     * Verifica se um token está no conjunto First de F.
     */
    public static boolean estaNoFirstF(TipoToken token) {
        return FIRST_F.contains(token);
    }
    
    /**
     * Verifica se um token está no conjunto Follow de E.
     */
    public static boolean estaNoFollowE(TipoToken token) {
        return FOLLOW_E.contains(token);
    }
    
    /**
     * Verifica se um token está no conjunto Follow de T.
     */
    public static boolean estaNoFollowT(TipoToken token) {
        return FOLLOW_T.contains(token);
    }
    
    /**
     * Verifica se um token está no conjunto Follow de F.
     */
    public static boolean estaNoFollowF(TipoToken token) {
        return FOLLOW_F.contains(token);
    }
}
