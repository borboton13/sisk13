SELECT *
FROM arcgms a
WHERE (a.tipo IS NULL OR a.tipo = '')
;
/** 15.03.2019 **/
SELECT *
FROM sf_tmpenc e WHERE e.`id_tmpenc` = 100440;
-- DELETE FROM sf_tmpdet WHERE id_tmpenc = 100440;
-- DELETE FROM sf_tmpenc WHERE id_tmpenc = 100440;

SELECT * FROM sf_tmpenc e WHERE e.`id_tmpenc` = 0;
SELECT * FROM sf_tmpdet e WHERE e.`id_tmpenc` = 0;

-- DELETE FROM sf_tmpdet WHERE id_tmpenc = 0;
-- DELETE FROM sf_tmpenc WHERE id_tmpenc = 0;

-- DELETE FROM sf_tmpdet WHERE id_tmpenc = 101652;
-- DELETE FROM sf_tmpenc WHERE id_tmpenc = 101652;

-- Actualizar TIPO en arcgms
-- Revisar antes Balance y etado resultados
UPDATE arcgms a SET a.`tipo` = 'A'
WHERE a.`cuenta` IN (
1530000000	,
1530400000	,
1530410000	,
1530420000	,
1590000000	,
1590300000	,
1590310000	,
1590310100	,
1590310200	,
1590320000	,
1590320100	,
1590320200	,
1671010200	,
1671010300	,
1671020300	,
1671020400	,
1810500000	,
1810510100	,
1810510200	
);

UPDATE arcgms a SET a.`tipo` = 'P'
WHERE a.`cuenta` IN (
2420511000	,
2420511100	,
2420511200	,
2420511300	,
2420610601	,
2420610800	,
2420610900	,
2431010200	,
2431010700	
);


UPDATE arcgms a SET a.`tipo` = 'C'
WHERE a.`cuenta` IN (
3230410000	,
3230410100	,
3230410200	,
3230410300	

);


UPDATE arcgms a SET a.`tipo` = 'OD'
WHERE a.`cuenta` IN (
8000000000	,
8400000000	,
8410000000	,
8410100000	,
8420000000	,
8420100000	,
8420200000	,
8450000000	,
8450100000	,
8500000000	,
8550000000	,
8550100000	,
8550110000	,
8550120000	,
8550130000	,
8550200000	,
8550210000	,
8550220000	,
8550230000	,
8550300000	,
8560000000	,
8560100000	,
8560120000	,
8560130000	,
8560200000	,
8560220000	,
8560230000	,
8560300000	,
8560320000	,
8560330000	,
8560400000	,
8560410000	,
8560420000	,
8560430000	
);


UPDATE arcgms a SET a.`tipo` = 'OA'
WHERE a.`cuenta` IN (
9000000000	,
9400000000	,
9410000000	,
9410100000	,
9420000000	,
9420100000	,
9450000000	,
9450100000	,
9500000000	,
9550000000	,
9550100000	,
9550110000	,
9550120000	,
9550130000	,
9550210000	,
9550220000	,
9550230000	,
9550300000	,
9560000000	,
9560100000	,
9560120000	,
9560130000	
);

UPDATE arcgms a SET a.`tipo` = 'Z'
WHERE a.`cuenta` IN (9999999999);

UPDATE arcgms SET cta_raiz = 	'1500000000'	 WHERE cuenta = 	'1530000000'	;
UPDATE arcgms SET cta_raiz = 	'1500000000'	 WHERE cuenta = 	'1530400000'	;
UPDATE arcgms SET cta_raiz = 	'1500000000'	 WHERE cuenta = 	'1530410000'	;
UPDATE arcgms SET cta_raiz = 	'1500000000'	 WHERE cuenta = 	'1530420000'	;
UPDATE arcgms SET cta_raiz = 	'1500000000'	 WHERE cuenta = 	'1590000000'	;
UPDATE arcgms SET cta_raiz = 	'1500000000'	 WHERE cuenta = 	'1590300000'	;
UPDATE arcgms SET cta_raiz = 	'1500000000'	 WHERE cuenta = 	'1590310000'	;
UPDATE arcgms SET cta_raiz = 	'1500000000'	 WHERE cuenta = 	'1590310100'	;
UPDATE arcgms SET cta_raiz = 	'1500000000'	 WHERE cuenta = 	'1590310200'	;
UPDATE arcgms SET cta_raiz = 	'1500000000'	 WHERE cuenta = 	'1590320000'	;
UPDATE arcgms SET cta_raiz = 	'1500000000'	 WHERE cuenta = 	'1590320100'	;
UPDATE arcgms SET cta_raiz = 	'1500000000'	 WHERE cuenta = 	'1590320200'	;
UPDATE arcgms SET cta_raiz = 	'1600000000'	 WHERE cuenta = 	'1671010200'	;
UPDATE arcgms SET cta_raiz = 	'1600000000'	 WHERE cuenta = 	'1671010300'	;
UPDATE arcgms SET cta_raiz = 	'1600000000'	 WHERE cuenta = 	'1671020300'	;
UPDATE arcgms SET cta_raiz = 	'1600000000'	 WHERE cuenta = 	'1671020400'	;
UPDATE arcgms SET cta_raiz = 	'1800000000'	 WHERE cuenta = 	'1810500000'	;
UPDATE arcgms SET cta_raiz = 	'1800000000'	 WHERE cuenta = 	'1810510100'	;
UPDATE arcgms SET cta_raiz = 	'1800000000'	 WHERE cuenta = 	'1810510200'	;
UPDATE arcgms SET cta_raiz = 	'2400000000'	 WHERE cuenta = 	'2420511000'	;
UPDATE arcgms SET cta_raiz = 	'2400000000'	 WHERE cuenta = 	'2420511100'	;
UPDATE arcgms SET cta_raiz = 	'2400000000'	 WHERE cuenta = 	'2420511200'	;
UPDATE arcgms SET cta_raiz = 	'2400000000'	 WHERE cuenta = 	'2420511300'	;
UPDATE arcgms SET cta_raiz = 	'2400000000'	 WHERE cuenta = 	'2420610601'	;
UPDATE arcgms SET cta_raiz = 	'2400000000'	 WHERE cuenta = 	'2420610800'	;
UPDATE arcgms SET cta_raiz = 	'2400000000'	 WHERE cuenta = 	'2420610900'	;
UPDATE arcgms SET cta_raiz = 	'2400000000'	 WHERE cuenta = 	'2431010200'	;
UPDATE arcgms SET cta_raiz = 	'2400000000'	 WHERE cuenta = 	'2431010700'	;
UPDATE arcgms SET cta_raiz = 	'3200000000'	 WHERE cuenta = 	'3230410000'	;
UPDATE arcgms SET cta_raiz = 	'3200000000'	 WHERE cuenta = 	'3230410100'	;
UPDATE arcgms SET cta_raiz = 	'3200000000'	 WHERE cuenta = 	'3230410200'	;
UPDATE arcgms SET cta_raiz = 	'3200000000'	 WHERE cuenta = 	'3230410300'	;


Cuentas q Admiten Analiticos
1220110605
1420310100
1420310200
1420310700
1420310800
1420320100
1420320700
1420410100
1420910100
1420910200
1421010100 Clientes
1429910100
1429920100
1429920200
1490110000
2420910000
2420910300
2420920000
2429910000
2429910400
2429910600
2429910700
2429920000
8550300000
9550110000



