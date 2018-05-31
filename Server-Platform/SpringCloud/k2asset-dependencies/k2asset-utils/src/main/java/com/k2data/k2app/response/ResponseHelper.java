package com.k2data.k2app.response;

import com.k2data.k2app.constant.ResponseCode;
import com.k2data.k2app.exception.DataNotFoundException;
import com.k2data.k2app.utils.ObjectUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * Response Helper
 *
 * @author WangShengguo
 */
public class ResponseHelper {

    public static <T> ResponseEntity<CommonResultResponse> success(T result, PageInfo pageInfo) throws Exception {

        return success(result, pageInfo, false);
    }

    /**
     * Response OK
     */
    public static <T> ResponseEntity<CommonResultResponse> success(T result) throws Exception {

        return success(result, null, false);
    }

    /**
     * For some functions return void
     * Response OK
     */
    public static ResponseEntity<CommonResultResponse> success() throws Exception {

        return success("", null, false);
    }


    /**
     * Response OK
     *
     * @param result     service result
     * @param emptyCheck empty check or not
     */
    @SuppressWarnings("unchecked")
    public static <T> ResponseEntity<CommonResultResponse> success(T result, PageInfo pageInfo, boolean emptyCheck) throws Exception {

        if (emptyCheck && ObjectUtils.isEmpty(result)) {
            throw new DataNotFoundException();
        }

        CommonResultResponse response = new CommonResultResponse();

        response.setCode(ResponseCode.GENERAL_SUCCESS);
        response.setMessage(ResponseCode.SUCCESS);
        response.setResult(result);
        response.setPageInfo(pageInfo);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
