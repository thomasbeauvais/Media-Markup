// For now we are going to have the window create the canvas and pass it in
function AudioAnnotation( parent ) {
    this.waveformCanvas                 = new Waveform( parent );
    this.selectionOverlay               = new SelectionOverlay( parent );

    this.idIndexFile                    =  null;

    this.onSamplesReceived = function( data ) {
        // data.samples are the values of the waveform starting from a center
        console.log( "data received for: " + this.idIndexFile );

        this.waveformCanvas.drawWaveform( data.samples );
        this.selectionOverlay.selectionEnabled( true );
    }

    this.loadIndexFile = function( idIndexFile ) {
        if( this.idIndexFile == idIndexFile ) {
            console.log( "already loaded: " + idIndexFile );
            return;
        }

        this.selectionOverlay.clearSelection();
        this.waveformCanvas.resetCanvas();

        this.idIndexFile        = idIndexFile;

        console.log( "loading waveform: " + idIndexFile );

        var self = this;

        $.getJSON( "visualData",
            {
                idIndexFile: encodeURI( idIndexFile ),
                width: waveform.width,
                height: waveform.height
            },
            function( data ) {
                self.onSamplesReceived( data );
            }
        );
    }
}