package com.sisuz.reportes.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Builder
public class EspecialidadReportResponse {
    private String keyId;
    private String especialidad;
    private String mes;
    private Integer totalUnidad;
    private BigDecimal totalValor;

    private String codigoProducto;
    private String descripcionProducto;

    private Integer reservaSolicitada;
    private Integer reservaPagada;
    private Integer totalSolicitudes;
    private Integer totalPagadas;
}
