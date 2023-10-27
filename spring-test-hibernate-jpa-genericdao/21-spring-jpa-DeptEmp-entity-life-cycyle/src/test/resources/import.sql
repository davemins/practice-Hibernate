-- Hibernate: create sequence department_seq start with 1 increment by 50
-- Hibernate: create sequence employee_seq start with 1 increment by 50

-- ID: 1 ~ 4
insert into department(id, name) values (nextval('department_seq'),      'Development');
insert into department(id, name) values (nextval('department_seq')-50+1, 'Management');
insert into department(id, name) values (currval('department_seq')-50+2, 'Marketing');
insert into department(id, name) values (currval('department_seq')-50+3, 'Personnel');

-- ID: 1 ~ 11
insert into employee(id, name, department_id) values (nextval('employee_seq'),       'Allison',     1);
insert into employee(id, name, department_id) values (nextval('employee_seq')-50+1,  'Lois',        1);
insert into employee(id, name, department_id) values (currval('employee_seq')-50+2,  'Ramon',       1);
insert into employee(id, name, department_id) values (currval('employee_seq')-50+3,  'Derek',       1);

insert into employee(id, name, department_id) values (currval('employee_seq')-50+4,  'Maria',       2);
insert into employee(id, name, department_id) values (currval('employee_seq')-50+5,  'Rosemary',    2);
insert into employee(id, name, department_id) values (currval('employee_seq')-50+6,  'Emma',        2);

insert into employee(id, name, department_id) values (currval('employee_seq')-50+7,  'Gabriel',     3);
insert into employee(id, name, department_id) values (currval('employee_seq')-50+8,  'Frances',     3);

insert into employee(id, name, department_id) values (currval('employee_seq')-50+9,  'Elaine',      4);

insert into employee(id, name, department_id) values (currval('employee_seq')-50+10, 'Bonnie',   NULL);

