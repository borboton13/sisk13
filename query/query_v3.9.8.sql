/** 09.03.2020 **/
alter table tipocuenta add column dias int after nombre;

-- Para F.Lech
update tipocuenta set dias = 180 where idtipocuenta = 2;
update tipocuenta set dias = 360 where idtipocuenta = 3;
update tipocuenta set dias = 720 where idtipocuenta = 6;
update tipocuenta set dias = 1080 where idtipocuenta = 4;
--