(function ($)
{
    var LABEL = "selection";

    var Selection = function (element, options)
    {
        var self = this;
        var element = element;

        var settings = $.extend({
            param: 'defaultValue'
        }, options || {});

        this.selectionEnabled = function (isEnabled)
        {
            self.isSelectionEnabled = isEnabled;
        };

        this.clear = function ()
        {
            self.startX = -1;
            self.endX = -1;

            self.isDragging = false;

            cleanCanvas(element);
        };

        this.mousein = function (event)
        {
            console.log("aching!");
        };

        this.mouseout = function (event)
        {
            // This should only set it on the first time we escape if we are dragging!
            if (self.isDragging == false)
            {
            }
            else if (event.layerX < 0)
            {
                self.paint(self.startX, 0);
            }
            else if (event.layerX > self.width)
            {
                self.paint(self.startX, self.width);
            }

            self.isDragging = false;
        };

        this.mouseclick = function (event)
        {
        };

        this.mouseup = function (event)
        {
            self.isDragging = false;
        };

        this.mousedown = function (event)
        {
            // TODO:  When nothing is loaded don't allow selection
            if (!self.isSelectionEnabled)
            {
                return;
            }

            self.isDragging = true;
            self.startX = event.layerX;
        };

        this.mousemove = function (event)
        {
            if (self.isDragging == false)
            {
                return;
            }

            self.paint(self.startX, event.layerX);
        };

        this.paint = function (startX, endX)
        {
            var canvas = element;
            // Sync these to make sure that anyone who asks from the selected region can retrieve it..
            this.startX = startX;
            this.endX = endX;

            var x = Math.min(startX, endX);
            var width = Math.max(startX, endX) - x;
            var height = canvas.height;

            var context = canvas.getContext("2d");
            context.clearRect(0, 0, canvas.width, canvas.height);
            context.fillStyle = "rgba(255, 0, 0, 0.2)";
            context.fillRect(x, 0, width, height);

            //console.log( "height=" + height + ", endX=" + endX + ", x=" + x + ", width=" + width + ",canvas.width=" + this.overlayCanvas.width );
        };
    };

    $.fn.selection = function (options)
    {
        if (this.data(LABEL))
        {
            return this.data(LABEL);
        }

        return this.each(function ()
        {
            $(this).data(LABEL, new Selection(this, options));
        });
    };
})(jQuery);