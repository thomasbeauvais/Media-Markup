// For now we are going to have the window create the canvas and pass it in
function Waveform( parent ) {
    this.waveformCanvas                 = document.createElement( 'canvas' );
    this.waveformCanvas.id              = "waveform";

    this.waveformCanvas.parent          = this;

    this.idIndexFile                    =  null;

   // Add it to the container..
    parent.appendChild( this.waveformCanvas );

    // Need to set the width after it's been added to the screen
    // The canvas width was the coordinate system with (default is 150 X 300 )
    this.waveformCanvas.width           = this.waveformCanvas.offsetWidth;
    this.waveformCanvas.height          = this.waveformCanvas.offsetHeight;

    this.drawWaveformSamples = function( samples ) {
        this.resetCanvas();

        var canvas              = this.waveformCanvas;

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

    this.resetCanvas = function() {
        this.resizeCanvas();

        var canvas              = this.waveformCanvas;

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

    this.resizeCanvas = function() {
        var canvas          = this.waveformCanvas;

        canvas.width        = canvas.clientWidth;
        canvas.height       = canvas.clientHeight;
    }

    this.resetCanvas();
}