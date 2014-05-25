package org.branch.annotation.audio.controller;

import org.apache.log4j.Logger;
import org.branch.annotation.audio.model.VisualData;
import org.branch.annotation.audio.model.VisualParameters;
import org.branch.annotation.audio.model.VisualRegion;
import org.branch.annotation.audio.services.VisualDataService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created with IntelliJ IDEA.
 * User: tbeauvais
 * Date: 7/4/12
 * Time: 1:02 AM
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("/visualData")
public class VisualDataController
{
    @Autowired
    private VisualDataService visualDataService;

    private static Logger logger = Logger.getLogger("org.branch.annotation.audio");

    /**
     * @param uid
     * @param width
     * @param height
     * @return
     *
     * @throws Exception
     */
    @RequestMapping
    @ResponseBody
    @Transactional
    public String visual_data_json(@RequestParam String uid, @RequestParam Integer width, @RequestParam Integer height) throws Exception
    {
        logger.debug(" **** Loading visual data for: " + uid);
        logger.debug(" **** Parameters: width=" + width + ", height=" + height);

        final VisualParameters visualParameters = new VisualParameters();
        visualParameters.setHeight(height);
        visualParameters.setWidth(width);

        final VisualData visualData = visualDataService.loadVisualData(uid, visualParameters);

        // in this instance we want to create a custom more explicit json object
        final JSONObject jsonObject = new JSONObject();
        final JSONArray jsonSamples = new JSONArray();
        final JSONArray jsonPositions = new JSONArray();

        final int[] visualSamples = visualData.getVisualSamples();
        final long[] visualPositions = visualData.getVisualPositions();
        for (int i = 0, visualSamplesLength = visualSamples.length; i < visualSamplesLength; i++)
        {
            final int value = visualSamples[i];
            final long position = visualPositions[i];

            jsonSamples.add(value);
            jsonPositions.add(position);
        }

        final JSONArray jsonRegions = new JSONArray();
        for (VisualRegion visualRegion : visualData.getVisualRegions())
        {
            final JSONObject region = new JSONObject();
            region.put("uid", visualRegion.getUid());
            region.put("startX", visualRegion.getStartX());
            region.put("endX", visualRegion.getEndX());

            jsonRegions.add(region);
        }

        jsonObject.put("samples", jsonSamples);
        jsonObject.put("positions", jsonPositions);
        jsonObject.put("regions", jsonRegions);

        return jsonObject.toJSONString();
    }
}
