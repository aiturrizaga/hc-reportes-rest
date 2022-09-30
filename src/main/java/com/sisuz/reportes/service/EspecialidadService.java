package com.sisuz.reportes.service;

import com.sisuz.reportes.dto.request.EspecialidadFilterRequest;
import com.sisuz.reportes.dto.response.TableStructureDTO;


public interface EspecialidadService {

    TableStructureDTO obtenerAgrupadosPorLaboratorio(EspecialidadFilterRequest request);

    TableStructureDTO obtenerReporteEspecialidades(EspecialidadFilterRequest request);

    TableStructureDTO obtenerReservasEspecialidades(EspecialidadFilterRequest request);
}
