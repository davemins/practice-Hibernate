drop table if exists Artist CASCADE 
drop table if exists Track CASCADE 
drop table if exists TRACK_ARTISTS CASCADE 
drop table if exists TRACK_COMMENTS CASCADE 
drop sequence if exists hibernate_sequence
create sequence hibernate_sequence start with 1 increment by 1
create table Artist (ARTIST_ID integer not null, name varchar(255), primary key (ARTIST_ID))
create table Track (TRACK_ID integer not null, added date, filePath varchar(255), playTime time, title varchar(255), volume smallint not null, primary key (TRACK_ID))
create table TRACK_ARTISTS (TRACK_ID integer not null, ARTIST_ID integer not null, primary key (TRACK_ID, ARTIST_ID))
create table TRACK_COMMENTS (TRACK_ID integer not null, COMMENT varchar(255))
alter table Artist add constraint UK_qk616bidu9lfr95vukvwwfawq unique (name)
alter table TRACK_ARTISTS add constraint FKt9is5vo46ulpkwnk48yg7n42i foreign key (ARTIST_ID) references Artist
alter table TRACK_ARTISTS add constraint FK9r7at1j9ttvvhiy45b1uesw8d foreign key (TRACK_ID) references Track
alter table TRACK_COMMENTS add constraint FKbl1s5slsa8eh7db3nwny6d1j6 foreign key (TRACK_ID) references Track
