import java.sql.SQLException;

public class Graphics_Shower {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //FirstBarChart.main(null); //гистограмма успеваемости студентов - deprecated
        //SecondBarChart.main(null); //гистограмма успеваемости по группам - deprecated.
        //ThirdBarChart.main(null); //успеваемость по группам СПОРТ - deprecated
        //FourthBarChart.main(null); //успеваемость по группам КОМФОРТ - deprecated
        FirstPieChart.main(null); //пирог успеваемости студентов по процентам
        SecondPieChart.main(null); //соотношение полов
        ThirdPieChart.main(null); //относительные успеваемости СПОРТА и КОМФОРТА
    }
}
