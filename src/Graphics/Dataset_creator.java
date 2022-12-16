package Graphics;

import DefaultPackage.DatabaseLauncher;
import DefaultPackage.conn;
import DefaultPackage.*;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DatasetUtilities;

import java.sql.SQLException;
import java.util.Collections;
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

    public static CategoryDataset createDataset8() throws SQLException, ClassNotFoundException{
        conn.Conn();
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        var averages = conn.GetAveragesFromModule("За весь курс");
        var ideal = conn.GetIdealScores();
        ideal[0] = ideal[0] / 100;
        ideal[1] = ideal[1] / 100;
        averages[0] = averages[0] / ideal[0];
        averages[1] = averages[1] / ideal[1];
        result.addValue(averages[0], "Практики", "Практики");
        result.addValue(averages[1], "Домашние задания", "Домашние задания");
        return result;
    }

    public static CategoryDataset createDataset9() throws SQLException, ClassNotFoundException {
        conn.Conn();
        DefaultCategoryDataset result = new DefaultCategoryDataset();
        var averages = conn.GetAveragesFromAllModules();
        var ideals = conn.GetIdealFromAllModules();
        for(String key: averages.keySet()){
            var avg = averages.get(key);
            var ideal = ideals.get(key);
            var percentage = avg / (ideal / 100);
            result.addValue(percentage, key, key);
        }
        return result;
    }
}
