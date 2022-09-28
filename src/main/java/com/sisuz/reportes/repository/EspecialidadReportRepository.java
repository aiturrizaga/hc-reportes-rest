package com.sisuz.reportes.repository;

import com.sisuz.reportes.model.EspecialidadDetalle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EspecialidadReportRepository extends JpaRepository<EspecialidadDetalle, Integer> {

    @Query(value = "select c.especialidad,\n" +
            "       to_char(c.mes, 'mm') mes,\n" +
            "       sum(c.unidades) Tot_Unidades,\n" +
            "       sum(c.valor) as Tot_Valor\n" +
            "  from DET_ESPECIALIDAD c\n" +
            " WHERE to_char(c.mes, 'mm/yyyy') in (:fechas)\n" +
            " and c.especialidad in (:especialidades)\n" +
            " group by c.especialidad, to_char(c.mes, 'mm')\n" +
            " order by 1, 2 asc", nativeQuery = true)
    List<Object[]> obtenerAgrupadosPorLaboratorio(@Param("especialidades") List<String> especialidades,
                                                  @Param("fechas") List<String> fechas);

    @Query(value = "select c.especialidad,\n" +
            "       to_char(c.mes, 'mm') mes,\n" +
            "       c.cod_prod,\n" +
            "       c.desc_prod,\n" +
            "       sum(c.unidades) Tot_Unidades,\n" +
            "       sum(c.valor) as Tot_Valor\n" +
            "  from DET_ESPECIALIDAD c\n" +
            " WHERE to_char(c.mes, 'mm/yyyy') in (:fechas)\n" +
            " and c.especialidad in (:especialidades)\n" +
            " group by c.especialidad,\n" +
            "          to_char(c.mes, 'mm'),\n" +
            "          c.cod_prod,\n" +
            "          c.desc_prod\n" +
            " order by 1, 2, 3 asc", nativeQuery = true)
    List<Object[]> obtenerOtrasEspecialidades(@Param("especialidades") List<String> especialidades,
                                              @Param("fechas") List<String> fechas);
}
