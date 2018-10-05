create table account (
  id SERIAL,
  national_identity_number varchar(20) not null,
  device_id varchar(256) not null,
  created timestamp not null default current_timestamp,
  primary key (id)
);
