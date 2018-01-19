package cl.idatum.overwatchapi.api;


public class ApiUtils {

    private ApiUtils(){};

    public  static final String BASE_URL= "https://overwatch-api.net/";

    /**
     * Obtiene y crea el cliente Retrofit
     * @return Retorna el cliente.
     */
    public static RetrofitApi getClient() {
        return ApiClient.getClient(BASE_URL).create(RetrofitApi.class);
    }
}
