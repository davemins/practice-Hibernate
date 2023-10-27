drop table if exists Track cascade ;
drop sequence if exists Track_SEQ;
create sequence Track_SEQ start with 1 increment by 50;
create table Track (TRACK_ID integer not null, added date, playTime time(6), volume smallint not null, filePath varchar(255), title varchar(255), primary key (TRACK_ID));
