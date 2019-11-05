
function mensajeAbcdroid(tipo,titulo, mensaje){
    var cadena = "";

    if(tipo == "v"){
        cadena = "<div class='kode-alert alert8'><i class='fa fa-info'></i> <a href='#' class='closed'>&times;</a>" + mensaje + "</div>";

    }else if(tipo == "r"){
        cadena = "<div class='kode-alert alert11'><i class='fa fa-warning'></i> <a href='#' class='closed'>&times;</a>"+ mensaje + "</div>";
    }

    return cadena;

}