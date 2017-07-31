package com.deathbydeco.server.resources;

import com.codahale.metrics.annotation.Timed;
import com.deathbydeco.MainClass;
import com.deathbydeco.network.NeuralNetwork;
import com.deathbydeco.utility.Matrix;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataMultiPart;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.deathbydeco.network.NeuralNetwork.FileType;

import javax.activation.MimetypesFileTypeMap;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.*;
import java.io.*;
import java.util.List;

/**
 * Created by rex on 2017/03/16.
 * <p>
 * This class is used for inputting things into the server by sending it
 * through the frontend.
 */
@Path("/dataset")
@Produces(MediaType.TEXT_PLAIN)
public class DatasetResource {

    private Logger LOGGER = LoggerFactory.getLogger(DatasetResource.class);

    /**
     * Get the images specified.
     *
     * @param image the name of image
     * @return a response with the file attached.
     * @throws WebApplicationException if file does not exist
     */
    @GET
    @Timed
    @Path("/get")
    @Produces("image/*")
    public Response getImage(@DefaultValue("cat.jpg") @QueryParam("image") String image) {
        // Grab the file and throw error if it does not exist.
        File file = new File(MainClass.DATASETS_BASE_PATH + File.separator + image);
        if (!file.exists()) {
            throw new WebApplicationException(404);
        }
        // Prepare the response and send it back to the client.
        String fileType = new MimetypesFileTypeMap().getContentType(file);
        return Response.ok(file, fileType).build();
    }

    /**
     * Return the mean errors as a json string to the old_frontend.
     *
     * @return the mean errors as a json string to the old_frontend
     */
    @GET
    @Timed
    @Path("/status")
    @Produces(MediaType.APPLICATION_JSON)
    public String status() {
        JSONObject jsonResponse = new JSONObject();
        List<Double> meanErrors = MainClass.NEURAL_NETWORK.getMeanErrors();
        // Return an indicator that the server is currently busy
        if (meanErrors == null || meanErrors.size() == 0) {
            jsonResponse.put("status", "busy");
        } else {
            jsonResponse.put("status", "available");
            jsonResponse.put("errorThreshold", NeuralNetwork.ERROR_THRESHOLD);
            jsonResponse.put("learningRate", NeuralNetwork.LEARNING_RATE);
            jsonResponse.put("maxEpoch", MainClass.NEURAL_NETWORK.getMaxEpoch());
            jsonResponse.put("hiddenLayers", MainClass.NEURAL_NETWORK.getNumberOfHiddenLayers());
            // Else return the json response
            JSONArray meanErrorsJS = new JSONArray();
            for (double error : meanErrors) {
                meanErrorsJS.put(error);
            }
            jsonResponse.put("meanErrors", meanErrors);
        }
        return jsonResponse.toString();
    }

    /**
     * This function is called when the input is sent to the server.
     *
     * @param formDataMultiPart the data that was sent
     * @return the response from the server
     */
    @POST
    @Timed
    @Path("/post")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public String postInput(FormDataMultiPart formDataMultiPart) {
        // Reject request if no data was sent.
        if (formDataMultiPart == null) {
            throw new WebApplicationException(400);
        }
        JSONObject response = new JSONObject();
        JSONArray responseBody = new JSONArray();
        try {
            response.put("status", "successful");
            for (String key : formDataMultiPart.getFields().keySet()) {
                JSONObject objectPerFile = new JSONObject();
                objectPerFile.put("name", key);
                // Retrieve file content specified by the key
                List<FormDataBodyPart> contents = formDataMultiPart.getFields(key);
                FormDataBodyPart content = contents.get(0);
                // Transform the file content to input stream.
                InputStream inputStream = content.getValueAs(InputStream.class);
                // Query the file and then put the numbers into an array
                FileType result = MainClass.NEURAL_NETWORK.queryType(inputStream);
                objectPerFile.put("type", result.toString());
                responseBody.put(objectPerFile);
            }
            response.put("data", responseBody);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            response.put("status", "fail");
        }
        return response.toString();
    }
}
