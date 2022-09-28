package com.sisuz.reportes.controller;

import com.sisuz.reportes.dto.request.EspecialidadByLabRequest;
import com.sisuz.reportes.dto.response.TableStructureDTO;
import com.sisuz.reportes.service.EspecialidadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/reportes/especialidades")
public class EspecialidadReportController {

    private final EspecialidadService especialidadService;

    public EspecialidadReportController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    @PostMapping("/agrupado-por-laboratorio")
    public ResponseEntity<TableStructureDTO> obtenerAgrupadosPorLaboratorio(@RequestBody EspecialidadByLabRequest request) {
        return ResponseEntity.ok(especialidadService.obtenerAgrupadosPorLaboratorio(request));
    }

    @PostMapping("/otras-especialidades")
    public ResponseEntity<TableStructureDTO> obtenerOtrasEspecialidades(@RequestBody EspecialidadByLabRequest request) {
        return ResponseEntity.ok(especialidadService.obtenerOtrasEspecialidades(request));
    }

}
