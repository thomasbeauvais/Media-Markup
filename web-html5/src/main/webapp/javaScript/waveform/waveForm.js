function resetCanvas( canvas ) {
    clearCanvas( canvas );
    resizeCanvas( canvas );
}
function clearCanvas( canvas ) {
    canvas.getContext("2d").clearRect ( 0 , 0, canvas.width, canvas.height );
}

function resizeCanvas( canvas ) {
    canvas.width        = canvas.clientWidth;
    canvas.height       = canvas.clientHeight;
}

function drawPlayedRegion( canvas, sound ) {
    resetCanvas( canvas );

    var context             = canvas.getContext("2d");

    var percentPlayed       = sound.position / sound.durationEstimate;

    var height              = canvas.height;
    var x                   = canvas.width * percentPlayed;

    context.fillStyle   = "#333";

    context.fillRect( x, 0, 1, height );
    context.fill();

    context.beginPath()
    context.moveTo( x - 10, height );
    context.lineTo( x, height - 20  );
    context.lineTo( x + 10, height  );
    context.lineTo( x -10, height  );
    context.fill();
    context.closePath()
}

function drawWaveForm( canvas, indexFile ) {
    resetCanvas( canvas );

    var context             = canvas.getContext("2d");

    var height              = canvas.height;
    var width               = canvas.width;

    var gradient = context.createLinearGradient(canvas.width/2, 0, canvas.width/2, canvas.height);
    gradient.addColorStop( 0, "#FFF" );
    gradient.addColorStop( 0.5, "#00F" );
    gradient.addColorStop( 1, "#FFF" );

    context.fillStyle       = gradient;

    context.fillRect( 0, 0, canvas.width, canvas.height );

    context.strokeStyle     = "rgb( 0, 0, 0 )"
    context.fillStyle       = "rgb( 0, 0, 0 )"

    if ( indexFile && indexFile.samples ) {
        var channel         = 0; // Only mono is supported
        var startX          = 0;

        var dataArray       = indexFile.samples;

        var step            = dataArray.length / width;
        var scale           = indexFile.maxes[ channel ] / height;

        var center          = Math.floor( height / 2 );

        context.moveTo( startX, center );

        for ( var i = 0; i < dataArray.length; i++, startX++ ) {
            var max = 0;
            var min = 0;

            for ( var s = 0; s < step && i < dataArray.length; s++, i++ ) {
                max         = Math.max( max, dataArray[i] );
                min         = Math.min( min, dataArray[i] );
            }

            var value       = ((max + (min * -1) ) / scale);
            var y           = value / 4;

            context.lineTo( startX, center - y );
            context.lineTo( startX, center + y );
        }

        context.fill();
    }
}

function drawLoadedRegion( canvas, sound ) {
    resetCanvas( canvas );

    var context             = canvas.getContext("2d");

    var percentLoaded       = sound.bytesLoaded / sound.bytesTotal;

    var height              = canvas.height;
    var width               = canvas.width * percentLoaded;

        context.fillStyle   = "rgba( 150, 0, 0, 0.1 )";

        context.fillRect( 0, 0, width, height );
}
