package com.sisuz.reportes.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "DET_ESPECIALIDAD")
public class EspecialidadDetalle {

    @Id
    private Integer id;

    @Column(name = "ESPECIALIDAD")
    private String especialidad;

    @Column(name = "MES")
    private LocalDate fecha;

    @Column(name = "COD_PROD")
    private String codProd;

    @Column(name = "DESC_PROD")
    private String descProd;

    @Column(name = "UNIDADES")
    private String unidad;

    @Column(name = "VALOR")
    private String valor;

    @Column(name = "EMPRESA")
    private String empresa;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        EspecialidadDetalle that = (EspecialidadDetalle) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
