/**
 * Created by abcdroid on 20/10/14.
 */
<script>
$(document).ready(

function () {

    var img = new Image();
    var canvas = document.getElementById('myCanvas');
    var context = canvas.getContext('2d');

    var ini_pos = "{{ exercice.initial_position }}"

    var valx = "{{ exercice.ball_x }}"
    var valy = "{{ exercice.ball_y }}"
    var repp = "{{ exercice.repetition_ball }}"

    var x = parseInt(ini_pos.substring(0,ini_pos.indexOf(","))), y = parseInt(ini_pos.substring((ini_pos.indexOf(",") + 1),ini_pos.length)); 	//POSICION INICIAL DEL ROBOT
    var cont_rot = 1;	//SENTIDO DE ROTACION

    var pos_u_c_x = 0;
    var pos_u_c_y = 0;
    var pos_u_c_r = 0;

    var val_x = [];		//POSICIONES EN X
    var val_y = [];		//POSICIONES EN Y
    var repeticion = [];//NRO DE PELOTAS EN LA UBICACION

    for (var r = 0; r <= parseInt((valx.match(/,/g) || []).length); r++) {
    if (r == 0) {
    val_x[r] = parseInt(valx.substr(r, valx.indexOf(",")));
    val_y[r] = parseInt(valy.substr(r, valy.indexOf(",")));
    repeticion[r] = parseInt(repp.substr(r, repp.indexOf(",")));

    } else {
    val_x[r] = parseInt(valx.substr(pos_u_c_x, valx.indexOf(",") + 1));
    val_y[r] = parseInt(valy.substr(pos_u_c_y, valy.indexOf(",")));
    repeticion[r] = parseInt(repp.substr(pos_u_c_r, repp.indexOf(",")));

    }
    pos_u_c_x = parseInt(valx.indexOf(",", pos_u_c_x)) + 1;
    pos_u_c_y = parseInt(valy.indexOf(",", pos_u_c_y)) + 1;
    pos_u_c_r = parseInt(repp.indexOf(",", pos_u_c_r)) + 1;
    }

    var lineas = [];	//LINEAS
    var cont_lin = 0;	//LINEAS EN EL EDITOR DE TEXTO
    var marker;			//SOMBRA DE LINEA DE EJECUCION

    //INDICADOR DE EJECUCION
    var ind_ejecucion = 0;


    //RECONOCIMIENTO DE FUNCIONES
    var funciones = [];
    var pos_func_i = [];
    var pos_rec = -1;

    //RECONOCIMIENTO DE WHILE
    var pos_i_w = [];

    //RECONOCIMIENTO IF
    var func_cond = ['verificarTope','verificarPelota'];
    var pre_else = 0;

    var en_if = 0;

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

    pintarCirculos();

    printObject();

    img.onload = function () {

    if (cont_rot == 1) {
    context.drawImage(img, x, y);
    }
    }

    img.id = "imgRobot";
    img.src =  STATIC_URL + "/static/student/images/androide_r.png";


    $("#btnEjecutar").click(function () {
    ejecutar();
    });
    $("#btnCancelar").click(function () {
    cancelar();
    });

    $("#btnContinuar").click(function () {
    compararResultado();
    });

    /*function continuar(){
     var valx = "{{ exercice.ball_x }}"
     var valy = "{{ exercice.ball_y }}"
     var repp = "{{ exercice.repetition_ball }}"

     var val_xp = [];

     var pos_u_c_x = 0;

     for(var r = 0; r <= parseInt((valx.match(/,/g) || []).length); r++){
     if(r == 0){
     val_xp[r] = valx.substr(r,valx.indexOf(","));
     }else{
     val_xp[r] = valx.substr(pos_u_c_x,valx.indexOf(",") + 1);
     }
     pos_u_c_x = parseInt(valx.indexOf(",",pos_u_c_x)) + 1;
     }
     }*/

    function derecha() {
    var interval = setInterval(function () {

    return function () {

    context.clearRect(0, 0,
    context.canvas.width,
    context.canvas.height);
    pintarCirculos();

    printObject()

    x += 1;

    if (x > 350) {
    mensajeInfo("Chocaste !!!");
    x = 350;
    }

    if (x % 50 == 0) {
    clearInterval(interval);
    }
    context.drawImage(img, x, y);

    };
    }(), 0.2);
    }


    function izquierda() {
    var interval = setInterval(function () {

    return function () {

    context.clearRect(0, 0,
    context.canvas.width,
    context.canvas.height);
    pintarCirculos();

    printObject()

    x -= 1

    if (x < 0) {
    mensajeInfo("Chocaste!!!");
    x = 0;
    }

    if (x % 50 == 0) {
    clearInterval(interval);
    }
    context.drawImage(img, x, y);
    };
    }(), 0.2);
    }
    ;

    function abajo() {
    var interval = setInterval(function () {

    return function () {

    context.clearRect(0, 0,
    context.canvas.width,
    context.canvas.height);
    pintarCirculos();

    printObject()

    y += 1;

    if (y > 350) {
    mensajeInfo("Chocaste !!!");
    y = 350;
    }

    if (y % 50 == 0) {
    clearInterval(interval);
    }
    context.drawImage(img, x, y);
    };
    }(), 0.2);
    };

    function arriba() {
    var interval = setInterval(function () {

    return function () {

    context.clearRect(0, 0,
    context.canvas.width,
    context.canvas.height);
    pintarCirculos();

    printObject()

    y -= 1

    if (y < 0) {
    mensajeInfo("Chocaste !!!");
    y = 0;
    }

    if (y % 50 == 0) {
    clearInterval(interval);
    }
    context.drawImage(img, x, y);
    };
    }(), 0.2);
    };

    function girarIzquierda() {

    dejarPelota(0);

    img = new Image();

    if (parseInt(cont_rot) == 1) {
    img.src =  STATIC_URL + "/static/student/images/androide_t.png";

    } else if (parseInt(cont_rot) == 2) {
    img.src =  STATIC_URL + "/static/student/images/androide_l.png";

    } else if (parseInt(cont_rot) == 3) {
    img.src =  STATIC_URL + "/static/student/images/androide_b.png";

    } else {
    img.src =  STATIC_URL + "/static/student/images/androide_r.png";

    }

    img.onload = function () {

    context.drawImage(img, x, y);
    }

    cont_rot++;

    if (cont_rot > 4) {
    cont_rot = 1;
    }
    };

    function avanzar() {

    switch (cont_rot) {
    case 1:
    derecha();
    break;
    case 2:
    arriba();
    break;
    case 3:
    izquierda();
    break;
    case 4:
    abajo();
    break;
    }
    }

    function cancelar() {
    reiniciarTiempos();
    }

    function ejecutar() {

    $("#lblMensaje").empty();
    $("#lblMensaje").css("display","none");

    lineas = [];
    x = parseInt(ini_pos.substring(0,ini_pos.indexOf(",")));
    y = parseInt(ini_pos.substring((ini_pos.indexOf(",") + 1),ini_pos.length));

    cont_rot = 1;

    var pos_u_c_x = 0;
    var pos_u_c_y = 0;
    var pos_u_c_r = 0;

    val_x = [];
    val_y = [];
    repeticion = [];

    for (var r = 0; r <= parseInt((valx.match(/,/g) || []).length); r++) {
    if (r == 0) {
    val_x[r] = parseInt(valx.substr(r, valx.indexOf(",")));
    val_y[r] = parseInt(valy.substr(r, valy.indexOf(",")));
    repeticion[r] = parseInt(repp.substr(r, repp.indexOf(",")));

    } else {
    val_x[r] = parseInt(valx.substr(pos_u_c_x, valx.indexOf(",") + 1));
    val_y[r] = parseInt(valy.substr(pos_u_c_y, valy.indexOf(",")));
    repeticion[r] = parseInt(repp.substr(pos_u_c_r, repp.indexOf(",")));

    }
    pos_u_c_x = parseInt(valx.indexOf(",", pos_u_c_x)) + 1;
    pos_u_c_y = parseInt(valy.indexOf(",", pos_u_c_y)) + 1;
    pos_u_c_r = parseInt(repp.indexOf(",", pos_u_c_r)) + 1;
    }


    funciones = [];
    pos_func_i = [];

    pre_else = 0;

    rec_func = 0;

    pos_i_w = [];

    en_bucle = false;

    pos_bucle = -1;

    //LIMPIA ECENARIO
    context.clearRect(0, 0,
    context.canvas.width,
    context.canvas.height);
    pintarCirculos();

    printObject()

    reiniciarTiempos();

    img = new Image();

    img.onload = function () {

    if (cont_rot == 1) {
    context.drawImage(img, x, y);
    }
    }

    img.src =  STATIC_URL + "/static/student/images/androide_r.png";

    var editor = ace.edit("editor");

    cont_lin = 0;

    for (var i = 0; i < editor.session.getLength(); i++) {
    lineas[i] = editor.session.getLine(i)

    //funciones
    var pos_fun = editor.session.getLine(i).indexOf("function");

    if(pos_fun > -1){
    var pos_i = editor.session.getLine(i).indexOf(" ",pos_fun +1);
    var pos_f = editor.session.getLine(i).indexOf("()",pos_i +1)

    var tam_fun = funciones.length;
    funciones[tam_fun] = editor.session.getLine(i).substring(pos_i + 1, pos_f);
    pos_func_i[tam_fun] = i;

    }
    pos_fun = null;

    //WHILE
    var pos_whi = editor.session.getLine(i).indexOf("while(");

    if(pos_whi > -1){
    var tam_whi = pos_i_w.length;
    var pos_i = i;
    pos_i_w[tam_whi] = pos_i;

    }
    pos_whi = null;

    //FOR
    var pos_for = editor.session.getLine(i).indexOf("for(");

    if(pos_for > -1){
    var tam_for = pos_i_for.length;
    var pos_i = i;
    pos_i_for[tam_for] = pos_i;

    //alert("FOR : " + pos_i + " - " + editor.session.getLine(i))

    }
    pos_for = null;

    }

    run();
    }

    function run() {
    editor.session.removeMarker(marker);
    //rec_func = 0;


    if(funciones.length > 0){
    for(var i = 0; i < funciones.length; i++){
    if(lineas[cont_lin].indexOf(funciones[i]) > -1 && lineas[cont_lin].indexOf("function") < 0){

    pos_rec = cont_lin;

    cont_lin = parseInt(pos_func_i[i]) + 1;
    }
    }
    }

    if(lineas[cont_lin].indexOf("function ") > -1){
    cont_lin = obtenerFinalBloqueAbsoluto(cont_lin);
    //rec_func++;
    }

    if(verificarElse(cont_lin)){

    //alert( "mensaje" + pre_else + " - " + cont_lin + " - " + rec_func);

    if(pre_else > 0){
    cont_lin = obtenerFinalBloqueAbsoluto(cont_lin + 1);

    pre_else--;
    rec_func++;
    }
    }

    if(lineas[cont_lin].indexOf("if(") > -1){  //lineas[cont_lin].indexOf("if (") > -1

    for(var j = 0; j < func_cond.length; j++){
    if(lineas[cont_lin].indexOf(func_cond[j]) > -1){

    rec_func++;

    var resEval = eval(func_cond[j] + '()')

    if(resEval){
    cont_lin = obtenerFinalBloqueAbsolutoIf(cont_lin);
    //pre_else = 0;

    }else{

    pre_else++;
    }
    }
    }
    }

    if(lineas[cont_lin].indexOf("while(") > -1){

    for(var t = 0; t < func_cond.length; t++){

    if(lineas[cont_lin].indexOf(func_cond[t]) > -1){

    for(var k = 0; k < pos_i_w.length; k++){

    if(parseInt(pos_i_w[k]) == cont_lin){

    var resEval = eval(func_cond[t] + '()');

    //alert(func_cond[t]+ " - " + resEval);

    if(!resEval){
    pos_bucle = cont_lin;
    en_bucle = true;

    }else{
    en_bucle = false;
    cont_lin = obtenerFinalBloqueAbsoluto(cont_lin);
    //alert(cont_lin)
    }
    }
    }
    }
    }
    }

    if(lineas[cont_lin].indexOf("for(") > -1){

    for(var r = 0; r < pos_i_for.length; r++){

    if(parseInt(pos_i_for[r]) == cont_lin){

    var numero = 0;

    var pos_signo = lineas[cont_lin].indexOf("<");
    var lim_signo = lineas[cont_lin].indexOf(";", pos_signo);

    var cadena = (lineas[cont_lin].substring(pos_signo + 1,lim_signo)).trim();

    if(comprobarEntero(cadena)){
    numero = parseInt(cadena);
    }

    rep_for = numero;
    en_bucle_for = true;
    pos_bucle_for = cont_lin;

    }
    }
    }
    if(lineas[cont_lin].trim() == "}"){
    //if(lineas[cont_lin].indexOf("}") > -1){
    //alert(lineas[cont_lin].trim());

    if(pos_rec > -1 && !en_bucle){
    cont_lin = pos_rec;
    pos_rec = -1;
    //alert("function")
    }else if(en_bucle && pos_bucle > -1 && rec_func == 0){
    comprobarBucle();
    cont_lin = pos_bucle;
    //alert("while")
    }else if(en_bucle_for && pos_bucle_for > -1 && rep_for > 1 && rec_func == 0){
    rep_for--;
    cont_lin = pos_bucle_for;
    //alert("FOR" + cont_lin + " - " + rec_func)
    }else{
    /*if(rec_func != 0){
     //cont_lin++;
     }*/
    //alert("else")
    rec_func = 0;
    }
    }

    if(lineas.length > 0){

    try{
    eval(lineas[cont_lin]);
    }
    catch (ex){

    }

    var Range = ace.require('ace/range').Range;
    marker = editor.session.addMarker(new Range(cont_lin, 0, cont_lin, lineas[cont_lin].length),"ace_active-line", "fullLine");

    cont_lin++;

    var postEjecucion;

    if (cont_lin <= editor.session.getLength()) {

    postEjecucion = setTimeout(function () {

    run();

    }, 1000);
    } else {
    editor.session.removeMarker(marker);
    clearTimeout(postEjecucion);
    }
    }
    }

function comprobarBucle() {
    for (var t = 0; t < func_cond.length; t++) {
    if (lineas[pos_bucle].indexOf(func_cond[t]) > -1) {

    for (var k = 0; k < pos_i_w.length; k++) {
    if (parseInt(pos_i_w[k]) == pos_bucle) {

    var resEval = eval(func_cond[t] + '()')

    if (!resEval) {

    en_bucle = true;

    } else {

    en_bucle = false;
    pos_bucle = obtenerFinalBloqueAbsoluto(pos_bucle);
    //alert(pos_bucle)
    }
    }
    }
    }
    }
    }

function verificarElse(posi){
    var retorno = false;
    if(lineas[posi].indexOf("}else{") > -1 || lineas[posi].indexOf("} else {") > -1){
    retorno = true;
    }
    return retorno;
    }

function obtenerFinalBloqueAbsoluto(posi) {
    var pos_final = -1;
    var exit = 0;
    var otros = 0;
    var cor_cer = 0;
    var sen = ["if(", "for(", "while("];

    for (var i = posi + 1; i < lineas.length; i++) {
    if (exit == 0) {
    for (var f = 0; f < sen.length; f++) {
    if (lineas[i].indexOf(sen[f]) > -1) {
    otros++;
    //alert("ABRE LLAVE : " +lineas[i] + " - " + otros + " - " + i);
    }
    }
    if (lineas[i].indexOf("}") > -1 && lineas[i].trim().length == 1) {
    cor_cer++;
    //alert( "CIERRA LLAVE : " +lineas[i] + " - " + cor_cer + " - " + i);
    }
    if (cor_cer > otros) {

    exit++;
    pos_final = i;

    //alert( "POS FINAL : " + pos_final + " - " + cor_cer + " > " + otros)
    }
    }
    }
//alert("DATOS : " + cor_cer + " > " + otros + " - pos final : " + pos_final)

return pos_final;
}

function obtenerFinalBloqueAbsolutoIf(posi) {
    var pos_final = -1;
    var exit = 0;
    var otros = 0;
    var cor_cer = 0;
    var sen = ["if(", "for(", "while("];

    for (var i = posi + 1; i < lineas.length; i++) {
    if (exit == 0) {
    for (var f = 0; f < sen.length; f++) {
    if (lineas[i].indexOf(sen[f]) > -1) {
    otros++;
    //alert("ABRE LLAVE : " +lineas[i] + " - " + otros + " - " + i);
    }
    }
    if (lineas[i].indexOf("}") > -1) {
    cor_cer++;
    //alert( "CIERRA LLAVE : " +lineas[i] + " - " + cor_cer + " - " + i);
    }
    if (cor_cer > otros) {

    exit++;
    pos_final = i;

    //alert( "POS FINAL : " + pos_final + " - " + cor_cer + " > " + otros)
    }
    }
    }
//alert("DATOS : " + cor_cer + " > " + otros + " - pos final : " + pos_final)

return pos_final;
}



function obtenerFinalBloque(posi){
    //ESTA FUNCION DEBE MEJORAR
    var pos_final = 0;
    var exit = 0;
    for(var i = posi; i < lineas.length; i++){
    if(exit == 0){
    if(lineas[i].indexOf("}") > -1){
    pos_final = i;
    exit++;
    }
    }
    }

return pos_final;
}

//FUNCIONES DE CONDICION

function verificarTope(){
    var retorno = false;
    if((x == 350 && cont_rot == 1) || (x == 0 && cont_rot == 3) || (y == 350 && cont_rot == 4) || (y == 0 && cont_rot == 2)){
    retorno = true;
    }
    return retorno;
    }

function verificarPelota(){
    var retorno = true;

    for(var i = 0; i < val_x.length; i++){
    if(val_x[i] == x && val_y[i] == y){
    retorno = false;
    }
    }
    return retorno;
    }

//FIN FUNCIONES DE CONDICION

function comprobarEntero(val){
    var retorno = false;

    if(parseInt(val) % 1 == 0){
    retorno = true;
    }

    return retorno;

    }

function pintarCirculos() {
    for (var posx = 25; posx <= 400; posx = posx + 50) {
    for (var posy = 25; posy <= 400; posy = posy + 50) {
    var centerX = posx;
    var centerY = posy;
    var radius = 1;
    context.beginPath();
    context.arc(centerX, centerY, radius, 0, 2 * Math.PI, false);
    context.fillStyle = 'black';
    context.fill();
    context.lineWidth = 1;
    context.strokeStyle = 'black';
    context.stroke();
    }
    }
    };

function dejarPelota(indicador) {

    if (indicador == null) {
    indicador = 1;
    }

    if (indicador > 0) {

    ubicacion = val_x.length;

    if (x % 50 == 0 && y % 50 == 0) {

    var indRegistro = 0;

    for (var i = 0; i < val_x.length; i++) {
    if (val_x[i] == x && val_y[i] == y) {

    var existente = parseInt(repeticion[i]) + 1;
    repeticion[i] = existente;
    indRegistro++;
    }
    }
    if (indRegistro == 0) {
    val_x[ubicacion] = x;
    val_y[ubicacion] = y;
    repeticion[ubicacion] = 1;
    }
    }
    }

    context.clearRect(0, 0,
    context.canvas.width,
    context.canvas.height);

    pintarCirculos();

    printObject();

    img = new Image();

    img.onload = function () {

    context.drawImage(img, x, y);
    }
    if (parseInt(cont_rot) == 1) {
    img.src =  STATIC_URL + "/static/student/images/androide_r.png";

    } else if (parseInt(cont_rot) == 2) {
    img.src =  STATIC_URL + "/static/student/images/androide_t.png";

    } else if (parseInt(cont_rot) == 3) {
    img.src =  STATIC_URL + "/static/student/images/androide_l.png";

    } else {
    img.src =  STATIC_URL + "/static/student/images/androide_b.png";
    }

    }

function recogerPelota() {

    var encontro = 0;

    if (val_x.length > 0 && val_y.length > 0) {
    for (var j = 0; j < val_x.length; j++) {
    if (parseInt(val_x[j]) == x && parseInt(val_y[j]) == y) {

    if (parseInt(repeticion[j]) > 1) {
    repeticion[j] = parseInt(repeticion[j]) - 1;
    } else {
    val_x.splice(j, 1);
    val_y.splice(j, 1);
    repeticion.splice(j, 1);
    }

    encontro++;
    }
    }

    if (encontro == 0) {

    var interval = setInterval(function () {

    return function () {

    mensajeInfo("No hay pelotas en esta posicion!");
    reiniciarTiempos();
    };
    }(), 0.2);
    }

    encontro = 0;

    context.clearRect(0, 0,
    context.canvas.width,
    context.canvas.height);

    pintarCirculos();

    printObject();

    img = new Image();

    img.onload = function () {

    context.drawImage(img, x, y);
    }
    if (parseInt(cont_rot) == 1) {
    img.src =  STATIC_URL + "/static/student/images/androide_r.png";

    } else if (parseInt(cont_rot) == 2) {
    img.src =  STATIC_URL + "/static/student/images/androide_t.png";

    } else if (parseInt(cont_rot) == 3) {
    img.src =  STATIC_URL + "/static/student/images/androide_l.png";

    } else {
    img.src =  STATIC_URL + "/static/student/images/androide_b.png";
    }

    } else {


    var interval = setInterval(function () {

    return function () {

    mensajeInfo("No hay pelotas en esta Posicion!");

    reiniciarTiempos();
    };
    }(), 0.2);

    }
    }

function printObject() {
    for (var i = 0; i < val_x.length; i++) {
    var centerX = val_x[i] + 25;
    var centerY = val_y[i] + 25;
    var radius = 25;
    context.beginPath();
    context.arc(centerX, centerY, radius, 0, 2 * Math.PI, false);
    context.fillStyle = 'yellow';
    context.fill();
    context.lineWidth = 0.5;
    context.strokeStyle = 'yellow';
    context.stroke();

    if (parseInt(repeticion[i]) > 1) {
    setTextRe(i);
    }
    }
    }

function setTextRe(val) {
    context.fillStyle = '#000';
    context.textBaseline = 'bottom';
    context.fillText("x " + repeticion[val], parseInt(val_x[val]) + 20, parseInt(val_y[val]) + 29);
    }

function reiniciarTiempos() {
    var allTimeout = setTimeout(";");
    for (var i = 0; i < allTimeout; i++) {
    clearTimeout(i);
    }
    }

function mensajeInfo(mensaje){
    $("#lblMensaje").text(mensaje).css("display","block");

    reiniciarTiempos();
    }

function compararResultado(){
    /*valx;
     valy;
     repp;*/

    var val_x_c = [200,300,50];
    var val_y_c = [150,50,100];
    var val_r_c = [4,2,3];

    var pos_com_x = 0;
    var pos_com_y = 0;
    var pos_com_r = 0;

    /*for (var r = 0; r <= parseInt((valx.match(/,/g) || []).length); r++) {
     if (r == 0) {
     val_x_c[r] = parseInt(valx.substr(r, valx.indexOf(",")));
     val_y_c[r] = parseInt(valy.substr(r, valy.indexOf(",")));
     val_r_c[r] = parseInt(repp.substr(r, repp.indexOf(",")));

     } else {
     val_x_c[r] = parseInt(valx.substr(pos_com_x, valx.indexOf(",") + 1));
     val_y_c[r] = parseInt(valy.substr(pos_com_y, valy.indexOf(",")));
     val_r_c[r] = parseInt(repp.substr(pos_u_c_r, repp.indexOf(",")));

     }
     pos_com_x = parseInt(valx.indexOf(",", pos_com_x)) + 1;
     pos_com_y = parseInt(valy.indexOf(",", pos_com_y)) + 1;
     pos_com_r = parseInt(repp.indexOf(",", pos_com_r)) + 1;
     }*/

    val_x = [50,200,300];//50,200,300
    val_y = [100,150,50];//100,150,50
    repeticion = [3,4,2];//3,4,2
    var num_val;

    for(var i = 0; i < val_x_c.length; i++){

    for(var k = 0; k < val_x.length; k++){
    //alert(val_x_c[i] + " - " + val_x[k])
    if(val_x_c[i] == val_x[k] && val_y_c[i] == val_y[k]){

    alert(val_x_c[i] + " - " + i + "/" + val_x[k] + " - " + k)
    }else{
    //alert(val_x_c[i] + " - " + i + "/" + val_x[k] + " - " + k)
    }
    }
    }
    }

});
</script>