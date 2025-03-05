package br.org.faculdadesalvadorarena.brazilian_id_validation_automaton.services;

import org.springframework.stereotype.Service;

import br.org.faculdadesalvadorarena.brazilian_id_validation_automaton.enums.State;
import br.org.faculdadesalvadorarena.brazilian_id_validation_automaton.enums.IdType;
import br.org.faculdadesalvadorarena.dto.ValidationResultDto;

@Service
public class IdValidator {
    public ValidationResultDto validate(String input) {
        System.out.println(input);
        State current = State.Q0;
        IdType idType = null;
        boolean hasDot = false;

        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            System.out.println(c);

            boolean isDigit = Character.isDigit(c);
            boolean isDot = c == '.';
            boolean isDash = c == '-';
            boolean isX = c == 'X' || c == 'x';
            boolean hasNext = i + 1 < input.length();

            switch (current) {
                case Q0:
                    if (isDigit) {
                        current = State.Q1;
                    } else {
                        current = State.ERROR;
                    }
                    break;

                case Q1:
                    if (isDigit) {
                        current = State.Q2;
                    } else {
                        current = State.ERROR;
                    }
                    break;

                case Q2:
                    if (isDigit) {
                        current = State.Q3;
                        idType = IdType.CPF;
                    } else if (isDot) {
                        hasDot = true;
                        idType = IdType.RG;
                        current = State.Q4;
                    } else {
                        current = State.ERROR;
                    }
                    break;

                case Q3:
                    if (isDigit) {
                        current = State.Q5;
                    } else if (isDot && idType == IdType.CPF) {
                        hasDot = true;
                        current = State.Q4;
                    } else {
                        current = State.ERROR;
                    }
                    break;

                case Q4:
                    if (isDigit) {
                        current = State.Q5;
                    } else {
                        current = State.ERROR;
                    }
                    break;

                case Q5:
                    if (isDigit) {
                        current = State.Q6;
                    } else {
                        current = State.ERROR;
                    }
                    break;

                case Q6:
                    if (isDigit) {
                        current = State.Q7;
                    } else {
                        current = State.ERROR;
                    }
                    break;

                case Q7:
                    if (isDigit && !hasDot) {
                        current = State.Q9;
                    } else if (isDot && hasDot) {
                        current = State.Q8;
                    } else {
                        current = State.ERROR;
                    }
                    break;

                case Q8:
                    if (isDigit) {
                        current = State.Q9;
                    } else {
                        current = State.ERROR;
                    }
                    break;

                case Q9:
                    if (isDigit) {
                        current = State.Q10;
                    } else {
                        current = State.ERROR;
                    }
                    break;
                case Q10:
                    if (isDigit && !hasNext && !hasDot) {
                        idType = IdType.RG;
                    }
                    else if (isDigit) {
                        current = State.Q11;
                    } else if (isX && !hasNext) {
                        idType = IdType.RG;
                    } else if (isDash && !hasDot) {
                        idType = IdType.RG;
                        current = State.Q12;
                    }
                    else {
                        current = State.ERROR;
                    }
                    break;

                case Q11:
                    if (isDigit && !hasDot) {
                        current = State.Q13;
                    } else if ((isDash && hasDot) || (isDash && !hasDot)) {
                        current = State.Q12;
                    } else {
                        current = State.ERROR;
                    }
                    break;
                case Q12:
                    if (isDigit || (isX && !hasNext)) {
                        current = State.Q13;
                    } else {
                        current = State.ERROR;
                    }
                    break;
                case Q13:
                    if (isDigit || idType == IdType.CPF) {
                        current = State.Q14;
                    } else {
                        current = State.ERROR;
                    }
                    break;

                default:
                    current = State.ERROR;
                    break;
            }

            // If we encounter an error state, break early
            if (current == State.ERROR) {
                return new ValidationResultDto(false, null, current);
            }
        }

        return new ValidationResultDto(true, idType, current);
    }
}