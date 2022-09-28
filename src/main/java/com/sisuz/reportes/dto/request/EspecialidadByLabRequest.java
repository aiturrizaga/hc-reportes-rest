package com.sisuz.reportes.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class EspecialidadByLabRequest {
    private List<String> fechas;
    private List<String> especialidades;
}
