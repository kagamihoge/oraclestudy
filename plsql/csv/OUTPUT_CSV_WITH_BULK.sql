create or replace PROCEDURE OUTPUT_CSV_WITH_BULK AS 
  BULK_SIZE CONSTANT PLS_INTEGER := 100;
  outputCSVFileHandle UTL_FILE.FILE_TYPE;
  CURSOR employeesCursor IS SELECT * FROM employees;
  TYPE typeEmployees IS TABLE OF employeesCursor%ROWTYPE INDEX BY BINARY_INTEGER;
  
  employeesRows typeEmployees;
BEGIN
  outputCSVFileHandle := UTL_FILE.FOPEN('CSV_OUTPUT_DIR','employees.csv','w',100);

  OPEN employeesCursor;
  LOOP
    FETCH employeesCursor BULK COLLECT INTO employeesRows LIMIT BULK_SIZE;
    EXIT WHEN employeesRows.COUNT = 0;
    FOR i IN 1 .. employeesRows.COUNT
    LOOP
    UTL_FILE.PUT_LINE(outputCSVFileHandle,
      employeesRows(i).employee_id
      ||','|| employeesRows(i).first_name
      ||','|| employeesRows(i).last_name
      ||','|| employeesRows(i).email
      ||','|| employeesRows(i).phone_number
      ||','|| employeesRows(i).hire_date
      ||','|| employeesRows(i).job_id
      ||','|| employeesRows(i).salary
      ||','|| employeesRows(i).commission_pct
      ||','|| employeesRows(i).manager_id
      ||','|| employeesRows(i).department_id);
    END LOOP;
  END LOOP;
  CLOSE employeesCursor;
  
END OUTPUT_CSV_WITH_BULK;