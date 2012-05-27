function SelectionOverlay() {
    this.overlayCanvas                 = document.createElement( 'canvas' );
    this.overlayCanvas.id              = "selectionOverlay";

    this.overlayCanvas.addEventListener('mousedown', this.onMouseDown, false);
    this.overlayCanvas.addEventListener('mouseup', this.onMouseUp, false);
    this.overlayCanvas.addEventListener('mousemove', this.onMouseMove, false);

    this.overlayCanvas.parent = this;

    this.startX = -1;

   // Add it to the container..
    document.body.appendChild( this.overlayCanvas );

    // Need to set the width after it's been added to the screen
    // The canvas width was the coordinate system with (default is 150 X 300 )
    this.overlayCanvas.width           = this.overlayCanvas.offsetWidth;
    this.overlayCanvas.height          = this.overlayCanvas.offsetHeight;

}

SelectionOverlay.prototype.onMouseUp = function (event) {
    this.parent.startX = -1;
};

SelectionOverlay.prototype.onMouseDown = function (event) {
    this.parent.startX = event.clientX;
};

SelectionOverlay.prototype.onMouseMove = function (event) {
    if ( this.parent.startX < 0 ) {
        return;
    }

    var x       = Math.min( this.parent.startX, event.clientX );
    var width   = Math.max( this.parent.startX, event.clientX ) - x;
    var height  = this.height;

    var context             = this.getContext( "2d" );
        context.clearRect ( 0 , 0, this.width, this.height );
        context.fillStyle   = "rgba(255, 0, 0, 0.2)";
        context.fillRect( x, 0, width, height );

    if ( document.debug == true ) {
        console.log( "clientX=" + event.clientX + ", x=" + x + ", width=" + width + ",canvas.width=" + this.width );
    }
};
