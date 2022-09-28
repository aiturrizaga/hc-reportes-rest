package com.sisuz.reportes.service.impl;

import com.sisuz.reportes.dto.request.EspecialidadByLabRequest;
import com.sisuz.reportes.dto.response.EspecialidadReportResponse;
import com.sisuz.reportes.dto.response.TableStructureDTO;
import com.sisuz.reportes.repository.EspecialidadReportRepository;
import com.sisuz.reportes.service.EspecialidadService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class EspecialidadServiceImpl implements EspecialidadService {

    private final EspecialidadReportRepository especialidadReportRepository;

    public EspecialidadServiceImpl(EspecialidadReportRepository especialidadReportRepository) {
        this.especialidadReportRepository = especialidadReportRepository;
    }

    @Override
    public TableStructureDTO obtenerAgrupadosPorLaboratorio(EspecialidadByLabRequest request) {
        List<TableStructureDTO.ColStructureDTO> columns = new ArrayList<>();
        List<EspecialidadReportResponse> reports = getEspecialidadesReport(request);

        columns.add(
                TableStructureDTO.ColStructureDTO.builder()
                        .id("especialidad")
                        .title("Especialidad")
                        .dataIndex("especialidad")
                        .key("especialidad")
                        .children(Collections.emptyList())
                        .build()
        );

        reports.stream()
                .filter(distinctByKey(EspecialidadReportResponse::getMes))
                .collect(Collectors.toList())
                .forEach(det -> {
                            try {
                                columns.add(
                                        TableStructureDTO.ColStructureDTO.builder()
                                                .id(det.getMes())
                                                .title(getMonthByIndex(det.getMes()).toUpperCase())
                                                .dataIndex(getMonthByIndex(det.getMes()).toLowerCase())
                                                .key(getMonthByIndex(det.getMes()).toLowerCase())
                                                .children(loadSubColumns(det.getMes()))
                                                .build()
                                );
                            } catch (ParseException e) {
                                log.error("Error al parsear el mes a string: {}", e.getMessage());
                            }
                        }
                );

        columns.add(
                TableStructureDTO.ColStructureDTO.builder()
                        .id("totalUnidades")
                        .title("Total Unidades")
                        .dataIndex("totalUnidades")
                        .key("totalUnidades")
                        .children(Collections.emptyList())
                        .build()
        );
        columns.add(
                TableStructureDTO.ColStructureDTO.builder()
                        .id("totalValores")
                        .title("Total Valores")
                        .dataIndex("totalValores")
                        .key("totalValores")
                        .children(Collections.emptyList())
                        .build()
        );

        List<Map<String, Object>> source = new ArrayList<>();
        reports.stream()
                .filter(distinctByKey(EspecialidadReportResponse::getEspecialidad))
                .collect(Collectors.toList())
                .forEach(withCounter((i, data) -> {
                            Map<String, Object> items = new LinkedHashMap<>();
                            List<Integer> unidades = new ArrayList<>();
                            List<BigDecimal> valores = new ArrayList<>();
                            items.put("key", i);
                            items.put("especialidad", data.getEspecialidad());
                            reports.stream()
                                    .filter(r -> r.getEspecialidad().equalsIgnoreCase(data.getEspecialidad()))
                                    .collect(Collectors.toList())
                                    .forEach(item -> {
                                        items.put("tu" + item.getMes(), item.getTotalUnidad());
                                        items.put("tv" + item.getMes(), item.getTotalValor());
                                        unidades.add(item.getTotalUnidad());
                                        valores.add(item.getTotalValor());
                                    });
                            items.put("totalUnidades", unidades.stream().reduce(0, Integer::sum));
                            items.put("totalValores", valores.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
                            source.add(items);
                        })
                );

        return TableStructureDTO.builder()
                .columns(columns)
                .dataSource(source)
                .build();
    }

    @Override
    public TableStructureDTO obtenerOtrasEspecialidades(EspecialidadByLabRequest request) {
        List<TableStructureDTO.ColStructureDTO> columns = new ArrayList<>();
        List<EspecialidadReportResponse> reports = getOtraEspecialidadesReport(request);

        columns.add(
                TableStructureDTO.ColStructureDTO.builder()
                        .id("especialidad")
                        .title("Especialidad")
                        .dataIndex("especialidad")
                        .key("especialidad")
                        .children(Collections.emptyList())
                        .build()
        );

        columns.add(
                TableStructureDTO.ColStructureDTO.builder()
                        .id("codigoProducto")
                        .title("Cod. Producto")
                        .dataIndex("codigoProducto")
                        .key("codigoProducto")
                        .children(Collections.emptyList())
                        .build()
        );

        columns.add(
                TableStructureDTO.ColStructureDTO.builder()
                        .id("descripcionProducto")
                        .title("Desc. Producto")
                        .dataIndex("descripcionProducto")
                        .key("descripcionProducto")
                        .children(Collections.emptyList())
                        .build()
        );

        reports.stream()
                .filter(distinctByKey(EspecialidadReportResponse::getMes))
                .collect(Collectors.toList())
                .forEach(det -> {
                            try {
                                columns.add(
                                        TableStructureDTO.ColStructureDTO.builder()
                                                .id(det.getMes())
                                                .title(getMonthByIndex(det.getMes()).toUpperCase())
                                                .dataIndex(getMonthByIndex(det.getMes()).toLowerCase())
                                                .key(getMonthByIndex(det.getMes()).toLowerCase())
                                                .children(loadSubColumns(det.getMes()))
                                                .build()
                                );
                            } catch (ParseException e) {
                                log.error("Error al parsear el mes a string: {}", e.getMessage());
                            }
                        }
                );

        columns.add(
                TableStructureDTO.ColStructureDTO.builder()
                        .id("totalUnidades")
                        .title("Total Unidades")
                        .dataIndex("totalUnidades")
                        .key("totalUnidades")
                        .children(Collections.emptyList())
                        .build()
        );
        columns.add(
                TableStructureDTO.ColStructureDTO.builder()
                        .id("totalValores")
                        .title("Total Valores")
                        .dataIndex("totalValores")
                        .key("totalValores")
                        .children(Collections.emptyList())
                        .build()
        );

        List<Map<String, Object>> source = new ArrayList<>();
        reports.stream()
                .filter(distinctByKey(EspecialidadReportResponse::getEspecialidad))
                .collect(Collectors.toList())
                .forEach(esp -> {
                    final Map<String, Object> header = new LinkedHashMap<>();
                    List<Map<String, Object>> children = new ArrayList<>();
                    reports.stream()
                            .filter(res -> esp.getEspecialidad().equalsIgnoreCase(res.getEspecialidad()))
                            .collect(Collectors.toList())
                            .forEach(withCounter((i, data) -> {
                                        Map<String, Object> items = new LinkedHashMap<>();
                                        List<Integer> unidades = new ArrayList<>();
                                        List<BigDecimal> valores = new ArrayList<>();
                                        if (i == 0) {
                                            header.put("key", data.getCodigoProducto());
                                            header.put("especialidad", data.getEspecialidad());
                                            header.put("codigoProducto", data.getCodigoProducto());
                                            header.put("descripcionProducto", data.getDescripcionProducto());
                                        }

                                        items.put("key", data.getCodigoProducto());
                                        items.put("especialidad", "");
                                        items.put("codigoProducto", data.getCodigoProducto());
                                        items.put("descripcionProducto", data.getDescripcionProducto());
                                        reports.stream()
                                                .filter(r -> (r.getCodigoProducto().equalsIgnoreCase(data.getCodigoProducto())))
                                                .collect(Collectors.toList())
                                                .forEach(item -> {
                                                    unidades.add(item.getTotalUnidad());
                                                    valores.add(item.getTotalValor());
                                                    if (i == 0) {
                                                        header.put("tu" + item.getMes(), item.getTotalUnidad());
                                                        header.put("tv" + item.getMes(), item.getTotalValor());
                                                        header.put("totalUnidades", unidades.stream().reduce(0, Integer::sum));
                                                        header.put("totalValores", valores.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
                                                    } else {
                                                        items.put("tu" + item.getMes(), item.getTotalUnidad());
                                                        items.put("tv" + item.getMes(), item.getTotalValor());
                                                        items.put("totalUnidades", unidades.stream().reduce(0, Integer::sum));
                                                        items.put("totalValores", valores.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
                                                    }
                                                });
                                        children.add(items);
                                    })
                            );

                    children.stream()
                            .filter(f -> String.valueOf(f.get("key")).equalsIgnoreCase(String.valueOf(header.get("key"))))
                            .collect(Collectors.toList())
                            .forEach(c -> children.remove(c));

                    Set<Map<String, Object>> childrenSet = new HashSet<>(children);
                    header.put("children", childrenSet);
                    source.add(header);
                });

        return TableStructureDTO.builder()
                .columns(columns)
                .dataSource(source)
                .build();
    }

    public static <T> Consumer<T> withCounter(BiConsumer<Integer, T> consumer) {
        AtomicInteger counter = new AtomicInteger(0);
        return item -> consumer.accept(counter.getAndIncrement(), item);
    }

    private List<TableStructureDTO.ColStructureDTO> loadSubColumns(String id) {
        List<TableStructureDTO.ColStructureDTO> subColumns = new ArrayList<>();
        subColumns.add(
                TableStructureDTO.ColStructureDTO.builder()
                        .id(String.format("tu%s", id))
                        .dataIndex(String.format("tu%s", id))
                        .title("Total Unidad")
                        .build()
        );

        subColumns.add(
                TableStructureDTO.ColStructureDTO.builder()
                        .id(String.format("tv%s", id))
                        .dataIndex(String.format("tv%s", id))
                        .title("Total Valor")
                        .build()
        );
        return subColumns;
    }

    private String getMonthByIndex(String month) throws ParseException {
        SimpleDateFormat monthParse = new SimpleDateFormat("MM");
        SimpleDateFormat monthDisplay = new SimpleDateFormat("MMMM");
        return monthDisplay.format(monthParse.parse(month));
    }

    private List<EspecialidadReportResponse> getEspecialidadesReport(EspecialidadByLabRequest request) {
        List<EspecialidadReportResponse> reports = new ArrayList<>();
        especialidadReportRepository.obtenerAgrupadosPorLaboratorio(request.getEspecialidades(), request.getFechas())
                .forEach(res -> reports.add(
                        EspecialidadReportResponse
                                .builder()
                                .especialidad(String.valueOf(res[0]))
                                .mes(String.valueOf(res[1]))
                                .totalUnidad(Integer.parseInt(String.valueOf(res[2])))
                                .totalValor(new BigDecimal(String.valueOf(res[3])))
                                .build()
                ));
        return reports;
    }

    private List<EspecialidadReportResponse> getOtraEspecialidadesReport(EspecialidadByLabRequest request) {
        List<EspecialidadReportResponse> reports = new ArrayList<>();
        especialidadReportRepository.obtenerOtrasEspecialidades(request.getEspecialidades(), request.getFechas())
                .forEach(res -> reports.add(
                        EspecialidadReportResponse
                                .builder()
                                .especialidad(String.valueOf(res[0]))
                                .mes(String.valueOf(res[1]))
                                .codigoProducto(String.valueOf(res[2]))
                                .descripcionProducto(String.valueOf(res[3]))
                                .totalUnidad(Integer.parseInt(String.valueOf(res[4])))
                                .totalValor(new BigDecimal(String.valueOf(res[5])))
                                .build()
                ));
        return reports;
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

}
