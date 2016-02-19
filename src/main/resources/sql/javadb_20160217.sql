DROP VIEW MG.RECIBO;
DROP VIEW MG.TRANSFERENCIA;
DROP VIEW MG.ENTIDADES_CON_PAGOS_PENDIENTES;
DROP TABLE MG.TBL_RECIBOS;
DROP TABLE MG.TBL_TRANSFERENCIAS;

CREATE TABLE MG.TBL_RECIBOS (
    ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
    FECHAHORA TIMESTAMP NOT NULL,
    CONCEPTO VARCHAR(50),
    MONTO INTEGER NOT NULL,
    PORCENTAJE_APORTE INTEGER NOT NULL,
    ID_ENTIDAD INTEGER NOT NULL,
    ID_EVENTO_TIPO INTEGER NOT NULL,
    ID_USER INTEGER NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE MG.TBL_TRANSFERENCIAS (
	ID INTEGER GENERATED ALWAYS AS IDENTITY NOT NULL,
	FECHAHORA TIMESTAMP NOT NULL,
	CONCEPTO VARCHAR(50),
	MONTO INTEGER NOT NULL,
	PORCENTAJE_APORTE INTEGER NOT NULL,
	ID_ENTIDAD INTEGER NOT NULL,
        ID_EVENTO_TIPO INTEGER NOT NULL,
        COBRADO BOOLEAN NOT NULL DEFAULT FALSE,
	ID_USER INTEGER NOT NULL,
	PRIMARY KEY (ID)
);

ALTER TABLE MG.TBL_RECIBOS
	ADD FOREIGN KEY (ID_ENTIDAD)
	REFERENCES MG.TBL_ENTIDADES (ID);

ALTER TABLE MG.TBL_RECIBOS
	ADD FOREIGN KEY (ID_EVENTO_TIPO)
	REFERENCES MG.TBL_EVENTO_TIPOS (ID);

ALTER TABLE MG.TBL_RECIBOS
	ADD FOREIGN KEY (ID_USER)
	REFERENCES MG.TBL_USERS (ID);


ALTER TABLE MG.TBL_TRANSFERENCIAS
	ADD FOREIGN KEY (ID_ENTIDAD)
	REFERENCES MG.TBL_ENTIDADES (ID);

ALTER TABLE MG.TBL_TRANSFERENCIAS
	ADD FOREIGN KEY (ID_EVENTO_TIPO)
	REFERENCES MG.TBL_EVENTO_TIPOS (ID);

ALTER TABLE MG.TBL_TRANSFERENCIAS
	ADD FOREIGN KEY (ID_USER)
	REFERENCES MG.TBL_USERS (ID);


CREATE VIEW MG.RECIBO AS SELECT p.id, p.fechahora, d.nombres || d.apellidos AS nombre, p.concepto, p.monto FROM TBL_RECIBOS p, TBL_ENTIDADES d WHERE ((p.ID_ENTIDAD = d.id));

CREATE VIEW MG.TRANSFERENCIA AS
    SELECT t.id,
        t.fechahora,
        d.ctacte AS cta_debito,
        i.ctacte AS cta_credito,
        d.nombres || ' ' || d.apellidos  AS d_nombre,
        i.nombre  AS c_nombre,
        d.domicilio AS d_domicilio,
        i.domicilio AS c_domicilio,
        d.box AS d_box,
        i.box AS c_box,
        t.monto,
        t.concepto
    FROM TBL_TRANSFERENCIAS t, TBL_ENTIDADES d, TBL_IGLESIA i
    WHERE t.ID_ENTIDAD = d.ID;

CREATE VIEW MG.ENTIDADES_CON_PAGOS_PENDIENTES AS
SELECT remates.id, remates.nombre, remates.ctacte, remates.domicilio, remates.box FROM
        (SELECT m.id, m.nombres || ' ' || m.apellidos AS nombre, m.ctacte, m.domicilio, m.box, SUM(rd.monto) AS monto FROM TBL_ENTIDADES m
            LEFT JOIN TBL_EVENTO_DETALLE rd ON m.id = rd.ID_ENTIDAD
            group by m.id, m.nombres, m.apellidos, m.RAZON_SOCIAL, m.RUC_SIN_DV, m.ctacte, m.domicilio, m.box, m.aporte_mensual, m.id_user, m.id_forma_de_pago_preferida,
                    m.ID_ENTIDAD_PAGANTE_APORTES,
                    m.IS_MIEMBRO_ACTIVO,
                    m.ID_FORMA_DE_PAGO_PREFERIDA,
                    m.APORTE_MENSUAL,
                    m.FECHA_NACIMIENTO,
                    m.FECHA_BAUTISMO,
                    m.FECHA_ENTRADA_CONGREGACION,
                    m.FECHA_SALIDA_CONGREGACION,
                    m.FECHA_DEFUNCION,
                    m.ID_AREA_SERVICIO_EN_IGLESIA,
                    m.ID_MIEMBROS_CATEGORIA_DE_PAGO,
                    m.ID_MIEMBROS_ALERGIA) remates,
        (SELECT m.*, COALESCE(SUM(p.monto),0) AS monto FROM TBL_ENTIDADES m
            LEFT JOIN TBL_TRANSFERENCIAS p ON m.id = p.ID_ENTIDAD
            group by m.id, m.nombres, m.apellidos, m.RAZON_SOCIAL, m.RUC_SIN_DV, m.ctacte, m.domicilio, m.box, m.aporte_mensual, m.id_user, m.id_forma_de_pago_preferida,
                    m.IS_MIEMBRO_ACTIVO,
                    m.ID_ENTIDAD_PAGANTE_APORTES,
                    m.ID_FORMA_DE_PAGO_PREFERIDA,
                    m.APORTE_MENSUAL,
                    m.FECHA_NACIMIENTO,
                    m.FECHA_BAUTISMO,
                    m.FECHA_ENTRADA_CONGREGACION,
                    m.FECHA_SALIDA_CONGREGACION,
                    m.FECHA_DEFUNCION,
                    m.ID_AREA_SERVICIO_EN_IGLESIA,
                    m.ID_MIEMBROS_CATEGORIA_DE_PAGO,
                    m.ID_MIEMBROS_ALERGIA) transferencias,
        (SELECT m.*, COALESCE(SUM(p.monto),0) AS monto FROM TBL_ENTIDADES m
            LEFT JOIN TBL_RECIBOS p ON m.id = p.ID_ENTIDAD
            group by m.id, m.nombres, m.apellidos, m.RAZON_SOCIAL, m.RUC_SIN_DV, m.ctacte, m.domicilio, m.box, m.aporte_mensual, m.id_user, m.id_forma_de_pago_preferida,
                    m.IS_MIEMBRO_ACTIVO,
                    m.ID_FORMA_DE_PAGO_PREFERIDA,
                    m.APORTE_MENSUAL,
                    m.ID_ENTIDAD_PAGANTE_APORTES,
                    m.FECHA_NACIMIENTO,
                    m.FECHA_BAUTISMO,
                    m.FECHA_ENTRADA_CONGREGACION,
                    m.FECHA_SALIDA_CONGREGACION,
                    m.FECHA_DEFUNCION,
                    m.ID_AREA_SERVICIO_EN_IGLESIA,
                    m.ID_MIEMBROS_CATEGORIA_DE_PAGO,
                    m.ID_MIEMBROS_ALERGIA) recibos
    WHERE remates.id = transferencias.id AND remates.id = recibos.id AND (remates.monto - transferencias.monto - recibos.monto) > 0
    ORDER BY remates.nombre;