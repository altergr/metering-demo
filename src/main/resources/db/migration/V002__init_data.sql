insert into country(id, code, name)
values (1, 'HR', 'Croatia');
insert into country(id, code, name)
values (2, 'NL', 'Netherlands');


insert into address(id, street_name, street_number, property_unit_number, zip_code, city, country_id)
values (1, 'Nova cesta', '5A', 'flat-323', '10000', 'Zagreb', 1);
insert into address(id, street_name, street_number, property_unit_number, zip_code, city, country_id)
values (2, 'Vukovarska', '128', 'flat-1', '21000', 'Zagreb', 1);
insert into address(id, street_name, street_number, property_unit_number, zip_code, city, country_id)
values (3, 'Damrak', '65', 'flat-001', '1012', 'Amsterdam', 1);


insert into meter(id, serial_number)
values (1, 'ELECTRO-1');
insert into meter(id, serial_number)
values (2, 'ELECTRO-2');
insert into meter(id, serial_number)
values (3, 'ELECTRO-3');

insert into client(id, first_name, last_name, address_id, meter_id)
values (1, 'Anita', 'Macelj', 1, 1);
insert into client(id, first_name, last_name, address_id, meter_id)
values (2, 'Marko', 'Horak', 2, 2);
insert into client(id, first_name, last_name, address_id, meter_id)
values (3, 'Dirk', 'Bakker', 3, 3);

insert into meter_reading(meter_id, month, year, value)
values (1, 1, 2019, 10);
insert into meter_reading(meter_id, month, year, value)
values (1, 2, 2019, 15);
insert into meter_reading(meter_id, month, year, value)
values (1, 3, 2019, 40);
insert into meter_reading(meter_id, month, year, value)
values (1, 4, 2019, 76);
insert into meter_reading(meter_id, month, year, value)
values (1, 5, 2019, 89);
insert into meter_reading(meter_id, month, year, value)
values (1, 6, 2019, 111);
insert into meter_reading(meter_id, month, year, value)
values (1, 7, 2019, 120);
insert into meter_reading(meter_id, month, year, value)
values (1, 8, 2019, 130);
insert into meter_reading(meter_id, month, year, value)
values (1, 9, 2019, 140);
insert into meter_reading(meter_id, month, year, value)
values (1, 10, 2019, 150);
insert into meter_reading(meter_id, month, year, value)
values (1, 11, 2019, 167);
insert into meter_reading(meter_id, month, year, value)
values (1, 12, 2019, 189);


insert into meter_reading(meter_id, month, year, value)
values (2, 1, 2019, 20);
insert into meter_reading(meter_id, month, year, value)
values (2, 2, 2019, 25);
insert into meter_reading(meter_id, month, year, value)
values (2, 3, 2019, 50);
insert into meter_reading(meter_id, month, year, value)
values (2, 4, 2019, 86);
insert into meter_reading(meter_id, month, year, value)
values (2, 5, 2019, 99);
insert into meter_reading(meter_id, month, year, value)
values (2, 6, 2019, 121);
insert into meter_reading(meter_id, month, year, value)
values (2, 7, 2019, 130);
insert into meter_reading(meter_id, month, year, value)
values (2, 8, 2019, 140);
insert into meter_reading(meter_id, month, year, value)
values (2, 9, 2019, 150);
insert into meter_reading(meter_id, month, year, value)
values (2, 10, 2019, 160);
insert into meter_reading(meter_id, month, year, value)
values (2, 11, 2019, 177);
insert into meter_reading(meter_id, month, year, value)
values (2, 12, 2019, 199);

insert into meter_reading(meter_id, month, year, value)
values (3, 1, 2019, 25);
insert into meter_reading(meter_id, month, year, value)
values (3, 2, 2019, 30);
insert into meter_reading(meter_id, month, year, value)
values (3, 3, 2019, 55);
insert into meter_reading(meter_id, month, year, value)
values (3, 4, 2019, 91);
insert into meter_reading(meter_id, month, year, value)
values (3, 5, 2019, 104);
insert into meter_reading(meter_id, month, year, value)
values (3, 6, 2019, 126);
insert into meter_reading(meter_id, month, year, value)
values (3, 7, 2019, 140);
insert into meter_reading(meter_id, month, year, value)
values (3, 8, 2019, 150);
insert into meter_reading(meter_id, month, year, value)
values (3, 9, 2019, 165);
insert into meter_reading(meter_id, month, year, value)
values (3, 10, 2019, 187);
insert into meter_reading(meter_id, month, year, value)
values (3, 11, 2019, 230);
insert into meter_reading(meter_id, month, year, value)
values (3, 12, 2019, 250);







