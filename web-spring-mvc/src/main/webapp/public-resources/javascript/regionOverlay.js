// For now we are going to have the window create the canvas and pass it in
function RegionOverlay( parent ) {
    this.regionCanvas                 = document.createElement( 'canvas' );
    this.regionCanvas.id              = "regionOverlay";

    // Add it to the container..
    parent.appendChild( this.regionCanvas );

    this.regionCanvas.parent = this;

    // Need to set the width after it's been added to the screen
    // The canvas width was the coordinate system with (default is 150 X 300 )
    this.regionCanvas.width           = this.regionCanvas.offsetWidth;
    this.regionCanvas.height          = this.regionCanvas.offsetHeight;

    var self = this;

    this.clear = function () {
        cleanCanvas( self.regionCanvas );
    };

    /**
     * Region data will be an array of objects that have:
     * - parentUid: the uid of the comment or whatever that the region corresponds to
     * - startX:    the x value of the start of the region
     * - endX:      the x value of the end of the region
     */
    this.drawWaveformRegions = function( regions ) {
        self.clear();
        self.regions = regions;

        var canvas  = self.regionCanvas;
        var width   = canvas.width;
        var height  = canvas.height;

        var context                 = canvas.getContext( "2d" );

        if ( regions ) {
            for ( var x = 0; x < regions.length; x++ ) {
                var region          = regions[ x ];

//                console.log( "*** Drawing region( " + region.parentUid + ") at (" + region.startX + ", " + region.endX + ")" );

                if ( region == self.selected ) {
                    context.strokeRect( region.startX, 0, region.endX - region.startX, height );
                }

                if ( region == self.rollover || region == self.selected ) {
                    context.fillStyle       = "rgba( 255, 165, 0, 0.5 )";
                } else {
                    context.fillStyle       = "rgba( 255, 165, 0, 0.2 )";
                }

                context.fillRect( region.startX, 0, region.endX - region.startX, height );

//                    context.strokeStyle = "rgb( 255, 255, 255 )";
//                    context.rect( region.startX, 0, region.endX - region.startX, height );
//                    context.stroke();
//                }
            }
        }
    };

    this.mouseout = function (event) {
        self.rollover = null;
        self.drawWaveformRegions( self.regions );
    };

    this.mousemove = function (event) {
        var curr = null;
        if ( self.rollover && testRegion( self.rollover, event ) ) {
            curr = self.rollover;
        }

        var mousedRegion = self.mousedRegion(  curr, event );

        if ( mousedRegion != self.rollover ) {
            self.rollover = mousedRegion;

//            console.log( "*** region now rolled over: " + ( self.rollover ? self.rollover.parentUid : "null" ) );

            self.drawWaveformRegions( self.regions );
        }
    }

    this.mousedRegion = function( curr, event ) {
        if ( self.regions ) {
            // There are a couple different use cases for selection of regions
            // One, you want to always select the last one if there is an overlay
            // Two, if there is an inclusive region, then you want to only show that
            // Three, is a 'what if' the use dropped into the overlapping region without having selected on

//            if ( self.rollover && !testRegion( self.rollover, event ) ) {
//                console.log( "*** nulling selected ");
//            } else if ( self.rollover && testRegion( self.rollover, event ) ) {
//            }


            // else, there is nothing selected
            for ( var x = 0; x < self.regions.length; x++ ) {
                var region          = self.regions[ x ];

                if ( testRegion( region, event ) && curr != region && ( curr == null || isBetterMatchThan( region, curr, event ) ) ) {
                    curr = region;
                }
            }

            return curr;
        }
    }

    this.mouseclick = function (event) {
        if ( self.rollover ) {
            self.selected = self.rollover;

            console.log( "*** region now selected: " + ( self.selected ? self.selected.parentUid : "null" ) );

            self.drawWaveformRegions( self.regions );
        }
    };

    this.mousedown = function (event) {
        if ( self.rollover ) {
//            console.log( "You have clicked region: " + self.rollover.parentUid );

            self.selected = self.rollover;

            console.log( "*** region now selected: " + ( self.selected ? self.selected.parentUid : "null" ) );

            self.drawWaveformRegions( self.regions );
        } else {
            console.log( "You didn't click a region!" );
        }

    };
};

function isBetterMatchThan( is, than, mouseEvent ) {
    if ( than == null ) {
        return true;
    }

    // Check to see if there is a better match
    // To be a better match it will be smaller than 'than'
    return is.startX > than.startX && is.endX < than.endX;
}

function testRegion( region, mouseEvent ) {
    return mouseEvent.layerX >= region.startX && mouseEvent.layerX <= region.endX;
}