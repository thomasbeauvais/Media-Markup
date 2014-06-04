<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Document</title>

    <link rel="stylesheet" type="text/css" href="/resources/css/application.css"/>

    <script type="application/javascript" src="/resources/javascript/jquery.js"></script>
    <script type="application/javascript" src="/resources/javascript/jquery.mousewheel.js"></script>
    <script type="application/javascript" src="/resources/javascript/application.js"></script>
    <script type="application/javascript" src="/resources/javascript/jquery-ui.js"></script>
    <script type="application/javascript" src="/resources/javascript/selection.js"></script>
    <script type="application/javascript" src="/resources/javascript/waveform.js"></script>
    <script type="application/javascript">
        var index = "<%= request.getParameter("index") %>";
        var zoom = 1;
        var waveform;
        var data;

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