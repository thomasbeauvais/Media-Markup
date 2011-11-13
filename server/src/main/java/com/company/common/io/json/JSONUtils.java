package com.company.common.io.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by IntelliJ IDEA.
 * User: tbeauvais
 * Date: 11/7/11
 * Time: 8:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class JSONUtils {

    public static short[] readShortArray(JSONObject parent, String key) {
        final JSONArray jsonArray   = (JSONArray) parent.get( key );
        final short[] shorts        = new short[jsonArray.size()];

        for (int i = 0; i < shorts.length; i++) {
            shorts[i] = ((Long) jsonArray.get(i)).shortValue();
        }

        return shorts;
    }

    public static long[] readLongArray(JSONObject parent, String key) {
        final JSONArray jsonArray   = (JSONArray) parent.get( key );
        final long[] longs          = new long[jsonArray.size()];

        for (int i = 0; i < longs.length; i++) {
            longs[i] = ((Long) jsonArray.get(i)).longValue();
        }

        return longs;
    }

    public static double[] readDoubleArray(JSONObject parent, String key) {
        final JSONArray jsonArray   = (JSONArray) parent.get( key );
        final double[] doubles          = new double[jsonArray.size()];

        for (int i = 0; i < doubles.length; i++) {
            doubles[i] = ((Double) jsonArray.get(i)).doubleValue();
        }

        return doubles;
    }

    public static float[] readFloatArray(JSONObject parent, String key) {
        final JSONArray jsonArray   = (JSONArray) parent.get( key );
        final float[] floats          = new float[jsonArray.size()];

        for (int i = 0; i < floats.length; i++) {
            floats[i] = ((Double) jsonArray.get(i)).floatValue();
        }

        return floats;
    }

    public static void writeShortArray(JSONObject parent, String key, short[] array ) {
        final JSONArray jsonArray        = new JSONArray();
        for ( short value : array ) {
            jsonArray.add(value);
        }

        parent.put( key, jsonArray );
    }

    public static void writeLongArray(JSONObject parent, String key, long[] array ) {
        final JSONArray jsonArray        = new JSONArray();
        for ( long value : array ) {
            jsonArray.add(value);
        }

        parent.put( key, jsonArray );
    }

    public static void writeFloatArray(JSONObject parent, String key, float[] array ) {
        final JSONArray jsonArray        = new JSONArray();
        for ( float value : array ) {
            jsonArray.add(value);
        }

        parent.put( key, jsonArray );
    }
}
