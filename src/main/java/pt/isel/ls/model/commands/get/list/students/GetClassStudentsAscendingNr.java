package pt.isel.ls.model.commands.get.list.students;


public class GetClassStudentsAscendingNr extends GetClassStudents {

    public GetClassStudentsAscendingNr() {
        super("GET /courses/{acr}/students/{sem}/{num}/students/sorted");
        STATEMENT = STATEMENT + "\norder by number asc";
    }

}
