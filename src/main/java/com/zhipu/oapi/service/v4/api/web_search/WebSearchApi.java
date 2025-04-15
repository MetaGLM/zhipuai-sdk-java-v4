package com.zhipu.oapi.service.v4.api.web_search;

import com.zhipu.oapi.service.v4.web_search.WebSearchDTO;
import com.zhipu.oapi.service.v4.web_search.WebSearchRequest;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface WebSearchApi {

    @POST("web_search")
    Single<WebSearchDTO> webSearch(@Body WebSearchRequest request);

}
