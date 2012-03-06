<!DOCTYPE html>
<meta charset="UTF-8"/>

<title>Test</title>

<link rel="stylesheet" type="text/css" href="css/audio.css">


<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="javaScript/jQuery/jquery.mousewheel.js"></script>
<script src="javaScript/waveform/waveForm.js"></script>
<script src="javaScript/waveform/selectionOverlay.js"></script>
<script src="javaScript/soundmanager/soundmanager2-nodebug-jsmin.js"></script>
<script>
    //            function onPause( ) {
    //                soundManager.pause( "testcase" );
    //            }
    //
    //            function onPlay( ) {
    //                soundManager.play( "testcase" );
    //            }
    //
    //            soundManager.onready(
    //                function() {
    //                    var sound = soundManager.createSound({
    //                        id:'testcase',
    //                        url:'audio/',
    //                        whileloading: function() {
    ////                            console.log( 'sound '+this.sID+' loading, '+this.bytesLoaded+' of '+this.bytesTotal);
    //
    //                            var canvas = document.getElementById("bytesLoaded");
    //                            drawLoadedRegion( canvas, this ) ;
    //                        },
    //                        whileplaying: function() {
    ////                            console.log( 'Peaks, position/bytes: '+this.position+'/'+this.bytesTotal);
    //
    //                            var canvas = document.getElementById("bytesPlayed");
    //                            drawPlayedRegion( canvas, this ) ;
    //                        }
    //                    });
    //
    //                    sound.play();
    //                }
    //            );
    //
    //    var waveFormObject = null;
    //
    //    // You know this one already, right?
    //    $( window ).resize( function( e ) {
    //        // do something when the window resizes
    //        if( waveFormObject ) {
    //            drawWaveForm( waveForm, waveFormObject );
    //        }
    //
    //    } );
    //    $( document ).ready( function() {
    //        var selectionOverlay = new SelectionOverlay();
    //
    //        $.getJSON( "persistence/", function( indexFile ) {
    //            drawWaveForm( waveForm, waveFormObject = indexFile );
    //        } );
    //    } );

    var zoom    = 1;
    var factor  = 2;

    var img     = new Image();
//        img.src = 'http://www.nasa.gov/images/content/312934main_image_1283-946.jpg';
        img.src = "original.jpg";

    // using the event helper
    img.onload = function() {
        var canvas      = $( "#myCanvas" ).get( 0 );

        canvas.width    = $( window ).width();
        canvas.height   = img.height;

        var context = canvas.getContext( '2d' );
            context.drawImage( this, 0, 0, canvas.width, canvas.height );

        $( "#myCanvas" ).mousewheel( function( event, delta, aaa, asdf ) {
            var out = delta < 0;

            console.log( "zooming " + ( out? "out" : "in" ) );

            var context = this.getContext( '2d' );

            if ( out && zoom > 1 ) {
                zoom /= factor;

            } else if ( !out && zoom < 1000 ) {
                // in
                zoom *= factor;
            }

            console.log( zoom );

            context.drawImage(
                    img,
                    0,
                    0,
                    img.width / zoom,
                    img.height,
                    0,
                    0,
                    this.width,
                    this.height
            );
        } );
    };
</script>

<canvas id="myCanvas"></canvas>

<%--<canvas id="waveForm" width="100%" height="auto"></canvas>--%>
<%--<canvas id="bytesLoaded" width="100%" height="auto"></canvas>--%>
<%--<canvas id="bytesPlayed" width="100%" height="auto"></canvas>--%>
