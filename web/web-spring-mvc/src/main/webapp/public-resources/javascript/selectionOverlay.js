function SelectionOverlay( parent ) {
    this.overlayCanvas                 = document.createElement( 'canvas' );
    this.overlayCanvas.id              = "selectionOverlay";

    this.idIndexFile = null;
    this.startX = -1;
    this.isDragging = false;
    this.isSelectionEnabled = false;

    // Add it to the container..
    parent.appendChild( this.overlayCanvas );

    this.overlayCanvas.parent = this;

    // Need to set the width after it's been added to the screen
    // The canvas width was the coordinate system with (default is 150 X 300 )
    this.overlayCanvas.width           = this.overlayCanvas.offsetWidth;
    this.overlayCanvas.height          = this.overlayCanvas.offsetHeight;

    var self = this;

    this.selectionEnabled = function( isEnabled ) {
        self.isSelectionEnabled = isEnabled;
    }

    this.clear = function () {
        self.startX = -1;
        self.endX = -1;

        self.isDragging = false;

        cleanCanvas( self.overlayCanvas );
    }

    this.mouseout = function (event) {
        // This should only set it on the first time we escape if we are dragging!
        if ( self.isDragging == false ) {
        } else if ( event.layerX < 0 ) {
            self.paint( self.startX, 0 );
        } else if ( event.layerX > self.width ) {
            self.paint( self.startX, self.width );
        }

        self.isDragging = false;
    };

    this.mouseclick = function (event) {
    };

    this.mouseup= function (event) {
        self.isDragging = false;
    };

    this.mousedown = function (event) {
        // TODO:  When nothing is loaded don't allow selection
        if ( !self.isSelectionEnabled ) {
            return;
        }

        // 'this' is the HTMLCanvas 'overlayCanvas'
        self.isDragging = true;
        self.startX = event.layerX;
    };

    this.mousemove = function (event) {
        if ( self.isDragging == false ) {
            return;
        }

        self.paint( self.startX, event.layerX );
    };

    this.paint = function( startX, endX ) {
        var canvas  = self.overlayCanvas;
        // Sync these to make sure that anyone who asks from the selected region can retrieve it..
        this.startX = startX;
        this.endX   = endX;

        var x       = Math.min( startX, endX );
        var width   = Math.max( startX, endX ) - x;
        var height  = canvas.height;

        var context             = canvas.getContext( "2d" );
            context.clearRect ( 0 , 0, canvas.width, canvas.height );
            context.fillStyle   = "rgba(255, 0, 0, 0.2)";
            context.fillRect( x, 0, width, height );

            //console.log( "height=" + height + ", endX=" + endX + ", x=" + x + ", width=" + width + ",canvas.width=" + this.overlayCanvas.width );
    };
};