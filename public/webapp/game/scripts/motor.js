
var ico_android_t = "/assets/webapp/game/images/androide_t.png";
var ico_android_l = "/assets/webapp/game/images/androide_l.png";
var ico_android_b = "/assets/webapp/game/images/androide_b.png";
var ico_android_r = "/assets/webapp/game/images/androide_r.png";

function derecha() {
    var interval = setInterval(function () {

        return function () {

            context.clearRect(0, 0,
                context.canvas.width,
                context.canvas.height);
            pintarCirculos();

            printObject();

            pintarObstaculo();

            verificarObstaculo();

            x += 1;

            if (x > 350) {
                $("#content_msg").html(mensajeAbcdroid("r","No puedes seguir avanzando!!! ","Por favor revisa tu codigo"));
                reiniciarTiempos();
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

            printObject();

            pintarObstaculo();

            verificarObstaculo();

            x -= 1;

            if (x < 0) {
                $("#content_msg").html(mensajeAbcdroid("r","No puedes seguir avanzando!!! ","Por favor revisa tu codigo"));
                reiniciarTiempos();
                x = 0;
            }

            if (x % 50 == 0) {
                clearInterval(interval);
            }
            context.drawImage(img, x, y);
        };
    }(), 0.2);
}

function abajo() {
    var interval = setInterval(function () {

        return function () {

            context.clearRect(0, 0,
                context.canvas.width,
                context.canvas.height);
            pintarCirculos();

            printObject();

            pintarObstaculo();

            verificarObstaculo();

            y += 1;

            if (y > 350) {
                $("#content_msg").html(mensajeAbcdroid("r","No puedes seguir avanzando!!! ","Por favor revisa tu codigo"));
                reiniciarTiempos();
                y = 350;
            }

            if (y % 50 == 0) {
                clearInterval(interval);
            }
            context.drawImage(img, x, y);
        };
    }(), 0.2);
}

function arriba() {
    var interval = setInterval(function () {

        return function () {

            context.clearRect(0, 0,
                context.canvas.width,
                context.canvas.height);
            pintarCirculos();

            printObject();

            pintarObstaculo();

            verificarObstaculo();

            y -= 1;

            if (y < 0) {
                $("#content_msg").html(mensajeAbcdroid("r","No puedes seguir avanzando!!! ","Por favor revisa tu codigo"));
                reiniciarTiempos();
                y = 0;
            }

            if (y % 50 == 0) {
                clearInterval(interval);
            }
            context.drawImage(img, x, y);
        };
    }(), 0.2);
}

function girarIzquierda() {

    dejarPelota(0);

    pintarObstaculo();

    //verificarObstaculo();

    img = new Image();

    if (parseInt(cont_rot) == 1) {
        img.src =  ico_android_t;

    } else if (parseInt(cont_rot) == 2) {
        img.src =  ico_android_l;

    } else if (parseInt(cont_rot) == 3) {
        img.src =  ico_android_b;

    } else {
        img.src = ico_android_r;
    }

    img.onload = function () {

        context.drawImage(img, x, y);
    };

    cont_rot++;

    if (cont_rot > 4) {
        cont_rot = 1;
    }
}

function girarDerechaa(){
    dejarPelota(0);

    pintarObstaculo();

    img = new Image();

    if (parseInt(cont_rot) == 1) {
        img.src =  ico_android_b;

    } else if (parseInt(cont_rot) == 2) {
        img.src =  ico_android_r;

    } else if (parseInt(cont_rot) == 3) {
        img.src =  ico_android_t;

    } else {
        img.src = ico_android_l;
    }

    img.onload = function () {

        context.drawImage(img, x, y);
    };

    cont_rot--;

    if(cont_rot == 0){
        cont_rot = 4;
    }
}

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

function retroceder(){
    switch (cont_rot) {
        case 1:
            izquierda();
            break;
        case 2:
            abajo();
            break;
        case 3:
            derecha();
            break;
        case 4:
            arriba();
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

    x = x_ini;
    y = y_ini;

    cont_rot = 1;

    val_x = [];
    val_y = [];
    repeticion = [];

    reiniciarPosiciones();

    funciones = [];
    pos_func_i = [];

    func_ulti = [];

    pre_else = 0;

    rec_func = 0;

    pos_i_w = [];

    en_bucle = false;

    pos_bucle = -1;

    //LIMPIA ECENARIO
    context.clearRect(0, 0, context.canvas.width, context.canvas.height);
    pintarCirculos();

    printObject();

    reiniciarTiempos();

    pintarObstaculo();

    verificarObstaculo();

    img = new Image();

    img.onload = function () {

        if (cont_rot == 1) {
            context.drawImage(img, x, y);
        }
    };

    img.src = ico_android_r;

    var editor = ace.edit("editor");

    cont_lin = 0;

    for (var i = 0; i < editor.session.getLength(); i++) {
        lineas[i] = editor.session.getLine(i);

        //funciones
        var pos_fun = editor.session.getLine(i).indexOf("function");

        if(pos_fun > -1){
            var pos_i = editor.session.getLine(i).indexOf(" ",pos_fun +1);
            var pos_f = editor.session.getLine(i).indexOf("()",pos_i +1);

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
    var line_ejec = 0;

    //VERIFICACION DE RESULTADO FINAL
    if(editor.session.getLength() == cont_lin){
        if(compararResultado(cont_rot,x,y,val_x,val_y,repeticion,1,x_fin,y_fin,val_x_fin,val_y_fin,repeticion_fin) > 0){
            $("#content_msg").html(mensajeAbcdroid("v","¡Felicitaciones!","Lograste completar este ejercicio."));
        }else{
            $("#content_msg").html(mensajeAbcdroid("r","Algo esta mal","La solucion del ejercicio es incorrecta."));
        }
        line_ejec++;
    }

    if(lineas[cont_lin].trim().length == 0){
        for(var c = cont_lin; c < lineas.length; c++){
            if(lineas[c].trim().length > 0){
                cont_lin = c;
                c = lineas.length;
            }
        }
    }

    //FIN VERIFICACION DE RESULTADO FINAL

    if(funciones.length > 0){
        for(var i = 0; i < funciones.length; i++){
            if(lineas[cont_lin].indexOf(funciones[i]) > -1 && lineas[cont_lin].indexOf("function") < 0){
                var tam_u = func_ulti.length;
                func_ulti[tam_u] = cont_lin;
                cont_lin = parseInt(pos_func_i[i]) + 1;
                line_ejec++;
            }
        }
    }

    if(lineas[cont_lin].indexOf("function ") > -1){
        cont_lin = obtenerFinalBloqueAbsoluto(cont_lin);
        line_ejec++;
        //rec_func++;
    }

    if(verificarElse(cont_lin)){

        //alert( "mensaje" + pre_else + " - " + cont_lin + " - " + rec_func);

        if(pre_else > 0){
            cont_lin = obtenerFinalBloqueAbsoluto(cont_lin + 1);

            pre_else--;
            rec_func++;
        }
        line_ejec++;
    }

    if(lineas[cont_lin].indexOf("if(") > -1){  //lineas[cont_lin].indexOf("if (") > -1

        var indFuncGet = 0;

        for(var j = 0; j < func_cond.length; j++){
            if(lineas[cont_lin].indexOf(func_cond[j]) > -1){

                rec_func++;

                var resEval = eval(func_cond[j] + '()');

                if(resEval){
                    cont_lin = obtenerFinalBloqueAbsolutoIf(cont_lin);
                    //pre_else = 0;
                }else{
                    pre_else++;
                }
                indFuncGet++;
            }
        }
        if(indFuncGet == 0){
            var interval = setInterval(function () {

                return function () {

                    $("#content_msg").html(mensajeAbcdroid("r","Algo esta mal!!!","Por favor usa los comandos indicados"));
                    reiniciarTiempos();
                };
            }(), 0.2);
        }
        line_ejec++;
    }

    if(lineas[cont_lin].indexOf("while(") > -1){
        var indFuncGet = 0;
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
                        indFuncGet++;
                    }
                }
            }
        }
        if(indFuncGet == 0){
            var interval = setInterval(function () {

                return function () {

                    $("#content_msg").html(mensajeAbcdroid("r","Algo esta mal!!!","Por favor usa los comandos indicados"));
                    reiniciarTiempos();
                };
            }(), 0.2);
        }
        line_ejec++;
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
        line_ejec++;
    }
    if(lineas[cont_lin].trim() == "}"){

        //if(pos_rec > -1 && !en_bucle){
        if(func_ulti.length > 0 && !en_bucle && rec_func == 0){
            var pos_pru = func_ulti.length;
            if(pos_pru > 1){
                cont_lin = func_ulti[pos_pru - 1];
                func_ulti.splice(pos_pru - 1,1);
            }else{
                cont_lin = func_ulti[0];
                func_ulti = [];
            }
            //pos_rec = -1;
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
            rec_func = 0;
        }
        line_ejec++;
    }

    if(lineas.length > 0){

        try{

            eval(lineas[cont_lin]);
        }
        catch (ex){
            if(line_ejec == 0){
                var interval = setInterval(function () {
                    return function () {
                        $("#content_msg").html(mensajeAbcdroid("r","Algo esta mal!","Por favor verifica que codigo este bien escrito"));
                        reiniciarTiempos();
                    };
                }(), 0.2);
            }
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

                    var resEval = eval(func_cond[t] + '()');

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
                }
            }
            if (lineas[i].indexOf("}") > -1) {
                cor_cer++;
            }
            if (cor_cer > otros) {

                exit++;
                pos_final = i;
            }
        }
    }
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

function backIsClear(){
    var retorno = false;
    if((x == 350 && cont_rot == 3) || (x == 0 && cont_rot == 1) || (y == 350 && cont_rot == 2) || (y == 0 && cont_rot == 4)){
        retorno = true;
    }
    return retorno;
}

function leftIsBlocked(){
    var retorno = false;
    for(var i = 0; i < cor_ini_x_obs.length; i++) {
        if (cor_ini_y_obs[i] == cor_fin_y_obs[i] && cor_ini_x_obs[i] != cor_fin_x_obs[i]) {

            var mayor = 0;
            var menor = 0;

            if (cor_ini_y_obs[i] > cor_fin_y_obs[i]) {
                mayor = cor_ini_y_obs[i];
                menor = cor_fin_y_obs[i];
            } else {
                mayor = cor_fin_y_obs[i];
                menor = cor_ini_y_obs[i];
            }

            if(cont_rot == 1){

                alert(cor_ini_x_obs[i] + " - " + cor_ini_y_obs[i] + " - " + cor_fin_x_obs[i] + " - " + cor_fin_y_obs[i] + " - X/Y: " + x + " - " + y)

                if(y == cor_ini_y_obs[i] && (x <= mayor && x >= menor)){
                    alert(entro);
                }
            }
        }
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

        var ubicacion = val_x.length;

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

    pintarObstaculo();

    img = new Image();

    img.onload = function () {

        context.drawImage(img, x, y);
    }
    if (parseInt(cont_rot) == 1) {
        img.src = ico_android_r;

    } else if (parseInt(cont_rot) == 2) {
        img.src =  ico_android_t;

    } else if (parseInt(cont_rot) == 3) {
        img.src =  ico_android_l;

    } else {
        img.src =  ico_android_b;
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

                    $("#content_msg").html(mensajeAbcdroid("r","No hay balones!!! ","Por favor revisa tu codigo"));
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

        pintarObstaculo();

        img = new Image();

        img.onload = function () {

            context.drawImage(img, x, y);
        }
        if (parseInt(cont_rot) == 1) {
            img.src = ico_android_r;

        } else if (parseInt(cont_rot) == 2) {
            img.src =  ico_android_t;

        } else if (parseInt(cont_rot) == 3) {
            img.src =  ico_android_l;

        } else {
            img.src = ico_android_b;
        }

    } else {


        var interval = setInterval(function () {

            return function () {

                $("#content_msg").html(mensajeAbcdroid("r","No hay balones!!! ","Por favor revisa tu codigo"));

                reiniciarTiempos();
            };
        }(), 0.2);

    }
}

function printObject() {
    for (var i = 0; i < val_x.length; i++) {
        var centerX = parseInt(val_x[i]) + 25;
        var centerY = parseInt(val_y[i]) + 25;
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

function iniciarJuego(){
    pintarCirculos();

    printObject();

    pintarObstaculo();

    img.onload = function () {

        if (cont_rot == 1) {
            context.drawImage(img, x, y);
        }
    }

    img.id = "imgRobot";
    img.src = ico_android_r;
}

function reiniciarPosiciones(){
    for(var o = 0; o < val_x_ini.length; o++){
        val_x[o] = val_x_ini[o];
        val_y[o] = val_y_ini[o];
        repeticion[o] = repeticion_ini[o];
    }
}

function obtenerPosiciones(val1, val2, val3, arr1, arr2, arr3, iden){
    var comas = parseInt((val1.match(/,/g) || []).length);

    if(val1.length > 0 && val2.length > 0 && val3.length > 0){
        for (var r = 0; r <= comas; r++) {
            if (r == 0) {
                if(val1.indexOf(",") == -1 && val2.indexOf(",") == -1 && val3.indexOf(",") == -1){
                    arr1[r] = val1.substr(r, val1.length);
                    arr2[r] = val2.substr(r, val2.length);
                    arr3[r] = val3.substr(r, val3.length);
                }else{
                    arr1[r] = val1.substr(r, val1.indexOf(","));
                    arr2[r] = val2.substr(r, val2.indexOf(","));
                    arr3[r] = val3.substr(r, val3.indexOf(","));

                    pos_u_c_x = parseInt(val1.indexOf(","));
                    pos_u_c_y = parseInt(val2.indexOf(","));
                    pos_u_c_r = parseInt(val3.indexOf(","));
                }
            } else {

                if(r == comas){

                    arr1[r] = val1.substr(pos_u_c_x + 1, (val1.length - pos_u_c_x) - 1);
                    arr2[r] = val2.substr(pos_u_c_y + 1, (val2.length - pos_u_c_y) - 1);
                    arr3[r] = val3.substr(pos_u_c_r + 1, (val3.length - pos_u_c_r) - 1)

                }else{

                    arr1[r] = val1.substr(pos_u_c_x + 1, (val1.indexOf(",", pos_u_c_x + 1) - pos_u_c_x) - 1);
                    arr2[r] = val2.substr(pos_u_c_y + 1, (val2.indexOf(",", pos_u_c_y + 1) - pos_u_c_y) - 1);
                    arr3[r] = val3.substr(pos_u_c_r + 1, (val3.indexOf(",", pos_u_c_r + 1) - pos_u_c_r) - 1);

                    pos_u_c_x = parseInt(val1.indexOf(",", pos_u_c_x + 1));
                    pos_u_c_y = parseInt(val2.indexOf(",", pos_u_c_y + 1));
                    pos_u_c_r = parseInt(val3.indexOf(",", pos_u_c_r + 1));
                }
            }
        }
    }

    if(iden == 'i'){
        reiniciarPosiciones();
    }
}

function obtenerObstaculos(val1, val2, val3, val4, arr1, arr2, arr3, arr4){
    var comas = parseInt((val1.match(/,/g) || []).length);

    if(val1.length > 0 && val2.length > 0 && val3.length > 0 && val4.length > 0){
        for (var r = 0; r <= comas; r++) {
            if (r == 0) {
                if(val1.indexOf(",") == -1 && val2.indexOf(",") == -1 && val3.indexOf(",") == -1){
                    arr1[r] = val1.substr(r, val1.length);
                    arr2[r] = val2.substr(r, val2.length);
                    arr3[r] = val3.substr(r, val3.length);
                    arr4[r] = val4.substr(r, val4.length);

                }else{
                    arr1[r] = val1.substr(r, val1.indexOf(","));
                    arr2[r] = val2.substr(r, val2.indexOf(","));
                    arr3[r] = val3.substr(r, val3.indexOf(","));
                    arr4[r] = val4.substr(r, val4.indexOf(","));

                    pos_u_c_x = parseInt(val1.indexOf(","));
                    pos_u_c_y = parseInt(val2.indexOf(","));
                    pos_u_c_r = parseInt(val3.indexOf(","));
                    pos_u_c_op = parseInt(val4.indexOf(","));

                }
            } else {

                if(r == comas){

                    arr1[r] = val1.substr(pos_u_c_x + 1, (val1.length - pos_u_c_x) - 1);
                    arr2[r] = val2.substr(pos_u_c_y + 1, (val2.length - pos_u_c_y) - 1);
                    arr3[r] = val3.substr(pos_u_c_r + 1, (val3.length - pos_u_c_r) - 1);
                    arr4[r] = val4.substr(pos_u_c_op + 1, (val4.length - pos_u_c_op) - 1);

                }else{

                    arr1[r] = val1.substr(pos_u_c_x + 1, (val1.indexOf(",", pos_u_c_x + 1) - pos_u_c_x) - 1);
                    arr2[r] = val2.substr(pos_u_c_y + 1, (val2.indexOf(",", pos_u_c_y + 1) - pos_u_c_y) - 1);
                    arr3[r] = val3.substr(pos_u_c_r + 1, (val3.indexOf(",", pos_u_c_r + 1) - pos_u_c_r) - 1);
                    arr4[r] = val4.substr(pos_u_c_op + 1, (val4.indexOf(",", pos_u_c_op + 1) - pos_u_c_op) - 1);


                    pos_u_c_x = parseInt(val1.indexOf(",", pos_u_c_x + 1));
                    pos_u_c_y = parseInt(val2.indexOf(",", pos_u_c_y + 1));
                    pos_u_c_r = parseInt(val3.indexOf(",", pos_u_c_r + 1));
                    pos_u_c_op = parseInt(val4.indexOf(",", pos_u_c_op + 1));
                }
            }
        }
    }
}

function compararResultado(rotate_i, pos_x_i_c, pos_y_i_c, val_x_c, val_y_c, val_r_c, rotate_i_p, pos_x_i_c_p, pos_y_i_c_p, val_x_c_p, val_y_c_p, val_r_c_p){

    var num_val = 0;

    if(pos_x_i_c == pos_x_i_c_p && pos_y_i_c == pos_y_i_c_p){
        if(rotate_i == rotate_i_p){
            if(val_x_c.length == val_x_c_p.length && val_y_c.length == val_y_c_p.length && val_r_c.length == val_r_c_p.length ){
                if(val_x_c.length > 0){
                    for(var i = 0; i < val_x_c.length; i++){
                        for(var j = 0; j < val_x_c_p.length; j++){
                            if(val_x_c[i] == val_x_c_p[j] && val_y_c[i] == val_y_c_p[j] && val_r_c[i] == val_r_c_p[j]){
                                num_val++;
                            }
                        }
                    }
                    if(val_r_c_p.length != num_val ){
                        num_val = 0;
                    }
                }else{
                    num_val++;
                }
            }
        }
    }

    return num_val;
}

function pintarObstaculo(){

    for(var i = 0; i < cor_ini_x_obs.length; i++){
        context.beginPath();
        context.moveTo(parseInt(cor_ini_x_obs[i]), parseInt(cor_ini_y_obs[i]));//x y
        context.lineTo(parseInt(cor_fin_x_obs[i]), parseInt(cor_fin_y_obs[i]));//x,y
        context.lineWidth = 2;

        context.strokeStyle = "#ff0000";
        context.stroke();
    }
}

function verificarObstaculo(){

    for(var i = 0; i < cor_ini_x_obs.length; i++){
        if(cor_ini_x_obs[i] == cor_fin_x_obs[i] && cor_ini_y_obs[i] != cor_fin_y_obs[i]){
            var mayor = 0;
            var menor = 0;

            if(cor_ini_y_obs[i] > cor_fin_y_obs[i]){
                mayor = cor_ini_y_obs[i];
                menor = cor_fin_y_obs[i];
            }else{
                mayor = cor_fin_y_obs[i];
                menor = cor_ini_y_obs[i];
            }

            if(cont_rot == 1){
                if(x + 50 == cor_ini_x_obs[i] && (y < mayor && y >= menor)){
                    reiniciarTiempos();
                    $("#content_msg").html(mensajeAbcdroid("r","Debes rodear el obstaculo!!! ","Por favor revisa tu codigo"));
                }
            }else if(cont_rot == 3){
                if(x == cor_ini_x_obs[i] && (y < mayor && y >= menor)){
                    reiniciarTiempos();
                    $("#content_msg").html(mensajeAbcdroid("r","Debes rodear el obstaculo!!! ","Por favor revisa tu codigo"));

                }
            }
        }else if(cor_ini_y_obs[i] == cor_fin_y_obs[i] && cor_ini_x_obs[i] != cor_fin_x_obs[i]){
            var mayor = 0;
            var menor = 0;

            if(cor_ini_x_obs[i] > cor_fin_x_obs[i]){
                mayor = cor_ini_x_obs[i];
                menor = cor_fin_x_obs[i];
            }else{
                mayor = cor_fin_x_obs[i];
                menor = cor_ini_x_obs[i];
            }

            if(cont_rot == 2){
                if(y == cor_ini_y_obs[i] && (x < mayor && x >= menor)){
                    $("#content_msg").html(mensajeAbcdroid("r","Debes rodear el obstaculo!!! ","Por favor revisa tu codigo"));
                    reiniciarTiempos();
                }
            }else if(cont_rot == 4){
                if(y + 50 == cor_ini_y_obs[i] && (x < mayor && x >= menor)){
                    $("#content_msg").html(mensajeAbcdroid("r","Debes rodear el obstaculo!!! ","Por favor revisa tu codigo"));
                    reiniciarTiempos();
                }
            }
        }
    }
}
