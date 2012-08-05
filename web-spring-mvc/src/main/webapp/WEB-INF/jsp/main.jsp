<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<link rel="stylesheet" type="text/css" href="resources/css/audio-annotation.css"/>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<head>
    <title>Welcome to AudioAnnotation</title>

    <script src="resources/javascript/canvasUtils.js"></script>
    <script src="resources/javascript/audioAnnotation.js"></script>
    <script src="resources/javascript/audioControls.js"></script>
    <script src="resources/javascript/regionOverlay.js"></script>
    <script src="resources/javascript/selectionOverlay.js"></script>
    <script src="resources/javascript/waveForm.js"  type="text/javascript"></script>
    <script src="soundmanager/soundmanager2-nodebug-jsmin.js"></script>
    <script src="resources/javascript/jquery-latest.min.js"></script>
    <!--<script src="http://code.jquery.com/jquery-latest.min.js"></script>-->
</head>

<script type="text/javascript" charset="utf-8">
//<![CDATA[
    $( window ).resize( function( e ) {
    } );

    $( document ).ready( function() {
        audioAnnotation = new AudioAnnotation( innerContainer );

        audioControls   = new AudioControls( controls );

        var myEventHandler = function() {
            reloadAnnotations();
        };

        document.addEventListener( "indexLoaded", myEventHandler, false);
    } );

    function saveAnnotation( save ) {
        var text        = $('#include-from-outside #newAnnotationText').val();
        var idIndexFile = this.audioAnnotation.idIndexFile;
        var startX      = this.audioAnnotation.selectionOverlay.startX;
        var endX        = this.audioAnnotation.selectionOverlay.endX;

        console.log( "**** actual region(" + startX + "," + endX + ")" );

        startX          = this.audioAnnotation.transform( startX );
        endX            = this.audioAnnotation.transform( endX );

        console.log( "**** adding comment for file=" + idIndexFile + " region(" + startX + "," + endX + ") with text=" + text );

        if ( !save ) { return; }

        $.post(
            'annotations/add',
            {
              idIndexFile: idIndexFile,
              text: text,
              startX: startX,
              endX: endX
            })
        .success( function() {
            reloadAnnotations();
            this.audioAnnotation.loadIndexFile( this.audioAnnotation.idIndexFile );
        } )
        .error( function( e ) {
            console.log( e );
        } );
    }

    function reloadAnnotations() {
        $('#include-from-outside').load('/audio/annotations?idIndexSummary=' + this.audioAnnotation.idIndexFile,
            function() {
                $('#include-from-outside #saveAnnotation').click( function() { saveAnnotation(true) } );
                $('#include-from-outside #simpleSave').click( function() { saveAnnotation(false) } );
            }
        );
    }

    function toggleList() {
        $('#indexFileList').slideToggle('slow', function() {
            // Animation complete.
        });
    }

    function loadIndexFile( nameIndexFile, idIndexFile ) {
        $('#currentIndexFile').text( "Current file... " +  nameIndexFile );

        // If there is already a sound.. kill it!
        stopAndDestroySound();

        this.audioAnnotation.loadIndexFile( idIndexFile );
        audioControls.clear();
    }

    function stopAndDestroySound() {
        if ( this.sound ) {
            this.sound.stop();
            soundManager.destroySound( this.sound.id );
            this.sound = null;
        }
    }

    function pause() {
        this.sound.togglePause();
    }

    function stop() {
        this.sound.stop();
    }

    function play() {
        if ( !this.audioAnnotation.idIndexFile ) {
            return;
        }

        var soundId = "sound-" + this.audioAnnotation.idIndexFile;
        if ( this.sound && this.sound.id != soundId ) {
            stopAndDestroySound();
        }

        this.sound = soundManager.createSound({
            id: soundId,
            url:'audioData?uidIndexFile=' + this.audioAnnotation.idIndexFile,
            whileloading: function() {
                console.log( 'sound '+this.sID+' loading, '+this.bytesLoaded+' of '+this.bytesTotal);

                audioControls.updateLoaded( this );
            },
            whileplaying: function() {
                // console.log( 'Peaks, position/bytes: '+this.position+'/'+this.bytesTotal);

                audioControls.updatePlayed( this );
            }
        });

        this.sound.play();
    }

    soundManager.setup({
        // location: path to SWF files, as needed (SWF file name is appended later.)
        url: '/audio/soundmanager/',
        onready: function() {

        },
        onbufferchange: function() {
            console.log('Buffering '+(this.isBuffering?'started':'stopped')+'.');
        }
    });
//]]>

</script>

<body>

<div id="actionsMenu" class="bordered padded margined"><a href="">Upload</a> | <a href="">Contact</a></div>

<div id="indexFileList" class="bordered padded">
    <c:if test="${!empty indexFiles}">
        <c:forEach items="${indexFiles}" var="indexFile">
            <a href="javascript:void(0);" onclick="javascript:window.loadIndexFile('${ indexFile.name }','${ indexFile.uid }');">
                <div class="index-file-list-element bordered padded margined">
                    <span>${indexFile.name}</span>
                </div>
            </a>
        </c:forEach>
    </c:if>
    <c:if test="${empty indexFiles}">
        You currently don't have access to any audio files
    </c:if>
</div>

<div id="currentIndexFile" class="bordered padded margined">
No file loaded..
</div>

<div class="bordered margined padded">
    <input id="toggleList" type="button" value="Toggle List" onclick="window.toggleList();"/>
    <input id="reloadAnnotations" type="button" value="Reload Annotations" onclick="window.reloadAnnotations();"/>
    <input id="playSound" type="button" value="Play" onclick="window.play();"/>
    <input id="pauseSound" type="button" value="Pause" onclick="window.pause();"/>
    <input id="stopSound" type="button" value="Stop" onclick="window.stop();"/>
</div>

<div class="container bordered margined">
    <div id="innerContainer" class="inner-container">
        <div id="controls" class="bordered"/>
    </div>
</div>

<div id='include-from-outside'></div>

</body>
</html>