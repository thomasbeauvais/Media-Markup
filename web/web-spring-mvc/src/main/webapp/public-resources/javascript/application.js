(function ($)
{
    $.fn.fit = function ()
    {
        this.children().each(function ()
        {
            this.width = this.parentElement.clientWidth;
            this.height = this.parentElement.clientHeight;
        });
    }
}(jQuery));