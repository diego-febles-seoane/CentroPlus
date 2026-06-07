PRAGMA foreign_keys = ON;

DROP TABLE IF EXISTS incidencias;
DROP TABLE IF EXISTS reservas;
DROP TABLE IF EXISTS actividades;
DROP TABLE IF EXISTS usuarios;

CREATE TABLE usuarios (
    id INTEGER PRIMARY KEY,
    nombre TEXT NOT NULL,
    dni TEXT NOT NULL UNIQUE,
    email TEXT NOT NULL,
    telefono TEXT,
    tipo_usuario TEXT NOT NULL
);

CREATE TABLE actividades (
    id INTEGER PRIMARY KEY,
    nombre TEXT NOT NULL,
    tipo_actividad TEXT NOT NULL,
    duracion INTEGER NOT NULL,
    precio REAL NOT NULL,
    plazas_maximas INTEGER NOT NULL,
    plazas_ocupadas INTEGER NOT NULL
);

CREATE TABLE reservas (
    id INTEGER PRIMARY KEY,
    id_usuario INTEGER NOT NULL,
    id_actividad INTEGER NOT NULL,
    fecha TEXT NOT NULL,
    estado TEXT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY (id_actividad) REFERENCES actividades(id)
);

CREATE TABLE incidencias (
    id INTEGER PRIMARY KEY,
    id_usuario INTEGER NOT NULL,
    asunto TEXT NOT NULL,
    descripcion TEXT NOT NULL,
    fecha TEXT NOT NULL,
    estado TEXT NOT NULL,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);