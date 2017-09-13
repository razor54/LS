package pt.isel.ls;


import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import pt.isel.ls.control.JDBCConnection;
import pt.isel.ls.model.data.dtos.UniqueTableDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;


public class Arrange {

    private static String Delete = "Delete dbo.student_class_assoc\n" +
            "DELETE dbo.Students;\n" +
            "DELETE dbo.programme_course_assoc;\n" +
            "DELETE dbo.teacher_class_assoc;\n" +
            "DELETE dbo.Classes;\n" +
            "DELETE dbo.Curricular;\n" +
            "DELETE dbo.Courses;\n" +
            "DELETE dbo.Teachers;\n" +
            "DELETE dbo.Users;\n" +
            "DELETE dbo.Programmes;\n" +
            "DELETE dbo.ACADEMIC;";

    private static String Insert = "\n" +
            "\n" +
            "insert into Programmes values('Lic.Eng.Inf.Comp.','LEIC',6)\n" +
            "\n" +
            "insert into Users(email,Name,tipo)\n" +
            "\tvalues ('miguelmartins@sa.pt','Miguel Martins','S'),\n" +
            "\t\t   ('Filipes@sa.pt','Filipe Sousa','T'),\n" +
            "\t\t   ('luisp@sa.pt','Luis Pedro','T'),\n" +
            "\t\t   ('JoaoRocha@sa.pt','Joao Rocha','T'),\n" +
            "\t\t   ('JJadams@sa.pt','JJ Adams','S'),\n" +
            "\t\t   ('Lucasm@sa.pt','Lucas M.','S'),\n" +
            "\t\t   ('Manuemanue@sa.pt','Manuel Ourives','S'),\n" +
            "\t\t   ('Jose@sa.pt','Jose Sa','S')\n" +
            "\n" +
            "\n" +
            "insert into Students(email,ProgrammeID,number) \n" +
            "\tvalues ('miguelmartins@sa.pt','LEIC',1221),\n" +
            "\t\t\t('jjadams@sa.pt','LEIC',1220),\n" +
            "\t\t\t('lucasm@sa.pt','LEIC',1230),\n" +
            "\t\t\t('jose@sa.pt','LEIC',1231),\n" +
            "\t\t\t('Manuemanue@sa.pt','leic',1232)\n" +
            "\n" +
            "insert into Teachers(number,email)\n" +
            "\t\tvalues( 12,'Filipes@sa.pt'),\n" +
            "\t\t\t  (14,'luisp@sa.pt'),\n" +
            "\t\t\t  (13,'JoaoRocha@sa.pt')\n" +
            "\t\t\t\n" +
            "\n" +
            "\n" +
            "insert into Courses(Mandatory,name,acronym,coordinator) \n" +
            "\t\t\tvalues ('false','LabSoft','LS',12),\n" +
            "\t\t\t       ('false','Mod. Padrao Dados','MPD',14),\n" +
            "\t\t\t\t   ('false','Sist.Multi.','SM',12)\n" +
            "\n" +
            "insert into Academic(SemYear,SemType,representation) \n" +
            "\t\t\t values (1617,'v','1617v')\n" +
            "\t\t\t        \n" +
            "\n" +
            "\n" +
            "insert into Classes (courseACR,semRepresentation,cID) \n" +
            "\t\t\t values ('LS','1617v','d1'),\n" +
            "\t\t\t\t\t('MPD','1617v','d2'),\n" +
            "\t\t\t\t\t('LS','1617v','d2'),\n" +
            "\t\t\t\t\t('sm','1617v','d1')\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n" +
            "insert into teacher_class_assoc (teacherNum,classCourseAcr,classCourseSem,classCID)\n" +
            "\t\t\t\t\t\t  values(12,'LS','1617v','d1'),\n" +
            "\t\t\t\t\t\t\t\t(13,'LS','1617v','d2'),\n" +
            "\t\t\t\t\t\t\t\t(13,'MPD','1617v','d2')\n" +
            "\n" +
            "\n" +
            "\n" +
            "insert into programme_course_assoc(course_fk_acr, programme_fk_acr)\n" +
            "\t\t\t\t\t\t\tvalues('LS','LEIC'),\n" +
            "\t\t\t\t\t\t\t\t  ('MPD','LEIC')\n" +
            "update Courses \n" +
            "\t\tset Mandatory='true'\n" +
            "\t\twhere acronym='ls'\n" +
            "\n" +
            "update Courses\n" +
            "\t\tset Mandatory='false'\n" +
            "\t\twhere acronym='mpd'\n" +
            "\n" +
            "insert into Curricular(courseID,programmeAcr,CurricularSem)\n" +
            "\t\t\t\tvalues('LS','LEIC',4),\n" +
            "\t\t\t\t\t  ('MPD','LEIC',4),\n" +
            "\t\t\t\t\t  ('MPD','LEIC',6)\n" +
            "\n" +
            "\n" +
            "\n" +
            "INSERT INTO student_class_assoc(number, courseACR, semRepresentation , cID )\n" +
            "\t\t\t\t\t\t\tvalues\t\n" +
            "\t\t\t\t\t\t\t\t\t(1232,'LS','1617v','d1'),\n" +
            "\t\t\t\t\t\t\t\t\t(1231,'LS','1617v','d1')\n" +
            "\n" +
            "\n" +
            "\n" +
            "\n";

    public static void beforeTest() {
        final SQLServerDataSource sqlServerDataSource = JDBCConnection.createDataSource("TestBase");
        Connection connection = null;
        try {
            connection = sqlServerDataSource.getConnection();
        } catch (SQLServerException e) {
            System.out.println("Error connecting");
        }
        PreparedStatement statement = null;
        try {
            assert connection != null;
            statement = connection.prepareStatement(Delete);
            if (statement == null) System.out.println("Null connection");
        } catch (SQLException e) {
            System.out.println("Error on statement");
        }
        try {
            if (statement == null) System.out.println("null statement");
            assert statement != null;
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error deleting");
        }
        try {
            statement = connection.prepareStatement(Insert);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error inserting");
        }
        try {
            statement.close();
        } catch (SQLException e) {
            System.out.println("Statement closing error");
        }
    }

    public static HashMap<String, String> createMap() {

        HashMap<String, String> map = new HashMap<>();
        map.put("top", "10");
        map.put("skip", "0");
        map.put("accept", "text/plain");
        //   map.put("file-name", "C:\\Users\\Druh\\Documents\\AISEL\\17-17\\LS\\1617-2-LI41D-G6\\src\\test\\resources\\test.txt");
        return map;
    }


    public static void timeout(long time) {
        long initial = System.currentTimeMillis();
        while ((System.currentTimeMillis() - initial) <= time) ;
    }

    public static int viewTable(SQLServerDataSource source, String entity) {
        UniqueTableDto classes;
        try {
            classes = JDBCConnection.selectFrom(entity, source, 0, 10);
            return classes.getRowNumber();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
