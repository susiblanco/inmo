DROP TABLE IF EXISTS users;
CREATE TABLE users(
  id  int(11) NOT NULL  auto_increment PRIMARY KEY,
  email VARCHAR(60),
  first_name VARCHAR(56),
  last_name VARCHAR(56)
);

DROP TABLE IF EXISTS inmuebles;
CREATE TABLE inmuebles(
  id  int(11) NOT NULL  auto_increment PRIMARY KEY,
  tipo VARCHAR(60),
  localidad VARCHAR(56),
  direccion VARCHAR(56),
  cant_ambientes int(20),
  cant_banios int(10),
  precio float,
  descripcion text ,
  operacion VARCHAR(50),
  propietario int(11),
  fecha date 									
);

DROP TABLE IF EXISTS propietarios;
CREATE TABLE propietarios(
  id  int(11) NOT NULL  auto_increment PRIMARY KEY,
  tipo VARCHAR(60),
  nombre VARCHAR(56),
  apellido VARCHAR(56),
  email VARCHAR(60),
  telefono int(10),
  direccion VARCHAR(50)									 
);
