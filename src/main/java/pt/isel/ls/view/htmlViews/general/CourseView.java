package pt.isel.ls.view.htmlViews.general;

import pt.isel.ls.model.data.entities.Course;
import pt.isel.ls.model.data.entities.Programme;
import pt.isel.ls.utils.html.HTMLTr;
import pt.isel.ls.view.htmlViews.View;
import pt.isel.ls.view.htmlViews.specific.SClassView;
import pt.isel.ls.view.htmlViews.specific.SCourseView;
import pt.isel.ls.view.htmlViews.specific.SProgrammeView;
import pt.isel.ls.view.htmlViews.specific.STeacherView;

import java.util.stream.Stream;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static pt.isel.ls.model.data.entities.Course.COORDINATOR_REF;
import static pt.isel.ls.model.data.entities.Programme.COURSE_REF;


public class CourseView extends View<Course> {
    //what is there to change ??
    // only adding rows to a table


    private static final String htmlFile = "public/views/Courses.html";

    protected String filePath;

    protected CourseView(String filePath) {
        this.filePath = filePath;
    }

    public CourseView() {
        this.filePath = htmlFile;
    }

    @Override
    public String getHtml(Stream<Course> entities) {
        return String.format(readHtmlFromFIle(filePath),
                _formatter.formatHtml(translateStream(entities),1),
                buildPaging(infoTable)) ;
    }

    public String getEntityHtml(Course c) {
        // basic info
        return contructGenericRow(c).getName();
    }

    protected HTMLTr<String> contructGenericRow(Course c) {
        HTMLTr<String> line = new HTMLTr<>();
        int coordinatorID = c.getCoordinatorID();
        String courceAcronym = c.getCourceAcronym();
        line
                .addData(valueOf(c.getMandatory()))
                .addData(c.getName())
                .addData(courceAcronym, format(COURSE_REF, courceAcronym))
                .addData(valueOf(coordinatorID), format(COORDINATOR_REF, coordinatorID));
        return line;
    }


    public static void main(String[] args) {
        SCourseView scView = new SCourseView();

//        testCourseView();
//        testSpecificCourses();
//        testProgramme();
//            testSpecificProgramme();
        testSpecificClass();
        testSpecificTeacher();
/*            testSpecificStudent();
            testClasses();
            testTeacheres();
            testcStudents();*/

    }

    private static void testSpecificTeacher() {
        STeacherView scView = new STeacherView();
      /*  Teacher c =new Course("lic", "1", "false", "laboratorio de informatica e computadores",
                new String[]{"abs", "b a ba"}, new String[]{"0xffff", "0x0000"});
        Stream<Course> cs = Stream.of(c);
*/
        // System.out.println(scView.getHtml(cs));
    }

    private static void testSpecificClass() {
        SClassView scView = new SClassView();
        Course c = new Course("lic", "1", "false", "laboratorio de informatica e computadores",
                new String[]{"abs", "b a ba"}, new String[]{"0xffff", "0x0000"});
        Stream<Course> cs = Stream.of(c);

        //  System.out.println(scView.getHtml(cs));
    }

    private static void testSpecificCourses() {
        SCourseView scView = new SCourseView();
        Course c = new Course("lic", "1", "false", "laboratorio de informatica e computadores",
                new String[]{"abs", "b a ba"}, new String[]{"0xffff", "0x0000"});
        Stream<Course> cs = Stream.of(c);

        System.out.println(scView.getHtml(cs));
    }

    private void testCourseView() {
        Course c = new Course("lic", "1", "false", "laboratorio de informatica e computadores");
        CourseView cView = new CourseView();
        Stream<Course> cs = Stream.of(c);
        System.out.println(cView.getHtml(cs));
    }

    private static void testSpecificProgramme() {
        SProgrammeView scView = new SProgrammeView();
        Programme p = new Programme("leic", "6", "licenciatura em informatica", new String[]{"abs", "b a ba"});
        Stream<Programme> cs = Stream.of(p);
        System.out.println(scView.getHtml(cs));
    }

    private static void testProgramme() {
        ProgrammeView scView = new ProgrammeView();
        Programme p = new Programme("leic", "6", "licenciatura em informatica");
        Stream<Programme> cs = Stream.of(p);
        System.out.println(scView.getHtml(cs));
    }
}
