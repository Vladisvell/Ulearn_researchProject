package Graphics;

import DefaultPackage.conn;
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

public class StudentGenderPie extends ApplicationFrame {

    private static final long serialVersionUID = 1L;

    Color[] colors = {new Color(200, 200, 255), new Color(255, 200, 200),
            new Color(200, 255, 200), new Color(200, 255, 200)};

    static {
        // set a theme using the new shadow generator feature available in
        // 1.0.14 - for backwards compatibility it is not enabled by default
        ChartFactory.setChartTheme(new StandardChartTheme("JFree/Shadow", true));
    }

    public StudentGenderPie(String title) throws SQLException {
        super(title);
        setContentPane(createDemoPanel());
    }

    private PieDataset createDataset() throws SQLException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        var sexes = conn.GetSexes();
        dataset.setValue("Женский" , sexes[0]);
        dataset.setValue("Мужской", sexes[1]);
        dataset.setValue("Боевой вертолёт", sexes[2]);
        return dataset;
    }

    private JFreeChart createChart(PieDataset dataset)
    {
        JFreeChart chart = ChartFactory.createPieChart(
                "Соотношение полов",  // chart title
                dataset,             // data
                true,               // legend
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
        TextTitle source = new TextTitle("Соотношение полов",
                new Font("Courier New", Font.PLAIN, 14));
        source.setPaint(Color.WHITE);
        source.setPosition(RectangleEdge.BOTTOM);
        source.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        chart.addSubtitle(source);

        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.GRAY);
        plot.setInteriorGap(0.1);
        plot.setOutlineVisible(false);

        // Определение секций круговой диаграммы
        plot.setSectionPaint("Женский" , Color.pink);
        plot.setSectionPaint("Мужской", Color.blue);
        plot.setSectionPaint("Боевой вертолёт", Color.black );
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

    public JPanel createDemoPanel() throws SQLException {
        JFreeChart chart = createChart(createDataset());
        chart.setPadding(new RectangleInsets(4, 8, 2, 2));
        ChartPanel panel = new ChartPanel(chart);
        panel.setFillZoomRectangle(true);
        panel.setMouseWheelEnabled(true);
        panel.setPreferredSize(new Dimension(800, 600));
        return panel;
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        conn.Conn();
        StudentGenderPie demo = new StudentGenderPie("Соотношения полов присутствующих на курсе");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
    }
}
