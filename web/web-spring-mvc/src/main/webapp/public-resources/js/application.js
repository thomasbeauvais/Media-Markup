(function ($)
{
    $.fn.mouser = function ()
    {
        var objects = arguments;

        var mouseFunction = function (event)
        {
            for (var i = 0; i < objects.length; i++)
            {
                if (objects[i] && objects[i][ event.type ])
                {
                    objects[i][ event.type ](event);
                }
            }
        };

        this.mousewheel(function (event)
        {
            mouseFunction(event)
        });

        this[0].addEventListener('mousedown', mouseFunction, false);
        this[0].addEventListener('mouseup', mouseFunction, false);
        this[0].addEventListener('mousemove', mouseFunction, false);
        this[0].addEventListener('click', mouseFunction, false);
        this[0].addEventListener('mouseenter', mouseFunction, false);
        this[0].addEventListener('mouseout', mouseFunction, false);
    };

    $.fn.fit = function ()
    {
        this.children().each(function ()
        {
            this.width = this.parentElement.clientWidth;
            this.height = this.parentElement.clientHeight;
        });
    }
}(jQuery));