package com.daewooenc.pips.admin.web.domain.vo.weather;

public class WeatherResponseVo {
    WeatherHeaderVo header;
    WeatherBodyVo body;

    public WeatherHeaderVo getHeader() {
        return header;
    }

    public void setHeader(WeatherHeaderVo header) {
        this.header = header;
    }

    public WeatherBodyVo getBody() {
        return body;
    }

    public void setBody(WeatherBodyVo body) {
        this.body = body;
    }
}
