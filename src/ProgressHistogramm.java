import java.awt.*;
import java.sql.SQLException;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;

import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;

import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import org.jfree.data.category.CategoryDataset;

import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;

import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;

public class ProgressHistogramm extends ApplicationFrame
{
    private static final long serialVersionUID = 1L;

    public ProgressHistogramm(final String title) throws SQLException, ClassNotFoundException {
        super(title);

        final CategoryDataset dataset    = Dataset_creator.createDataset4();
        final JFreeChart      chart      = createChart(dataset);
        final ChartPanel      chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(chartPanel);

    }
    private JFreeChart createChart(final CategoryDataset dataset) {

        final JFreeChart chart = ChartFactory.createBarChart3D(
                "Статистика успеваемости студентов",   // chart title
                "",                  // domain axis label
                "Число студентов",                  // range axis label
                dataset,                  // data
                PlotOrientation.VERTICAL, // orientation
                true,                     // include legend
                true,                     // tooltips
                false                     // urls
        );
        // Определение фона plot'a
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint (new Color(212, 212, 248));

        // Настройка CategoryAxis
        CategoryAxis axis = plot.getDomainAxis();
        // Скрытие осевых линий и меток делений
        axis.setAxisLineVisible (false);    // осевая линия
        axis.setTickMarksVisible(false);    // метки деления оси
        // Наклон меток значений
        axis.setCategoryLabelPositions(
                CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0)
        );

        CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setBaseItemLabelsVisible(true);
        BarRenderer r = (BarRenderer) renderer;
        r.setMaximumBarWidth(0.05);

        return chart;
    }

    public static void main(final String[] args) throws SQLException, ClassNotFoundException {
        final ProgressHistogramm demo = new ProgressHistogramm("3D статистика успеваемости студентов");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}
