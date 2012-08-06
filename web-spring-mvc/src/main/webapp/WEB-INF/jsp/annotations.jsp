<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<link rel="stylesheet" type="text/css" href="resources/css/audio-annotation.css"/>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<head>
    <title>Welcome to AudioAnnotation</title>

    <script src="http://code.jquery.com/jquery-latest.min.js"></script>
    <script src="resources/javascript/canvasUtils.js"></script>
</head>

<script type="text/javascript" charset="utf-8">
    $( document ).ready( function() {
        $('.annotationElement').hover(
            function(){
                // console.log("fuck in");

            },
            function(){
                // console.log("fuck out");
            }
        );

        document.addEventListener( "annotationSelected", onAnnotationsSelected, false );
        document.addEventListener( "annotationRollover", onAnnotationsRollover, false );
    } );

    function onAnnotationsSelected( event ) {
        selectedAnnotation( event.data.uid );
    }

    function onAnnotationsRollover( event ) {
        rolloverAnnotation( event.data.uid );
    }

    function fireAnnotationRollover( uid ) {
        fireDocumentEvent( "annotationRollover", { uid : uid } );
    }

    function fireAnnotationSelected( uid ) {
        fireDocumentEvent( "annotationSelected", { uid : uid } );
    }

    var prevSelected = null;
    var prevRollover = null;

    function selectedAnnotation( uid ) {
        var clazz = "annotation-selected-element";

        if ( prevSelected ) {
            prevSelected.removeClass( clazz );
        }

        if ( uid ) {
            var annotationId = '#annotation-' + uid;

            prevSelected = $( annotationId );
            prevSelected.addClass( clazz );
        }
    }

    function rolloverAnnotation( uid ) {
        var clazz = "annotation-rollover-element";

        if ( prevRollover ) {
            prevRollover.removeClass( clazz );
        }

        if ( uid ) {
            var annotationId = '#annotation-' + uid;

            prevRollover = $( annotationId );
            prevRollover.addClass( clazz );
        }
    }

    function showComment() {
        $( '#newComment' ).slideDown();
    }

    function newCommentHtml() {

    }

</script>

<body>

<div class="bordered margined padded">
    <input id="showComment" type="button" value="New Comment" onclick="window.showComment();"/>
    <input id="simpleSave" type="button" value="Simple Save (dev)" onclick="window.showComment();"/>
</div>

<div id="newComment" class="bordered margined padded">
    <input id="newAnnotationText" type="textarea" id="text" name="text" />
    <input id="saveAnnotation" type="button" value="Save"/>
</div>

<div id="annotationsList" class="bordered padded">
    <c:if test="${!empty annotations}">
        <c:forEach items="${annotations}" var="annotation">
            <div id='annotation-${ annotation.uid }'
            class="annotation-element bordered margined"
            onmouseout="fireAnnotationRollover(null);"
            onclick="fireAnnotationSelected('${ annotation.uid }');"
            onmouseover="fireAnnotationRollover('${ annotation.uid }');"');">${annotation.text}<span class="add-comment">+</span></div>
        </c:forEach>
    </c:if>
    <c:if test="${empty annotations}">
        There are no annotations for this file
    </c:if>
</div>

</body>
</html>