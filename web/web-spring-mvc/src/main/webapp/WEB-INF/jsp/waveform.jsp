<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
    <title>Document</title>
    <script type="application/javascript" src="/resources/javascript/jquery.js"></script>
    <script type="application/javascript" src="/resources/javascript/jquery.mousewheel.js"></script>
    <script type="application/javascript" src="/resources/javascript/jquery.scraggable.js"></script>
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
                width: $("#waveform").width(),
                zoom: zoom
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
            waveform.clear();
            waveform.setData(data.samples);
            waveform.redraw();
        }

        $(document).ready(function ()
        {
            var $waveform = $("#waveform");
            waveform = new Waveform(
            {
                container: document.getElementById("waveform")
            });

            if (index)
            {
                loadSamples();
            }

            $waveform.on('mousewheel', function(event) {
                zoom = Math.max(1, zoom + event.deltaY);
                zoom = Math.min(10, zoom);

                loadSamples();

                event.preventDefault();
            })
        });

    </script>
</head>
<body>

<div id="waveform" style="height: 360">
</div>

</body>
</html>