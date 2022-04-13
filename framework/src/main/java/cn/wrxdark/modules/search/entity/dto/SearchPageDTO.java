package cn.wrxdark.modules.search.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchPageDTO<T> {
    /**
     * 搜索内容列表
     */
    private List<T> content;
    /**
     * 列表总页数
     */
    private Integer totalPages;
    /**
     * 列表每页大小
     */
    private Integer size;
    /**
     * 列表总大小
     */
    private Integer totalElements;
    
}
