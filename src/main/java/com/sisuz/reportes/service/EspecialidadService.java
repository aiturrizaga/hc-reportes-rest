package com.sisuz.reportes.service;

import com.sisuz.reportes.dto.request.EspecialidadByLabRequest;
import com.sisuz.reportes.dto.response.TableStructureDTO;


public interface EspecialidadService {

    TableStructureDTO obtenerAgrupadosPorLaboratorio(EspecialidadByLabRequest request);

    TableStructureDTO obtenerOtrasEspecialidades(EspecialidadByLabRequest request);
}
