package br.org.faculdadesalvadorarena.analisador_sintatico.parser;

import br.org.faculdadesalvadorarena.analisador_sintatico.ast.*;
import br.org.faculdadesalvadorarena.analisador_sintatico.lexer.AnalisadorLexico;
import br.org.faculdadesalvadorarena.analisador_sintatico.lexer.TipoToken;
import br.org.faculdadesalvadorarena.analisador_sintatico.lexer.Token;
import br.org.faculdadesalvadorarena.analisador_sintatico.util.ConjuntosFirstFollow;

/**
 * Analisador sintático recursivo descendente para a gramática:
 * E → E + T | E - T | T
 * T → T * F | T / F | F
 * F → F++ | F-- | ( E ) | id
 */
public class AnalisadorSintatico {
    private final AnalisadorLexico lexer;
    private Token tokenAtual;
    
    public AnalisadorSintatico(AnalisadorLexico lexer) {
        this.lexer = lexer;
        this.avancar(); // Inicializa o tokenAtual
    }
    
    /**
     * Avança para o próximo token.
     */
    private void avancar() {
        tokenAtual = lexer.consumirToken();
    }
    
    /**
     * Verifica se o token atual é do tipo esperado e avança para o próximo token.
     * Caso contrário, lança uma exceção.
     */
    private void consumir(TipoToken tipo, String mensagemErro) {
        if (tokenAtual.getTipo() == tipo) {
            avancar();
        } else {
            throw new ErroSintatico(mensagemErro, tokenAtual.getPosicao());
        }
    }
    
    /**
     * Ponto de entrada para a análise sintática.
     * Inicia a análise a partir do não-terminal E (expressão).
     * @return A raiz da árvore sintática.
     */
    public No analisar() {
        No raiz = expressao();
        
        // Verifica se chegou ao fim da entrada
        if (tokenAtual.getTipo() != TipoToken.EOF) {
            throw new ErroSintatico("Tokens inesperados após o fim da expressão", tokenAtual.getPosicao());
        }
        
        return raiz;
    }
    
    /**
     * Implementa a regra: E → E + T | E - T | T
     * Transformada para eliminar a recursão à esquerda:
     * E → T E'
     * E' → + T E' | - T E' | ε
     */
    private No expressao() {
        // Verifica se o token atual está no conjunto First de E
        if (!ConjuntosFirstFollow.estaNoFirstE(tokenAtual.getTipo())) {
            throw new ErroSintatico("Esperado início de expressão", tokenAtual.getPosicao());
        }
        
        // T
        No noTermo = termo();
        NoExpressao noExpressao = new NoExpressao("E");
        noExpressao.adicionarFilho(noTermo);
        
        // E'
        while (tokenAtual.getTipo() == TipoToken.MAIS || tokenAtual.getTipo() == TipoToken.MENOS) {
            TipoToken tipoOperador = tokenAtual.getTipo();
            String valorOperador = tokenAtual.getLexema();
            avancar();
            
            // Cria um novo nó de expressão para representar a operação
            NoExpressao novaExpressao = new NoExpressao("E");
            
            // Adiciona a expressão anterior como primeiro filho
            novaExpressao.adicionarFilho(noExpressao);
            
            // Adiciona o operador como segundo filho
            NoOperador noOperador = new NoOperador(valorOperador);
            novaExpressao.adicionarFilho(noOperador);
            
            // Adiciona o termo como terceiro filho
            No novoTermo = termo();
            novaExpressao.adicionarFilho(novoTermo);
            
            // Atualiza a expressão atual
            noExpressao = novaExpressao;
        }
        
        return noExpressao;
    }
    
    /**
     * Implementa a regra: T → T * F | T / F | F
     * Transformada para eliminar a recursão à esquerda:
     * T → F T'
     * T' → * F T' | / F T' | ε
     */
    private No termo() {
        // Verifica se o token atual está no conjunto First de T
        if (!ConjuntosFirstFollow.estaNoFirstT(tokenAtual.getTipo())) {
            throw new ErroSintatico("Esperado início de termo", tokenAtual.getPosicao());
        }
        
        // F
        No noFator = fator();
        NoTermo noTermo = new NoTermo("T");
        noTermo.adicionarFilho(noFator);
        
        // T'
        while (tokenAtual.getTipo() == TipoToken.MULT || tokenAtual.getTipo() == TipoToken.DIV) {
            TipoToken tipoOperador = tokenAtual.getTipo();
            String valorOperador = tokenAtual.getLexema();
            avancar();
            
            // Cria um novo nó de termo para representar a operação
            NoTermo novoTermo = new NoTermo("T");
            
            // Adiciona o termo anterior como primeiro filho
            novoTermo.adicionarFilho(noTermo);
            
            // Adiciona o operador como segundo filho
            NoOperador noOperador = new NoOperador(valorOperador);
            novoTermo.adicionarFilho(noOperador);
            
            // Adiciona o fator como terceiro filho
            No novoFator = fator();
            novoTermo.adicionarFilho(novoFator);
            
            // Atualiza o termo atual
            noTermo = novoTermo;
        }
        
        return noTermo;
    }
    
    /**
     * Implementa a regra: F → F++ | F-- | ( E ) | id
     * Transformada para eliminar a recursão à esquerda:
     * F → ( E ) F' | id F'
     * F' → ++ F' | -- F' | ε
     */
    private No fator() {
        // Verifica se o token atual está no conjunto First de F
        if (!ConjuntosFirstFollow.estaNoFirstF(tokenAtual.getTipo())) {
            throw new ErroSintatico("Esperado início de fator", tokenAtual.getPosicao());
        }
        
        No noFator;
        
        if (tokenAtual.getTipo() == TipoToken.ABRE_PAREN) {
            // ( E )
            avancar();
            No noExpressao = expressao();
            consumir(TipoToken.FECHA_PAREN, "Esperado ')'");
            
            noFator = new NoFator("F");
            noFator.adicionarFilho(noExpressao);
        } else if (tokenAtual.getTipo() == TipoToken.ID) {
            // id
            String valorId = tokenAtual.getLexema();
            avancar();
            
            noFator = new NoFator("F");
            NoIdentificador noId = new NoIdentificador(valorId);
            noFator.adicionarFilho(noId);
        } else {
            throw new ErroSintatico("Esperado '(' ou identificador", tokenAtual.getPosicao());
        }
        
        // F'
        while (tokenAtual.getTipo() == TipoToken.INCREMENTO || tokenAtual.getTipo() == TipoToken.DECREMENTO) {
            TipoToken tipoOperador = tokenAtual.getTipo();
            String valorOperador = tokenAtual.getLexema();
            avancar();
            
            // Cria um novo nó de fator para representar a operação
            NoFator novoFator = new NoFator("F");
            
            // Adiciona o fator anterior como primeiro filho
            novoFator.adicionarFilho(noFator);
            
            // Adiciona o operador como segundo filho
            NoOperador noOperador = new NoOperador(valorOperador);
            novoFator.adicionarFilho(noOperador);
            
            // Atualiza o fator atual
            noFator = novoFator;
        }
        
        return noFator;
    }
}
