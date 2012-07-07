// For now we are going to have the window create the canvas and pass it in
function ClazzA() {
    this.idCurrentIndexFile = null;
    this.onSamplesReceived = function( data ) {
        // data.samples are the values of the waveform starting from a center
        console.log( "We have some data!" );

        this.drawWaveform( data.samples );
    };

    this.loadIndexFile = function( idIndexFile ) {
        this.idCurrentIndexFile = idIndexFile;

        console.log( "loading waveform: " + idIndexFile );

        // TODO:  If there are previous samples, then scale before loading

//        $.ajax({
//            url : "../visualData",
//            data :{
//                      idIndexFile: encodeURI( idIndexFile ),
//                      width: 100,
//                      height: 100
//                  },
//            dataType : 'json',
//            context : this,
//            complete : function (data) {
//                // 'this' will be what you passed as context
//                this.onSamplesReceived( data );
//            }
//        });

        var self = this;

        $.getJSON( "../visualData",
            {
                idIndexFile: encodeURI( idIndexFile ),
                width: 100,
                height: 100
            },
            function ( data ) {
                self.onSamplesReceived( data );
            }
        );
    };

    this.drawWaveform = function( samples ) {
        console.log( "drawing samples!" );

        console.log( samples );
    };
}