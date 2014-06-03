(function ($)
{
    var Waveform = function (element, options)
    {
        var obj = this;
        var settings = $.extend({
            param: 'defaultValue'
        }, options || {});

        // Need to set the width after it's been added to the screen
        // The canvas width was the coordinate system with (default is 150 X 300 )
        element.width = element.offsetWidth;
        element.height = element.offsetHeight;

        this.drawWaveformSamples = function (samples)
        {
            this.resetCanvas();

            var canvas = element;

            var context = canvas.getContext("2d");

            var height = canvas.height;
            var width = canvas.width;

            context.strokeStyle = "rgb( 0, 0, 0 )";
            context.fillStyle = "rgb( 0, 0, 0 )";

            if (samples)
            {
                var center = Math.floor(height / 2);

                context.moveTo(0, center);

                for (var x = 0; x < samples.length; x++)
                {
                    var value = samples[x] * height;
                    var y = value / 2;

                    context.lineTo(x, center - y);
                    context.lineTo(x, center + y);
                }

                context.lineTo(x - 1, center);

                context.fill();
//                context.stroke();
            }
        };

        this.resetCanvas = function ()
        {
            this.resizeCanvas();

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

        this.resizeCanvas = function ()
        {
            var canvas = element;

            canvas.width = canvas.clientWidth;
            canvas.height = canvas.clientHeight;
        };

        this.resetCanvas();
    };

    $.fn.waveform = function (options)
    {
        return this.each(function ()
        {
            var element = $(this);

            // Return early if this element already has a plugin instance
            if (element.data('waveform'))
            {
                return;
            }

            // pass options to plugin constructor
            var waveform = new Waveform(this, options);

            // Store plugin object in this element's data
            element.data('waveform', waveform);
        });
    };
})(jQuery);