package com.k2data.k2app.chart;

import lombok.Data;

import java.util.List;

/**
 * @author lidong9144@163.com 17-3-27.
 */
@Data
public class ChartDomain {

    private List<String> labels;
    private List<Object> values;

}
