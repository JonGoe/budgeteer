package org.wickedsource.budgeteer.web.pages.dashboard.burnedbudgetchart;

import com.googlecode.wickedcharts.highcharts.options.*;
import com.googlecode.wickedcharts.highcharts.options.series.Series;
import org.wickedsource.budgeteer.MoneyUtil;
import org.wickedsource.budgeteer.web.BudgeteerSession;
import org.wickedsource.budgeteer.web.PropertyLoader;
import org.wickedsource.budgeteer.web.charts.ChartUtils;

public class BurnedBudgetChartOptions extends Options {

    public BurnedBudgetChartOptions(BurnedBudgetChartModel model) {
        setChart(new ChartOptions()
                .setType(SeriesType.COLUMN)
                .setHeight(300));

        setxAxis(new Axis()
                .setCategories(ChartUtils.getWeekLabels(model.getNumberOfWeeks(), PropertyLoader.getProperty(BurnedBudgetChart.class, "chart.weekLabelFormat"))));

        setyAxis(new Axis()
                .setTitle(new Title("")));

        addSeries(new Series<Double>() {
        }
                .setData(MoneyUtil.toDouble(model.getObject(), BudgeteerSession.get().getSelectedBudgetUnit()))
                .setName(PropertyLoader.getProperty(BurnedBudgetChart.class, "chart.seriesName")));

    }

}
