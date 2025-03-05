package br.org.faculdadesalvadorarena.dto;

import br.org.faculdadesalvadorarena.brazilian_id_validation_automaton.enums.IdType;
import br.org.faculdadesalvadorarena.brazilian_id_validation_automaton.enums.State;

public class ValidationResultDto {
    public boolean isValid;
    public IdType type;
    public State lastState;
    
    public ValidationResultDto(boolean isValid, IdType type, State lastState) {
        this.isValid = isValid;
        this.type = type;
        this.lastState = lastState;
    }

    public boolean isValid() {
        return isValid;
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public IdType getType() {
        return type;
    }

    public void setType(IdType type) {
        this.type = type;
    }

    public State getLastState() {
        return lastState;
    }

    public void setLastState(State lastState) {
        this.lastState = lastState;
    }

    
}
