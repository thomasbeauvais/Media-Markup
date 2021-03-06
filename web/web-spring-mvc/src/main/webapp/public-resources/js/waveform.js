(function ($)
{
    var LABEL = "waveform";

    var Waveform = function (element, options)
    {
        var settings = $.extend({
            param: 'defaultValue'
        }, options || {});

        this.drawWaveformSamples = function (samples)
        {
            this.resetCanvas();

            var canvas = element;

            var context = canvas.getContext("2d");

            var height = canvas.height;
            var width = canvas.width;

            var gradient = context.createLinearGradient(width / 2, 0, width / 2, height);
            gradient.addColorStop(0, "#000");
            gradient.addColorStop(0.5, "#00F");
            gradient.addColorStop(1, "#000");

            context.fillStyle = gradient;
            //            context.fillStyle = "rgb( 0, 0, 0 )";

            if (samples)
            {
                var center = Math.floor(height / 2);

                context.moveTo(0, center);

                // Drawing lines and using fill at the end of the loop allows for a
                // smoother waveform and faster load time than using stroke
                for (var x = 0; x < samples.length; x++)
                {
                    var value = samples[x] * height;
                    var y = value / 2;

                    context.lineTo(x, Math.floor(center - y));
                    context.lineTo(x, Math.floor(center + y));
                    context.moveTo(x, center);
                }

                // Reset the line so that it doesn't draw a diagonal all of the way across the wave from
                context.fill();
            }
        };

        this.resetCanvas = function ()
        {
            var canvas = element;

            var context = canvas.getContext("2d");

            var height = canvas.height;
            var width = canvas.width;

            context.clearRect(0, 0, width, height);

            // draw gradient
            //            var gradient = context.createLinearGradient(width / 2, 0, width / 2, height);
            //            gradient.addColorStop(0, "#FFF");
            //            gradient.addColorStop(0.5, "#00F");
            //            gradient.addColorStop(1, "#FFF");
            //
            //            context.fillStyle = gradient;
            //
            //            context.fillRect(0, 0, width, height);
        };

        this.resetCanvas();
    };

    $.fn.waveform = function (options)
    {
        if (this.data(LABEL))
        {
            return this.data(LABEL);
        }

        return this.each(function ()
        {
            $(this).data(LABEL, new Waveform(this, options));
        });
    };
})(jQuery);