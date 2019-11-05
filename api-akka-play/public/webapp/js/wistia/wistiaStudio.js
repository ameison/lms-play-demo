var timewistia = localStorage.getItem("timewistia");
var statuswistia = localStorage.getItem("statuswistia");
var volumewistia;
var tockenwistia;

//wistiaEmbed = Wistia.embed("e1mf7b177g", {
wistiaEmbed = Wistia.embed("tockenwistia", {
    videoFoam: false,
    fullscreenButton : false,
    playButton:false,
    smallPlayButton:false,
    playbar: false,
    allowSkip:false,
    controlsVisibleOnLoad:false,
    volumeControl:false
});


function stoper() {
    wistiaEmbed.time(timewistia);
    wistiaEmbed.pause();
}
function player(){
    wistiaEmbed.time(timewistia);
    wistiaEmbed.pause();
}
function detenerwistia(){
    wistiaEmbed.pause();
    localStorage.setItem("status", false);
    wistiaEmbed.bind("play",function(){
        wistiaEmbed.time(timewistia);
        wistiaEmbed.pause();
    });
    wistiaEmbed.unbind("pause",player());
}

function iniciarwistia(){
    wistiaEmbed.unbind("play",stoper());
    wistiaEmbed.play();
    localStorage.setItem("status", true);
    wistiaEmbed.bind("pause",function(){
        wistiaEmbed.time(timewistia);
        wistiaEmbed.play();
    });
}

wistiaEmbed.bind("end",function(){
    localStorage.setItem("timewistia", 0);
});
wistiaEmbed.bind("timechange", function() {
    timewistia = wistiaEmbed.time();
    localStorage.setItem("timewistia", timewistia);
});

function loadWistia() {
    if (statuswistia != "false") {
        iniciarwistia();
    }
    else {
        detenerwistia();
    }
}