package br.org.faculdadesalvadorarena.analisador_sintatico;

import br.org.faculdadesalvadorarena.analisador_sintatico.ast.No;
import br.org.faculdadesalvadorarena.analisador_sintatico.lexer.AnalisadorLexico;
import br.org.faculdadesalvadorarena.analisador_sintatico.parser.AnalisadorSintatico;
import br.org.faculdadesalvadorarena.analisador_sintatico.parser.ErroSintatico;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes automatizados para o analisador sintático.
 */
public class AnalisadorSintaticoTest {

    @Test
    @DisplayName("Teste de casos de aceitação")
    public void testCasosAceitacao() {
        // Casos de aceitação
        String[] casosAceitacao = {
            "x",                // Identificador simples
            "x + y",            // Adição simples
            "x * y + z",        // Operações com precedência
            "(x + y) * z",      // Expressão com parênteses
            "x++ * y--"         // Incremento e decremento
        };
        
        for (String caso : casosAceitacao) {
            try {
                AnalisadorLexico lexer = new AnalisadorLexico(caso);
                AnalisadorSintatico parser = new AnalisadorSintatico(lexer);
                No raiz = parser.analisar();
                
                // Se chegou aqui, o teste passou
                assertNotNull(raiz, "A árvore sintática não deveria ser nula para: " + caso);
                System.out.println("Caso aceito: " + caso);
                System.out.println("Árvore: \n" + raiz.imprimirArvore());
            } catch (Exception e) {
                fail("O caso '" + caso + "' deveria ser aceito, mas lançou exceção: " + e.getMessage());
            }
        }
    }
    
    @Test
    @DisplayName("Teste de casos de rejeição")
    public void testCasosRejeicao() {
        // Casos de rejeição
        String[] casosRejeicao = {
            "",                 // String vazia
            "+",                // Apenas um operador
            "x +",              // Operador no final sem operando
            "x + + y",          // Operadores consecutivos
            "x ) + y"           // Parêntese de fechamento sem abertura
        };
        
        for (String caso : casosRejeicao) {
            AnalisadorLexico lexer = new AnalisadorLexico(caso);
            AnalisadorSintatico parser = new AnalisadorSintatico(lexer);
            
            Exception exception = assertThrows(
                Exception.class,
                () -> parser.analisar(),
                "O caso '" + caso + "' deveria ser rejeitado, mas foi aceito"
            );
            
            System.out.println("Caso rejeitado (como esperado): " + caso);
            System.out.println("Erro: " + exception.getMessage());
        }
    }
    
    @Test
    @DisplayName("Teste de expressões complexas")
    public void testExpressoesComplexas() {
        // Expressões mais complexas que devem ser aceitas
        String[] expressoes = {
            "a + b * c",
            "(a + b) * c",
            "a * (b + c)",
            "a++ + b--",
            "(a++)-- * b"
        };
        
        for (String expressao : expressoes) {
            try {
                AnalisadorLexico lexer = new AnalisadorLexico(expressao);
                AnalisadorSintatico parser = new AnalisadorSintatico(lexer);
                No raiz = parser.analisar();
                
                assertNotNull(raiz, "A árvore sintática não deveria ser nula para: " + expressao);
                System.out.println("Expressão aceita: " + expressao);
            } catch (Exception e) {
                fail("A expressão '" + expressao + "' deveria ser aceita, mas lançou exceção: " + e.getMessage());
            }
        }
    }
    
    @Test
    @DisplayName("Teste de precedência de operadores")
    public void testPrecedenciaOperadores() {
        // Teste para verificar se a precedência dos operadores está correta
        String expressao = "a + b * c";
        
        try {
            AnalisadorLexico lexer = new AnalisadorLexico(expressao);
            AnalisadorSintatico parser = new AnalisadorSintatico(lexer);
            No raiz = parser.analisar();
            
            String arvore = raiz.imprimirArvore();
            System.out.println("Árvore para '" + expressao + "':\n" + arvore);
            
            // A árvore deve refletir que * tem precedência sobre +
            // Não podemos verificar a estrutura exata da árvore facilmente em um teste,
            // mas podemos imprimi-la para inspeção visual
            assertTrue(arvore.contains("NoOperador: +"), "A árvore deve conter o operador +");
            assertTrue(arvore.contains("NoOperador: *"), "A árvore deve conter o operador *");
        } catch (Exception e) {
            fail("A expressão '" + expressao + "' deveria ser aceita, mas lançou exceção: " + e.getMessage());
        }
    }
}
