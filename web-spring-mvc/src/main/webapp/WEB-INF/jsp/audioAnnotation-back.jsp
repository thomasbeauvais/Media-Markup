<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="css/styles.css"></link>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Existing Events</title>

</head>

<body>
<h1>Existing Events</h1>
<c:url var="addEventUrl" value="/audioAnnotation/add.html" />
<a href="${addEventUrl}">Add Event</a>

<br /><br />
<c:if test="${!empty indexFiles}">
	<table class="table">
		<tr>
			<th>ID</th>
			<th>Title</th>
			<th>Date</th>
		</tr>
		
		<c:forEach items="${indexFiles}" var="indexFile">
		<tr>
			<td><c:out value="${indexFile.id}" /></td>
			<td><c:out value="${indexFile.name}" /></td>
		</tr>
		</c:forEach>
	</table>
</c:if>
<c:if test="${empty indexFiles}">
	You currently don't have access to any audio files
</c:if>
</body>
</html>

