package com.alpha.globalConfig;

import com.alpha.GeneralExceptionResponse;
import net.minidev.json.JSONArray;
import org.hamcrest.Matchers;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * This class is used to assert that error response contains additional data described in
 * {@link GeneralExceptionResponse}.
 */
public class GlobalErrorMatcher
{
    private static <T> void assertFieldMatches(ResultActions resultActions, String jsonPath, T expected) throws Exception
    {
        if (expected == null)
        {
            resultActions.andExpect(jsonPath(jsonPath, Matchers.anything()));
        }
        else
        {
            resultActions.andExpect(jsonPath(jsonPath, Matchers.equalTo(expected)));
        }
    }

    public static void assertErrorResponseContainFields(ResultActions resultActions) throws Exception
    {
        assertFieldMatches(resultActions, "$.message", null);
        assertFieldMatches(resultActions, "$.timestamp", null);
        assertFieldMatches(resultActions, "$.servicePath", null);
        assertFieldMatches(resultActions, "$.queryParameterMap", null);
        assertFieldMatches(resultActions, "$.status", null);
    }

    public static void assertErrorResponseContainFields(ResultActions resultActions, String message, String servicePath,
                                                        Map<String, String[]> queryParameterMap, Integer status)
            throws Exception
    {
        assertFieldMatches(resultActions, "$.message", message);
        assertFieldMatches(resultActions, "$.timestamp", null);
        assertFieldMatches(resultActions, "$.servicePath", servicePath);

        for (var mapEntry : queryParameterMap.entrySet())
        {
            var jsonArray = new JSONArray();
            jsonArray.addAll(Arrays.asList(mapEntry.getValue()));
            resultActions.andExpect((jsonPath("$.queryParameterMap." + mapEntry.getKey(),
                    Matchers.samePropertyValuesAs(jsonArray))));
        }

        assertFieldMatches(resultActions, "$.status", status);
    }

    public static void isErrorResponseFormattedCorrectly(ResultActions resultActions, HttpStatus expectedStatus)
            throws Exception
    {
        resultActions.andExpect(header().string("Content-Type", "application/json"));
        resultActions.andExpect(status().is(expectedStatus.value()));
        assertErrorResponseContainFields(resultActions);
    }

    public static void isErrorResponseFormattedCorrectly(ResultActions resultActions, HttpStatus expectedStatus,
                                                         String message, String servicePath,
                                                         Map<String, String[]> queryParameterMap)
            throws Exception
    {
        resultActions.andExpect(header().string("Content-Type", "application/json"));
        resultActions.andExpect(status().is(expectedStatus.value()));
        assertErrorResponseContainFields(resultActions, message, servicePath, queryParameterMap, expectedStatus.value());
    }
}
