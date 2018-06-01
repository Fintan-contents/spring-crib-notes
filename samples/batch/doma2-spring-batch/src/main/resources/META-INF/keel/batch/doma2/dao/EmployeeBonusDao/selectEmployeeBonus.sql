select
  employee.id,
  employee.name,
  employee.salary,
  grade.bonus_magnification,
  grade.fixed_bonus
from employee
join grade
  on employee.grade_id = grade.id
