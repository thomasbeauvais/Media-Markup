<!DOCTYPE html>
<html lang="en">
    <head>
        <title>Test</title>

        <link rel="stylesheet" type="text/css" href="css/audio.css">

        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
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

            var waveFormObject = null;

            // You know this one already, right?
            $(window).resize(function(e){
                // do something when the window resizes
                if ( waveFormObject ) {
                    drawWaveForm( waveForm, waveFormObject );
                }

            });
            $(document).ready(function() {
                var selectionOverlay = new SelectionOverlay();

                $.getJSON( "persistence/", function( indexFile ) {
                    drawWaveForm( waveForm, waveFormObject = indexFile );
                });
            });
        </script>
    </head>
    <body>
        <canvas id="waveForm" width="100%" height="auto"></canvas>
        <canvas id="bytesLoaded" width="100%" height="auto"></canvas>
        <canvas id="bytesPlayed" width="100%" height="auto"></canvas>
    </body>

</html>