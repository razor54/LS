use[LS_TEST]

select * from ACADEMIC
Select * from Programmes 
select *from Users
select *from Students 
select* from teachers
select * from teacher_class_assoc
select *from Curricular
select * from Courses
select * from classes
select * from student_class_assoc order by number asc

select * from classes where courseACR='LS'
select * from classes

select * from Curricular  inner join Courses		
				on programmeAcr='LEIC'and courseID=acronym

Select * from Courses where acronym='ls'	

select * from classes

select * from teacher_class_assoc

select distinct * from 
		teacher_class_assoc
		where teacherNum=13
		
select * from Programmes
select * from Curricular
select * from courses

select distinct  Programmes.name,length,courseID,Courses.name,acronym,Mandatory,CurricularSem from Curricular 
			inner join Programmes on programmeAcr=PID and PID='LEIC'
			inner join Courses on courseID = acronym
			

select * from student_class_assoc

select Students.email,name,Students.ProgrammeID,Students.number from student_class_assoc inner join Students on cid='d1'and courseACR='ls' and semRepresentation='1617v' and Students.number=student_class_assoc.number
inner join Users on Users.email=Students.email


where cid='d1'and courseACR='ls' and semRepresentation='1617v'
select email from Teachers where number=12
  