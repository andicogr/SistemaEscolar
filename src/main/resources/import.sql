INSERT INTO usuario(id, fecha_actualizacion, fecha_creacion, uid_actualizacion, uid_creacion, activo, bloqueado, expirar_usuario, password, username)
VALUES (1, now(), now(), 1, 1, true, false, false, '$2a$10$rJ/w5Akm0lzGIKspjt4greb8mmr5NkpWbZHhM2E7EVsjHW8dCuA7u', 'admin');

INSERT INTO rol(id, fecha_actualizacion, fecha_creacion, uid_actualizacion, uid_creacion, activo, nombre)
VALUES (1, now(), now(), 1, 1, true, 'ADMINISTRADOR');

INSERT INTO public.privilegio(id, fecha_actualizacion, fecha_creacion, uid_actualizacion, uid_creacion, activo, nombre, orden, padre)
VALUES (1, now(), now(), 1, 1, true, 'ROLE_ADMIN', 1, true);
