package br.org.faculdadesalvadorarena.brazilian_id_validation_automaton.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import br.org.faculdadesalvadorarena.brazilian_id_validation_automaton.services.IdValidator;
import br.org.faculdadesalvadorarena.dto.ValidationResultDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ValidationController {
    @Autowired
    private IdValidator idValidator;

    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/index")
    public String index2() {
        return "index";
    }
 
    @PostMapping("/validate")
    @ResponseBody
    public ValidationResultDto validate(@RequestBody String input) {
        return idValidator.validate(input);
    }
}
