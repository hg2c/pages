class LoggingInterceptor implements Interceptor {
    @Override public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        long t1 = System.nanoTime();
        logger.info(String.format("Sending request %s on %s%n%s",
                                  request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        logger.info(String.format("Received response for %s in %.1fms%n%s",
                                  response.request().url(), (t2 - t1) / 1e6d, response.headers()));

        return response;
    }
}

OkHttpClient client = new OkHttpClient();
client.interceptors().add(new LoggingInterceptor());

Request request = new Request.Builder()
    .url("http://www.publicobject.com/helloworld.txt")
    .header("User-Agent", "OkHttp Example")
    .build();

Response response = client.newCall(request).execute();
response.body().close();

//创建Retrofit接口，这里用回调方式
public class API {
    public interface MyService {
        @GET("/{id}/api")
        void getFoods(@path("id") int id ,@QueryMap Map<String,Object> param,Callback<<List<Food>> callback);

        //这里要注意的是 post跟Patch方法都需要传Body(RESTful规范)
        @POST("/your/api")
        void createFood(@Body Food food,Callback<Response> callback);

        //跟post方法类似
        @PATCH("/your/api")
        void modifyFood(@Body Food food ,Callback<<List<Food>> callback);

        //删除
        @DELETE("/{id}/api")
        void deleteFood(@path("id") int id ,Callback<<List<Food>> callback);

    }
    public static MyService service = new RestAdapter.Builder()
            //设置你的请求EndPoint
            .setEndpoint(“http://123.0.0.0”)
            //设置Log提示
            .setLogLevel(BuildConfig.DEBUG ? RestAdapter.LogLevel.FULL : RestAdapter.LogLevel.NONE)
            //设置请求其他参数 例如加header
            .setRequestInterceptor(new RequestInterceptor() {
                @Override
                public void intercept(RequestFacade request) {
                    request.addHeader("token", "your token");
                }
            }).build().create(MyService.class);
}
//使用接口

API.service.getFoods(id,param, new Callback<List<Food>>() {
            @Override
            public void success(List<Food> foods, Response response) {
              //请求成功 处理你的操作
              // foods 为已经解析的数据
              // response 含有服务器返回的所有信息
            }

            @Override
            public void failure(RetrofitError error) {
              //请求失败
              //error含有所有错误信息
              //例如 error.getKind() == RetrofitError.Kind.NETWORK  网络连接异常
              //error.getResponse().getStatus(); 获取HTTP错误代码
            }
        });
