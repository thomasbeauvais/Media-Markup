function cleanCanvas( canvas )  {
    var context             = canvas.getContext("2d");

    var height              = canvas.height;
    var width               = canvas.width;

    context.clearRect ( 0 , 0, width, height );
}