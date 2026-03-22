package com.dosecerta.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dosecerta.service.EstatisticaService;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticaController {
    
    private EstatisticaService service = new EstatisticaService();

    @GetMapping("/total")
    public int total() throws Exception {
        return service.totalAplicadas();
    }

    @GetMapping("/vacina/{id}")
    public int porVacina(@PathVariable int id) throws Exception {
        return service.totalPorVacina(id);
    }

    //QTE DE VACINAS APLICADAS POR PACIENTE

    //QTE DE VACINAS APLICAVEIS NO PROXIMO MES POR PACIENTE

    //QTE DE VACINAS ATRASADAS

    //QTE DE VACINAS ACIMA DE UMA DETERMINADA IDADE
}
