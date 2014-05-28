<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Document</title>
    <script type="application/javascript" src="/resources/javascript/jquery.js"></script>
    <script type="application/javascript" src="/resources/javascript/waveform.js"></script>
    <script type="application/javascript">
        $(document).ready(function ()
        {
            var index = "<%= request.getParameter("index") %>";
            if (index)
            {
                var params = {
                    index: encodeURI(index),
                    width: $("#waveform").width(),
                    height: $("#waveform").height()
                };

                $.getJSON("/visual", params)
                        .success(onSamplesReceived)
                        .fail(onError);
            }
        });

        function onError(data)
        {
            console.log("error:" + data);
        }

        function onSamplesReceived(data)
        {
            var waveform = new Waveform(
                    {
                        container: document.getElementById("waveform"),
                        data: data.samples,
                        interpolate: true
                    });

        }

    </script>
</head>
<body>

<div id="waveform" style="height: 600">
</div>

</body>
</html>