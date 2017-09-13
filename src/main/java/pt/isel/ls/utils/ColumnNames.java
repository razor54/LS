package pt.isel.ls.utils;

import java.util.Arrays;
import java.util.List;


public class ColumnNames {

    public static final String COURSE_ACR = "Course Acronym";
    public static final String TEACHER_NUMBER = "Teacher Number";
    public static final String SEM_REPRESENTATION = "Semester Representation";
    public static final String CLASS_ID = "Class ID";
    public static final List<String> classColumnNames = Arrays.asList(COURSE_ACR, SEM_REPRESENTATION, CLASS_ID);

    public static final List<String> teacherClassColumnNames = Arrays.asList(TEACHER_NUMBER,COURSE_ACR, SEM_REPRESENTATION, CLASS_ID);


    public static final String EMAIL = "Email";
    public static final String NAME = "Name";
    public static final String PROGRAMME_ID = "Programme ID";
    public static final String NUMBER = "Number";
    public static final List<String> studentColumnNames = Arrays.asList(EMAIL, NAME, PROGRAMME_ID, NUMBER);

    public static final String COURSE_ID = "Course ID";
    public static final String PROGRAMME_ACR = "Programme Acronym";
    public static final String CURRICULAR_SEMESTER = "Curricular Semester";
    public static final String MANDATORY = "Mandatory";
    public static final String ACRONYM = "Acronym";
    public static final String COORDINATOR = "Coordinator";
    public static final List<String> courseColumnNames = Arrays.asList(MANDATORY, NAME, ACRONYM, COORDINATOR);

    public static final String LENGTH = "Length";
    public static final List<String> programmeColumnNames = Arrays.asList(NAME, LENGTH, COURSE_ID, NAME, ACRONYM, MANDATORY, CURRICULAR_SEMESTER);

    public static final List<String> teacherColumnNames = Arrays.asList(NUMBER, EMAIL);
}
