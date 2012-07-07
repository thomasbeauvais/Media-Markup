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

        reloadAnnotations();
    } );

    function reloadAnnotations() {
        $('#include-from-outside').load('/audio/annotations');
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

<div id="indexFileList" class="bordered padded">
    <c:if test="${!empty indexFiles}">
        <c:forEach items="${indexFiles}" var="indexFile">
            <a href="javascript:void(0);" onclick="javascript:window.loadIndexFile('${ indexFile.name }','${ indexFile.id }');">
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

<div id='include-from-outside' class="asdf"></div>


</form>
</body>
</html>