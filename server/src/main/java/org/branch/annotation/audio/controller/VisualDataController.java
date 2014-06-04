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

@Controller
@RequestMapping("visual")
public class VisualDataController
{
    @Autowired
    private VisualDataService visualDataService;

    private static Logger logger = Logger.getLogger("org.branch.annotation.audio");

    /**
     * @param index
     * @param width
     * @param zoom
     * @return
     *
     * @throws Exception
     */
    @RequestMapping
    @ResponseBody
    @Transactional(readOnly = true)
    public String visual_data_json(@RequestParam String index,
                                   @RequestParam int width,
                                   @RequestParam(defaultValue = "1") int zoom,
                                   @RequestParam(defaultValue = "0") int start) throws Exception
    {
        logger.debug(" **** Loading visual data for: " + index);
        logger.debug(" **** Parameters: width=" + width + ", zoom=" + zoom);

        final VisualParameters visualParameters = new VisualParameters();
        visualParameters.setZoom(zoom);
        visualParameters.setWidth(width);
        visualParameters.setCenter(start);

        final VisualData visualData = visualDataService.loadVisualData(index, visualParameters);

        return toJSON(visualData);
    }

    /**
     * @param index
     * @return
     *
     * @throws Exception
     */
    @RequestMapping("/all")
    @ResponseBody
    @Transactional(readOnly = true)
    public String visual_all_data_json(@RequestParam String index) throws Exception
    {
        logger.debug(" **** Loading visual data for: " + index);

        final VisualData visualData = visualDataService.loadAllVisualData(index);

        return toJSON(visualData);
    }

    private String toJSON(VisualData visualData)
    {
        // in this instance we want to create a custom more explicit json object
        final JSONObject jsonObject = new JSONObject();
        final JSONArray jsonSamples = new JSONArray();
        final JSONArray jsonPositions = new JSONArray();

        final float[] visualSamples = visualData.getVisualSamples();
        final long[] visualPositions = visualData.getVisualPositions();
        for (int i = 0, visualSamplesLength = visualSamples.length; i < visualSamplesLength; i++)
        {
            final double value = visualSamples[i];
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
