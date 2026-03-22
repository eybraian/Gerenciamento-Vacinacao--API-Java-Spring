package com.dosecerta.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dosecerta.model.ImunizacaoModel;
import com.dosecerta.service.ImunizacaoService;

@RestController
@RequestMapping("imunizacoes")
public class ImunizacaoController {
    
    private ImunizacaoService service = new ImunizacaoService();

    //ADICIONAR IMUNIZACAO
    @PostMapping
    public int registrar(@RequestBody ImunizacaoModel i) throws SQLException {
        return service.registrar(i);
    }

    //ATUALIZAR IMUNIZACAO
    @PutMapping("/{id}")
    public boolean atualizar(@PathVariable int id, @RequestBody ImunizacaoModel i) throws SQLException {
        return service.atualizar(id, i);
    }

    //EXCLUIR IMUNIZACAO
    @DeleteMapping("/{id}")
    public boolean deletar(@PathVariable int id) throws SQLException {
        return service.deletar(id);
    }

    //EXCLUIR TODAS AS IMUNIZACOES DE UM DETERMINADO PACIENTE
    {}

    //CONSULTAR TODAS AS IMUNIZACOES
    @GetMapping
    public List<ImunizacaoModel> listar() throws SQLException {
        return service.listar();
    }

    //CONSULTAR IMUNIZACAO POR ID
    {}
    
    //CONSULTAR IMUNIZACAO POR ID DO PACIENTE
    @GetMapping("/paciente/{id}")
    public List<ImunizacaoModel> porPaciente(@PathVariable int id) throws SQLException {
        return service.porPaciente(id);
    }

    //CONSULTAR CONSULTAR IMUNIZACOES POR INTERVALO DE APLICACAO
    @GetMapping("/periodo")
    public List<ImunizacaoModel> porPeriodo(
            @RequestParam int paciente,
            @RequestParam String inicio,
            @RequestParam String fim
    ) throws SQLException {

        return service.porPeriodo(
                paciente,
                LocalDate.parse(inicio),
                LocalDate.parse(fim)
        );
    }

   

    
   

}
