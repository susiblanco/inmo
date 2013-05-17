DROP TABLE IF EXISTS users;
CREATE TABLE users(
  id  int(11) NOT NULL  auto_increment PRIMARY KEY,
  nombre_usuario VARCHAR(60),
  contrasenia VARCHAR(60)
  
);

DROP TABLE IF EXISTS buildings;
CREATE TABLE buildings(
  id  INT(11) NOT NULL  auto_increment PRIMARY KEY,
  tipo ENUM('campo','quinta','casa','departamento','oficina','cochera'),
  direccion VARCHAR(60),
  cant_habitaciones INT(11),
  cant_banios INT(11),
  precio FLOAT,
  descripcion text ,
  operacion ENUM('venta','alquiler'),
  fecha_inicio DATE,
  fecha_fin DATE,
  owner_id INT(11) NOT NULL references owners(id),
  location_id INT(11) NOT NULL references locations(id),
  real_estate_id INT(11) NOT NULL references real_estates(id)									
);

DROP TABLE IF EXISTS owners;
CREATE TABLE owners(
  id  INT(11) NOT NULL  auto_increment PRIMARY KEY,
  nombre VARCHAR(60),
  email VARCHAR(60),
  telefono INT(16),
  direccion VARCHAR(60),
  real_estate_id INT(11) NOT NULL references real_estates(id),
  location_id INT(11) NOT NULL references locations(id)									 
);

DROP TABLE IF EXISTS real_estates;
CREATE TABLE real_estates(
  id INT(11) NOT NULL auto_increment PRIMARY KEY,
  nombre VARCHAR(60),
  telefono INT(10),
  email VARCHAR(60),
  sitio_web VARCHAR(60),
  direccion VARCHAR(60),
  location_id INT(11) NOT NULL references locations(id)
);

DROP TABLE IF EXISTS admins;
CREATE TABLE admins(
  id INT(11) NOT NULL auto_increment PRIMARY KEY
);

DROP TABLE IF EXISTS locations;
CREATE TABLE locations(
   id INT(11) NOT NULL auto_increment PRIMARY KEY,
   nombre VARCHAR(60),
   codigo_postal INT(11)
);
