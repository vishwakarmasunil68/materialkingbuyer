package com.appentus.materialking.webservice;

/**
 * Created by ggg on 2/17/2018.
 */

public class WebServiceUrl {


//    public static String BASEURL = "http://appentus.me/mk/appiqo_bid/";
//    public static String BASE_IP = "http://192.168.2.4";
    public static String BASE_IP = "http://appentus.me";
    public static String BASEURL = BASE_IP+"/mk/appiqo_bid/";

    public static String IMAGEBASEURL = BASEURL + "uploads/";

    public static String getReceiptUrl(String order_id,String user_id,String seller_id){
        return BASE_IP+"/mk/receipt1.php?order_id="+order_id+"&user_id="+user_id+"&seller_id="+seller_id;
    }

    public static final String LOGINBUYER = BASEURL + "webservice_v2/login";
    public static final String create_user = BASEURL + "webservice_v2/create_user";
    public static final String authenticate_otp = BASEURL + "webservice_v2/authenticate_otp";
    public static final String resend_otp = BASEURL + "webservice_v2/resend_otp";
    public static final String user_register = BASEURL + "webservice_v2/user_register";
    public static final String update_profile = BASEURL + "webservice_v2/update_profile";
    public static final String update_profile_details = BASEURL + "webservice_v2/update_profile_details";
    public static final String get_home_data = BASEURL + "webservice_v2/get_home_data";
    public static final String get_products_by_id = BASEURL + "webservice_v2/get_products_by_id";
    //    public static final String GET_PRODUCTS_BY_FILTERS = BASEURL+"get_products_by_filers";
    public static final String GET_PRODUCTS_BY_FILTERS = BASEURL + "webservice_v2/get_filter_products";
    public static final String ADD_PRODUCT_TO_CART = BASEURL + "webservice_v2/add_product_to_cart";
    public static final String CART_LIST = BASEURL + "webservice_v2/get_my_cart_detail";
    public static final String GENERATE_MY_ORDER = BASEURL + "webservice_v2/generate_my_order";
    public static final String MY_ORDER_LIST = BASEURL + "webservice_v2/get_all_orders_for_buyer";
    public static final String GET_FILTERS = BASEURL + "webservice_v2/get_filers_for_product";
    public static final String GET_PRODUCT_FULL_DETAIL_BY_FILTERS = BASEURL + "webservice_v2/get_product_full_detail_by_filers";
    public static final String GET_SELLER_TYPE = BASEURL + "webservice_v2/get_all_seller_type";
    public static final String GET_SELLER_TYPES = BASEURL + "SellerRegistration/getSellerTypes";
    public static final String GET_ORDER_PARTIALS_FOR_BUYER = BASEURL + "webservice_v2/get_orders_partial_for_buyer";
    public static final String UPDATE_CART_ITEM = BASEURL + "webservice_v2/update_cart_product_item";
    public static final String DELETE_CART_ITEM = BASEURL + "webservice_v2/delete_cart_item";
    public static final String GET_COMPLETE_ORDER_DETAIL = BASEURL + "webservice_v2/get_complete_order_detail";
    public static final String GET_COMPLETE_BID_PRODUCT_INFO = BASEURL + "webservice_v2/get_complete_bid_product_info";
    public static final String GET_PARTIAL_ORDER_DETAILS = BASEURL + "webservice_v2/get_partial_order_detail";
    public static final String GET_RECOMMENDED_BID = BASEURL + "webservice_v2/get_recommended_bid";
    public static final String GET_RECOMMENDED_BID_INFO = BASEURL + "webservice_v2/get_recommended_bid_info";
    public static final String GET_RECOMMENDED_BID_DETAIL_INFO = BASEURL + "webservice_v2/get_recommended_bid_detail_info";
    public static final String INSERT_BID_PRODUCTS = BASEURL + "ProcessOrder/insert_selected_order_product";
    public static final String INSERT_FINAL_BID_PRODUCT = BASEURL + "ProcessOrder/insert_final_selected_order";
    public static final String GET_OFFER_DETAILS = BASEURL + "ProcessOrder/get_my_order_selection";
    public static final String DELETE_OFFER_DATA = BASEURL + "ProcessOrder/delete_my_selected_product";
    public static final String GET_ORDER_ITEMS = BASEURL + "orderctrl/getOrderItems";
    public static final String GET_PRODUCT_BID = BASEURL + "orderctrl/getProductBids";
    public static final String PLACE_FINAL_OFFER = BASEURL + "orderctrl/place_final_order";
    public static final String NOTIFICATION_LIST = BASEURL + "ProcessOrder/get_buyer_notification";
    public static final String GET_NOTIFICATION = BASEURL + "ProcessOrder/get_notification";
    public static final String GET_SELLER_DETAIL = BASEURL + "ProcessOrder/get_seller_detail";

    public static final String GET_STATES_OF_INDIA = BASEURL + "SellerRegistration/getAllStatesofIndia";
    public static final String GET_CITIES = BASEURL + "SellerRegistration/getCitiesFromStates";
    public static final String GET_ALL_SELECTED_OFFERS = BASEURL + "ProcessOrder/getBuyerSelectedOffers";
}
