package com.k2data.k2app.chart;

import java.util.List;

/**
 * @author lidong9144@163.com 17-3-27.
 */
public interface BaseChartHelper<T> {

    ChartDomain generateData(List<T> source);

}
