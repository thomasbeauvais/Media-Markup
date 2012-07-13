// For now we are going to have the window create the canvas and pass it in
function RegionOverlay( parent ) {
    this.regionCanvas                 = document.createElement( 'canvas' );
    this.regionCanvas.id              = "regionOverlay";

//    this.regionCanvas.addEventListener('mousedown', this.onMouseDown, false);
//    this.regionCanvas.addEventListener('mouseup', this.onMouseUp, false);
//    this.regionCanvas.addEventListener('mousemove', this.onMouseMove, false);
//    this.regionCanvas.addEventListener('click', this.onMouseClick, false);
//    this.regionCanvas.addEventListener('mouseout', this.onMouseOut, false);

    // Add it to the container..
    parent.appendChild( this.regionCanvas );

    this.regionCanvas.parent = this;

    // Need to set the width after it's been added to the screen
    // The canvas width was the coordinate system with (default is 150 X 300 )
    this.regionCanvas.width           = this.regionCanvas.offsetWidth;
    this.regionCanvas.height          = this.regionCanvas.offsetHeight;

    this.clear = function () {
        cleanCanvas( this.regionCanvas );
    };

    /**
     * Region data will be an array of objects that have:
     * - parentUid: the uid of the comment or whatever that the region corresponds to
     * - startX:    the x value of the start of the region
     * - endX:      the x value of the end of the region
     */
    this.drawWaveformRegions = function( regions ) {
        var canvas  = this.regionCanvas;
        var width   = canvas.width;
        var height  = canvas.height;

        var context             = canvas.getContext( "2d" );

        if ( regions ) {
            for ( var x = 0; x < regions.length; x++ ) {
                var region          = regions[ x ];

                context.fillStyle   = "rgba( 255, 165, 0, 0.2 )";
                context.fillRect( region.startX, 0, region.endX, height );
            }
        }
    };

    this.mouseout = function (event) {
    };

    this.mouseclick = function (event) {
    };

    this.mousedown = function (event) {
        console.log( "Hola, you clicked the regions!" );
    };
};