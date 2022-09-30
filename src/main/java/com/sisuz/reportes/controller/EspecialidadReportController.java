package com.sisuz.reportes.controller;

import com.sisuz.reportes.dto.request.EspecialidadFilterRequest;
import com.sisuz.reportes.dto.response.TableStructureDTO;
import com.sisuz.reportes.service.EspecialidadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1/reportes")
public class EspecialidadReportController {

    private final EspecialidadService especialidadService;

    public EspecialidadReportController(EspecialidadService especialidadService) {
        this.especialidadService = especialidadService;
    }

    @PostMapping("/especialidades")
    public ResponseEntity<TableStructureDTO> obtenerReporteEspecialidades(@RequestBody EspecialidadFilterRequest request) {
        return ResponseEntity.ok(especialidadService.obtenerReporteEspecialidades(request));
    }

    @PostMapping("/especialidades/agrupado-por-laboratorio")
    public ResponseEntity<TableStructureDTO> obtenerAgrupadosPorLaboratorio(@RequestBody EspecialidadFilterRequest request) {
        return ResponseEntity.ok(especialidadService.obtenerAgrupadosPorLaboratorio(request));
    }

    @PostMapping("/especialidades/reservas")
    public ResponseEntity<TableStructureDTO> obtenerResrvasEspecialidades(@RequestBody EspecialidadFilterRequest request) {
        return ResponseEntity.ok(especialidadService.obtenerReservasEspecialidades(request));
    }

}
