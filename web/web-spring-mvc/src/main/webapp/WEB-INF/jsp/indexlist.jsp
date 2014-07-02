<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<link rel="stylesheet" type="text/css" href="resources/css/audio-annotation.css"/>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

<head>
    <script src="resources/javascript/jquery-latest.min.js"></script>
    <script src="resources/javascript/canvasUtils.js"></script>
</head>

<script type="text/javascript" charset="utf-8">
    $( document ).ready( function() {
    } );
</script>

<div id="indexFileList">
    <c:if test="${!empty indexFiles}">
        <c:forEach items="${indexFiles}" var="indexFile">
            <a href="javascript:void(0);" onclick="javascript:window.loadIndexFile('${ indexFile.name }','${ indexFile.uid }');">
                <div class="index-file-list-element padded margined">
                    <span>${indexFile.name}</span>
                </div>
            </a>
        </c:forEach>
    </c:if>
    <c:if test="${empty indexFiles}">
        You currently don't have access to any audio files
    </c:if>
</div>
</html>