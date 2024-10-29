merge into Marks
using NewMarks
on Marks.student_id = NewMarks.student_id
and Marks.course_id = NewMarks.course_id
when matched
and Marks.Mark < NewMarks.Mark
	then update set Mark = NewMarks.Mark
when not matched
	then insert (student_id, course_id, Mark)
	values (NewMarks.student_id, NewMarks.course_id, NewMarks.Mark);