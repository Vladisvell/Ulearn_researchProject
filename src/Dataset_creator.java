import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DatasetUtilities;

import java.sql.SQLException;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Dataset_creator
{
    private static final double[][] data = new double[][] {
    	     {12.0, 29.0, 36.0, 64.0, 58.0, 79.0, 70.0, 92.0},
             {49.0, 72.0, 74.0, 68.0, 88.0, 54.0, 38.0, 23.0},
             {41.0, 33.0, 22.0, 34.0, 62.0, 32.0, 42.0, 34.0}
    };

    public static CategoryDataset createDataset1()
    {
    	DefaultCategoryDataset dataset; 
        // row keys...
        final String series1 = "Чай"     ;
        final String series2 = "Кофе"    ;
        final String series3 = "Коктейль";

        // column keys...
        final String category1 = "Январь" ;
        final String category2 = "Февраль";
        final String category3 = "Март"   ;
        final String category4 = "Апрель" ;
        final String category5 = "Май"    ;

        dataset = new DefaultCategoryDataset();

        dataset.addValue(3.1, series1, category1);
        dataset.addValue(2.2, series1, category2);
        dataset.addValue(2.3, series1, category3);
        dataset.addValue(3.4, series1, category4);
        dataset.addValue(4.5, series1, category5);

        dataset.addValue(7.2, series2, category1);
        dataset.addValue(5.4, series2, category2);
        dataset.addValue(6.2, series2, category3);
        dataset.addValue(6.3, series2, category4);
        dataset.addValue(7.5, series2, category5);

        dataset.addValue(3.5, series3, category1);
        dataset.addValue(2.8, series3, category2);
        dataset.addValue(2.9, series3, category3);
        dataset.addValue(3.3, series3, category4);
        dataset.addValue(4.8, series3, category5);
        
        return dataset;        
    }    

    public static CategoryDataset createDataset2() { 
        return DatasetUtilities.createCategoryDataset("Series ", "Factor ", data);
    }

    public static CategoryDataset createDataset4() throws SQLException, ClassNotFoundException {
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        DatabaseLauncher.main(null);
        var set = conn.GetProgresses();
        result.addValue(set[0], "0-20%", "0-20%");
        result.addValue(set[1], "20%-40%", "20%-40%");
        result.addValue(set[2], "40%-60%", "40%-60%");
        result.addValue(set[3], "60%-80%", "60%-80%");
        result.addValue(set[4], "80%-100%", "80%-100%");
        return result;
    }

    public static CategoryDataset createDataset5() throws SQLException, ClassNotFoundException {
        conn.Conn();
        DefaultCategoryDataset result = new DefaultCategoryDataset();

        //TODO: отсортировать это
        var averages = conn.GetAverageScores();
        LinkedHashMap<String, Float> linked = new LinkedHashMap<>();
        var entries = averages.entrySet().stream()
                .sorted(Map.Entry.comparingByValue()).collect(Collectors.toList());
        Collections.reverse(entries);
        for(var entry: entries){
            //result.addValue(entry.getValue(), entry.getKey(), entry.getKey());
            result.addValue(entry.getValue(), entry.getKey(), "");
        }
        return result;
    }


    public static CategoryDataset createDataset6() throws SQLException, ClassNotFoundException {
        conn.Conn();
        DefaultCategoryDataset result = new DefaultCategoryDataset();

        //TODO: отсортировать это
        var averages = conn.GetAverageScores();
        LinkedHashMap<String, Float> linked = new LinkedHashMap<>();
        var entries = averages.entrySet().stream().filter(x -> x.getKey().contains("У2"))
                .sorted(Map.Entry.comparingByValue()).collect(Collectors.toList());
        Collections.reverse(entries);
        for(var entry: entries){
            //result.addValue(entry.getValue(), entry.getKey(), entry.getKey());
            result.addValue(entry.getValue(), entry.getKey(), "");
        }
        return result;
    }

    public static CategoryDataset createDataset7() throws SQLException, ClassNotFoundException {
        conn.Conn();
        DefaultCategoryDataset result = new DefaultCategoryDataset();

        //TODO: отсортировать это
        var averages = conn.GetAverageScores();
        LinkedHashMap<String, Float> linked = new LinkedHashMap<>();
        var entries = averages.entrySet().stream().filter(x -> x.getKey().contains("У1"))
                .sorted(Map.Entry.comparingByValue()).collect(Collectors.toList());
        Collections.reverse(entries);
        for(var entry: entries){
            //result.addValue(entry.getValue(), entry.getKey(), entry.getKey());
            result.addValue(entry.getValue(), entry.getKey(), "");
        }
        return result;
    }
}
