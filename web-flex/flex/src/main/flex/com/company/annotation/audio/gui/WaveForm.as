/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 3/8/12
 * Time: 6:31 PM
 * To change this template use File | Settings | File Templates.
 */
package com.company.annotation.audio.gui {
import com.company.annotation.audio.pojos.SampleList;

import flash.display.GradientType;
import flash.display.Graphics;
import flash.display.InterpolationMethod;
import flash.display.Shape;
import flash.display.SpreadMethod;
import flash.geom.Matrix;

import mx.containers.Canvas;

public class WaveForm extends Canvas {
    public var sampleList:SampleList;

    public function WaveForm() {

    }


//    public function drawPlayedRegion( canvas, sound ) {
//        var context             = canvas.getContext("2d");
//
//        var percentPlayed       = sound.position / sound.durationEstimate;
//
//        var height              = canvas.height;
//        var x                   = canvas.width * percentPlayed;
//
//        context.fillStyle   = "#333";
//
//        context.fillRect( x, 0, 1, height );
//        context.fill();
//
//        context.beginPath()
//        context.moveTo( x - 10, height );
//        context.lineTo( x, height - 20  );
//        context.lineTo( x + 10, height  );
//        context.lineTo( x -10, height  );
//        context.fill();
//        context.closePath()
//    }

    public function draw():void {
        var context:Graphics    = graphics;

        var height:int          = this.height;
        var width:int           = this.width;

        var type:String = GradientType.LINEAR;
        var colors:Array = [0x00FF00, 0x000088];
        var alphas:Array = [1, 1];
        var ratios:Array = [0, 255];
        var spreadMethod:String = SpreadMethod.PAD;
        var interp:String = InterpolationMethod.LINEAR_RGB;
        var focalPtRatio:Number = 0;

        var matrix:Matrix = new Matrix();
        var boxWidth:Number = 50;
        var boxHeight:Number = 100;
        var boxRotation:Number = Math.PI/2; // 90?
        var tx:Number = 25;
        var ty:Number = 0;
        matrix.createGradientBox(boxWidth, boxHeight, boxRotation, tx, ty);

        var square:Shape = new Shape;
//        square.graphics.beginGradientFill(type,
//                                        colors,
//                                        alphas,
//                                        ratios,
//                                        matrix,
//                                        spreadMethod,
//                                        interp,
//                                        focalPtRatio);
//        square.graphics.drawRect(0, 0, 100, 100);

        if ( sampleList && sampleList.samples ) {
            var startX:int      = 0;

            var dataArray:Array = sampleList.samples;

            var step:int            = dataArray.length / width;
            var scale:Number        = sampleList.max / height;

            var center:int          = Math.floor( height / 2 );

            context.beginFill( 0xFF0000 );
            context.lineStyle( 1, 0xFF0000 );

            context.moveTo( startX, center );

            for ( var i:int = 0; i < dataArray.length; i++, startX++ ) {
                var max = 0;
                var min = 0;

                for ( var s:int = 0; s < step && i < dataArray.length; s++, i++ ) {
                    max             = Math.max( max, dataArray[i].value );
                    min             = Math.min( min, dataArray[i].value );
                }

                var value           = ((max + (min * -1) ) / scale);
                var y               = value / 4;

                context.lineTo( startX, center - y );
                context.lineTo( startX, center + y );
            }

//            context.endFill()
        }
    }

//    public function drawLoadedRegion( canvas, sound ) {
//        var context             = canvas.getContext("2d");
//
//        var percentLoaded       = sound.bytesLoaded / sound.bytesTotal;
//
//        var height              = canvas.height;
//        var width               = canvas.width * percentLoaded;
//
//            context.fillStyle   = "rgba( 150, 0, 0, 0.1 )";
//
//            context.fillRect( 0, 0, width, height );
//    }

}
}