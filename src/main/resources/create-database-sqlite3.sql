--  - - - - thoughtfully generated by synapticloop h2zero - - - -
--     with the use of synapticloop templar templating language
--              (sql-create-database-sqlite3.templar)

drop table if exists collection;
create table collection (
	id_collection INTEGER not null PRIMARY KEY AUTOINCREMENT,
	nm_collection varchar(256) not null
);

create unique index collection_nm_collection_idx_unq on collection(nm_collection);


drop table if exists field_type;
create table field_type (
	id_field_type INTEGER not null PRIMARY KEY AUTOINCREMENT,
	nm_field_type varchar(256) not null,
	fl_field_should_quote boolean not null default '0'
);



-- The field_type table is defined as being constant
-- insert the values

insert into field_type values(1, 'string', 1);
insert into field_type values(2, 'text', 1);
insert into field_type values(3, 'integer', 0);
insert into field_type values(4, 'float', 0);
insert into field_type values(5, 'boolean', 0);
insert into field_type values(6, 'media', 1);


drop table if exists field;
create table field (
	id_field INTEGER not null PRIMARY KEY AUTOINCREMENT,
	id_field_type bigint not null,
	id_collection bigint not null,
	nm_field varchar(256) not null,
	foreign key (id_field_type) references field_type (id_field_type),
	foreign key (id_collection) references collection (id_collection)
);



drop table if exists data_collection;
create table data_collection (
	id_data_collection INTEGER not null PRIMARY KEY AUTOINCREMENT,
	id_collection bigint not null,
	nm_collection varchar(256) not null,
	fl_is_published boolean not null default '0',
	foreign key (id_collection) references collection (id_collection)
);



drop table if exists data_field;
create table data_field (
	id_data_field INTEGER not null PRIMARY KEY AUTOINCREMENT,
	id_field bigint not null,
	foreign key (id_field) references field (id_field)
);



