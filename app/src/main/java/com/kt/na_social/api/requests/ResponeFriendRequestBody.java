package com.kt.na_social.api.requests;

public class ResponeFriendRequestBody {
    private long requestId;
    private int response;

    public long getRequestId() {
        return requestId;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }

    public int getResponse() {
        return response;
    }

    public void setResponse(int response) {
        this.response = response;
    }
}
