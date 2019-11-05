/**
 * Created by abcdroid on 20/10/14.
 */
/**
 * Created by abcdroid on 20/10/14.
 */
var editor;

var img = new Image();
var canvas;
var context;

var ini_pos;
var pos_fin;

var x_ini = 0;
var y_ini = 0;

var x_fin = 0;
var y_fin = 0;

var x;
var y;
var cont_rot = 1;	//SENTIDO DE ROTACION

var pos_u_c_x = 0;
var pos_u_c_y = 0;
var pos_u_c_r = 0;
var pos_u_c_op = 0;

var val_x_ini = [];
var val_y_ini = [];
var repeticion_ini = [];

var val_x_fin = [];
var val_y_fin = [];
var repeticion_fin = [];

var val_x = [];		//POSICIONES EN X
var val_y = [];		//POSICIONES EN Y
var repeticion = [];//NRO DE PELOTAS EN LA UBICACION

var lineas = [];	//LINEAS
var cont_lin = 0;	//LINEAS EN EL EDITOR DE TEXTO
var marker;			//SOMBRA DE LINEA DE EJECUCION

//RECONOCIMIENTO DE FUNCIONES
var funciones = [];
var pos_func_i = [];
var pos_rec = -1;

//RECONOCIMIENTO DE WHILE
var pos_i_w = [];

//RECONOCIMIENTO IF
var func_cond = ['verificarTope','verificarPelota'];
var pre_else = 0;

//RECONICIMIENTO FOR
var pos_i_for = [];

//RECONOCIMIENTO DE FUNCION
var rec_func = 0;

//RECONOCIMIENTO DE BUCLE WHILE
var en_bucle = false;
var pos_bucle = -1;

//RECONOCIMIENTO DEL BUCLE FOR
var en_bucle_for = false;
var pos_bucle_for = -1;
var rep_for = 0;

//OBSTACULOS
var cor_ini_x_obs = [];
var cor_ini_y_obs = [];
var cor_fin_x_obs = [];
var cor_fin_y_obs = [];

//ALMACENA POS ULTIMA FUNCION
var func_ulti = [];