-- Hibernate: create sequence department_seq start with 1 increment by 50
-- Hibernate: create sequence employee_seq start with 1 increment by 50

-- ID: 1 ~ 4
insert into department(id, name) values (nextval('department_seq'),      'Development');
insert into department(id, name) values (nextval('department_seq')-50+1, 'Management');
insert into department(id, name) values (currval('department_seq')-50+2, 'Marketing');
insert into department(id, name) values (currval('department_seq')-50+3, 'Personnel');

-- ID: 1 ~ 2
insert into address(id, street, city) values (nextval('address_seq'),      'Hangang-daero', 'Seoul');
insert into address(id, street, city) values (nextval('address_seq')-50+1, 'Dongseo-daero', 'Daejon');

-- ID: 1 ~ 11
insert into employee(id, name, department_id, address_id) values (nextval('employee_seq'),       'Allison',     1, 2);
insert into employee(id, name, department_id, address_id) values (nextval('employee_seq')-50+1,  'Lois',        1, 1);
insert into employee(id, name, department_id, address_id) values (currval('employee_seq')-50+2,  'Ramon',       1, NULL);
insert into employee(id, name, department_id, address_id) values (currval('employee_seq')-50+3,  'Derek',       1, NULL);

insert into employee(id, name, department_id, address_id) values (currval('employee_seq')-50+4,  'Maria',       2, NULL);
insert into employee(id, name, department_id, address_id) values (currval('employee_seq')-50+5,  'Rosemary',    2, NULL);
insert into employee(id, name, department_id, address_id) values (currval('employee_seq')-50+6,  'Emma',        2, NULL);

insert into employee(id, name, department_id, address_id) values (currval('employee_seq')-50+7,  'Gabriel',     3, NULL);
insert into employee(id, name, department_id, address_id) values (currval('employee_seq')-50+8,  'Frances',     3, NULL);

insert into employee(id, name, department_id, address_id) values (currval('employee_seq')-50+9,  'Elaine',      4, NULL);

insert into employee(id, name, department_id, address_id) values (currval('employee_seq')-50+10, 'Bonnie',   NULL, NULL);

-- ID: 1 ~ 4
insert into phone (id, model, employee_id) values
    (nextval('phone_seq'),      'Galaxy', 1),
    (nextval('phone_seq')-50+1, 'iPhone', 1),
    (currval('phone_seq')-50+2, 'Galaxy', 2),
    (currval('phone_seq')-50+3, 'iPhone', 3);

-- ID: 1 ~ 2
insert into project (id, title) values
    (nextval('project_seq'),      'Java Projects'),
    (nextval('project_seq')-50+1, 'Android App Projects');

insert into employee_project (employee_id, project_id) values
    (1, 1),
    (1, 2),
    (2, 1),
    (2, 2);

