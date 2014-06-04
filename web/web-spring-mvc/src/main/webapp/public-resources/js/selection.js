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

        var log = $(this).log(LABEL);

        this.selectionEnabled = function (isEnabled)
        {
            self.isSelectionEnabled = isEnabled;
        };

        this.clear = function ()
        {
            self.x1 = -1;
            self.x2 = -1;

            self.isDragging = false;

            cleanCanvas(element);
        };

        this.mousewheel = function (event)
        {
        };

        this.mouseenter = function (event)
        {
        };

        this.mouseout = function (event)
        {
            // This should only set it on the first time we escape if we are dragging!
            if (self.isDragging == false)
            {
            }
            else if (event.layerX < 0)
            {
                self.paint(self.x1, 0);
            }
            else if (event.layerX > self.width)
            {
                self.paint(self.x1, self.width);
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
            if (!self.isSelectionEnabled)
            {
                return;
            }

            self.isDragging = true;
            self.x1 = event.layerX;
        };

        this.mousemove = function (event)
        {
            if (self.isDragging == false)
            {
                return;
            }

            self.paint(self.x1, event.layerX);
        };

        this.paint = function (startX, endX)
        {
            var canvas = element;

            // Sync these to make sure that anyone who asks from the selected region can retrieve it..
            this.x1 = startX;
            this.x2 = endX;

            var width = this.endX() - this.startX();
            var height = canvas.height;

            var context = canvas.getContext("2d");
            context.clearRect(0, 0, canvas.width, canvas.height);
            context.fillStyle = "rgba(255, 0, 0, 0.2)";
            context.fillRect(this.startX(), 0, width, height);

            $(log).html("startX: " + this.startX() + ", width:" + width);
        };

        this.startX = function()
        {
            return Math.min(this.x1, this.x2);
        };

        this.endX = function()
        {
            return Math.max(this.x1, this.x2);
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