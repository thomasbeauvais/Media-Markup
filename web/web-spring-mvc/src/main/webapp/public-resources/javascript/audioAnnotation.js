// For now we are going to have the window create the canvas and pass it in
function AudioAnnotation( parent ) {
    this.waveformCanvas                 = new Waveform( parent );
    this.regionOverlay                  = new RegionOverlay( parent );
    this.selectionOverlay               = new SelectionOverlay( parent );

    this.idIndexFile                    =  null;

    var self = this;

    this.rolloverAnnotation = function( uid ) {
        self.regionOverlay.rolloverAnnotation( uid );
    };

    this.selectAnnotation = function( uid ) {
        self.regionOverlay.selectAnnotation( uid );
    };

    this.transform = function( x ) {
        if ( self.currentData == null ) {
            return -1;
        }

        return self.currentData.positions[ x ];
    }

    this.onSamplesReceived = function( data ) {
        // data.samples are the values of the waveform starting from a center
        console.log( "data received for: " + self.idIndexFile );

        this.currentData = data;

        this.waveformCanvas.drawWaveformSamples( data.samples );
        this.regionOverlay.drawWaveformRegions( data.regions );
        this.selectionOverlay.selectionEnabled( true );
    }

    this.reload = function() {
        this.loadOrReloadIndexFile( this.idIndexFile, true );
    };

    this.loadIndexFile = function( idIndexFile ) {
        this.loadOrReloadIndexFile( idIndexFile, false );
    };

    this.loadOrReloadIndexFile = function( idIndexFile, reload ) {
        if( this.idIndexFile == idIndexFile && !reload ) {
            console.log( "already loaded: " + idIndexFile );
            return;
        }

        this.regionOverlay.clear();
        this.selectionOverlay.clear();
        this.waveformCanvas.resetCanvas();

        this.idIndexFile        = idIndexFile;

        console.log( "loading waveform: " + idIndexFile );

        fireDocumentEvent( "indexLoaded", { uid : idIndexFile } );

        var self = this;

        $.getJSON( "visualData",
            {
                idIndexFile: encodeURI( idIndexFile ),
                width: waveform.width,
                height: waveform.height
            }
        ).success(
            function( data ) {
                self.onSamplesReceived( data );
            })
         .error(
            function(data) {
                console.log("error:" + data);
            });
    }

    this.mouseEventOverlay                 = document.createElement( 'canvas' );
    this.mouseEventOverlay.id              = "mouseEventOverlay";

    var mouseFunction = function( event ) {
        if ( self.selectionOverlay ) {
            var fn = self.selectionOverlay[ event.type ]
            if(typeof fn === 'function') {
                fn( event );
            }
        }

        if ( self.regionOverlay )  {
            var fn = self.regionOverlay[ event.type ]
            if(typeof fn === 'function') {
                fn( event );
            }
        }
    }

    this.mouseEventOverlay.addEventListener('mousedown',    mouseFunction, false);
    this.mouseEventOverlay.addEventListener('mouseup',      mouseFunction, false);
    this.mouseEventOverlay.addEventListener('mousemove',    mouseFunction, false);
    this.mouseEventOverlay.addEventListener('click',        mouseFunction, false);
    this.mouseEventOverlay.addEventListener('mouseout',     mouseFunction, false);

    // Add it to the container..
    parent.appendChild( this.mouseEventOverlay );
};