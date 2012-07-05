function drawWaveForm( canvas, samples ) {
    resetCanvas( canvas );

    var context             = canvas.getContext("2d");

    var height              = canvas.height;
    var width               = canvas.width;

    context.strokeStyle     = "rgb( 0, 0, 0 )"
    context.fillStyle       = "rgb( 0, 0, 0 )"

    if ( samples ) {
        var center          = Math.floor( height / 2 );

        context.moveTo( 0, center );

        for ( var x = 0; x < samples.length; x++ ) {
            var value   = samples[ x ];
            var y       = value / 2;

            context.lineTo( x, center - y );
            context.lineTo( x, center + y );
        }

        context.fill();
    }
}

function resetCanvas( canvas ) {
    resizeCanvas( canvas );

    var context             = canvas.getContext("2d");

    var height              = canvas.height;
    var width               = canvas.width;

    context.clearRect ( 0 , 0, width, height );

    var gradient = context.createLinearGradient( width/2, 0, width/2, height );
    gradient.addColorStop( 0, "#FFF" );
    gradient.addColorStop( 0.5, "#00F" );
    gradient.addColorStop( 1, "#FFF" );

    context.fillStyle       = gradient;

    context.fillRect( 0, 0, width, height );
}

function resizeCanvas( canvas ) {
    canvas.width        = canvas.clientWidth;
    canvas.height       = canvas.clientHeight;
}