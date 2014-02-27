SET ARRAYSIZE 100
SET FLUSH OFF
SET LINESIZE 100
SET PAGESIZE 0
SET SERVEROUTPUT OFF
SET SQLPROMPT OF
SET FEEDBACK OFF
SET TIMING ON
SET TERMOUT OFF
SET TRIMSPOOL ON
SPOOL C:\sqlplustest\employees.csv
SELECT
  e.employee_id
  ||','|| e.first_name
  ||','|| e.last_name
  ||','|| e.email
  ||','|| e.phone_number
  ||','|| e.hire_date
  ||','|| e.job_id
  ||','|| e.salary
  ||','|| e.commission_pct
  ||','|| e.manager_id
  ||','|| e.department_id
FROM  employees e;
SPOOL OFF
QUIT
