create or replace PROCEDURE OUTPUT_CSV AS 
  outputCSVFileHandle UTL_FILE.FILE_TYPE;
  CURSOR employeesCursor IS SELECT * FROM employees;
  employeesRow employeesCursor%ROWTYPE;
BEGIN
  outputCSVFileHandle := UTL_FILE.FOPEN('CSV_OUTPUT_DIR','employees.csv','w',100);

  OPEN employeesCursor;
  LOOP
    FETCH employeesCursor INTO employeesRow;
    EXIT WHEN employeesCursor%NOTFOUND;
    UTL_FILE.PUT_LINE(outputCSVFileHandle,
      employeesRow.employee_id
      ||','|| employeesRow.first_name
      ||','|| employeesRow.last_name
      ||','|| employeesRow.email
      ||','|| employeesRow.phone_number
      ||','|| employeesRow.hire_date
      ||','|| employeesRow.job_id
      ||','|| employeesRow.salary
      ||','|| employeesRow.commission_pct
      ||','|| employeesRow.manager_id
      ||','|| employeesRow.department_id);
  END LOOP;
  CLOSE employeesCursor;
  
END OUTPUT_CSV;