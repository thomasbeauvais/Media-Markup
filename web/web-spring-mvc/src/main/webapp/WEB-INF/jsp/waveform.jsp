<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Document</title>

    <script type="application/javascript" src="/resources/javascript/jquery.js"></script>
    <script type="application/javascript" src="/resources/javascript/jquery-ui.js"></script>
    <%--<script type="application/javascript" src="/resources/javascript/waveform.js"></script>--%>
    <script type="application/javascript" src="/resources/javascript/waveform-old.js"></script>
    <script type="application/javascript">
        var index = "<%= request.getParameter("index") %>";
        var zoom = 1;
        var waveform;
        var data;

        function loadSamples()
        {
            var params = {
                index: encodeURI(index),
                width: $("#waveform-canvas").width()
            };

            $.getJSON("/visual", params)
                    .success(onSamplesReceived)
                    .fail(onError);
        }

        function onError(data)
        {
            console.log("error:" + data);
        }

        function onSamplesReceived(data)
        {
            $("#waveform-canvas").data("waveform").drawWaveformSamples(data.samples);

//            waveform = new Waveform({
//                container: document.getElementById("waveform"),
//                width: data.samples.length,
//                data: data.samples
//            });
        }

        $(function ()
        {
            $("#waveform-canvas").waveform();

            if (index)
            {
                loadSamples();
            }


//            $('#waveform').mousewheel(function (event){
//                if (event.deltaY > 0)
//                {
//                    zoomIn();
//                }
//            });
        });

    </script>
</head>
<body>

<div id="waveform" style="height: 360; overflow-x:scroll;">
    <canvas id="waveform-canvas" style="width: 100%; height: 100%;"></canvas>
</div>
</body>
</html>