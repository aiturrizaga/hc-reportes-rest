package com.sisuz.reportes.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@ToString
@Builder
public class TableStructureDTO {
    private List<ColStructureDTO> columns;
    private List<Map<String, Object>> dataSource;

    @Getter
    @Setter
    @ToString
    @Builder
    public static class ColStructureDTO {
        private String id;
        private String title;
        private String dataIndex;
        private String key;
        private List<ColStructureDTO> children;
    }
}
