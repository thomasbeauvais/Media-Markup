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
    <script src="resources/javascript/regionOverlay.js"></script>
    <script src="resources/javascript/selectionOverlay.js"></script>
    <script src="resources/javascript/waveForm.js"  type="text/javascript"></script>
    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
</head>

<script type="text/javascript" charset="utf-8">
//<![CDATA[
    $( window ).resize( function( e ) {
    } );

    $( document ).ready( function() {
        audioAnnotation = new AudioAnnotation( innerContainer );

        var myEventHandler = function() {
            reloadAnnotations();
        };

        document.addEventListener( "indexLoaded", myEventHandler, false);
    } );

    function saveAnnotation() {
        var text        = $('#include-from-outside #newAnnotationText').val();
        var idIndexFile = this.audioAnnotation.idIndexFile;
        var startX      = this.audioAnnotation.selectionOverlay.startX;
        var endX        = this.audioAnnotation.selectionOverlay.endX;

        startX          = this.audioAnnotation.transform( startX );
        endX            = this.audioAnnotation.transform( endX );

        console.log( "**** adding comment for file=" + idIndexFile + " region(" + startX + "," + endX + ") with text=" + text );

        $.post(
            'annotations/add',
            {
              idIndexFile: idIndexFile,
              text: text,
              startX: startX,
              endX: endX
            })
        .success( function() { reloadAnnotations(); } )
        .error( function( e ) {
            console.log( e );
        } );
    }

    function reloadAnnotations() {
        $('#include-from-outside').load('/audio/annotations?idIndexSummary=' + this.audioAnnotation.idIndexFile,
            function() {
                $('#include-from-outside #saveAnnotation').click(
                    function() {
                        saveAnnotation();
                    }
                );
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

        this.audioAnnotation.loadIndexFile( idIndexFile );
    }

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
</div>

<div class="container bordered margined">
    <div id="innerContainer" class="inner-container">
    </div>
</div>

<div id='include-from-outside'></div>

</body>
</html>