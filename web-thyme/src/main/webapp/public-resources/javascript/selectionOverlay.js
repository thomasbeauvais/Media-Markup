function SelectionOverlay( parent ) {
    this.overlayCanvas                 = document.createElement( 'canvas' );
//    this.overlayCanvas                 = canvas;
    this.overlayCanvas.id              = "selectionOverlay";

    this.overlayCanvas.addEventListener('mousedown', this.onMouseDown, false);
    this.overlayCanvas.addEventListener('mouseup', this.onMouseUp, false);
    this.overlayCanvas.addEventListener('mousemove', this.onMouseMove, false);
    this.overlayCanvas.addEventListener('click', this.onMouseClick, false);
    this.overlayCanvas.addEventListener('mouseout', this.onMouseOut, false);

    this.overlayCanvas.parent = this;

    this.startX = -1;
    this.isDragging = false;

   // Add it to the container..
    parent.appendChild( this.overlayCanvas );

    // Need to set the width after it's been added to the screen
    // The canvas width was the coordinate system with (default is 150 X 300 )
    this.overlayCanvas.width           = this.overlayCanvas.offsetWidth;
    this.overlayCanvas.height          = this.overlayCanvas.offsetHeight;

    this.clearSelection = function () {
        this.startX = -1;
        this.endX = -1;

        this.isDragging = false;

        cleanCanvas( this.overlayCanvas );
    }

}

SelectionOverlay.prototype.onMouseOut = function (event) {
    // This should only set it on the first time we escape if we are dragging!
    if ( this.parent.isDragging == false ) {
    } else if ( event.layerX < 0 ) {
        this.parent.paint( this.parent.startX, 0 );
    } else if ( event.layerX > this.width ) {
        this.parent.paint( this.parent.startX, this.width );
    }

    this.parent.isDragging = false;
};

SelectionOverlay.prototype.onMouseClick = function (event) {
};

SelectionOverlay.prototype.onMouseUp = function (event) {
    this.parent.isDragging = false;
    //this.parent.startX = -1;
};

SelectionOverlay.prototype.onMouseDown = function (event) {
    this.parent.isDragging = true;
    this.parent.startX = event.layerX;
};

SelectionOverlay.prototype.onMouseMove = function (event) {
    if ( this.parent.isDragging == false ) {
        return;
    }

    this.parent.paint( this.parent.startX, event.layerX );
}

SelectionOverlay.prototype.paint = function( startX, endX ) {
    // Sync these to make sure that anyone who asks from the selected region can retrieve it..
    this.startX = startX;
    this.endX   = endX;

    var x       = Math.min( startX, endX );
    var width   = Math.max( startX, endX ) - x;
    var height  = this.overlayCanvas.height;

    var context             = this.overlayCanvas.getContext( "2d" );
        context.clearRect ( 0 , 0, this.overlayCanvas.width, this.overlayCanvas.height );
        context.fillStyle   = "rgba(255, 0, 0, 0.2)";
        context.fillRect( x, 0, width, height );

        //console.log( "height=" + height + ", endX=" + endX + ", x=" + x + ", width=" + width + ",canvas.width=" + this.overlayCanvas.width );
};
