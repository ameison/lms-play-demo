

# --- !Ups

insert into general(id, abbreviation, description, code, created_date, updated_date)
values (nextval('general_seq'), 'Col', 'Colegio', 'TIPORG', now(), now());
insert into general(id, abbreviation, description, code, created_date, updated_date)
values (nextval('general_seq'), 'Univ', 'Universidad', 'TIPORG', now(), now());
insert into general(id, abbreviation, description, code, created_date, updated_date)
values (nextval('general_seq'), 'Inst', 'Instituto', 'TIPORG', now(), now());
insert into general(id, abbreviation, description, code, created_date, updated_date)
values (nextval('general_seq'), '$20' , '20 dólares' , 'TIPPLAN', now(), now());
insert into general(id, abbreviation, description, code, created_date, updated_date)
values (nextval('general_seq'), '$30' , '30 dólares' , 'TIPPLAN', now(), now());
insert into general(id, abbreviation, description, code, created_date, updated_date)
values (nextval('general_seq'), '$40' , '40 dólares' ,'TIPPLAN', now(), now());
insert into general(id, abbreviation, description, code, created_date, updated_date)
values (nextval('general_seq'), 'PE' , 'Perú' ,'TIPCOUNTRY', now(), now());
insert into general(id, abbreviation, description, code, created_date, updated_date)
values (nextval('general_seq'), 'CO' , 'Colombia' ,'TIPCOUNTRY', now(), now());
insert into general(id, abbreviation, description, code, created_date, updated_date)
values (nextval('general_seq'), 'EC' , 'Ecuador' ,'TIPCOUNTRY', now(), now());
insert into general(id, abbreviation, description, code, created_date, updated_date)
values (nextval('general_seq'), 'BO' , 'Bolivia' ,'TIPCOUNTRY', now(), now());
insert into general(id, abbreviation, description, code, created_date, updated_date)
values (nextval('general_seq'), 'CH' , 'Chile' ,'TIPCOUNTRY', now(), now());
insert into general(id, abbreviation, description, code, created_date, updated_date)
values (nextval('general_seq'), 'AR' , 'Argentina' ,'TIPCOUNTRY', now(), now());

insert into general_detail(id, general_id, abbreviation, description, created_date, updated_date)
values (nextval('general_detail_seq'), 7, 'LI' , 'Lima', now(), now());
insert into general_detail(id, general_id, abbreviation, description, created_date, updated_date)
values (nextval('general_detail_seq'), 7, 'AR' , 'Arequipa', now(), now());
insert into general_detail(id, general_id, abbreviation, description, created_date, updated_date)
values (nextval('general_detail_seq'), 7, 'CH' , 'Chiclayo', now(), now());
insert into general_detail(id, general_id, abbreviation, description, created_date, updated_date)
values (nextval('general_detail_seq'), 7, 'TR' , 'Trujillo', now(), now());

insert into users (id, code , username , first_name , last_name , about , birthday , email , password, url_image, class_code, organization_name, organization_type, country_id, state, telephone, info_completed, rol, created_date, updated_date )
values(nextval('user_seq'), '100000', 'ameison', 'Meison', 'Chirinos', 'android dev', '2012-12-10', 'achirinos@abcdroid.pe',
'$2a$10$C/VoceLmcr/LP8L3WST1Z.L4s2p9MUwEcXGEpHNbwDUCj9IfDmPx.', 'images/default_users.jpg', 'CL782738', 'Ricardo Palma', 1,
'1', 'A', '997773937', true, 'S', now(), now());

insert into users (id, code , username , first_name , last_name , about , birthday , email , password ,url_image , class_code, organization_name, organization_type, country_id, state, telephone, info_completed, rol, created_date, updated_date )
values(nextval('user_seq'), '100566', 'admin', 'Admin', 'Admin', 'android dev', '2012-12-10',  'admin@correo.com',
'$2a$10$C/VoceLmcr/LP8L3WST1Z.L4s2p9MUwEcXGEpHNbwDUCj9IfDmPx.', 'images/default_users.jpg', 'CL782738', 'VES', 1,
'1', 'A', '997773937', true, 'A', now(), now());
