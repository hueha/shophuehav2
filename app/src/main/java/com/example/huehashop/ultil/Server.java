package com.example.huehashop.ultil;

public class Server {

    // server offline
    public static String domain = "192.168.56.1";
    public static String localhost = domain + "/shophueha/server/";
    public static String duongdanloaisp = "http://" + localhost + "getloaisp.php";
    public static String duongdanspmoinhat = "http://" + localhost + "getsanphammoinhat.php";
    public static String duongdansp = "http://" + localhost + "getsanpham.php?page=";
    public static String duongdandonhang = "http://" + localhost + "thongtinkhachhang.php";
    public static String duongdanchitietdonhang = "http://" + localhost + "chitietdonhang.php";
    // server online

    /*
    public static String base_url = "http://lacai.000webhostapp.com/server/";
    public static String duongdanloaisp = base_url + "getloaisp.php";
    public static String duongdanspmoinhat = base_url + "getsanphammoinhat.php";
    public static String duongdansp = base_url + "getsanpham.php?page=";
    */

}
