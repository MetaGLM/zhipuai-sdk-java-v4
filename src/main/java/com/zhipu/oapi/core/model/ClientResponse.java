package com.zhipu.oapi.core.model;

import com.zhipu.oapi.service.v4.model.ChatError;
import io.reactivex.Flowable;

public interface ClientResponse<T> {

    public T getData();

    public void setData(T data);

    void setCode(int code);

    void setMsg(String msg);

    void setSuccess(boolean b);

    void setError(ChatError chatError);

}
