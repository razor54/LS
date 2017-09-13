//package pt.isel.ls.commands;
//
//import org.junit.Test;
//import pt.isel.ls.model.commands.HeaderCollection;
//import pt.isel.ls.Result.IResult;
//import pt.isel.ls.model.data.dtos.DTO;
//import pt.isel.ls.model.data.dtos.UniqueTableDto;
//
//import java.sql.Connection;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.function.Function;
//
//import static org.junit.Assert.assertNotNull;
//import static pt.isel.ls.control.JDBCConnection.*;
//
//public class HeaderCollectionTest {
//
//    @Test
//    public void should_find_default_header() throws SQLException {
//        HeaderCollection headers = new HeaderCollection();
//        HashMap<String, String> map = new HashMap<>();
//        map.put("accept", "hjk");
//        // Act
//        Function<DTO, IResult> res = headers.getResultFormat(map);
//        // Assert
//        assertNotNull(res);
//
//        // Visualize
//        viewResultExecution(res);
//
//
//    }
//
//    @Test
//    public void should_find_html_header() throws SQLException {
//
//        HashMap<String, String> map = new HashMap<>();
//        map.put("accept", "text/html");
//        // Act
//        Function<DTO, IResult> res = HeaderCollection.getResultFormat(map);
//        // Assert
//        assertNotNull(res);
//
//        // Visualize
//        viewResultExecution(res);
//    }
//
//    @Test
//    public void should_find_json_header() throws SQLException {
//        HeaderCollection headers = new HeaderCollection();
//        HashMap<String, String> map = new HashMap<>();
//        map.put("accept", "application/json");
//        // Act
//        Function<DTO, IResult> res = headers.getResultFormat(map);
//        // Assert
//        assertNotNull(res);
//        // Visualize
//        viewResultExecution(res);
//    }
//
//    private void viewResultExecution(Function<DTO, IResult> res) throws SQLException {
//        Connection connection = null;
//        try {
//            connection = beginConnection(createDataSource("TestBase"));
//            ResultSet r = connection.prepareStatement("select * from Users", ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE).executeQuery();
//            IResult rs = res.apply(new UniqueTableDto(r, 0, Integer.MAX_VALUE));
//            System.out.println(rs.getResult());
//
//        } catch (SQLException ignored) {
//            System.out.println(ignored.getMessage());
//        } finally {
//            if (connection != null) closeConnection(connection);
//        }
//    }
//}
