import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.sql.SQLException;

public class FirstPieChart extends ApplicationFrame {

    private static final long serialVersionUID = 1L;

    Color[] colors = {new Color(200, 200, 255), new Color(255, 200, 200),
            new Color(200, 255, 200), new Color(200, 255, 200)};

    static {
        // set a theme using the new shadow generator feature available in
        // 1.0.14 - for backwards compatibility it is not enabled by default
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow", true));
    }

    public FirstPieChart(String title) {
        super(title);
        setContentPane(createDemoPanel());
    }

    private PieDataset createDataset() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        var data = conn.studentsStats;
        dataset.setValue("0-20%" , data[0]);
        dataset.setValue("20-40%", data[1]);
        dataset.setValue("40%-60%", data[2]);
        dataset.setValue("60%-80%", data[3]);
        dataset.setValue("80-100%", data[4]);
        return dataset;
    }

    private JFreeChart createChart(PieDataset dataset)
    {
        JFreeChart chart = ChartFactory.createPieChart(
                "Успеваемость студентов",  // chart title
                dataset,             // data
                true,               // no legend
                true,                // tooltips
                false                // no URL generation
        );

        // Определение фона графического изображения
        chart.setBackgroundPaint(new GradientPaint(new Point(  0,   0), new Color(20, 20, 20),
                new Point(400, 200), Color.DARK_GRAY));
        // Определение заголовка
        TextTitle t = chart.getTitle();
        t.setHorizontalAlignment(HorizontalAlignment.LEFT);
        t.setPaint(new Color(240, 240, 240));
        t.setFont(new Font("Arial", Font.BOLD, 26));

        // Определение подзаголовка
        TextTitle source = new TextTitle("Успеваемость студентов по курсу",
                new Font("Courier New", Font.PLAIN, 14));
        source.setPaint(Color.WHITE);
        source.setPosition(RectangleEdge.BOTTOM);
        source.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        chart.addSubtitle(source);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.GRAY);
        plot.setInteriorGap(0.1);
        plot.setOutlineVisible(false);

        RadialGradientPaint rgpBlue  ;
        RadialGradientPaint rgpRed   ;
        RadialGradientPaint rgpGreen ;
        RadialGradientPaint rgpYellow;

        rgpBlue   = createGradientPaint(colors[0], Color.BLUE  );
        rgpRed    = createGradientPaint(colors[1], Color.RED   );
        rgpGreen  = createGradientPaint(colors[2], Color.GREEN );
        rgpYellow = createGradientPaint(colors[3], Color.YELLOW);

        // Определение секций круговой диаграммы
        plot.setSectionPaint("0-20%" , rgpBlue  );
        plot.setSectionPaint("20-40%", rgpRed   );
        plot.setSectionPaint("40%-60%"  , rgpGreen );
        plot.setSectionPaint("60%-80%", rgpYellow);
        plot.setSectionPaint("80%-100%", rgpRed);
        plot.setBaseSectionOutlinePaint(Color.WHITE);
        plot.setSectionOutlinesVisible(true);
        plot.setBaseSectionOutlineStroke(new BasicStroke(2.0f));

        // Настройка меток названий секций
        plot.setLabelFont(new Font("Courier New", Font.BOLD, 20));
        plot.setLabelLinkPaint(Color.WHITE);
        plot.setLabelLinkStroke(new BasicStroke(2.0f));
        plot.setLabelOutlineStroke(null);
        plot.setLabelPaint(Color.WHITE);
        plot.setLabelBackgroundPaint(null);

        return chart;
    }

    private RadialGradientPaint createGradientPaint(Color c1, Color c2) {
        Point2D center = new Point2D.Float(0, 0);
        float radius = 200;
        float[] dist = {0.0f, 1.0f};
        return new RadialGradientPaint(center, radius, dist,
                new Color[] {c1, c2});
    }

    public JPanel createDemoPanel() {
        JFreeChart chart = createChart(createDataset());
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(800, 600));
        return panel;
    }

    public static void main(String[] args)
    {
        try {
            DatabaseLauncher.main(null);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        FirstPieChart demo = new FirstPieChart("Успеваемость студентов относительно максимума");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}
