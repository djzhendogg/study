merge into Marks
using NewMarks
on Marks.StudentId = NewMarks.StudentId
and Marks.CourseId = NewMarks.CourseId
when matched
	and Marks.Mark < NewMarks.Mark 
	then update set Mark = NewMarks.Mark;