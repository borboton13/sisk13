/** 24.02.2019 **/
-- inta
-- intb
ALTER TABLE tipocuenta ADD COLUMN intb DECIMAL(4,2) AFTER interes;
ALTER TABLE tipocuenta ADD COLUMN inta DECIMAL(4,2) AFTER interes;

ALTER TABLE tipocuenta DROP COLUMN interes;

/* Para fondo CISC */
UPDATE tipocuenta t SET t.`inta` = 2 WHERE t.`idtipocuenta` IN (1, 2);
UPDATE tipocuenta t SET t.`inta` = 5 WHERE t.`idtipocuenta` IN (3);
UPDATE tipocuenta t SET t.`intb` = 1.5 WHERE t.`idtipocuenta` IN (1, 2);
UPDATE tipocuenta t SET t.`intb` = 0 WHERE t.`idtipocuenta` IN (3);

/* Para fondo Lechero */
-- todo

/* Para fondo CISC */
ALTER TABLE tipocuenta ADD  COLUMN tipo VARCHAR(10) AFTER activo;
UPDATE tipocuenta t SET t.`tipo` = 'AHO';
UPDATE tipocuenta t SET t.`tipo` = 'DPF' WHERE t.`idtipocuenta` = 3;

/** Para ambos **/
DELETE FROM arcgms  WHERE cuenta IN (4110000000, 4110100000, 4110110000);
-- upload csv


SELECT *
FROM sf_tmpdet d
WHERE d.`cuenta` IN (4110100000, 4110110000)
;