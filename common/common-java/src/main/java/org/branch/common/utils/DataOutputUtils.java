package org.branch.common.utils;

import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: tbeauvais
 * Date: 5/28/12
 * Time: 2:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class DataOutputUtils {
    public static void writeShortArray(DataOutput dataOutput, short[] shorts) throws IOException {
        int len = shorts.length;

        dataOutput.writeInt( len );
        for ( int i = 0; i < len; i++ ) {
            dataOutput.writeShort( shorts[ i ] );
        }
    }

    public static void writeFloatArray(DataOutput dataOutput, float[] floats) throws IOException {
        int len = floats.length;

        dataOutput.writeInt( len );
        for ( int i = 0; i < len; i++ ) {
            dataOutput.writeFloat(floats[i]);
        }
    }

    public static void writeLongArray(DataOutput dataOutput, long[] longs) throws IOException {
        int len = longs.length;

        dataOutput.writeInt( len );
        for ( int i = 0; i < len; i++ ) {
            dataOutput.writeFloat(longs[i]);
        }
    }

    public static short[] readShortArray(DataInput dataInput) throws IOException {
        int len = dataInput.readInt();

        short[] shorts = new short[len];

        for ( int i = 0; i < len; i++ ) {
            shorts[ i ] = dataInput.readShort();
        }

        return shorts;
    }

    public static long[] readLongArray(DataInput dataInput) throws IOException {
        int len = dataInput.readInt();

        long[] longs = new long[len];

        for ( int i = 0; i < len; i++ ) {
            longs[ i ] = dataInput.readLong();
        }

        return longs;
    }

    public static float[] readFloatArray(DataInput dataInput) throws IOException {
        int len = dataInput.readInt();

        float[] floats = new float[len];

        for ( int i = 0; i < len; i++ ) {
            floats[ i ] = dataInput.readFloat();
        }

        return floats;
    }
}
