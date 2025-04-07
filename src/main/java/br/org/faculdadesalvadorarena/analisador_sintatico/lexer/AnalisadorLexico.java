package br.org.faculdadesalvadorarena.analisador_sintatico.lexer;

import java.util.ArrayList;
import java.util.List;

/**
 * Analisador léxico que converte uma string de entrada em uma sequência de tokens.
 */
public class AnalisadorLexico {
    private final String entrada;
    private final List<Token> tokens;
    private int posicaoAtual;
    private int inicioLexema;
    
    public AnalisadorLexico(String entrada) {
        this.entrada = entrada;
        this.tokens = new ArrayList<>();
        this.posicaoAtual = 0;
        this.inicioLexema = 0;
    }
    
    /**
     * Analisa a entrada e gera a lista de tokens.
     * @return Lista de tokens gerados a partir da entrada.
     */
    public List<Token> analisar() {
        while (!fimDeEntrada()) {
            // Ignora espaços em branco
            if (Character.isWhitespace(caracterAtual())) {
                avancar();
                continue;
            }
            
            inicioLexema = posicaoAtual;
            
            // Verifica o tipo de token com base no caractere atual
            char c = caracterAtual();
            
            if (c == '+') {
                if (verificarProximo('+')) {
                    adicionarToken(TipoToken.INCREMENTO);
                    avancar(); // Avança um caractere adicional para o segundo '+'
                } else {
                    adicionarToken(TipoToken.MAIS);
                }
            } else if (c == '-') {
                if (verificarProximo('-')) {
                    adicionarToken(TipoToken.DECREMENTO);
                    avancar(); // Avança um caractere adicional para o segundo '-'
                } else {
                    adicionarToken(TipoToken.MENOS);
                }
            } else if (c == '*') {
                adicionarToken(TipoToken.MULT);
            } else if (c == '/') {
                adicionarToken(TipoToken.DIV);
            } else if (c == '(') {
                adicionarToken(TipoToken.ABRE_PAREN);
            } else if (c == ')') {
                adicionarToken(TipoToken.FECHA_PAREN);
            } else if (Character.isLetter(c)) {
                identificador();
            } else {
                // Caractere não reconhecido
                adicionarToken(TipoToken.INVALIDO);
            }
            
            avancar();
        }
        
        // Adiciona token de fim de arquivo
        tokens.add(new Token(TipoToken.EOF, "", posicaoAtual));
        
        return tokens;
    }
    
    /**
     * Processa um identificador.
     */
    private void identificador() {
        while (!fimDeEntrada() && (Character.isLetterOrDigit(caracterAtual()) || caracterAtual() == '_')) {
            avancar();
        }
        
        // Retrocede um caractere, pois o loop avançou um a mais
        retroceder();
        
        String lexema = entrada.substring(inicioLexema, posicaoAtual + 1);
        tokens.add(new Token(TipoToken.ID, lexema, inicioLexema));
    }
    
    /**
     * Adiciona um token à lista de tokens.
     */
    private void adicionarToken(TipoToken tipo) {
        String lexema = entrada.substring(inicioLexema, posicaoAtual + 1);
        tokens.add(new Token(tipo, lexema, inicioLexema));
    }
    
    /**
     * Verifica se o próximo caractere é igual ao esperado.
     */
    private boolean verificarProximo(char esperado) {
        if (posicaoAtual + 1 >= entrada.length()) {
            return false;
        }
        return entrada.charAt(posicaoAtual + 1) == esperado;
    }
    
    /**
     * Retorna o caractere na posição atual.
     */
    private char caracterAtual() {
        if (fimDeEntrada()) {
            return '\0';
        }
        return entrada.charAt(posicaoAtual);
    }
    
    /**
     * Avança para o próximo caractere.
     */
    private void avancar() {
        posicaoAtual++;
    }
    
    /**
     * Retrocede um caractere.
     */
    private void retroceder() {
        posicaoAtual--;
    }
    
    /**
     * Verifica se chegou ao fim da entrada.
     */
    private boolean fimDeEntrada() {
        return posicaoAtual >= entrada.length();
    }
    
    /**
     * Retorna o próximo token sem consumir.
     */
    public Token proximoToken() {
        if (tokens.isEmpty()) {
            analisar();
        }
        return tokens.get(0);
    }
    
    /**
     * Consome o próximo token e o retorna.
     */
    public Token consumirToken() {
        if (tokens.isEmpty()) {
            analisar();
        }
        return tokens.remove(0);
    }
}
