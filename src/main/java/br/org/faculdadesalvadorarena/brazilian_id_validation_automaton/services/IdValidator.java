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
                    } else if (isDot) {
                        idType = IdType.RG;
                        current = State.Q21;
                    } else {
                        current = State.ERROR;
                    }
                    break;

                case Q3:
                    if (isDigit) {
                        current = State.Q4;
                    } else if (isDot) {
                        idType = IdType.CPF;
                        current = State.Q13;
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
                    if (isDigit) {
                        current = State.Q8;
                    } else {
                        current = State.ERROR;
                    }
                    break;

                case Q8:
                    if (isDigit) {
                        if (hasNext) {
                            idType = IdType.CPF;
                        } else {
                            idType = IdType.RG;
                        }
                        current = State.Q9;
                    } else if (isX) {
                        idType = IdType.RG;
                        current = State.Q12;
                    } else if (isDash) {
                        idType = IdType.RG;
                        current = State.Q29;
                    } else {
                        current = State.ERROR;
                    }
                    break;

                case Q9:
                    if (isDigit) {
                        current = State.Q11;
                    } else if (isDash) {
                        current = State.Q10;
                    } else {
                        current = State.ERROR;
                    }
                    break;

                case Q10:
                    if (isDigit) {
                        current = State.Q11;
                    } else {
                        current = State.ERROR;
                    }
                    break;

                case Q11:
                    if (isDigit) {
                        current = State.Q12;
                    } else {
                        current = State.ERROR;
                    }
                    break;
                case Q12:
                    current = State.ERROR;
                    break;
                case Q13:
                    if (isDigit) {
                        idType = IdType.CPF;
                        current = State.Q14;
                    } else {
                        current = State.ERROR;
                    }
                    break;
                case Q14:
                    if (isDigit) {
                        current = State.Q15;
                    } else {
                        current = State.ERROR;
                    }
                    break;
                case Q15:
                    if (isDigit) {
                        current = State.Q16;
                    } else {
                        current = State.ERROR;
                    }
                    break;
                case Q16:
                    if (isDot) {
                        current = State.Q17;
                    } else {
                        current = State.ERROR;
                    }
                    break;
                case Q17:
                    if (isDigit) {
                        current = State.Q18;
                    } else {
                        current = State.ERROR;
                    }
                    break;
                case Q18:
                    if (isDigit) {
                        current = State.Q19;
                    } else {
                        current = State.ERROR;
                    }
                    break;
                case Q19:
                    if (isDigit) {
                        current = State.Q20;
                    } else {
                        current = State.ERROR;
                    }
                    break;
                case Q20:
                    if (isDash) {
                        current = State.Q10;
                    } else {
                        current = State.ERROR;
                    }
                    break;
                case Q21:
                    if (isDigit) {
                        current = State.Q22;
                    } else {
                        current = State.ERROR;
                    }
                    break;
                case Q22:
                    if (isDigit) {
                        current = State.Q23;
                    } else {
                        current = State.ERROR;
                    }
                    break;
                case Q23:
                    if (isDigit) {
                        current = State.Q24;
                    } else {
                        current = State.ERROR;
                    }
                    break;
                case Q24:
                    if (isDot) {
                        current = State.Q25;
                    } else {
                        current = State.ERROR;
                    }
                    break;
                case Q25:
                    if (isDigit) {
                        current = State.Q26;
                    } else {
                        current = State.ERROR;
                    }
                    break;
                case Q26:
                    if (isDigit) {
                        current = State.Q27;
                    } else {
                        current = State.ERROR;
                    }
                    break;
                case Q27:
                    if (isDigit) {
                        current = State.Q28;
                    } else {
                        current = State.ERROR;
                    }
                    break;
                case Q28:
                    if (isDash) {
                        current = State.Q29;
                    } else {
                        current = State.ERROR;
                    }
                    break;
                case Q29:
                    if (isDigit || isX) {
                        current = State.Q12;
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

        // Check if we are in a valid final state
        if (current == State.Q9 || current == State.Q12) {
            return new ValidationResultDto(true, idType, current);
        }

        return new ValidationResultDto(false, null, current);
    }
}