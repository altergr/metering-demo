create table meter(
    id bigint auto_increment primary key,
    serial_number varchar(100) not null,
    constraint meter_uq_serial_number unique(serial_number)
);

create table meter_reading(
    id bigint auto_increment primary key,
    meter_id int not null,
    month int not null,
    year int not null,
    value int not null,
    constraint meter_reading_uq_meter_month_year unique(meter_id, month, year),
    constraint meter_reading_ch_month check(month between 1 and 12),
    constraint meter_reading_ch_year check(year between 2000 and 2100),
    constraint meter_reading_ch_value check(value >= 0)
);

create table country(
    id bigint auto_increment primary key,
    code char(2),
    name varchar(300) not null,
    constraint country_uq_code unique(code)
);

create table address(
    id bigint auto_increment primary key,
    street_name varchar(300) not null,
    street_number varchar (50) not null,
    property_unit_number varchar(50) not null,
    zip_code varchar(100) not null,
    city varchar(150) not null,
    country_id bigint not null,
    constraint address_uq_all unique(street_name, street_number, property_unit_number, zip_code, city, country_id)
);

create table client(
    id bigint auto_increment primary key,
    first_name varchar(250) not null,
    last_name varchar(250) not null,
    address_id bigint not null,
    meter_id bigint not null,
    constraint client_fk_address foreign key(address_id) references address(id),
    constraint client_fk_meter foreign key (address_id) references meter(id),
    constraint client_uq_address unique(address_id),
    constraint client_uq_meter unique(meter_id)
);


