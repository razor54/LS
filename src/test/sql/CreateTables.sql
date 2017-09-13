
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
	PID VARCHAR(10) primary key,
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
    Mandatory varchar(5) ,
    name varchar(256) UNIQUE ,
    acronym VARCHAR(4)primary key,
    coordinator numeric REFERENCES dbo.Teachers,
	constraint[boolean]
	check (Mandatory='true' or Mandatory='false')

)

CREATE TABLE Curricular(
    courseID varchar(4) references Courses(acronym),
	programmeAcr VARCHAR(10) REFERENCES dbo.Programmes(PID),
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

create table Students (
	email VARCHAR (256) references Users,
	ProgrammeID varchar(10) references Programmes ,
	number NUMERIC primary key,
)

create table student_class_assoc (
	number numeric references Students,
	courseACR varchar(4) ,
	semRepresentation varchar(5) ,
	cID varchar(2) ,
	FOREIGN KEY(CourseACR,semRepresentation,cid) REFERENCES
		dbo.Classes(courseACR,semRepresentation,cID),
	primary key(number, courseACR, semRepresentation, cID)
	
)


CREATE TABLE programme_course_assoc(
	course_fk_acr VARCHAR(4) REFERENCES dbo.Courses,
	programme_fk_acr VARCHAR(10) REFERENCES dbo.Programmes,
	PRIMARY KEY(course_fk_acr,programme_fk_acr)
)




insert into Programmes values('Lic.Eng.Inf.Comp.','LEIC',6)

insert into Users(email,Name,tipo,num)
	values ('miguelmartins@sa.pt','Miguel Martins','S',1221),
		   ('Filipes@sa.pt','Filipe Sousa','T',12),
		   ('luisp@sa.pt','Luis Pedro','T',14),
		   ('JoaoRocha@sa.pt','Joao Rocha','T',13),
		   ('JJadams@sa.pt','JJ Adams','S',1220),
		   ('Lucasm@sa.pt','Lucas M.','S',1230),
		   ('Manuemanue@sa.pt','Manuel Ourives','S',1232),
		   ('Jose@sa.pt','Jose Sa','S',1231)


insert into Students(email,ProgrammeID,number) 
	values ('miguelmartins@sa.pt','LEIC',1221),
			('jjadams@sa.pt','LEIC',1220),
			('lucasm@sa.pt','LEIC',1230),
			('jose@sa.pt','LEIC',1231),
			('Manuemanue@sa.pt','leic',1232)

insert into Teachers(number,email)
		values( 12,'Filipes@sa.pt'),
			  (14,'luisp@sa.pt'),
			  (13,'JoaoRocha@sa.pt')
			


insert into Courses(Mandatory,name,acronym,coordinator) 
			values ('true','LabSoft','LS',12),
			       ('false','Mod. Padrao Dados','MPD',14),
				   ('true','Sist.Multi.','SM',12)

insert into Academic(SemYear,SemType,representation) 
			 values (1617,'v','1617v')
			        


insert into Classes (courseACR,semRepresentation,cID) 
			 values ('LS','1617v','d1'),
					('MPD','1617v','d2'),
					('LS','1617v','d2'),
					('sm','1617v','d1')




insert into teacher_class_assoc (teacherNum,classCourseAcr,classCourseSem,classCID)
						  values(12,'LS','1617v','d1'),
								(13,'LS','1617v','d2'),
								(13,'MPD','1617v','d2')



insert into programme_course_assoc(course_fk_acr, programme_fk_acr)
							values('LS','LEIC'),
								  ('MPD','LEIC')
update Courses 
		set Mandatory='true'
		where acronym='ls'

update Courses
		set Mandatory='false'
		where acronym='mpd'

insert into Curricular(courseID,programmeAcr,CurricularSem)
				values('LS','LEIC',4),
					  ('MPD','LEIC',4),
					  ('MPD','LEIC',6)



INSERT INTO student_class_assoc(number, courseACR, semRepresentation , cID )
							values	
									(1232,'LS','1617v','d1'),
									(1231,'LS','1617v','d1')




