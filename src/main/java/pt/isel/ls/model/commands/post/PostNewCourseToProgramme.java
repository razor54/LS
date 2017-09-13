package pt.isel.ls.model.commands.post;

import pt.isel.ls.model.exceptions.InvalidParametersException;
import pt.isel.ls.utils.Utils;

import java.sql.SQLException;
import java.util.HashMap;


public class PostNewCourseToProgramme extends AbstractPost {

    private final String INSERT = "insert into Curricular(courseID,programmeAcr,CurricularSem)" +
            "values(?,?,?)";
    private final String UPDATE = "update Courses " +
            "set Mandatory = ? " +
            "where acronym = ? ";
    private final String QUERY = "select mandatory from courses\twhere acronym=?";

    private final String QUERY2 = "select *from curricular where programmeAcr=? and courseid=?";

    public PostNewCourseToProgramme() {
        super("POST /programmes/{pid}/courses", PostNewCourseToProgramme::redirect);
    }

    @Override
    protected boolean postInsert(HashMap<String, String> map) throws SQLException {
        try {
            if (connection != null)
                connection.setAutoCommit(false);

            if (Boolean.parseBoolean(map.get("mandatory")) && map.get("semesters").length() > 1)
                throw new InvalidParametersException("More than one semester inserted for a mandatory course");

            String acr = map.get("acronym");
            statementInsert = connection.prepareStatement(QUERY);
            statementInsert.setString(1, acr);
            resultSet = statementInsert.executeQuery();
            String[] result = Utils.printResultSet(resultSet).split(" ");
            if (result.length == 1)
                throw new InvalidParametersException("No course with " + acr + " acronym");
            String mandatory = result[1]
                    .replace("\n", "");
            String pid = map.get("pid");

            statementInsert = connection.prepareStatement(QUERY2);
            statementInsert.setString(1, pid);
            statementInsert.setString(2, acr);
            resultSet = statementInsert.executeQuery();
            String[] result2 = Utils.printResultSet(resultSet).split(" ");

            //ja tem dados na base para o course x no programa y
            if (mandatory.equals("true") && result2.length != 1)
                throw new InvalidParametersException("Mandatory Courses only have one Semester");
            if (!mandatory.equals(map.get("mandatory")))
                throw new InvalidParametersException("Mandatory Courses cannot be optional and vice-versa");

            statementInsert = connection != null ? connection.prepareStatement(UPDATE) : null;
            statementInsert.setString(1, map.get("mandatory"));
            statementInsert.setString(2, acr);

            if (statementInsert != null) statementInsert.executeUpdate();

            String semesters[] = map.get("semesters").split(",");


            for (String semester : semesters) {
                statementInsert = connection != null ? connection.prepareStatement(INSERT) : null;
                assert statementInsert != null;
                statementInsert.setString(1, acr);
                statementInsert.setString(2, pid);
                statementInsert.setInt(3, Integer.parseInt(semester));

                if (statementInsert != null) statementInsert.executeUpdate();
            }


            if (connection != null) {
                connection.commit();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            if (connection != null) {
                connection.rollback();
            }
            throw new SQLException(e.getLocalizedMessage());
        }
        return false;

    }

    public HashMap<String, String> pathConverter(String[] path, HashMap<String, String> map) {
        map.put("pid", path[2]);
        return map;
    }

    private static String redirect(HashMap<String, String> map) {
        return "/programmes/" + map.get("pid") + "/courses";
    }
}
