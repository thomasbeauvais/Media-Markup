// For now we are going to have the window create the canvas and pass it in
function AudioControls( parent ) {
    this.bytesLoadedCanvas              = document.createElement( 'canvas' );
    this.bytesLoadedCanvas.id           = "bytesLoadedCanvas";
    this.bytesLoadedCanvas.parent       = this;

    this.bytesPlayedCanvas               = document.createElement( 'canvas' );
    this.bytesPlayedCanvas.id            = "bytesPlayedCanvas";
    this.bytesPlayedCanvas.parent        = this;

    // Add it to the container..
    //parent.appendChild( this.bytesLoadedCanvas );
    parent.appendChild( this.bytesPlayedCanvas );

    // Need to set the width after it's been added to the screen
    // The canvas width was the coordinate system with (default is 150 X 300 )
    this.bytesLoadedCanvas.width        = this.bytesLoadedCanvas.offsetWidth;
    this.bytesLoadedCanvas.height       = this.bytesLoadedCanvas.offsetHeight;
    this.bytesPlayedCanvas.width         = this.bytesPlayedCanvas.offsetWidth;
    this.bytesPlayedCanvas.height        = this.bytesPlayedCanvas.offsetHeight;

    var self = this;

    this.updateLoaded = function( sound ) {
        var canvas              = self.bytesLoadedCanvas;

        cleanCanvas( canvas );
        var context             = canvas.getContext( '2d' );

        context.strokeStyle     = "rgba( 255, 0, 0, 0.2 )"
        context.fillStyle       = "rgba( 255, 0, 0, 0.2 )"

        var percentLoaded       = sound.bytesLoaded / sound.bytesTotal;

        var height              = canvas.height;
        var x                   = canvas.width * percentLoaded;

        context.fillStyle       = "rgba( 255, 0, 0, 0.2 )";

        context.fillRect( 0, 0, x, height );
        context.fill();
    }

    this.updatePlayed = function( sound ) {
        var canvas              = self.bytesPlayedCanvas;

        cleanCanvas( canvas );
        var context             = canvas.getContext( '2d' );

        context.strokeStyle     = "rgba( 255, 0, 0, 0.2 )"
        context.fillStyle       = "rgba( 255, 0, 0, 0.2 )"

        var percentPlayed       = sound.position / sound.bytesTotal;

        var height              = canvas.height;
        var x                   = canvas.width * percentPlayed;

        context.fillStyle       = "rgba( 0, 0, 255, 0.8 )";

        context.fillRect( 0, 0, x, height );
        context.fill();
    }
}