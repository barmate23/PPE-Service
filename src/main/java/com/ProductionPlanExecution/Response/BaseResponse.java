package com.ProductionPlanExecution.Response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
public class BaseResponse<T> {
    private Integer status;
    private String message;
    private List<T> data;
    private Integer code;
    private String logId;
    private Integer totalPageCount;
    private Long totalRecordCount;
    public BaseResponse(Integer status, String message,List<T> data, Integer code, String logId) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.code = code;
        this.logId = logId;
    }

    public BaseResponse(Integer status, String message,List<T> data, Integer code, String logId, Integer totalPageCount, Long totalRecordCount) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.code = code;
        this.logId = logId;
        this.totalPageCount = totalPageCount;
        this.totalRecordCount = totalRecordCount;
    }
}

