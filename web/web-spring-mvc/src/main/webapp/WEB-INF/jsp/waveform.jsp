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
            $("#waveform-canvas").waveform().drawWaveformSamples(data.samples);
        }

        $(function ()
        {
            $("#waveform-canvas").waveform();

            $("#waveform-select").selection();

            $("#waveform-mouser").mouser($("#waveform-select").selection());

            $("#waveform").fit();

            if (index)
            {
                loadSamples();
            }
        });

    </script>
</head>
<body>

<div id="waveform" style="height: 360; overflow-x:scroll;">
    <canvas id="waveform-canvas"></canvas>
    <canvas id="waveform-select"></canvas>
    <canvas id="waveform-mouser"></canvas>
</div>
</body>
</html>