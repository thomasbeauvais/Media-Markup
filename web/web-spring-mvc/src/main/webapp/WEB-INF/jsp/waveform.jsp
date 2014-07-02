<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Document</title>

    <link rel="stylesheet" type="text/css" href="/resources/css/application.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.css"/>

    <script type="application/javascript" src="/resources/js/jquery.js"></script>
    <script type="application/javascript" src="/resources/js/jquery.mousewheel.js"></script>
    <script type="application/javascript" src="/resources/js/application.js"></script>
    <script type="application/javascript" src="/resources/js/jquery-ui.js"></script>
    <script type="application/javascript" src="/resources/js/selection.js"></script>
    <script type="application/javascript" src="/resources/js/waveform.js"></script>

    <script type="application/javascript" src="/resources/js/bootstrap.js"></script>
    <script type="application/javascript">
        var index = "<%= request.getParameter("index") %>";
        var currentData;
        var log;

        function loadSamples()
        {
            var params = {
                index: encodeURI(index),
                width: Math.floor($("#waveform-canvas").width())
            };

            $.getJSON("/visual", params)
                    .success(onSamplesReceived)
                    .fail(onError);

            $("#waveform-select").selection().selectionEnabled(true);
        }

        function onError(data)
        {
            console.log("error:" + data);
        }

        function onSamplesReceived(data)
        {
            currentData = data;

            $("#waveform-canvas").waveform().drawWaveformSamples(currentData.samples);

            console.log(currentData.regions);
        }

        function onAnnotationAdded(data)
        {
            console.log(data);
        }

        $(function ()
        {
            log = $(this).log("main");

            $("#waveform-canvas").waveform();

            $("#waveform-select").selection();

            $("#waveform-mouser").mouser($("#waveform-select").selection());

            $("#waveform").fit();

            if (index)
            {
                loadSamples();
            }
        });

        function addAnnotation()
        {
            var selection = $("#waveform-select").selection();

            $(log).html("adding annotation startX: " + selection.startX() + ", endX:" + selection.endX());

            var params = {
                index : encodeURI(index),
                startX: currentData.positions[selection.startX()],
                endX: currentData.positions[selection.endX()],
                text  : "testing"
            };

            $.getJSON("/annotation/save", params)
                    .success(onAnnotationAdded)
                    .fail(onError);

        }
    </script>
</head>
<body>

<div id="waveform" style="height: 360; overflow-x:scroll;">
    <canvas id="waveform-canvas"></canvas>
    <canvas id="waveform-select"></canvas>
    <canvas id="waveform-mouser"></canvas>
</div>
<br/>
<button class="btn btn-default" onclick="addAnnotation(); return false;">Add</button>
</body>
</html>