import java.sql.SQLException;

public class Graphics_Shower {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //ProgressHistogramm.main(null); //гистограмма успеваемости студентов - deprecated
        //Groups_ProgressHistogramm.main(null); //гистограмма успеваемости по группам - deprecated.
        //SportProgressHistogramm.main(null); //успеваемость по группам СПОРТ - deprecated
        //ComfortProgressHistogramm.main(null); //успеваемость по группам КОМФОРТ - deprecated
        StudentProgressPie.main(null); //пирог успеваемости студентов по процентам
        StudentGenderPie.main(null); //соотношение полов
        StudentDifficultiesPie.main(null); //относительные успеваемости СПОРТА и КОМФОРТА
        PracticesAndHomeworksHistogramm.main(null); //средний прогресс по упражнениям и домашним заданиям в отношении
                                                    //100%
    }
}
