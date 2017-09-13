use[LS]

CREATE TABLE ACADEMIC (
	semYear numeric(4) ,
	semType VARCHAR(1)
	CONSTRAINT [semester_cosntraint]
		CHECK (semType ='i' OR semType ='v'),
	representation  VARCHAR(5)unique ,
	primary key(semYear,SemType)
)

create table Programmes (
	name varchar(256) ,
	PID VARCHAR(4) primary key,
	length numeric not null
)


create table Users (
    email varchar(256) primary key,
    name varchar(256) NOT NULL,
	tipo varchar(1) not null,
	num numeric,
	CONSTRAINT [user_constraint]
		CHECK (tipo ='T'  or tipo = 'S')
)

create table Teachers (
	number numeric primary key,
	email VARCHAR (256) REFERENCES Users UNIQUE
)


create table Courses (
    Mandatory BIT ,
    name varchar(256) UNIQUE ,
    acronym VARCHAR(4) PRIMARY KEY,
    coordinator numeric REFERENCES dbo.Teachers UNIQUE,

)

CREATE TABLE Curricular(
    courseID varchar(4) references Courses(acronym),
	programmeAcr VARCHAR(4) REFERENCES dbo.Programmes(PID),
	CurricularSem numeric(1),
	primary key(courseID,programmeAcr,CurricularSem)
)


CREATE TABLE Classes (
	courseACR VARCHAR(4) REFERENCES dbo.Courses ,
	semRepresentation VARCHAR(5) REFERENCES dbo.ACADEMIC(representation),
	cID VARCHAR(2),

	PRIMARY KEY(courseACR,semRepresentation,cID),

)



CREATE TABLE teacher_class_assoc (
	teacherNum NUMERIC  REFERENCES dbo.Teachers,
	classCourseAcr VARCHAR(4),
	classCourseSem VARCHAR(5),
	classCID varchar(2),
	FOREIGN KEY(classCourseAcr,classCourseSem,classCID) REFERENCES
		dbo.Classes(courseACR,semRepresentation,cID),
	Primary key(teacherNum,classCourseAcr,classCourseSem,classCID)

)


CREATE TABLE programme_course_assoc(
	course_fk_acr VARCHAR(4) REFERENCES dbo.Courses,
	programme_fk_acr VARCHAR(4) REFERENCES dbo.Programmes,
	PRIMARY KEY(course_fk_acr,programme_fk_acr)
)


create table Students (
	email VARCHAR (256) references Users,
	ProgrammeID varchar(4) references Programmes ,
	number NUMERIC primary key,
)




