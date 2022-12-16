package DefaultPackage;

import Graphics.*;

import java.sql.SQLException;

public class Graphics_Shower {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //Graphics.ProgressHistogramm.main(null); //гистограмма успеваемости студентов - deprecated
        //Graphics.Groups_ProgressHistogramm.main(null); //гистограмма успеваемости по группам - deprecated.
        //Graphics.SportProgressHistogramm.main(null); //успеваемость по группам СПОРТ - deprecated
        //Graphics.ComfortProgressHistogramm.main(null); //успеваемость по группам КОМФОРТ - deprecated
        StudentProgressPie.main(null); //пирог успеваемости студентов по процентам
        StudentGenderPie.main(null); //соотношение полов
        StudentDifficultiesPie.main(null); //относительные успеваемости СПОРТА и КОМФОРТА
        PracticesAndHomeworksHistogramm.main(null); //средний прогресс по упражнениям и домашним заданиям в отношении
                                                    //100%
        ModulesProgressHistogramm.main(null); //средние успеваемости по всем модулям
        conn.CloseDB();
    }
}
