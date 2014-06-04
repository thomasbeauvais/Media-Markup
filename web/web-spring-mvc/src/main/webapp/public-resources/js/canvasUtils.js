function cleanCanvas( canvas )  {
    var context             = canvas.getContext("2d");

    var height              = canvas.height;
    var width               = canvas.width;

    context.clearRect ( 0 , 0, width, height );
};

function fireDocumentEvent( type, data ) {
    // console.log( "firing document event: " + type );

    // Dispatch the event letting everyone know
    var evt = document.createEvent("Event");
    evt.initEvent( type, true, true );
    evt.data = data;
    document.dispatchEvent( evt );
};